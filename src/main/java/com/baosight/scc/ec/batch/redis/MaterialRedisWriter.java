package com.baosight.scc.ec.batch.redis;

import com.baosight.scc.ec.model.Fabric;
import com.baosight.scc.ec.model.Material;
import com.baosight.scc.ec.service.RedisItemService;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Administrator on 2014/10/30.
 */
public class MaterialRedisWriter implements ItemWriter<Material>{
    @Autowired
    private RedisItemService service;

    @Override
    public void write(List<? extends Material> items) throws Exception {
        for(Material f:items){
            f.setRange(f.getRanges());
            f.setUrl("/material/"+f.getId());
            service.save(f);
        }

    }
}
