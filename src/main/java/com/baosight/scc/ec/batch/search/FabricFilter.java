package com.baosight.scc.ec.batch.search;

import com.baosight.scc.ec.model.Fabric;
import com.baosight.scc.ec.model.FabricIndex;
import com.baosight.scc.ec.service.FabricIndexService;
import com.baosight.scc.ec.type.ItemState;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2014/10/23.
 */
public class FabricFilter implements ItemProcessor<Fabric,FabricIndex> {
    @Autowired
    private FabricIndexService fabricIndexService;

    @Override
    public FabricIndex process(Fabric item) throws Exception {
        if(!item.getState().equals(ItemState.出售中))
            return null;
        return fabricIndexService.transferFromFabric(item);
    }
}
