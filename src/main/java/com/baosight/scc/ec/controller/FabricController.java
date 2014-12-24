package com.baosight.scc.ec.controller;

import com.baosight.scc.ec.model.*;
import com.baosight.scc.ec.search.properties.CompositeQueryParam;
import com.baosight.scc.ec.search.properties.SearchParam;
import com.baosight.scc.ec.security.UserContext;
import com.baosight.scc.ec.service.*;
import com.baosight.scc.ec.type.*;
import com.baosight.scc.ec.web.EcGrid;
import com.baosight.scc.ec.web.FabricCategoryJSON;
import com.baosight.scc.ec.web.FabricSourceJSON;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.elasticsearch.search.sort.SortOrder;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by ThinkPad on 2014/5/6.
 */
@Controller
public class FabricController extends AbstractSearchController {
    @Autowired
    private FabricService fabricService;
    @Autowired
    private UserContext userContext;
    @Autowired
    private FabricMainUseTypeService useTypeService;
    @Autowired
    private FabricCategoryService categoryService;
    @Autowired
    private FabricSourceService sourceService;
    @Autowired
    private OrderLineService orderLineService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private FabricProvideTypeService fabricProvideTypeService;
    @Autowired
    private FabricTechnologyTypeService fabricTechnologyTypeService;
    @Autowired
    private EcUserService userService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private CompositeScoreService compositeScoreService;
    @Autowired
    private EcPatternService patternService;
    @Autowired
    private FavouriteFabricCategoryService favouriteFabricCategoryService;
    @Autowired
    private ShopService shopService;
    @Autowired
    private MaterialService materialService;
    @Autowired
    private FavouriteShopService favouriteShopService;

    final Logger logger = LoggerFactory.getLogger(FabricController.class);

    //列表页面
    //跳转列表
    private final static String REDIRECT_LIST = "redirect:/sellerCenter/fabrics";
    //详细页面
    private final static String FABRIC_VIEW = "fabric_detail";
    //面料交易记录列表页面
    private final static String FABRIC_ORDERS = "fabric/bidList";
    //面料交易评价列表页面
    private final static String FABRIC_COMMENTS = "fabric/commentList";

    private final static String FABRIC_SEARCH = "fabric_search";
    private final static String ERROR_404 = "redirect:/notFound";


    @RequestMapping(value = "/fabric/{id}", method = RequestMethod.GET)
    public String view(@PathVariable("id") String id, Model uiModel, HttpServletRequest request) {
        String currentUserId = (String) request.getSession().getAttribute("id");
        Cookie[] cookies = request.getCookies();
        if (cookies != null && currentUserId == null) {
            for (int i = 0; i < cookies.length; i++) {
                Cookie c = cookies[i];
                if (c.getName().equalsIgnoreCase("buap_casUser")) {
                    currentUserId = c.getValue();
                }
            }
        }
        Fabric fabric = fabricService.findById(id);
        boolean isAttention = false;
        if (fabric == null) {
            return ERROR_404;
        }
        Item item = new Item();
        item.setId(id);
        Long orderCount = orderLineService.countByItem(item);
        Long commentCount = commentService.countByItem(item);
        sortFabricRanges(fabric);
        CompositeScore score = compositeScoreService.findByUser(fabric.getCreatedBy());
        EcUser user = userService.findById(fabric.getCreatedBy().getId());
        List<Shop> shops = shopService.findByUser(user);
        user.setShops(shops);
        if (!org.apache.commons.lang.StringUtils.isEmpty(currentUserId)) {
            EcUser currentUser = userService.findById(currentUserId);
            isAttention = favouriteShopService.countByUserAndShop(currentUser, shops.get(0));
        }
        List<Map<String, Object>> similar = esService.moreLikeThis("fabric", id, 6, "title", "category", "area");
        List<FabricSearchType> moreLike = convertFromMap(similar);
        //    List<FabricCategory> categories = categoryService.findAllFirstCategory();
        //    List<MaterialCategory> materialCategories = this.materialCategoryService.findAllFirstCategory();
        List<FabricCategory> categories = fabricService.findByUserId(user.getId());
        List<MaterialCategory> materialCategories = materialService.findByUserId(user.getId());
        if (moreLike.size() < 6) {
            CompositeQueryParam compositeQueryParam = new CompositeQueryParam();
            compositeQueryParam.setLimit(6);
            compositeQueryParam.setSortField("price");
            compositeQueryParam.setSortOrder(SortOrder.DESC);
            SearchParam param = new SearchParam("title", "面料", false);
            compositeQueryParam.addQueryStringParam(param);
            List<Map<String, Object>> list = esService.search("fabric", compositeQueryParam);

            moreLike = convertFromMap(list);
        }

        uiModel.addAttribute("like", moreLike);
        uiModel.addAttribute("fabric", fabric);
        uiModel.addAttribute("categories", categories);
        uiModel.addAttribute("materialCategories", materialCategories);
        uiModel.addAttribute("orderCount", orderCount);
        uiModel.addAttribute("commentCount", commentCount);
        uiModel.addAttribute("score", score);
        uiModel.addAttribute("user", user);
        uiModel.addAttribute("isAttention", isAttention);
        uiModel.addAttribute("currentUserId", currentUserId);
        return FABRIC_VIEW;
    }

    public void sortFabricRanges(Fabric fabric) {
        fabric.setRanges(new TreeMap<Double, Double>(fabric.getRanges()));
        Map<Double, Double> ranges = fabric.getRanges();
        Map<String, Double> result = fabric.getShowRanges();
        Set<Double> set = ranges.keySet();
        Double[] keys = set.toArray(new Double[]{});
        if (keys.length == 1) {
            result.put(String.valueOf(keys[0].intValue()), ranges.get(keys[0]));
        }
        if (keys.length > 1) {
            result.put(keys[0].intValue() + "～" + (int) (keys[1] - 1), ranges.get(keys[0]));
            for (int j = 1; j < keys.length - 1; j++) {
                result.put(keys[j].intValue() + "～" + (int) (keys[j + 1] - 1), ranges.get(keys[j]));
            }
            result.put("≥" + String.valueOf(keys[keys.length - 1].intValue()), ranges.get(keys[keys.length - 1]));
        }
    }

    /*
        通过主键获取到登入用户希望修改的面料信息
        @param:id 面料主键
     */
    public Fabric editForm(String id) {
        Fabric fabric = fabricService.findByIdAndUser(id, userContext.getCurrentUser(), true);
        sortFabricRanges(fabric);
        fabric.setFakeWeight(Double.parseDouble(fabric.getFabricWidthType()));
        fabric.setFakeHeight(Double.parseDouble(fabric.getFabricHeightType()));
        if (Double.parseDouble(fabric.getFabricHeightType()) == 0)
            fabric.setFakeHeight(null);
        fabric.setTempImages(initImages(fabric.getImages()));
        return fabric;
    }


    /*
     *   创建空表单，让用户进行填写
     */
    public Fabric createForm() {
        Fabric fabric = new Fabric();
        return fabric;
    }

    /*
     *	判断该会话中的面料是否为空
     *  @param:fabric 整个flow中，登入用户希望操作的面料
     */
    public boolean checkUserFabric(Fabric fabric) {
        return fabric == null;
    }

    /*
     *   保存用户提交的面料数据
     *  @param:fabric 整个flow中，对应的fabric对象
     */
    public void saveFabric(Fabric fabric) {
        EcUser user = userContext.getCurrentUser();
        fabric.setState(ItemState.出售中);
        fabric.setCreatedBy(user);
        fabricService.save(fabric);
    }

    public void standardSave(Fabric fabric) {
        //build map
        fabric.setRanges(buildRanges(fabric.getKeys(), fabric.getValues()));

        //set createdby
        EcUser user = userContext.getCurrentUser();
        fabric.setCreatedBy(user);

        //save
        saveFabric(fabric);
    }

    public void updateFabric(Fabric fabric) {
        //build map
        fabric.setRanges(buildRanges(fabric.getKeys(), fabric.getValues()));

        EcUser user = userContext.getCurrentUser();
        fabric.setCreatedBy(user);
        fabric.setState(ItemState.出售中);

        fabricService.update(fabric);
    }

    public void tempUpdateFabric(Fabric fabric) {
        //build map
        fabric.setRanges(buildRanges(fabric.getKeys(), fabric.getValues()));

        EcUser user = userContext.getCurrentUser();
        fabric.setCreatedBy(user);
        fabric.setState(ItemState.草稿);

        fabricService.update(fabric);
    }

    /*
       暂存
     */
    public void tempSaveFabric(Fabric fabric) {
        fabric.setRanges(buildRanges(fabric.getKeys(), fabric.getValues()));

        EcUser user = userContext.getCurrentUser();
        fabric.setCreatedBy(user);
        fabric.setState(ItemState.草稿);

        fabricService.tempSave(fabric);
    }


    /*

     */
    public String successMessage() {
        return "暂存成功";
    }

    /**
     * 面料交易记录列表
     *
     * @param uiModel
     * @param page
     * @param id      面料id
     * @return
     */
    @RequestMapping(value = "/fabric/{id}/orders", method = RequestMethod.GET)
    public String showFabricOrdersByFid(Model uiModel,
                                        @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                        @RequestParam(value = "size", required = false, defaultValue = "15") Integer size,
                                        @PathVariable("id") String id) {
        PageRequest pageRequest = null;
        //定义排序字段
        Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
        //spring 封装page
        if (page != null)
            pageRequest = new PageRequest(page - 1, size, sort);
        else
            pageRequest = new PageRequest(0, size, sort);
        //获取交易记录数据
        Item item = itemService.findById(id);
        Page<OrderLine> fabricPage = orderLineService.showFabricOrdersByFid(id, pageRequest);
        EcGrid<OrderLine> grid = new EcGrid<OrderLine>();
        grid.setCurrentPage(fabricPage.getNumber() + 1);
        grid.setEcList(Lists.newArrayList(fabricPage));
        grid.setTotalPages(fabricPage.getTotalPages());
        grid.setTotalRecords(fabricPage.getTotalElements());
        DateTime time = new DateTime();
        DateTime begin = time.minusDays(30);
        Long totalBidSuccess = orderLineService.findTimeBetweenAndStatusFinish(item, "GOODS_RECEIVE", begin.toCalendar(Locale.SIMPLIFIED_CHINESE), time.toCalendar(Locale.SIMPLIFIED_CHINESE));
        Long totalBid = orderLineService.findTimeBetweenAndStatusNFinish(item, "GOODS_RECEIVE", begin.toCalendar(Locale.SIMPLIFIED_CHINESE), time.toCalendar(Locale.SIMPLIFIED_CHINESE));

        //传递数据到页面
        uiModel.addAttribute("grid", grid);
        uiModel.addAttribute("totalBid", totalBid);
        uiModel.addAttribute("totalBidSuccess", totalBidSuccess);
        uiModel.addAttribute("id", id);
        return FABRIC_ORDERS;
    }

    /**
     * 根据面料id，查询面料交易评价记录
     *
     * @param uiModel
     * @param page
     * @param id      面料id
     * @return
     */
    @RequestMapping(value = "/fabric/{id}/comments", method = RequestMethod.GET)
    public String showFabricCommentsByFid(Model uiModel,
                                          @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                          //     @RequestParam(value = "flag", required = false, defaultValue = "0") Integer flag,
                                          @RequestParam(value = "size", required = false, defaultValue = "15") Integer size,
                                          @RequestParam(value = "type", required = false, defaultValue = "0") Integer type,
                                          @PathVariable("id") String id) {
        PageRequest pageRequest = null;
        //定义排序字段
        Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
        //spring 封装page
        if (page != null)
            pageRequest = new PageRequest(page - 1, size, sort);
        else
            pageRequest = new PageRequest(0, size, sort);
        Item item = new Item();
        item.setId(id);
        uiModel.addAttribute("type", type);
        //调用服务层接口
        Page<Comment> fabricPage = commentService.findByItemAndType(item, type, pageRequest);
        /*//modifyed by sam
        Page<Comment> fabricPage = commentService.findCommentByItemAndType(item,type,pageRequest);
        //ended*/

        EcGrid<Comment> grid = new EcGrid<Comment>();
        grid.setCurrentPage(fabricPage.getNumber() + 1);
        grid.setEcList(Lists.newArrayList(fabricPage));
        grid.setTotalPages(fabricPage.getTotalPages());
        grid.setTotalRecords(fabricPage.getTotalElements());
        //30天内交易中，交易完成的个数
        DateTime time = new DateTime();
        DateTime begin = time.minusDays(30);
        Long totalBidSuccess = orderLineService.findTimeBetweenAndStatusFinish(item, "FINISH", begin.toCalendar(Locale.SIMPLIFIED_CHINESE), time.toCalendar(Locale.SIMPLIFIED_CHINESE));
        Long totalBid = orderLineService.findTimeBetweenAndStatusNFinish(item, "FINISH", begin.toCalendar(Locale.SIMPLIFIED_CHINESE), time.toCalendar(Locale.SIMPLIFIED_CHINESE));

        //    Long totalBid=orderItemService.countByReceiveTimeBetweenNowAnd30DaysBefore(DateTime.now());
        // Long totalBidSuccess=orderItemService.countByReceiveTimeBetweenNowAnd30DaysBeforeAndStatus(DateTime.now(),"FINISH");

        //好、中、差评个数
        Long goodCount = commentService.countByItemAndStatus(item, 1);
        Long ybCount = commentService.countByItemAndStatus(item, 2);
        Long wasteCount = commentService.countByItemAndStatus(item, 3);

        uiModel.addAttribute("grid", grid);
        uiModel.addAttribute("totalBid", totalBid);
        uiModel.addAttribute("totalBidSuccess", totalBidSuccess);
        uiModel.addAttribute("good", goodCount);
        uiModel.addAttribute("yb", ybCount);
        uiModel.addAttribute("waste", wasteCount);
        return FABRIC_COMMENTS;
    }

    @RequestMapping(value = "/fabricCategory/{id}/secondCategory", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<FabricCategoryJSON> findCategoryByParent(@PathVariable("id") String id) {
        FabricCategory category = new FabricCategory();
        category.setId(id);
        List<FabricCategory> categories = categoryService.findByParentCategoryAndIsValid(category, 0);
        List<FabricCategoryJSON> result = new ArrayList<FabricCategoryJSON>();
        for (FabricCategory c : categories) {
            result.add(new FabricCategoryJSON(c));
        }
        return result;
    }

    @RequestMapping(value = "/fabricFirstCategory/{id}/secondCategory", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<FabricCategoryJSON> findFabricCategory(@PathVariable("id") String id, @RequestParam(value = "uid", required = true) String uid) {
        List<FabricCategory> categories = fabricService.findByUserIdAndFirstCategory(uid, id);
        List<FabricCategoryJSON> result = new ArrayList<FabricCategoryJSON>();
        for (FabricCategory c : categories) {
            result.add(new FabricCategoryJSON(c));
        }
        return result;
    }

    @RequestMapping(value = "/fabric/{id}/sourceDetail", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<FabricSourceJSON> initDetailSource(@PathVariable("id") String id) {
        FabricSource fabricSource = new FabricSource();
        fabricSource.setId(id);
        return sourceService.findByParentAndIsValidJSON(fabricSource, 0);
    }

    @RequestMapping(value = "/fabricTechnology/{id}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<FabricTechnologyType> findTechnologyByParentId(@PathVariable("id") String id) {
        return fabricTechnologyTypeService.findByParentId(id);
    }

    /*-----------------------------------select category init data-------------------------------------------------------------------*/
    //pref
    public List<FavouriteFabricCategory> initUserPref() {
        EcUser user = userContext.getCurrentUser();
        return favouriteFabricCategoryService.findByUser(user);
    }


    public List<FabricCategory> findSameLevelCategory(Fabric fabric) {
        if (fabric != null && fabric.getCategory() != null) {
            FabricCategory category = fabric.getCategory().getParentCategory();
            return categoryService.findByParentCategoryAndIsValid(category, 0);
        }
        return null;
    }

    public List<FabricSource> findSameLevelSourceDetail(Fabric fabric) {
        if (fabric != null && fabric.getSourceDetail() != null) {
            FabricSource source = fabric.getSourceDetail().getParent();
            return sourceService.findByParent(source);
        }
        return null;
    }

    /*
        初始化面料分类
     */
    public List<FabricCategory> initFabricCategory() {
        return categoryService.findFirstCategoryByIsValid(0);
    }

    /*
        初始化原料成分
     */
    public List<FabricSource> initSource() {
        return sourceService.findAllFirstCategory();
    }

    /*
     *  初始化选择分类时的主要使用方式
     */
    @Cacheable(value = "fabricController-fabricMainUseType")
    public List<FabricMainUseType> initMainUserType() {
        return useTypeService.findAll();
    }
    /*--------------------------------end init date--------------------------------------------------*/

    /*--------------------------------init fill content date--------------------------------------------------*/
    //init季节
    @Cacheable(value = "f-controller-season")
    public List<Season> initSeason() {
        logger.debug("call season");
        return seasonService.findAll();
    }

    //ini幅宽
    @Cacheable(value = "fabricWidthType")
    public List<FabricWidthType> initFabricWidthType() {
        logger.debug("call widthtype");
        return convertListMapToListWidthType();
    }


    //init染整工艺
    public List<FabricTechnologyType> initFabricTechnology() {
        List<FabricTechnologyType> types = new ArrayList<FabricTechnologyType>(fabricTechnologyTypeService.findFirstCategory());
        FabricTechnologyType type = new FabricTechnologyType();
        type.setId(null);
        type.setName("--请选择--");
        types.add(0, type);
        return types;
    }

    public List<FabricTechnologyType> findSameLevelTechnology(Fabric fabric) {
        String id = fabric.getFabricSecondTechnologyId();
        logger.debug("fabric id" + id);
        logger.debug("call technolotyType");
        if (!StringUtils.isEmpty(id)) {
            return fabricTechnologyTypeService.findSameLevelTechnology(id);
        }
        return null;
    }


    //ini计量单位
    @Cacheable(value = "unit")
    public List<Unit> initUnit() {
        logger.debug("call unit");
        return convertListMapToUnit();
    }

    //供货方式
    @Cacheable(value = "f-controller-unit")
    public Collection<FabricProvideType> initProvideType() {
        logger.debug("call fabricprovidetype");
        return fabricProvideTypeService.findAll();
    }

    //图案
    @Cacheable(value = "f-controller-pattern")
    public Collection<EcPattern> initPattern() {
        logger.debug("call pattern");
        return patternService.findFirstCategory();
    }
    /*--------------------------------end init date--------------------------------------------------*/

    @RequestMapping(value = "/admin/fabric/{id}", method = RequestMethod.GET)
    public String auditFabric(@PathVariable("id") String id, Model uiModel) {
        Fabric fabric = fabricService.findById(id);
        List<FabricCategory> categories = categoryService.findAllFirstCategory();
        sortFabricRanges(fabric);
        uiModel.addAttribute("fabric", fabric);
        uiModel.addAttribute("categories", categories);
        return FABRIC_VIEW;
    }

    public boolean confirmType(Fabric fabric) {
        return fabric.getState() == ItemState.下架 || fabric.getState() == ItemState.草稿;
    }

    @RequestMapping(value = "/search/fabric", method = RequestMethod.GET)
    public String searchFabric(@ModelAttribute("search") FabricSearch search, Model uiModel) {
        CompositeQueryParam param = convertSearchParam(search);

        Long count = esService.count("fabric", param);
        Long pageCount = count % PAGESIZE == 0 ? count / PAGESIZE : count / PAGESIZE + 1;

        if (search.getCurrentPage() <= 0)
            search.setCurrentPage(1);
        if (search.getCurrentPage() > pageCount)
            search.setCurrentPage(pageCount.intValue());

        List<Map<String, Object>> list = esService.search("fabric", convertSearchParam(search));

        List<FabricSearchType> fabrics = convertFromMap(list);


        List<FabricSearchType> suggestions = getSearchSuggestions("fabric");

        if (suggestions == null) logger.info("suggestions is null please check");
        if (list == null) logger.info("list is null please check");

        if (!StringUtils.isEmpty(search.getKeyWord()) && list != null && list.size() > 0)
            explainKeyword(search.getKeyWord());

        uiModel.addAttribute("lists", fabrics);

        List<FabricCategory> categories = categoryService.findAllFirstCategory();
        List<Season> seasons = seasonService.findAll();

        List<FabricTechnologyType> technology = fabricTechnologyTypeService.findFirstCategory();

        List<FabricMainUseType> types = useTypeService.findAll();

        List<FabricSource> sources = sourceService.findAllFirstCategory();
        List<EcPattern> pattern = patternService.findFirstCategory();

        List<Hierarchy> hierarchies = colorService.findAll();

        Set<String> hotKeys = template.opsForZSet().reverseRange(redis_fabric, 0, 9);

        uiModel.addAttribute("firstCategory", categories);
        uiModel.addAttribute("seasons", seasons);
        uiModel.addAttribute("technology", technology);
        uiModel.addAttribute("types", types);
        uiModel.addAttribute("sources", sources);
        uiModel.addAttribute("hierarchies", hierarchies);
        uiModel.addAttribute("search", search);
        uiModel.addAttribute("count", count);
        uiModel.addAttribute("pattern", pattern);
        uiModel.addAttribute("totalPage", pageCount);
        uiModel.addAttribute("currentPage", search.getCurrentPage());
        uiModel.addAttribute("suggestion", suggestions);
        uiModel.addAttribute("hotKeys", hotKeys);
        return FABRIC_SEARCH;
    }

    private List<FabricSearchType> getSearchSuggestions(String type) {
        List<Map<String, Object>> suggests = esService.matchAll(type, convertSuggestParam(6));
        List<FabricSearchType> suggestions = convertFromMap(suggests);
        return suggestions;
    }

    private List<FabricSearchType> convertFromMap(List<Map<String, Object>> list) {
        List<FabricSearchType> fabrics = new ArrayList<FabricSearchType>();
        if (list != null) {
            for (Map<String, Object> maps : list) {
                FabricSearchType fabricSearch = new FabricSearchType(maps);
                fabrics.add(fabricSearch);
            }
        }
        return fabrics;
    }

    private CompositeQueryParam convertSuggestParam(int limit) {
        CompositeQueryParam compositeQueryParam = new CompositeQueryParam();
        compositeQueryParam.setSortField("popular");
        compositeQueryParam.setLimit(limit);
        return compositeQueryParam;
    }

    public CompositeQueryParam convertSearchParam(FabricSearch search) {
        CompositeQueryParam compositeQueryParam = new CompositeQueryParam();
        SearchParam param = null;

        if (!StringUtils.isEmpty(search.getKeyWord())) {
            String keyWord = search.getKeyWord();
            param = new SearchParam("title", keyWord, true);
            compositeQueryParam.addQueryStringParam(param);
        }

        //addQueryAndFilter(compositeQueryParam, "color", ListToStringArray(search.getColor()));
        if (!CollectionUtils.isEmpty(search.getSeason()))
            addQueryAndFilter(compositeQueryParam, "season", ListToStringArray(search.getSeason()));
        if (!CollectionUtils.isEmpty(search.getMainUse()))
            addQueryAndFilter(compositeQueryParam, "use", ListToStringArray(search.getMainUse()));
        if (!CollectionUtils.isEmpty(search.getTechnology()))
            addQueryAndFilter(compositeQueryParam, "technology", ListToStringArray(search.getTechnology()));
        if (!CollectionUtils.isEmpty(search.getPatterns()))
            addQueryAndFilter(compositeQueryParam, "pattern", ListToStringArray(search.getPatterns()));
        if (!CollectionUtils.isEmpty(search.getHierarchy()))
            addQueryAndFilter(compositeQueryParam, "hierarchy", ListToStringArray(search.getHierarchy()));

        if (search.getCategory() != null) {
            SearchParam categoryParam = new SearchParam("category", search.getCategory().getName(), false);
            compositeQueryParam.addFilterMustParam(categoryParam);
        }
        if (search.getSource() != null) {
            SearchParam categoryParam = new SearchParam("source", search.getSource().getName(), false);
            compositeQueryParam.addFilterMustParam(categoryParam);
        }
        if (search.getArea() != null) {
            String area = search.getArea();
            if (!area.equals("请选择")) {
                SearchParam categoryParam = new SearchParam("area", search.getArea(), false);
                compositeQueryParam.addFilterMustParam(categoryParam);
            }
        }

        if (search.getWeightRangeMin() != null && search.getWeightRangeMin() == 500)
            addRange(compositeQueryParam, "weight", search.getWeightRangeMin(), null);
        if (search.getWidthRangeMin() != null && search.getWidthRangeMin() == 180)
            addRange(compositeQueryParam, "width", search.getWidthRangeMin(), null);

        if (search.getWeightRangeMin() != null && search.getWeightRangeMax() != null) {
            addRange(compositeQueryParam, "weight", search.getWeightRangeMin(), search.getWeightRangeMax());
        } else if (search.getMinWeight() != null && search.getMaxWeight() != null) {
            addRange(compositeQueryParam, "weight", search.getMinWeight(), search.getMaxWeight());
        } else if (search.getMinWeight() != null) {
            addRange(compositeQueryParam, "weight", search.getMinWeight(), Double.MAX_VALUE);
        } else if (search.getMaxWeight() != null) {
            addRange(compositeQueryParam, "weight", 0.0, search.getMaxWeight());
        } else
            addRange(compositeQueryParam, "weight", 0.0, Double.MAX_VALUE);

        if (search.getWidthRangeMin() != null && search.getWidthRangeMax() != null)
            addRange(compositeQueryParam, "width", search.getWidthRangeMin(), search.getWidthRangeMax());
        else if (search.getMinWidth() != null && search.getMaxWidth() != null)
            addRange(compositeQueryParam, "width", search.getMinWidth(), search.getMaxWidth());
        else if (search.getMinWidth() != null)
            addRange(compositeQueryParam, "width", search.getMinWidth(), Double.MAX_VALUE);
        else if (search.getMaxWidth() != null)
            addRange(compositeQueryParam, "width", 0.0, search.getMaxWidth());
        else
            addRange(compositeQueryParam, "width", 0.0, Double.MAX_VALUE);


        if (search.getMinPrice() != null && search.getMaxPrice() != null)
            addRange(compositeQueryParam, "price", search.getMinPrice(), search.getMaxPrice());
        else if (search.getMaxPrice() != null)
            addRange(compositeQueryParam, "price", 0.0, search.getMaxPrice());
        else if (search.getMinPrice() != null)
            addRange(compositeQueryParam, "price", search.getMinPrice(), Double.MAX_VALUE);
        else
            addRange(compositeQueryParam, "price", 0.0, Double.MAX_VALUE);


        addSort(compositeQueryParam, search.getSort());

        if (search.getCurrentPage() == null)
            search.setCurrentPage(1);
        compositeQueryParam.setOffset((search.getCurrentPage() - 1) * PAGESIZE);
        compositeQueryParam.setLimit(PAGESIZE);

        return compositeQueryParam;
    }

}
