package com.baosight.scc.ec.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zodiake on 2014/7/22.
 */
public class FabricSourceRmi implements Serializable{
    private String id;
    private String name;
    private List<FabricSourceRmi> children=new ArrayList<FabricSourceRmi>();

    public FabricSourceRmi(){}

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

    public List<FabricSourceRmi> getChildren() {
        return children;
    }

    public void setChildren(List<FabricSourceRmi> children) {
        this.children = children;
    }
}
