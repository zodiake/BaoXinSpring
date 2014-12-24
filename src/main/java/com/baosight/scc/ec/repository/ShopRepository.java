package com.baosight.scc.ec.repository;

import com.baosight.scc.ec.model.EcUser;
import com.baosight.scc.ec.model.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by zodiake on 2014/6/3.
 */
public interface ShopRepository extends PagingAndSortingRepository<Shop,String>{
 //   Page<Shop> findByUser(EcUser user,Pageable pageable);
    List<Shop> findByUser(EcUser user);
}
