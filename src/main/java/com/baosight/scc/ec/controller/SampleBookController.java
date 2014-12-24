package com.baosight.scc.ec.controller;

import com.baosight.scc.ec.model.*;
import com.baosight.scc.ec.service.ItemService;
import com.baosight.scc.ec.service.SampleBookService;
import com.baosight.scc.ec.type.ItemState;
import com.baosight.scc.ec.utils.RedisConstant.ConstantKey;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by Charles on 2014/7/4.
 * 调样册，所有调样册的商品保存在session中
 */
@Controller
@RequestMapping(value = "/sampleBook")
public class SampleBookController {
    @Autowired
    private ItemService itemService;
    @Autowired
    private SampleBookService sampleBookService;
    //调样册页面
    private final static String SAMPLE_BOOK = "order_sampleBook";

    private final static String VIEW_BOOK = "/sampleOrder/viewBook";

    /*
      @author Charles
      用户添加商品到调样册，前台AJax提交请求，即向redis中存入一个商品Map对象
      @param:request 前端请求商品的id和类型
      @return success:商品添加成功；error:商品添加失败
      @exception
   */
    @RequestMapping(value = "/add",  method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public Map addSampleBook(HttpServletRequest request){
        Map resultMap = new HashMap();
        String itemId = request.getParameter("id");
        String userId=(String)request.getSession().getAttribute("id");
        if (StringUtils.isEmpty(userId)){
            //如果没有登录，使用匿名key
            userId=(String)request.getSession().getAttribute(ConstantKey.ANNOY);
        }
        String result = "success";
        if(itemId == ""){
            result = "error";
        }
        if(sampleBookService.exist(userId,itemId)){
            result = "itemExist";
        }
        Item item = itemService.findByIdThroughRedis(itemId);
        item.getCreatedBy();
        //判断商品是否在可售状态
        if(!item.getState().equals(ItemState.出售中)){
            result = "offSale";
        }
        String sellerId = item.getCreatedBy().getId().trim();
        //判断买家是否调样了自己的商品
       if(userId != null){
           if(userId.equals(sellerId)){
               result = "wrongBuyer";
           }
       }
        if(result == "success"){
            sampleBookService.addItem(userId,itemId);
            SampleBook sampleBook = sampleBookService.findByUserId(userId);
            resultMap.put("sampleQuantity", sampleBook.getItem().size());
        }

        resultMap.put("result", result);
        return resultMap;
    }

    /*
      @author Charles
      查看调样册
      @param:
      @param:
      @return 跳转到调样册商品确认页面
      @exception
   */
    @RequestMapping(value = "/sample",method = RequestMethod.GET)
    public String showCart(HttpServletRequest request,Model uiModel){
        String userId=(String)request.getSession().getAttribute("id");
        if (StringUtils.isEmpty(userId)){
            //如果没有登录，使用匿名key
            userId=(String)request.getSession().getAttribute(ConstantKey.ANNOY);
        }
        Map<String,List<Item>> bookMap = sampleBookService.tidyBook(userId.trim());
        uiModel.addAttribute("bookMap",bookMap);
        return SAMPLE_BOOK;
    }

    /*
     @author Charles
     用户删除购物车里商品，前台AJax提交请求，更新redis中商品Map对象
     @param:request 商品id
     @return success:商品删除成功；error:商品添加删除失败
     @exception
  */
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Map deleteItem(HttpServletRequest request) {
        Map resultMap = new HashMap();
        String userId=(String)request.getSession().getAttribute("id");
        String itemId = request.getParameter("id");
        if (StringUtils.isEmpty(userId)){
            //如果没有登录，使用匿名key
            userId=(String)request.getSession().getAttribute(ConstantKey.ANNOY);
        }
        if (sampleBookService.exist(userId,itemId)){
            sampleBookService.deleteItem(userId,itemId);
        }
        SampleBook sampleBook = sampleBookService.findByUserId(userId);
        resultMap.put("sampleQuantity", sampleBook.getItem().size());
        resultMap.put("result", "success");
        return resultMap;
    }

    /*
     @author Charles
     页面头上现实购物车商品时的Ajax请求
     @param:request
     @return success,error
     @exception
  */
    @RequestMapping(value = "/viewBook", method = RequestMethod.GET)
    public String viewCart(HttpServletRequest request, Model uiModel) {
        String userId=(String)request.getSession().getAttribute("id");
        if (StringUtils.isEmpty(userId)){
            //如果没有登录，使用匿名key
            userId=(String)request.getSession().getAttribute(ConstantKey.ANNOY);
        }
        SampleBook sampleBook = sampleBookService.findByUserId(userId);
        if(sampleBook != null){
            List<Item> items = sampleBook.getItem();
            if(items != null){
                uiModel.addAttribute("sampleBook", items);
                uiModel.addAttribute("sampleQuantity", items.size());
            }
        }
        return VIEW_BOOK;
    }

    /*
     @author Charles
     页面加载时获取购物车中的商品数量
     @param:request 商品id
     @return success:商品删除成功；error:商品添加删除失败
     @exception
  */
    @RequestMapping(value = "/quantity", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Map getShopCart(HttpServletRequest request) {
        String userId=(String)request.getSession().getAttribute("id");
        Map resultMap = new HashMap();
        if (StringUtils.isEmpty(userId)){
            //如果没有登录，使用匿名key
            userId=(String)request.getSession().getAttribute(ConstantKey.ANNOY);
        }
        SampleBook sampleBook = sampleBookService.findByUserId(userId);
        if(sampleBook.getItem() == null){
            resultMap.put("sampleQuantity", 0);
        }else {
            resultMap.put("sampleQuantity", sampleBook.getItem().size());
        }
        resultMap.put("result", "success");

        return resultMap;
    }

}