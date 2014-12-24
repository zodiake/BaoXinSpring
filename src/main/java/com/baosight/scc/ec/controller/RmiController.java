package com.baosight.scc.ec.controller;

import com.baosight.scc.ec.model.FabricCategory;
import com.baosight.scc.ec.model.FabricSource;
import com.baosight.scc.ec.model.MaterialCategory;
import com.baosight.scc.ec.service.FabricCategoryService;
import com.baosight.scc.ec.service.FabricSourceService;
import com.baosight.scc.ec.service.MaterialCategoryService;
import com.baosight.scc.ec.web.FabricCategoryRmi;
import com.baosight.scc.ec.web.FabricSourceRmi;
import com.baosight.scc.ec.web.MaterialCategoryRmi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by zodiake on 2014/7/22.
 */
@Controller
@RequestMapping("/remote")
public class RmiController {
    @Autowired
    private FabricCategoryService fabricCategoryService;
    @Autowired
    private MaterialCategoryService materialCategoryService;
    @Autowired
    private FabricSourceService fabricSourceService;

    @RequestMapping(value="/json/FabricCategory",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public List<FabricCategoryRmi> getFabricCategory(){
        return fabricCategoryService.findAll();
    }

    @RequestMapping(value="/json/MaterialCategory",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public List<MaterialCategoryRmi> getMaterialCategory(){
        return materialCategoryService.findAll();
    }

    @RequestMapping(value="/json/FabricSource",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public List<FabricSourceRmi> getFabricSource(){
        return fabricSourceService.findAll();
    }

}
