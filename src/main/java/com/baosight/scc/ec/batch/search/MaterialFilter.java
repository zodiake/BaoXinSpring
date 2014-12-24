package com.baosight.scc.ec.batch.search;

import com.baosight.scc.ec.model.Fabric;
import com.baosight.scc.ec.model.Material;
import com.baosight.scc.ec.type.ItemState;
import org.springframework.batch.item.ItemProcessor;

/**
 * Created by Administrator on 2014/10/23.
 */
public class MaterialFilter implements ItemProcessor<Material,Material> {
    @Override
    public Material process(Material item) throws Exception {
        if(!item.getState().equals(ItemState.出售中))
            return null;
        return item;
    }
}
