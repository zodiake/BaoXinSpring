package com.baosight.scc.ec.model;

import java.util.List;

/**
 * Created by zodiake on 2014/7/22.
 */
public class UserInfo {
    private String id;
    private String mianProductService;//主营产品或服务
    private String companyRemark;//公司简介
    private String companyName;//公司名称
    private String business;//经营范围
    private long operatingCapital;//注册资本
    private String logo;//图片
    private List<String> userType;
    private String yyzz;//营业执照
    private double score;//@sam 2014-8-27

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMianProductService() {
        return mianProductService;
    }

    public void setMianProductService(String mianProductService) {
        this.mianProductService = mianProductService;
    }

    public String getCompanyRemark() {
        return companyRemark;
    }

    public void setCompanyRemark(String companyRemark) {
        this.companyRemark = companyRemark;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public List<String> getUserType() {
        return userType;
    }

    public void setUserType(List<String> userType) {
        this.userType = userType;
    }

    public String getYyzz() {
        return yyzz;
    }

    public void setYyzz(String yyzz) {
        this.yyzz = yyzz;
    }

    public long getOperatingCapital() {
        return operatingCapital;
    }

    public void setOperatingCapital(long operatingCapital) {
        this.operatingCapital = operatingCapital;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
