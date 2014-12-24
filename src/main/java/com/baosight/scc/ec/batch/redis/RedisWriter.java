package com.baosight.scc.ec.batch.redis;

import com.baosight.scc.ec.model.Fabric;
import com.baosight.scc.ec.model.Item;
import com.baosight.scc.ec.service.RedisItemService;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Administrator on 2014/10/30.
 */
public class RedisWriter implements ItemWriter<Fabric>{
    @Autowired
    private RedisItemService service;

    @Override
    public void write(List<? extends Fabric> items) throws Exception {
        for(Fabric f:items){
            f.setRange(f.getRanges());
            f.setUrl("/fabric/"+f.getId());
            service.save(f);
        }
    }
}
