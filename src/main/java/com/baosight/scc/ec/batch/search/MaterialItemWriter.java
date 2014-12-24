package com.baosight.scc.ec.batch.search;

import com.baosight.scc.ec.model.Material;
import com.baosight.scc.ec.model.MaterialIndex;
import com.baosight.scc.ec.repository.MaterialRepository;
import com.baosight.scc.ec.repository.search.FabricSearchRepository;
import com.baosight.scc.ec.repository.search.MaterialSearchRepository;
import com.baosight.scc.ec.service.FabricIndexService;
import com.baosight.scc.ec.service.MaterialIndexService;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Administrator on 2014/10/30.
 */
public class MaterialItemWriter implements ItemWriter<Material>{
    @Autowired
    private MaterialIndexService service;
    @Autowired
    private MaterialSearchRepository repository;

    @Override
    public void write(List<? extends Material> items) throws Exception {
        for(Material f:items) {
            MaterialIndex index=service.transferFromMaterial(f);
            repository.save(index);
        }
    }
}
