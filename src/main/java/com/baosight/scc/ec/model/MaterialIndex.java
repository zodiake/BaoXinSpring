package com.baosight.scc.ec.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.List;

/**
 * Created by Administrator on 2014/9/24.
 */
@Document(indexName = "sccindex", type = "material")
public class MaterialIndex {
    @Id
    private String id;
    private String title;
    private String category;
    private String area;
    private String company;
    private List<String> provideTypeList;
    private List<String> use;
    private String weight;
    private double price;
    private String cover;
    private int viewCount;
    private int sales;
    private String createdBy;

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public List<String> getProvideTypeList() {
        return provideTypeList;
    }

    public void setProvideTypeList(List<String> provideTypeList) {
        this.provideTypeList = provideTypeList;
    }

    public List<String> getUse() {
        return use;
    }

    public void setUse(List<String> use) {
        this.use = use;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}
