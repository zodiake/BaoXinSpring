package com.baosight.scc.ec.controller;

import com.baosight.scc.ec.model.*;
import com.baosight.scc.ec.security.UserContext;
import com.baosight.scc.ec.service.*;
import com.baosight.scc.ec.type.ItemState;
import com.baosight.scc.ec.type.OrderState;
import com.baosight.scc.ec.type.SampleOrderState;
import com.baosight.scc.ec.web.EcGrid;
import com.baosight.scc.ec.web.ItemJSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

/**
 * 用户控制器
 *
 * @author sam
 */
@Controller
public class EcUserController extends AbstractController {
    @Autowired
    private UserContext userContext;
    @Autowired
    private FavouriteItemsService favouriteItemsService;
    @Autowired
    private FavouriteShopService favouriteShopService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private EcUserService userService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private SampleOrderService sampleOrderService;
    @Autowired
    private OrderLineService orderLineService;
    @Autowired
    private DemandOrderService demandOrderService;
    @Autowired
    private EcUserService ecUserService;

    @Autowired
    private ExternalService externalService;
    @Autowired
    private InsideLetterService insideLetterService;
    @Autowired
    private ShopService shopService;

    final Logger logger = LoggerFactory.getLogger(EcUserController.class);

    private final static String FAVOURITE_ITEM_LIST = "favouriteItem_List"; //收藏的商品列表
    private final static String FAVOURITE_ITEM_TABLE = "favouriteItem_Table";   //收藏的商品表格
    private final static String FAVOURITE_SHOP_LIST = "favourite.listShop"; //收藏的店铺
    private final static String BUYER_PROFILE = "buyer_profile";    //买家信息维护
    private final static String SELLER_PROFILE = "seller_profile";  //卖家信息维护
    private final static String BUYER_CENTER = "buyer_center";  //买家中心
    private final static String SELLER_CENTER = "seller_center";    //卖家中心
    private final static String ATTENTION_DESIGNERS = "attentionDesigners"; //关注的设计师
    private final static String ATTENTION_BRANDS = "attentionBrands";   //关注的品牌

    /*
        list all favourite items
     */
    @RequestMapping(value = "/buyerCenter/favouritesList", method = RequestMethod.GET)
    public String listFavourites(Model uiModel,
                                 @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                 @RequestParam(value = "size", required = false, defaultValue = "15") Integer size) {
        Pageable pageable = createPageRequest(page, size);
        EcUser user = userContext.getCurrentUser();
        EcGrid<FavouriteItems> grid = createGrid(favouriteItemsService.findByUser(user, pageable));
        for (FavouriteItems item : grid.getEcList()) {
            /*if (item.getItem() instanceof Fabric){
                item.getItem().setUrl("fabric");
                item.getUser();
            }else{
                item.getItem().setUrl("material");
                item.getUser();
            }*/
            int flag = this.itemService.checkItemType(item.getItem().getId());
            String url="";
            if (flag == 0){
                url = "/fabric/"+item.getItem().getId();
            }else{
                url = "/material/"+item.getItem().getId();
            }
            item.getItem().setUrl(url);
        }
        uiModel.addAttribute("grid", grid);

        return FAVOURITE_ITEM_LIST;
    }

    /*
        table all favourite items
     */
    @RequestMapping(value = "/buyerCenter/favouritesTable", method = RequestMethod.GET)
    public String tableFavourites(Model uiModel,
                                 @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                 @RequestParam(value = "size", required = false, defaultValue = "16") Integer size) {
        Pageable pageable = createPageRequest(page, size);
        EcUser user = userContext.getCurrentUser();
        EcGrid<FavouriteItems> grid = createGrid(favouriteItemsService.findByUser(user, pageable));
        for (FavouriteItems item : grid.getEcList()) {
            /*if (item.getItem() instanceof Fabric){
                item.getItem().setUrl("fabric");
                item.getUser();
            }else{
                item.getItem().setUrl("material");
                item.getUser();
            }*/
            int flag = this.itemService.checkItemType(item.getItem().getId());
            String url="";
            if (flag == 0){
                url = "/fabric/"+item.getItem().getId();
            }else{
                url = "/material/"+item.getItem().getId();
            }
            item.getItem().setUrl(url);
        }
        uiModel.addAttribute("grid", grid);

        return FAVOURITE_ITEM_TABLE;
    }


    /*
        add a favourite item
     */
    @RequestMapping(value = "/buyerCenter/favourites/item/{id}", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Message addFavourites(@PathVariable("id") String id, Locale locale) {
        EcUser user = userContext.getCurrentUser();
        Item item = new Item();
        item.setId(id);
        if (favouriteItemsService.countByItemAndUser(item, user)) {
            return new Message("fail", messageSource.getMessage("favourite_duplicate", new Object[]{}, locale));
        } else {
            favouriteItemsService.addFavourite(user, item);
            return new Message("success", messageSource.getMessage("favourite_success", new Object[]{}, locale));
        }
    }

    /*
        delete a favourite item
     */
    @RequestMapping(value = "/buyerCenter/favourites/item/{id}", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseBody
    public Message deleteFavourites(@PathVariable("id") String id, Locale locale) {
        EcUser user = userContext.getCurrentUser();
        Item item = new Item();
        item.setId(id);
        if (favouriteItemsService.countByItemAndUser(item, user)) {
            favouriteItemsService.deleteFavourite(item, user);
            return new Message("success", messageSource.getMessage("favourite_delete_success", new Object[]{}, locale));
        } else {
            return new Message("fail", messageSource.getMessage("favourite_no_exist", new Object[]{}, locale));
        }
    }

    /*
        list all favourite shops
     */
    @RequestMapping(value = "/buyerCenter/favourite/shops", method = RequestMethod.GET)
    public String listFavouriteShop(Model uiModel,
                                    @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                    @RequestParam(value = "size", required = false, defaultValue = "15") Integer size) {
        Pageable pageable = createPageRequest(page, size);
        EcUser u = userContext.getCurrentUser();
        Page<FavouriteShops> shops = favouriteShopService.findByUser(u, pageable);
        EcGrid<FavouriteShops> grid = createGrid(shops);
        uiModel.addAttribute("grid", grid);
        return FAVOURITE_SHOP_LIST;
    }

    /*
        add a favourite shop
     */
    @RequestMapping(value = "/buyerCenter/favourite/shops/{id}", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Message addFavouriteShop(@PathVariable("id") String id, Locale locale) {
        Shop shop = new Shop();
        shop.setId(id);
        EcUser user = userContext.getCurrentUser();
        if (favouriteShopService.countByUserAndShop(user, shop)) {
            return new Message("fail", messageSource.getMessage("favourite_duplicate", new Object[]{}, locale));
        } else {
            favouriteShopService.save(user, shop);
            return new Message("fail", messageSource.getMessage("favourite_success", new Object[]{}, locale));
        }
    }

    /*
        delete a favourite shop
     */
    @RequestMapping(value = "/buyerCenter/favourites/shops/{id}", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseBody
    public Message deleteShopFavourites(@PathVariable("id") String id, Locale locale) {
        EcUser user = userContext.getCurrentUser();
        Shop shop = new Shop();
        shop.setId(id);
        FavouriteShops favouriteShops=new FavouriteShops(user,shop);
        favouriteShopService.delete(favouriteShops);
        return new Message("success", messageSource.getMessage("favourite_delete_success", new Object[]{}, locale));
    }

    /*
        ajax requesting to show hottest items in a shop
     */
    @RequestMapping(value = "/buyerCenter/favourite/shops/{id}/hottestItems", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<ItemJSON> listHottestItems(@PathVariable("id") String id) {
        EcUser u = new EcUser();
        u.setId(id);
        return itemService.findTop4ByCreatedByOrderByBidCount(u);
    }

    /**
     * 买家信息维护
    */
    @RequestMapping(value="/buyerCenter/profile",method = RequestMethod.GET)
    public String modifyUserInfo(Model uiModel){
        EcUser currentUser=userContext.getCurrentUser();
        uiModel.addAttribute("user",currentUser);
        return BUYER_PROFILE;
    }

    @RequestMapping(value="/buyerCenter/profile",params = "form",method = RequestMethod.POST)
    public String saveUserInfo(Model uiModel,@Valid @ModelAttribute("user")EcUser user,BindingResult result,Locale locale,HttpServletRequest request){
        if(result.hasErrors()){
            uiModel.addAttribute("message", new Message("error", messageSource.getMessage("profile.required", new Object[]{}, locale)));
            uiModel.addAttribute("user", user);
            return BUYER_PROFILE;
        }
        else {
            EcUser u = userContext.getCurrentUser();
            u.setName("");
            userService.save(u);
            return BUYER_PROFILE;
        }
    }

    /**
     * 卖家信息维护
     */
    @RequestMapping(value="/buyerCenter/company",method = RequestMethod.GET)
    public String modifyComInfo(Model uiModel){
        EcUser currentUser=userContext.getCurrentUser();
        uiModel.addAttribute("user",currentUser);
        return SELLER_PROFILE;
    }

    @RequestMapping(value="/buyerCenter/company",params = "form",method = RequestMethod.POST)
    public String saveComInfo(Model uiModel,@Valid @ModelAttribute("user")EcUser user,BindingResult result,Locale locale,HttpServletRequest request){
        if(result.hasErrors()){
            uiModel.addAttribute("message", new Message("error", messageSource.getMessage("profile.required", new Object[]{}, locale)));
            uiModel.addAttribute("user", user);
            return SELLER_PROFILE;
        }
        else {
            EcUser u = userContext.getCurrentUser();
            u.setName("");
            userService.save(u);
            return SELLER_PROFILE;
        }
    }

    /**
     * 买家中心
     */
    @RequestMapping(value="/buyerCenter",method = RequestMethod.GET)
    public String buyerCenter(Model uiModel){
        EcUser user = userContext.getCurrentUser();
        user = userService.findById(user.getId());
        //统计各订单状态，订单数据
        //待发货
        Long orderDFSum = orderItemService.countByStatusAndBuyer("WAIT_GOODS_SEND",user);
        //待收货
        Long OrderDSSum = orderItemService.countByStatusAndBuyer("GOODS_DELIVER", user);
        //待评价
        Long OrderDPSum = orderItemService.countOrdersByStatusAndBuyer("GOODS_RECEIVE", "SELLER_APPRAISE", user);
        //统计各调样单状态，调样单数据
        //待发货
        Long sampleOrderDFSum = sampleOrderService.countByCreatorAndStatus(user, SampleOrderState.WAIT_GOODS_SEND);
        //待收货
        Long sampleOrderDSSum = sampleOrderService.countByCreatorAndStatus(user, SampleOrderState.GOODS_DELIVER);
        //已取消(卖家已取消)
        Long sampleOrderQXSum = sampleOrderService.countByCreatorAndStatus(user, SampleOrderState.SELLER_CANCEL);
        //统计关注的供应商
        Long favouriteShopSum = favouriteShopService.countByUser(user);
        //统计关注的商品
        Long favouriteItemSum = favouriteItemsService.countByUser(user);
        //统计未读站内信
    //    int total = insideLetterService.countByReceiverUserAndLetterStatus(user,0);
        //我的订单
        int len = 4;
        int p=1,size=4;
        Pageable pageable = null;
        pageable = createPageRequest(p, size);
        List<OrderItem> orderItems = new ArrayList<OrderItem>();
        EcGrid<OrderItem> ecGrid = new EcGrid<OrderItem>();
        ecGrid = createGrid(orderItemService.findByBuyer(user,pageable));
        orderItems = ecGrid.getEcList();
        //我的调样单
        List<SampleOrder> sampleOrders = new ArrayList<SampleOrder>();
        EcGrid<SampleOrder> ecGrid1 = new EcGrid<SampleOrder>();
        ecGrid1 = createGrid(sampleOrderService.findByCreatedBy(user,pageable));
        for (SampleOrder so: ecGrid1.getEcList()) {
            for(SampleLine s :so.getSampleLines()){
                if (s.getItem() instanceof Fabric){
                    s.getItem().setUrl("fabric");
                }else{
                    s.getItem().setUrl("material");
                }
            }
        }
        sampleOrders = ecGrid1.getEcList();
        //我的求购单
        List<DemandOrder> demandOrders = demandOrderService.findDemandOrdersByCreatedBy(user,len);
        //关注的供应商
        size=2;
        pageable = createPageRequest(p, size);
        Page<FavouriteShops> page = favouriteShopService.findByUser(user,pageable);
        EcGrid<FavouriteShops> grid=new EcGrid<FavouriteShops>();
        grid=createGrid(page);
        List<FavouriteShops> favouriteShopses=grid.getEcList();
        //关注的商品
        EcGrid<FavouriteItems> grid1 = createGrid(favouriteItemsService.findByUser(user, pageable));
        List<FavouriteItems> favouriteItemses = grid1.getEcList();
        for(FavouriteItems item : favouriteItemses){
            int flag = this.itemService.checkItemType(item.getItem().getId());
            String url="";
            if (flag == 0){
                url = "/fabric/"+item.getItem().getId();
            }else{
                url = "/material/"+item.getItem().getId();
            }
            item.getItem().setUrl(url);
        }
        uiModel.addAttribute("orderDFSum",orderDFSum);
        uiModel.addAttribute("OrderDSSum",OrderDSSum);
        uiModel.addAttribute("OrderDPSum",OrderDPSum);
        uiModel.addAttribute("sampleOrderDFSum",sampleOrderDFSum);
        uiModel.addAttribute("sampleOrderDSSum",sampleOrderDSSum);
        uiModel.addAttribute("sampleOrderQXSum",sampleOrderQXSum);
        uiModel.addAttribute("favouriteShopSum",favouriteShopSum);
        uiModel.addAttribute("favouriteItemSum",favouriteItemSum);
        uiModel.addAttribute("orderItems",orderItems);
        uiModel.addAttribute("sampleOrders",sampleOrders);
        uiModel.addAttribute("demandOrders",demandOrders);
        uiModel.addAttribute("favouriteShopses",favouriteShopses);
        uiModel.addAttribute("favouriteItemses",favouriteItemses);
        uiModel.addAttribute("user",user);
    //    uiModel.addAttribute("letterTotal",total);
        return BUYER_CENTER;
    }

    /**
     * 卖家中心
     */
    @RequestMapping(value="/sellerCenter",method = RequestMethod.GET)
    public String sellerCenter(Model uiModel){
        EcUser user = userContext.getCurrentUser();
        user = ecUserService.findById(user.getId());
        //商品管理
        //草稿
        Long itemSHSum = itemService.countByCreatedByAndState(user, ItemState.草稿);
        //出售中
        Long itemCSSum = itemService.countByCreatedByAndState(user,ItemState.出售中);
        //已下架
        Long itemYXJSum = itemService.countByCreatedByAndState(user,ItemState.下架);

        //订单管理
        //代发货
        Long orderDFSum = orderItemService.countByStatusAndSeller("WAIT_GOODS_SEND",user);
        //已发货
        Long orderYFSum = orderItemService.countByStatusAndSeller("GOODS_DELIVER", user);
        //待评价
        Long orderDPSum = orderItemService.countOrdersStatusAndSeller("GOODS_RECEIVE","BUYER_APPRAISE",user);
        //调样管理
        //待发货
        Long sampleOrderDFSum = sampleOrderService.countByProviderAndStatus(user,SampleOrderState.WAIT_GOODS_SEND);
        //已发货
        Long sampleOrderYFSum = sampleOrderService.countByProviderAndStatus(user,SampleOrderState.GOODS_DELIVER);
        int p=1,size=4;
        Pageable pageable = null;
        pageable = createPageRequest(p, size);
        //商品管理列表
        EcGrid<Item> ecGrid = new EcGrid<Item>();
        ecGrid = createGrid(itemService.findByCreatedByAndState(user,ItemState.出售中,pageable));
        for (Item i : ecGrid.getEcList()) {
            if (i instanceof Fabric)
                i.setUrl("fabric");
            else
                i.setUrl("material");
        }
        //订单管理列表
        EcGrid<OrderItem> ecGrid1 = new EcGrid<OrderItem>();
        ecGrid1 = createGrid(orderItemService.findBySeller(user,pageable));
        for (OrderItem i : ecGrid1.getEcList()) {
            for (OrderLine o : i.getLines()) {
                if (o.getItem() instanceof Fabric) {
                    o.getItem().setUrl("fabric");
                } else {
                    o.getItem().setUrl("material");
                }
            }
        }
        //调样单管理
        EcGrid<SampleOrder> ecGrid2 = new EcGrid<SampleOrder>();
        ecGrid2 = createGrid(sampleOrderService.findByProvider(user,pageable));
        for (SampleOrder so: ecGrid2.getEcList()) {
            for(SampleLine s :so.getSampleLines()){
                if (s.getItem() instanceof Fabric){
                    s.getItem().setUrl("fabric");
                }else{
                    s.getItem().setUrl("material");
                }
            }
        }

        uiModel.addAttribute("itemSHSum",itemSHSum);
        uiModel.addAttribute("itemCSSum",itemCSSum);
        uiModel.addAttribute("itemYXJSum",itemYXJSum);
        uiModel.addAttribute("orderDFSum",orderDFSum);
        uiModel.addAttribute("orderYFSum",orderYFSum);
        uiModel.addAttribute("orderDPSum",orderDPSum);
        uiModel.addAttribute("sampleOrderDFSum",sampleOrderDFSum);
        uiModel.addAttribute("sampleOrderYFSum",sampleOrderYFSum);
        uiModel.addAttribute("ecGrid",ecGrid);
        uiModel.addAttribute("ecGrid1",ecGrid1);
        uiModel.addAttribute("ecGrid2",ecGrid2);
        uiModel.addAttribute("user",user);
        return SELLER_CENTER;
    }

    /**
     * 关注的设计师
     * @param uiModel
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/buyerCenter/attentionDesigner",method = RequestMethod.GET)
    public String attentionDesigner(Model uiModel,@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,@RequestParam(value = "size", required = false, defaultValue = "12") Integer size) {
        String userId = userContext.getCurrentUser().getId();
        int total = externalService.totalDesigners(userId);
        List<Designer> designerList = externalService.attentionDesigners(page,size,userId);
        List<Designer> recommendDesigners = externalService.recommendDesigners(userId);
        int totalPage = (int)Math.ceil(Double.valueOf(total/size+""));
        uiModel.addAttribute("designerList", designerList);
        uiModel.addAttribute("currentPage", page);
        uiModel.addAttribute("totalPage", totalPage);
        uiModel.addAttribute("recommendDesigners", recommendDesigners);
        return ATTENTION_DESIGNERS;
    }

    /**
     * 关注的品牌
     * @param uiModel
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/buyerCenter/attentionBrand",method = RequestMethod.GET)
    public String attentionBrand(Model uiModel,@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,@RequestParam(value = "size", required = false, defaultValue = "12") Integer size) {
        String userId = userContext.getCurrentUser().getId();
        int total = externalService.totalBrands(userId);
        List<Designer> brandList = externalService.attentionBrands(page,size,userId);
        List<Designer> recommendBrands = externalService.recommendBrands(userId);
        int totalPage = (int)Math.ceil(Double.valueOf(total/size+""));
        uiModel.addAttribute("brandList", brandList);
        uiModel.addAttribute("currentPage", page);
        uiModel.addAttribute("totalPage", totalPage);
        uiModel.addAttribute("recommendBrands", recommendBrands);
        return ATTENTION_BRANDS;
    }

    @RequestMapping(value = "/buyerCenter/unlikeDesigner",method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public Map<String,String> unlikeDesigner(HttpServletRequest request){
        String userId=(String)request.getSession().getAttribute("id");
        String designerId = request.getParameter("id");
        Map<String,String> resultMap = new HashMap<String, String>();
        int result = externalService.cancelAttentionDesigner(userId,designerId);
        if(result>0){
            resultMap.put("result","success");
        }else {
            resultMap.put("result","error");
        }
        return resultMap;
    }

    @RequestMapping(value = "/buyerCenter/unlikeBrand",method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public Map<String,String> unlikeBrand(HttpServletRequest request){
        String userId=(String)request.getSession().getAttribute("id");
        String brandId = request.getParameter("id");
        Map<String,String> resultMap = new HashMap<String, String>();
        int result = externalService.cancelAttentionBrand(userId,brandId);
        if(result>0){
            resultMap.put("result","success");
        }else {
            resultMap.put("result","error");
        }
        return resultMap;
    }

}
