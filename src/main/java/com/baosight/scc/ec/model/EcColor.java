package com.baosight.scc.ec.model;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by zodiake on 2014/7/9.
 */
@Embeddable
public class EcColor implements Serializable,Cloneable {
    private String id;
    private String rgb;
    private String name;
    private String hierarchy;

    public EcColor() {
    }

    public EcColor(String id, String rgb) {
        this.id = id;
        this.rgb = rgb;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRgb() {
        return rgb;
    }

    public void setRgb(String rgb) {
        this.rgb = rgb;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EcColor ecColor = (EcColor) o;

        if (id != null ? !id.equals(ecColor.id) : ecColor.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return rgb;
    }

    public String getHierarchy() {
        return hierarchy;
    }

    public void setHierarchy(String hierarchy) {
        this.hierarchy = hierarchy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
