package com.baosight.scc.ec.controller;

import com.baosight.scc.ec.model.*;
import com.baosight.scc.ec.service.*;
import com.baosight.scc.ec.type.ItemState;
import com.baosight.scc.ec.utils.RedisConstant.ConstantKey;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Charles on 2014/5/16.
 * 购物车，所有购物车的商品保存在session中
 */
@Controller
@RequestMapping(value = "/shoppingCart")
public class CartLineController {
    @Autowired
    private ItemService itemService;
    @Autowired
    private CartService cartService;
    //购物车页面
    private final static String CART_LIST = "order_shoppingCart";

    private final static String REDIRECT_CART = "redirect:/shoppingCart/shopCart";

    private final static String VIEW_CART = "/order/viewCart";

    //列表页面
    private final static String BUYER_ORDER_LIST = "order_buyerOrderList";

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setDisallowedFields("id");
    }

    /*
      @author Charles
      用户添加商品到购物车，前台AJax提交请求，2014-10-27更新购物车存储在redis里
      @param:request 前端请求商品的id和类型
      @return success:商品添加成功；error:商品添加失败
      @exception
   */
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Map addCartLine(HttpServletRequest request) {
        String result = "success";
        double price = 0.0;
        Map resultMap = new HashMap();
        String userId = (String) request.getSession().getAttribute("id");
        String itemId = request.getParameter("id");
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        if (StringUtils.isEmpty(userId)){
            //如果没有登录，使用匿名key
            userId=(String)request.getSession().getAttribute(ConstantKey.ANNOY);
        }
        if(StringUtils.isEmpty(itemId)){
            result =  "error";
        }
        Item item = itemService.findByIdThroughRedis(itemId);
        //判断商品是否在可售状态
        if(!item.getState().equals(ItemState.出售中)){
            result =  "offSale";
        }
        String sellerId = item.getCreatedBy().getId().trim();
        //登录用户判断买家是否购买了自己的商品
        if(userId != null){
            if(userId.equals(sellerId)){
                result =  "wrongBuyer";
            }
        }

        Map<Double, Double> priceRanges = item.getRange();
        List<Double> ranges = new ArrayList<Double>();

        for (Map.Entry<Double, Double> entry : priceRanges.entrySet()) {
            ranges.add(entry.getKey());
        }
        Collections.sort(ranges);
        for (int i = 0; i < ranges.size(); i++) {
            if (quantity < ranges.get(0)) {
                result = "notForSale";
                price = -1;
                break;
            } else if(quantity >= ranges.get(ranges.size()-1)) {
                price = priceRanges.get(ranges.get(ranges.size()-1));
                break;
            } else if (quantity <= ranges.get(i)-1) {
                price = priceRanges.get(ranges.get(i-1));
                break;
            }
        }
        if(price == 0){
            result =  "negotiable";
        }
        if(price > 0){
            if(cartService.existItem(itemId,userId)){
                //如果购物车中存在商品，则重新统计商品数量
                cartService.increaseItemCount(item,userId,quantity);
            }else{
                //购物车中没有商品，新增到购物车
                cartService.addItem(item,userId,quantity);
            }
        }
        List<CartLine> cartLines = cartService.findAllItems(userId,null);
        resultMap.put("cartQuantity", cartLines.size());
        resultMap.put("result", result);
        return resultMap;
    }


    /*
      @author Charles
      用户添加商品到购物车，前台AJax提交请求，购物车页面增减商品数量时调用此方法
      @param:request 前端请求商品的id和类型
      @return success:商品添加成功；error:商品添加失败
      @exception
   */
    @RequestMapping(value = "/modifyNum", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Map modifyCartLine(HttpServletRequest request) {
        String result = "success";
        double price = 0.0;
        int sumQuantity = 0;
        double sumPrice = 0.0;
        String sellerId = "";
        Map resultMap = new HashMap();
        String userId = (String) request.getSession().getAttribute("id");
        String itemId = request.getParameter("id");
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        if (StringUtils.isEmpty(userId)){
            //如果没有登录，使用匿名key
            userId=(String)request.getSession().getAttribute(ConstantKey.ANNOY);
        }
        if(StringUtils.isEmpty(itemId)){
            result =  "error";
        }
        if(cartService.existItem(itemId,userId)){
            //先判断购物车中增减数量后是否需要面议和在销售范围内
            CartLine cartLine = cartService.findCartById(userId,itemId);
            sellerId = cartLine.getSupplierId();
            Map<Double, Double> priceRanges = cartLine.getPriceRange();
            List<Double> ranges = new ArrayList<Double>();
            int quantityNew = cartLine.getQuantity() + quantity;
            for (Map.Entry<Double, Double> entry : priceRanges.entrySet()) {
                ranges.add(entry.getKey());
            }
            Collections.sort(ranges);
            for (int j = 0; j< ranges.size(); j++) {
                if (quantityNew < ranges.get(0)) {
                    result = "notForSale";
                    price = -1;
                    break;
                } else if(quantityNew >= ranges.get(ranges.size()-1)) {
                    price = priceRanges.get(ranges.get(ranges.size() - 1));
                    break;
                } else if (quantityNew <= ranges.get(j)-1) {
                    price = priceRanges.get(ranges.get(j - 1));
                    break;
                }
            }
            if(price == 0.00 && result.equals("success")){
                result =  "negotiable";
            }else if(price > 0){
                Item item = itemService.findByIdThroughRedis(itemId);
                cartService.increaseItemCount(item,userId,quantity);
                resultMap.put("quantityNew", quantityNew);
                resultMap.put("priceNew", price);
                resultMap.put("summaryNew", multiply(price,quantityNew));
            }
        }

        List<CartLine> cartLines = cartService.findAllItems(userId,null);
        for (int i = 0; i < cartLines.size(); i++) {
            if(sellerId.equals(cartLines.get(i).getSupplierId())){
                sumQuantity += cartLines.get(i).getQuantity();
                sumPrice += cartLines.get(i).getSummary();
            }
        }
        resultMap.put("sumQuantity", sumQuantity);
        resultMap.put("sumPrice", sumPrice);
        resultMap.put("result", result);
        resultMap.put("cartQuantity", cartLines.size());
        return resultMap;
    }


    /*
      @author Charles
      查看购物车
      @param:
      @param:
      @return 跳转到订单购物车商品确认页面
      @exception
   */
    @RequestMapping(value = "/shopCart", method = RequestMethod.GET)
    public String showCart(HttpServletRequest request, Model uiModel) {
        String userId = (String) request.getSession().getAttribute("id");
        uiModel.addAttribute("currentUserId", userId);
        if (StringUtils.isEmpty(userId)){
            //如果没有登录，使用匿名key
            userId=(String)request.getSession().getAttribute(ConstantKey.ANNOY);
        }
        Map<String, List<CartLine>> cartLineMap = cartService.tidyCart(userId);
        uiModel.addAttribute("cartLineList", cartLineMap);
        return CART_LIST;
    }

        /*
         @author Charles
         用户删除购物车里商品，前台AJax提交请求，更新redis中的购物车
         @param:request 商品id
         @return success:商品删除成功；error:商品添加删除失败
         @exception
      */
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Map deleteItem(HttpServletRequest request) {
        Map resultMap = new HashMap();
        String userId = (String) request.getSession().getAttribute("id");
        String itemId = request.getParameter("id");
        int sumQuantity = 0;
        double sumPrice = 0.0;
        String sellerId = "";
        if (StringUtils.isEmpty(userId)){
            //如果没有登录，使用匿名key
            userId=(String)request.getSession().getAttribute(ConstantKey.ANNOY);
        }
        if(cartService.existItem(itemId,userId)){
            //如果购物车中存在商品，则删除商品
            cartService.deleteItem(userId,itemId);
            resultMap.put("result", "success");
        }else{
            resultMap.put("result", "emptyCart");
        }
        Item item = itemService.findByIdThroughRedis(itemId);
        sellerId = item.getCreatedBy().getId();
        List<CartLine> cartLines = cartService.findAllItems(userId,null);
        for (int i = 0; i < cartLines.size(); i++) {
            if(sellerId.equals(cartLines.get(i).getSupplierId())){
                sumQuantity += cartLines.get(i).getQuantity();
                sumPrice += cartLines.get(i).getSummary();
            }
        }
        resultMap.put("sumQuantity", sumQuantity);
        resultMap.put("sumPrice", sumPrice);
        resultMap.put("cartQuantity", cartLines.size());
        return resultMap;
    }

        /*
         @author Charles
         用户在购物车车中手动输入数量，前台AJax提交请求，更新redis中的购物车
         @param:request 前端请求商品的id和类型
         @return success:商品添加成功；error:商品添加失败
         @exception
      */
    @RequestMapping(value = "/refresh", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Map refreshItem(HttpServletRequest request) {
        String result = "success";
        double price = 0.0;
        int sumQuantity = 0;
        double sumPrice = 0.0;
        String sellerId = "";
        Map resultMap = new HashMap();
        String userId = (String) request.getSession().getAttribute("id");
        String itemId = request.getParameter("id");
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        if (StringUtils.isEmpty(userId)){
            //如果没有登录，使用匿名key
            userId=(String)request.getSession().getAttribute(ConstantKey.ANNOY);
        }
        if(StringUtils.isEmpty(itemId)){
            result =  "error";
        }
        Item item = itemService.findByIdThroughRedis(itemId);

        Map<Double, Double> priceRanges = item.getRange();
        List<Double> ranges = new ArrayList<Double>();

        for (Map.Entry<Double, Double> entry : priceRanges.entrySet()) {
            ranges.add(entry.getKey());
        }
        Collections.sort(ranges);
        for (int i = 0; i < ranges.size(); i++) {
            if (quantity < ranges.get(0)) {
                result = "notForSale";
                price = -1;
                break;
            } else if(quantity >= ranges.get(ranges.size()-1)) {
                price = priceRanges.get(ranges.get(ranges.size()-1));
                break;
            } else if (quantity <= ranges.get(i)-1) {
                price = priceRanges.get(ranges.get(i-1));
                break;
            }
        }
        if(price == 0){
            result =  "negotiable";
        }
        if(price >= 0){
            cartService.addItem(item,userId,quantity);
            resultMap.put("priceNew", price);
            resultMap.put("summaryNew", multiply(price,quantity));
            resultMap.put("quantityNew", quantity);
        }
        sellerId = item.getCreatedBy().getId();
        List<CartLine> cartLines = cartService.findAllItems(userId,null);
        for (int i = 0; i < cartLines.size(); i++) {
            if(sellerId.equals(cartLines.get(i).getSupplierId())){
                sumQuantity += cartLines.get(i).getQuantity();
                sumPrice += cartLines.get(i).getSummary();
            }
        }
        resultMap.put("sumQuantity", sumQuantity);
        resultMap.put("sumPrice", sumPrice);
        resultMap.put("cartQuantity", cartLines.size());
        resultMap.put("result", result);
        return resultMap;
    }

    /*
     @author Charles
     将订单确认页面的商品放回购物车，前台AJax提交请求，即向session中存入一个商品Map对象
     @param:request 前端请求商品的id和类型
     @return success:商品添加成功；error:商品添加失败
     @exception
     2014-10-28 取消此功能
  */
//    @RequestMapping(value = "/reAdd", method = RequestMethod.POST, produces = "application/json")
//    @ResponseBody
//    public Map reAdd(HttpServletRequest request) {
//        Map resultMap = new HashMap();
//        try {
//            String itemId = request.getParameter("id");
//            String sellerId = request.getParameter("sellerId");
//            Map<String, List<CartLine>> cartLineMap = (Map<String, List<CartLine>>) request.getSession().getAttribute("shoppingCart");
//            if (cartLineMap == null || cartLineMap.size() == 0) {
//                //如果session里的购物车为空则创建一个新的购物车
//                cartLineMap = new HashMap<String, List<CartLine>>();
//            }
//            if (itemId == "" || sellerId == "") {
//                resultMap.put("result", "error");
//            }
//            List<CartLine> cartLineList = cartLineMap.get(sellerId);
//            if (cartLineList != null) {
//                for (int i = 0; i < cartLineList.size(); i++) {
//                    CartLine cl = cartLineList.get(i);
//                    if (itemId.equals(cl.getItemId())) {
//                        cl.setIsCheck(1);
//                        cartLineList.set(i, cl);
//                    }
//                }
//                cartLineMap.put(sellerId, cartLineList);
//            }
//            request.getSession().setAttribute("shoppingCart", cartLineMap);
//            resultMap.put("result", "success");
//        } catch (Exception e) {
//            e.printStackTrace();
//            resultMap.put("result", "error");
//        }
//        return resultMap;
//    }

    /*
     @author Charles
     页面头上现实购物车商品时的Ajax请求
     @param:request
     @return success,error
     @exception
  */
    @RequestMapping(value = "/viewCart", method = RequestMethod.GET)
    public String viewCart(HttpServletRequest request, Model uiModel) {
        String userId = (String) request.getSession().getAttribute("id");
        double summaryPrice = 0;
        int summaryQuantity = 0;
        if (StringUtils.isEmpty(userId)){
            //如果没有登录，使用匿名key
            userId=(String)request.getSession().getAttribute(ConstantKey.ANNOY);
        }
        List<CartLine> cartLines = cartService.findAllItems(userId,0);
        List<CartLine> newList = new ArrayList<CartLine>();
        if (cartLines != null) {
            for (int i = 0; i < cartLines.size(); i++) {
                summaryPrice += cartLines.get(i).getSummary();
                if(i < 6){
                    newList.add(cartLines.get(i));
                }
            }
            summaryQuantity = cartLines.size();
            uiModel.addAttribute("summaryPrice", summaryPrice);
            uiModel.addAttribute("summaryQuantity", summaryQuantity);
        }
        uiModel.addAttribute("cartLineMap", cartLines);
        return VIEW_CART;
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
        Map resultMap = new HashMap();
        int cartQuantity = 0;
        String userId = (String) request.getSession().getAttribute("id");
        if (StringUtils.isEmpty(userId)){
            //如果没有登录，使用匿名key
            userId=(String)request.getSession().getAttribute(ConstantKey.ANNOY);
        }
        List<CartLine> cartLines = cartService.findAllItems(userId,null);
        resultMap.put("result", "success");
        resultMap.put("cartQuantity", cartLines.size());
        return resultMap;
    }

    /**
     * 立即购买，检查订购量是否在面议区间
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/orderImmediately", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Map orderImmediately(HttpServletRequest request) {
        String result = "success";
        double price = 0.0;
        Map resultMap = new HashMap();
        String userId = (String) request.getSession().getAttribute("id");
        String itemId = request.getParameter("itemId");
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        if(StringUtils.isEmpty(itemId)){
            result =  "error";
        }
        Item item = itemService.findByIdThroughRedis(itemId);
        //判断商品是否在可售状态
        if(!item.getState().equals(ItemState.出售中)){
            result =  "offSale";
        }
        String sellerId = item.getCreatedBy().getId();
        //登录用户判断买家是否购买了自己的商品
        if(!StringUtils.isEmpty(userId)){
            if(userId.equals(sellerId.trim())){
                result =  "wrongBuyer";
            }
        }

        Map<Double, Double> priceRanges = item.getRange();
        List<Double> ranges = new ArrayList<Double>();

        for (Map.Entry<Double, Double> entry : priceRanges.entrySet()) {
            ranges.add(entry.getKey());
        }
        Collections.sort(ranges);
        for (int i = 0; i < ranges.size(); i++) {
            if (quantity < ranges.get(0)) {
                result = "notForSale";
                price = -1;
                break;
            } else if(quantity >= ranges.get(ranges.size()-1)) {
                price = priceRanges.get(ranges.get(ranges.size()-1));
                break;
            } else if (quantity <= ranges.get(i)-1) {
                price = priceRanges.get(ranges.get(i-1));
                break;
            }
        }
        if(price == 0){
            result =  "negotiable";
        }
        resultMap.put("result", result);
        return resultMap;
    }

    /**
     * 立即购买，提交订单页面，将商品放到购物车
     *2014-10-28 取消此功能
     * @param request
     * @return
     */
    @RequestMapping(value = "/immediatelyToCart", method = RequestMethod.GET)
    public String submitOrderImmediately(HttpServletRequest request) {
        Map<String, List<CartLine>> cartLineMap = (Map<String, List<CartLine>>) request.getSession().getAttribute("orderImmediately");
        Map<String, List<CartLine>> cartMap = (Map<String, List<CartLine>>) request.getSession().getAttribute("shoppingCart");
        if (cartLineMap == null || cartLineMap.size() == 0) {
            //如果session里的购物车为空则提示
            //                redirectAttributes.addFlashAttribute("message", "购物车中没有商品，请添加商品到购物车");
            //                return "";
            return BUYER_ORDER_LIST;
        }
        if (cartMap == null || cartMap.size() == 0) {
            //如果session里的购物车为空则提示
            //                redirectAttributes.addFlashAttribute("message", "购物车中没有商品，请添加商品到购物车");
            //                return "";
            cartMap = new HashMap<String, List<CartLine>>();
        }
        for (Map.Entry<String, List<CartLine>> entry : cartLineMap.entrySet()) {
            List<CartLine> cartLineList = cartMap.get(entry.getKey());
            List<CartLine> value = entry.getValue();
            String itemId = value.get(0).getItemId();
            int quantity = value.get(0).getQuantity();
            if (cartLineList != null) {
                int index = 0;
                for (int i = 0; i < cartLineList.size(); i++) {
                    CartLine cl = cartLineList.get(i);
                    if (itemId.equals(cl.getItemId())) {
                        Map<Double, Double> priceRanges = cl.getPriceRange();
                        List<Double> ranges = new ArrayList<Double>();
                        double price = 0.0;
                        int quantityNew = cl.getQuantity() + quantity;
                        for (Map.Entry<Double, Double> priceEntry : priceRanges.entrySet()) {
                            ranges.add(priceEntry.getKey());
                        }
                        Collections.sort(ranges);
                        for (int j = 0; j < ranges.size(); j++) {
                            if (quantityNew < ranges.get(0)) {
                                price = -1;
                                break;
                            } else if (quantityNew > ranges.get(ranges.size() - 1)) {
                                price = priceRanges.get(ranges.get(ranges.size() - 1));
                                break;
                            } else if (quantityNew <= ranges.get(j) - 1) {
                                price = priceRanges.get(ranges.get(j - 1));
                                break;
                            }
                        }
                        cl.setPrice(price);
                        cl.setQuantity(quantityNew);
                        cl.setSummary(multiply(price, quantityNew));
                        cartLineList.set(i, cl);
                        index = 1;
                    }
                }
                if (index == 0) {
                    //购物车中没有此商品时，新增一件该商品
                    cartLineList.add(value.get(0));
                }
                cartMap.put(entry.getKey(), cartLineList);
            } else {
                cartMap.put(entry.getKey(), entry.getValue());
            }
        }
        int cartQuantity = 0;
        for (Map.Entry<String, List<CartLine>> entry : cartMap.entrySet()) {
            List<CartLine> cartLines = entry.getValue();
            for (int i = 0; i < cartLines.size(); i++) {
                cartQuantity += cartLines.get(i).getQuantity();
            }
        }
        request.getSession().removeAttribute("orderImmediately");
        request.getSession().setAttribute("shoppingCart", cartMap);
        request.getSession().setAttribute("cartQuantity", cartQuantity);
        return REDIRECT_CART;
    }

    public static double multiply(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }
}
