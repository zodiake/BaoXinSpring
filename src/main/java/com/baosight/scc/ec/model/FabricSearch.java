package com.baosight.scc.ec.model;

import com.baosight.scc.ec.type.FabricMainUseType;
import com.baosight.scc.ec.type.FabricTechnologyType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zodiake on 2014/6/27.
 */
public class FabricSearch extends SearchParam{
    private List<FabricTechnologyType> technology = new ArrayList<FabricTechnologyType>();
    private List<FabricMainUseType> mainUse = new ArrayList<FabricMainUseType>();
    private List<EcPattern> patterns=new ArrayList<EcPattern>();
    private FabricCategory category;
    private FabricSource source;
    private Double widthRangeMin;
    private Double widthRangeMax;
    private Double weightRangeMin;
    private Double weightRangeMax;
    private String area;

    public List<FabricTechnologyType> getTechnology() {
        return technology;
    }

    public void setTechnology(List<FabricTechnologyType> technology) {
        this.technology = technology;
    }

    public List<FabricMainUseType> getMainUse() {
        return mainUse;
    }

    public void setMainUse(List<FabricMainUseType> mainUse) {
        this.mainUse = mainUse;
    }

    public FabricCategory getCategory() {
        return category;
    }

    public void setCategory(FabricCategory category) {
        this.category = category;
    }

    public FabricSource getSource() {
        return source;
    }

    public void setSource(FabricSource source) {
        this.source = source;
    }

    public Double getWidthRangeMin() {
        return widthRangeMin;
    }

    public void setWidthRangeMin(Double widthRangeMin) {
        this.widthRangeMin = widthRangeMin;
    }

    public Double getWidthRangeMax() {
        return widthRangeMax;
    }

    public void setWidthRangeMax(Double widthRangeMax) {
        this.widthRangeMax = widthRangeMax;
    }

    public Double getWeightRangeMin() {
        return weightRangeMin;
    }

    public void setWeightRangeMin(Double weightRangeMin) {
        this.weightRangeMin = weightRangeMin;
    }

    public Double getWeightRangeMax() {
        return weightRangeMax;
    }

    public void setWeightRangeMax(Double weightRangeMax) {
        this.weightRangeMax = weightRangeMax;
    }

    public List<EcPattern> getPatterns() {
        return patterns;
    }

    public void setPatterns(List<EcPattern> patterns) {
        this.patterns = patterns;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}

