package com.baosight.scc.ec.type;

import com.baosight.scc.ec.model.Fabric;

import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * Created by ThinkPad on 2014/5/6.
 */
//@Entity
//@Table(name="ec_FabricMeasureType")
public class FabricMeasureType{
    @Id
    private String id;

    private String name;

    @OneToOne(mappedBy = "measureType")
    private Fabric fabric;
}
