package com.baosight.scc.ec.repository;

import com.baosight.scc.ec.type.FabricMainUseType;

import java.util.Collection;

/**
 * Created by zodiake on 2014/5/19.
 */
public interface FabricMainUseTypeRepository {
    public Collection<FabricMainUseType> findAll();
    public FabricMainUseType findOne(String id);
    public FabricMainUseType findByName(String name);
}
