package com.baosight.scc.ec.controller;

import com.baosight.scc.ec.model.*;
import com.baosight.scc.ec.security.UserContext;
import com.baosight.scc.ec.service.*;
import com.baosight.scc.ec.type.ItemState;
import com.baosight.scc.ec.web.CommentJson;
import com.baosight.scc.ec.web.EcGrid;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
/**
 * 店铺首页
 * @author sam
 *
 */
public class ShopController extends AbstractController{

	@Autowired
	private MaterialService materialService;
	@Autowired
	private FabricService fabricService;
	@Autowired
	private OrderLineService orderLineService;
	@Autowired
	private EcUserService ecUserService;
	@Autowired
	private FabricCategoryService fabricCategoryService;
	@Autowired
	private MaterialCategoryService materialCategoryService;
    @Autowired
    private UserContext userContext;
    @Autowired
    private FavouriteItemsService favouriteItemsService;
    @Autowired
    private  ItemService itemService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private  SellerCommentService sellerCommentService;
    @Autowired
    private DimensionRateService dimensionRateService;
    @Autowired
    private SellerCreditService sellerCreditService;
    @Autowired
    private CompositeScoreService compositeScoreService;
    @Autowired
    private ShopService shopService;
    @Autowired
    private FavouriteShopService favouriteShopService;
	
	final Logger logger = LoggerFactory.getLogger(ShopController.class);
	
	private final static String PROVIDER_MATERIAL = "providerMaterials"; //辅料供应商页面
	private final static String PROVIDER_FABRIC = "providerFabrics"; //面料供应商页面
	private final static String PROVIDER_DETAIL = "provider_detail"; //服务商详情页面
    private final static String PROVIDER_ITEMS = "providerIndex";  //供应商页面
    private final static String BUYER_COMMENTS = "buyer_comments"; //买家评价列表
    private final static String SELLER_COMMENTS = "shop/fromSeller"; //卖家评价列表
    private final static String SELLER_CREDIT = "sellerCredit"; //店铺动态评分
    private final static String SENDLIST_SELLERCOMMENT = "shop/sellerSend"; //卖家发出的评论
    private final static String PROVIDER_INDEX = "provider_index"; //供应商主页
    private final static String BUYERCENTER_FROM_SELLER = "buyerCenterFromSeller";//来自卖家评论
    private final static String BUYERCENTER_BUYER_SENDER = "shop/buyerCenterBuyerSend";//用户发出的评论


    /**
	 * 供应商店铺
	 * @author sam
	 */
    @RequestMapping(value="/shopCenter/{id}/items",method=RequestMethod.GET)
    public String findItemsByProviderType(Model model,
                                          @PathVariable("id") String id,
                                          @RequestParam(value = "type",required = false) String type,
                                          @RequestParam(value = "proName",required = false) String proName,
                                          @RequestParam(value="page",required=false,defaultValue="1") Integer page,
                                          @RequestParam(value = "size", required = false,defaultValue = "16") Integer size,
                                          @RequestParam(value="secondCategory",required=false) String secondCategory,HttpServletRequest request){
        PageRequest pageRequest = null;
        //指定排序字段
        Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
        if (page != null)
            pageRequest = new PageRequest(page - 1, size, sort);
        else
            pageRequest = new PageRequest(0, size, sort);
        //封装查询结果到grid对象中，便于在页面取数
        EcGrid<Item> grid = new EcGrid<Item>();
        //调用服务接口
        EcUser user = this.ecUserService.findById(id);
        String currentUserId=(String)request.getSession().getAttribute("id");
        boolean isAttention = false;
        List<Shop> shops = shopService.findByUser(user);
        if(currentUserId != null && currentUserId != "" && shops.size()>0){
            EcUser currentUser = ecUserService.findById(currentUserId);
            isAttention = favouriteShopService.countByUserAndShop(currentUser,shops.get(0));
        }
        user.setShops(shops);
        Page<Item> itemPage = null;
        if (type !=null && !"".equals(type)){
            if("material".equals(type)){
                MaterialCategory materialCategory=null;
                if (secondCategory != null && !"".equals(secondCategory)){
                    materialCategory=this.materialCategoryService.findById(secondCategory);
                }
                itemPage=this.itemService.findItemsByProviderAndMaterialCategory(user,materialCategory,proName,pageRequest);
            }else if ("fabric".equals(type)){
                FabricCategory fabricCategory=null;
                if (secondCategory != null && !"".equals(secondCategory)){
                    fabricCategory=this.fabricCategoryService.findById(secondCategory);
                }
                itemPage=this.itemService.findItemsByProviderAndFabricCategory(user,fabricCategory,proName,pageRequest);
            }
        }else{
            if(proName != null && !"".equals(proName)){
                String likeName = "%"+proName+"%";
                itemPage=this.itemService.findByCreatedByAndStateAndNameLike(user, ItemState.出售中,likeName,pageRequest);
            }else{
                itemPage=this.itemService.findByCreatedByAndState(user,ItemState.出售中,pageRequest);
            }
        }
    //    List<MaterialCategory> materialCategoryList=this.materialCategoryService.findAllFirstCategory();
    //    List<FabricCategory> fabricCategoryList=this.fabricCategoryService.findAllFirstCategory();
        List<MaterialCategory> materialCategoryList=materialService.findByUserId(user.getId());
        List<FabricCategory> fabricCategoryList=fabricService.findByUserId(user.getId());
        CompositeScore compositeScore = this.compositeScoreService.findByUser(user);
        grid.setEcList(Lists.newArrayList(itemPage));
        grid.setTotalPages(itemPage.getTotalPages());
        grid.setCurrentPage(itemPage.getNumber() + 1);
        grid.setTotalRecords(itemPage.getTotalElements());
        for (Item item : grid.getEcList()){
           int flag = this.itemService.checkItemType(item.getId());
            String url="";
            if (flag == 0){
                url = "/fabric/"+item.getId();
            }else{
                url = "/material/"+item.getId();
            }
            item.setUrl(url);
        }
        //最新5条记录
        List<Item> list = new ArrayList<Item>();
        Pageable pageable = createPageRequest(1,5,sort);
        Page<Item> page1 = itemService.findByCreatedByAndState(user,ItemState.出售中,pageable);
        EcGrid<Item> grid1 = new EcGrid<Item>();
        grid1 = createGrid(page1);
        for (Item item : grid1.getEcList()){
            int flag = this.itemService.checkItemType(item.getId());
            String url="";
            if (flag == 0){
                url = "/fabric/"+item.getId();
            }else{
                url = "/material/"+item.getId();
            }
            item.setUrl(url);
        }
        list=grid1.getEcList();
        model.addAttribute("list",list);
        model.addAttribute("grid", grid);
        model.addAttribute("materialCategoryList",materialCategoryList);
        model.addAttribute("fabricCategoryList",fabricCategoryList);
        model.addAttribute("user",user);
        model.addAttribute("compositeScore",compositeScore);
        model.addAttribute("secondCategory",secondCategory);
        model.addAttribute("type",type);
        model.addAttribute("isAttention",isAttention);
        model.addAttribute("currentUserId",currentUserId);
        model.addAttribute("proName",proName);
        return PROVIDER_INDEX;
    }

    /*
    暂时先不用
     */
	@RequestMapping(value="/shopCenter/{id}/materials",method=RequestMethod.GET)
	public String searchMaterials(Model uiModel,@RequestParam(value="proName",required=false) String proName,
									@PathVariable("id") String id,
									@RequestParam(value="page",required=false,defaultValue="1") Integer page,
									@RequestParam(value = "size", required = false,defaultValue = "15") Integer size,
									@RequestParam(value="secondCategory",required=false) String secondCategory){
		
		PageRequest pageRequest = null;
		//指定排序字段
		Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
		if (page != null)
			pageRequest = new PageRequest(page - 1, size, sort);
		else
		    pageRequest = new PageRequest(0, size, sort);
		//封装查询结果到grid对象中，便于在页面取数
		EcGrid<Item> grid = new EcGrid<Item>();
		//调用服务接口
        EcUser user = this.ecUserService.findById(id);
	//	Page<Material> mpage = this.materialService.searhItems(id, proName,secondCategory, pageRequest);
    //    Page<Item> mpage = this.itemService.findItemsByProviderAndMaterialCategory(user,null,pageRequest);
	//	grid.setCurrentPage(mpage.getNumber() + 1);
	//	grid.setEcList(Lists.newArrayList(mpage));
	//	grid.setTotalPages(mpage.getTotalPages());
	//	grid.setTotalRecords(mpage.getTotalElements());
		//辅料一级分类
		List<MaterialCategory> firstCategoryList = this.materialCategoryService.getMaterialFirstCategorys();
		//服务商信息
	//	EcUser user = this.ecUserService.findById(id);
		//设置返回页面数据
		uiModel.addAttribute("grid", grid);
		uiModel.addAttribute("firstCategoryList", firstCategoryList);
		uiModel.addAttribute("user", user);
		//返回页面
		return PROVIDER_MATERIAL;
	}
	
	/**
	 * 面料供应商页面，根据面料产品名称、二级分类id，搜索产品列表分页
	 * @param uiModel
	 * @param proName 产品名称
	 * @param id 供应商id
	 * @param page
	 * @param secondCategory 二级分类id
	 * @return
	 * @author sam
     * 暂时先不用
	 */
	@RequestMapping(value="/shopCenter/{id}/fabrics",method=RequestMethod.GET)
	public String searchFabrics(Model uiModel,@RequestParam(value="proName",required=false) String proName,
									@PathVariable("id") String id,
									@RequestParam(value="page",required=false,defaultValue="1") Integer page,
									@RequestParam(value = "size", required = false,defaultValue = "15") Integer size,
									@RequestParam(value="secondCategory",required=false) String secondCategory){
		PageRequest pageRequest = null;
		//指定排序字段
		Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
		if (page != null)
			pageRequest = new PageRequest(page - 1, size, sort);
		else
		    pageRequest = new PageRequest(0, size, sort);
		//调用服务层接口
		Page<Fabric> fabricPage = this.fabricService.searchItems(id, proName, secondCategory, pageRequest);
		//封装查询结果到grid对象中，便于在页面取数
		EcGrid<Fabric> grid = new EcGrid<Fabric>();
		grid.setCurrentPage(fabricPage.getNumber() + 1);
		grid.setEcList(Lists.newArrayList(fabricPage));
		grid.setTotalPages(fabricPage.getTotalPages());
		grid.setTotalRecords(fabricPage.getTotalElements());
		//面料一级分类
		List<FabricCategory> firstCategory = this.fabricCategoryService.findAllFirstCategory();
		//服务商信息
		EcUser user = this.ecUserService.findById(id);
		uiModel.addAttribute("grid", grid);
		uiModel.addAttribute("firstCategory", firstCategory);
		uiModel.addAttribute("user", user);
		return PROVIDER_FABRIC;
	}

    /*
    供应商主页
    暂时先不用
     */
    @RequestMapping(value="/shopCenter/provider/{id}",method = RequestMethod.GET)
    public String searchProviderItems(Model uiModel,@RequestParam(value = "secondCategoryId",required = false) String secondCategoryId,
                                      @PathVariable("id") String id,
                                      @RequestParam(value = "size",required = false,defaultValue = "15") Integer size,
                                      @RequestParam(value = "page",required = false,defaultValue = "1") Integer  page,
                                      @RequestParam(value = "categoryType",required = false,defaultValue = "0") Integer categoryType){
        PageRequest pageRequest = null;
        //指定排序字段
        Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
        if (page != null)
            pageRequest = new PageRequest(page - 1, size, sort);
        else
            pageRequest = new PageRequest(0, size, sort);

        Page<Item> itemPage = this.itemService.findByUserIdAndCategoryIdAndType(id,secondCategoryId,categoryType,pageRequest);
        //供应商信息
        EcUser user = this.ecUserService.findById(id);
        //面料一级分类
        List<FabricCategory> fabricCategories = this.fabricCategoryService.findAllFirstCategory();
        //辅料一级分类
        List<MaterialCategory> materialCategories = this.materialCategoryService.getMaterialFirstCategorys();

        EcGrid<Item> grid = new EcGrid<Item>();
        grid.setTotalPages(itemPage.getTotalPages());
        grid.setTotalRecords(itemPage.getTotalElements());
        grid.setEcList(Lists.newArrayList(itemPage));
        grid.setCurrentPage(itemPage.getNumber() + 1);
        uiModel.addAttribute("grid",grid);
        uiModel.addAttribute("fabricCategories",fabricCategories);
        uiModel.addAttribute("materialCategories",materialCategories);
        return PROVIDER_ITEMS;
    }

	/**
	 * 服务商信息
	 * @param uiModel
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/provider/{id}",method = RequestMethod.GET)
	public String showProviderInfo(Model uiModel,@PathVariable("id") String id,HttpServletRequest request){
        String currentUserId=(String)request.getSession().getAttribute("id");
        boolean isAttention = false;
		EcUser user  = this.ecUserService.findById(id);
        CompositeScore compositeScore=compositeScoreService.findByUser(user);
        List<Shop> shops = shopService.findByUser(user);
        if(currentUserId != null && currentUserId != "" && shops.size()>0){
            EcUser currentUser = ecUserService.findById(currentUserId);
            isAttention = favouriteShopService.countByUserAndShop(currentUser,shops.get(0));
        }
        user.setShops(shops);
		uiModel.addAttribute("user", user);
        uiModel.addAttribute("compositeScore",compositeScore);
        uiModel.addAttribute("isAttention",isAttention);
        uiModel.addAttribute("currentUserId",currentUserId);
		return PROVIDER_DETAIL;
	}

    /*
        店铺动态评分
         */
    //@RequestMapping(value = "/shopCenter/shopRateScore/{id}",method = RequestMethod.GET)
    public String shopRateScore(Model uiModel,@PathVariable("id") String id){
        EcUser user = this.ecUserService.findById(id);
        DimensionRate dimensionRate = this.dimensionRateService.findBySeller(user);
        List<SellerCredit> sellerCredits = this.sellerCreditService.findByUser(user);
        SellerCredit sellerCredit = this.sellerCreditService.findTypeTotal(user);
        uiModel.addAttribute("dimensionRate",dimensionRate);
        uiModel.addAttribute("sellerCredits",sellerCredits);
        uiModel.addAttribute("sellerCredit",sellerCredit);
        return SELLER_CREDIT;
    }
    /*
    以下为卖家中心评价管理
     */

    /*
   来自买家评价列表
    */
    @RequestMapping(value="/sellerCenter/comments",method = RequestMethod.GET)
    public String listCommentsFromBuyer(Model uiModel,
                                        @RequestParam(value="type",required = false,defaultValue = "0") Integer type,
                                        @RequestParam(value="content",required = false,defaultValue = "6") String content,
                                        @RequestParam(value = "page",required = false,defaultValue = "1") Integer page,
                                        @RequestParam(value = "size",required = false,defaultValue = "15") Integer size){
        PageRequest pageRequest = null;
        //定义排序字段
        Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
        //spring 封装page
        if (page != null)
            pageRequest = new PageRequest(page - 1, size, sort);
        else
            pageRequest = new PageRequest(0, size, sort);
        EcUser user = userContext.getCurrentUser();
        /*if (content != null){
            if ("5".equals(content)){
                content = null;
            }else{
                content = "6";
            }
        }*/
        Page<Comment> commentPage = this.commentService.findCommentsFromBuyer(user,type,content,pageRequest);;
        EcGrid<Comment> grid = new EcGrid<Comment>();
        grid.setCurrentPage(commentPage.getNumber() + 1);
        grid.setEcList(Lists.newArrayList(commentPage));
        grid.setTotalRecords(commentPage.getTotalElements());
        grid.setTotalPages(commentPage.getTotalPages());
        uiModel.addAttribute("grid",grid);
        uiModel.addAttribute("id",user.getId());
        DimensionRate dimensionRate = this.dimensionRateService.findBySeller(user);
        List<SellerCredit> sellerCredits = this.sellerCreditService.findByUser(user);
        SellerCredit sellerCredit = this.sellerCreditService.findTypeTotal(user);
        uiModel.addAttribute("dimensionRate",dimensionRate);
        uiModel.addAttribute("sellerCredits",sellerCredits);
        uiModel.addAttribute("type",type);
        uiModel.addAttribute("content",content);
        uiModel.addAttribute("sellerCredit",sellerCredit);

        return BUYER_COMMENTS;
    }

    /*
    来自卖家的评价
     */
    @RequestMapping(value = "/sellerCenter/comments/{id}/fromSeller",method = RequestMethod.GET)
    public String listCommentsFromSeller(Model uiModel,
                                         @RequestParam(value = "type",required = false,defaultValue = "0") Integer type,
                                         @RequestParam(value = "content",required = false,defaultValue = "6") String content,
                                         @PathVariable("id") String id,
                                         @RequestParam(value = "page",required = false,defaultValue = "1") Integer page,
                                         @RequestParam(value = "size",required = false,defaultValue = "15") Integer size){
        PageRequest pageRequest = null;
        //定义排序字段
        Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
        //spring 封装page
        if (page != null)
            pageRequest = new PageRequest(page - 1, size, sort);
        else
            pageRequest = new PageRequest(0, size, sort);
        EcUser user = this.ecUserService.findById(id);
       /* if ("5".equals(content)){
            content = null;
        }*/
        Page<SellerComment> sellerCommentPage = this.sellerCommentService.findSellerCommentsFromSeller(user,type,content,pageRequest);
        EcGrid<SellerComment> grid = new EcGrid<SellerComment>();
        grid.setCurrentPage(sellerCommentPage.getNumber()+1);
        grid.setTotalPages(sellerCommentPage.getTotalPages());
        grid.setEcList(Lists.newArrayList(sellerCommentPage));
        grid.setTotalRecords(sellerCommentPage.getTotalElements());
        uiModel.addAttribute("grid",grid);
        uiModel.addAttribute("type",type);
        uiModel.addAttribute("content",content);
        return SELLER_COMMENTS;
    }

    /*
    用户发出的评价
     */
    @RequestMapping(value="/sellerCenter/{id}/sellerComments",method = RequestMethod.GET)
    public String sellerSendComments(Model uiModel,
                                     @RequestParam(value = "type",required = false,defaultValue = "0") Integer type,
                                     @PathVariable("id") String id,
                                     @RequestParam(value = "page",required = false,defaultValue = "1") Integer page,
                                     @RequestParam(value = "size",required = false,defaultValue = "15") Integer size,
                                     @RequestParam(value = "content",required = false,defaultValue = "6") String content){
        PageRequest pageRequest = null;
        //定义排序字段
        Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
        //spring 封装page
        if (page != null)
            pageRequest = new PageRequest(page - 1, size, sort);
        else
            pageRequest = new PageRequest(0, size, sort);
       /* if ("5".equals(content)){
            content = null;
        }*/
        //获取当前用户信息
   //     EcUser user = userContext.getCurrentUser();
        EcUser user = ecUserService.findById(id);
        if (type == 0)
            type=null;
        Page<CommentJson> sellerCommentPage = this.sellerCommentService.findCommentsFromSellerSend(user,type,content,pageRequest);
        EcGrid<CommentJson> grid = new EcGrid<CommentJson>();
        grid.setCurrentPage(sellerCommentPage.getNumber() + 1);
        grid.setEcList(Lists.newArrayList(sellerCommentPage));
        grid.setTotalPages(sellerCommentPage.getTotalPages());
        grid.setTotalRecords(sellerCommentPage.getTotalElements());
        uiModel.addAttribute("grid",grid);
        uiModel.addAttribute("type",type);
        uiModel.addAttribute("content",content);
        return SENDLIST_SELLERCOMMENT;
    }

    /*
    以下为我的平台买家评价管理
     */

    /*
    来自卖家的评价
     */
    @RequestMapping(value = "/buyerCenter/comments/fromSeller",method = RequestMethod.GET)
    public String buyerCenterListCommentsFromSeller(Model uiModel,
                                         @RequestParam(value = "type",required = false,defaultValue = "0") Integer type,
                                         @RequestParam(value = "content",required = false,defaultValue = "6") String content,
                                         @RequestParam(value = "page",required = false,defaultValue = "1") Integer page,
                                         @RequestParam(value = "size",required = false,defaultValue = "15") Integer size){
        PageRequest pageRequest = null;
        //定义排序字段
        Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
        //spring 封装page
        if (page != null)
            pageRequest = new PageRequest(page - 1, size, sort);
        else
            pageRequest = new PageRequest(0, size, sort);
    //    EcUser user = this.ecUserService.findById(id);
        EcUser user = userContext.getCurrentUser();
       /* if ("5".equals(content)){
            content = null;
        }*/
        Page<SellerComment> sellerCommentPage = this.sellerCommentService.findSellerCommentsFromSeller(user,type,content,pageRequest);
        EcGrid<SellerComment> grid = new EcGrid<SellerComment>();
        grid.setCurrentPage(sellerCommentPage.getNumber()+1);
        grid.setTotalPages(sellerCommentPage.getTotalPages());
        grid.setEcList(Lists.newArrayList(sellerCommentPage));
        grid.setTotalRecords(sellerCommentPage.getTotalElements());
        uiModel.addAttribute("grid",grid);
        uiModel.addAttribute("type",type);
        uiModel.addAttribute("content",content);
        return BUYERCENTER_FROM_SELLER;
    }

    /*
    用户发出的评论
     */
    @RequestMapping(value="/buyerCenter/{id}/sellerComments",method = RequestMethod.GET)
    public String buyerCenterSellerSendComments(Model uiModel,
                                     @RequestParam(value = "type",required = false,defaultValue = "0") Integer type,
                                     @PathVariable("id") String id,
                                     @RequestParam(value = "page",required = false,defaultValue = "1") Integer page,
                                     @RequestParam(value = "size",required = false,defaultValue = "15") Integer size,
                                     @RequestParam(value = "content",required = false,defaultValue = "6") String content){
        PageRequest pageRequest = null;
        //定义排序字段
        Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
        //spring 封装page
        if (page != null)
            pageRequest = new PageRequest(page - 1, size, sort);
        else
            pageRequest = new PageRequest(0, size, sort);
       /* if ("5".equals(content)){
            content = null;
        }*/
        //获取当前用户信息
        //     EcUser user = userContext.getCurrentUser();
        EcUser user = ecUserService.findById(id);
        if (type == 0)
            type=null;
        Page<CommentJson> sellerCommentPage = this.sellerCommentService.findCommentsFromSellerSend(user,type,content,pageRequest);
        EcGrid<CommentJson> grid = new EcGrid<CommentJson>();
        grid.setCurrentPage(sellerCommentPage.getNumber() + 1);
        grid.setEcList(Lists.newArrayList(sellerCommentPage));
        grid.setTotalPages(sellerCommentPage.getTotalPages());
        grid.setTotalRecords(sellerCommentPage.getTotalElements());
        uiModel.addAttribute("grid",grid);
        uiModel.addAttribute("type",type);
        uiModel.addAttribute("content",content);
        return BUYERCENTER_BUYER_SENDER;
    }
}
