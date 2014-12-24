package com.baosight.scc.ec.controller;

import com.baosight.scc.ec.model.Information;
import com.baosight.scc.ec.model.InformationCategory;
import com.baosight.scc.ec.model.Message;
import com.baosight.scc.ec.service.InformationCategoryService;
import com.baosight.scc.ec.service.InformationService;
import com.baosight.scc.ec.web.EcGrid;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Charles on 2014/6/5.
 */
@Controller
public class InformationController extends AbstractController{
    @Autowired
    private InformationService informationService;
    @Autowired
    private InformationCategoryService informationCategoryService;
    @Autowired
    private MessageSource messageSource;

    public final static String INFO_LIST = "info_list";
    public final static String INFO_CREATE = "info_create";
    public final static String INFO_EDIT = "info_edit";
    public final static String INFO_VIEW = "info_view";
    public final static String REDIRECT_LIST = "redirect:/admin/informationList";
    public final static String INFO_SHOW_LIST = "infoShowList";
    public final static String INFO_DETAIL = "infoDetail";
    public final static String NOTICE_SHOW_LIST = "noticeShowList";
    public final static String NOTICE_DETAIL = "noticeDetail";
    public final static String TOPIC_SHOW_LIST = "topicShowList";
    public final static String TOPIC_DETAIL = "topicDetail";
    public final static String TOPIC1022_SHOW_LIST = "topic1022ShowList";
    public final static String TOPIC1022_DETAIL = "topic1022Detail";
    public final static String MASTERVIEW_DETAIL = "masterViewDetail";
    public final static String MASTERVIEW_SHOW_LIST = "masterViewShowList";
    /**
     * 查看资讯分类列表
     * @param page
     * @param uiModel
     * @return
     */
    @RequestMapping(value = "/admin/informationList", method = RequestMethod.GET)
    public String findAll(@RequestParam(value = "page", required = false,defaultValue = "1") Integer page,@RequestParam(value = "size", required = false,defaultValue = "15") Integer size,
                          Model uiModel){
        Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
        PageRequest pageRequest = null;
        if (page != null)
            pageRequest = new PageRequest(page - 1, size, sort);
        else
            pageRequest = new PageRequest(0, size, sort);
        EcGrid<Information> grid = new EcGrid<Information>();
        Page<Information> informationPage = informationService.findByIsValid(0,pageRequest);
        grid.setCurrentPage(informationPage.getNumber() + 1);
        grid.setEcList(Lists.newArrayList(informationPage));
        grid.setTotalPages(informationPage.getTotalPages());
        grid.setTotalRecords(informationPage.getTotalElements());

        uiModel.addAttribute("grid", grid);
        return INFO_LIST;
    }

    /**
     * 创建form让用户输入
     */
    @RequestMapping(value = "/admin/information/add", method = RequestMethod.GET)
    public String createForm(Model uiModel) {
        Information information = new Information();
        List<InformationCategory> informationCategoryList = informationCategoryService.findByIsValid(0);
        uiModel.addAttribute("information",information);
        uiModel.addAttribute("informationCategoryList",informationCategoryList);
        return INFO_CREATE;
    }

    /**
     * 创建form让用户编辑
     */
    @RequestMapping(value = "/admin/information/edit/{id}", method = RequestMethod.GET)
    public String editForm(@PathVariable("id") String id,Model uiModel) {
        Information information = informationService.findById(id);
        information.setFakeContent(information.getInfoContent().getContent());
        List<InformationCategory> informationCategoryList = informationCategoryService.findByIsValid(0);
        uiModel.addAttribute("information",information);
        uiModel.addAttribute("informationCategoryList",informationCategoryList);
        return INFO_EDIT;
    }

    /**
     * 提交广告信息表单,根据提交信息中的id是否有值来进行insert和update操作
     * @param uiModel
     * @param information
     * @param result
     * @param locale
     * @return
     */
    @RequestMapping(value="/admin/information/add",method = RequestMethod.POST)
    public String save(Model uiModel,@Valid @ModelAttribute("information")Information information,BindingResult result,Locale locale,HttpServletRequest request){
        String id = request.getParameter("id");
        String category_id = request.getParameter("category_id");
        if(result.hasErrors()){
            List<InformationCategory> informationCategoryList = informationCategoryService.findByIsValid(0);
            uiModel.addAttribute("message", new Message("error", result.getAllErrors().get(0).getDefaultMessage()));
            uiModel.addAttribute("informationCategoryList",informationCategoryList);
            uiModel.addAttribute("information", information);
            if(!StringUtils.isEmpty(id)){
                return INFO_EDIT;
            }else{
                return INFO_CREATE;
            }
        }
        else {
            InformationCategory informationCategory = informationCategoryService.findById(category_id);
            information.setInformationCategory(informationCategory);
            information.setIsValid(0);
            if(StringUtils.isEmpty(id)){
                informationService.save(information);
            }else {
                informationService.update(information,id);
            }
            //更新缓存
            ServletContext context = request.getSession().getServletContext();
            Sort sort = new Sort(Sort.Direction.DESC,  "createdTime");
            PageRequest pageRequest = new PageRequest(0, 5, sort);
            List<Information> list = informationService.findTop5List(informationCategory,0,pageRequest);
            if(informationCategory.getCategoryName().equals("资讯")){
                context.setAttribute("infoList",list);
            }else if (informationCategory.getCategoryName().equals("公告")){
                context.setAttribute("noticeList",list);
            }else if (informationCategory.getCategoryName().equals("大师访谈")){
                context.setAttribute("masterViewList",list);
            }
            return REDIRECT_LIST;
        }
    }

    /**
     * 查看资讯信息详情
     * @param id
     * @param uiModel
     * @return
     */
    @RequestMapping(value = "/admin/information/{id}", method = RequestMethod.GET)
    public String view(@PathVariable("id") String id, Model uiModel) {
        Information information = informationService.findById(id);
        information.setFakeContent(information.getInfoContent().getContent());
        uiModel.addAttribute("information", information);
        return INFO_VIEW;
    }

    /**
     * 资讯删除
     * @param request
     * @return
     */
    @RequestMapping(value = "/admin/information/delete", method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public Map delete(HttpServletRequest request) {
        String id = request.getParameter("id");
        Map<String,String> result = new HashMap<String, String>();
        if(StringUtils.isEmpty(id)){
            result.put("result","error");
        }else{
            informationService.delete(id);
            result.put("result","success");
        }
        return result;
    }

    /**
     * 前台查看资讯列表
     * @param page
     * @param uiModel
     * @return
     */
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public String getInfoList(@RequestParam(value = "page", required = false,defaultValue = "1") Integer page,@RequestParam(value = "size", required = false,defaultValue = "15") Integer size,
                          Model uiModel){
        Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
        PageRequest pageRequest = null;
        if (page != null)
            pageRequest = new PageRequest(page - 1, size, sort);
        else
            pageRequest = new PageRequest(0, size, sort);
        InformationCategory informationCategory = informationCategoryService.findByName("资讯");
        EcGrid<Information> grid = new EcGrid<Information>();
        Page<Information> informationPage = informationService.findByCategoryAndIsValid(informationCategory,0,pageRequest);
        grid.setCurrentPage(informationPage.getNumber() + 1);
        grid.setEcList(Lists.newArrayList(informationPage));
        grid.setTotalPages(informationPage.getTotalPages());
        grid.setTotalRecords(informationPage.getTotalElements());

        uiModel.addAttribute("grid", grid);
        return INFO_SHOW_LIST;
    }

    /**
     * 前台查看资讯信息详情
     * @param id
     * @param uiModel
     * @return
     */
    @RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
    public String getInfoDetail(@PathVariable("id") String id, Model uiModel) {
        Information information = informationService.findById(id);
        information.setFakeContent(information.getInfoContent().getContent());
        uiModel.addAttribute("information", information);
        return INFO_DETAIL;
    }

    /**
     * 前台查看公告列表
     * @param page
     * @param uiModel
     * @return
     */
    @RequestMapping(value = "/notice", method = RequestMethod.GET)
    public String getNoticeList(@RequestParam(value = "page", required = false,defaultValue = "1") Integer page,@RequestParam(value = "size", required = false,defaultValue = "15") Integer size,
                          Model uiModel){
        Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
        PageRequest pageRequest = null;
        if (page != null)
            pageRequest = new PageRequest(page - 1, size, sort);
        else
            pageRequest = new PageRequest(0, size, sort);
        InformationCategory informationCategory = informationCategoryService.findByName("公告");
        EcGrid<Information> grid = new EcGrid<Information>();
        Page<Information> informationPage = informationService.findByCategoryAndIsValid(informationCategory,0,pageRequest);
        grid.setCurrentPage(informationPage.getNumber() + 1);
        grid.setEcList(Lists.newArrayList(informationPage));
        grid.setTotalPages(informationPage.getTotalPages());
        grid.setTotalRecords(informationPage.getTotalElements());

        uiModel.addAttribute("grid", grid);
        return NOTICE_SHOW_LIST;
    }

    /**
     * 前台查看资讯信息详情
     * @param id
     * @param uiModel
     * @return
     */
    @RequestMapping(value = "/notice/{id}", method = RequestMethod.GET)
    public String getNoticeDetail(@PathVariable("id") String id, Model uiModel) {
        Information information = informationService.findById(id);
        information.setFakeContent(information.getInfoContent().getContent());
        uiModel.addAttribute("information", information);
        return NOTICE_DETAIL;
    }

    /**
     * 前台查看专题列表
     * @param page
     * @param uiModel
     * @return
     */
    @RequestMapping(value = "/topic", method = RequestMethod.GET)
    public String getTopicList(@RequestParam(value = "page", required = false,defaultValue = "1") Integer page,@RequestParam(value = "size", required = false,defaultValue = "15") Integer size,
                          Model uiModel){
        Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
        PageRequest pageRequest = null;
        if (page != null)
            pageRequest = new PageRequest(page - 1, size, sort);
        else
            pageRequest = new PageRequest(0, size, sort);
        InformationCategory informationCategory = informationCategoryService.findByName("专题");
        EcGrid<Information> grid = new EcGrid<Information>();
        Page<Information> informationPage = informationService.findByCategoryAndIsValid(informationCategory,0,pageRequest);
        grid.setCurrentPage(informationPage.getNumber() + 1);
        grid.setEcList(Lists.newArrayList(informationPage));
        grid.setTotalPages(informationPage.getTotalPages());
        grid.setTotalRecords(informationPage.getTotalElements());

        uiModel.addAttribute("grid", grid);
        return TOPIC_SHOW_LIST;
    }

    /**
     * 前台查看专题信息详情
     * @param id
     * @param uiModel
     * @return
     */
    @RequestMapping(value = "/topic/{id}", method = RequestMethod.GET)
    public String getTopicDetail(@PathVariable("id") String id, Model uiModel) {
        Information information = informationService.findById(id);
        information.setFakeContent(information.getInfoContent().getContent());
        uiModel.addAttribute("information", information);
        return TOPIC_DETAIL;
    }


    /**
     * 前台查看专题列表
     * @param page
     * @param uiModel
     * @return
     */
    @RequestMapping(value = "/topic1022", method = RequestMethod.GET)
    public String getTopic1022List(@RequestParam(value = "page", required = false,defaultValue = "1") Integer page,@RequestParam(value = "size", required = false,defaultValue = "15") Integer size,
                               Model uiModel){
        Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
        PageRequest pageRequest = null;
        if (page != null)
            pageRequest = new PageRequest(page - 1, size, sort);
        else
            pageRequest = new PageRequest(0, size, sort);
        InformationCategory informationCategory = informationCategoryService.findByName("1022专题");
        EcGrid<Information> grid = new EcGrid<Information>();
        Page<Information> informationPage = informationService.findByCategoryAndIsValid(informationCategory,0,pageRequest);
        grid.setCurrentPage(informationPage.getNumber() + 1);
        grid.setEcList(Lists.newArrayList(informationPage));
        grid.setTotalPages(informationPage.getTotalPages());
        grid.setTotalRecords(informationPage.getTotalElements());

        uiModel.addAttribute("grid", grid);
        return TOPIC1022_SHOW_LIST;
    }

    /**
     * 前台查看专题信息详情
     * @param id
     * @param uiModel
     * @return
     */
    @RequestMapping(value = "/topic1022/{id}", method = RequestMethod.GET)
    public String getTopic1022Detail(@PathVariable("id") String id, Model uiModel) {
        Information information = informationService.findById(id);
        information.setFakeContent(information.getInfoContent().getContent());
        uiModel.addAttribute("information", information);
        return TOPIC1022_DETAIL;
    }

    /**
     * 前台查看大师访谈专题列表
     * @param page
     * @param uiModel
     * @return
     */
    @RequestMapping(value = "/masterView", method = RequestMethod.GET)
    public String getMasterViewList(@RequestParam(value = "page", required = false,defaultValue = "1") Integer page,@RequestParam(value = "size", required = false,defaultValue = "15") Integer size,
                                   Model uiModel){
        Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
        PageRequest pageRequest = null;
        if (page != null)
            pageRequest = new PageRequest(page - 1, size, sort);
        else
            pageRequest = new PageRequest(0, size, sort);
        InformationCategory informationCategory = informationCategoryService.findByName("大师访谈");
        EcGrid<Information> grid = new EcGrid<Information>();
        Page<Information> informationPage = informationService.findByCategoryAndIsValid(informationCategory,0,pageRequest);
        grid.setCurrentPage(informationPage.getNumber() + 1);
        grid.setEcList(Lists.newArrayList(informationPage));
        grid.setTotalPages(informationPage.getTotalPages());
        grid.setTotalRecords(informationPage.getTotalElements());

        uiModel.addAttribute("grid", grid);
        return MASTERVIEW_SHOW_LIST;
    }

    /**
     * 前台查看大师访谈专题信息详情
     * @param id
     * @param uiModel
     * @return
     */
    @RequestMapping(value = "/masterView/{id}", method = RequestMethod.GET)
    public String getMasterViewDetail(@PathVariable("id") String id, Model uiModel) {
        Information information = informationService.findById(id);
        information.setFakeContent(information.getInfoContent().getContent());
        uiModel.addAttribute("information", information);
        return MASTERVIEW_DETAIL;
    }
}
