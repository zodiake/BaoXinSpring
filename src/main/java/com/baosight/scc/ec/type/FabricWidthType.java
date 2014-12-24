package com.baosight.scc.ec.type;

import com.baosight.scc.ec.model.Fabric;

import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.Map;

/**
 * Created by ThinkPad on 2014/5/5.
 */
//@Entity
//@Table(name="ec_fabricWidthType")
public class FabricWidthType {
    private String id;

    private String name;

    public FabricWidthType() {
    }

    public FabricWidthType(Map<String, Object> map) {
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
}
