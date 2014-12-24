package com.baosight.scc.ec.service.jpa;

import com.baosight.scc.ec.service.LinkConfigService;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Created by Charles on 2014/7/22.
 */
@Service
public class LinkConfigServiceImpl implements LinkConfigService {
    @Override
    public Map<String, String> linkMap() {
        InputStream in = LinkConfigServiceImpl.class.getClassLoader().getResourceAsStream("linkConfig.properties");
        Properties properties = new Properties();
        Map<String,String> map = new HashMap<String, String>();
        try {
            properties.load(in);
            Set<Map.Entry<Object, Object>> entrySet = properties.entrySet();
            for (Map.Entry<Object, Object> entry : entrySet) {
                if (!entry.getKey().toString().startsWith("#")) {
                    map.put(((String) entry.getKey()).trim(), ((String) entry.getValue()).trim());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
