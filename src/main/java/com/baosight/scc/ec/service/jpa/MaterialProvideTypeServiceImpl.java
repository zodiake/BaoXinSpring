package com.baosight.scc.ec.service.jpa;

import com.baosight.scc.ec.rest.CodeAPI;
import com.baosight.scc.ec.service.MaterialProvideTypeService;
import com.baosight.scc.ec.type.MaterialProvideType;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by zodiake on 2014/6/16.
 */
@Service
public class MaterialProvideTypeServiceImpl implements MaterialProvideTypeService {
    private final static String code = "MaterialProvideType";

    public MaterialProvideTypeServiceImpl() {
    }

    @Override
    @Cacheable(value = "materialProvideType")
    public Collection<MaterialProvideType> findAll() {
        CodeAPI api = new CodeAPI();
        List<Map<String, Object>> lists = api.getBusinessCodeTree(code, "", 1);
        List<MaterialProvideType> results = new ArrayList<MaterialProvideType>();

        for (Map<String, Object> entry : lists) {
            MaterialProvideType pattern = new MaterialProvideType(entry);
            results.add(pattern);
        }
        return results;
    }

    @Override
    public MaterialProvideType findById(String id) {
        CodeAPI api = new CodeAPI();
        Map<String, Object> map = api.getBusinessCodeByValue(code, id);
        if (!map.isEmpty())
            return new MaterialProvideType(map);
        return null;
    }

    @Override
    public MaterialProvideType findByName(String name) {
        CodeAPI api = new CodeAPI();
        Map<String, Object> map = api.getBusinessCodeByName(code, name);
        if (!map.isEmpty())
            return new MaterialProvideType(map);
        return null;

    }
}
