package com.baosight.scc.ec.repository;

import com.baosight.scc.ec.model.EcUser;
import com.baosight.scc.ec.model.SampleOrder;
import com.baosight.scc.ec.type.SampleOrderState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;


/**
 * Created by zodiake on 2014/5/29.
 */
public interface SampleOrderRepository extends PagingAndSortingRepository<SampleOrder,String>{
    public SampleOrder findByIdAndCreator(String id,EcUser user);
    public Page<SampleOrder> findByCreatorAndState(EcUser user,SampleOrderState state,Pageable pageable);

    public Page<SampleOrder> findByCreator(EcUser user,Pageable pageable);
    public Page<SampleOrder> findByStateAndCreator(SampleOrderState state,EcUser user,Pageable pageable);

    public Page<SampleOrder> findByProvider(EcUser user,Pageable pageable);
    public Page<SampleOrder> findByStateAndProvider(SampleOrderState state,EcUser user,Pageable pageable);

    //根据发布人、状态统计调样单数据
    Long countByCreatorAndState(EcUser user,SampleOrderState state);

    //根据供应商、状态统计调样单数据
    Long countByProviderAndState(EcUser user,SampleOrderState state);
}
