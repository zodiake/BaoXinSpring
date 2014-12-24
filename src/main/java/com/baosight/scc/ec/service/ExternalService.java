package com.baosight.scc.ec.service;

import com.baosight.scc.ec.model.Designer;

import java.util.List;
import java.util.Map;

/**
 * Created by Charles on 2014/7/23.
 * 查询其他模块数据使用
 */
public interface ExternalService {
    /**
     * 获取首页设计师列表
     * @return
     */
    public List<Designer> designerOnHome();

    /**
     * 获取首页品牌列表
     * @return
     */
    public List<Designer> brandsOnHome();

    /**
     * 关注的设计师
     * @return
     */
    public List<Designer> attentionDesigners(int pageTo,int pageSize,String userId);


    /**
     * 关注的供应商
     * @return
     */
    public List<Designer> attentionBrands(int pageTo,int pageSize,String userId);


    /**
     * 关注的设计师数量
     * @return
     */
    public int totalDesigners(String userId);


    /**
     * 关注的供应商数量
     * @return
     */
    public int totalBrands(String userId);

    public Map<String,String> statistics();

    public Map<String, String> demandStatistics();

    /**
     * 取消关注设计师
     * @param userId
     * @param designerId
     */
    public int cancelAttentionDesigner(String userId,String designerId);

    /**
     * 取消关注品牌
     * @param userId
     * @param brandId
     */
    public int cancelAttentionBrand(String userId,String brandId);

    /**
     * 推荐的设计师
     * @return
     */
    public List<Designer> recommendDesigners(String userId);


    /**
     * 推荐的供应商
     * @return
     */
    public List<Designer> recommendBrands(String userId);

    /**
     * 获取首页订货会列表
     * @return
     */
    public List<Designer> orderingOnHome();
}
