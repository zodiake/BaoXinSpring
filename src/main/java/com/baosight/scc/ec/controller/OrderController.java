package com.baosight.scc.ec.controller;

import com.baosight.scc.ec.model.*;
import com.baosight.scc.ec.security.UserContext;
import com.baosight.scc.ec.service.*;
import com.baosight.scc.ec.type.ItemState;
import com.baosight.scc.ec.web.EcGrid;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Charles on 2014/5/15.
 * 订单
 */
@Controller
@RequestMapping(value = "/orderCenter")
public class OrderController {
    @Autowired
    private ItemService itemService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private UserContext userContext;
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private EcUserService ecUserService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private CartService cartService;
    //购物车页面
    private final static String CART_LIST = "order_shoppingCart";
    //列表页面
    private final static String BUYER_ORDER_LIST = "order_buyerOrderList";
    //列表页面
    private final static String SELLER_ORDER_LIST = "order_sellerOrderList";
    //订单确认页面
    private final static String ORDER_CHECKOUT = "order_checkOut";
    //订单提交成功
    private final static String SUBMIT_OK = "redirect:/orderCenter/orderSubmitSuccess";
    //订单提交失败
    private final static String SUBMIT_FAIL = "redirect:/orderCenter/orderSubmitFail";
    //订单提交成功
    private final static String OK_PAGE = "order_orderSubmitSuccess";
    //订单提交失败
    private final static String FAIL_PAGE = "order_orderSubmitFail";

    //买家查看订单详情
    private final static String BUYER_ORDER_VIEW = "order_buyerViewOrderDetail";

    //卖家查看订单详情
    private final static String SELLER_ORDER_VIEW = "order_sellerViewOrderDetail";

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setDisallowedFields("id");
    }

    /**
     * 进入订单确认页面
     *
     * @param request
     * @param uiModel
     * @return
     */
    @RequestMapping(value = "/checkOut", method = RequestMethod.POST)
    public String checkOutOrder(HttpServletRequest request, Model uiModel) {
        String sellerId = request.getParameter("sellerId");
        String userId = (String) request.getSession().getAttribute("id");
        Map<String, List<CartLine>> cartLineMap = cartService.tidyCart(userId);
        if (cartLineMap == null || cartLineMap.size() == 0) {
            return CART_LIST;
        }
        String result = "success";
        boolean flag = true;
        if(!StringUtils.isEmpty(userId)){
            if(userId.equals(sellerId.trim())){
                result = "wrongBuyer";
                cartLineMap.remove(sellerId);
                flag = false;
            }
        }
        if(flag){
            double summaryPrice = 0;
            int GoodsCount = 0;
            List<CartLine> cartLines = cartLineMap.get(sellerId);
            for (int i = 0; i < cartLines.size(); i++) {
                if(cartLines.get(i).getPrice()<=0){
                    result = "negotiable";
                }
                if (cartLines.get(i).getPrice()>0) {
                    summaryPrice += cartLines.get(i).getSummary();
                    GoodsCount += 1;
                }
            }
            GoodsCount = cartLines.size();
            if(GoodsCount<1){
                result = "offSale";
            }
            EcUser ecUser = em.find(EcUser.class, userId);
            Address defaultAddress = ecUser.getDefaultAddress();
            List<Address> addresses = addressService.findByUser(ecUser);
            uiModel.addAttribute("defaultAddress", defaultAddress);
            uiModel.addAttribute("addresses", addresses);
            uiModel.addAttribute("cartLineList", cartLines);
            uiModel.addAttribute("summaryPrice", summaryPrice);
            uiModel.addAttribute("GoodsCount", GoodsCount);
            uiModel.addAttribute("sellerId", sellerId);
            uiModel.addAttribute("orderImmediately",1);
        }
        uiModel.addAttribute("result", result);
        return ORDER_CHECKOUT;
    }

    /**
     * 提交订单
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/submitOrder", method = RequestMethod.POST)
    public String submitOrder(HttpServletRequest request, Model uiModel) {
        String addressId = request.getParameter("addressId");
        String orderTitle = request.getParameter("title");
        int needInvoice = Integer.parseInt(request.getParameter("needInvoice"));
        String sellerId = request.getParameter("sellerId");
        String userId = (String) request.getSession().getAttribute("id");
        Map<String, List<CartLine>> cartLineMap = cartService.tidyCart(userId);
        if (cartLineMap == null || cartLineMap.size() == 0) {
            return CART_LIST;
        }
        List<CartLine> cartLines = cartLineMap.get(sellerId.trim());
        if(cartLines != null){
            for (int i=0;i<cartLines.size();i++){
                if(!cartLines.get(i).getState().equals(ItemState.出售中)){
                    uiModel.addAttribute("cartLineList", cartLineMap);
                    return CART_LIST;
                }
            }
        }
        orderItemService.createOrder(cartLineMap, addressId, orderTitle, needInvoice, sellerId.trim());
        return SUBMIT_OK;
    }

    /**
     * 订单处理成功，跳转到成功提示页面
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/orderSubmitSuccess", method = RequestMethod.GET)
    public String orderSubmitSuccess(HttpServletRequest request) {
        return OK_PAGE;
    }

    /**
     * 订单提交失败，跳转到错误提示页面
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/orderSubmitFail", method = RequestMethod.GET)
    public String orderSubmitFail(HttpServletRequest request) {
        return FAIL_PAGE;
    }

    /**
     * 作为买家，查看我的订单列表
     *
     * @param page
     * @param uiModel
     * @return
     */
    @RequestMapping(value = "/buyerOrderList", method = RequestMethod.GET)
    public String findBuyerOrderList(Model uiModel,
                                     @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                     @RequestParam(value = "status", required = false, defaultValue = "ALL") String status) {
        EcUser user = userContext.getCurrentUser();
        Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
        PageRequest pageRequest = null;
        if (page != null)
            pageRequest = new PageRequest(page - 1, 15, sort);
        else
            pageRequest = new PageRequest(0, 15, sort);
        EcGrid<OrderItem> grid = new EcGrid<OrderItem>();
        Page<OrderItem> orderItemPage = null;
        if ("ALL".equals(status)) {
            orderItemPage = orderItemService.findByBuyer(user, pageRequest);
        } else {
            orderItemPage = orderItemService.findByBuyerAndStatus(user, status, pageRequest);
        }
        grid.setCurrentPage(orderItemPage.getNumber() + 1);
        grid.setEcList(Lists.newArrayList(orderItemPage));
        grid.setTotalPages(orderItemPage.getTotalPages());
        grid.setTotalRecords(orderItemPage.getTotalElements());
        for (OrderItem i : grid.getEcList()) {
            for (OrderLine o : i.getLines()) {
                if (o.getItem() instanceof Fabric) {
                    o.getItem().setUrl("fabric");
                } else if(o.getItem() instanceof Material){
                    o.getItem().setUrl("material");
                }else{
                    o.getItem().setUrl("item");
                }
            }

        }
        uiModel.addAttribute("grid", grid);
        uiModel.addAttribute("status", status);
        return BUYER_ORDER_LIST;
    }

    /**
     * 作为买家，查看我的订单列表
     *
     * @param id
     * @param uiModel
     * @return
     */
    @RequestMapping(value = "/buyerViewOrder/{id}", method = RequestMethod.GET)
    public String buyerViewOrder(@PathVariable("id") String id, Model uiModel) {
        OrderItem orderItem = orderItemService.findById(id);
        EcUser seller = ecUserService.findById(orderItem.getSeller().getId());
        orderItem.setSeller(seller);
        for (OrderLine o : orderItem.getLines()) {
            if (o.getItem() instanceof Fabric) {
                o.getItem().setUrl("fabric");
            } else {
                o.getItem().setUrl("material");
            }
        }
        uiModel.addAttribute("orderItem", orderItem);
        return BUYER_ORDER_VIEW;
    }

    /**
     * 作为买家，查看我的订单列表
     *
     * @param id
     * @param uiModel
     * @return
     */
    @RequestMapping(value = "/sellerViewOrder/{id}", method = RequestMethod.GET)
    public String sellerViewOrder(@PathVariable("id") String id, Model uiModel) {
        OrderItem orderItem = orderItemService.findById(id);
        for (OrderLine o : orderItem.getLines()) {
            if (o.getItem() instanceof Fabric) {
                o.getItem().setUrl("fabric");
            } else {
                o.getItem().setUrl("material");
            }
        }
        uiModel.addAttribute("orderItem", orderItem);
        return SELLER_ORDER_VIEW;
    }

    /**
     * 作为买家，查看我的订单列表,过滤已取消的订单
     *
     * @param page
     * @param uiModel
     * @return
     */
    @RequestMapping(value = "/buyerOrderListByStatus", method = RequestMethod.GET)
    public String findByBuyerAndStatusGreaterThan(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page, @RequestParam(value = "status", required = false, defaultValue = "0") String status, Model uiModel) {
        EcUser user = userContext.getCurrentUser();
        Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
        PageRequest pageRequest = null;
        if (page != null)
            pageRequest = new PageRequest(page - 1, 15, sort);
        else
            pageRequest = new PageRequest(0, 15, sort);
        EcGrid<OrderItem> grid = new EcGrid<OrderItem>();
        Page<OrderItem> orderItemPage = orderItemService.findByBuyerAndStatusNotLike(user, status, pageRequest);
        grid.setCurrentPage(orderItemPage.getNumber() + 1);
        grid.setEcList(Lists.newArrayList(orderItemPage));
        grid.setTotalPages(orderItemPage.getTotalPages());
        grid.setTotalRecords(orderItemPage.getTotalElements());
        for (OrderItem i : grid.getEcList()) {
            for (OrderLine o : i.getLines()) {
                if (o.getItem() instanceof Fabric) {
                    o.getItem().setUrl("fabric");
                } else {
                    o.getItem().setUrl("material");
                }
            }
        }
        uiModel.addAttribute("grid", grid);
        return BUYER_ORDER_LIST;
    }

    /**
     * 作为卖家，查看我的订单列表
     *
     * @param page
     * @param uiModel
     * @return
     */
    @RequestMapping(value = "/sellerOrderList", method = RequestMethod.GET)
    public String findSellerOrderList(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page, @RequestParam(value = "status", required = false, defaultValue = "ALL") String status, Model uiModel) {
        EcUser user = userContext.getCurrentUser();
        Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
        PageRequest pageRequest = null;
        if (page != null)
            pageRequest = new PageRequest(page - 1, 15, sort);
        else
            pageRequest = new PageRequest(0, 15, sort);
        EcGrid<OrderItem> grid = new EcGrid<OrderItem>();
        Page<OrderItem> orderItemPage = null;
        if ("ALL".equalsIgnoreCase(status)){
            orderItemPage = orderItemService.findBySeller(user,pageRequest);
        }else{
            orderItemPage = orderItemService.findBySellerAndStatus(user,status, pageRequest);
        }
        grid.setCurrentPage(orderItemPage.getNumber() + 1);
        grid.setEcList(Lists.newArrayList(orderItemPage));
        grid.setTotalPages(orderItemPage.getTotalPages());
        grid.setTotalRecords(orderItemPage.getTotalElements());
        for (OrderItem i : grid.getEcList()) {
            for (OrderLine o : i.getLines()) {
                if (o.getItem() instanceof Fabric) {
                    o.getItem().setUrl("fabric");
                } else {
                    o.getItem().setUrl("material");
                }
            }
        }
        uiModel.addAttribute("grid", grid);
        uiModel.addAttribute("status",status);
        return SELLER_ORDER_LIST;
    }

    /**
     * 更新订单状态，前端Ajax请求
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/order/updateStatus", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Map updateStatus(HttpServletRequest request) {
        String orderId = request.getParameter("id");
        String status = request.getParameter("status");
        Map resultMap = new HashMap();
        String result = orderItemService.updateStatus(orderId, status);
        resultMap.put("result", result);
        return resultMap;
    }

    /**
     * 立即购买，进入订单确认页面
     *
     * @param request
     * @param uiModel
     * @return
     */
    @RequestMapping(value = "/orderImmediately", method = RequestMethod.GET)
    public String orderImmediately(HttpServletRequest request, Model uiModel) {
        String itemId = request.getParameter("itemId");
        String quantityStr = request.getParameter("quantity");
        String userId = (String)request.getSession().getAttribute("id");
        boolean flag =true;
        String result = "success";
        int quantity = 0;
        if (quantityStr != "" && quantityStr != null) {
            quantity = Integer.parseInt(quantityStr);
        }
        List<CartLine> cartLineList = new ArrayList<CartLine>();
        CartLine c = cartService.findTemplateCart(itemId, quantity);
        String sellerId = c.getSupplierId();
        //判断商品是否在可售状态
        if(!c.getItem().getState().equals(ItemState.出售中)){
            result = "offSale";
            flag = false;
        }
        if(flag){
            if(c.getPrice() == 0 && result.equals("success")){
                result = "negotiable";
            }else if (result.equals("success")){
                cartLineList.add(c);
                EcUser ecUser = em.find(EcUser.class, userId);
                Address defaultAddress = ecUser.getDefaultAddress();
                List<Address> addresses = addressService.findByUser(ecUser);
                uiModel.addAttribute("defaultAddress", defaultAddress);
                uiModel.addAttribute("addresses", addresses);
                uiModel.addAttribute("cartLineList", cartLineList);
                uiModel.addAttribute("summaryPrice", c.getSummary());
                uiModel.addAttribute("GoodsCount", cartLineList.size());
                uiModel.addAttribute("sellerId", sellerId);
                uiModel.addAttribute("itemId", itemId);
                uiModel.addAttribute("quantity", quantityStr);
                uiModel.addAttribute("orderImmediately", 0);
            }
            uiModel.addAttribute("result",result);
        }else{
            uiModel.addAttribute("result",result);
        }
        return ORDER_CHECKOUT;
    }

    /**
     * 立即购买，提交订单
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/submitOrderImmediately", method = RequestMethod.POST)
    public String submitOrderImmediately(HttpServletRequest request) {
        String addressId = request.getParameter("addressId");
        String orderTitle = request.getParameter("title");
        String sellerId = request.getParameter("sellerId");
        String itemId = request.getParameter("itemId");
        String quantityStr = request.getParameter("quantity");
        int quantity = 0;
        int needInvoice = Integer.parseInt(request.getParameter("needInvoice"));
        if (quantityStr != "" && quantityStr != null) {
            quantity = Integer.parseInt(quantityStr);
        }
        CartLine cartLine = cartService.findTemplateCart(itemId,quantity);
        Map<String, List<CartLine>> cartLineMap = new HashMap<String, List<CartLine>>();
        List<CartLine> cartLineList = new ArrayList<CartLine>();
        cartLineList.add(cartLine);
        cartLineMap.put(sellerId,cartLineList);
        orderItemService.createOrder(cartLineMap, addressId, orderTitle, needInvoice,sellerId);
        return SUBMIT_OK;
    }

    public static double multiply(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 订单提交前检查是否有已下架商品，前端Ajax请求
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/preCheckOrder", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Map preCheckOrder(HttpServletRequest request) {
        Map resultMap = new HashMap();
        String userId = (String) request.getSession().getAttribute("id");
        String sellerId = request.getParameter("sellerId");
        String itemId = request.getParameter("itemId");
        String result = "success";
        String itemName = "";
        if(StringUtils.isEmpty(itemId)){
            Map<String, List<CartLine>> cartLineMap = cartService.tidyCart(userId);
            if (cartLineMap == null || cartLineMap.size() == 0) {
                result = "empty";
            }
            List<CartLine> cartLines = cartLineMap.get(sellerId);
            if(cartLines != null){
                for (int i=0;i<cartLines.size();i++){
                    if (cartLines.get(i).getPrice()<=0){
                        result = "negotiable";
                        itemName = cartLines.get(i).getTitle();
                        break;
                    }
                    if(!cartLines.get(i).getState().equals(ItemState.出售中)){
                        result = "offSale";
                        itemName = cartLines.get(i).getTitle();
                        break;
                    }
                }
            }
        }else{
            Item item = itemService.findByIdThroughRedis(itemId);
            if(item.getState().equals(ItemState.出售中)){
                result = "success";
            }else{
                result = "offSale";
                itemName = item.getName();
            }
        }

        resultMap.put("item",itemName);
        resultMap.put("result",result);
        return resultMap;
    }
}