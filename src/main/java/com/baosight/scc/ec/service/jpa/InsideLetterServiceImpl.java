package com.baosight.scc.ec.service.jpa;

import com.baosight.scc.ec.model.EcUser;
import com.baosight.scc.ec.model.InsideLetter;
import com.baosight.scc.ec.repository.InsideLetterRepository;
import com.baosight.scc.ec.service.EcUserService;
import com.baosight.scc.ec.service.InsideLetterService;
import com.baosight.scc.ec.utils.GuidUtil;
import com.baosight.scc.ec.utils.ListSort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.*;

/**
 * Created by sam on 2014/6/27.
 */
@Service
public class InsideLetterServiceImpl implements InsideLetterService {

    @Autowired
    private InsideLetterRepository insideLetterRepository;
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private EcUserService ecUserService;

    @Override
    public Page<InsideLetter> findLettersByReceiver(EcUser receiveUser, Integer letterType, Integer letterStatus, Pageable pageable) {
        return insideLetterRepository.findByReceiveUserAndLetterTypeAndLetterStatusNot(receiveUser,letterType,letterStatus,pageable);
    }

    @Override
    public Page<InsideLetter> findLettersBySender(EcUser sendUser, Integer letterType, Integer letterStatus, Pageable pageable) {
        return insideLetterRepository.findBySendUserAndLetterTypeAndLetterStatusNot(sendUser,letterType,letterStatus,pageable);
    }

    @Override
    public InsideLetter findLetterById(String id) {
        InsideLetter insideLetter = insideLetterRepository.findOne(id);
        if (insideLetter.getLetterStatus() == 0){
            insideLetter.setLetterStatus(1);
        }
        return insideLetter;
    }

    @Override
    public InsideLetter save(InsideLetter insideLetter) {
        insideLetter.setId(GuidUtil.newGuid());
      //  em.persist(insideLetter);
        return this.insideLetterRepository.save(insideLetter);
    }

    @Override
    public void updateLetterStatus(String[] arr,Integer letterStatus) {
        for (String id : arr){
            InsideLetter insideLetter = insideLetterRepository.findOne(id);
            insideLetter.setLetterStatus(letterStatus);
        }
    }

    @Override
    public Page<InsideLetter> findLettersAboutUser(EcUser user, Pageable pageable) {
        /*String nSql = "select sender_id,receiver_id from(select row_number() over() as rownum, sender_id,receiver_id " +
                " from (select * from t_ec_insideletter where sender_id='" +user.getId()+ "' or receiver_id='"+user.getId()+"' "+
                " order by createdtime desc) a " +
                " group by sender_id,receiver_id) b " +
                " where rownum >"+pageable.getOffset()+ " and rownum<="+pageable.getPageSize();

        String nCount = "select count(1) from(select row_number() over() as rownum, sender_id,receiver_id " +
                " from (select * from t_ec_insideletter where sender_id='" +user.getId()+ "' or receiver_id='"+user.getId()+"' "+
                " order by createdtime desc) a " +
                " group by sender_id,receiver_id) b ";
        System.out.println("===========nsql="+nSql);
        System.out.println("===========nCount="+nCount);

        String querySql1 = "select t from InsideLetter t where t.receiveUser=:cuser and t.sendUser=:user1 order by t.createdTime desc";
        String querySql2 = "select t from InsideLetter t where t.sendUser=:cuser and t.receiveUser=:user2 order by t.createdTime desc";
        //nsql结果集
        List nList = new ArrayList();
        //querySql1结果集
        List<InsideLetter> sqlList1 = new ArrayList<InsideLetter>();
        //querSql2结果集
        List<InsideLetter> sqlList2 = new ArrayList<InsideLetter>();
        //合并querySql1、querySql2结果集
        List<InsideLetter> result = new ArrayList<InsideLetter>();
        Query q1=em.createQuery(querySql1);
        Query q2=em.createQuery(querySql2);
        Query nq=em.createNativeQuery(nSql);
        Query nCountQ=em.createNativeQuery(nCount);
        nList = nq.getResultList();
        for (Iterator iterator=nList.iterator();iterator.hasNext();){
            EcUser user1=new EcUser();
            EcUser user2=new EcUser();
            Object[] values=(Object[])iterator.next();
            String senderId=(String)values[0];
            String receiverId=(String)values[1];
            user1.setId(senderId);
            user2.setId(receiverId);
            q1.setParameter("cuser",user).setParameter("user1",user1);
            q2.setParameter("cuser",user).setParameter("user2",user2);
            sqlList1 = q1.getResultList();
            sqlList2 = q2.getResultList();
            InsideLetter letter = null;
            if(sqlList2 !=null){
                if (sqlList2.size()>0){
                    letter = sqlList2.get(0);
                    result.add(sqlList2.get(0));
                }
            }
            if(sqlList1 !=null){
                if (sqlList1.size()>0){
                    letter = sqlList1.get(0);
                    result.add(sqlList1.get(0));
                }
            }
        }
        for(InsideLetter letter:result){
            EcUser sendUser = ecUserService.findById(letter.getSendUser().getId());
            EcUser receiverUser = ecUserService.findById(letter.getReceiveUser().getId());
            letter.setSendUser(sendUser);
            letter.setReceiveUser(receiverUser);
        }
        //自定义排序
        Collections.sort(result,new ListSort());
        Long len = new Long((Integer)nCountQ.getSingleResult());
        Page<InsideLetter> page=null;
        page = new PageImpl<InsideLetter>(result,pageable,len);*/
        String sql = "select i from InsideLetter i where (i.receiveUser=:user1 or i.sendUser=:user2) and i.flag=1";
        String countSql = "select count(i) from InsideLetter i where (i.receiveUser=:user1 or i.sendUser=:user2) and i.flag=1";
        List<InsideLetter> result = em.createQuery(sql).setParameter("user1",user).setParameter("user2",user).setFirstResult(pageable.getOffset()).setMaxResults(pageable.getPageSize()).getResultList();
        Long count = (Long)em.createQuery(countSql).setParameter("user1",user).setParameter("user2",user).getSingleResult();
        Page<InsideLetter> page = null;
        List<InsideLetter> list = new ArrayList<InsideLetter>();
        for(InsideLetter letter : result){
            EcUser receiver = ecUserService.findById(letter.getReceiveUser().getId());
            EcUser sender = ecUserService.findById(letter.getSendUser().getId());
            letter.setReceiveUser(receiver);
            letter.setSendUser(sender);
            list.add(letter);
        }
        page = new PageImpl<InsideLetter>(list,pageable,count);
        return page;
    }

    @Override
    public Page<InsideLetter> findLetterDetailsAboutUserTalks(EcUser user1, EcUser user2, Pageable pageable) {
        String querySql = "select t from InsideLetter t where (t.receiveUser=:user1 and t.sendUser=:user2) or (t.receiveUser=:user3 and t.sendUser=:user4) order by t.createdTime desc";
        String countSql = "select count(t) from InsideLetter t where (t.receiveUser=:user1 and t.sendUser=:user2) or (t.receiveUser=:user3 and t.sendUser=:user4)";
        Query query = em.createQuery(querySql).setParameter("user1",user1).setParameter("user2",user2).setParameter("user4",user1).setParameter("user3",user2);
        Query countQuery = em.createQuery(countSql).setParameter("user1",user1).setParameter("user2",user2).setParameter("user4",user1).setParameter("user3", user2);
        List<InsideLetter> result = query.setFirstResult(pageable.getOffset()).setMaxResults(pageable.getPageSize()).getResultList();
        for(InsideLetter letter:result){
            EcUser sendUser = ecUserService.findById(letter.getSendUser().getId());
            EcUser receiverUser = ecUserService.findById(letter.getReceiveUser().getId());
            letter.setSendUser(sendUser);
            letter.setReceiveUser(receiverUser);
        }
        Long len = (Long)countQuery.getSingleResult();
        Page<InsideLetter> page = null;
        page = new PageImpl<InsideLetter>(result,pageable,len);
        return page;
    }

    @Override
    public int countByReceiverUserAndLetterStatus(EcUser user,Integer status) {
        return insideLetterRepository.countByReceiveUserAndLetterStatus(user,status);
    }

    @Transactional
    @Override
    public int updateInsideLetterStatusByReceiverAndSender(Integer status, EcUser receiver, EcUser sender) {
        String sql = "update InsideLetter i set i.letterStatus=:status where i.sendUser=:sender and i.receiveUser=:receiver and i.letterStatus=0";
        Query query = em.createQuery(sql).setParameter("sender",sender).setParameter("receiver",receiver).setParameter("status",status);
        int count = query.executeUpdate();
        return count;
    }

    @Transactional
    @Override
    public int updateInsideLetterByReceiverOrSender(EcUser receiver, EcUser sender) {
        String sql = "update InsideLetter i set i.flag=0 where ((i.sendUser=:sender1 and i.receiveUser=:receiver1) or (i.sendUser=:receiver2 and i.receiveUser=:sender2))";
        Query query = em.createQuery(sql).setParameter("sender1",sender).setParameter("receiver1",receiver).setParameter("receiver2",receiver).setParameter("sender2",sender);
        int count = query.executeUpdate();
        return count;
    }
}
