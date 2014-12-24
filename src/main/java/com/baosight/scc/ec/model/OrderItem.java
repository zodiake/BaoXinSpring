package com.baosight.scc.ec.model;

import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by ThinkPad on 2014/5/6.
 */
@Entity
@Table(name="T_ec_orderItem")
//订单 因为order关键字，所以后面加了item
public class OrderItem {
    @Id
    private String id;  //订单id

    private String orderNo; //订单编号

    private double summary; // 订单总价

    private int needInvoice;    //是否需要发票

    @OneToMany(mappedBy = "orderItem")
    private List<OrderLine> lines=new ArrayList<OrderLine>();   //订单商品列表

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="buyer_id")
    private EcUser buyer;    //买家

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="seller_id")
    private EcUser seller;    //卖家

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar createdTime;   //创建时间

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar cancelTime;   //取消时间

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar deliverTime;   //发货时间

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar receiveTime;   //收货时间


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderAddress_id")
    private OrderAddress orderAddress;  //订单地址

    private String status; //  订单状态

    private String sellerName;
    /*@OneToOne(mappedBy = "orderItem")
    private Comment comment; //买家评论*/
    @OneToMany(mappedBy = "orderItem")
    private List<Comment> commentList;

    @OneToMany(mappedBy = "orderItem")
    private List<SellerComment> sellerCommentList; //卖家评论

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

    public double getSummary() {
        return summary;
    }

    public void setSummary(double summary) {
        this.summary = summary;
    }

    public List<OrderLine> getLines() {
        return lines;
    }

    public void setLines(List<OrderLine> lines) {
        this.lines = lines;
    }

    public Calendar getCreatedTime() {
        return createdTime;
    }

    @PrePersist
    public void setCreatedTime() {
        DateTime dt = new DateTime();
        this.createdTime = dt.toCalendar(Locale.SIMPLIFIED_CHINESE);
    }

    public Calendar getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(Calendar cancelTime) {
        this.cancelTime = cancelTime;
    }

    public Calendar getDeliverTime() {
        return deliverTime;
    }

    public void setDeliverTime(Calendar deliverTime) {
        this.deliverTime = deliverTime;
    }

    public Calendar getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Calendar receiveTime) {
        this.receiveTime = receiveTime;
    }

    public OrderAddress getOrderAddress() {
        return orderAddress;
    }

    public void setOrderAddress(OrderAddress orderAddress) {
        this.orderAddress = orderAddress;
    }

    public EcUser getBuyer() {
        return buyer;
    }

    public void setBuyer(EcUser buyer) {
        this.buyer = buyer;
    }

    public EcUser getSeller() {
        return seller;
    }

    public void setSeller(EcUser seller) {
        this.seller = seller;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getNeedInvoice() {
        return needInvoice;
    }

    public void setNeedInvoice(int needInvoice) {
        this.needInvoice = needInvoice;
    }

    public void setCreatedTime(Calendar createdTime) {
        this.createdTime = createdTime;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public List<SellerComment> getSellerCommentList() {
        return sellerCommentList;
    }

    public void setSellerCommentList(List<SellerComment> sellerCommentList) {
        this.sellerCommentList = sellerCommentList;
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
        OrderItem that = (OrderItem)o;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
