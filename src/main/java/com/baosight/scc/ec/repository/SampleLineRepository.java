package com.baosight.scc.ec.repository;

import com.baosight.scc.ec.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Charles on 2014/5/16.
 */
public interface SampleLineRepository extends PagingAndSortingRepository<SampleLine,String> {
  //根据产品id，查看交易记录
    Page<SampleLine> findOrderByItem(SampleOrder sampleOrder,Pageable pageable);
}
