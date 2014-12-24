package com.baosight.scc.ec.batch.repository;

import com.baosight.scc.ec.batch.BatchMaterial;
import com.baosight.scc.ec.model.Material;

import java.text.ParseException;

/**
 * Created by Administrator on 2014/9/26.
 */
public interface MaterialDTOService {
    public Material transferFromBatch(BatchMaterial material) throws ParseException;
}
