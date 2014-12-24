package com.baosight.scc.ec.search.properties;

public class SearchParam {
    //private boolean must=true;
    private float boost = -1;
    private String fieldName;
    private boolean highlight = true;
    private Object value;

    public SearchParam(float boost, String fieldName, boolean highlight, Object value) {
        this.boost = boost;
        this.fieldName = fieldName;
        this.value = value;
        this.highlight = highlight;

    }


    public SearchParam(String fieldName, Object value, boolean highlight) {
        this.fieldName = fieldName;
        this.value = value;
        this.highlight = highlight;
    }

    public float getBoost() {
        return boost;
    }

    public void setBoost(float boost) {
        this.boost = boost;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }


    public Object getValue() {
        return value;
    }


    public void setValue(Object value) {
        this.value = value;
    }


    public boolean isHighlight() {
        return highlight;
    }

    public void setHighlight(boolean highlight) {
        this.highlight = highlight;
    }


}
