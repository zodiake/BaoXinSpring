package com.baosight.scc.ec.service.jpa;

import com.baosight.scc.ec.model.*;
import com.baosight.scc.ec.repository.*;
import com.baosight.scc.ec.service.ItemService;
import com.baosight.scc.ec.type.ItemState;
import com.baosight.scc.ec.utils.RedisConstant.ConstantKey;
import com.baosight.scc.ec.web.ItemJSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import static com.baosight.scc.ec.utils.RedisConstant.ConstantKey.ItemKey;

/**
 * Created by Charles on 2014/5/19.
 */
@Service
@Transactional
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemRepository ir;
    @Autowired
    private MaterialRepository mr;
    @Autowired
    private FabricRepository fr;
    @Autowired
    private EcUserRepository er;
    @Autowired
    private FabricCategoryRepository fabricCategoryRepository;
    @Autowired
    private MaterialCategoryRepository materialCategoryRepository;
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private StringRedisTemplate template;

    public Item findByIdThroughRedis(String id) {
        return initFromRedis(id);
    }

    @Transactional(readOnly = true)
    private Item initFromRedis(String id) {
        Item item = new Item();
        String key = ConstantKey.REDIS_ITEM + id;
        String itemId = (String) template.opsForHash().get(key, "id");
        String cover = (String) template.opsForHash().get(key, "cover");
        String providerId = (String) template.opsForHash().get(key, "providerId");
        String providerName = (String) template.opsForHash().get(key, "providerName");
        String name = (String) template.opsForHash().get(key, "name");
        String state = (String) template.opsForHash().get(key, "state");
        String url = (String) template.opsForHash().get(key, ItemKey.url);
        Set<String> rangeKeys = (Set) template.opsForHash().keys(ConstantKey.REDIS_PRICE + id);
        item.setId(id);
        item.setCoverImage(cover);
        EcUser user = new EcUser();
        user.setId(providerId);
        user.setName(providerName);
        item.setCreatedBy(user);
        item.setName(name);
        item.setState(ItemState.valueOf(state));
        item.setUrl(url);
        for (String s : rangeKeys) {
            String v = (String) template.opsForHash().get(ConstantKey.REDIS_PRICE + id, s);
            item.getRange().put(Double.parseDouble(s), Double.parseDouble(v));
        }
        return item;
    }

    @Override
    public Item findById(String id) {
        return ir.findOne(id);
    }

    /**
     * 根据产品id，判断产品类型，0：表示面料，1：表示辅料,2:表示不存在
     *
     * @param id
     * @return
     * @author sam
     */
    public int itemTypeById(String id) {
        int flag = 2;
        Item item = null;
        Material material = null;
        Fabric fabric = null;
        material = this.mr.findOne(id);
        if (null != material) {
            flag = 1;
        } else {
            fabric = this.fr.findOne(id);
            if (null != fabric) {
                flag = 0;
            }
        }
        return flag;
    }

    @Override
    public List<ItemJSON> findTop4ByCreatedByOrderByBidCount(EcUser user) {
        /*
        Sort sort = new Sort(Sort.Direction.DESC, "bidCount");
        PageRequest pageRequest = new PageRequest(0, 4, sort);
        Page<Item> items=ir.findByCreatedBy(user,pageRequest);
        return Lists.newArrayList(items);
        */
        Query query = em.createQuery("select i from Item i where i.createdBy=:user and i.state=:state order by i.bidCount desc");
        query.setParameter("user", user).setParameter("state", ItemState.出售中);
        List<Item> list = query.setMaxResults(4).getResultList();
        List<ItemJSON> result = new ArrayList<ItemJSON>();
        for (Item i : list) {
            if (i instanceof Fabric)
                i.setUrl("fabric");
            else if (i instanceof Material)
                i.setUrl("material");
            result.add(new ItemJSON(i));
        }
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Item> findByCreatedBy(EcUser user, Pageable pageable) {
        return ir.findByCreatedBy(user, pageable);
    }

    @Override
    public int checkItemType(String proId) {
        Material material = null;
        material = mr.findOne(proId);
        if (null != material) {
            return 1;
        }
        return 0;
    }

    @Override
    public Page<Item> findByUserIdAndCategoryIdAndType(String uid, String secondCategoryId, Integer type, Pageable pageable) {
        Page<Item> page = null;
        EcUser user = er.findOne(uid);
        if (secondCategoryId == null)
            page = this.ir.findByCreatedBy(user, pageable);
        else {
            if (type == 0) {
                FabricCategory fabricCategory = this.fabricCategoryRepository.findOne(secondCategoryId);
                page = (Page) this.fr.findByCreatedByAndCategory(user, fabricCategory, pageable);
            } else {
                MaterialCategory materialCategory = this.materialCategoryRepository.findOne(secondCategoryId);
                page = (Page) this.mr.findByCreatedByAndCategory(user, materialCategory, pageable);
            }
        }

        return page;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Item> findByCreatedByAndStateAndNameLike(EcUser user, ItemState state, String name, Pageable pageable) {
        return ir.findByCreatedByAndStateAndNameLike(user, state, name, pageable);
    }

    @Override
    public Page<Item> findByCreatedByAndState(EcUser user, ItemState state, Pageable pageable) {
        return ir.findByCreatedByAndState(user, state, pageable);
    }

    @Override
    public Item updateState(Item item) {
        Item source = em.find(Item.class, item.getId());
        source.setState(item.getState());
        if(item.getState().equals(ItemState.下架)){
            template.opsForHash().put(ConstantKey.REDIS_ITEM+item.getId(),ItemKey.state,item.getState().name());
        }
        return source;
    }

    @Override
    public Page<Item> findItemsByProviderAndMaterialCategory(EcUser user, MaterialCategory materialCategory, String proName, Pageable pageable) {
        String querySql = "select i from Item i,Material m where i.id=m.id and i.createdBy=:user and i.state=:state ";
        String countSql = "select count(i) from Item i,Material m where i.id=m.id and i.createdBy=:user and i.state=:state ";
        if (materialCategory != null) {
            querySql = querySql + " and m.category=:category";
            countSql = countSql + " and m.category=:category";
        }
        if (proName != null) {
            querySql = querySql + " and i.name like :proName";
            countSql = countSql + " and i.name like :proName";
        }
        Query query = em.createQuery(querySql).setParameter("user", user).setParameter("state", ItemState.出售中);
        Query countQuery = em.createQuery(countSql).setParameter("user", user).setParameter("state", ItemState.出售中);
        if (materialCategory != null) {
            query.setParameter("category", materialCategory);
            countQuery.setParameter("category", materialCategory);
        }
        if (proName != null) {
            query.setParameter("proName", "%" + proName.trim() + "%");
            countQuery.setParameter("proName", "%" + proName.trim() + "%");
        }
        List<Item> result = query.setFirstResult(pageable.getOffset()).setMaxResults(pageable.getPageSize()).getResultList();
        Long len = (Long) countQuery.getSingleResult();
        Page<Item> page = null;
        page = new PageImpl<Item>(result, pageable, len);
        return page;
    }

    @Override
    public Page<Item> findItemsByProviderAndFabricCategory(EcUser user, FabricCategory fabricCategory, String proName, Pageable pageable) {
        String querySql = "select i from Item i,Fabric f where i.id=f.id and i.createdBy=:user and i.state=:state ";
        String countSql = "select count(i) from Item i,Fabric f where i.id=f.id and i.createdBy=:user and i.state=:state ";
        if (fabricCategory != null) {
            querySql = querySql + " and f.category=:category";
            countSql = countSql + " and f.category=:category";
        }
        if (proName != null) {
            querySql = querySql + " and i.name like :proName";
            countSql = countSql + " and i.name like :proName";
        }
        Query query = em.createQuery(querySql).setParameter("user", user).setParameter("state", ItemState.出售中);
        Query countQuery = em.createQuery(countSql).setParameter("user", user).setParameter("state", ItemState.出售中);
        if (fabricCategory != null) {
            query.setParameter("category", fabricCategory);
            countQuery.setParameter("category", fabricCategory);
        }
        if (proName != null) {
            query.setParameter("proName", "%" + proName.trim() + "%");
            countQuery.setParameter("proName", "%" + proName.trim() + "%");
        }
        List<Item> result = query.setFirstResult(pageable.getOffset()).setMaxResults(pageable.getPageSize()).getResultList();
        Long len = (Long) countQuery.getSingleResult();
        Page<Item> page = null;
        page = new PageImpl<Item>(result, pageable, len);
        return page;
    }

    @Override
    public boolean countByIdAndCreatedBy(String id, EcUser user) {
        Long count = ir.countByIdAndCreatedBy(id, user);
        return count > 0;
    }

    @Override
    public Long countByCreatedByAndState(EcUser user, ItemState state) {
        return ir.countByCreatedByAndState(user, state);
    }

    @Override
    public Long itemCount(ItemState state) {
        return ir.countByState(state);
    }

    @Override
    public Page<Item> findByCreatedByAndStateAndNameLikeOrCustomIdLike(EcUser user, String name, Pageable pageable) {
        String querySql = "select i from Item i where i.createdBy=:user and (i.name like :name or i.customId like :name)";
        String countSql = "select count(i) from Item i where i.createdBy=:user and (i.name like :name or i.customId like :name) ";
        Query query = em.createQuery(querySql).setParameter("user", user).setParameter("name", "%" + name.trim() + "%");
        Query countQuery = em.createQuery(countSql).setParameter("user", user).setParameter("name", "%" + name.trim() + "%");
        List<Item> result = query.setFirstResult(pageable.getOffset()).setMaxResults(pageable.getPageSize()).getResultList();
        Long len = (Long) countQuery.getSingleResult();
        Page<Item> page = null;
        page = new PageImpl<Item>(result, pageable, len);
        return page;
    }
}
