package com.baosight.scc.ec.repository;

import com.baosight.scc.ec.model.OrderAddress;
import com.baosight.scc.ec.model.SampleOrderCopyAddress;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Charles on 2014/7/12.
 */
public interface SampleOrderAddressRepository extends PagingAndSortingRepository<SampleOrderCopyAddress,String>{
}
