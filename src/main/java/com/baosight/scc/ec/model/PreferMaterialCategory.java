package com.baosight.scc.ec.model;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by zodiake on 2014/5/13.
 */
@Embeddable
public class PreferMaterialCategory implements Serializable {
    private FabricCategory category;

    public FabricCategory getCategory() {
        return category;
    }

    public void setCategory(FabricCategory category) {
        this.category = category;
    }
}
