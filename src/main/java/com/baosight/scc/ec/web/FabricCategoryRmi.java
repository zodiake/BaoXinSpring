package com.baosight.scc.ec.web;

import com.baosight.scc.ec.model.FabricCategory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zodiake on 2014/7/22.
 */
public class FabricCategoryRmi implements Serializable{
    private String id;
    private String name;
    private List<FabricCategoryRmi> children=new ArrayList<FabricCategoryRmi>();

    public FabricCategoryRmi(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FabricCategoryRmi> getChildren() {
        return children;
    }

    public void setChildren(List<FabricCategoryRmi> children) {
        this.children = children;
    }
}
