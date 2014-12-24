package com.baosight.scc.ec.type;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Map;

@Embeddable
public class MaterialScope implements Serializable,Cloneable {
    private String id;
    private String name;

    public MaterialScope() {
    }

    public MaterialScope(Map<String, Object> map) {
        this.id = (String) map.get("valueCd");
        this.name = (String) map.get("valueName");
    }

    public MaterialScope(String id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MaterialScope that = (MaterialScope) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}