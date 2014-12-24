package com.baosight.scc.ec.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by sam on 2014/7/22.
 */
public class EcProvider implements Serializable{

    private String id;

    private String userName;

    private String logo;

    private String createTime; //创建日期

    private String companyWebsite; //公司网址

    private String supplyGoodsType;

    private String companyRemark;//公司简介

    private String registeredCapitalCurrency; //注册资金单位

    private String companyAddr; //公司地址

    private String modiTime; //修改时间

    private String businessLicEffectiveDate;

    private String business; //业务范围

    private String orgCode;

    private String zipCode;

    private String businessLicExpirationDate;

    private String tariff;

    private String createPerson;

    private String orgCodeExpirationDate;

    private String bank;

    private String mobileTelephone;

    private String operatingCapital;

    private String fax;

    private String mianProductService;

    private String businessLicRegisterProv;

    private String modiPerson; //修改人

    private String mainIndustry;

    private String businessLicRegisterCity;

    private String companyName;

    private String fixedTelephone;

    private String businessLicenseCode;

    private String bankCcount;

    private String businessLicRegRegion;

    private String tariffEffectiveDate;

    private String email;

    private String orgCodeEffectiveDate;

    private String companyType;

    private String companyMail;

    private String tariffExpirationDate;

    private String contactPerson;

    private List<String> yyList = new ArrayList<String>();

    private String yyzz;

    private String org;

    private String tax;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCompanyWebsite() {
        return companyWebsite;
    }

    public void setCompanyWebsite(String companyWebsite) {
        this.companyWebsite = companyWebsite;
    }

    public String getSupplyGoodsType() {
        return supplyGoodsType;
    }

    public void setSupplyGoodsType(String supplyGoodsType) {
        this.supplyGoodsType = supplyGoodsType;
    }

    public String getCompanyRemark() {
        return companyRemark;
    }

    public void setCompanyRemark(String companyRemark) {
        this.companyRemark = companyRemark;
    }

    public String getRegisteredCapitalCurrency() {
        return registeredCapitalCurrency;
    }

    public void setRegisteredCapitalCurrency(String registeredCapitalCurrency) {
        this.registeredCapitalCurrency = registeredCapitalCurrency;
    }

    public String getCompanyAddr() {
        return companyAddr;
    }

    public void setCompanyAddr(String companyAddr) {
        this.companyAddr = companyAddr;
    }

    public String getModiTime() {
        return modiTime;
    }

    public void setModiTime(String modiTime) {
        this.modiTime = modiTime;
    }

    public String getBusinessLicEffectiveDate() {
        return businessLicEffectiveDate;
    }

    public void setBusinessLicEffectiveDate(String businessLicEffectiveDate) {
        this.businessLicEffectiveDate = businessLicEffectiveDate;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getBusinessLicExpirationDate() {
        return businessLicExpirationDate;
    }

    public void setBusinessLicExpirationDate(String businessLicExpirationDate) {
        this.businessLicExpirationDate = businessLicExpirationDate;
    }

    public String getTariff() {
        return tariff;
    }

    public void setTariff(String tariff) {
        this.tariff = tariff;
    }

    public String getCreatePerson() {
        return createPerson;
    }

    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson;
    }

    public String getOrgCodeExpirationDate() {
        return orgCodeExpirationDate;
    }

    public void setOrgCodeExpirationDate(String orgCodeExpirationDate) {
        this.orgCodeExpirationDate = orgCodeExpirationDate;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getMobileTelephone() {
        return mobileTelephone;
    }

    public void setMobileTelephone(String mobileTelephone) {
        this.mobileTelephone = mobileTelephone;
    }

    public String getOperatingCapital() {
        return operatingCapital;
    }

    public void setOperatingCapital(String operatingCapital) {
        this.operatingCapital = operatingCapital;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getMianProductService() {
        return mianProductService;
    }

    public void setMianProductService(String mianProductService) {
        this.mianProductService = mianProductService;
    }

    public String getBusinessLicRegisterProv() {
        return businessLicRegisterProv;
    }

    public void setBusinessLicRegisterProv(String businessLicRegisterProv) {
        this.businessLicRegisterProv = businessLicRegisterProv;
    }

    public String getModiPerson() {
        return modiPerson;
    }

    public void setModiPerson(String modiPerson) {
        this.modiPerson = modiPerson;
    }

    public String getMainIndustry() {
        return mainIndustry;
    }

    public void setMainIndustry(String mainIndustry) {
        this.mainIndustry = mainIndustry;
    }

    public String getBusinessLicRegisterCity() {
        return businessLicRegisterCity;
    }

    public void setBusinessLicRegisterCity(String businessLicRegisterCity) {
        this.businessLicRegisterCity = businessLicRegisterCity;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getFixedTelephone() {
        return fixedTelephone;
    }

    public void setFixedTelephone(String fixedTelephone) {
        this.fixedTelephone = fixedTelephone;
    }

    public String getBusinessLicenseCode() {
        return businessLicenseCode;
    }

    public void setBusinessLicenseCode(String businessLicenseCode) {
        this.businessLicenseCode = businessLicenseCode;
    }

    public String getBankCcount() {
        return bankCcount;
    }

    public void setBankCcount(String bankCcount) {
        this.bankCcount = bankCcount;
    }

    public String getBusinessLicRegRegion() {
        return businessLicRegRegion;
    }

    public void setBusinessLicRegRegion(String businessLicRegRegion) {
        this.businessLicRegRegion = businessLicRegRegion;
    }

    public String getTariffEffectiveDate() {
        return tariffEffectiveDate;
    }

    public void setTariffEffectiveDate(String tariffEffectiveDate) {
        this.tariffEffectiveDate = tariffEffectiveDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOrgCodeEffectiveDate() {
        return orgCodeEffectiveDate;
    }

    public void setOrgCodeEffectiveDate(String orgCodeEffectiveDate) {
        this.orgCodeEffectiveDate = orgCodeEffectiveDate;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public String getCompanyMail() {
        return companyMail;
    }

    public void setCompanyMail(String companyMail) {
        this.companyMail = companyMail;
    }

    public String getTariffExpirationDate() {
        return tariffExpirationDate;
    }

    public void setTariffExpirationDate(String tariffExpirationDate) {
        this.tariffExpirationDate = tariffExpirationDate;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public List<String> getYyList() {
        return yyList;
    }

    public void setYyList(List<String> yyList) {
        this.yyList = yyList;
    }

    public String getYyzz() {
        return yyzz;
    }

    public void setYyzz(String yyzz) {
        this.yyzz = yyzz;
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EcProvider ecProvider = (EcProvider) o;

        if (id != null ? !id.equals(ecProvider.id) : ecProvider.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
