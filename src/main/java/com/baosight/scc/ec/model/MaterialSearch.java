package com.baosight.scc.ec.model;

import com.baosight.scc.ec.type.MaterialProvideType;
import com.baosight.scc.ec.type.MaterialScope;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zodiake on 2014/7/2.
 */
public class MaterialSearch extends SearchParam{
    private MaterialCategory category;
    private List<MaterialScope> scopes=new ArrayList<MaterialScope>();
    private List<MaterialProvideType> materialProvideType = new ArrayList<MaterialProvideType>();
    private String weight;
    private String area;

    public MaterialCategory getCategory() {
        return category;
    }

    public void setCategory(MaterialCategory category) {
        this.category = category;
    }

    public List<MaterialScope> getScopes() {
        return scopes;
    }

    public void setScopes(List<MaterialScope> scopes) {
        this.scopes = scopes;
    }

    public List<MaterialProvideType> getMaterialProvideType() {
        return materialProvideType;
    }

    public void setMaterialProvideType(List<MaterialProvideType> materialProvideType) {
        this.materialProvideType = materialProvideType;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
