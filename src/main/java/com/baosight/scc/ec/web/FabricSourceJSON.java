package com.baosight.scc.ec.web;

import com.baosight.scc.ec.model.FabricSource;

import java.io.Serializable;

/**
 * Created by zodiake on 2014/6/12.
 */
public class FabricSourceJSON implements Serializable {
    private String id;
    private String name;

    public FabricSourceJSON(FabricSource source) {
        this.id=source.getId();
        this.name=source.getName();
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
}
