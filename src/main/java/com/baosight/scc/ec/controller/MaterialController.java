package com.baosight.scc.ec.controller;


import com.baosight.scc.ec.model.*;
import com.baosight.scc.ec.search.properties.CompositeQueryParam;
import com.baosight.scc.ec.search.properties.SearchParam;
import com.baosight.scc.ec.search.service.EsService;
import com.baosight.scc.ec.security.UserContext;
import com.baosight.scc.ec.service.*;
import com.baosight.scc.ec.type.*;
import com.baosight.scc.ec.web.EcGrid;
import com.baosight.scc.ec.web.MaterialCategoryJSON;
import com.google.common.collect.Lists;
import org.elasticsearch.search.sort.SortOrder;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 辅料控制器
 *
 * @author sam
 */
@Controller
public class MaterialController extends AbstractSearchController {
    @Autowired
    private MaterialService materialService;

    @Autowired
    private MaterialCategoryService categoryService;

    @Autowired
    private UserContext userContext;

    @Autowired
    private MaterialCategoryService materialCategoryService;
    @Autowired
    private FabricCategoryService fabricCategoryService;
    @Autowired
    private EcUserService ecUserService;
    @Autowired
    private CompositeScoreService compositeScoreService;
    @Autowired
    private OrderLineService orderLineService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private MaterialProvideTypeService materialProvideTypeService;
    @Autowired
    private MaterialScopeService materialScopeService;
    @Autowired
    private FavouriteItemsService favouriteItemsService;
    @Autowired
    private EsService esService;
    @Autowired
    private FavouriteMaterialCategoryService favouriteMaterialCategoryService;
    @Autowired
    private ShopService shopService;
    @Autowired
    private FabricService fabricService;
    @Autowired
    private FavouriteShopService favouriteShopService;

    final Logger logger = LoggerFactory.getLogger(MaterialController.class);


    private final static String MATERIAL_DETAIL = "materialDetail"; //辅料详情页面
    private final static String MATERIAL_COMMENTS = "material/materialCommentList"; //辅料交易评价记录页面
    private final static String MATERIAL_ORDERS = "material/materialOrderList"; //辅料交易记录页面
    private final static String MATERIAL_LIST = "material_list"; //辅料交易记录页面
    //辅料查询结果页面
    private final static String MATERIAL_SEARCH = "material_search";
    private final static String ERROR_404 = "redirect:/notFound";

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setDisallowedFields("id");
    }

    //面料详情页面
    @RequestMapping(value = "/material/{id}", method = RequestMethod.GET)
    public String getMaterial(Model uiModel, @PathVariable("id") String id, HttpServletRequest request) {
        String currentUserId = (String) request.getSession().getAttribute("id");
        boolean isAttention = false;
        Material mt = null;
        //辅料信息
        mt = this.materialService.getMaterialInfo(id);
        if (mt == null) {
            return ERROR_404;
        }
        //供应商信息
        EcUser user = this.ecUserService.findById(mt.getCreatedBy().getId());
        //辅料一级分类
        //    List<MaterialCategory> materialCategories = this.materialCategoryService.findAllFirstCategory();
        List<MaterialCategory> materialCategories = materialService.findByUserId(user.getId());
        //面料一级分类
        //    List<FabricCategory> fabricCategories = this.fabricCategoryService.findAllFirstCategory();
        List<FabricCategory> fabricCategories = fabricService.findByUserId(user.getId());
        if (mt == null) {
            mt = new Material();
        }
        Long orderCount = orderLineService.countByItem(mt);
        Long commentCount = commentService.countByItem(mt);
        sortMaterialRanges(mt);
        //综合评分
        CompositeScore score = compositeScoreService.findByUser(user);
        List<Shop> shops = shopService.findByUser(user);
        if (currentUserId != null && currentUserId != "" && shops.size() > 0) {
            EcUser currentUser = ecUserService.findById(currentUserId);
            isAttention = favouriteShopService.countByUserAndShop(currentUser, shops.get(0));
        }
        user.setShops(shops);
        List<Map<String, Object>> similar = esService.moreLikeThis("material", id, 6, "title", "category", "area");
        List<MaterialSearchType> moreLike = convertFromMap(similar);

        if (moreLike.size() < 6) {
            CompositeQueryParam compositeQueryParam = new CompositeQueryParam();
            compositeQueryParam.setLimit(6);
            compositeQueryParam.setSortField("price");
            compositeQueryParam.setSortOrder(SortOrder.DESC);
            SearchParam param = new SearchParam("title", "辅料", false);
            compositeQueryParam.addQueryStringParam(param);
            List<Map<String, Object>> list = esService.search("material", compositeQueryParam);

            moreLike = convertFromMap(list);
        }


        uiModel.addAttribute("like", moreLike);
        uiModel.addAttribute("material", mt);
        uiModel.addAttribute("user", user);
        uiModel.addAttribute("materialCategories", materialCategories);
        uiModel.addAttribute("fabricCategories", fabricCategories);
        uiModel.addAttribute("score", score);
        uiModel.addAttribute("orderCount", orderCount);
        uiModel.addAttribute("commentCount", commentCount);
        uiModel.addAttribute("isAttention", isAttention);
        uiModel.addAttribute("currentUserId", currentUserId);
        return MATERIAL_DETAIL;
    }

    public void sortMaterialRanges(Material material) {
        material.setRanges(new TreeMap<Double, Double>(material.getRanges()));
        Map<Double, Double> ranges = material.getRanges();
        Map<String, Double> result = material.getShowRanges();
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
            result.put("≥"+String.valueOf(keys[keys.length - 1].intValue()), ranges.get(keys[keys.length - 1]));
        }
    }

    //面料交易评价列表
    @RequestMapping(value = "/material/{id}/comments", method = RequestMethod.GET)
    public String showMaterialComments(Model uiModel,
                                       @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                       @RequestParam(value = "size", required = false, defaultValue = "15") Integer size,
                                       @PathVariable("id") String id,
                                       @RequestParam(value = "type", required = false) Integer type) {
        try {
            if (null != id) {
                //根据辅料id查询辅料信息
                Material m = this.materialService.getMaterialInfo(id);
                PageRequest pageRequest = null;
                Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
                if (page != null)
                    pageRequest = new PageRequest(page - 1, size, sort);
                else
                    pageRequest = new PageRequest(0, size, sort);
                //选择好、中、差评
                CommentType ctype = null;
                if (type != null) {
                    ctype = CommentType.values()[type];
                } else {
                    type = 0;
                }
                uiModel.addAttribute("type", type);
                //封装数据到页面显示
                EcGrid<Comment> cgrid = new EcGrid<Comment>();
                Page<Comment> commentPage = this.materialService.showMaterialComments(m, pageRequest, ctype);
                //30天内交易中、交易完成的个数
                DateTime time = new DateTime();
                DateTime begin = time.minusDays(30);
                Long bidEnd = orderLineService.findTimeBetweenAndStatusFinish(m, "FINISH", begin.toCalendar(Locale.SIMPLIFIED_CHINESE), time.toCalendar(Locale.SIMPLIFIED_CHINESE));
                Long bidStart = orderLineService.findTimeBetweenAndStatusNFinish(m, "FINISH", begin.toCalendar(Locale.SIMPLIFIED_CHINESE), time.toCalendar(Locale.SIMPLIFIED_CHINESE));
                //好、中、差评个数
                Long goodCount = commentService.countByItemAndStatus(m, 1);
                Long ybCount = commentService.countByItemAndStatus(m, 2);
                Long wasteCount = commentService.countByItemAndStatus(m, 3);

                uiModel.addAttribute("good", goodCount);
                uiModel.addAttribute("yb", ybCount);
                uiModel.addAttribute("waste", wasteCount);
                cgrid.setCurrentPage(commentPage.getNumber() + 1);
                cgrid.setEcList(Lists.newArrayList(commentPage));
                cgrid.setTotalPages(commentPage.getTotalPages());
                cgrid.setTotalRecords(commentPage.getTotalElements());
                uiModel.addAttribute("materialComments", cgrid);
                uiModel.addAttribute("bidStart", bidStart);
                uiModel.addAttribute("bidEnd", bidEnd);
                uiModel.addAttribute("id", id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return MATERIAL_COMMENTS;
    }

    /*
     * 辅料交易记录
     */
    @RequestMapping(value = "material/{id}/orders", method = RequestMethod.GET)
    public String showMaterialOrders(Model uiModel,
                                     @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                     @RequestParam(value = "size", required = false, defaultValue = "15") Integer size,
                                     @PathVariable("id") String id) {
        try {
            //辅料信息
            Material material = this.materialService.getMaterialInfo(id);

            PageRequest pageRequest = null;
            //定义排序字段
            Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
            //spring 封装page
            if (page != null)
                pageRequest = new PageRequest(page - 1, size, sort);
            else
                pageRequest = new PageRequest(0, size, sort);
            //获取交易数据
            //       Page<OrderLine> materialPage = this.materialService.showMaterialOrders(material, pageRequest);
            Page<OrderLine> materialPage = orderLineService.showFabricOrdersByFid(id, pageRequest);
            //将交易数据封装到一个页面对象mgrid上
            EcGrid<OrderLine> mgrid = new EcGrid<OrderLine>();
            mgrid.setCurrentPage(materialPage.getNumber() + 1);
            mgrid.setEcList(Lists.newArrayList(materialPage));
            mgrid.setTotalPages(materialPage.getTotalPages());
            mgrid.setTotalRecords(materialPage.getTotalElements());
            DateTime time = new DateTime();
            DateTime begin = time.minusDays(30);
            Long bidEnd = orderLineService.findTimeBetweenAndStatusFinish(material, "GOODS_RECEIVE", begin.toCalendar(Locale.SIMPLIFIED_CHINESE), time.toCalendar(Locale.SIMPLIFIED_CHINESE));
            Long bidStart = orderLineService.findTimeBetweenAndStatusNFinish(material, "GOODS_RECEIVE", begin.toCalendar(Locale.SIMPLIFIED_CHINESE), time.toCalendar(Locale.SIMPLIFIED_CHINESE));
            uiModel.addAttribute("mgrid", mgrid);
            uiModel.addAttribute("bidStart", bidStart);
            uiModel.addAttribute("bidEnd", bidEnd);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return MATERIAL_ORDERS;
    }

    @RequestMapping(value = "/sellerCenter/materials", method = RequestMethod.GET)
    public String list(Model uiModel,
                       @RequestParam(value = "size", required = false, defaultValue = "15") Integer size,
                       @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                       @RequestParam(value = "type", required = false, defaultValue = "4") Integer type) {
        ItemState state = ItemState.values()[type];

        EcUser user = userContext.getCurrentUser();
        Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
        Pageable pageable = createPageRequest(page, size, sort);
        EcGrid<Material> materialGrid;
        if (type == 4)
            materialGrid = createGrid(materialService.findByCreatedBy(user, pageable));
        else
            materialGrid = createGrid(materialService.findByCreatedByAndState(user, state, pageable));

        uiModel.addAttribute("materialGrid", materialGrid);
        return MATERIAL_LIST;
    }

    /*
        create form for material create
     */
    public Material createForm() {
        return new Material();
    }

    /*
        init material first category
     */
    public List<MaterialCategory> initFirstCategory() {
        return categoryService.getMaterialFirstCategorys();
    }

    /*
        init material second category
     */
    public List<MaterialCategory> initSecondCategory() {
        return categoryService.findAllSecondCategory();
    }

    @RequestMapping(value = "/materialCategory/{id}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<MaterialCategoryJSON> findSecondCategoryByParentId(@PathVariable("id") String id) {
        MaterialCategory materialCategory = new MaterialCategory();
        materialCategory.setId(id);
        return categoryService.findByParentCategory(materialCategory);
    }

    /*
        保存辅料
     */
    public void saveMaterial(Material material) {
        material.setRanges(buildRanges(material.getKeys(), material.getValues()));
        EcUser user = userContext.getCurrentUser();
        material.setCreatedBy(user);
        material.setState(ItemState.出售中);
        materialService.save(material);
    }

    /*
        暂存
     */
    public void tempSaveMaterial(Material material) {
        material.setRanges(buildRanges(material.getKeys(), material.getValues()));
        EcUser user = userContext.getCurrentUser();
        material.setCreatedBy(user);
        material.setState(ItemState.草稿);
        materialService.tempSave(material);
    }

    /*
    @param:id
    get materi by id
 */
    public Material editForm(String id) {
        Material material = materialService.findOne(id);
        material.setTempImages(initImages(material.getImages()));
        return material;
    }

    /*
        @param: detached material
     */
    public void updateMaterial(Material material) {
        //build map
        material.setRanges(buildRanges(material.getKeys(), material.getValues()));

        EcUser user = userContext.getCurrentUser();
        material.setCreatedBy(user);
        material.setState(ItemState.出售中);

        materialService.update(material);
    }

    /*
        @param:material detached material
        temp update material
     */
    public void tempUpdateMaterial(Material material) {
        //build map
        material.setRanges(buildRanges(material.getKeys(), material.getValues()));

        EcUser user = userContext.getCurrentUser();
        material.setCreatedBy(user);
        material.setState(ItemState.草稿);

        materialService.update(material);
    }

    public String successMessage() {
        return "暂存成功";
    }


    @RequestMapping(value = "/sellerCenter/material/favourite/{id}", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public String addFavourite(@PathVariable("id") String id) {
        EcUser currentUser = userContext.getCurrentUser();
        if (currentUser != null) {
            Material material = new Material();
            material.setId(id);
            favouriteItemsService.addFavourite(currentUser, material);
            return "success";
        } else {
            return "fail";
        }
    }

    @RequestMapping(value = "/sellerCenter/material/favourite/{id}", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseBody
    public String delFavourite(@PathVariable("id") String id) {
        EcUser currentUser = userContext.getCurrentUser();
        if (currentUser != null) {
            Material material = new Material();
            material.setId(id);
            favouriteItemsService.removeFavourite(currentUser, material);
            return "success";
        } else {
            return "fail";
        }
    }

    /**/
    public List<MaterialCategory> findSameLevelCategory(Material material) {
        if (material != null && material.getCategory() != null) {
            MaterialCategory category = material.getCategory().getParentCategory();
            return categoryService.findByParentCategorySource(category);
        }
        return null;
    }

    /*------------------------------------------------content fillContent init-----------------------------------------------------------------*/
    //ini计量单位
    @Cacheable(value = "materialUnit")
    public List<Unit> initUnits() {
        return convertListMapToUnit();
    }

    //供货方式
    public Collection<MaterialProvideType> initProvideType() {
        return materialProvideTypeService.findAll();
    }

    //重量厚薄
    @Cacheable(value = "materialWidthType")
    public List<MaterialWidthAndSizeType> initMaterialWidthType() {
        return convertListMapToMaterialHeight();
    }

    //适用范围
    public Collection<MaterialScope> initMaterialScope() {
        return materialScopeService.findAll();
    }

    //pref
    public List<FavouriteMaterialCategory> initUserPref() {
        EcUser user = userContext.getCurrentUser();
        return favouriteMaterialCategoryService.findByUser(user);
    }

    //类型
    @Cacheable(value = "ecMaterialType")
    public List<MaterialType> initMaterialType() {
        return convertListMapToMaterialType("aa", MaterialType.class);
    }
    /*------------------------------------------------content fillContent end-----------------------------------------------------------------*/

    /**
     * 辅料搜索
     *
     * @param uiModel
     * @return
     */
    @RequestMapping(value = "/search/material", method = RequestMethod.GET)
    public String searchMaterial(@ModelAttribute("search") MaterialSearch search, Model uiModel) {

        Long count = esService.count("material", convertSearchParam(search));
        Long totalPage = count % PAGESIZE == 0 ? count / PAGESIZE : count / PAGESIZE + 1;

        if (search.getCurrentPage() <= 0)
            search.setCurrentPage(1);
        if (search.getCurrentPage() > totalPage)
            search.setCurrentPage(totalPage.intValue());

        List<Map<String, Object>> list = esService.search("material", convertSearchParam(search));

        List<MaterialSearchType> materials = convertFromMap(list);

        if (list == null) logger.info("list is null please check");

        if (!StringUtils.isEmpty(search.getKeyWord()) && list != null && list.size() > 0)
            explainMaterialKeyword(search.getKeyWord());

        uiModel.addAttribute("lists", materials);

        List<MaterialCategory> categories = categoryService.findFirstCategoryByIsValid(0);
        List<Season> seasons = seasonService.findAll();
        Collection<MaterialScope> scopes = materialScopeService.findAll();
        Collection<MaterialProvideType> types = materialProvideTypeService.findAll();
        List<String> width = convertListMapToList("widthType");

        List<Map<String, Object>> suggests = esService.matchAll("material", convertSuggestParam(6));
        List<MaterialSearchType> suggestions = convertFromMap(suggests);
        Set<String> hotKeys = template.opsForZSet().reverseRange(redis_material, 0, 9);

        uiModel.addAttribute("firstCategory", categories);
        uiModel.addAttribute("seasons", seasons);
        uiModel.addAttribute("search", search);
        uiModel.addAttribute("count", count);
        uiModel.addAttribute("scopes", scopes);
        uiModel.addAttribute("types", types);
        uiModel.addAttribute("width", width);
        uiModel.addAttribute("totalPage", totalPage);
        uiModel.addAttribute("currentPage", search.getCurrentPage());
        uiModel.addAttribute("suggestion", suggestions);
        uiModel.addAttribute("hotKeys", hotKeys);
        return MATERIAL_SEARCH;
    }

    private CompositeQueryParam convertSuggestParam(int limit) {
        CompositeQueryParam compositeQueryParam = new CompositeQueryParam();
        compositeQueryParam.setSortField("popular");
        compositeQueryParam.setLimit(limit);
        return compositeQueryParam;
    }

    public CompositeQueryParam convertSearchParam(MaterialSearch search) {
        CompositeQueryParam compositeQueryParam = new CompositeQueryParam();
        SearchParam param = null;

        if (!StringUtils.isEmpty(search.getKeyWord())) {
            String keyWord = search.getKeyWord();
            param = new SearchParam("title", keyWord, true);
            compositeQueryParam.addQueryStringParam(param);
        }

        addQueryAndFilter(compositeQueryParam, "color", ListToStringArray(search.getColor()));
        addQueryAndFilter(compositeQueryParam, "use", ListToStringArray(search.getScopes()));
        addQueryAndFilter(compositeQueryParam, "provideType", ListToStringArray(search.getMaterialProvideType()));
        addQueryAndFilter(compositeQueryParam, "hierarchy", ListToStringArray(search.getHierarchy()));

        if (search.getCategory() != null) {
            SearchParam categoryParam = new SearchParam("category", search.getCategory().getName(), false);
            compositeQueryParam.addFilterMustParam(categoryParam);
        }
        if (search.getWeight() != null) {
            SearchParam categoryParam = new SearchParam("weight", search.getWeight(), false);
            compositeQueryParam.addFilterMustParam(categoryParam);
        }

        if (search.getMinPrice() != null && search.getMaxPrice() != null)
            addRange(compositeQueryParam, "price", search.getMinPrice(), search.getMaxPrice());
        else
            addRange(compositeQueryParam, "price", search.getMinRangePrice(), search.getMaxRangePrice());

        addSort(compositeQueryParam, search.getSort());

        if (search.getCurrentPage() == null)
            search.setCurrentPage(1);
        compositeQueryParam.setOffset((search.getCurrentPage() - 1) * PAGESIZE);
        compositeQueryParam.setLimit(PAGESIZE);

        return compositeQueryParam;
    }

    public List<MaterialSearchType> convertFromMap(List<Map<String, Object>> list) {
        List<MaterialSearchType> materials = new ArrayList<MaterialSearchType>();
        if (list != null) {
            for (Map<String, Object> maps : list) {
                MaterialSearchType fabricSearch = new MaterialSearchType(maps);
                materials.add(fabricSearch);
            }
        }
        return materials;
    }
}
