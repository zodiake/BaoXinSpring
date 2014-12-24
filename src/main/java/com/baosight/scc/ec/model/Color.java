package com.baosight.scc.ec.model;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by ThinkPad on 2014/5/6.
 */
//颜色类
@Embeddable
public class Color implements Serializable {
    private String id;

    private String rgb; //rgb颜色

    private String name;

    public Color() {
    }

    public Color(String id, String rgb) {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Color color = (Color) o;

        if (id != null ? !id.equals(color.id) : color.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
