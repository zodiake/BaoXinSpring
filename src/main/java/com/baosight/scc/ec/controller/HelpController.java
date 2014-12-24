package com.baosight.scc.ec.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by sam on 2014/9/24.
 */
@Controller
@RequestMapping(value = "/help")
public class HelpController {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setDisallowedFields("id");
    }

    @RequestMapping(value = "/userGuide",method = RequestMethod.GET)
    public String userGuide(){
        return "user_guide";
    }

    @RequestMapping(value = "/providerGuide",method = RequestMethod.GET)
    public String providerGuide(){
        return "provider_guide";
    }

    @RequestMapping(value = "/commonProblems",method = RequestMethod.GET)
    public String commonProblems(){
        return "common_problem";
    }

    @RequestMapping(value = "/newUserOption",method = RequestMethod.GET)
    public String newUserOption(){
        return "new_user_option";
    }

    @RequestMapping(value = "/companyLayOutManage",method = RequestMethod.GET)
    public String layOutManage(){
        return "company_layout_manage";
    }

    @RequestMapping(value = "/onlineOrderingManage",method = RequestMethod.GET)
    public String onlineOrderingManage(){
        return "online_ordering_manage";
    }
}
