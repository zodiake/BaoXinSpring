package com.baosight.scc.ec.model;


import com.baosight.scc.ec.type.MaterialProvideType;
import com.baosight.scc.ec.type.MaterialScope;
import org.joda.time.DateTime;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.binding.validation.ValidationContext;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.*;

/**
 * Created by ThinkPad on 2014/5/6.
 */
@Entity
@Table(name = "t_ec_material")
//辅料
public class Material extends Item implements Cloneable {
    /*
    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name="materialType_id")
    private MaterialType materialType;  //类型
    */
    private String materialType;

    /*
    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name="widthAndSize_id")
    private MaterialWidthAndSizeType widthAndSizeType;  //重量/厚薄
    */
    @Column(name = "widthAndSize")
    private String materialWidthAndSizeType;

    /*
    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name="materialScope_id")
    private MaterialScope materialScope;//适用范围
    */
    @ElementCollection
    @CollectionTable(name = "t_ec_materialScope", joinColumns = {@JoinColumn(name = "material_id")})
    private List<MaterialScope> materialScope = new ArrayList<MaterialScope>();

    @ElementCollection
    @CollectionTable(name = "t_ec_material_color", joinColumns = {@JoinColumn(name = "material_id")})
    private List<EcColor> colors = new ArrayList<EcColor>();

    /*
    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name="provideType_id")
    private MaterialProvideType provideType;//供货方式
    */
    @ElementCollection
    @CollectionTable(name = "t_ec_materialProvideType", joinColumns = {@JoinColumn(name = "material_id")})
    private List<MaterialProvideType> materialProvideType = new ArrayList<MaterialProvideType>();

    /*
    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name="materialMeasureType_id")
    private MaterialMeasureType measureType;//计量单位
    */
    private String materialMeasureType;

    @ElementCollection
    @CollectionTable(name = "t_ec_material_range", joinColumns = @JoinColumn(name = "material_id"))
    @MapKeyColumn(name = "unit_from")
    @Column(name = "price")
    private Map<Double, Double> ranges = new HashMap<Double, Double>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private MaterialCategory category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "firstCategory_id")
    private MaterialCategory firstCategory;

    @Transient
    private Double[] keys;  //起定量

    @Transient
    private Double[] values;//起定量对应的价格

    @ElementCollection
    @CollectionTable(name = "t_ec_CultureImage", joinColumns = @JoinColumn(name = "item_id"))
    @OrderBy(value = "orderNum asc")
    private List<CultureImage> images = new ArrayList<CultureImage>();

    @Transient
    private Map<String, Double> showRanges = new LinkedHashMap<String, Double>();

    private Double availableSum;//可售总量

    private Integer shipInterval;//发货时间

    @PrePersist
    public void prePersist() {
        DateTime dt = new DateTime();
        this.createdTime = dt.toCalendar(Locale.SIMPLIFIED_CHINESE);
    }

    @PreUpdate
    public void preUpdate() {
        DateTime dt = new DateTime();
        this.updatedTime = dt.toCalendar(Locale.SIMPLIFIED_CHINESE);
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public String getMaterialWidthAndSizeType() {
        return materialWidthAndSizeType;
    }

    public void setMaterialWidthAndSizeType(String materialWidthAndSizeType) {
        this.materialWidthAndSizeType = materialWidthAndSizeType;
    }

    public List<MaterialScope> getMaterialScope() {
        return materialScope;
    }

    public void setMaterialScope(List<MaterialScope> materialScope) {
        this.materialScope = materialScope;
    }

    public List<EcColor> getColors() {
        return colors;
    }

    public void setColors(List<EcColor> colors) {
        this.colors = colors;
    }

    public List<MaterialProvideType> getMaterialProvideType() {
        return materialProvideType;
    }

    public void setMaterialProvideType(List<MaterialProvideType> materialProvideType) {
        this.materialProvideType = materialProvideType;
    }

    public String getMaterialMeasureType() {
        return materialMeasureType;
    }

    public void setMaterialMeasureType(String materialMeasureType) {
        this.materialMeasureType = materialMeasureType;
    }

    public Map<Double, Double> getRanges() {
        return ranges;
    }

    public void setRanges(Map<Double, Double> ranges) {
        this.ranges = ranges;
    }

    public MaterialCategory getCategory() {
        return category;
    }

    public void setCategory(MaterialCategory category) {
        this.category = category;
    }

    public Double[] getKeys() {
        return keys;
    }

    public void setKeys(Double[] keys) {
        this.keys = keys;
    }

    public Double[] getValues() {
        return values;
    }

    public void setValues(Double[] values) {
        this.values = values;
    }

    public List<CultureImage> getImages() {
        return images;
    }

    public void setImages(List<CultureImage> images) {
        this.images = images;
    }

    public Map<String, Double> getShowRanges() {
        return showRanges;
    }

    public void setShowRanges(Map<String, Double> showRanges) {
        this.showRanges = showRanges;
    }

    public MaterialCategory getFirstCategory() {
        return firstCategory;
    }

    public void setFirstCategory(MaterialCategory firstCategory) {
        this.firstCategory = firstCategory;
    }

    public Double getAvailableSum() {
        return availableSum;
    }

    public void setAvailableSum(Double availableSum) {
        this.availableSum = availableSum;
    }

    public Integer getShipInterval() {
        return shipInterval;
    }

    public void setShipInterval(Integer shipInterval) {
        this.shipInterval = shipInterval;
    }

    /*
        valid material select category step
    */
    public void validateSelectCategory(ValidationContext context) {
        if (context.getUserEvent().equals("next")) {
            MessageContext messages = context.getMessageContext();
            if (category == null)
                messages.addMessage(new MessageBuilder().error().source("category").code("material_category_required").build());
            if (firstCategory == null)
                messages.addMessage(new MessageBuilder().error().source("firstCategory").code("material_category_required").build());
        }
    }

    /*
        valid material fill content step
     */
    public void validateFillContent(ValidationContext context) {
        MessageContext messages = context.getMessageContext();
        if (context.getUserEvent().equals("finish")) {
            //货品名称
            if (StringUtils.isEmpty(name))
                messages.addMessage(new MessageBuilder().error().source("name").code("material.required").build());
            if (name != null && name.length() * 2 > 50)
                messages.addMessage(new MessageBuilder().error().source("name").code("item.name.length").build());
            //end
            if (keys == null || keys.length == 0)
                messages.addMessage(new MessageBuilder().error().source("keys").code("fabric.required").build());
            if (values == null || values.length == 0)
                messages.addMessage(new MessageBuilder().error().source("values").code("fabric.required").build());
            if (keys != null && values != null) {
                for (Double i : values) {
                    if (i == null) {
                        messages.addMessage(new MessageBuilder().error().source("values").code("fabric.values.required").build());
                        break;
                    }
                }
                for (Double i : keys) {
                    if (i == null) {
                        messages.addMessage(new MessageBuilder().error().source("keys").code("fabric.keys.required").build());
                        break;
                    }
                }
            }
            //类型
            if (StringUtils.isEmpty(materialType))
                messages.addMessage(new MessageBuilder().error().source("materialType").code("material.required").build());
            //货号
            if (customId != null && customId.length() > 16)
                messages.addMessage(new MessageBuilder().error().source("customId").code("material.customId.length").build());
            //重量厚薄
            if (materialWidthAndSizeType.equals("--请选择--"))
                messages.addMessage(new MessageBuilder().error().source("materialWidthAndSizeType").code("material.required").build());
            //使用类型
            if (materialScope.size() == 0)
                messages.addMessage(new MessageBuilder().error().source("materialScope").code("material.required").build());
            //供货方式
            if (materialProvideType.size() == 0)
                messages.addMessage(new MessageBuilder().error().source("materialProvideType").code("material.required").build());
            //计量单位
            if (StringUtils.isEmpty(materialMeasureType))
                messages.addMessage(new MessageBuilder().error().source("materialMeasureType").code("material.required").build());
            if (validDateFrom != null && validDateTo != null && validDateFrom.after(validDateTo))
                messages.addMessage(new MessageBuilder().error().source("validDateFrom").code("material.date").build());
            if (validDateFrom != null && validDateTo != null && (new DateTime(validDateFrom).isBefore(new DateTime().minusDays(1)) || new DateTime(validDateTo).isBefore(new DateTime().minusDays(1))))
                messages.addMessage(new MessageBuilder().error().source("validDateFrom").code("material.validateDate").build());
            if (StringUtils.isEmpty(fakeContent))
                messages.addMessage(new MessageBuilder().error().source("fakeContent").code("fabric.required").build());
            if (name.length() * 2 > 50)
                messages.addMessage(new MessageBuilder().error().source("name").code("name_size").build());
            if (customId.length() * 2 > 32)
                messages.addMessage(new MessageBuilder().error().source("name").code("customId_size").build());
        }
        if (context.getUserEvent().equals("temporary")) {
            if (StringUtils.isEmpty(name))
                messages.addMessage(new MessageBuilder().error().source("name").code("material.required").build());
        }

    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
