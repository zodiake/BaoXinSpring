package com.baosight.scc.ec.service;

import com.baosight.scc.ec.type.MaterialScope;

import java.util.List;

/**
 * Created by zodiake on 2014/6/16.
 */
public interface MaterialScopeService {
    public MaterialScope findOne(String id);
    public List<MaterialScope> findAll();
    public MaterialScope findByName(String name);
}
