package com.baosight.scc.ec.service.rest;


import com.baosight.scc.ec.model.EcColor;
import com.baosight.scc.ec.model.Hierarchy;
import com.baosight.scc.ec.service.EcColorService;
import net.sf.json.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zodiake on 2014/7/21.
 */
@Service
public class EcColorServiceImpl implements EcColorService {
    @Value("${color.url}")
    private String colorUrl;

    @Value("${color.hierarchyUrl}")
    private String hierarchyUrl;

    @Override
    @Cacheable(value = "color-hierarchy" )
    public List<Hierarchy> findAll() {
        String result = postMethod(hierarchyUrl, null, null);
        ObjectMapper m = new ObjectMapper();
        List<Hierarchy> hierarchies=new ArrayList<Hierarchy>();
        JsonNode rootNode = null;

        if (result != null) {
            try {
                rootNode = m.readTree(result);
                JsonNode node = rootNode.get("data");
                if (node.isArray()) {
                    for (final JsonNode objNode : node) {
                        hierarchies.add(new Hierarchy(objNode.path("tinctCode").getValueAsText(),objNode.path("tinctDesc").getValueAsText()));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return hierarchies;
        } else
            return null;
    }

    @Override
    @Cacheable(value = "ecColor", key = "#p0")
    public EcColor findOne(String id) {
        String result = postMethod(colorUrl, "colourCode", id);
        ObjectMapper m = new ObjectMapper();
        EcColor color = new EcColor();
        JsonNode rootNode = null;
        if (result != null) {
            try {
                rootNode = m.readTree(result);
                JsonNode node = rootNode.get("data");
                if (node.isArray()) {
                    for (final JsonNode objNode : node) {
                        String rgb = objNode.path("colourSr").getValueAsText() + "," + objNode.path("colourSg").getValueAsText() + "," + objNode.path("colourSb").getValueAsText();
                        color.setRgb(rgb);
                        color.setHierarchy(objNode.path("tinctGuid").getValueAsText());
                    }
                }
                color.setId(id);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return color;
        } else
            return null;
    }

    private String postMethod(String url, String paramName, String value) {
        JSONObject object = new JSONObject();
        String param = null;
        if (paramName != null && value != null) {
            object.put(paramName, value);
            param = object.toString();
        } else {
            param = "{}";
        }
        StringRequestEntity requestEntity = null;
        try {
            requestEntity = new StringRequestEntity(param, "application/json", "UTF-8");
            PostMethod postMethod = new PostMethod(url);
            postMethod.setRequestEntity(requestEntity);
            HttpClient httpClient = new HttpClient();
            httpClient.executeMethod(postMethod);
            int status = httpClient.executeMethod(postMethod);
            if (status == 200 || status == 204) {
                return postMethod.getResponseBodyAsString();
            } else {
                System.err.println("建立请求失败，返回状态码==》" + status);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
