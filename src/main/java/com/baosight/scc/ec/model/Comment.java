package com.baosight.scc.ec.model;

import com.baosight.scc.ec.type.CommentType;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by ThinkPad on 2014/5/8.
 */
@Entity
@Table(name = "T_ec_comment")
//买家评价
/*@NamedQueries({@NamedQuery(name = "Comment.findCommentsFromBuyer", query = "select c from Comment c where exists (" +
        "select a from c.item a where a.createdBy.id = ?1  order by  c.createdTime desc )"),
        @NamedQuery(name = "Comment.countCommentsFromBuyer", query = "select count(c) from Comment c where exists (" +
                "select a from c.item a where a.createdBy.id = ?1)")})*/
@NamedQueries({@NamedQuery(name="Comment.findCommentsFromBuyer",query = "select c from Comment  c where exists (" +
        "select o from c.orderItem o where o.buyer.id =?1 order by c.createdTime desc )"),
        @NamedQuery(name = "Comment.countCommentsFromBuyer",query = "select count(c) from Comment c where exists (" +
                "select  o from c.orderItem o where o.buyer.id = ?1)")})
public class Comment implements Serializable {
    @Id
    private String id;  //pk

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;  //对应的商品

  //  @OneToOne(fetch = FetchType.LAZY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private OrderItem orderItem; //对应的订单

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private EcUser user;    //评价人

    private String content; //评论内容

    @Enumerated(EnumType.STRING)
//    @NotEmpty(message = "请选择好、中、差评")
    private CommentType type=CommentType.好评;   //好中差评

    @Temporal(TemporalType.TIMESTAMP)
    protected Calendar updatedTime;        //更新日期

    @Temporal(TemporalType.TIMESTAMP)
    protected Calendar createdTime;        //创建日期

    protected Integer attitude; //服务态度

    protected Integer deliverySpeed; //发货速度

    protected Integer satisfied; //满意度

    protected Integer hasName = 0; //是否匿名，0：否，1：是

    protected Integer defaultShow=0; //默认显示商品信息,0:否，1：是

    public Comment() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public OrderItem getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
    }

    public EcUser getUser() {
        return user;
    }

    public void setUser(EcUser user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public CommentType getType() {
        return type;
    }

    public void setType(CommentType type) {
        this.type = type;
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

    public Integer getAttitude() {
        return attitude;
    }

    public void setAttitude(Integer attitude) {
        this.attitude = attitude;
    }

    public Integer getDeliverySpeed() {
        return deliverySpeed;
    }

    public void setDeliverySpeed(Integer deliverySpeed) {
        this.deliverySpeed = deliverySpeed;
    }

    public Integer getSatisfied() {
        return satisfied;
    }

    public void setSatisfied(Integer satisfied) {
        this.satisfied = satisfied;
    }

    public Integer getHasName() {
        return hasName;
    }

    public void setHasName(Integer hasName) {
        this.hasName = hasName;
    }

    public Integer getDefaultShow() {
        return defaultShow;
    }

    public void setDefaultShow(Integer defaultShow) {
        this.defaultShow = defaultShow;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comment that = (Comment) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id='" + id + '\'' +
                '}';
    }

}
