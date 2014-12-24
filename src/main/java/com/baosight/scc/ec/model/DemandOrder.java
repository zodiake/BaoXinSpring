package com.baosight.scc.ec.model;

import com.baosight.scc.ec.type.DemandOrderState;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by ThinkPad on 2014/5/8.
 */
@Entity
@Table(name = "T_ec_DemandOrder")
//求购单
public class DemandOrder implements Serializable {
    @Id
    private String id;

    private String exceptionAddress;    //期望所在地

    @Size(min=1,max=50,message = "字数在1-50之间")
// @NotEmpty(message = "不能为空")
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private EcUser createdBy;   //创建人
    @NotNull
    private String demandType;  //求购类型
    @Size(min=1,max = 2000,message = "字数不能够超过2000")
    private String content; //内容
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar validDateFrom; //有效期开始
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar validDateTo;   //有效期结束

    @NotEmpty(message = "选择供货方式")
    @ElementCollection
    @CollectionTable(name = "t_ec_demandOrderProvideType", joinColumns = {@JoinColumn(name = "demandOrder_id")})
    @Column(name="name")
    private List<String> provideType = new ArrayList<String>();

    @NotNull(message="不能为空")
    private String unit;    //计量单位

    private double priceFrom;   //价格区间开始

    private double priceTo; //价格区间结束
    @NotNull(message = "不能为空")
    private double demandSum;   //求购总量

    private int deliveryDuration;   //发货时间

    @ElementCollection
    @CollectionTable(name = "T_ec_DemandOrderImage", joinColumns = @JoinColumn(name = "demandOrder_id"))
    private List<CultureImage> images;  //图片列表

    @Temporal(TemporalType.TIMESTAMP)
    protected Calendar updatedTime;        //更新日期

    @Temporal(TemporalType.TIMESTAMP)
    protected Calendar createdTime;        //创建日期

    @Enumerated(EnumType.STRING)
    private DemandOrderState state;

    @Transient
    private String tempImages[];
    protected String coverImage; //封面图

    private String demandOrderNo; //求购单号

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExceptionAddress() {
        return exceptionAddress;
    }

    public void setExceptionAddress(String exceptionAddress) {
        this.exceptionAddress = exceptionAddress;
    }

    public EcUser getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(EcUser createdBy) {
        this.createdBy = createdBy;
    }

    public String getDemandType() {
        return demandType;
    }

    public void setDemandType(String demandType) {
        this.demandType = demandType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Calendar getValidDateFrom() {
        return validDateFrom;
    }

    public void setValidDateFrom(Calendar validDateFrom) {
        this.validDateFrom = validDateFrom;
    }

    public Calendar getValidDateTo() {
        return validDateTo;
    }

    public void setValidDateTo(Calendar validDateTo) {
        this.validDateTo = validDateTo;
    }

    public List<String> getProvideType() {
        return provideType;
    }

    public void setProvideType(List<String> provideType) {
        this.provideType = provideType;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getPriceFrom() {
        return priceFrom;
    }

    public void setPriceFrom(double priceFrom) {
        this.priceFrom = priceFrom;
    }

    public double getPriceTo() {
        return priceTo;
    }

    public void setPriceTo(double priceTo) {
        this.priceTo = priceTo;
    }

    public double getDemandSum() {
        return demandSum;
    }

    public void setDemandSum(double demandSum) {
        this.demandSum = demandSum;
    }

    public int getDeliveryDuration() {
        return deliveryDuration;
    }

    public void setDeliveryDuration(int deliveryDuration) {
        this.deliveryDuration = deliveryDuration;
    }

    public List<CultureImage> getImages() {
        return images;
    }

    public void setImages(List<CultureImage> images) {
        this.images = images;
    }

    public Calendar getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Calendar updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Calendar getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Calendar createdTime) {
        this.createdTime = createdTime;
    }

    public DemandOrderState getState() {
        return state;
    }

    public void setState(DemandOrderState state) {
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getTempImages() {
        return tempImages;
    }

    public void setTempImages(String[] tempImages) {
        this.tempImages = tempImages;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getDemandOrderNo() {
        return demandOrderNo;
    }

    public void setDemandOrderNo(String demandOrderNo) {
        this.demandOrderNo = demandOrderNo;
    }
}
