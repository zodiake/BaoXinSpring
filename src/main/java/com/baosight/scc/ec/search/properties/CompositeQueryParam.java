package com.baosight.scc.ec.search.properties;

import org.elasticsearch.search.sort.SortOrder;

import java.util.ArrayList;
import java.util.List;

public class CompositeQueryParam {
    List<SearchParam> queryMustParams = null;
    List<SearchParam> queryShouldParams = null;
    List<RangeParam> queryRangeParams = null;
    List<SearchParam> filterMustParams = null;
    List<SearchParam> filterShouldParams = null;
    List<SearchParam> queryStringParams = null;
    int offset = 0;
    int limit = 10;
    String sortField = null;
    SortOrder sortOrder = null;

    public void addQueryMustParam(SearchParam param) {
        if (queryMustParams == null) {
            queryMustParams = new ArrayList<SearchParam>();
        }
        queryMustParams.add(param);
    }

    public void addQueryShouldParam(SearchParam param) {
        if (queryShouldParams == null) {
            queryShouldParams = new ArrayList<SearchParam>();
        }
        queryShouldParams.add(param);
    }

    public void addQueryRangeParam(RangeParam param) {
        if (queryRangeParams == null) {
            queryRangeParams = new ArrayList<RangeParam>();
        }
        queryRangeParams.add(param);
    }

    public void addFilterMustParam(SearchParam param) {
        if (filterMustParams == null) {
            filterMustParams = new ArrayList<SearchParam>();
        }
        filterMustParams.add(param);
    }

    public void addFilterShouldParam(SearchParam param) {
        if (filterShouldParams == null) {
            filterShouldParams = new ArrayList<SearchParam>();
        }
        filterShouldParams.add(param);
    }

    public void addQueryStringParam(SearchParam param) {
        if (queryStringParams == null) {
            queryStringParams = new ArrayList<SearchParam>();
        }
        queryStringParams.add(param);
    }

    public List<SearchParam> getQueryMustParams() {
        return queryMustParams;
    }

    public List<SearchParam> getQueryShouldParams() {
        return queryShouldParams;
    }

    public List<RangeParam> getQueryRangeParams() {
        return queryRangeParams;
    }

    public List<SearchParam> getFilterMustParams() {
        return filterMustParams;
    }

    public List<SearchParam> getFilterShouldParams() {
        return filterShouldParams;
    }

    public List<SearchParam> getQueryStringParams() {
        return queryStringParams;
    }

    public int getOffset() {
        return offset;
    }

    public int getLimit() {
        return limit;
    }

    public String getSortField() {
        return sortField;
    }

    public SortOrder getSortOrder() {
        return sortOrder;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public void setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }


}
