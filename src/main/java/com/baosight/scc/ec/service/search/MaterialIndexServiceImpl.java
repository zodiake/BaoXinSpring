package com.baosight.scc.ec.service.search;

import com.baosight.scc.ec.model.EcProvider;
import com.baosight.scc.ec.model.Material;
import com.baosight.scc.ec.model.MaterialIndex;
import com.baosight.scc.ec.service.EcProviderService;
import com.baosight.scc.ec.service.MaterialIndexService;
import com.baosight.scc.ec.type.MaterialProvideType;
import com.baosight.scc.ec.type.MaterialScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2014/9/24.
 */
@Service
public class MaterialIndexServiceImpl implements MaterialIndexService{
    @Autowired
    private EcProviderService providerService;

    @Override
    public MaterialIndex transferFromMaterial(Material material) {
        MaterialIndex index=new MaterialIndex();
        index.setId(material.getId());
        index.setTitle(material.getName());
        index.setCategory(material.getCategory().getName());
        setAreaAndCompany(index,material);
        index.setProvideTypeList(getMaterialProvideType(material));
        index.setUse(getMainUse(material));
        index.setWeight(material.getMaterialWidthAndSizeType());
        index.setPrice(material.getPrice());
        index.setCover(material.getCoverImage());
        index.setViewCount(material.getViewCount());
        index.setSales(material.getBidCount());
        index.setCreatedBy(material.getCreatedBy().getId());
        return index;
    }

    private void setAreaAndCompany(MaterialIndex index,Material material){
        EcProvider provider=providerService.findOne(material.getId());
        index.setArea(provider.getBusinessLicRegisterProv());
        index.setCompany(provider.getCompanyName());
    }

    private List<String> getMaterialProvideType(Material material){
        List<MaterialProvideType> source=material.getMaterialProvideType();
        List<String> result=new ArrayList<String>();
        for(MaterialProvideType t:source){
            result.add(t.getName());
        }
        return result;
    }

    private List<String> getMainUse(Material material){
        List<MaterialScope> source=material.getMaterialScope();
        List<String> result=new ArrayList<String>();
        for(MaterialScope t:source){
            result.add(t.getName());
        }
        return result;

    }
}
