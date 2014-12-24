package com.baosight.scc.ec.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zodiake on 2014/7/1.
 */
public class FabricSearchType {
    private String id;
    private String title;//名称
    private String price;//价格
    private String width;//幅宽
    private String weight;//克重
    private String companyName;//公司名称
    private String source;//制造工艺
    private List<String> seasons = new ArrayList<String>();//季节
    private List<String> use = new ArrayList<String>();//用途
    private List<String> patterns = new ArrayList<String>();//图案
    private String area;
    private String cover;
    private List<String> hierarchy;
    private int viewCount;
    private int sales;

    public FabricSearchType() {
    }

    public FabricSearchType(Map<String, Object> maps) {
        if (maps.get("_id") != null)
            id = (String) maps.get("_id");
        if (maps.get("title") != null)
            title = (String) maps.get("title");
        if (maps.get("price") != null)
            price = String.valueOf(maps.get("price"));
        if (maps.get("width") != null)
            width = String.valueOf(maps.get("width"));
        if (maps.get("weight") != null)
            weight = String.valueOf(maps.get("weight"));
        if (maps.get("season") != null)
            seasons = (List<String>) maps.get("season");
        if (maps.get("company") != null)
            companyName = (String) maps.get("company");
        if (maps.get("source") != null)
            source = (String) maps.get("source");
        if (maps.get("area") != null)
            area = (String) maps.get("area");
        if (maps.get("use") != null)
            use = (List<String>) maps.get("use");
        if (maps.get("pattern") != null)
            patterns = (List<String>) maps.get("pattern");
        if (maps.get("cover") != null)
            cover = (String) maps.get("cover");
        if (maps.get("hierarchy") != null)
            hierarchy = (List<String>) maps.get("hierarchy");
        if (maps.get("viewCount") != null)
            viewCount = Integer.parseInt(maps.get("viewCount").toString());
        if (maps.get("sales") != null)
            sales = Integer.parseInt(maps.get("sales").toString());
    }


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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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

    public List<String> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<String> seasons) {
        this.seasons = seasons;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public List<String> getUse() {
        return use;
    }

    public void setUse(List<String> use) {
        this.use = use;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public List<String> getPatterns() {
        return patterns;
    }

    public void setPatterns(List<String> patterns) {
        this.patterns = patterns;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public List<String> getHierarchy() {
        return hierarchy;
    }

    public void setHierarchy(List<String> hierarchy) {
        this.hierarchy = hierarchy;
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
}
