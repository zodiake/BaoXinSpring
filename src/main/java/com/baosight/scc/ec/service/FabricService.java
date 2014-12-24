package com.baosight.scc.ec.service;

import com.baosight.scc.ec.model.*;
import com.baosight.scc.ec.type.ItemState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Created by zodiake on 2014/5/12.
 */
public interface FabricService {
    Page<Fabric> findByUser(EcUser user, Pageable pageable);

    Fabric findById(String id);

    Fabric findByIdAndUser(String id, EcUser u, boolean init);

    Fabric save(Fabric fabric);

    Fabric tempSave(Fabric fabric);

    Fabric update(Fabric fabric);

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
    Page<Fabric> searchItems(String id, String proName, String secondCategory, Pageable pageable);

    /**
     * 根据面料供应商、分类，查询该面料供应商下的产品分页列表信息
     *
     * @param id             供应商id
     * @param secondCategory 二级分类
     * @param pageable
     * @return
     * @author sam
     */
    Page<Fabric> searchItemsByProAndCateId(String id, String secondCategory, Pageable pageable);

    /**
     * 根据面料供应商、产品名称，查询该面料供应商下的产品分页列表信息
     *
     * @param id       供应商id
     * @param proName  产品名称
     * @param pageable
     * @return
     * @author sam
     */
    Page<Fabric> searchItemsByProIdAndName(String id, String proName, Pageable pageable);

    List<Fabric> findTop4(ItemState state,Pageable pageable);

    Page<Fabric> findByCreatedByAndState(EcUser user,ItemState state,Pageable pageable);

    Page<Fabric> findByCreatedBy(EcUser user,Pageable pageable);

    //更新搜索引擎数据 @sam
    void updateFabric(ItemState state);

    //删除搜索引擎数据 @sam
    void deleteFabrics();

    /*
    查询用户出售中的商品二级分类
     */
    List<FabricCategory> findByUserIdAndFirstCategory(String userId,String firstCategory);

    /*
    查询用户出售中的商品一级分类
     */
    public List<FabricCategory> findByUserId(String userId);

    Fabric fabricClone(String id);

    Map<String,Integer> fabricCount();
}
