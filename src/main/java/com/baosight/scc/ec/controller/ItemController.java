package com.baosight.scc.ec.controller;

import com.baosight.scc.ec.model.*;
import com.baosight.scc.ec.search.service.EsService;
import com.baosight.scc.ec.security.UserContext;
import com.baosight.scc.ec.service.FabricService;
import com.baosight.scc.ec.service.ItemService;
import com.baosight.scc.ec.service.MaterialService;
import com.baosight.scc.ec.service.OrderLineService;
import com.baosight.scc.ec.type.ItemState;
import com.baosight.scc.ec.type.MaterialState;
import com.baosight.scc.ec.web.EcGrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by zodiake on 2014/6/9.
 */
@Controller
public class ItemController extends AbstractController {
    @Autowired
    private UserContext userContext;
    @Autowired
    private ItemService itemService;
    @Autowired
    private OrderLineService orderLineService;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private EsService esService;
    @Autowired
    private FabricService fabricService;
    @Autowired
    private MaterialService materialService;

    private final static String FABRIC_LIST = "fabric_list";
    //辅料查询结果页面
    private final static String MATERIAL_SEARCH = "material_search";
    /*
        获取当前登入用户所发布过的所有面料信息
        @param:page 分页，希望获取第几页
        @param:uiModel spring封装对象，在页面中获取传递的对象
     */
    @RequestMapping(value = "/sellerCenter/items", method = RequestMethod.GET)
    public String listGrid(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                           @RequestParam(value = "size", required = false, defaultValue = "15") Integer size,
                           @RequestParam(value = "type", required = false, defaultValue = "4") Integer type,
                           @RequestParam(value = "itemType",required = false,defaultValue = "all") String itemType,
                           @RequestParam(value = "itemString",required = false,defaultValue = "") String itemStr,
                           Model uiModel) {
        Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
        Pageable pageable = createPageRequest(page, size, sort);
        EcUser user = userContext.getCurrentUser();
        ItemState state;
        if (type >= 0 && type <= 4){
            state = ItemState.values()[type];
        } else{
            state = ItemState.全部;
        }

        EcGrid<Item> grid=new EcGrid<Item>();

        /*if (state == ItemState.全部)
            grid = createGrid(itemService.findByCreatedBy(user, pageable));
        else
            grid = createGrid(itemService.findByCreatedByAndState(user, state, pageable));*/
        if("".equals(itemStr)){
            if("".equals(itemType) || "all".equals(itemType)){
                if (state == ItemState.全部)
                    grid = createGrid(itemService.findByCreatedBy(user, pageable));
                else
                    grid = createGrid(itemService.findByCreatedByAndState(user, state, pageable));
            }else{
                if ("fabric".equals(itemType)){
                    //状态
                    if (state == ItemState.全部)
                        grid = createGrid(fabricService.findByCreatedBy(user,pageable));
                    else
                        grid = createGrid(fabricService.findByCreatedByAndState(user,state,pageable));
                }else{
                    //状态,materialState
                    if (state == ItemState.全部)
                        grid = createGrid(materialService.findByCreatedBy(user,pageable));
                    else
                        grid = createGrid(materialService.findByCreatedByAndState(user,state,pageable));
                }
            }
        }else{
            grid = createGrid(itemService.findByCreatedByAndStateAndNameLikeOrCustomIdLike(user,itemStr,pageable));
        }

        for (Item i : grid.getEcList()) {
            if (i instanceof Fabric)
                i.setUrl("fabric");
            else
                i.setUrl("material");
        }
        uiModel.addAttribute("grid", grid);
        uiModel.addAttribute("type", type);
        uiModel.addAttribute("itemType",itemType);
        uiModel.addAttribute("itemStr",itemStr);
        return FABRIC_LIST;
    }

    @RequestMapping(value = "/sellerCenter/items/{id}", method = RequestMethod.PUT,produces = "application/json")
    @ResponseBody
    public Message updateState(@PathVariable("id") String id, Locale locale) {
        EcUser user=userContext.getCurrentUser();
        String type="";
        Item item = new Item();
        item.setId(id);

        if(!itemService.countByIdAndCreatedBy(id,user)){
            return new Message("fail",messageSource.getMessage("no_permission",new Object[]{},locale));
        }

        if (orderLineService.countUnfinishedDeal(item)) {
            return new Message("confirm", messageSource.getMessage("item_down_fail", new Object[]{}, locale));
        }

        item.setState(ItemState.下架);
        item=itemService.updateState(item);

        if(item instanceof Fabric){
            type="fabric";
            esService.deleteById("fabric",item.getId());
        }
        if(item instanceof Material){
            type="material";
            esService.deleteById("material",item.getId());
        }
        return new Message(type, messageSource.getMessage("save_success", new Object[]{}, locale));
    }

    @RequestMapping(value = "/sellerCenter/itemsContinue/{id}", method = RequestMethod.PUT,produces = "application/json")
    @ResponseBody
    public Message updateContinue(@PathVariable("id") String id, Locale locale) {
        EcUser user=userContext.getCurrentUser();
        String type="";
        Item item = new Item();
        item.setId(id);

        if(!itemService.countByIdAndCreatedBy(id,user)){
            return new Message("fail",messageSource.getMessage("no_permission",new Object[]{},locale));
        }

        item.setState(ItemState.下架);
        item=itemService.updateState(item);

        if(item instanceof Fabric){
            type="fabric";
            esService.deleteById("fabric",item.getId());
        }
        if(item instanceof Material){
            type="material";
            esService.deleteById("material",item.getId());
        }
        return new Message(type, messageSource.getMessage("save_success", new Object[]{}, locale));
    }


    @RequestMapping(value = "/sellerCenter/itemsClone/{id}", method = RequestMethod.PUT,produces = "application/json")
    @ResponseBody
    public Map itemClone(@PathVariable("id") String id, Locale locale) throws CloneNotSupportedException{
        String type="";
        String newId = "";
        Map resultMap = new HashMap();
        String resultStr = "";
        Item item = itemService.findById(id);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(item instanceof Fabric){
            type="fabric";
            Fabric fabric = fabricService.fabricClone(id);
            String image = fabric.getCoverImage()==null?"/resources/img/btn-th.png":fabric.getCoverImage();
            Calendar createdTime = fabric.getCreatedTime();
            newId = fabric.getId();
            resultStr = "<tr><td><div class='central'><img src='"+image+"'></div></td><td><a href='/fabric/"+newId+"'>"+fabric.getName()+"</a></td><td><a href='/fabric/"+newId+"'>"+fabric.getCustomId()+"</a></td><td>"+simpleDateFormat.format(createdTime.getTime())+"</td><td>面料</td><td>下架</td><td align='center'> <a type='button' class='button button-deep' href='/sellerCenter/fabricEdit?id="+newId+"'>编辑</a></td></tr>";
        }else if(item instanceof Material){
            type="material";
            Material material = materialService.materialClone(id);
            String image = material.getCoverImage()==null?"/resources/img/btn-th.png":material.getCoverImage();
            Calendar createdTime = material.getCreatedTime();
            newId = material.getId();
            resultStr = "<tr><td><div class='central'><img src='"+image+"'></div></td><td><a href='/material/"+newId+"'>"+material.getName()+"</a></td><td><a href='/material/"+newId+"'>"+material.getCustomId()+"</a></td><td>"+simpleDateFormat.format(createdTime.getTime())+"</td><td>面料</td><td>下架</td><td align='center'> <a type='button' class='button button-deep' href='/sellerCenter/materialEdit?id="+newId+"'>编辑</a></td></tr>";
        }
        resultMap.put("type",type);
        resultMap.put("resultStr",resultStr);
        return resultMap;
    }
}
