package com.baosight.scc.ec.service.jpa;

import com.baosight.scc.ec.model.*;
import com.baosight.scc.ec.repository.SellerCommentRepository;
import com.baosight.scc.ec.service.*;
import com.baosight.scc.ec.type.CommentType;
import com.baosight.scc.ec.type.OrderState;
import com.baosight.scc.ec.utils.GuidUtil;
import com.baosight.scc.ec.web.CommentJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by sam on 2014/5/30.
 */
@Service
public class SellerCommentServiceImpl implements SellerCommentService {

    @Autowired
    private SellerCommentRepository scr;
    @Autowired
    private OrderItemService orderItemService;
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private ItemService itemService;
    @Autowired
    private FabricService fabricService;
    @Autowired
    private MaterialService materialService;
    @Autowired
    private EcUserService ecUserService;

    @Transactional
    @Override
    public SellerComment save(SellerComment sellerComment) {
     //   em.persist(sellerComment);
        //modifyed by sam 2014-7-31
        //   this.scr.save(sellerComment);
        List<OrderLine> list = sellerComment.getOrderItem().getLines();
        for(int i=0,len=list.size();i<len;i++){
            if (i==0){
                sellerComment.setDefaultShow(1);
            }else{
                sellerComment.setDefaultShow(0);
            }
            sellerComment.setId(GuidUtil.newGuid());
            sellerComment.setItem(list.get(i).getItem());
            scr.save(sellerComment);
        }
        //ended by sma
        OrderItem orderItem = orderItemService.findById(sellerComment.getOrderItem().getId());
        String status = orderItem.getStatus();
        if (!status.equals(OrderState.BUYER_APPRAISE.toString())){
            orderItem.setStatus(OrderState.SELLER_APPRAISE.toString()); //卖家已评价
            System.out.println("======================卖家在评价，状态更改为卖家已评价");
            //修改产品成交笔数
            for (OrderLine ol:sellerComment.getOrderItem().getLines()){
                String proId = ol.getItem().getId();
                int flag = itemService.checkItemType(proId);
                if(flag == 0){
                    Fabric fabric = this.fabricService.findById(proId);
                    fabric.setBidCount(fabric.getBidCount()+1);
                }else {
                    Material material = this.materialService.findOne(proId);
                    material.setBidCount(material.getBidCount() + 1);
                }
            }
        }else {
            orderItem.setStatus(OrderState.FINISH.toString()); //双方已互评
            System.out.println("======================修改交易状态，双方已互评");
        }
        return sellerComment;
    }

    @Override
    public Page<SellerComment> findByUser(EcUser user, Pageable pageable) {
        return this.scr.findByUser(user,pageable);
    }

    @Override
    public Page<SellerComment> findByUserAndType(EcUser user, CommentType type, Pageable pageable) {
        return null;
    }

    @Override
    public Page<SellerComment> findByUserAndContent(EcUser user, String content, Pageable pageable) {
        return null;
    }

    @Override
    public Page<SellerComment> findByUserAndTypeAndContent(EcUser user, CommentType type, String content, Pageable pageable) {
        return null;
    }

    @Override
    public Page<SellerComment> findSellerSendComments(EcUser user, Integer type, String content, Pageable pageable) {
        CommentType commentType = null;
        Page<SellerComment> page = null;
        if(type != null && type != 0){
            commentType = CommentType.values()[type];
        }
        if(type != null && type != 0 && ("".equals(content) || null == content))
            page = this.findByUserAndType(user,commentType,pageable);
        else if(type == null && null != content && type != 0)
            page = this.findByUserAndContent(user,content,pageable);
        else if (type != null && content != null && type != 0)
            page = this.findByUserAndTypeAndContent(user,commentType,content,pageable);
        else if (type == null && content == null && type != 0)
            page = this.findByUser(user,pageable);
        return page;
    }

    @Override
    public Page<SellerComment> findSellerCommentsFromSeller(EcUser user, Integer type, String content, Pageable pageable) {
        StringBuffer querySql = new StringBuffer("select sc from OrderItem i join i.sellerCommentList sc where i.buyer=:user and sc.defaultShow=1");
        StringBuffer countSql = new StringBuffer("select count(sc) from OrderItem i join i.sellerCommentList sc where i.buyer=:user and sc.defaultShow=1");
        if (type != null && type != 0){
            querySql.append(" and sc.type=:type");
            countSql.append(" and sc.type=:type");
        }
        querySql.append("  order by sc.createdTime desc");
        Query query = em.createQuery(querySql.toString()).setParameter("user",user);
        Query countQuery = em.createQuery(countSql.toString()).setParameter("user",user);
        CommentType commentType = null;
        if (type != null && type != 0){
            commentType = CommentType.values()[type];
            query.setParameter("type",commentType);
            countQuery.setParameter("type",commentType);
        }
        List<SellerComment> list = query.setFirstResult(pageable.getOffset()).setMaxResults(pageable.getPageSize()).getResultList();
        Long total = (Long)countQuery.getSingleResult();

        Page<SellerComment> page = new PageImpl<SellerComment>(list,pageable,total);



        return page;
    }

    @Override
    public List<SellerComment> findByOrderItem(OrderItem orderItem) {
        return scr.findByOrderItem(orderItem);
    }

    /*
    卖家发出的评论
     */
    @Override
    public Page<CommentJson> findCommentsFromSellerSend(EcUser user,Integer type,String content,Pageable pageable){
        /*StringBuffer querySql = new StringBuffer("select sc from Item i join i.sellerComments sc where i.createdBy=:user ");
        StringBuffer countSql = new StringBuffer("select count(sc) from Item i join i.sellerComments sc where i.createdBy=:user ");
        if (type != null && type != 0){
            querySql.append(" and sc.type=:type");
            countSql.append(" and sc.type=:type");
        }
        *//*if(content != null){
            querySql.append(" and sc.content is not null");
            countSql.append(" and sc.content is not null");
        }else{
            querySql.append(" and sc.content is null");
            countSql.append(" and sc.content is null");
        }*//*
        if ("4".equals(content)){
            querySql.append(" and sc.content is not null");
            countSql.append(" and sc.content is not null");
        }else if ("5".equals(content)){
            querySql.append(" and sc.content is null");
            countSql.append(" and sc.content is null");
        }
        querySql.append("  order by sc.createdTime desc");
        Query query = em.createQuery(querySql.toString()).setParameter("user",user);
        Query countQuery = em.createQuery(countSql.toString()).setParameter("user",user);
        CommentType commentType = null;
        if (type != null && type != 0){
            commentType = CommentType.values()[type];
            query.setParameter("type",commentType);
            countQuery.setParameter("type",commentType);
        }
        List<SellerComment> list = query.setFirstResult(pageable.getOffset()).setMaxResults(pageable.getPageSize()).getResultList();
        Long total = (Long)countQuery.getSingleResult();

        Page<SellerComment> page = new PageImpl<SellerComment>(list,pageable,total);*/

        String sql = "select sc.id,sc.content,i.name,sc.createdTime,o.buyer_id as user_id,sc.hasName,sc.type from t_ec_sellerComment sc join t_ec_item i on sc.item_id=i.id join t_ec_orderItem o on sc.order_id=o.id where sc.user_id='"+user.getId()+"' and sc.defaultShow=1 ";
        String cql = "select count(sc.id) total from t_ec_sellerComment sc join t_ec_item i on sc.item_id=i.id join t_ec_orderItem o on o.id=sc.order_id where sc.user_id='"+user.getId()+"' and sc.defaultShow=1 ";
        if (type != null){
            sql = sql + " and sc.type='"+CommentType.values()[type]+"' ";
            cql = cql + " and sc.type='"+CommentType.values()[type]+"' ";
        }
        sql = sql + " union ";
        cql = cql + " union ";
        sql = sql + " select c.id,c.content,i.name,c.createdTime,o.seller_id as user_id,c.hasName,c.type from t_ec_comment c join t_ec_item i on c.item_id=i.id join t_ec_orderItem o on o.id=c.order_id where c.user_id='"+user.getId()+"' and c.defaultShow=1 ";
        cql = cql + " select count(c.id) from t_ec_comment c join t_ec_item i on c.item_id=i.id join t_ec_orderItem o on o.id=c.order_id where c.user_id='"+user.getId()+"' and c.defaultShow=1 ";
        if (type != null){
            sql = sql + " and c.type='"+CommentType.values()[type]+"' ";
            cql = cql + " and c.type='"+CommentType.values()[type]+"' ";
        }
        String nSql = "select * from ("+sql+") a order by createdTime desc";
        String nCql = "select sum(total) from ("+cql+") b";
        List list = new ArrayList();
        list = em.createNativeQuery(nSql).setFirstResult(pageable.getOffset()).setMaxResults(pageable.getPageSize()).getResultList();
        int total =(Integer)em.createNativeQuery(nCql).getSingleResult();
        Long len =new Long(total);
        List<CommentJson> result = new ArrayList<CommentJson>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(Iterator iterator=list.iterator();iterator.hasNext();){
            Calendar calendar = Calendar.getInstance();
            Object[] values=(Object[])iterator.next();
            CommentJson cj = new CommentJson();
            cj.setId((String)values[0]);
            cj.setContent((String)values[1]);
            cj.setName((String)values[2]);
        //    EcUser user1 = ecUserService.findById(values[4].toString());
            EcUser user1 = new EcUser();
            user1.setId(values[4].toString());
            user1.setName(values[4].toString());
            cj.setUser(user1);
        //    cj.setCreatedTime((Timestamp) values[3]);
            String createdTime = sdf.format((Timestamp)values[3]);
            try {
                Date d = sdf.parse(createdTime);
                calendar.setTime(d);
                cj.setCreatedTime(calendar);
            } catch (ParseException e) {
                e.printStackTrace();
                cj.setCreatedTime(Calendar.getInstance());
            }
            cj.setHasName(Integer.parseInt(values[5].toString()));
            cj.setType(values[6].toString());
            result.add(cj);
        }
        PageImpl page = null;
        page = new PageImpl(result,pageable,len);
        return page;
    }
}
