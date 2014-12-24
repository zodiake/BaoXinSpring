package com.baosight.scc.ec.service.jpa;

import com.baosight.scc.ec.model.EcPattern;
import com.baosight.scc.ec.rest.CodeAPI;
import com.baosight.scc.ec.service.EcPatternService;
import com.baosight.scc.ec.web.EcPatternJSON;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zodiake on 2014/7/7.
 */
@Service
public class EcPatternServiceImpl implements EcPatternService {
    private final static String code = "patternClass";

    public List<EcPatternJSON> findAll() {
        CodeAPI api = new CodeAPI();
        List<Map<String, Object>> lists = api.getBusinessCodeTree(code, "", 1);
        List<EcPatternJSON> results = new ArrayList<EcPatternJSON>();

        for (Map<String, Object> entry : lists) {
            EcPattern pattern = new EcPattern(entry);
            EcPatternJSON jsonPattern = new EcPatternJSON(pattern);

            List<Map<String, Object>> childLists = api.getBusinessCodeTree(code, pattern.getCode(), 2);
            List<EcPatternJSON> childResults = new ArrayList<EcPatternJSON>();

            for (Map<String, Object> childEntry : childLists) {
                EcPattern childPattern = new EcPattern(childEntry);
                EcPatternJSON childJSON = new EcPatternJSON(childPattern);
                childResults.add(childJSON);
            }

            jsonPattern.setChildren(childResults);
            results.add(jsonPattern);
        }
        return results;
    }

    @Override
    public EcPattern findByName(String name) {
        CodeAPI api = new CodeAPI();
        Map<String, Object> map = api.getBusinessCodeByName(code, name);
        if (!map.isEmpty())
            return new EcPattern(map);
        return null;
    }

    @Override
    @Cacheable(value = "pattern-firstCategory")
    public List<EcPattern> findFirstCategory() {
        CodeAPI api = new CodeAPI();
        List<Map<String, Object>> lists = api.getBusinessCodeTree(code, "", 1);
        List<EcPattern> results = new ArrayList<EcPattern>();

        for (Map<String, Object> entry : lists) {
            EcPattern pattern = new EcPattern(entry);
            List<Map<String, Object>> childLists = api.getBusinessCodeTree(code, pattern.getCode(), 2);
            List<EcPattern> childResults = new ArrayList<EcPattern>();

            for (Map<String, Object> childEntry : childLists) {
                EcPattern childPattern = new EcPattern(childEntry);
                childResults.add(childPattern);
            }

            pattern.setChildren(childResults);
            results.add(pattern);
        }
        return results;
    }

    @Override
    public EcPattern findOne(String id) {
        CodeAPI api = new CodeAPI();
        Map<String, Object> map = api.getBusinessCodeByValue(code, id);
        if (map != null && !map.isEmpty())
            return new EcPattern(map);
        return null;
    }
}
