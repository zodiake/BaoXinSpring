package com.baosight.scc.ec.model;

import com.baosight.scc.ec.batch.BatchProduct;
import com.baosight.scc.ec.type.FabricMainUseType;
import com.baosight.scc.ec.type.FabricProvideType;
import com.baosight.scc.ec.utils.GuidUtil;
import org.joda.time.DateTime;
import org.joda.time.ReadableInstant;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.binding.validation.ValidationContext;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.*;

/**
 * Created by ThinkPad on 2014/5/5.
 */
@Entity
@Table(name = "T_ec_fabric")
//面料
@DiscriminatorValue("f")
@NamedQueries({@NamedQuery(name = "fabric.findByIdAndCreatedBy", query = "select f from Fabric f where f.id=?1 and f.createdBy=?2")})
public class Fabric extends Item implements Cloneable {

    /*
    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name="fabricSeasonType_id")
    private FabricSeasonType fabricSeasonType;  //适用季节
    */

    private String ingredient;      //成分

    private String yarn;            //纱支

    private String density;         //密度

    /*
    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name="width_id")
    private FabricWidthType fabricWidthType;    //幅宽
    */
    private String fabricWidthType;

    /*
    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name="heightType_id")
    private FabricHeightType fabricHeightType;  //克重
    */
    private String fabricHeightType;

    @Transient
    private Double fakeHeight;

    @Transient
    private Double fakeWeight;
    /*
    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name="technology_id")
    private FabricTechnology fabricTechnology;  //染整工艺
    */
    private String fabricTechnologyId;

    private String fabricSecondTechnologyId;

    @ElementCollection
    @CollectionTable(name = "t_ec_fabric_color", joinColumns = {@JoinColumn(name = "fabric_id")})
    private List<EcColor> colors = new ArrayList<EcColor>();

    /*
    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name="provideType_id")
    private FabricProvideType provideType;    //供货方式
    */
    @ElementCollection
    @CollectionTable(name = "t_ec_fabricProvideType", joinColumns = {@JoinColumn(name = "fabric_id")})
    private List<FabricProvideType> fabricProvideType = new ArrayList<FabricProvideType>();

    /*
    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name="FabricMeasureType_id")
    private FabricMeasureType measureType;    //计量单位
    */
    private String fabricMeasureType;

    @Transient
    private Double[] keys;

    @Transient
    private Double[] values;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_id")
    private FabricSource source;    //面料成分一级

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "detailSource_id")
    private FabricSource sourceDetail;

    @ElementCollection
    @CollectionTable(name = "t_ec_fabric_range", joinColumns = @JoinColumn(name = "fabric_id"))
    @MapKeyColumn(name = "unit_from")
    @Column(name = "price")
    private Map<Double, Double> ranges = new HashMap<Double, Double>();

    @Transient
    private Map<String, Double> showRanges = new LinkedHashMap<String, Double>();

    private Integer shipInterval;   //发货时间

    @ElementCollection
    @CollectionTable(name = "t_ec_CultureImage", joinColumns = @JoinColumn(name = "item_id"))
    @OrderBy(value = "orderNum asc")
    private List<CultureImage> images = new ArrayList<CultureImage>();

    @ElementCollection
    @CollectionTable(name = "t_ec_pattern", joinColumns = @JoinColumn(name = "item_id"))
    private List<EcPattern> patterns = new ArrayList<EcPattern>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private FabricCategory category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "first_category_id")
    private FabricCategory firstCategory;

    @ElementCollection
    @CollectionTable(name = "t_ec_fabric_mainUse", joinColumns = @JoinColumn(name = "fabric_id"))
    private List<FabricMainUseType> mainUseTypes = new ArrayList<FabricMainUseType>();

    private Double availableSum;    //可售总量

    private String itemNumber;

    @ElementCollection
    @CollectionTable(name = "T_ec_item_seasonType", joinColumns = @JoinColumn(name = "fabric_id"))
    private List<Season> seasons = new ArrayList<Season>();

    @PrePersist
    private void setTheCreatedTime() {
        DateTime dt = new DateTime();
        this.createdTime = dt.toCalendar(Locale.SIMPLIFIED_CHINESE);
    }

    @PreUpdate
    private void setTheUpdatedTime() {
        DateTime dt = new DateTime();
        this.updatedTime = dt.toCalendar(Locale.SIMPLIFIED_CHINESE);
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getYarn() {
        return yarn;
    }

    public void setYarn(String yarn) {
        this.yarn = yarn;
    }

    public String getDensity() {
        return density;
    }

    public void setDensity(String density) {
        this.density = density;
    }

    public String getFabricWidthType() {
        return fabricWidthType;
    }

    public void setFabricWidthType(String fabricWidthType) {
        this.fabricWidthType = fabricWidthType;
    }

    public String getFabricHeightType() {
        return fabricHeightType;
    }

    public void setFabricHeightType(String fabricHeightType) {
        this.fabricHeightType = fabricHeightType;
    }

    public List<EcColor> getColors() {
        return colors;
    }

    public void setColors(List<EcColor> colors) {
        this.colors = colors;
    }

    public List<FabricProvideType> getFabricProvideType() {
        return fabricProvideType;
    }

    public void setFabricProvideType(List<FabricProvideType> fabricProvideType) {
        this.fabricProvideType = fabricProvideType;
    }

    public String getFabricMeasureType() {
        return fabricMeasureType;
    }

    public void setFabricMeasureType(String fabricMeasureType) {
        this.fabricMeasureType = fabricMeasureType;
    }

    public Map<Double, Double> getRanges() {
        return ranges;
    }

    public void setRanges(Map<Double, Double> ranges) {
        this.ranges = ranges;
    }

    public List<CultureImage> getImages() {
        return images;
    }

    public void setImages(List<CultureImage> images) {
        this.images = images;
    }

    public FabricCategory getCategory() {
        return category;
    }

    public void setCategory(FabricCategory category) {
        this.category = category;
    }

    public List<FabricMainUseType> getMainUseTypes() {
        return mainUseTypes;
    }

    public void setMainUseTypes(List<FabricMainUseType> mainUseTypes) {
        this.mainUseTypes = mainUseTypes;
    }

    public Double getAvailableSum() {
        return availableSum;
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

    public FabricSource getSource() {
        return source;
    }

    public void setSource(FabricSource source) {
        this.source = source;
    }

    public FabricSource getSourceDetail() {
        return sourceDetail;
    }

    public void setSourceDetail(FabricSource sourceDetail) {
        this.sourceDetail = sourceDetail;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public Map<String, Double> getShowRanges() {
        return showRanges;
    }

    public void setShowRanges(Map<String, Double> showRanges) {
        this.showRanges = showRanges;
    }

    public FabricCategory getFirstCategory() {
        return firstCategory;
    }

    public void setFirstCategory(FabricCategory firstCategory) {
        this.firstCategory = firstCategory;
    }

    public List<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<Season> seasons) {
        this.seasons = seasons;
    }

    public String getFabricTechnologyId() {
        return fabricTechnologyId;
    }

    public void setFabricTechnologyId(String fabricTechnologyId) {
        this.fabricTechnologyId = fabricTechnologyId;
    }

    public String getFabricSecondTechnologyId() {
        return fabricSecondTechnologyId;
    }

    public void setFabricSecondTechnologyId(String fabricSecondTechnologyId) {
        this.fabricSecondTechnologyId = fabricSecondTechnologyId;
    }

    public List<EcPattern> getPatterns() {
        return patterns;
    }

    public void setPatterns(List<EcPattern> patterns) {
        this.patterns = patterns;
    }

    public Integer getShipInterval() {
        return shipInterval;
    }

    public void setShipInterval(Integer shipInterval) {
        this.shipInterval = shipInterval;
    }

    public void setAvailableSum(Double availableSum) {
        this.availableSum = availableSum;
    }

    public Double getFakeWeight() {
        return fakeWeight;
    }

    public void setFakeWeight(Double fakeWeight) {
        this.fakeWeight = fakeWeight;
    }

    public Double getFakeHeight() {
        return fakeHeight;
    }

    public void setFakeHeight(Double fakeHeight) {
        this.fakeHeight = fakeHeight;
    }

    public void validateSelectCategory(ValidationContext context) {
        if (context.getUserEvent().equals("next")) {
            MessageContext messages = context.getMessageContext();
            if (mainUseTypes == null)
                messages.addMessage(new MessageBuilder().error().source("mainUseTypes").code("fabric.required").build());
            if (firstCategory == null)
                messages.addMessage(new MessageBuilder().error().source("firstCategory").code("fabric.required").build());
            if (category == null)
                messages.addMessage(new MessageBuilder().error().source("category").code("fabric.required").build());
            if (sourceDetail == null)
                messages.addMessage(new MessageBuilder().error().source("sourceDetail").code("fabric.required").build());
            if (source == null)
                messages.addMessage(new MessageBuilder().error().source("source").code("fabric.required").build());
        }
    }

    public void validateFillContent(ValidationContext context) {
        if (context.getUserEvent().equals("finish")) {
            MessageContext messages = context.getMessageContext();
            if (StringUtils.isEmpty(name))
                messages.addMessage(new MessageBuilder().error().source("name").code("fabric.required").build());
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
            if (CollectionUtils.isEmpty(seasons))
                messages.addMessage(new MessageBuilder().error().source("seasons").code("fabric.required").build());
            if (StringUtils.isEmpty(ingredient))
                messages.addMessage(new MessageBuilder().error().source("ingredient").code("fabric.required").build());
            if (StringUtils.isEmpty(fakeWeight))
                messages.addMessage(new MessageBuilder().error().source("fakeWeight").code("fabric.required").build());
            if (fabricProvideType.size() == 0)
                messages.addMessage(new MessageBuilder().error().source("fabricProvideType").code("fabric.required").build());
            if (StringUtils.isEmpty(fabricMeasureType))
                messages.addMessage(new MessageBuilder().error().source("fabricMeasureType").code("fabric.required").build());
            if (StringUtils.isEmpty(fakeContent))
                messages.addMessage(new MessageBuilder().error().source("fakeContent").code("fabric.required").build());
            if (fabricProvideType.size() == 0)
                messages.addMessage(new MessageBuilder().error().source("fabricProvideType ").code("fabric.required").build());
            if (validDateFrom != null && validDateTo != null && validDateFrom.after(validDateTo))
                messages.addMessage(new MessageBuilder().error().source("validDateFrom").code("material.date").build());
            if (colors != null && colors.size() > 10)
                messages.addMessage(new MessageBuilder().error().source("colors").code("color_size").build());
            if (name.length() * 2 > 50)
                messages.addMessage(new MessageBuilder().error().source("name").code("item.name.length").build());
            if (customId.length() * 2 > 32)
                messages.addMessage(new MessageBuilder().error().source("customId").code("customId_size").build());
            if (validDateFrom != null && validDateTo != null && (new DateTime(validDateFrom).isBefore(new DateTime().minusDays(1)) || new DateTime(validDateTo).isBefore(new DateTime().minusDays(1))))
                messages.addMessage(new MessageBuilder().error().source("validDateFrom").code("material.validateDate").build());
        }
        if (context.getUserEvent().equals("temporary")) {
            MessageContext messages = context.getMessageContext();
            if (StringUtils.isEmpty(name))
                messages.addMessage(new MessageBuilder().error().source("name").code("fabric.required").build());
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
