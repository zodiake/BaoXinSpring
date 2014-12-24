package com.baosight.scc.ec.service;

import com.baosight.scc.ec.model.EcColor;
import com.baosight.scc.ec.model.Hierarchy;

import java.util.List;

/**
 * Created by zodiake on 2014/7/21.
 */
public interface EcColorService {
    public EcColor findOne(String id);
    public List<Hierarchy> findAll();
}
