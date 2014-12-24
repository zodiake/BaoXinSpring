package com.baosight.scc.ec.service.jpa;

import com.baosight.scc.ec.model.*;
import com.baosight.scc.ec.repository.CommentRepository;
import com.baosight.scc.ec.service.*;
import com.baosight.scc.ec.type.CommentType;
import com.baosight.scc.ec.type.OrderState;
import com.baosight.scc.ec.utils.GuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.Order;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository cr;
    @Autowired
    private ItemService itemService;
    @Autowired
    private FabricService fabricService;
    @Autowired
    private MaterialService materialService;
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private OrderItemService orderItemService;

    @Transactional
    @Override
    public Comment save(Comment comment) {
        List<OrderLine> list1=comment.getOrderItem().getLines();
        for(int i=0,len=list1.size();i<len;i++){
            if(i==0){
                comment.setDefaultShow(1);
            }else{
                comment.setDefaultShow(0);
            }
            comment.setId(GuidUtil.newGuid());
            comment.setItem(list1.get(i).getItem());
            cr.save(comment);
        }
       // cr.save(comments);
        //ended by sam
        OrderItem orderItem = orderItemService.findById(comment.getOrderItem().getId());
        String status = orderItem.getStatus();
        if (!status.equals(OrderState.SELLER_APPRAISE.toString())) {
            orderItem.setStatus(OrderState.BUYER_APPRAISE.toString()); //买家已评
            //修改成交笔数
            List<OrderLine> list = comment.getOrderItem().getLines();
            for (OrderLine ol:list){
                String proId = ol.getItem().getId();
                int flag = itemService.checkItemType(proId);
                if (flag == 0) {
                    Fabric fabric = this.fabricService.findById(proId);
                    fabric.setBidCount(fabric.getBidCount() + 1);
                } else {
                    Material material = this.materialService.findOne(proId);
                    material.setBidCount(material.getBidCount() + 1);
                }
            }
        } else {
            orderItem.setStatus(OrderState.FINISH.toString()); //双方已评价
        }
        return comment;
    }

    @Override
    public Page<Comment> findByUser(EcUser user, Pageable pageable) {
        return this.cr.findByUser(user, pageable);
    }


    @Override
    public Page<Comment> findByItemAndType(Item item, Integer type, Pageable pageable) {
        CommentType commentType = CommentType.values()[type];
        Page<Comment> result;
        if (commentType == CommentType.全部)
            result = cr.findByItem(item, pageable);
        else
            result = cr.findByItemAndType(item, commentType, pageable);
        return result;
    }

    @Override
    public Long countByItem(Item item) {
        return cr.countByItem(item);
    }

    @Override
    public Page<DimensionRate> findCommentsDimensionRates(Pageable pageable) {
        StringBuffer querySql = new StringBuffer("select avg(c.attitude) as attitude,avg(c.deliverySpeed) as deliverySpeed," +
                "avg(c.satisfied) as satisfied,i.createdBy as seller from Item i join i.comments c group by i.createdBy");
        Query query = em.createQuery(querySql.toString());
        List result = query.getResultList();
        DimensionRate dimensionRate = new DimensionRate();
        return null;
    }

    @Override
    public Long countByItemAndStatus(Item item, Integer type) {
        CommentType commentType = CommentType.values()[type];
        return cr.countByItemAndType(item,commentType);
    }

    @Override
    public Page<Comment> findCommentsFromBuyer(EcUser user, Integer type, String content, Pageable pageable) {
        StringBuffer querySql = new StringBuffer("select c from Item m join m.comments c where m.createdBy=:user and c.defaultShow=1");

        if (type != null && type != 0){
            querySql.append(" and c.type=:type ");
        }
        querySql.append(" order by c.createdTime desc");

        StringBuffer countSql = new StringBuffer("select count(a) from Item i join i.comments a where i.createdBy=:user and a.defaultShow=1");
        if (type != null && type != 0){
            countSql.append(" and a.type=:type ");
        }

        CommentType commentType = null;
        Query query = em.createQuery(querySql.toString()).setParameter("user",user);
        Query countQuery = em.createQuery(countSql.toString()).setParameter("user",user);

        if (type != null && type != 0){
            commentType = CommentType.values()[type];
            query.setParameter("type",commentType);
            countQuery.setParameter("type",commentType);
        }

        query.setFirstResult(pageable.getOffset()).setMaxResults(pageable.getPageSize());
        List<Comment> comments = query.getResultList();
        Long total = (Long)countQuery.getSingleResult();

        Page<Comment> page = new PageImpl<Comment>(comments,pageable,total);

        return page;
    }

    @Override
    public List<Comment> findByOrderItem(OrderItem orderItem){
        return this.cr.findByOrderItem(orderItem);
    }

    //@sam  2014-7-31
    @Override
    public Page<Comment> findCommentByItemAndType(Item item, Integer type, Pageable pageable) {
        String sql = "select c.id,c.user,c.content,c.createdTime,c.type,c.hasName,ol.item from OrderItem o join OrderLine ol join Comment c where ol.item=:item and c.type=:type";
        String countSql = "select count(c) from OrderItem o join OrderLine ol join Comment c where ol.item=:item and c.type=:type";
        Query query = em.createQuery(sql).setParameter("item",item).setParameter("type",type);
        Query countQuery = em.createQuery(countSql).setParameter("item",item).setParameter("type",type);
        List list = query.setFirstResult(pageable.getOffset()).setMaxResults(pageable.getPageSize()).getResultList();
        List<Comment> result=new ArrayList<Comment>();
        for(Iterator iterator=list.iterator();iterator.hasNext();){
            Object[] values = (Object[])iterator.next();
            Comment comment = new Comment();
            comment.setId((String)values[0]);
            comment.setUser((EcUser)values[1]);
            comment.setContent((String)values[2]);
            comment.setCreatedTime((Calendar)values[3]);
            comment.setType((CommentType)values[4]);
            comment.setHasName((Integer)values[5]);
            comment.setItem((Item)values[6]);
            result.add(comment);
        }
        Long len = (Long)countQuery.getSingleResult();
        Page<Comment> page=null;
        page = new PageImpl<Comment>(result,pageable,len);
        return page;
    }
}
