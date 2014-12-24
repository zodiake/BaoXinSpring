package com.baosight.scc.ec.model;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Map;

/**
 * Created by zodiake on 2014/6/13.
 */
@Embeddable
public class Season implements Serializable,Cloneable {
    private String id;
    private String type;

    public Season() {
    }

    public Season(Map<String, Object> map) {
        if (map.get("valueCd") != null)
            this.id = (String) map.get("valueCd");
        if (map.get("valueName") != null)
            this.type = (String) map.get("valueName");
    }

    public Season(String id, String type) {
        this.id = id;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

        Season season = (Season) o;

        if (id != null ? !id.equals(season.id) : season.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return type;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
