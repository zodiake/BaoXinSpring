package com.baosight.scc.ec.type;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zodiake on 2014/6/17.
 */
public class FabricTechnologyType implements Serializable {
    private String id;
    private String name;
    private String code;

    private List<FabricTechnologyType> secondType = new ArrayList<FabricTechnologyType>();

    private String parent;

    public FabricTechnologyType() {
    }

    public FabricTechnologyType(Map<String, Object> map) {
        this.id = (String) map.get("valueCd");
        this.name = (String) map.get("valueName");
        this.code = (String) map.get("allValueCd");
        this.parent=(String) map.get("parentValueId");
    }

    public FabricTechnologyType(String id, String name, List<FabricTechnologyType> types) {
        this.id = id;
        this.name = name;
        this.secondType = types;
    }

    public FabricTechnologyType(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<FabricTechnologyType> getSecondType() {
        return secondType;
    }

    public void setSecondType(List<FabricTechnologyType> secondType) {
        this.secondType = secondType;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
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

        FabricTechnologyType that = (FabricTechnologyType) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return name;
    }
}
