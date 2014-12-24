package com.baosight.scc.ec.model;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by ThinkPad on 2014/5/6.
 */
@Embeddable
//非实体对象，依存与面料辅料
public class CultureImage implements Serializable {
    private String location;

    private int orderNum;

    @Temporal(TemporalType.TIMESTAMP)
    protected Calendar updatedTime;        //更新日期

    @Temporal(TemporalType.TIMESTAMP)
    protected Calendar createdTime;        //创建日期

    public CultureImage(){}

    public CultureImage(String location,int orderNum){
        this.location=location;
        this.orderNum=orderNum;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CultureImage image = (CultureImage) o;

        if (location != null ? !location.equals(image.location) : image.location != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return location != null ? location.hashCode() : 0;
    }
}
