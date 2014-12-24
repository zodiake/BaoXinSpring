package com.baosight.scc.ec.service.jpa;

import com.baosight.scc.ec.rest.CodeAPI;
import com.baosight.scc.ec.service.FabricTechnologyTypeService;
import com.baosight.scc.ec.type.FabricTechnologyType;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by zodiake on 2014/6/17.
 */
@Service
public class FabricTechnologyTypeServiceImpl implements FabricTechnologyTypeService {
    private final static String codeType = "fabricTechnologyType";

    @Override
    public FabricTechnologyType findOne(String id) {
        CodeAPI api = new CodeAPI();
        Map<String, Object> map = api.getBusinessCodeByValue(codeType, id);
        return new FabricTechnologyType(map);
    }

    @Override
    public List<FabricTechnologyType> findAll() {
        CodeAPI api = new CodeAPI();
        List<Map<String, Object>> lists = api.getBusinessCodeTree(codeType, "", 1);
        List<FabricTechnologyType> results = new ArrayList<FabricTechnologyType>();

        for (Map<String, Object> entry : lists) {
            FabricTechnologyType pattern = new FabricTechnologyType(entry);
            List<Map<String, Object>> childLists = api.getBusinessCodeTree(codeType, pattern.getCode(), 2);
            List<FabricTechnologyType> childResults = new ArrayList<FabricTechnologyType>();

            for (Map<String, Object> childEntry : childLists) {
                FabricTechnologyType childPattern = new FabricTechnologyType(childEntry);
                childResults.add(childPattern);
            }

            pattern.setSecondType(childResults);
            results.add(pattern);
        }
        return results;
    }

    @Override
    public List<FabricTechnologyType> findByParentId(String id) {
        CodeAPI api = new CodeAPI();
        FabricTechnologyType type = findOne(id);
        List<Map<String, Object>> lists = api.getBusinessCodeTree(codeType, type.getCode(), 2);
        return convertListMapToList(lists);
    }

    @Override
    @Cacheable(value="fabric-technologyType")
    public List<FabricTechnologyType> findFirstCategory() {
        CodeAPI api = new CodeAPI();
        List<Map<String, Object>> lists = api.getBusinessCodeTree(codeType, "", 1);
        List<FabricTechnologyType> results = new LinkedList<FabricTechnologyType>();

        for (Map<String, Object> entry : lists) {
            FabricTechnologyType pattern = new FabricTechnologyType(entry);

            List<Map<String, Object>> childLists = api.getBusinessCodeTree(codeType, pattern.getCode(), 2);
            List<FabricTechnologyType> childResults = new ArrayList<FabricTechnologyType>();

            for (Map<String, Object> childEntry : childLists) {
                FabricTechnologyType childPattern = new FabricTechnologyType(childEntry);
                childResults.add(childPattern);
            }

            pattern.setSecondType(childResults);

            results.add(pattern);
        }
        return results;
    }

    @Override
    public List<FabricTechnologyType> findSameLevelTechnology(String id) {
        CodeAPI api = new CodeAPI();
        FabricTechnologyType type = findOne(id);
        List<FabricTechnologyType> result=new LinkedList<FabricTechnologyType>();
        List<Map<String,Object>> list=api.getCodeValueByParentId(codeType, type.getParent());
        for(Map<String,Object> map:list){
            result.add(new FabricTechnologyType(map));
        }
        return result;
    }

    @Override
    public FabricTechnologyType findByName(String name) {
        CodeAPI api = new CodeAPI();
        Map<String, Object> map = api.getBusinessCodeByName(codeType, name);
        return new FabricTechnologyType(map);
    }

    private List<FabricTechnologyType> convertListMapToList(List<Map<String, Object>> lists) {
        List<FabricTechnologyType> types = new ArrayList<FabricTechnologyType>();
        for (Map<String, Object> map : lists) {
            FabricTechnologyType type = new FabricTechnologyType(map);
            types.add(type);
        }
        return types;
    }
}
