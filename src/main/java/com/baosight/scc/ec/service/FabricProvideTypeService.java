package com.baosight.scc.ec.service;

import com.baosight.scc.ec.type.FabricProvideType;

import java.util.Collection;

/**
 * Created by zodiake on 2014/6/17.
 */
public interface FabricProvideTypeService {
    public FabricProvideType findOne(String id);
    public Collection<FabricProvideType> findAll();
    public FabricProvideType findByName(String name);
}
