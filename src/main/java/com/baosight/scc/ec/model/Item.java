package com.baosight.scc.ec.model;

import com.baosight.scc.ec.type.ItemState;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.*;

@Entity
@Table(name = "T_ec_item")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type")
//所有商品父类
@NamedQueries({@NamedQuery(name = "item.findByUser", query = "select i from Item i where i.createdBy=:user and i.state=:state order by i.createdTime desc"),
        @NamedQuery(name = "item.findHottestItemByUser", query = "select count(o) as ordernum,o.item from OrderLine o where o.item.createdBy=:user group by o.item order by ordernum")})
public class Item implements Serializable {
    @Transient
    protected int num;

    @Id
    protected String id;                        //pk

    protected String name;                //货品F名称

    protected String customId;            //货品编号

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "content_id")
    protected EcContent content;

    @Transient
    protected String fakeContent;

    protected int bidCount=0;              //成交笔数

    @Temporal(TemporalType.TIMESTAMP)
    protected Calendar updatedTime;        //更新日期

    @Temporal(TemporalType.TIMESTAMP)
    protected Calendar createdTime;        //创建日期

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "createdBy")
    protected EcUser createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    protected Calendar validDateFrom;         //有效期开始

    @Temporal(TemporalType.TIMESTAMP)
    protected Calendar validDateTo;        //有效期结束

    @OneToMany(mappedBy = "item")
    protected List<Comment> comments = new ArrayList<Comment>();

    @OneToMany(mappedBy = "item")
    private List<FavouriteItems> favouriteItems = new ArrayList<FavouriteItems>();

    protected String coverImage;

    @Transient
    private String url;

    @Transient
    private Map<Double,Double> range=new HashMap<Double,Double>();

    @Transient
    private String tempImages[];

    private Double price;

    @Enumerated(EnumType.STRING)
    private ItemState state;

    private int viewCount=0; //浏览数

    @OneToMany(mappedBy = "item")
    protected List<SellerComment> sellerComments = new ArrayList<SellerComment>();

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
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

    public String getCustomId() {
        return customId;
    }

    public void setCustomId(String customId) {
        this.customId = customId;
    }

    public EcContent getContent() {
        return content;
    }

    public void setContent(EcContent content) {
        this.content = content;
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

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public EcUser getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(EcUser createdBy) {
        this.createdBy = createdBy;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<FavouriteItems> getFavouriteItems() {
        return favouriteItems;
    }

    public void setFavouriteItems(List<FavouriteItems> favouriteItems) {
        this.favouriteItems = favouriteItems;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getBidCount() {
        return bidCount;
    }

    public void setBidCount(int bidCount) {
        this.bidCount = bidCount;
    }

    public List<SellerComment> getSellerComments() {
        return sellerComments;
    }

    public void setSellerComments(List<SellerComment> sellerComments) {
        this.sellerComments = sellerComments;
    }

    public ItemState getState() {
        return state;
    }

    public void setState(ItemState state) {
        this.state = state;
    }

    public String[] getTempImages() {
        return tempImages;
    }

    public void setTempImages(String[] tempImages) {
        this.tempImages = tempImages;
    }

    public String getFakeContent() {
        return fakeContent;
    }

    public void setFakeContent(String fakeContent) {
        this.fakeContent = fakeContent;
    }

    @Override
    public String toString() {
        return name;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public Map<Double, Double> getRange() {
        return range;
    }

    public void setRange(Map<Double, Double> range) {
        this.range = range;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        if (id != null ? !id.equals(item.id) : item.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
