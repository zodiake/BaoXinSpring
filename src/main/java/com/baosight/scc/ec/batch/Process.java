package com.baosight.scc.ec.batch;

import com.baosight.scc.ec.batch.repository.FabricDTOService;
import com.baosight.scc.ec.model.Fabric;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2014/9/22.
 */
public class Process implements ItemProcessor<BatchProduct,Fabric> {
    @Autowired
    private FabricDTOService service;

    public FabricDTOService getService() {
        return service;
    }

    public void setService(FabricDTOService service) {
        this.service = service;
    }

    @Override
    public Fabric process(BatchProduct item) throws Exception {
        return service.transferFromBatchProductToFabric(item);
    }
}
