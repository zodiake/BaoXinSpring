package com.baosight.scc.ec.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by zodiake on 2014/7/11.
 */
public class ShopSearchType implements Serializable{
    private String id;
    private String name;
    private String focus;
    private double score;
    private double money;
    private String cover;
    private String scope;
    private List<String> type;

    public ShopSearchType(){}

    public ShopSearchType(Map<String,Object> maps){
        if (maps.get("_id") != null)
            id = (String) maps.get("_id");
        if (maps.get("focus") != null)
            focus = (String) maps.get("focus");
        if (maps.get("score") != null)
            score = (Double)maps.get("score");
        if (maps.get("name") != null)
            name = (String)maps.get("name");
        if (maps.get("cover") != null)
            cover = (String)maps.get("cover");
        if (maps.get("type") != null)
            type = (List<String>)maps.get("type");
        if (maps.get("scope") != null)
            scope = (String)maps.get("scope");
        if (maps.get("money") != null)
            money = Double.parseDouble(maps.get("money").toString());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFocus() {
        return focus;
    }

    public void setFocus(String focus) {
        this.focus = focus;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getType() {
        return type;
    }

    public void setType(List<String> type) {
        this.type = type;
    }

    public String getCover() {

        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
