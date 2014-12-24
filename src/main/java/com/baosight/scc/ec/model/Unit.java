package com.baosight.scc.ec.model;

import java.util.Map;

/**
 * Created by zodiake on 2014/7/24.
 */
public class Unit {
    private String id;
    private String name;

    public Unit() {
    }

    public Unit(Map<String, Object> map) {
        if (map.get("valueCd") != null)
            this.id = (String) map.get("valueName");
        if (map.get("valueName") != null)
            this.name = (String) map.get("valueName");
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

        Unit unit = (Unit) o;

        if (id != null ? !id.equals(unit.id) : unit.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
