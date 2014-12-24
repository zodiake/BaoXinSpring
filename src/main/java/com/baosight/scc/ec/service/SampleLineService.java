package com.baosight.scc.ec.service;

import com.baosight.scc.ec.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
/**
 * Created by Charles on 2014/7/12.
 */
public interface SampleLineService {
    /**
     * 调样册内商品保存
     *
     * @param sampleLine
     * @return
     */
    SampleLine save(SampleLine sampleLine);

    /**
     * 调样册详情商品分页列表
     *
     * @param sampleOrder
     * @param pageable
     * @return
     */
    Page<SampleLine> findByItem(SampleOrder sampleOrder, Pageable pageable);

}
