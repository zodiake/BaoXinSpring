package com.baosight.scc.ec.web;

import com.baosight.scc.ec.model.EcPattern;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zodiake on 2014/7/28.
 */
public class EcPatternJSON {
    private String id;
    private String name;
    private List<EcPatternJSON> children=new ArrayList<EcPatternJSON>();

    public EcPatternJSON(EcPattern pattern) {
        this.id = pattern.getId();
        this.name = pattern.getName();
    }

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

    public List<EcPatternJSON> getChildren() {
        return children;
    }

    public void setChildren(List<EcPatternJSON> children) {
        this.children = children;
    }
}
