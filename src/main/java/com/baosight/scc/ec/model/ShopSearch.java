package com.baosight.scc.ec.model;

import com.baosight.scc.ec.type.SearchSortType;
import com.baosight.scc.ec.type.ShopSearchSort;

import java.util.List;

/**
 * Created by zodiake on 2014/7/11.
 */
public class ShopSearch extends SearchParam{
    private String keyWord;
    private List<String> type;
    private Double minMoney;
    private Double maxMoney;
    private Integer page;
    private ShopSearchSort sort;
    private Double rangeMinMoney;
    private Double rangeMaxMoney;

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public List<String> getType() {
        return type;
    }

    public void setType(List<String> type) {
        this.type = type;
    }

    public Double getMinMoney() {
        return minMoney;
    }

    public void setMinMoney(Double minMoney) {
        this.minMoney = minMoney;
    }

    public Double getMaxMoney() {
        return maxMoney;
    }

    public void setMaxMoney(Double maxMoney) {
        this.maxMoney = maxMoney;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

//    public ShopSearchSort getSort() {
//        return sort;
//    }
//
//    public void setSort(ShopSearchSort sort) {
//        this.sort = sort;
//    }

    public Double getRangeMinMoney() {
        return rangeMinMoney;
    }

    public void setRangeMinMoney(Double rangeMinMoney) {
        this.rangeMinMoney = rangeMinMoney;
    }

    public Double getRangeMaxMoney() {
        return rangeMaxMoney;
    }

    public void setRangeMaxMoney(Double rangeMaxMoney) {
        this.rangeMaxMoney = rangeMaxMoney;
    }
}
