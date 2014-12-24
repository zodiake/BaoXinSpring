package com.baosight.scc.ec.model;

import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zodiake on 2014/7/7.
 */
@Embeddable
public class EcPattern  implements Serializable,Cloneable{
    private String id;
    private String name;
    @Transient
    private String code;
    @Transient
    private List<EcPattern> children = new ArrayList<EcPattern>();

    public EcPattern() {
    }

    public EcPattern(String id){
        this.id=id;
    }

    public EcPattern(Map<String, Object> map) {
        this.id = (String) map.get("valueCd");
        this.name = (String) map.get("valueName");
        this.code = (String) map.get("allValueCd");
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

    public List<EcPattern> getChildren() {
        return children;
    }

    public void setChildren(List<EcPattern> children) {
        this.children = children;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EcPattern ecPattern = (EcPattern) o;

        if (id != null ? !id.equals(ecPattern.id) : ecPattern.id != null) return false;

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
