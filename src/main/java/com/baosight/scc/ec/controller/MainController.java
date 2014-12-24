package com.baosight.scc.ec.controller;

import com.baosight.scc.ec.model.*;
import com.baosight.scc.ec.security.UserContext;
import com.baosight.scc.ec.service.*;
import com.baosight.scc.ec.type.FabricMainUseType;
import com.baosight.scc.ec.type.FabricTechnologyType;
import com.baosight.scc.ec.type.ItemState;
import com.google.common.collect.Lists;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.*;

/**
 * Created by zodiake on 2014/6/5.
 */
@Controller
public class MainController {
    @Autowired
    private FabricService fabricService;

    @Autowired
    private MaterialService materialService;

    @Autowired
    private DemandOrderService demandOrderService;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private SampleOrderService sampleOrderService;

    @Autowired
    private ExternalService externalService;
    @Autowired
    private FabricTechnologyTypeService fabricTechnologyTypeService;
    @Autowired
    private FabricMainUseTypeService fabricMainUseTypeService;

    @Autowired
    private UserContext userContext;

    @PersistenceContext
    private EntityManager em;

    public final static String REDIRECT = "redirect:/index";
    public final static String ADMIN_MAIN_PAGE = "adminMainPage";
    public final static String HOMEPAGE = "homePage";
    private final static String LOGIN_REDIRECT = "login_redirect";
    private final static String LOGOUT_REDIRECT = "logout_redirect";
    private final static String FORBIDDEN = "forbidden";
    private final static String NOT_FOUND = "notFound";
    private final static String INNER_ERROR = "innerError";
    private final static String UNDER_CONSTRUCTION = "underConstruction";
    private final static String ADMIN_STATISTICS = "adminStatistics";


    @RequestMapping(value = {"/index"}, method = RequestMethod.GET)
    public String index(Model uiModel) {
        List<DemandOrder> demandOrderList = demandOrderService.findTopN(5);
        Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
        PageRequest fabricPageRequest = new PageRequest(0, 4, sort);
        List<Fabric> fabricList = fabricService.findTop4(ItemState.出售中,fabricPageRequest);
        for (Fabric f : fabricList) {
            f.setUrl("fabric");
        }

        List<Material> materialList = materialService.findTop4(ItemState.出售中,fabricPageRequest);
        for (Material m : materialList) {
            m.setUrl("material");
        }

        DateTime dateTime = new DateTime();
        int year = dateTime.getYear();
        int day = dateTime.getDayOfMonth();
        int month = dateTime.getMonthOfYear();
        String monthStr = "";
        String dayStr = "";
        if(month<10){
            monthStr = "0"+month;
        }else {
            monthStr = month+"";
        }
        if(day<10){
            dayStr = "0"+day;
        }else {
            dayStr = ""+day;
        }

        //面料工艺分类
        List<FabricTechnologyType> fabricTechnologyTypeList = fabricTechnologyTypeService.findFirstCategory();
        //面料主要用途分类
        List<FabricMainUseType> fabricMainUseTypeList = fabricMainUseTypeService.findAll();

        List<Designer> designerList = externalService.designerOnHome();

        List<Designer> brands = externalService.brandsOnHome();

        List<Designer> orderingList = externalService.orderingOnHome();
        //平台统计数据
        Map<String,String> statistics = externalService.statistics();

        uiModel.addAttribute("year",year);
        uiModel.addAttribute("month",monthStr);
        uiModel.addAttribute("day",dayStr);
        uiModel.addAttribute("fabricList",fabricList);
        uiModel.addAttribute("materialList",materialList);
        uiModel.addAttribute("demandOrderList",demandOrderList);
        uiModel.addAttribute("designerList",designerList);
        uiModel.addAttribute("brands",brands);
        uiModel.addAttribute("orderingList",orderingList);
        uiModel.addAttribute("fabricTechnologyTypeList",fabricTechnologyTypeList);
        uiModel.addAttribute("fabricMainUseTypeList",fabricMainUseTypeList);
        uiModel.addAttribute("statistics",statistics);
        return HOMEPAGE;
    }

    @RequestMapping(value = {"/admin"}, method = RequestMethod.GET)
    public String admin(Model uiModel) {
        EcUser user = userContext.getCurrentUser();
        Date date = new Date();
        uiModel.addAttribute("user",user);
        uiModel.addAttribute("date",date);
        return ADMIN_MAIN_PAGE;
    }

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String redirectIndex(){
        return REDIRECT;
    }

    @RequestMapping(value = {"/header"}, method = RequestMethod.GET)
    public String head(){
        return "template/Outsideheader";
    }

    /**
     * 登录跳转
     */
    @RequestMapping(value="/login",method = RequestMethod.GET)
    public String login(){
        return LOGIN_REDIRECT;
    }

    /**
     * 登出跳转
     */
    @RequestMapping(value="/logout",method = RequestMethod.GET)
    public String logout(){
        return LOGOUT_REDIRECT;
    }


    /**
     * 登出跳转
     */
    @RequestMapping(value="/whoami",method = RequestMethod.GET)
    public String whoami(){
        return "whoami";
    }

    /**
     * 403页面跳转
     */
    @RequestMapping(value="/forbidden",method = RequestMethod.GET)
    public String forbidden(){
        return FORBIDDEN;
    }

    /**
     * 404页面跳转
     */
    @RequestMapping(value="/notFound",method = RequestMethod.GET)
    public String notFound(){
        return NOT_FOUND;
    }


    /**
     * 500页面跳转
     */
    @RequestMapping(value="/innerError",method = RequestMethod.GET)
    public String innerError(){
        return INNER_ERROR;
    }

    /**
     * 网站建设页面跳转
     */
    @RequestMapping(value="/underConstruction",method = RequestMethod.GET)
    public String underConstruction(){
        return UNDER_CONSTRUCTION;
    }

    @RequestMapping(value = {"/admin/statistics"}, method = RequestMethod.GET)
    public String statistics(Model uiModel) {
        Date date = new Date();
        Map<String,Integer> fabricMap = fabricService.fabricCount();
        Map<String,Integer> materialMap = materialService.materialCount();
        Map<String,Integer> orderMap = orderItemService.orderCount();
        Map<String,Integer> sampleMap = sampleOrderService.sampleCount();
        Map<String,Integer> demandMap = demandOrderService.demandMap();
        uiModel.addAttribute("date",date);
        uiModel.addAttribute("fabricMap",fabricMap);
        uiModel.addAttribute("materialMap",materialMap);
        uiModel.addAttribute("orderMap",orderMap);
        uiModel.addAttribute("sampleMap",sampleMap);
        uiModel.addAttribute("demandMap",demandMap);
        return ADMIN_STATISTICS;
    }
}
