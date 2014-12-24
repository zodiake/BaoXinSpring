package com.baosight.scc.ec.model;

import com.baosight.scc.ec.type.SearchSortType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zodiake on 2014/7/2.
 */
public class SearchParam {
    protected String keyWord;
    protected List<Season> season = new ArrayList<Season>();
    protected List<Color> color = new ArrayList<Color>();
    protected String width;
    protected String weight;
    protected Double minWidth;
    protected Double maxWidth;
    protected Double minWeight;
    protected Double maxWeight;
    protected Double minPrice;
    protected Double maxPrice;
    protected SearchSortType sort;
    protected Integer currentPage;
    protected Integer totalPage;
    protected Double minRangePrice;
    protected Double maxRangePrice;
    protected List<String> hierarchy;

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public List<Season> getSeason() {
        return season;
    }

    public void setSeason(List<Season> season) {
        this.season = season;
    }

    public List<Color> getColor() {
        return color;
    }

    public void setColor(List<Color> color) {
        this.color = color;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public Double getMinWidth() {
        return minWidth;
    }

    public void setMinWidth(Double minWidth) {
        this.minWidth = minWidth;
    }

    public Double getMaxWidth() {
        return maxWidth;
    }

    public void setMaxWidth(Double maxWidth) {
        this.maxWidth = maxWidth;
    }

    public Double getMinWeight() {
        return minWeight;
    }

    public void setMinWeight(Double minWeight) {
        this.minWeight = minWeight;
    }

    public Double getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(Double maxWeight) {
        this.maxWeight = maxWeight;
    }

    public SearchSortType getSort() {
        return sort;
    }

    public void setSort(SearchSortType sort) {
        this.sort = sort;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Double getMinRangePrice() {
        return minRangePrice;
    }

    public void setMinRangePrice(Double minRangePrice) {
        this.minRangePrice = minRangePrice;
    }

    public Double getMaxRangePrice() {
        return maxRangePrice;
    }

    public void setMaxRangePrice(Double maxRangePrice) {
        this.maxRangePrice = maxRangePrice;
    }

    public List<String> getHierarchy() {
        return hierarchy;
    }

    public void setHierarchy(List<String> hierarchy) {
        this.hierarchy = hierarchy;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }
}
