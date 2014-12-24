package com.baosight.scc.ec.search.properties;

public class RangeParam<K> {
    private String fieldName;
    private K start;
    private K end;

    public RangeParam(String fieldName, K start, K end) {
        this.start = start;
        this.end = end;
        this.fieldName = fieldName;

    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public K getStart() {
        return start;
    }

    public void setStart(K start) {
        this.start = start;
    }

    public K getEnd() {
        return end;
    }

    public void setEnd(K end) {
        this.end = end;
    }


}
