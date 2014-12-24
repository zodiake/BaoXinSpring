package com.baosight.scc.ec.service.search;

import com.baosight.common.utils.StringUtil;
import com.baosight.scc.ec.model.*;
import com.baosight.scc.ec.service.EcProviderService;
import com.baosight.scc.ec.service.FabricIndexService;
import com.baosight.scc.ec.service.FabricTechnologyTypeService;
import com.baosight.scc.ec.type.FabricMainUseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2014/9/24.
 */
@Service
public class FabricIndexServiceImpl implements FabricIndexService{
    private EcProviderService providerService;

    private FabricTechnologyTypeService technologyTypeService;

    @Autowired
    public FabricIndexServiceImpl(EcProviderService providerService,FabricTechnologyTypeService technologyTypeService){
        this.providerService=providerService;
        this.technologyTypeService=technologyTypeService;
    }


    @Override
    public FabricIndex transferFromFabric(Fabric fabric) {
        FabricIndex index=new FabricIndex();
        index.setId(fabric.getId());
        index.setTitle(fabric.getName());
        index.setSeasons(getSeasons(fabric));
        index.setColors(getColors(fabric));
        index.setCategory(fabric.getCategory().getName());
        setAreaAndCompany(index,fabric);
        index.setTechnology(fabric.getFabricSecondTechnologyId());
        index.setUse(getUse(fabric));
        index.setWidth(Double.parseDouble(fabric.getFabricWidthType()));
        index.setWeight(Double.parseDouble(fabric.getFabricHeightType()));
        index.setPrice(fabric.getPrice());
        index.setSource(fabric.getSource().getName());
        index.setPattern(getPattern(fabric));
        index.setCover(fabric.getCoverImage());
        index.setViewCount(fabric.getViewCount());
        index.setSales(fabric.getBidCount());
        index.setCreatedBy(StringUtils.trimTrailingWhitespace(fabric.getCreatedBy().getId()));
        return index;
    }

    private List<String> getSeasons(Fabric fabric){
        List<Season> seasons = fabric.getSeasons();
        List<String> seasonString = new ArrayList<String>();
        if (seasons.size() > 0) {
            for (Season s : seasons) {
                seasonString.add(s.toString());
            }
        }
        return seasonString;
    }

    private List<String> getColors(Fabric fabric){
        List<EcColor> colors = fabric.getColors();
        List<String> colorString = new ArrayList<String>();
        for (EcColor c : colors) {
            colorString.add(c.getRgb());
        }
        return colorString;
    }

    private void setAreaAndCompany(FabricIndex index,Fabric fabric){
        EcProvider provider = providerService.findOne(fabric.getCreatedBy().getId());
        index.setArea(provider.getBusinessLicRegisterProv());
        index.setCompany(provider.getCompanyName());
    }

    private String getTechnology(Fabric fabric){
        if(!StringUtil.isEmpty(fabric.getFabricSecondTechnologyId())&&!fabric.getFabricSecondTechnologyId().equals("--请选择--"))
            return technologyTypeService.findOne(fabric.getFabricSecondTechnologyId()).getName();
        return null;
    }

    private List<String> getUse(Fabric fabric){
        List<FabricMainUseType> use=fabric.getMainUseTypes();
        List<String> result=new ArrayList<String>();
        for(FabricMainUseType f:use){
            result.add(f.getName());
        }
        return result;
    }

    private List<String> getPattern(Fabric fabric){
        List<EcPattern> patterns=fabric.getPatterns();
        List<String> result=new ArrayList<String>();
        if(patterns!=null) {
            for (EcPattern p : patterns) {
                result.add(StringUtils.trimAllWhitespace(p.getName()));
            }
        }
        if(result.size()>0)
            return result;
        else
            return null;
    }

}
