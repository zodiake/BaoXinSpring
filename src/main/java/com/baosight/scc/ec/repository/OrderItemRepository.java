package com.baosight.scc.ec.repository;

import com.baosight.scc.ec.model.EcUser;
import com.baosight.scc.ec.model.OrderItem;
import com.baosight.scc.ec.type.OrderState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Charles on 2014/5/16.
 */
public interface OrderItemRepository extends PagingAndSortingRepository<OrderItem, String> {
    Page<OrderItem> findByBuyer(EcUser buyer, Pageable pageable);

    Page<OrderItem> findBySeller(EcUser seller, Pageable pageable);

    Page<OrderItem> findByBuyerAndStatusNotLike(EcUser buyer, String status, Pageable pageable);

    Long countByReceiveTimeBetween(Calendar calendarBegin,Calendar calendarEnd);

    Long countByReceiveTimeBetweenAndStatus(Calendar calendarBegin,Calendar calendarEnd,String status);

    Page<OrderItem> findByBuyerAndStatus(EcUser buyer,String status,Pageable pageable);

    Page<OrderItem> findBySellerAndStatus(EcUser seller,String status,Pageable pageable);

    //根据状态统计买家订单数据
    Long countByStatusAndBuyer(String status,EcUser buyer);
    //根据状态统计卖家订单数据
    Long countByStatusAndSeller(String status,EcUser seller);

    List<OrderItem> findById(String id);
}
