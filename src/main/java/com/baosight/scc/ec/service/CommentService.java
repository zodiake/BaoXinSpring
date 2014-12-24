package com.baosight.scc.ec.service;

import com.baosight.scc.ec.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 
 * @author sam
 *
 */
public interface CommentService {

	/**
	 * 添加评论信息
	 * @param comment
	 * @return
	 * @author sam
	 */
	public Comment save(Comment comment);

    /**
     * 根据用户信息，查询该用户下的所有评论内容
     * @param user 用户信息
     * @param pageable
     * @return
     * @author sam
     */
    public Page<Comment> findByUser(EcUser user,Pageable pageable);


    /**
     * 来自买家的评论
     * @param
     * @param type 评价类型
     * @param pageable
     * @return
     */
    public Page<Comment> findByItemAndType(Item item,Integer type,Pageable pageable);

    /**
     * 卖家店铺商品维度动态评分
     * @param pageable 卖家
     * @return
     */
    public Page<DimensionRate> findCommentsDimensionRates(Pageable pageable);


    public Long countByItem(Item item);

    public Page<Comment> findCommentsFromBuyer(EcUser user, Integer type, String content, Pageable pageable);

    public Long countByItemAndStatus(Item item,Integer type);

    public List<Comment> findByOrderItem(OrderItem orderItem);

    //@sam 商品评价列表
    Page<Comment> findCommentByItemAndType(Item item,Integer type,Pageable pageable);
}
