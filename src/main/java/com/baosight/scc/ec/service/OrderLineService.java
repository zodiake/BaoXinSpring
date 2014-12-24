package com.baosight.scc.ec.service;

import com.baosight.scc.ec.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Charles on 2014/5/16.
 */
public interface OrderLineService {
    /**
     * 订单内商品保存
     *
     * @param orderLine
     * @return
     */
    OrderLine save(OrderLine orderLine);

    /**
     * 订单详情商品分页列表
     *
     * @param item
     * @param pageable
     * @return
     */
    Page<OrderLine> findByItem(OrderItem item, Pageable pageable);

    List<OrderLine> findTest();

    public Page<OrderLine> showFabricOrdersByFid(String id, Pageable pageable);

    public Long countByItem(Item item);

    public Long findTimeBetweenAndStatusFinish(Item item,String status,Calendar startTime,Calendar endTime);

    public Long findTimeBetweenAndStatusNFinish(Item item,String status,Calendar startTime,Calendar endTime);

    public boolean countUnfinishedDeal(Item item);

    public OrderLine findOrderLineByItem(Item item);

    List<OrderLine> findByOrderItem(OrderItem orderItem);
}
