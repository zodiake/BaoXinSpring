package com.baosight.scc.ec.model;

import com.baosight.scc.ec.utils.GuidUtil;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by Charles on 2014/7/3.
 */
@Entity
@Table(name = "T_ec_SampleLine")
public class SampleLine {
    @Id
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="sampleOrder_id")
    private SampleOrder sampleOrder;

    @OneToOne
    @JoinColumn(name="item_id")
    private Item item;
    public SampleLine() {}

    public SampleLine(Item item) {
        setId(GuidUtil.newGuid());
        setItem(item);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SampleOrder getSampleOrder() {

        return sampleOrder;
    }

    public void setSampleOrder(SampleOrder sampleOrder) {
        this.sampleOrder = sampleOrder;
    }

    public Item getItem() {

        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SampleLine)) return false;

        SampleLine that = (SampleLine) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
