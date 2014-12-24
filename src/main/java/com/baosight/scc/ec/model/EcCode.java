package com.baosight.scc.ec.model;

import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zodiake on 2014/7/9.
 */
public class EcCode {
    protected String id;
    protected String name;
    @Transient
    protected String code;
    @Transient
    protected List<EcCode> children = new ArrayList<EcCode>();

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<EcCode> getChildren() {
        return children;
    }

    public void setChildren(List<EcCode> children) {
        this.children = children;
    }
}
