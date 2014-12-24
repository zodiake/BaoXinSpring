package com.baosight.scc.ec.service.jpa;

import com.baosight.scc.ec.repository.FabricMainUseTypeRepository;
import com.baosight.scc.ec.service.FabricMainUseTypeService;
import com.baosight.scc.ec.type.FabricMainUseType;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zodiake on 2014/5/14.
 */
@Service
public class FabricMainUseTypeServiceImpl implements FabricMainUseTypeService {
    @Autowired
    private FabricMainUseTypeRepository repository;

    public FabricMainUseType findOne(String id) {
        return repository.findOne(id);
    }

    @Override
    @Cacheable(value = "fabricMainUseType")
    public List<FabricMainUseType> findAll() {
        return Lists.newArrayList(repository.findAll());
    }

    @Override
    public FabricMainUseType findByName(String name) {
        return repository.findByName(name);
    }
}
