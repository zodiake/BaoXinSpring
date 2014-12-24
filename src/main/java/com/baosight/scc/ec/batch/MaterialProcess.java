package com.baosight.scc.ec.batch;

import com.baosight.scc.ec.batch.repository.MaterialDTOService;
import com.baosight.scc.ec.model.Material;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2014/9/26.
 */
public class MaterialProcess implements ItemProcessor<BatchMaterial,Material> {
    @Autowired
    private MaterialDTOService service;

    @Override
    public Material process(BatchMaterial item) throws Exception {
        return service.transferFromBatch(item);
    }
}
