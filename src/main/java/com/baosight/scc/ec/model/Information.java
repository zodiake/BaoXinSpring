package com.baosight.scc.ec.model;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Charles on 2014/6/5.
 * 广告管理
 */
@Entity
@Table(name = "T_ec_information")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Information implements Serializable {
    @Id
    private String id;

    @Size(max = 100,message = "标题最大长度为100个字符")
    private String title;
    @Size(max = 500,message = "内容简要最大长度为500个字符")
    private String briefContent;

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar createdTime;

    private int isValid;

    private String videoPath;

    private String viewPath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private InformationCategory informationCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private EcUser createdBy;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "content_id")
    private InfoContent infoContent;

    @Transient
    private String fakeContent;

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

    public Calendar getCreatedTime() {
        return createdTime;
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

    public InformationCategory getInformationCategory() {
        return informationCategory;
    }

    public void setInformationCategory(InformationCategory informationCategory) {
        this.informationCategory = informationCategory;
    }

    public EcUser getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(EcUser createdBy) {
        this.createdBy = createdBy;
    }

    public InfoContent getInfoContent() {
        return infoContent;
    }

    public void setInfoContent(InfoContent infoContent) {
        this.infoContent = infoContent;
    }

    public String getFakeContent() {
        return fakeContent;
    }

    public void setFakeContent(String fakeContent) {
        this.fakeContent = fakeContent;
    }

    public String getBriefContent() {
        return briefContent;
    }

    public void setBriefContent(String briefContent) {
        this.briefContent = briefContent;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public String getViewPath() {
        return viewPath;
    }

    public void setViewPath(String viewPath) {
        this.viewPath = viewPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Information)) return false;

        Information that = (Information) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
