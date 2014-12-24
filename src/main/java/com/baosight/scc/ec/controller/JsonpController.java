package com.baosight.scc.ec.controller;

import com.baosight.scc.ec.search.properties.CompositeQueryParam;
import com.baosight.scc.ec.search.properties.SearchParam;
import com.baosight.scc.ec.search.service.EsService;
import com.baosight.scc.ec.service.EcPatternService;
import com.baosight.scc.ec.service.FabricCategoryService;
import com.baosight.scc.ec.service.MaterialCategoryService;
import com.baosight.scc.ec.web.EcPatternJSON;
import com.baosight.scc.ec.web.FabricCategoryRmi;
import com.baosight.scc.ec.web.MaterialCategoryRmi;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

/**
 * Created by zodiake on 2014/7/22.
 */
@Controller
@RequestMapping("/json")
public class JsonpController {
    @Autowired
    private EsService service;
    @Autowired
    private EcPatternService patternService;
    @Autowired
    private FabricCategoryService fabricCategoryService;
    @Autowired
    private MaterialCategoryService materialCategoryService;
    Logger logger = LoggerFactory.getLogger(JsonpController.class);

    @RequestMapping(value = "/fabricJsonp", method = RequestMethod.GET)
    public void FabricJsonp(@RequestParam("callback") String callback,
                            @RequestParam(value = "category", defaultValue = "") String category,
                            @RequestParam(value = "technology", defaultValue = "") String technology,
                            @RequestParam(value = "source", defaultValue = "") String source,
                            @RequestParam(value = "page", defaultValue = "1") Integer page,
                            HttpServletResponse response) throws Exception {
        response.setContentType("text/javascript");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            CompositeQueryParam param = new CompositeQueryParam();
            SearchParam param1 = null, param2 = null, param3 = null;
            if (!StringUtils.isEmpty(category)) {
                param1 = new SearchParam("category", category, false);
                param.addFilterMustParam(param1);
            }
            if (!StringUtils.isEmpty(source)) {
                param2 = new SearchParam("source", source, false);
                param.addFilterMustParam(param2);
            }
            if (!StringUtils.isEmpty(technology)) {
                param3 = new SearchParam("technology", technology, false);
                param.addFilterMustParam(param3);
            }
            param.setOffset((page - 1) * 10);
            List<Map<String, Object>> result = service.search("fabric", param);

            String jsonString = JSONValue.toJSONString(result);
            out.print(callback + "(" + jsonString.toString() + ")");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/fabricCategoryJsonp", method = RequestMethod.GET)
    public void fabricCategoryJson(@RequestParam("callback") String callback,
                                   HttpServletResponse response) throws IOException {
        response.setContentType("text/javascript");
        PrintWriter out = null;
        out = response.getWriter();
        List<MaterialCategoryRmi> result = materialCategoryService.findAll();

        JSONArray parent = new JSONArray();

        for (MaterialCategoryRmi json : result) {
            JSONArray array = new JSONArray();
            for (MaterialCategoryRmi child : json.getChildren()) {
                JSONObject c = new JSONObject();
                c.put("name", child.getName());
                c.put("id", child.getId());
                array.add(c);
            }

            JSONObject o = new JSONObject();
            o.put("name", json.getName());
            o.put("id", json.getId());
            o.put("children", array);

            parent.add(o);
        }
        out.print(callback + "(" + parent.toString() + ")");
    }

    @RequestMapping(value = "/materialCategoryJsonp", method = RequestMethod.GET)
    public void materialCategoryJson(@RequestParam("callback") String callback,
                                     HttpServletResponse response) throws IOException {
        response.setContentType("text/javascript");
        PrintWriter out = null;
        out = response.getWriter();
        List<FabricCategoryRmi> result = fabricCategoryService.findAll();

        JSONArray parent = new JSONArray();

        for (FabricCategoryRmi json : result) {
            JSONArray array = new JSONArray();
            for (FabricCategoryRmi child : json.getChildren()) {
                JSONObject c = new JSONObject();
                c.put("name", child.getName());
                c.put("id", child.getId());
                array.add(c);
            }

            JSONObject o = new JSONObject();
            o.put("name", json.getName());
            o.put("id", json.getId());
            o.put("children", array);

            parent.add(o);
        }
        out.print(callback + "(" + parent.toString() + ")");
    }

    @RequestMapping(value = "/patternJsonp", method = RequestMethod.GET)
    public void patternJson(@RequestParam("callback") String callback,
                            HttpServletResponse response) throws Exception {
        response.setContentType("text/javascript");
        PrintWriter out = null;
        out = response.getWriter();
        List<EcPatternJSON> result = patternService.findAll();

        JSONArray parent = new JSONArray();

        for (EcPatternJSON json : result) {
            JSONArray array = new JSONArray();
            for (EcPatternJSON child : json.getChildren()) {
                JSONObject c = new JSONObject();
                c.put("name", child.getName());
                c.put("id", child.getId());
                array.add(c);
            }

            JSONObject o = new JSONObject();
            o.put("name", json.getName());
            o.put("id", json.getId());
            o.put("children", array);

            parent.add(o);
        }
        out.print(callback + "(" + parent.toString() + ")");
    }

    @RequestMapping(value = "/materialJsonp", method = RequestMethod.GET)
    public void MaterialJsonp(@RequestParam("callback") String callback,
                              @RequestParam(value = "category", defaultValue = "") String category,
                              @RequestParam(value = "page", defaultValue = "1") Integer page,
                              HttpServletResponse response) throws Exception {
        response.setContentType("text/javascript");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            CompositeQueryParam param = new CompositeQueryParam();
            SearchParam param1 = null;
            if (!StringUtils.isEmpty(category))
                param1 = new SearchParam("category", category, false);
            param.addFilterMustParam(param1);
            param.setOffset((page - 1) * 10);
            List<Map<String, Object>> result = service.search("material", param);

            String jsonString = JSONValue.toJSONString(result);
            out.print(callback + "(" + jsonString.toString() + ")");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
