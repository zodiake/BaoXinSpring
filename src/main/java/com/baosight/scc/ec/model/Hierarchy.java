package com.baosight.scc.ec.model;

import java.io.Serializable;

/**
 * Created by zodiake on 2014/7/21.
 */
public class Hierarchy implements Serializable{
    private String name;
    private String id;

    public Hierarchy() {
    }

    public Hierarchy(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Hierarchy hierarchy = (Hierarchy) o;

        if (id != null ? !id.equals(hierarchy.id) : hierarchy.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
