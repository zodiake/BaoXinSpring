package com.baosight.scc.ec.model;

import com.baosight.scc.ec.type.ItemState;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Map;

/**
 * Created by ThinkPad on 2014/5/6.
 * 购物车明细条目
 */

public class CartLine implements Serializable{
    private String itemId;  //商品id
    private String title;   //商品标题
    private String supplierId;  //供应商id
    private String supplierName;    //供应商名称
    private String itemType;    //商品类型(辅料，面料)
    private double price;   //单价
    private int quantity;   // 数量
    private double summary; //总价
    private String imgPath; //图片路径
    private Map priceRange; //价格区间
    private ItemState state;
    private Item item;

    public CartLine(){}


    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getSummary() {
        return summary;
    }

    public void setSummary(double summary) {
        this.summary = summary;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Map getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(Map priceRange) {
        this.priceRange = priceRange;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public ItemState getState() {
        return state;
    }

    public void setState(ItemState state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartLine)) return false;

        CartLine cartLine = (CartLine) o;

        if (itemId != null ? !itemId.equals(cartLine.itemId) : cartLine.itemId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return itemId != null ? itemId.hashCode() : 0;
    }
}
