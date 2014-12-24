package com.baosight.scc.ec.batch.repository;

import com.baosight.scc.ec.batch.BatchProduct;
import com.baosight.scc.ec.model.Fabric;

import java.text.ParseException;

/**
 * Created by Administrator on 2014/9/22.
 */
public interface FabricDTOService {
    Fabric transferFromBatchProductToFabric(BatchProduct product) throws ParseException;
}
