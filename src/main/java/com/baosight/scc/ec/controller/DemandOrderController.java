package com.baosight.scc.ec.controller;

import com.baosight.scc.ec.model.*;
import com.baosight.scc.ec.rest.CodeAPI;
import com.baosight.scc.ec.security.UserContext;
import com.baosight.scc.ec.service.DemandOrderService;
import com.baosight.scc.ec.service.EcUserService;
import com.baosight.scc.ec.service.ExternalService;
import com.baosight.scc.ec.service.FabricProvideTypeService;
import com.baosight.scc.ec.type.DemandOrderState;
import com.baosight.scc.ec.type.FabricProvideType;
import com.baosight.scc.ec.utils.GuidUtil;
import com.baosight.scc.ec.utils.UrlUtil;
import com.baosight.scc.ec.web.EcGrid;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

/**
 * Created by zodiake on 2014/5/22.
 */
@Controller
public class DemandOrderController extends AbstractController {
    @Autowired
    private DemandOrderService service;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private UserContext userContext;
    @Autowired
    private EcUserService ecUserService;
    @Autowired
    private ExternalService externalService;
    @Autowired
    private FabricProvideTypeService provideTypeService;

    private Logger logger = LoggerFactory.getLogger(DemandOrderController.class);

    public final static String EDIT_DEMANDORDER = "demandOrder.edit";
    public final static String VIEW = "demandOrder.view";
    public final static String RELEASEDLIST = "demandOrder.releasedList";
    public final static String REDIRECT = "redirect:/buyerCenter/demandOrders";
    public final static String FORBIDDEN = "redirect:/forbidden";
    public final static String LIST = "demandList";

    /*
        获取当前用户提交的求购单列表
        @param:page current page
        @Param:size current page items number
        @param:uiModel
        @param:state demandOrder state
     */
    @RequestMapping(value = "/buyerCenter/demandOrders", method = RequestMethod.GET)
    public String viewDemandOrder(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                  @RequestParam(value = "size", required = false, defaultValue = "15") Integer size,
                                  @RequestParam(value = "type", required = false, defaultValue = "8") int state,
                                  Model uiModel) {

        DemandOrderState demandOrderState;
        if (state >= 0 && state <= 8)
            demandOrderState = DemandOrderState.values()[state];
        else
            demandOrderState = DemandOrderState.全部;

        EcUser currentUser = userContext.getCurrentUser();
        Sort sort = new Sort(Sort.Direction.DESC, "createdTime");

        PageRequest pageRequest = createPageRequest(page, size, sort);
        EcGrid<DemandOrder> grids;
        if (state == 8)
            grids = createGrid(service.findByUser(currentUser, pageRequest));
        else
            grids = createGrid(service.findByUserAndState(currentUser, demandOrderState, pageRequest));

        if (logger.isDebugEnabled())
            logger.debug(grids.getEcList().size() + "");
        uiModel.addAttribute("grids", grids);
        uiModel.addAttribute("status",state);
        return RELEASEDLIST;
    }

    /*
        查看demandOrder
        @param id demandOrder pk
        @param uiModel
     */
    @RequestMapping(value = "/demandOrder/{id}", method = RequestMethod.GET)
    public String view(@PathVariable("id") String id, Model uiModel) {
        DemandOrder demandOrder = service.findOne(id);
        Calendar calendar =demandOrder.getValidDateTo();
        long day = Math.round((calendar.getTime().getTime() - new Date().getTime())/(3600*24*1000));
        Calendar c1 = demandOrder.getCreatedTime();
        Calendar c2 = Calendar.getInstance();
        long createDay = 0;
        createDay = Math.round((new Date().getTime() - demandOrder.getCreatedTime().getTime().getTime())/(3600*24*1000));
        EcUser user = ecUserService.findById(demandOrder.getCreatedBy().getId());
        InsideLetter letter = new InsideLetter();
        uiModel.addAttribute("demandOrder", demandOrder);
        uiModel.addAttribute("day",day);
        uiModel.addAttribute("createDay",createDay);
        uiModel.addAttribute("letter",letter);
        uiModel.addAttribute("user",user);
        return VIEW;
    }

    /*
        创建form让用户输入
        @param uiModel
     */
    @RequestMapping(value = "/buyerCenter/demandOrder", method = RequestMethod.GET)
    public String createForm(Model uiModel) {
        DemandOrder demandOrder = new DemandOrder();
        List<Unit> units = initUnit();
        Collection<FabricProvideType> types=provideTypeService.findAll();
        uiModel.addAttribute("demandOrder", demandOrder);
        uiModel.addAttribute("units",units);
        uiModel.addAttribute("types",types);
        return EDIT_DEMANDORDER;
    }

    public List<Unit> initUnit() {
        logger.debug("call unit");
        return convertListMapToUnit();
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
    /*
        处理用户新建的表单数据
        @param demandOrder submit form data
        @param redirectArrributes
        @param locale
        @param request
        @param uiModel
     */
    @RequestMapping(value = "/buyerCenter/demandOrder", method = RequestMethod.POST)
    public String save(@Valid @ModelAttribute("demandOrder") DemandOrder demandOrder,BindingResult result, Model uiModel) {
        if (result.hasErrors()) {
            List<Unit> units = initUnit();
            Collection<FabricProvideType> types=provideTypeService.findAll();
            uiModel.addAttribute("units",units);
            uiModel.addAttribute("types",types);
            uiModel.addAttribute("demandOrder", demandOrder);
            return EDIT_DEMANDORDER;
        }
        demandOrder.setCreatedBy(userContext.getCurrentUser());
        demandOrder.setDemandOrderNo(GuidUtil.getStr());
        demandOrder.setState(DemandOrderState.发布中);
        DemandOrder source = service.save(demandOrder);

        return REDIRECT;
    }


    /*
        获取用户需要更新的demandOrder
        将地址分装成 demanorderaddress
        @param id demandOrder pk
     */
    @RequestMapping(value = "/buyerCenter/demandOrder/{id}/edit", method = RequestMethod.GET)
    public String getEditForm(@PathVariable("id") String id, Model uiModel) {
        EcUser user = userContext.getCurrentUser();
        DemandOrder demandOrder = service.findByIdAndUser(id, user);
        demandOrder.setTempImages(initImages(demandOrder.getImages()));
        if (demandOrder != null) {
            List<Unit> units = initUnit();
            Collection<FabricProvideType> types=provideTypeService.findAll();

            uiModel.addAttribute("units",units);
            uiModel.addAttribute("types",types);
            uiModel.addAttribute("demandOrder", demandOrder);
            return EDIT_DEMANDORDER;
        } else {
            return FORBIDDEN;
        }
    }

    /*
        处理用户跟新数据
        @param id demandOrder pk
        @param uiModel
        @param request
        @param locale
        @param result
        @param redirectAttributes
     */
    @RequestMapping(value = "/buyerCenter/demandOrder/{id}/edit", method = RequestMethod.POST)
    public String updateForm(@PathVariable("id") String id, Model uiModel, HttpServletRequest request, Locale locale,
                             @Valid @ModelAttribute("demandOrder") DemandOrder demandOrder,
                             BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            List<Unit> units = initUnit();
            Collection<FabricProvideType> types=provideTypeService.findAll();

            uiModel.addAttribute("units",units);
            uiModel.addAttribute("types",types);
            uiModel.addAttribute("demandOrder", demandOrder);
            return EDIT_DEMANDORDER;
        }
        EcUser u = userContext.getCurrentUser();
        demandOrder.setId(id);
        demandOrder.setCreatedBy(u);
        demandOrder.setDemandOrderNo(GuidUtil.getStr());
        demandOrder.setState(DemandOrderState.发布中);
        DemandOrder order = service.update(demandOrder);
        /*redirectAttributes.addFlashAttribute("message", new Message("success", messageSource.getMessage("save_success", new Object[]{}, locale)));
        return REDIRECT + UrlUtil.encodeUrlPathSegment(order.getId(), request) + "?edit";*/
        return REDIRECT;
    }

    @RequestMapping(value = "/isLogin",method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String isLogin(){
        EcUser user = userContext.getCurrentUser();
        if (user != null){
            return "success";
        }
       return "fail";
    }

    /*
    更新求购单状态
     */
    @RequestMapping(value = "/buyerCenter/demandOrder/{id}/updateStatus",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public Map<String,String> updateStatus(Model model,@PathVariable("id") String id,@RequestParam(value = "type",defaultValue = "8") Integer status){
        Map<String,String > map = new HashMap<String, String>();

        if (status > 8 || status < 0){
            map.put("message","参数不合法");
        }else{
            DemandOrderState demandOrderState = DemandOrderState.values()[status];
            DemandOrder demandOrder = service.findOne(id);
            demandOrder.setState(demandOrderState);
            service.update(demandOrder);
            map.put("message","操作成功");
        }
        return map;
    }

    @RequestMapping(value = "/demandOrderList",method = RequestMethod.GET)
    public String findDemandOrdersByParams(Model model,
                                           @RequestParam(value = "page",required = false,defaultValue = "1") Integer page,
                                           @RequestParam(value = "size",required = false,defaultValue = "5") Integer size,
                                           @RequestParam(value="type",required = false,defaultValue = "all") String type,
                                           @RequestParam(value = "content",required = false) String content){
        Sort sort = new Sort(Sort.Direction.DESC, "validDateFrom");
        PageRequest pageRequest = createPageRequest(page, size, sort);
        String demandType = null;
        if(type != null && !"".equals(type)){
            if("all".equals(type))
                demandType=null;
            else if("fabric".equals(type))
                demandType="面料";
            else if ("material".equals(type))
                demandType="辅料";
        }
        if (content == null || "".equals(content))
            content = null;
        EcGrid<DemandOrder> grid = new EcGrid<DemandOrder>();
        grid = createGrid(service.findDemandOrdersByParams(demandType,content,pageRequest));
        //平台统计数据
        Map<String,String> statistics = externalService.demandStatistics();
        model.addAttribute("grid",grid);
        model.addAttribute("type",type);
        model.addAttribute("content",content);
        model.addAttribute("statistics",statistics);
        return LIST;
    }
}
