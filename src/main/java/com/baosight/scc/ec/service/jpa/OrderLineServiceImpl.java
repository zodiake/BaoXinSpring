package com.baosight.scc.ec.service.jpa;

import com.baosight.scc.ec.model.EcUser;
import com.baosight.scc.ec.model.Item;
import com.baosight.scc.ec.model.OrderItem;
import com.baosight.scc.ec.model.OrderLine;
import com.baosight.scc.ec.repository.OrderLineRepository;
import com.baosight.scc.ec.service.ItemService;
import com.baosight.scc.ec.service.OrderLineService;
import com.baosight.scc.ec.utils.GuidUtil;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Charles on 2014/5/16.
 */
@Service
@Transactional
public class OrderLineServiceImpl implements OrderLineService {
    @Autowired
    private OrderLineRepository or;
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private ItemService itemService;


    @Override
    public OrderLine save(OrderLine orderLine) {
        orderLine.setId(GuidUtil.newGuid());
        return or.save(orderLine);
    }

    @Override
    public Page<OrderLine> findByItem(OrderItem item, Pageable pageable) {
        return or.findOrderByItem(item, pageable);
    }

    @Transactional(readOnly = true)
    public List<OrderLine> findTest() {
        return em.createNamedQuery("OrderLine.findTest", OrderLine.class).getResultList();
    }

    @Transactional(readOnly = true)
    public Page<OrderLine> showFabricOrdersByFid(String id, Pageable pageable) {
        Item item = new Item();
        item.setId(id);
        TypedQuery<OrderLine> query = em.createNamedQuery("OrderLine.findByItem", OrderLine.class);
        query.setParameter("item", item);
        List lines = query.getResultList();

        Long count = or.countByItem(item);
        return new PageImpl<OrderLine>(lines, pageable, count);
    }

    public Long countByItem(Item item) {
        DateTime time = new DateTime();
        DateTime begin = time.minusDays(30);
        Calendar time1=begin.toCalendar(Locale.SIMPLIFIED_CHINESE);
        Calendar time2=time.toCalendar(Locale.SIMPLIFIED_CHINESE);


        String sql = "select count(o) from OrderItem oi join oi.lines o where o.item=:item and oi.createdTime between :time1 and :time2";
        Query query = em.createQuery(sql).setParameter("item",item).setParameter("time1",time1).setParameter("time2",time2);
        Long len = (Long)query.getSingleResult();
       // return or.countByItem(item);
        return len;
    }

    @Override
    public Long findTimeBetweenAndStatusFinish(Item item, String status, Calendar startTime, Calendar endTime) {
        TypedQuery<Long> query = em.createNamedQuery("OrderLine.findTimeBetweenAndStatusFinish", Long.class);
        query.setParameter("item", item).setParameter("status", status).setParameter("startTime", startTime).setParameter("endTime", endTime);
        Long count = query.getSingleResult();
        return count;
    }

    @Override
    public Long findTimeBetweenAndStatusNFinish(Item item, String status, Calendar startTime, Calendar endTime) {
        TypedQuery<Long> query = em.createNamedQuery("OrderLine.findTimeBetweenAndStatusNFinish", Long.class);
        query.setParameter("item", item).setParameter("status", status).setParameter("startTime", startTime).setParameter("endTime", endTime);
        Long count = query.getSingleResult();
        return count;
    }

    @Override
    public boolean countUnfinishedDeal(Item item) {
        TypedQuery<Long> query = em.createNamedQuery("OrderLine.countUnfinishedDeal", Long.class);
        query.setParameter("item",item);
        Long count=query.getSingleResult();
        return count>0;
    }

    @Override
    public OrderLine findOrderLineByItem(Item item){
        String sql = "select o from OrderLine o where o.item=:item";
        Query query=em.createQuery(sql).setParameter("item",item);
        List<OrderLine> list = new ArrayList<OrderLine>();
        list = query.getResultList();
        OrderLine orderLine = new OrderLine();
        for(int i=0,size=list.size();i<size;i++){
            if (i==0){
                orderLine=list.get(i);
            }
        }
        return orderLine;
    }

    @Override
    public List<OrderLine> findByOrderItem(OrderItem orderItem) {
        return or.findOrderByItem(orderItem);
    }
}
