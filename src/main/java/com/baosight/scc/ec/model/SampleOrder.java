package com.baosight.scc.ec.model;

import com.baosight.scc.ec.type.SampleOrderState;
import com.baosight.scc.ec.utils.GuidUtil;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by ThinkPad on 2014/5/8.
 */
//吊样单
@Entity
@Table(name = "T_ec_SampleOrder")
@NamedQueries({
        @NamedQuery(name = "sampleOrder.findByCreatedBy", query = "select s from SampleOrder s where s.provider=?1 order by s.createdTime"),
        @NamedQuery(name = "sampleOrder.countByCreatedBy", query = "select count(s) from SampleOrder s where s.provider=?1 order by s.createdTime"),
        @NamedQuery(name = "sampleOrder.findByStateAndCreatedBy", query = "select s from SampleOrder s where s.state=?1 and s.provider=?2 order by s.createdTime"),
        @NamedQuery(name = "sampleOrder.countByStateAndCreatedBy", query = "select count(s) from SampleOrder s where s.state=?1 and s.provider=?2 order by s.createdTime")
})
public class SampleOrder implements Serializable {
    @Id
    private String id;

    private String orderNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private EcUser creator;

    @Enumerated(EnumType.STRING)
    private SampleOrderState state;

    @OneToMany(mappedBy = "sampleOrder",cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private List<SampleLine> sampleLines = new ArrayList<SampleLine>(); //调样册明细

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    @NotNull
    private SampleOrderCopyAddress address;

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar createdTime;   //创建日期

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar updatedTime;   //更新日期

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar deliveryTime;  //交货日期

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id")
    private EcUser provider;

    private String remark;  //备注信息

    private String sellerName;
    public SampleOrder(){}

    public SampleOrder(String sellerId){
        setId(GuidUtil.newGuid());
    }

    @PrePersist
    private void prePersist() {
        DateTime time = new DateTime();
        this.createdTime = time.toCalendar(Locale.SIMPLIFIED_CHINESE);
    }

    @PreUpdate
    private void preUpdate() {
        DateTime time = new DateTime();
        this.updatedTime = time.toCalendar(Locale.SIMPLIFIED_CHINESE);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public EcUser getCreator() {
        return creator;
    }

    public void setCreator(EcUser creator) {
        this.creator = creator;
    }

    public SampleOrderState getState() {
        return state;
    }

    public void setState(SampleOrderState state) {
        this.state = state;
    }

    public Calendar getCreatedTime() {
        return createdTime;
    }

    public Calendar getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Calendar deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public EcUser getProvider() {
        return provider;
    }

    public void setProvider(EcUser provider) {
        this.provider = provider;
    }

    public SampleOrderCopyAddress getAddress() {
        return address;
    }

    public void setAddress(SampleOrderCopyAddress address) {
        this.address = address;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<SampleLine> getSampleLines() {
        return sampleLines;
    }

    public void setSampleLines(List<SampleLine> sampleLines) {
        this.sampleLines = sampleLines;
    }

    public Calendar getUpdatedTime() {
        return updatedTime;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SampleOrder that = (SampleOrder) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
