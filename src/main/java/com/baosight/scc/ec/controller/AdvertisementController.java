package com.baosight.scc.ec.controller;

import com.baosight.scc.ec.model.Advertisement;
import com.baosight.scc.ec.model.AdvertisementPosition;
import com.baosight.scc.ec.model.Message;
import com.baosight.scc.ec.service.AdvertisementPositionService;
import com.baosight.scc.ec.service.AdvertisementService;
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
 * Created by Charles on 2014/6/3.
 */
@Controller
@RequestMapping("/admin")
public class AdvertisementController extends AbstractController {
    @Autowired
    private AdvertisementService advertisementService;
    @Autowired
    private AdvertisementPositionService advertisementPositionService;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private ServletContext context;

    public final static String AD_CREATE = "ad_create";
    public final static String AD_EDIT = "ad_edit";
    public final static String AD_LIST = "ad_list";
    public final static String REDIRECT_LIST = "redirect:/admin/advertisementList";

    /**
     * 查看广告栏位列表
     *
     * @param page
     * @param uiModel
     * @return
     */
    @RequestMapping(value = "/advertisementList", method = RequestMethod.GET)
    public String findAll(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page, @RequestParam(value = "size", required = false, defaultValue = "15") Integer size, Model uiModel) {
        Sort sort = new Sort(Sort.Direction.DESC, "createdTime");

        PageRequest pageRequest = null;
        if (page != null)
            pageRequest = new PageRequest(page - 1, size, sort);
        else
            pageRequest = new PageRequest(0, size, sort);
        EcGrid<Advertisement> grid = new EcGrid<Advertisement>();
        Page<Advertisement> advertisementPage = advertisementService.findByIsValid(0, pageRequest);
        grid.setCurrentPage(advertisementPage.getNumber() + 1);
        grid.setEcList(Lists.newArrayList(advertisementPage));
        grid.setTotalPages(advertisementPage.getTotalPages());
        grid.setTotalRecords(advertisementPage.getTotalElements());

        uiModel.addAttribute("grid", grid);
        return AD_LIST;
    }

    /**
     * 创建form让用户输入
     */
    @RequestMapping(value = "/advertisement/add", method = RequestMethod.GET)
    public String createForm(Model uiModel) {
        Advertisement advertisement = new Advertisement();
        List<AdvertisementPosition> advertisementPositionList = advertisementPositionService.findByIsValid(0);
        uiModel.addAttribute("advertisement", advertisement);
        uiModel.addAttribute("advertisementPositionList", advertisementPositionList);
        return AD_CREATE;
    }

    /**
     * 创建form让用户编辑
     */
    @RequestMapping(value = "/advertisement/edit/{id}", method = RequestMethod.GET)
    public String editForm(@PathVariable("id") String id, Model uiModel) {
        Advertisement advertisement = advertisementService.findById(id);
        List<AdvertisementPosition> advertisementPositionList = advertisementPositionService.findByIsValid(0);
        uiModel.addAttribute("advertisement", advertisement);
        uiModel.addAttribute("advertisementPositionList", advertisementPositionList);
        return AD_EDIT;
    }

    /**
     * 提交广告信息表单,根据提交信息中的id是否有值来进行insert和update操作
     *
     * @param uiModel
     * @param advertisement
     * @param result
     * @param locale
     * @return
     */
    @RequestMapping(value = "/advertisement/add", method = RequestMethod.POST)
    public String save(Model uiModel, @Valid @ModelAttribute("advertisement") Advertisement advertisement, BindingResult result, Locale locale, HttpServletRequest request) {
        if (result.hasErrors()) {
            uiModel.addAttribute("message", new Message("error", messageSource.getMessage("fabric.required", new Object[]{}, locale)));
            uiModel.addAttribute("advertisement", advertisement);
            return AD_CREATE;
        } else {
            String id = request.getParameter("id");
            String position_id = request.getParameter("position_id");
            AdvertisementPosition advertisementPosition = advertisementPositionService.findById(position_id);
            advertisement.setAdvertisementPosition(advertisementPosition);
            if (StringUtils.isEmpty(id)) {
                advertisementService.save(advertisement);
            } else {
                advertisement.setIsValid(0);
                advertisementService.update(advertisement, id);
            }
            ServletContext context = request.getSession().getServletContext();
            String positionNo = advertisementPosition.getPositionNo();
            List<Advertisement> list = advertisementService.updateAdContext(advertisementPosition);
            context.setAttribute(positionNo, list);
            return REDIRECT_LIST;
        }
    }

    /**
     * 广告删除
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/advertisement/delete", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Map delete(HttpServletRequest request) {
        String id = request.getParameter("id");
        Map<String, String> result = new HashMap<String, String>();
        if (StringUtils.isEmpty(id)) {
            result.put("result", "error");
        } else {
            Advertisement advertisement = advertisementService.findById(id);
            advertisement.setIsValid(1);
            advertisementService.update(advertisement, id);
            Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
            AdvertisementPosition bannerPosition = advertisementPositionService.findByPositionNo("bannerOnHome");
            PageRequest bannerPageRequest = new PageRequest(0, 12, sort);
            List<Advertisement> bannerOnHome = advertisementService.findByAdvertisementPositionAndIsValid(bannerPosition, 0, bannerPageRequest);
            context.setAttribute("bannerOnHome", bannerOnHome);
            result.put("result", "success");
        }
        return result;
    }
}
