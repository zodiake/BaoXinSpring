package com.baosight.scc.ec.controller;

import com.baosight.scc.ec.model.*;
import com.baosight.scc.ec.security.UserContext;
import com.baosight.scc.ec.service.*;
import com.baosight.scc.ec.type.ItemState;
import com.baosight.scc.ec.type.SampleOrderState;
import com.baosight.scc.ec.utils.RedisConstant.ConstantKey;
import com.baosight.scc.ec.web.EcGrid;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by zodiake on 2014/5/28.
 */
@Controller
@RequestMapping(value = "/orderCenter")
public class SampleOrderController extends AbstractController {
    @Autowired
    private AddressService addressService;

    @Autowired
    private SampleOrderService sampleOrderService;
    @Autowired
    private UserContext userContext;
    @Autowired
    private EcUserService ecUserService;
    @PersistenceContext
    private EntityManager em;

    @Autowired
    private SampleBookService sampleBookService;

    @Autowired
    private ItemService itemService;

    private final String BUYER_VIEW = "sampleOrder_buyerView";
    private final String SELLER_VIEW = "sampleOrder_sellerView";
    private final String SELLER_VIEW_LIST = "sampleOrder_seller_list";
    private final String BUYER_VIEW_LIST = "sampleOrder_buyer_list";
    //订单提交成功
    private final static String SUBMIT_OK = "redirect:/orderCenter/sampleOrderSubmitSuccess";
    //订单提交失败
    private final static String SUBMIT_FAIL = "redirect:/orderCenter/sampleOrderSubmitFail";
    //订单提交成功
    private final static String OK_PAGE = "order_sampleOrderSubmitSuccess";
    //订单提交失败
    private final static String FAIL_PAGE = "order_sampleOrderSubmitFail";

    //订单确认页面
    private final static String ORDER_CHECKOUT = "submit_sampleBook";

    private final Logger logger = LoggerFactory.getLogger(SampleOrderController.class);

    /**
     * 进入调样单提交确认页面
     * @param request
     * @param uiModel
     * @return
     */
    @RequestMapping(value = "/sampleCheckOut/{sellerId}", method = RequestMethod.GET)
    public String checkOutOrder(@PathVariable("sellerId") String sellerId,HttpServletRequest request,Model uiModel){
        String userId=(String)request.getSession().getAttribute("id");
        Map<String,List<Item>> bookMap = sampleBookService.tidyBook(userId.trim());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if(bookMap == null || bookMap.size() == 0){
            return ORDER_CHECKOUT;
        }
        String result = "success";
        List<Item> items = bookMap.get(sellerId.trim());
        EcUser user = userContext.getCurrentUser();
        EcUser ecUser = em.find(EcUser.class, user.getId());
        Address defaultAddress = ecUser.getDefaultAddress();
        List<Address> addresses= addressService.findByUser(user);
        uiModel.addAttribute("defaultAddress",defaultAddress);
        uiModel.addAttribute("addresses",addresses);
        uiModel.addAttribute("sampleBookList",items);
        uiModel.addAttribute("goodsCount",items.size());
        uiModel.addAttribute("sellerId",sellerId);
        uiModel.addAttribute("serverTime",simpleDateFormat.format(new Date()));
        uiModel.addAttribute("result", result);

        return ORDER_CHECKOUT;
    }

    /**
     * 提交订单
     * @param request
     * @return
     */
    @RequestMapping(value = "/submitBook", method = RequestMethod.POST)
    public String submitOrder(HttpServletRequest request){
        String userId=(String)request.getSession().getAttribute("id");
        String addressId = request.getParameter("addressId");
        String sellerId = request.getParameter("sellerId").trim();
        String deliveryTime = request.getParameter("deliveryTime");
        String remark = request.getParameter("remark");
        Map<String,List<Item>> bookMap = sampleBookService.tidyBook(userId.trim());
        if(bookMap == null || bookMap.size() == 0){
            //如果session里的购物车为空则提示
//                redirectAttributes.addFlashAttribute("message", "购物车中没有商品，请添加商品到购物车");
//                return "";
            return BUYER_VIEW_LIST;
        }
        List<Item> items = bookMap.get(sellerId);
        List<SampleLine> sampleLines = new ArrayList<SampleLine>();
        SampleOrder sampleOrder = new SampleOrder(sellerId);
        sampleOrder.setSellerName(items.get(0).getCreatedBy().getName());
        for(int i = 0; i < items.size(); i ++){
            SampleLine sampleLine = new SampleLine(items.get(i));
            sampleLine.setSampleOrder(sampleOrder);
            sampleLines.add(sampleLine);
            sampleBookService.deleteItem(userId,items.get(i).getId());
        }

        SampleOrderCopyAddress address = new SampleOrderCopyAddress(addressService.findById(addressId));
        EcUser user = userContext.getCurrentUser();
        EcUser seller = new EcUser();
        seller.setId(sellerId);
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try {
            date = sdf.parse(deliveryTime);
        }catch (Exception e){
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        sampleOrder.setAddress(address);
        sampleOrder.setCreator(user);
        sampleOrder.setDeliveryTime(calendar);
        sampleOrder.setProvider(seller);
        sampleOrder.setRemark(remark);
        sampleOrder.setSampleLines(sampleLines);
        sampleOrder.setState(SampleOrderState.WAIT_GOODS_SEND);
        sampleOrderService.save(sampleOrder);
        return SUBMIT_OK;
    }

    /**
     * 订单处理成功，跳转到成功提示页面
     * @param request
     * @return
     */
    @RequestMapping(value = "/sampleOrderSubmitSuccess", method = RequestMethod.GET)
    public String orderSubmitSuccess(HttpServletRequest request){
        return OK_PAGE;
    }

    /**
     * 订单提交失败，跳转到错误提示页面
     * @param request
     * @return
     */
    @RequestMapping(value = "/sampleOrderSubmitFail", method = RequestMethod.GET)
    public String orderSubmitFail(HttpServletRequest request){
        return FAIL_PAGE;
    }


    @RequestMapping(value = "/buyerViewSampleOrder/{id}", method = RequestMethod.GET)
    public String buyerView(@PathVariable("id") String id, Model uiModel) {
        SampleOrder sampleOrder = sampleOrderService.findById(id);
        EcUser seller = ecUserService.findById(sampleOrder.getProvider().getId());
        sampleOrder.setProvider(seller);
        for(SampleLine s : sampleOrder.getSampleLines()){
            if (s.getItem() instanceof Fabric){
                s.getItem().setUrl("fabric");
            }else{
                s.getItem().setUrl("material");
            }
        }
        uiModel.addAttribute("sampleOrder", sampleOrder);
        return BUYER_VIEW;
    }

    @RequestMapping(value = "/sellerViewSampleOrder/{id}", method = RequestMethod.GET)
    public String sellerView(@PathVariable("id") String id, Model uiModel) {
        SampleOrder sampleOrder = sampleOrderService.findById(id);
        for(SampleLine s : sampleOrder.getSampleLines()){
            if (s.getItem() instanceof Fabric){
                s.getItem().setUrl("fabric");
            }else{
                s.getItem().setUrl("material");
            }
        }
        uiModel.addAttribute("sampleOrder", sampleOrder);
        return SELLER_VIEW;
    }

    @RequestMapping(value = "/sellerSamples", method = RequestMethod.GET)
    public String getAllReceivedOrders(Model uiModel, @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                       @RequestParam(value = "size", required = false, defaultValue = "15") Integer size,
                                       @RequestParam(value = "state", required = false, defaultValue = "5") int state) {
        SampleOrderState sampleOrderState =SampleOrderState.values()[0];
        if (state >= 0 && state < 5){
            sampleOrderState = SampleOrderState.values()[state];
        }
        Pageable pageable = createPageRequest(page, size);
        EcUser user = userContext.getCurrentUser();
        EcGrid<SampleOrder> grid;
        if (state == 5)
            grid = createGrid(sampleOrderService.findByProvider(user, pageable));
        else
            grid = createGrid(sampleOrderService.findByStateAndProvider(user,sampleOrderState,  pageable));
        for (SampleOrder so: grid.getEcList()) {
            for(SampleLine s :so.getSampleLines()){
                if (s.getItem() instanceof Fabric){
                    s.getItem().setUrl("fabric");
                }else{
                    s.getItem().setUrl("material");
                }
            }
        }
        uiModel.addAttribute("grid", grid);
        uiModel.addAttribute("state",state);
        return SELLER_VIEW_LIST;
    }

    @RequestMapping(value = "/buyerSamples", method = RequestMethod.GET)
    public String getAllSendOrders(Model uiModel, @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                   @RequestParam(value = "size", required = false, defaultValue = "15") Integer size,
                                   @RequestParam(value = "state", required = false, defaultValue = "5") int state) {
        SampleOrderState sampleOrderState =SampleOrderState.values()[0];
        if (state >= 0 && state < 5){
            sampleOrderState = SampleOrderState.values()[state];
        }
        Pageable pageable = createPageRequest(page, size);
        EcUser user = userContext.getCurrentUser();
        EcGrid<SampleOrder> grid;
        if (state == 5)
            grid = createGrid(sampleOrderService.findByCreatedBy(user, pageable));
        else
            grid = createGrid(sampleOrderService.findByStateAndCreatedBy(user, sampleOrderState, pageable));
        for (SampleOrder so: grid.getEcList()) {
            for(SampleLine s :so.getSampleLines()){
                if (s.getItem() instanceof Fabric){
                    s.getItem().setUrl("fabric");
                }else{
                    s.getItem().setUrl("material");
                }
            }
        }
        uiModel.addAttribute("grid", grid);
        uiModel.addAttribute("state",state);
        return BUYER_VIEW_LIST;
    }


    /**
     * 更新订单状态，前端Ajax请求
     * @param request
     * @return
     */
    @RequestMapping(value = "/sampleOrder/updateStatus", method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public Map updateStatus(HttpServletRequest request){
        String orderId = request.getParameter("id");
        String status = request.getParameter("status");
        SampleOrder sampleOrder = sampleOrderService.findById(orderId);
        sampleOrder.setState(SampleOrderState.valueOf(status));
        sampleOrderService.update(sampleOrder);
        Map resultMap = new HashMap();
        resultMap.put("result","success");
        return resultMap;
    }

    /**
     * 调样单提交前检查是否有已下架商品
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/samplePreCheck", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Map preCheckOrder(HttpServletRequest request) {
        Map resultMap = new HashMap();
        String userId = (String) request.getSession().getAttribute("id");
        String sellerId = request.getParameter("sellerId");
        String itemId = request.getParameter("itemId");
        String result = "success";
        String itemName = "";
        if(StringUtils.isEmpty(itemId)){
            Map<String,List<Item>> bookMap = sampleBookService.tidyBook(userId.trim());
            if (bookMap == null || bookMap.size() == 0) {
                result = "empty";
            }
            List<Item> items = bookMap.get(sellerId);
            if(items != null){
                for (int i=0;i<items.size();i++){
                    if(!items.get(i).getState().equals(ItemState.出售中)){
                        result = "offSale";
                        itemName = items.get(i).getName();
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
