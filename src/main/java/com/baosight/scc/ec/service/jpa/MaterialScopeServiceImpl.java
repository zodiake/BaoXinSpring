package com.baosight.scc.ec.service.jpa;

import com.baosight.scc.ec.rest.CodeAPI;
import com.baosight.scc.ec.service.MaterialScopeService;
import com.baosight.scc.ec.type.MaterialScope;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zodiake on 2014/6/16.
 */
@Service
public class MaterialScopeServiceImpl implements MaterialScopeService {
    private final static String code = "materialScope";

    @Override
    public MaterialScope findOne(String id) {
        CodeAPI api = new CodeAPI();
        Map<String, Object> map = api.getBusinessCodeByValue(code, id);
        if (!map.isEmpty())
            return new MaterialScope(map);
        return null;
    }

    @Override
    @Cacheable(value = "materialScope")
    public List<MaterialScope> findAll() {
        CodeAPI api = new CodeAPI();
        List<Map<String, Object>> lists = api.getBusinessCodeTree(code, "", 1);
        List<MaterialScope> results = new ArrayList<MaterialScope>();

        for (Map<String, Object> entry : lists) {
            MaterialScope pattern = new MaterialScope(entry);
            results.add(pattern);
        }
        return results;
    }

    @Override
    public MaterialScope findByName(String name) {
        CodeAPI api = new CodeAPI();
        Map<String, Object> map = api.getBusinessCodeByName(code, name);
        if (!map.isEmpty())
            return new MaterialScope(map);
        return null;
    }
}
