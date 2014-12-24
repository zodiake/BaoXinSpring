package com.baosight.scc.ec.service;

import com.baosight.scc.ec.model.FabricSource;
import com.baosight.scc.ec.web.FabricSourceJSON;
import com.baosight.scc.ec.web.FabricSourceRmi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by zodiake on 2014/5/22.
 */
public interface FabricSourceService {
    public FabricSource findFirstSourceByName(String name);

    public FabricSource findSecondSourceByName(String name);

    public List<FabricSource> findAllFirstCategory();

    public List<FabricSource> findAllSecondCategory();

    FabricSource findById(String id);

    /**
     * 保存
     *
     * @param fabricSource
     * @return
     */
    FabricSource save(FabricSource fabricSource);

    /**
     * 更新
     *
     * @param fabricSource
     * @return
     */
    FabricSource update(FabricSource fabricSource);

    /**
     * 后台一级分类维护 Charles 2014/6/9
     *
     * @return
     */
    Page<FabricSource> findAllFirstCategoryByPage(Pageable pageable);

    /**
     * 后台二级分类维护 Charles 2014/6/9
     *
     * @return
     */
    Page<FabricSource> findAllSecondCategoryByPage(Pageable pageable);

    /**
     * 查询有效的一级分类 Charles 2014/6/9
     *
     * @return
     */
    List<FabricSource> findFirstCategoryByIsValid(int isValid);

    /**
     * 查询有效的二级分类 Charles 2014/6/9
     *
     * @return
     */
    List<FabricSource> findSecondCategoryByIsValid(int isValid);

    /**
     * 根据一级分类查询二级分类 Charles 2014/6/11
     *
     * @param fabricSource
     * @return
     */
    List<FabricSource> findByParent(FabricSource fabricSource);

    /**
     * 根据一级分类查询有效的二级分类 Charles 2014/6/11
     *
     * @param fabricSource
     * @param isValid
     * @return
     */
    List<FabricSource> findByParentAndIsValid(FabricSource fabricSource, int isValid);

    List<FabricSourceJSON> findByParentAndIsValidJSON(FabricSource fabricSource, int isValid);

    List<FabricSourceRmi> findAll();
}
