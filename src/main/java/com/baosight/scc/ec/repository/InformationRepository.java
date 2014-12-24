package com.baosight.scc.ec.repository;

import com.baosight.scc.ec.model.Information;
import com.baosight.scc.ec.model.InformationCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by Charles on 2014/5/30.
 */
public interface InformationRepository extends PagingAndSortingRepository<Information,String>{
    Page<Information> findByIsValid(int isValid, Pageable pageable);


    Page<Information> findByInformationCategoryAndIsValid(InformationCategory informationCategory,int isValid,Pageable pageable);

    List<Information> findIdAndTitleByInformationCategoryAndIsValid(InformationCategory informationCategory,int isValid,Pageable pageable);

}
