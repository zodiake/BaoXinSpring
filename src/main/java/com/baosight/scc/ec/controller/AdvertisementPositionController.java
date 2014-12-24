package com.baosight.scc.ec.controller;

import com.baosight.scc.ec.model.AdvertisementPosition;
import com.baosight.scc.ec.service.AdvertisementPositionService;
import com.baosight.scc.ec.web.EcGrid;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Charles on 2014/6/3.
 */
@Controller
@RequestMapping("/admin")
public class AdvertisementPositionController extends AbstractController {
    @Autowired
    private AdvertisementPositionService advertisementPositionService;

    public final static String ADP_LIST = "adp_list";
    public final static String ADP_REDIRECT = "redirect:/admin/advertisementPositionList";

    /**
     * 查看广告栏位列表
     * @param page
     * @param uiModel
     * @return
     */
    @RequestMapping(value = "/advertisementPositionList", method = RequestMethod.GET)
    public String findAll(@RequestParam(value = "page", required = false,defaultValue = "1") Integer page,@RequestParam(value = "size", required = false,defaultValue = "15") Integer size,
                          Model uiModel){
        Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
        PageRequest pageRequest = null;
        if (page != null)
            pageRequest = new PageRequest(page - 1, size, sort);
        else
            pageRequest = new PageRequest(0, size, sort);
        EcGrid<AdvertisementPosition> grid = new EcGrid<AdvertisementPosition>();
        Page<AdvertisementPosition> advertisementPositionPage = advertisementPositionService.findByIsValid(0,pageRequest);
        grid.setCurrentPage(advertisementPositionPage.getNumber() + 1);
        grid.setEcList(Lists.newArrayList(advertisementPositionPage));
        grid.setTotalPages(advertisementPositionPage.getTotalPages());
        grid.setTotalRecords(advertisementPositionPage.getTotalElements());
        AdvertisementPosition advertisementPosition = new AdvertisementPosition();
        uiModel.addAttribute("advertisementPosition",advertisementPosition);
        uiModel.addAttribute("grid", grid);
        return ADP_LIST;
    }

    /**
     * 广告栏位增加
     */
    @RequestMapping(value = "/advertisementPosition/add", method = RequestMethod.POST)
    public String save(@Valid @ModelAttribute("advertisementPosition")AdvertisementPosition advertisementPosition,HttpServletRequest request) {
        String id = request.getParameter("id");
        if(!StringUtils.isEmpty(id)){
            advertisementPosition.setId(id);
            advertisementPositionService.update(advertisementPosition);
        }else{
            advertisementPositionService.save(advertisementPosition);
        }
        return ADP_REDIRECT;
    }

    /**
     * 广告栏位查看
     * @param request
     * @return
     */
    @RequestMapping(value = "/advertisementPosition", method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public Map view(HttpServletRequest request) {
        String id = request.getParameter("id");
        Map<String,String> result = new HashMap<String, String>();
        if(StringUtils.isEmpty(id)){
            result.put("result","error");
        }else{
            AdvertisementPosition advertisementPosition = advertisementPositionService.findById(id);
            result.put("positionNo",advertisementPosition.getPositionNo());
            result.put("name",advertisementPosition.getName());
            result.put("description",advertisementPosition.getDescription());
            result.put("result","success");
        }
        return result;
    }

    /**
     * 广告栏位删除
     * @param request
     * @return
     */
    @RequestMapping(value = "/advertisementPosition/delete", method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public Map delete(HttpServletRequest request) {
        String id = request.getParameter("id");
        Map<String,String> result = new HashMap<String, String>();

        if(StringUtils.isEmpty(id)){
            result.put("result","error");
        }else{
            AdvertisementPosition advertisementPosition = advertisementPositionService.findById(id);
            advertisementPosition.setIsValid(1);
            advertisementPositionService.update(advertisementPosition);
            result.put("result","success");
        }
        return result;
    }
}
