package com.baosight.scc.ec.service.jpa;

import com.baosight.scc.ec.model.EcCode;

/**
 * Created by zodiake on 2014/7/9.
 */
public class RestHelper <T extends EcCode>{
    /*
    @Override
    public List<T> findAll(Class<T> clazz,String codeType) {
        CodeAPI api = new CodeAPI();
        List<Map<String, Object>> lists = api.getBusinessCodeTree(codeType, "", 1);
        List<T> results = new ArrayList<T>();

        for (Map<String, Object> entry : lists) {
            T pattern = clazz.newInstance();
            List<Map<String, Object>> childLists = api.getBusinessCodeTree(codeType, pattern.getCode(), 2);
            List<T> childResults = new ArrayList<T>();

            for (Map<String, Object> childEntry : childLists) {
                T childPattern = clazz.newInstance();
                childResults.add(childPattern);
            }

            pattern.setSecondType(childResults);
            results.add(pattern);
        }
        return results;
    }
    */
}
