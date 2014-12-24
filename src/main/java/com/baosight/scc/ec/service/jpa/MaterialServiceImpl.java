package com.baosight.scc.ec.service.jpa;

import com.baosight.scc.ec.model.*;
import com.baosight.scc.ec.repository.search.MaterialSearchRepository;
import com.baosight.scc.ec.search.properties.SearchParam;
import com.baosight.scc.ec.repository.*;
import com.baosight.scc.ec.search.properties.*;
import com.baosight.scc.ec.search.service.EsService;
import com.baosight.scc.ec.service.*;
import com.baosight.scc.ec.type.*;
import com.baosight.scc.ec.utils.GuidUtil;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.*;

@Service
@Transactional(rollbackFor = Exception.class)
public class MaterialServiceImpl implements MaterialService {
    @Autowired
    private MaterialRepository fr;
    @Autowired
    private CommentRepository cr;
    @Autowired
    private EcUserRepository er;
    @Autowired
    private OrderLineRepository or;
    @Autowired
    private MaterialCategoryRepository mr;
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private EsService esService;
    @Autowired
    private FavouriteMaterialCategoryRepository favouriteMaterialCategoryRepository;
    @Autowired
    private EcProviderService providerService;
    @Autowired
    private MaterialCategoryService materialCategoryService;
    @Autowired
    private MaterialIndexService materialIndexService;
    @Autowired
    private MaterialSearchRepository materialSearchRepository;
    @Autowired
    private RedisItemService redisItemService;

    /**
     * 辅料产品详细信息
     *
     * @param mid 辅料产品id
     *            2014-5-16
     * @author sam
     */
    @Transactional(readOnly = true)
    public Material getMaterialInfo(String mid) {
        Material mt = null;
        try {
            mt = fr.findById(mid);
            if (mt != null) {
                if (mt.getContent() != null)
                    mt.setFakeContent(mt.getContent().getContent());
                if (null == mt) {
                    mt = new Material();
                }
                mt.setViewCount(mt.getViewCount() + 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mt;
    }

    /**
     * 辅料交易评价记录
     *
     * @param item     辅料
     * @param pageable
     * @param type
     * @return
     */
    @Transactional(readOnly = true)
    public Page<Comment> showMaterialComments(Material item, Pageable pageable, CommentType type) {
        Page<Comment> page = null;
        if (type != null) {
            page = cr.findByTypeAndItem(type, item, pageable);
        } else {
            page = cr.findByItem(item, pageable);
        }
        return page;
    }


    /**
     * 根据辅料服务商id，分类id，搜产品
     *
     * @param mid         供应商id
     * @param mCategoryId 分类id
     * @author sam
     */
    @Transactional(readOnly = true)
    public Page<Material> searchItemsByProAndCateId(String mid, String mCategoryId, Pageable pageable) {
        //根据mid查询辅料服务商信息
        EcUser user = this.er.findById(mid);
        //根据mCategoryId查询分类信息
        MaterialCategory mcategory = this.mr.findOne(mCategoryId);
        Page<Material> page = null;
        //根据服务商和分类条件查询该服务商下辅料产品列表
        page = this.fr.findByCreatedByAndCategory(user, mcategory, pageable);
        return page;
    }

    /**
     * 根据辅料服务商id，产品名称，搜产品
     *
     * @param mid       辅料供应商id
     * @param mItemName 产品名称
     * @param pageable
     */
    @Transactional(readOnly = true)
    public Page<Material> searchItemsByProIdAndName(String mid, String mItemName, Pageable pageable) {
        EcUser user = this.er.findById(mid);
        Page<Material> page = null;
        if (null != mItemName) {
            page = this.fr.findByCreatedByAndName(user, mItemName, pageable);
        } else {
            page = this.fr.findByCreatedBy(user, pageable);
        }
        return page;
    }

    @Transactional(readOnly = true)
    public Page<Material> searhItems(String mid, String mItemName, String secondCategory, Pageable pageable) {
        Page<Material> page = null;
        if (null != secondCategory) {
            this.searchItemsByProAndCateId(mid, secondCategory, pageable);
        } else {
            this.searchItemsByProIdAndName(mid, mItemName, pageable);
        }
        return page;
    }

    /**
     * 根据辅料供应商id，查询辅料分页列表
     *
     * @param proId    供应商id
     * @param pageable
     * @return
     */
    public Page<Material> getItemsByProviderId(String proId, Pageable pageable) {
        EcUser user = this.er.findById(proId);
        return this.fr.findByCreatedBy(user, pageable);
    }


    @Override
    public Page<Material> findByCreatedBy(EcUser u, Pageable pageable) {
        return fr.findByCreatedBy(u, pageable);
    }

    /**
     * 辅料交易记录
     *
     * @param item
     * @param pageable
     */
    @Transactional(readOnly = true)
    public Page<OrderLine> showMaterialOrders(Material item, Pageable pageable) {
        return or.findByItem(item, pageable);
    }


    @Override
    public Page<Material> findByCreatedByAndState(EcUser u, ItemState state, Pageable pageable) {
        return fr.findByCreatedByAndState(u, state, pageable);
    }

    @Override
    public Material findOne(String id) {
        Material material = fr.findOne(id);
        if (material != null) {
            material.getImages().size();
            material.getColors().size();
            material.getRanges().size();
            if (material.getFirstCategory() != null)
                material.getFirstCategory().getName();
            if (material.getCategory() != null)
                material.getCategory().getName();
            if (material.getContent() != null)
                material.setFakeContent(material.getContent().getContent());
            material.getMaterialScope().size();
            material.getMaterialProvideType().size();
            material.getImages().size();
        }
        return material;
    }

    @Override
    public Material update(Material material) {
        final Material target = fr.save(bindDate(material));
        target.setRange(material.getRanges());
        target.setUrl("/material/"+target.getId());
        final MaterialIndex index = materialIndexService.transferFromMaterial(target);
        if (!target.getState().equals(ItemState.草稿))
            TransactionSynchronizationManager.registerSynchronization(
                    new TransactionSynchronizationAdapter() {
                        @Override
                        public void afterCommit() {
                            materialSearchRepository.save(index);
                            redisItemService.delete(target);
                            redisItemService.save(target);
                        }
                    }
            );
        return target;
    }

    @Override
    public Material save(Material material) {
        material.setId(GuidUtil.newGuid());
        final Material target = fr.save(bindDate(material));
        target.setRange(material.getRanges());
        target.setUrl("/material/"+target.getId());
        final MaterialIndex index = materialIndexService.transferFromMaterial(target);
        if (!target.getState().equals(ItemState.草稿))
            TransactionSynchronizationManager.registerSynchronization(
                    new TransactionSynchronizationAdapter() {
                        @Override
                        public void afterCommit() {
                            materialSearchRepository.save(index);
                            redisItemService.save(target);
                        }
                    }
            );
        return material;
    }

    @Override
    public Material tempSave(Material material) {
        material.setId(GuidUtil.newGuid());
        return fr.save(bindDate(material));
    }

    private List<Map<String, Object>> convertObjectToList(Material material) {
        List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
        lists.add(convertObjectToMap(material));
        return lists;
    }

    private Material bindDate(Material material) {
        EcUser sourUser = em.find(EcUser.class, material.getCreatedBy().getId());
        List<CultureImage> images = new ArrayList<CultureImage>();

        if (material.getTempImages().length > 0) {
            material.setCoverImage(material.getTempImages()[0]);
            for (int i = 0; i < material.getTempImages().length; i++) {
                String location = material.getTempImages()[i];
                //and temp image`s src is not empty string
                if (!StringUtils.isEmpty(location)) {
                    CultureImage image = new CultureImage(material.getTempImages()[i], i);
                    image.setCreatedTime(Calendar.getInstance());
                    //and source item not contain this image
                    images.add(image);
                }
            }
        }
        material.setImages(images);

        if (!sourUser.getPreferMaterialCategory().contains(material.getCategory())) {
            FavouriteMaterialCategory favouriteMaterialCategory = new FavouriteMaterialCategory(sourUser, material.getCategory());
            favouriteMaterialCategoryRepository.save(favouriteMaterialCategory);
        }


        Double min = new Double(0);
        if (material.getRanges().size() > 0)
            min = Collections.min(material.getRanges().values());
        material.setPrice(min);

        if (material.getContent() != null)
            material.getContent().setContent(material.getFakeContent());
        else {
            EcContent ecContent = new EcContent();
            ecContent.setId(GuidUtil.newGuid());
            ecContent.setContent(material.getFakeContent());
            material.setContent(ecContent);
        }

        return material;
    }

    private Map<String, Object> convertObjectToMap(Material material) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("_id", material.getId());
        map.put("title", material.getName());

        map.put("category", material.getCategory().getName());

        EcProvider provider = providerService.findOne(material.getCreatedBy().getId());
        map.put("area", provider.getBusinessLicRegisterProv());
        map.put("company", provider.getCompanyName());

        map.put("provideType", material.getMaterialProvideType());
        map.put("use", material.getMaterialScope());
        map.put("weight", material.getMaterialWidthAndSizeType());
        map.put("price", material.getPrice());
        map.put("cover", material.getCoverImage());
        //added by sam 2014-8-11 添加销售量、浏览量
        map.put("viewCount", material.getViewCount());
        map.put("sales", material.getBidCount());
        map.put("createdBy", material.getCreatedBy().getId());
        //ended by sam
        return map;
    }

    @Override
    @Cacheable(value = "homeMaterialCacheLoadService")
    public List<Material> findTop4(ItemState state, Pageable pageable) {
        return fr.findByState(state, pageable);
    }

    @Override
    public void updateMaterial(ItemState state) {
        String sqlM = "select m from Material m where m.state=:state";
        int offset = 0;
        Query queryM = em.createQuery(sqlM).setParameter("state", state).setFirstResult(offset).setMaxResults(200);
        List<Material> materials = queryM.getResultList();
        while (materials.size() > 0) {
            //    Map<String,Map<String,Object>> map=new HashMap<String, Map<String, Object>>();

            for (Material m : materials) {
                esService.updateById("material", m.getId(), this.convertObjectToMap(m));
                //    map.put(m.getId(),this.convertObjectToMap(m));
            }
            //    esService.updateByIdBulk("material",map);
            em.clear();
            offset += 200;
            queryM = em.createQuery(sqlM).setParameter("state", state).setFirstResult(offset).setMaxResults(200);
            materials = queryM.getResultList();
        }
    }

    @Override
    public void deleteMaterials() {
        String sql = "select m from Item m where  m.state=:state ";
        int offset = 0;
        Calendar currentTime = Calendar.getInstance();
        Query query = em.createQuery(sql).setParameter("state", ItemState.下架).setFirstResult(offset).setMaxResults(200);
        List<Material> materials = query.getResultList();
        while (materials.size() > 0) {
            //    List<SearchParam> list = new ArrayList<SearchParam>();

            for (Material f : materials) {
                esService.deleteById("material", f.getId());
                //    SearchParam searchParam = new SearchParam("id",f.getId(),true);
                //    list.add(searchParam);
            }
            //    esService.deleteByQuery("material",list);
            em.clear();
            offset += 200;
            query = em.createQuery(sql).setParameter("state", ItemState.下架).setFirstResult(offset).setMaxResults(200);
            materials = query.getResultList();
        }
    }

    @Override
    public List<MaterialCategory> findByUserIdAndFirstCategory(String userId, String firstCategory) {
        String sql = "select a.category_id from t_ec_material a join t_ec_item b on a.id=b.id where a.firstcategory_id='" + firstCategory + "' and b.createdBy='" + userId + "' group by a.category_id";
        Query query = em.createNativeQuery(sql);
        List result = query.getResultList();
        return getMaterialCategoryList(result);
    }

    @Override
    public List<MaterialCategory> findByUserId(String userId) {
        String sql = "select a.firstcategory_id from t_ec_material a join t_ec_item b on a.id=b.id where a.firstcategory_id is not null and b.state='出售中' and b.createdBy='" + userId + "' group by a.firstcategory_id";
        Query query = em.createNativeQuery(sql);
        List result = query.getResultList();
        return getMaterialCategoryList(result);
    }

    private List<MaterialCategory> getMaterialCategoryList(List result) {
        List<MaterialCategory> list = new ArrayList<MaterialCategory>();
        for (Iterator iterator = result.iterator(); iterator.hasNext(); ) {
            String values = (String) iterator.next();
            MaterialCategory materialCategory = materialCategoryService.findById(values);
            list.add(materialCategory);
        }
        return list;
    }

    public String[] initImages(List<CultureImage> images) {
        List<String> locationName = new ArrayList<String>();
        for (CultureImage image : images) {
            locationName.add(image.getLocation());
        }
        return locationName.toArray(new String[]{});
    }

    @Override
    public Material materialClone(String id) {
        Material material = fr.findOne(id);
        Material materialCopy = new Material();
        material.getRanges();
        material.getFirstCategory();
        material.getCategory();
        List<MaterialScope> materialScopes = material.getMaterialScope();
        List<MaterialProvideType> materialProvideTypes = material.getMaterialProvideType();
        List<MaterialScope> materialScopeList = new ArrayList<MaterialScope>();
        List<MaterialProvideType> materialProvideTypeList = new ArrayList<MaterialProvideType>();
        try {
            materialCopy = (Material) material.clone();
            for (MaterialScope ms : materialScopes) {
                materialScopeList.add((MaterialScope) ms.clone());
            }
            for (MaterialProvideType mpt : materialProvideTypes) {
                materialProvideTypeList.add((MaterialProvideType) mpt.clone());
            }
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        String newId = GuidUtil.newGuid();
        DateTime dt = new DateTime();
        materialCopy.setCreatedTime(dt.toCalendar(Locale.SIMPLIFIED_CHINESE));
        materialCopy.setId(newId);
        materialCopy.setState(ItemState.下架);
        materialCopy.setContent(null);
        materialCopy.setFakeContent(material.getContent().getContent());
        materialCopy.setTempImages(initImages(material.getImages()));
        materialCopy.setMaterialScope(materialScopeList);
        materialCopy.setMaterialProvideType(materialProvideTypeList);
        return fr.save(bindDate(materialCopy));
    }

    @Override
    public Map<String, Integer> materialCount() {
        Map<String,Integer> materialMap = new HashMap<String, Integer>();
        String materialSql = "select t2.state as itemState,count(*) as itemCount from t_ec_material t1 left join t_ec_item t2 on t1.id=t2.id where t2.CREATEDBY not in('admin','admintest','lichaoyi') group by t2.state";
        Query materialQuery = em.createNativeQuery(materialSql);
        List materialResult =  materialQuery.getResultList();
        for (Iterator iterator = materialResult.iterator(); iterator.hasNext(); ) {
            Object[] values = (Object[]) iterator.next();
            materialMap.put((String) values[0],(Integer) values[1]);
        }
        return materialMap;
    }
}
