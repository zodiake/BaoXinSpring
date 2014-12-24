package com.baosight.scc.ec.service;

import com.baosight.scc.ec.model.EcUser;
import com.baosight.scc.ec.model.SampleOrder;
import com.baosight.scc.ec.type.SampleOrderState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * Created by zodiake on 2014/5/28.
 */
public interface SampleOrderService {
    /*
        @Param id sampleOrder id
        @Param creator of the sampleOrder
     */
    public SampleOrder findById(String id);
    public SampleOrder findByIdAndCreator(String id,EcUser user);
    public SampleOrder save(SampleOrder sampleOrder);
    public SampleOrder update(SampleOrder sampleOrder);

    public Page<SampleOrder> findByCreatedBy(EcUser user,Pageable pageable);
    public Page<SampleOrder> findByStateAndCreatedBy(EcUser user,SampleOrderState state,Pageable pageable);

    public Page<SampleOrder> findByItemCreatedBy(EcUser user,Pageable pageable);
    public Page<SampleOrder> findByStateAndItemCreatedBy(SampleOrderState state,EcUser user, Pageable pageable);

    public Page<SampleOrder> findByProvider(EcUser user,Pageable pageable);
    public Page<SampleOrder> findByStateAndProvider(EcUser user,SampleOrderState state,Pageable pageable);

    public List<SampleOrder> findAll();

    //根据状态、发布人，统计调样单数据@sam
    public Long countByCreatorAndStatus(EcUser user,SampleOrderState state);

    //根据状态、供应商，统计调样单数据@sam
    public Long countByProviderAndStatus(EcUser user,SampleOrderState state);

    Map<String,Integer> sampleCount();
}
