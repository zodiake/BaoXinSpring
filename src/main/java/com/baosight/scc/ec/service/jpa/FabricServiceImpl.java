package com.baosight.scc.ec.service.jpa;

import com.baosight.scc.ec.model.*;
import com.baosight.scc.ec.repository.*;
import com.baosight.scc.ec.repository.search.FabricSearchRepository;
import com.baosight.scc.ec.rest.StringUtil;
import com.baosight.scc.ec.search.service.EsService;
import com.baosight.scc.ec.service.*;
import com.baosight.scc.ec.type.FabricMainUseType;
import com.baosight.scc.ec.type.FabricProvideType;
import com.baosight.scc.ec.type.ItemState;
import com.baosight.scc.ec.utils.GuidUtil;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.*;

import static com.baosight.scc.ec.utils.RedisConstant.ConstantKey.*;

/**
 * Created by zodiake on 2014/5/12.
 */
@Service
@Transactional(rollbackFor = TransactionException.class)
public class FabricServiceImpl implements FabricService {
    @Autowired
    private FabricRepository fr;
    @Autowired
    private EcUserRepository er;
    @Autowired
    private FabricCategoryRepository fcr;
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private EsService esService;
    @Autowired
    private FabricTechnologyTypeService technologyTypeService;
    @Autowired
    private FavouriteFabricCategoryRepository favouriteFabricCategoryRepository;
    @Autowired
    private EcProviderService providerService;
    @Autowired
    private FabricCategoryService fabricCategoryService;
    @Autowired
    private FabricSearchRepository fabricSearchRepository;
    @Autowired
    private FabricIndexService fabricIndexService;
    @Autowired
    private RedisItemService redisItemService;


    final Logger logger = LoggerFactory.getLogger(FabricServiceImpl.class);

    @Transactional(readOnly = true)
    public Page<Fabric> findAll(Pageable pageable) {
        return fr.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Fabric> findByUser(EcUser user, Pageable pageable) {
        return fr.findByCreatedBy(user, pageable);
    }

    @Transactional(readOnly = true)
    public Fabric findById(String id) {
        Fabric fabric = fr.findOne(id);
        if (fabric != null) {
            if (fabric.getContent() != null)
                fabric.setFakeContent(fabric.getContent().getContent());
            fabric.getCreatedBy();
            fabric.setViewCount(fabric.getViewCount() + 1);
        }
        return fabric;
    }


    /*
    @param id pk
    @Param u currentUser
    @param init onetomany
 */
    @Transactional(readOnly = true)
    public Fabric findByIdAndUser(String id, EcUser u, boolean init) {
        Fabric fabric = fr.findByIdAndCreatedBy(id, u);
        if (fabric != null && init) {
            fabric.getSeasons().size();
            fabric.getMainUseTypes().size();
            fabric.getColors().size();
            fabric.getRanges().size();
            fabric.getImages().size();
            fabric.getPatterns().size();
            if (fabric.getFabricProvideType() != null)
                fabric.getFabricProvideType().size();
            if (fabric.getFirstCategory() != null)
                fabric.getFirstCategory();
            if (fabric.getCategory() != null)
                fabric.getCategory().getName();
            fabric.getCategory().getParentCategory().getName();
            fabric.getMainUseTypes().size();
            if (fabric.getSource() != null)
                fabric.getSource().getName();
            if (fabric.getSourceDetail() != null)
                fabric.getSourceDetail().getParent().getName();
            if (fabric.getContent() != null)
                fabric.setFakeContent(fabric.getContent().getContent());
            return fabric;
        }
        return null;
    }

    @Override
    public Fabric save(Fabric fabric) {
        final String id = GuidUtil.newGuid();
        fabric.setId(id);
        final Fabric result = fr.save(bindData(fabric));
        result.setRange(fabric.getRanges());
        result.setUrl("/fabric/" + id);
        final FabricIndex index = fabricIndexService.transferFromFabric(result);
        if (!fabric.getState().equals(ItemState.草稿))
            TransactionSynchronizationManager.registerSynchronization(
                    new TransactionSynchronizationAdapter() {
                        @Override
                        public void afterCommit() {
                            fabricSearchRepository.save(index);
                            redisItemService.save(result);
                        }
                    }
            );
        return result;
    }


    @Override
    public Fabric tempSave(Fabric fabric) {
        fabric.setId(GuidUtil.newGuid());
        Fabric result = fr.save(bindData(fabric));
        return result;
    }

    private List<Map<String, Object>> convertObjectToList(Fabric fabric) {
        List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
        lists.add(convertObjectToMap(fabric));
        return lists;
    }

    private Map<String, Object> convertObjectToMap(Fabric fabric) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("_id", fabric.getId());
        map.put("title", fabric.getName());

        List<Season> seasons = fabric.getSeasons();
        List<String> seasonString = new ArrayList<String>();
        if (seasons.size() > 0) {
            for (Season s : seasons) {
                seasonString.add(s.toString());
            }
        }
        map.put("season", seasonString);

        List<EcColor> colors = fabric.getColors();
        List<String> colorString = new ArrayList<String>();
        List<String> hierarchy = new ArrayList<String>();
        for (EcColor c : colors) {
            colorString.add(c.getRgb());
            if (!hierarchy.contains(c.getHierarchy()))
                hierarchy.add(c.getHierarchy());
        }
        map.put("color", colorString);
        map.put("hierarchy", hierarchy);

        map.put("category", fabric.getCategory());

        EcProvider provider = providerService.findOne(fabric.getCreatedBy().getId());
        map.put("area", provider.getBusinessLicRegisterProv());
        map.put("company", provider.getCompanyName());

        if (!StringUtil.isEmpty(fabric.getFabricSecondTechnologyId()) && !fabric.getFabricSecondTechnologyId().equals("--请选择--"))
            map.put("technology", technologyTypeService.findOne(fabric.getFabricSecondTechnologyId()).getName());
        map.put("use", fabric.getMainUseTypes());
        map.put("width", fabric.getFabricWidthType());
        map.put("weight", fabric.getFabricHeightType());
        map.put("price", fabric.getPrice());
        map.put("source", fabric.getSourceDetail().getName());
        List<EcPattern> patterns = fabric.getPatterns();
        for (EcPattern p : patterns) {
            p.setName(StringUtils.trimAllWhitespace(p.getName()));
        }
        map.put("pattern", fabric.getPatterns());
        map.put("cover", fabric.getCoverImage());
        //added by sam 2014-8-11
        map.put("viewCount", fabric.getViewCount());
        map.put("sales", fabric.getBidCount());
        map.put("createdBy", fabric.getCreatedBy().getId());
        //ended by sam
        return map;
    }

    @Override
    public Fabric update(Fabric fabric) {
        final Fabric result = fr.save(bindData(fabric));
        final FabricIndex index = fabricIndexService.transferFromFabric(result);
        result.setRange(fabric.getRanges());
        result.setUrl("/fabric/" + result.getId());
        if (!fabric.getState().equals(ItemState.草稿))
            TransactionSynchronizationManager.registerSynchronization(
                    new TransactionSynchronizationAdapter() {
                        @Override
                        public void afterCommit() {
                            fabricSearchRepository.save(index);
                            redisItemService.delete(result);
                            redisItemService.save(result);
                        }
                    }
            );
        return result;
    }

    private Fabric bindData(Fabric fabric) {
        EcUser user = fabric.getCreatedBy();
        EcUser sourUser = em.find(EcUser.class, user.getId());
        List<CultureImage> images = new ArrayList<CultureImage>();

        if (fabric.getTempImages().length > 0) {
            fabric.setCoverImage(fabric.getTempImages()[0]);
            for (int i = 0; i < fabric.getTempImages().length; i++) {
                String location = fabric.getTempImages()[i];
                //and temp image`s src is not empty string
                if (!StringUtils.isEmpty(location)) {
                    CultureImage image = new CultureImage(fabric.getTempImages()[i], i);
                    image.setCreatedTime(Calendar.getInstance());
                    //and source item not contain this image
                    images.add(image);
                }
            }
        }
        fabric.setImages(images);

        if (!sourUser.getPreferFabricCategory().contains(fabric.getCategory())) {
            FavouriteFabricCategory favouriteFabricCategory = new FavouriteFabricCategory(user, fabric.getCategory());
            favouriteFabricCategoryRepository.save(favouriteFabricCategory);
        }
        Double min = new Double(0);
        if (fabric.getRanges().size() > 0)
            min = Collections.min(fabric.getRanges().values());
        fabric.setPrice(min);

        if (fabric.getContent() != null)
            fabric.getContent().setContent(fabric.getFakeContent());
        else {
            EcContent ecContent = new EcContent();
            ecContent.setId(GuidUtil.newGuid());
            ecContent.setContent(fabric.getFakeContent());
            fabric.setContent(ecContent);
        }

        fabric.setFabricWidthType(fabric.getFakeWeight().toString());
        if (fabric.getFakeHeight() == null)
            fabric.setFabricHeightType("0");
        else
            fabric.setFabricHeightType(fabric.getFakeHeight().toString());
        return fabric;
    }

    /**
     * 根据条件查询面料供应商店内产品列表分页信息
     *
     * @param id             面料供应商id
     * @param proName        面料名称
     * @param secondCategory 二级分类id
     * @param pageable
     * @return
     * @author sam
     */
    @Transactional(readOnly = true)
    public Page<Fabric> searchItems(String id, String proName, String secondCategory, Pageable pageable) {
        Page<Fabric> page = null;
        if (secondCategory != null) {
            page = this.searchItemsByProAndCateId(id, secondCategory, pageable);
        } else {
            page = this.searchItemsByProIdAndName(id, proName, pageable);
        }
        return page;
    }

    /**
     * 根据面料供应商、分类，查询该面料供应商下的产品分页列表信息
     *
     * @param id             供应商id
     * @param secondCategory 二级分类
     * @param pageable
     * @return
     * @author sam
     */
    @Transactional(readOnly = true)
    public Page<Fabric> searchItemsByProAndCateId(String id, String secondCategory, Pageable pageable) {
        Page<Fabric> page = null;
        //根据供应商id，获取面料供应商信息
        EcUser user = this.er.findOne(id);
        //根据二级分类id，查询分类信息
        FabricCategory category = this.fcr.findOne(secondCategory);
        //根据供应商、分类，查询面料产品列表信息
        page = this.fr.findByCreatedByAndCategory(user, category, pageable);
        return page;
    }

    /**
     * 根据面料供应商、产品名称，查询该面料供应商下的产品分页列表信息
     *
     * @param id       供应商id
     * @param proName  产品名称
     * @param pageable
     * @return
     * @author sam
     */
    @Transactional(readOnly = true)
    public Page<Fabric> searchItemsByProIdAndName(String id, String proName, Pageable pageable) {
        Page<Fabric> page = null;
        //根据面料供应商id，查询供应商信息
        EcUser user = this.er.findOne(id);
        //根据供应商、产品名称，查询面料产品列表信息
        page = this.fr.findByCreatedByAndName(user, proName, pageable);
        return page;
    }

    @Override
    @Cacheable(value = "homeFabricCacheLoadService")
    public List<Fabric> findTop4(ItemState state, Pageable pageable) {
        return fr.findByState(state, pageable);
    }

    @Override
    public Page<Fabric> findByCreatedByAndState(EcUser user, ItemState state, Pageable pageable) {
        return fr.findByCreatedByAndState(user, state, pageable);
    }

    @Override
    public Page<Fabric> findByCreatedBy(EcUser user, Pageable pageable) {
        return fr.findByCreatedBy(user, pageable);
    }

    @Override
    public void updateFabric(ItemState state) {
        String sqlF = "select f from Fabric f where f.state=:state";
        int offset = 0;
        Query queryF = em.createQuery(sqlF).setParameter("state", state).setFirstResult(offset).setMaxResults(200);
        List<Fabric> fabrics = queryF.getResultList();
        while (fabrics.size() > 0) {
            for (Fabric f : fabrics) {
                FabricIndex index = fabricIndexService.transferFromFabric(f);
                fabricSearchRepository.save(index);
            }
            em.clear();
            offset += 200;
            queryF = em.createQuery(sqlF).setParameter("state", state).setFirstResult(offset).setMaxResults(200);
            fabrics = queryF.getResultList();
        }
    }

    @Override
    public void deleteFabrics() {
        Calendar currentTime = Calendar.getInstance();
        String sql = "select f from Item f where  f.state=:state";
        int offset = 0;
        Query query = em.createQuery(sql).setParameter("state", ItemState.下架).setFirstResult(offset).setMaxResults(200);
        List<Fabric> fabrics = query.getResultList();
        while (fabrics.size() > 0) {
            //    List<SearchParam> list = new ArrayList<SearchParam>();
            for (Fabric f : fabrics) {
                esService.deleteById("fabric", f.getId());
                //    SearchParam searchParam = new SearchParam("id",f.getId(),true);
                //    list.add(searchParam);
            }
            //    esService.deleteByQuery("fabric",list);
            em.clear();
            offset += 200;
            query = em.createQuery(sql).setParameter("state", ItemState.下架).setFirstResult(offset).setMaxResults(200);
            fabrics = query.getResultList();
        }
    }

    @Override
    public List<FabricCategory> findByUserId(String userId) {
        String sql = "select a.first_category_id from t_ec_fabric a join t_ec_item b on a.id=b.id where a.first_category_id is not null and b.state='出售中' and b.createdBy='" + userId + "' group by a.first_category_id";
        Query query = em.createNativeQuery(sql);
        List result = query.getResultList();
        return getFabricCategoryList(result);
    }

    @Override
    public List<FabricCategory> findByUserIdAndFirstCategory(String userId, String firstCategory) {
        String sql = "select a.category_id from t_ec_fabric a join t_ec_item b on a.id=b.id where a.first_category_id='" + firstCategory + "' and b.createdBy='" + userId + "' group by a.category_id";
        Query query = em.createNativeQuery(sql);
        List result = query.getResultList();
        return getFabricCategoryList(result);
    }

    private List<FabricCategory> getFabricCategoryList(List result) {
        List<FabricCategory> list = new ArrayList<FabricCategory>();
        for (Iterator iterator = result.iterator(); iterator.hasNext(); ) {
            String values = (String) iterator.next();
            FabricCategory fabricCategory = fabricCategoryService.findById(values);
            list.add(fabricCategory);
        }
        return list;
    }

    @Override
    public Fabric fabricClone(String id) {
        Fabric fabric = fr.findOne(id);
        Fabric fabricCopy = new Fabric();
        fabric.getSource();
        fabric.getSourceDetail();
        fabric.getRanges();
        if (fabric.getFabricTechnologyId() == null) {
            fabric.setFabricTechnologyId("--请选择--");
        }
        if (fabric.getFabricSecondTechnologyId() == null) {
            fabric.setFabricSecondTechnologyId("--请选择--");
        }
        List<EcColor> ecColors = fabric.getColors();
        List<EcPattern> ecPatterns = fabric.getPatterns();
        List<FabricMainUseType> fabricMainUseTypes = fabric.getMainUseTypes();
        List<Season> fabricSeasons = fabric.getSeasons();
        List<FabricProvideType> fabricProvideTypes = fabric.getFabricProvideType();
        List<EcColor> colors = new ArrayList<EcColor>();
        List<EcPattern> patterns = new ArrayList<EcPattern>();
        List<FabricMainUseType> mainUseTypes = new ArrayList<FabricMainUseType>();
        List<Season> seasons = new ArrayList<Season>();
        List<FabricProvideType> provideTypes = new ArrayList<FabricProvideType>();
        try {
            fabricCopy = (Fabric) fabric.clone();
            for (EcColor c : ecColors) {
                colors.add((EcColor) c.clone());
            }
            for (EcPattern p : ecPatterns) {
                patterns.add((EcPattern) p.clone());
            }
            for (FabricMainUseType mainUseType : fabricMainUseTypes) {
                mainUseTypes.add((FabricMainUseType) mainUseType.clone());
            }
            for (Season s : fabricSeasons) {
                seasons.add((Season) s.clone());
            }
            for (FabricProvideType provideType : fabricProvideTypes) {
                provideTypes.add((FabricProvideType) provideType.clone());
            }
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        String newId = GuidUtil.newGuid();
        DateTime dt = new DateTime();
        fabricCopy.setCreatedTime(dt.toCalendar(Locale.SIMPLIFIED_CHINESE));
        fabricCopy.setId(newId);
        fabricCopy.setState(ItemState.下架);
        fabricCopy.setFakeWeight(Double.parseDouble(fabric.getFabricWidthType()));
        fabricCopy.setTempImages(initImages(fabric.getImages()));
        fabricCopy.setContent(null);
        fabricCopy.setFakeContent(fabric.getContent().getContent());
        fabricCopy.setColors(colors);
        fabricCopy.setPatterns(patterns);
        fabricCopy.setMainUseTypes(mainUseTypes);
        fabricCopy.setSeasons(seasons);
        fabricCopy.setFabricProvideType(provideTypes);
        fr.save(bindData(fabricCopy));
        return fabricCopy;
    }

    public String[] initImages(List<CultureImage> images) {
        List<String> locationName = new ArrayList<String>();
        for (CultureImage image : images) {
            locationName.add(image.getLocation());
        }
        return locationName.toArray(new String[]{});
    }

    @Override
    public Map<String, Integer> fabricCount() {
        Map<String,Integer> fabricMap = new HashMap<String, Integer>();
        String fabricSql = "select t2.state as itemState,count(*) as itemCount from t_ec_fabric t1 left join t_ec_item t2 on t1.id=t2.id where t2.CREATEDBY not in('admin','admintest','lichaoyi') group by t2.state";
        Query fabricQuery = em.createNativeQuery(fabricSql);
        List fabricResult =  fabricQuery.getResultList();
        for (Iterator iterator = fabricResult.iterator(); iterator.hasNext(); ) {
            Object[] values = (Object[]) iterator.next();
            fabricMap.put((String) values[0], (Integer)values[1]);
        }
        return fabricMap;
    }
}
