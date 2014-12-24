package com.baosight.scc.ec.repository;

import com.baosight.scc.ec.model.IPConfig;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by sam on 2014/9/4.
 */
public interface IPConfigRepository extends PagingAndSortingRepository<IPConfig,Integer> {

    IPConfig findByFlag(int flag);

}
