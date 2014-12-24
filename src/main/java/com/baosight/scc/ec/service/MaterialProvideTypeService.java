package com.baosight.scc.ec.service;

import com.baosight.scc.ec.type.MaterialProvideType;

import java.util.Collection;

/**
 * Created by zodiake on 2014/6/16.
 */
public interface MaterialProvideTypeService {
    public Collection<MaterialProvideType> findAll();
    public MaterialProvideType findById(String id);
    public MaterialProvideType findByName(String name);
}
