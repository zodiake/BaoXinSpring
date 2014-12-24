package com.baosight.scc.ec.batch.search;

import com.baosight.scc.ec.model.Fabric;
import com.baosight.scc.ec.model.FabricIndex;
import com.baosight.scc.ec.repository.search.FabricSearchRepository;
import com.baosight.scc.ec.service.FabricIndexService;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Administrator on 2014/10/23.
 */
public class FabricItemWriter implements ItemWriter<FabricIndex>{
    @Autowired
    private FabricSearchRepository repository;

    @Override
    public void write(List<? extends FabricIndex> items) throws Exception {
        for(FabricIndex f:items) {
            repository.save(items);
        }
    }
}
