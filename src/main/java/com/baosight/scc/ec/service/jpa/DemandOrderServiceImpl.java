package com.baosight.scc.ec.service.jpa;

import com.baosight.scc.ec.model.CultureImage;
import com.baosight.scc.ec.model.DemandOrder;
import com.baosight.scc.ec.model.EcUser;
import com.baosight.scc.ec.repository.DemandOrderRepository;
import com.baosight.scc.ec.service.DemandOrderService;
import com.baosight.scc.ec.type.DemandOrderState;
import com.baosight.scc.ec.utils.GuidUtil;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by zodiake on 2014/5/22.
 */
@Service
@Transactional
public class DemandOrderServiceImpl implements DemandOrderService {
    @Autowired
    private DemandOrderRepository repository;
    @PersistenceContext
    private EntityManager em;
    private Logger logger = LoggerFactory.getLogger(DemandOrderServiceImpl.class);

    @Override
    @Transactional(readOnly = true)
    public DemandOrder findOne(String id) {
        return repository.findOne(id);
    }

    @Override
    public DemandOrder findByIdAndUser(String id, EcUser user) {
        return repository.findByIdAndCreatedBy(id, user);
    }

    @Override
    public DemandOrder save(DemandOrder demandOrder) {
        demandOrder.setId(GuidUtil.newGuid());
        demandOrder=bindData(demandOrder);
        demandOrder.setCreatedTime(Calendar.getInstance());
        return this.repository.save(demandOrder);
    }

    public DemandOrder bindData(DemandOrder demandOrder){
        List<CultureImage> images = new ArrayList<CultureImage>();
        if(demandOrder.getTempImages().length>0){
            demandOrder.setCoverImage(demandOrder.getTempImages()[0]);
            for (int i = 0; i < demandOrder.getTempImages().length; i++) {
                String location = demandOrder.getTempImages()[i];
                //and temp image`s src is not empty string
                if (!StringUtils.isEmpty(location)) {
                    CultureImage image = new CultureImage(demandOrder.getTempImages()[i], i);
                    image.setCreatedTime(Calendar.getInstance());
                    image.setUpdatedTime(Calendar.getInstance());
                    //and source item not contain this image
                    images.add(image);
                }
            }
        }
        demandOrder.setImages(images);
        demandOrder.setUpdatedTime(Calendar.getInstance());
        return demandOrder;
    }

    @Override
    public DemandOrder update(DemandOrder demandOrder) {
        demandOrder.setCreatedTime(Calendar.getInstance());
        demandOrder.setUpdatedTime(Calendar.getInstance());
        if(DemandOrderState.发布中.equals(demandOrder.getState())){
            demandOrder=bindData(demandOrder);
        }
        return em.merge(demandOrder);
    }

    @Override
    public Page<DemandOrder> findByUserAndState(EcUser u, DemandOrderState state, Pageable pageable) {
        return repository.findByCreatedByAndState(u, state, pageable);
    }

    @Override
    public Page<DemandOrder> findByUser(EcUser u, Pageable pageable) {
        logger.debug(repository.findByCreatedBy(u, pageable).getContent().size() + "");
        logger.debug(repository.findByCreatedBy(u).size() + "--no page");
        return repository.findByCreatedBy(u, pageable);
    }

    @Override
    public List<DemandOrder> findAll() {
        return Lists.newArrayList(repository.findAll());
    }

    @Override
    @Cacheable(value = "homeDemandCacheLoadService")
    public List<DemandOrder> findTopN(int len) {
        String querySql = "select id,title,content,validDateFrom,validDateTo,demandType from T_ec_DemandOrder " +
                " where current_timestamp <= validDateTo and state='发布中' order by validDateFrom desc fetch first "+len+"  rows only";
        Query query = em.createNativeQuery(querySql);
        List result = new ArrayList();
        result = query.getResultList();
        List<DemandOrder> list = new ArrayList<DemandOrder>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Iterator iterator = result.iterator();iterator.hasNext();){
            Calendar calendar1 = Calendar.getInstance();
            Calendar calendar2 = Calendar.getInstance();
            Object[] values = (Object[])iterator.next();
            DemandOrder demandOrder = new DemandOrder();
            demandOrder.setId((String)values[0]);
            demandOrder.setTitle((String) values[1]);
            demandOrder.setContent((String) values[2]);
            String validDateFrom = sdf.format((Timestamp)values[3]);
            String validDateTo = sdf.format((Timestamp)values[4]);
            demandOrder.setDemandType((String)values[5]);
            try {
                Date d = sdf.parse(validDateFrom);
                calendar1.setTime(d);
                demandOrder.setValidDateFrom(calendar1);
                d=sdf.parse(validDateTo);
                calendar2.setTime(d);
                demandOrder.setValidDateTo(calendar2);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            list.add(demandOrder);
        }
        return list;
    }

    @Override
    public List<DemandOrder> findDemandOrdersByCreatedBy(EcUser user, int len) {
        DemandOrderState state = DemandOrderState.发布中;
        String sql = "select d from DemandOrder d where d.createdBy=:user and d.state=:state order by d.createdTime desc";
        Query query = em.createQuery(sql).setParameter("user",user).setParameter("state",state);
        List<DemandOrder> result = new ArrayList<DemandOrder>();
        result = query.setMaxResults(len).getResultList();
        return result;
    }

    @Override
    public Page<DemandOrder> findDemandOrdersByParams(String type, String content, Pageable pageable) {
        DemandOrderState state = DemandOrderState.发布中;
        String sql = "select o from DemandOrder o where 1=1 and o.state=:state ";
        String countSql = "select count(o) from DemandOrder o where 1=1 and o.state=:state ";
        if (type != null){
            sql = sql + " and o.demandType=:type ";
            countSql = countSql + " and o.demandType=:type ";
        }
        if (content != null){
            sql = sql + " and o.content like :content ";
            countSql = countSql + " and o.content like :content ";
        }
        sql = sql + " and o.validDateTo >= current_timestamp order by o.validDateFrom desc ";
        countSql = countSql + " and o.validDateTo >= current_timestamp ";
        Query query = em.createQuery(sql).setParameter("state",state);
        Query countQuery = em.createQuery(countSql).setParameter("state",state);
        if (type != null){
            query.setParameter("type",type);
            countQuery.setParameter("type",type);
        }
        if(content != null){
            query.setParameter("content","%"+content.trim()+"%");
            countQuery.setParameter("content","%"+content.trim()+"%");
        }
        List<DemandOrder> list = query.setFirstResult(pageable.getOffset()).setMaxResults(pageable.getPageSize()).getResultList();
        Long len = (Long)countQuery.getSingleResult();
        Page<DemandOrder> page = new PageImpl<DemandOrder>(list,pageable,len);
        return page;
    }

    @Override
    public Long demandCount() {
        return repository.count();
    }

    @Override
    public Map<String, Integer> demandMap() {
        Map<String,Integer> demandMap = new HashMap<String, Integer>();
        String demandSql = "select sum(case when t1.state='发布中' and current timestamp between t1.VALIDDATEFROM and t1.VALIDDATETO then 1 else 0 end) as v1,sum(case when t1.state='发布中' and (current timestamp < t1.VALIDDATEFROM or current timestamp> t1.VALIDDATETO) then 1 else 0 end) as v2,sum(case when t1.state='下架' then 1 else 0 end) as v3 from t_ec_demandorder t1 where t1.USER_ID not in('admin','admintest','lichaoyi')";
        Query demandQuery = em.createNativeQuery(demandSql);
        List demandResult =  demandQuery.getResultList();
        for (Iterator iterator = demandResult.iterator(); iterator.hasNext(); ) {
            Object[] values = (Object[]) iterator.next();
            demandMap.put("valid",(Integer) values[0]);
            demandMap.put("invalid",(Integer) values[1]);
            demandMap.put("offSale",(Integer) values[2]);
        }
        return demandMap;
    }
}
