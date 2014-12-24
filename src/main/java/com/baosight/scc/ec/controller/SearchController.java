package com.baosight.scc.ec.controller;

import com.baosight.scc.ec.model.ShopSearch;
import com.baosight.scc.ec.model.ShopSearchType;
import com.baosight.scc.ec.search.properties.CompositeQueryParam;
import com.baosight.scc.ec.search.properties.SearchParam;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.spi.LoggerFactoryBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import org.apache.commons.httpclient.HttpClient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by zodiake on 2014/7/11.
 */
@Controller
@RequestMapping(value = "/search")
public class SearchController extends AbstractSearchController {
    private Logger logger = LoggerFactory.getLogger(SearchController.class);
    private static final String SEARCH = "shop_search";

    @RequestMapping(value = "/shop", method = RequestMethod.GET)
    public String searchFabric(@ModelAttribute("search") ShopSearch search, Model uiModel) {
        Long count = esService.countOper("user", convertSearchParam(search),QueryStringQueryBuilder.Operator.AND);
        Long pageCount = count % PAGESIZE == 0 ? count / PAGESIZE : count / PAGESIZE + 1;

        if (search.getPage() <= 0)
            search.setPage(1);
        if (search.getPage() > pageCount)
            search.setPage(pageCount.intValue());

        List<Map<String, Object>> list = esService.searchOper("user", convertSearchParam(search), QueryStringQueryBuilder.Operator.AND);

        List<ShopSearchType> shops = getSearchSuggestions("user");


        if (list == null) logger.info("list is null please check");

        List<String> types = new ArrayList<String>();
        types.add("面料");
        types.add("辅料");

        uiModel.addAttribute("shops", shops);
        uiModel.addAttribute("lists", list);
        uiModel.addAttribute("types", types);
        uiModel.addAttribute("search", search);
        uiModel.addAttribute("count", count);
        uiModel.addAttribute("pageCount", pageCount);
        uiModel.addAttribute("currentPage", search.getPage());
        return SEARCH;
    }

    public CompositeQueryParam convertSearchParam(ShopSearch search) {
        CompositeQueryParam compositeQueryParam = new CompositeQueryParam();

        if (!StringUtils.isEmpty(search.getKeyWord())) {
            String keyWord = search.getKeyWord();
            SearchParam param = new SearchParam("name", keyWord, true);
            compositeQueryParam.addQueryStringParam(param);
        }

        addQueryAndFilter(compositeQueryParam, "type", ListToStringArray(search.getType()));

        if (search.getMinMoney() != null && search.getMaxMoney() != null) {
            addRange(compositeQueryParam, "money", search.getMinMoney(), search.getMaxMoney());
        }

        if (search.getRangeMinMoney() != null && search.getRangeMaxMoney() != null)
            addRange(compositeQueryParam, "money", search.getRangeMinMoney(), search.getRangeMaxMoney());
        else if (search.getMinMoney() != null && search.getMaxMoney() != null)
            addRange(compositeQueryParam, "money", search.getMinMoney(), search.getMaxMoney());
        else if (search.getMinMoney() != null)
            addRange(compositeQueryParam, "money", search.getMinMoney(), Double.MAX_VALUE);
        else if (search.getMaxMoney() != null)
            addRange(compositeQueryParam, "money", 0.0, search.getMaxMoney());
        else
            addRange(compositeQueryParam, "money", 0.0, Double.MAX_VALUE);


        if (search.getPage() == null)
            search.setPage(1);

        compositeQueryParam.setSortField("_id");
        compositeQueryParam.setSortOrder(SortOrder.ASC);

        compositeQueryParam.setOffset((search.getPage() - 1) * PAGESIZE);
        compositeQueryParam.setLimit(PAGESIZE);

        return compositeQueryParam;
    }

    private List<ShopSearchType> getSearchSuggestions(String type) {
        List<Map<String, Object>> suggests = esService.matchAll(type, convertSuggestParam(6));
        List<ShopSearchType> suggestions = convertFromMap(suggests);
        return suggestions;
    }

    private CompositeQueryParam convertSuggestParam(int limit) {
        CompositeQueryParam compositeQueryParam = new CompositeQueryParam();
        compositeQueryParam.setLimit(limit);
        return compositeQueryParam;
    }

    private List<ShopSearchType> convertFromMap(List<Map<String, Object>> list) {
        List<ShopSearchType> shopSearchTypes = new ArrayList<ShopSearchType>();
        if (list != null) {
            for (Map<String, Object> maps : list) {
                ShopSearchType shopSearchType = new ShopSearchType(maps);
                shopSearchTypes.add(shopSearchType);
            }
        }
        return shopSearchTypes;
    }
}
