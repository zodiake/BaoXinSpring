package com.baosight.scc.ec.repository;

import com.baosight.scc.ec.model.Advertisement;
import com.baosight.scc.ec.model.AdvertisementPosition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by Charles on 2014/5/30.
 */
public interface AdvertisementRepository extends PagingAndSortingRepository<Advertisement,String>{
    Page<Advertisement> findByIsValid(int isValid, Pageable pageable);
    List<Advertisement> findByAdvertisementPositionAndIsValid(AdvertisementPosition advertisementPosition,int isValid,Pageable pageable);
}
