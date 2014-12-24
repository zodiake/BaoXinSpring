package com.baosight.scc.ec.controller;

import com.baosight.scc.ec.model.Unit;
import com.baosight.scc.ec.rest.CodeAPI;
import com.baosight.scc.ec.search.properties.CompositeQueryParam;
import com.baosight.scc.ec.search.properties.RangeParam;
import com.baosight.scc.ec.search.properties.SearchParam;
import com.baosight.scc.ec.search.service.EsService;
import com.baosight.scc.ec.service.EcColorService;
import com.baosight.scc.ec.service.SeasonService;
import com.baosight.scc.ec.type.FabricWidthType;
import com.baosight.scc.ec.type.MaterialType;
import com.baosight.scc.ec.type.MaterialWidthAndSizeType;
import com.baosight.scc.ec.type.SearchSortType;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.elasticsearch.search.sort.SortOrder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by zodiake on 2014/7/2.
 */
@Component
public abstract class AbstractSearchController extends AbstractController {
    @Autowired
    protected EsService esService;
    @Autowired
    protected SeasonService seasonService;
    @Autowired
    protected EcColorService colorService;
    @Autowired
    protected StringRedisTemplate template;

    private String url;

    @Value("${explain-url}")
    public void setUrl(String url) {
        this.url = url;
    }

    protected final int PAGESIZE = 15;
    protected final String redis_fabric = "ec:explain:fabric";
    protected final String redis_material = "ec:explain:material";

    private Logger logger = LoggerFactory.getLogger(AbstractSearchController.class);

    protected void addSort(CompositeQueryParam compositeQueryParam, SearchSortType sort) {
        if (sort == SearchSortType.priceasc) {
            compositeQueryParam.setSortField("price");
            compositeQueryParam.setSortOrder(SortOrder.ASC);
        } else if (sort == SearchSortType.pricedesc) {
            compositeQueryParam.setSortField("price");
            compositeQueryParam.setSortOrder(SortOrder.DESC);
        } else if (sort == SearchSortType.sales) {
            compositeQueryParam.setSortField("sales");
            compositeQueryParam.setSortOrder(SortOrder.DESC);
        } else if (sort == SearchSortType.view) {
            compositeQueryParam.setSortField("viewCount");
            compositeQueryParam.setSortOrder(SortOrder.DESC);
        }
    }

    protected void addRange(CompositeQueryParam compositeQueryParam, String field, Double min, Double max) {
        RangeParam param = new RangeParam(field, min, max);
        compositeQueryParam.addQueryRangeParam(param);
    }

    protected String[] ListToStringArray(List<?> list) {
        List<String> lists = new ArrayList<String>();
        if (list != null) {
            for (Object c : list) {
                lists.add(c.toString());
            }
        }
        return lists.toArray(new String[]{});
    }

    protected void addQueryAndFilter(CompositeQueryParam param, String attribute, String[] filter) {
        if (filter != null && filter.length > 0) {
            for (String c : filter) {
                if (!StringUtils.isEmpty(c)) {
                    SearchParam colorParam = new SearchParam(attribute, c, false);
                    param.addFilterMustParam(colorParam);
                }
            }
        }
    }

    protected List<String> convertListMapToList(String codeType) {
        CodeAPI api = new CodeAPI();
        List<Map<String, Object>> childLists = api.getBusinessCodeTree(codeType, "", 1);
        List<String> result = new LinkedList<String>();
        for (Map<String, Object> map : childLists) {
            if (map.get("valueName") != null)
                result.add((String) map.get("valueName"));
        }
        return result;
    }

    protected List<FabricWidthType> convertListMapToListWidthType() {
        CodeAPI api = new CodeAPI();
        List<Map<String, Object>> childLists = api.getBusinessCodeTree("FabricWidth", "", 1);
        List<FabricWidthType> result = new LinkedList<FabricWidthType>();
        for (Map<String, Object> map : childLists) {
            result.add(new FabricWidthType(map));
        }
        FabricWidthType type = new FabricWidthType();
        type.setName("--请选择--");
        result.add(0, type);
        return result;
    }

    protected List<Unit> convertListMapToUnit() {
        CodeAPI api = new CodeAPI();
        List<Map<String, Object>> childLists = api.getBusinessCodeTree("unit", "", 1);
        List<Unit> result = new LinkedList<Unit>();
        for (Map<String, Object> map : childLists) {
            result.add(new Unit(map));
        }
        Unit type = new Unit();
        type.setName("--请选择--");
        result.add(0, type);
        return result;
    }

    protected List<MaterialType> convertListMapToMaterialType(String code, Class clazz) {
        CodeAPI api = new CodeAPI();
        List<Map<String, Object>> childLists = api.getBusinessCodeTree("ecMaterialType", "", 1);
        List<MaterialType> result = new LinkedList<MaterialType>();
        for (Map<String, Object> map : childLists) {
            result.add(new MaterialType(map));
        }
        MaterialType type = new MaterialType();
        type.setName("--请选择--");
        result.add(0, type);
        return result;
        /*
        List result = new LinkedList();
        for (Map<String, Object> map : childLists) {
            result.add(new MaterialType(map));
        }

        try {
            result.add(clazz.newInstance());
            Object type = clazz.newInstance();
            Field field = clazz.getDeclaredField("name");
            field.setAccessible(true);
            field.set(type, "--请选择--");
            result.add(0, type);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return (List<MaterialType>) result;
        */
    }

    protected List<MaterialWidthAndSizeType> convertListMapToMaterialHeight() {
        CodeAPI api = new CodeAPI();
        List<Map<String, Object>> childLists = api.getBusinessCodeTree("widthType", "", 1);
        List<MaterialWidthAndSizeType> result = new LinkedList<MaterialWidthAndSizeType>();
        for (Map<String, Object> map : childLists) {
            result.add(new MaterialWidthAndSizeType(map));
        }
        MaterialWidthAndSizeType type = new MaterialWidthAndSizeType();
        type.setName("--请选择--");
        result.add(0, type);
        return result;
    }

    protected void explainMaterialKeyword(String word) {
        explain(word, redis_material);
    }

    protected void explainKeyword(String word) {
        explain(word, redis_fabric);
    }

    @Async
    private void explain(String word, String key) {
        PostMethod postMethod = new PostMethod(url);
        try {
            RequestEntity requestEntity = new StringRequestEntity(word, "text/plain", "UTF-8");
            postMethod.setRequestEntity(requestEntity);
            HttpClient httpClient = new HttpClient();
            httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(50000);// 设置超时

            int status = httpClient.executeMethod(postMethod);
            if (status == 200 || status == 204) {
                String json = postMethod.getResponseBodyAsString();

                JSONParser parser = new JSONParser();
                JSONObject object = (JSONObject) parser.parse(json);
                JSONArray array = (JSONArray) object.get("tokens");
                Iterator iterator = array.iterator();
                while (iterator.hasNext()) {
                    JSONObject obj = (JSONObject) iterator.next();
                    String token = (String) obj.get("token");
                    if(token.length()>1)
                        template.opsForZSet().incrementScore(key, token, 1);
                }
                logger.debug(template.opsForZSet().zCard(key) + "");
            } else {
                System.err.println("建立请求失败，返回状态码==》" + status);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
