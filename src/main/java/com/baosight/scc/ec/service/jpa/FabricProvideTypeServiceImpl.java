package com.baosight.scc.ec.service.jpa;

import com.baosight.scc.ec.rest.CodeAPI;
import com.baosight.scc.ec.service.FabricProvideTypeService;
import com.baosight.scc.ec.type.FabricProvideType;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by zodiake on 2014/6/17.
 */
@Service
public class FabricProvideTypeServiceImpl implements FabricProvideTypeService {
    private final static String code = "fabricProvideType";

    @Override
    public FabricProvideType findOne(String id) {
        CodeAPI api = new CodeAPI();
        Map<String, Object> map = api.getBusinessCodeByValue(code, id);
        if (!map.isEmpty())
            return new FabricProvideType(map);
        return null;
    }

    @Override
    @Cacheable(value = "provideType")
    public Collection<FabricProvideType> findAll() {
        CodeAPI api = new CodeAPI();
        List<Map<String, Object>> lists = api.getBusinessCodeTree(code, "", 1);
        List<FabricProvideType> results = new ArrayList<FabricProvideType>();

        for (Map<String, Object> entry : lists) {
            FabricProvideType pattern = new FabricProvideType(entry);
            results.add(pattern);
        }
        return results;
    }

    @Override
    public FabricProvideType findByName(String name) {
        CodeAPI api = new CodeAPI();
        Map<String, Object> map = api.getBusinessCodeByName(code, name);
        return new FabricProvideType(map);
    }
}
