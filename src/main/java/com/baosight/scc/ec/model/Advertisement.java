package com.baosight.scc.ec.model;

import org.hibernate.annotations.*;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Charles on 2014/5/30.
 * 广告管理
 */
@Entity
@Table(name = "T_ec_ad")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Advertisement implements Serializable {
    @Id
    private String id;

    private String title;

    private String subTitle;

    private String desc;

    private String link;

    private String coverPath;

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar createdTime;

    private int isValid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id")
    private AdvertisementPosition advertisementPosition;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCoverPath() {
        return coverPath;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }

    public Calendar getCreatedTime() {
        return createdTime;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    @PrePersist
    public void setCreatedTime() {
        DateTime dt = new DateTime();
        this.createdTime = dt.toCalendar(Locale.SIMPLIFIED_CHINESE);
    }

    public int getIsValid() {
        return isValid;
    }

    public void setIsValid(int isValid) {
        this.isValid = isValid;
    }

    public AdvertisementPosition getAdvertisementPosition() {
        return advertisementPosition;
    }

    public void setAdvertisementPosition(AdvertisementPosition advertisementPosition) {
        this.advertisementPosition = advertisementPosition;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Advertisement)) return false;

        Advertisement that = (Advertisement) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
