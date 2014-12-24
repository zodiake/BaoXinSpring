package com.baosight.scc.ec.repository.impl;

import com.baosight.scc.ec.repository.FabricMainUseTypeRepository;
import com.baosight.scc.ec.rest.CodeAPI;
import com.baosight.scc.ec.type.FabricMainUseType;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by zodiake on 2014/5/19.
 */
@Component
public class FabricMainUseTypeRepositoryImpl implements FabricMainUseTypeRepository {
    private static final String code = "FabricMainUse";

    @Override
    public Collection<FabricMainUseType> findAll() {
        CodeAPI api = new CodeAPI();
        List<Map<String, Object>> lists = api.getBusinessCodeTree(code, "", 1);
        List<FabricMainUseType> seasons = new ArrayList<FabricMainUseType>();
        for (Map<String, Object> map : lists) {
            if (map != null)
                seasons.add(new FabricMainUseType(map));
        }
        return seasons;
    }

    @Override
    public FabricMainUseType findOne(String id) {
        CodeAPI api = new CodeAPI();
        Map<String, Object> map = api.getBusinessCodeByValue(code, id);
        return new FabricMainUseType(map);
    }

    public FabricMainUseType findByName(String name){
        CodeAPI api = new CodeAPI();
        Map<String, Object> map = api.getBusinessCodeByName(code,name);
        return new FabricMainUseType(map);
    }
}
