package com.baosight.scc.ec.service;

import com.baosight.scc.ec.type.FabricTechnologyType;

import java.util.Collection;
import java.util.List;

/**
 * Created by zodiake on 2014/6/17.
 */
public interface FabricTechnologyTypeService {
    public Collection<FabricTechnologyType> findAll();

    public FabricTechnologyType findOne(String id);

    public List<FabricTechnologyType> findByParentId(String id);

    public List<FabricTechnologyType> findFirstCategory();

    public List<FabricTechnologyType> findSameLevelTechnology(String id);

    public FabricTechnologyType findByName(String name);
}
