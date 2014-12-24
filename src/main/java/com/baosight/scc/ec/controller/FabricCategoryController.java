package com.baosight.scc.ec.controller;

import com.baosight.scc.ec.model.EcUser;
import com.baosight.scc.ec.model.FabricCategory;
import com.baosight.scc.ec.model.Message;
import com.baosight.scc.ec.security.UserContext;
import com.baosight.scc.ec.service.FabricCategoryService;
import com.baosight.scc.ec.web.EcGrid;
import com.google.common.collect.Lists;
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
import java.util.List;
import java.util.Locale;

/**
 * Created by Charles on 2014/6/6.
 */
@Controller
@RequestMapping("/admin")
public class FabricCategoryController extends AbstractController{
    @Autowired
    private FabricCategoryService fabricCategoryService;
    @Autowired
    private UserContext userContext;
    @Autowired
    private MessageSource messageSource;

    public final static String FIRST_LIST = "first_list";
    public final static String FIRST_ADD = "first_add";
    public final static String FIRST_EDIT = "first_edit";
    public final static String FIRST_REDIRECT_LIST = "redirect:/admin/fabricFirstCategory";

    public final static String SECOND_LIST = "second_list";
    public final static String SECOND_ADD = "second_add";
    public final static String SECOND_EDIT = "second_edit";
    public final static String SECOND_REDIRECT_LIST = "redirect:/admin/fabricSecondCategory";

    /**
     * 查看一级分类列表
     * @param page
     * @param uiModel
     * @return
     */
    @RequestMapping(value = "/fabricFirstCategory", method = RequestMethod.GET)
    public String findFirstCategory(@RequestParam(value = "page", required = false,defaultValue = "1") Integer page,@RequestParam(value = "size", required = false,defaultValue = "15") Integer size, Model uiModel){
        Sort sort = new Sort(Sort.Direction.ASC, "sortNo");
        PageRequest pageRequest = null;
        if (page != null)
            pageRequest = new PageRequest(page - 1, size, sort);
        else
            pageRequest = new PageRequest(0, size, sort);
        EcGrid<FabricCategory> grid = new EcGrid<FabricCategory>();
        Page<FabricCategory> fabricCategoryPage = fabricCategoryService.findAllFirstCategoryByPage(pageRequest);
        grid.setCurrentPage(fabricCategoryPage.getNumber() + 1);
        grid.setEcList(Lists.newArrayList(fabricCategoryPage));
        grid.setTotalPages(fabricCategoryPage.getTotalPages());
        grid.setTotalRecords(fabricCategoryPage.getTotalElements());

        uiModel.addAttribute("grid", grid);
        uiModel.addAttribute("menu", "5");
        return FIRST_LIST;
    }

    /**
     * 创建form让用户输入
     */
    @RequestMapping(value = "/fabricFirstCategory/add", method = RequestMethod.GET)
    public String createFirstCategoryForm(Model uiModel) {
        FabricCategory firstCategory = new FabricCategory();
        List<FabricCategory> fabricCategories = fabricCategoryService.findAllFirstCategory();
        uiModel.addAttribute("firstCategory",firstCategory);
        uiModel.addAttribute("fabricCategories",fabricCategories);
        uiModel.addAttribute("menu", "5");
        return FIRST_ADD;
    }

    /**
     * 创建form让用户编辑
     */
    @RequestMapping(value = "/fabricFirstCategory/edit/{id}", method = RequestMethod.GET)
    public String editFirstCategoryForm(@PathVariable("id") String id,Model uiModel) {
        FabricCategory firstCategory = fabricCategoryService.findById(id);
        List<FabricCategory> fabricCategories = fabricCategoryService.findAllFirstCategory();
        uiModel.addAttribute("firstCategory",firstCategory);
        uiModel.addAttribute("fabricCategories",fabricCategories);
        uiModel.addAttribute("menu", "5");
        return FIRST_EDIT;
    }

    /**
     * 提交广告信息表单,根据提交信息中的id是否有值来进行insert和update操作
     * @param uiModel
     * @param firstCategory
     * @param result
     * @param locale
     * @return
     */
    @RequestMapping(value="/fabricFirstCategory/add",method = RequestMethod.POST)
    public String saveFirstCategory(Model uiModel,@Valid @ModelAttribute("firstCategory")FabricCategory firstCategory,BindingResult result,Locale locale,HttpServletRequest request){
        if(result.hasErrors()){
            uiModel.addAttribute("message", new Message("error", messageSource.getMessage("category.required", new Object[]{}, locale)));
            uiModel.addAttribute("firstCategory", firstCategory);
            uiModel.addAttribute("menu", "5");
            return FIRST_ADD;
        }
        else {
            String id = request.getParameter("id");
            EcUser createdBy = userContext.getCurrentUser();
            if(id == "" || id == null){
                firstCategory.setCreatedBy(createdBy);
                fabricCategoryService.save(firstCategory);
            }else {
                firstCategory.setId(id);
                firstCategory.setUpdatedBy(createdBy);
                fabricCategoryService.update(firstCategory);
            }
            ServletContext context = request.getSession().getServletContext();
            List<FabricCategory> list = fabricCategoryService.findFirstCategoryByIsValid(0);
            context.setAttribute("fabricCategoryList",list);
            return FIRST_REDIRECT_LIST;
        }
    }

    /***********************************************二级分类维护*****************************************
     * 查看二级分类列表
     * @param page
     * @param uiModel
     * @return
     */
    @RequestMapping(value = "/fabricSecondCategory", method = RequestMethod.GET)
    public String findSecondCategory(@RequestParam(value = "page", required = false,defaultValue = "1") Integer page,@RequestParam(value = "size", required = false,defaultValue = "15") Integer size, Model uiModel){
        Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
        PageRequest pageRequest = null;
        if (page != null)
            pageRequest = new PageRequest(page - 1, size, sort);
        else
            pageRequest = new PageRequest(0, size, sort);
        EcGrid<FabricCategory> grid = new EcGrid<FabricCategory>();
        Page<FabricCategory> fabricCategoryPage = fabricCategoryService.findAllSecondCategoryByPage(pageRequest);
        grid.setCurrentPage(fabricCategoryPage.getNumber() + 1);
        grid.setEcList(Lists.newArrayList(fabricCategoryPage));
        grid.setTotalPages(fabricCategoryPage.getTotalPages());
        grid.setTotalRecords(fabricCategoryPage.getTotalElements());

        uiModel.addAttribute("grid", grid);
        uiModel.addAttribute("menu", "6");
        return SECOND_LIST;
    }

    /**
     * 创建form让用户输入
     */
    @RequestMapping(value = "/fabricSecondCategory/add", method = RequestMethod.GET)
    public String createSecondCategoryForm(Model uiModel) {
        FabricCategory secondCategory = new FabricCategory();
        List<FabricCategory> firstCategoryList = fabricCategoryService.findFirstCategoryByIsValid(0);
        List<FabricCategory> secondCategoryList = fabricCategoryService.findAllSecondCategory();
        uiModel.addAttribute("secondCategory",secondCategory);
        uiModel.addAttribute("firstCategoryList",firstCategoryList);
        uiModel.addAttribute("secondCategoryList",secondCategoryList);
        uiModel.addAttribute("menu", "6");
        return SECOND_ADD;
    }

    /**
     * 创建form让用户编辑
     */
    @RequestMapping(value = "/fabricSecondCategory/edit/{id}", method = RequestMethod.GET)
    public String editSecondCategoryForm(@PathVariable("id") String id,Model uiModel) {
        FabricCategory secondCategory = fabricCategoryService.findById(id);
        List<FabricCategory> firstCategoryList = fabricCategoryService.findFirstCategoryByIsValid(0);
        List<FabricCategory> secondCategoryList = secondCategoryList = fabricCategoryService.findAllSecondCategory();
        uiModel.addAttribute("secondCategory",secondCategory);
        uiModel.addAttribute("firstCategoryList",firstCategoryList);
        uiModel.addAttribute("secondCategoryList",secondCategoryList);
        uiModel.addAttribute("menu", "6");
        return SECOND_EDIT;
    }

    /**
     * 从一级分类页面进入，创建form让用户编辑
     */
    @RequestMapping(value = "/fabricSecondCategory/add/{id}", method = RequestMethod.GET)
    public String addSecondCategoryForm(@PathVariable("id") String id,Model uiModel) {
        FabricCategory secondCategory = new FabricCategory();
        FabricCategory parentCategory = new FabricCategory();
        parentCategory.setId(id);
        secondCategory.setParentCategory(parentCategory);
        List<FabricCategory> firstCategoryList = fabricCategoryService.findFirstCategoryByIsValid(0);
        List<FabricCategory> secondCategoryList = fabricCategoryService.findByParentCategory(parentCategory);
        uiModel.addAttribute("secondCategory",secondCategory);
        uiModel.addAttribute("firstCategoryList",firstCategoryList);
        uiModel.addAttribute("secondCategoryList",secondCategoryList);
        uiModel.addAttribute("menu", "6");
        return SECOND_EDIT;
    }

    /**
     * 提交广告信息表单,根据提交信息中的id是否有值来进行insert和update操作
     * @param uiModel
     * @param secondCategory
     * @param result
     * @param locale
     * @return
     */
    @RequestMapping(value="/fabricSecondCategory/add",method = RequestMethod.POST)
    public String saveSecondCategory(Model uiModel,@Valid @ModelAttribute("secondCategory")FabricCategory secondCategory,BindingResult result,Locale locale,HttpServletRequest request){
        if(result.hasErrors()){
            uiModel.addAttribute("message", new Message("error", messageSource.getMessage("category.required", new Object[]{}, locale)));
            uiModel.addAttribute("secondCategory", secondCategory);
            uiModel.addAttribute("menu", "6");
            return SECOND_ADD;
        }
        else {
            String id = request.getParameter("id");
            EcUser createdBy = userContext.getCurrentUser();
            if(id == "" || id == null){
                secondCategory.setCreatedBy(createdBy);
                fabricCategoryService.save(secondCategory);
            }else {
                secondCategory.setId(id);
                secondCategory.setUpdatedBy(createdBy);
                fabricCategoryService.update(secondCategory);
            }
            ServletContext context = request.getSession().getServletContext();
            List<FabricCategory> list = fabricCategoryService.findFirstCategoryByIsValid(0);
            context.setAttribute("fabricCategoryList",list);
            return SECOND_REDIRECT_LIST;
        }
    }

}
