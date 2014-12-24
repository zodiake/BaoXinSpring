package com.baosight.scc.ec.service;

import com.baosight.scc.ec.model.Information;
import com.baosight.scc.ec.model.InformationCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by Charles on 2014/6/5.
 */

public interface InformationService {
    /**
     * 保存资讯
     * @param information
     * @return
     */
    Information save(Information information);
    /**
     * 更新资讯
     * @param information
     * @return
     */
    Information update(Information information,String id);
    /**
     * 查找一条资讯
     * @param id
     * @return
     */
    Information findById(String id);

    /**
     * 查询有效或无效的资讯
     * @param isValid
     * @param pageable
     * @return
     */
    Page<Information> findByIsValid(int isValid, Pageable pageable);

    /**
     * 删除资讯
     * @param id
     * @return
     */
    Information delete(String id);

    /**
     * 查询有效或无效的资讯
     * @param isValid
     * @param pageable
     * @return
     */
    Page<Information> findByCategoryAndIsValid(InformationCategory informationCategory,int isValid, Pageable pageable);


    /**
     * 首页查询资讯和公告
     * @param informationCategory
     * @param isValid
     * @return
     */
    List<Information> findTop5List(InformationCategory informationCategory,int isValid,Pageable pageable);

}
