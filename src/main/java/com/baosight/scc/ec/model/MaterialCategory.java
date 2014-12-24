package com.baosight.scc.ec.model;

import org.hibernate.annotations.*;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by ThinkPad on 2014/5/6.
 */
@Entity
@Table(name = "T_ec_MaterialCategory")
@NamedQueries({@NamedQuery(name = "MaterialCategory.findAllFirstCategorys", query = "select mc from MaterialCategory mc where mc.secondCategory is not empty and mc.isValid=0"),
        @NamedQuery(name = "MaterialCategory.findAllSecondCategories", query = "select m from MaterialCategory m where m.secondCategory is empty and m.isValid=0 "),
        @NamedQuery(name = "MaterialCategory.findByParentCategory", query = "select mc from MaterialCategory mc where mc.parentCategory=:category and mc.isValid=0")})
//辅料分类
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MaterialCategory implements Serializable {
    @Id
    private String id;

    private String name;

    @OneToMany(mappedBy = "parentCategory")
    private List<MaterialCategory> secondCategory = new ArrayList<MaterialCategory>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private MaterialCategory parentCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "createdBy")
    private EcUser createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updatedBy")
    private EcUser updatedBy;

    @OneToMany(mappedBy = "category")
    private List<Material> fabrics = new ArrayList<Material>();

    @Temporal(TemporalType.TIMESTAMP)
    protected Calendar updatedTime;        //更新日期

    @Temporal(TemporalType.TIMESTAMP)
    protected Calendar createdTime;        //创建日期

    private int isValid;
    private int sortNo;
    @OneToMany(mappedBy = "category")
    private List<FavouriteMaterialCategory> favourCategory = new ArrayList<FavouriteMaterialCategory>();

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

    public List<MaterialCategory> getSecondCategory() {
        return secondCategory;
    }

    public void setSecondCategory(List<MaterialCategory> secondCategory) {
        this.secondCategory = secondCategory;
    }

    public MaterialCategory getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(MaterialCategory parentCategory) {
        this.parentCategory = parentCategory;
    }

    public List<Material> getFabrics() {
        return fabrics;
    }

    public void setFabrics(List<Material> fabrics) {
        this.fabrics = fabrics;
    }

    public EcUser getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(EcUser createdBy) {
        this.createdBy = createdBy;
    }

    @PrePersist
    public void setCreatedTime() {
        DateTime dt = new DateTime();
        this.createdTime = dt.toCalendar(Locale.SIMPLIFIED_CHINESE);
    }

    public Calendar getCreatedTime() {
        return createdTime;
    }

    public EcUser getUpdatedBy() {

        return updatedBy;
    }

    public void setUpdatedBy(EcUser updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Calendar getUpdatedTime() {

        return updatedTime;
    }

    public void setUpdatedTime(Calendar updatedTime) {
        this.updatedTime = updatedTime;
    }

    public int getIsValid() {
        return isValid;
    }

    public void setIsValid(int isValid) {
        this.isValid = isValid;
    }

    public void setCreatedTime(Calendar createdTime) {
        this.createdTime = createdTime;
    }

    public List<FavouriteMaterialCategory> getFavourCategory() {
        return favourCategory;
    }

    public void setFavourCategory(List<FavouriteMaterialCategory> favourCategory) {
        this.favourCategory = favourCategory;
    }

    public int getSortNo() {
        return sortNo;
    }

    public void setSortNo(int sortNo) {
        this.sortNo = sortNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MaterialCategory that = (MaterialCategory) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return name;
    }
}
