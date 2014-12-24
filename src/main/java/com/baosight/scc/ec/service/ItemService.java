package com.baosight.scc.ec.service;

import com.baosight.scc.ec.model.EcUser;
import com.baosight.scc.ec.model.FabricCategory;
import com.baosight.scc.ec.model.Item;
import com.baosight.scc.ec.model.MaterialCategory;
import com.baosight.scc.ec.type.ItemState;
import com.baosight.scc.ec.web.ItemJSON;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.Query;
import java.util.List;

/**
 * Created by Charles on 2014/5/19.
 */
public interface ItemService {

    /**
     * 查询单个商品
     * @param id
     * @return
     */
    Item findById(String id);

    Item findByIdThroughRedis(String id);

    /**
     * 判断产品类型，0：面料，1：辅料
     * @param proId 产品id
     * @return
     */
    int checkItemType(String proId);

    /**
     * 根据用户id、二级分类id，查询该用户下下的所有产品列表
     * @param uid 用户id
     * @param type 分类类型，0：面料分类，1辅料分类
     * @param secondCategoryId 二级分类id
     * @param pageable
     * @return
     */
    Page<Item> findByUserIdAndCategoryIdAndType(String uid,String secondCategoryId,Integer type,Pageable pageable);

    int itemTypeById(String id);
    
    public List<ItemJSON> findTop4ByCreatedByOrderByBidCount(EcUser user);

    public Page<Item> findByCreatedBy(EcUser user ,Pageable pageable);

    public Page<Item> findByCreatedByAndStateAndNameLike(EcUser user ,ItemState state,String name,Pageable pageable);

    Page<Item> findByCreatedByAndState(EcUser user, ItemState state,Pageable pageable);

    public Item updateState(Item item);

    public Page<Item> findItemsByProviderAndMaterialCategory(EcUser user,MaterialCategory materialCategory,String proName,Pageable pageable);

    public Page<Item> findItemsByProviderAndFabricCategory(EcUser user, FabricCategory fabricCategory,String proName, Pageable pageable);

    public boolean countByIdAndCreatedBy(String id,EcUser user);

    public Long countByCreatedByAndState(EcUser user,ItemState state);

    public Long itemCount(ItemState state);

    public Page<Item> findByCreatedByAndStateAndNameLikeOrCustomIdLike(EcUser user ,String name,Pageable pageable);
}
