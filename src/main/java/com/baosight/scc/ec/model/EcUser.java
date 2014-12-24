package com.baosight.scc.ec.model;

import org.jboss.resteasy.spi.touri.MappedBy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by ThinkPad on 2014/5/6.
 */
//本系统对应的用户父类
@Entity
@Table(name = "T_ec_EcUser")
public class EcUser extends User implements Serializable {
    @Id
    protected String id;

    protected String name;

    protected String password;

    private String userType;

    @Transient
    protected List<OrderItem> buyerOrders = new ArrayList<OrderItem>();

    @Transient
    protected List<OrderItem> sellerOrders = new ArrayList<OrderItem>();

    @OneToMany(mappedBy = "user")
    private List<Address> addresses = new ArrayList<Address>();

    @OneToOne
    @JoinColumn(name = "defaultAddress_id")
    private Address defaultAddress;

    @Transient
    private List<Comment> sendComments = new ArrayList<Comment>();

    @Transient
    private List<SellerComment> sellerComments = new ArrayList<SellerComment>();

    @Transient
    private List<SampleOrder> sampleOrders = new ArrayList<SampleOrder>();

    @Transient
    private List<SampleOrder> receivedSampleOrders = new ArrayList<SampleOrder>();

    @Transient
    private List<DemandOrder> demandOrders = new ArrayList<DemandOrder>();

    @Transient
    private List<Information> informations = new ArrayList<Information>();

    @Transient
    private List<FavouriteFabricCategory> preferFabricCategory = new ArrayList<FavouriteFabricCategory>();

    @Transient
    private List<FavouriteMaterialCategory> preferMaterialCategory = new ArrayList<FavouriteMaterialCategory>();

    @Transient
    private List<Item> fabrics = new ArrayList<Item>();

    @Transient
    private List<Item> materials = new ArrayList<Item>();

    @Transient
    private List<FabricCategory> fabricCategoryListCreatedBy = new ArrayList<FabricCategory>();

    @Transient
    private List<MaterialCategory> materialCategoryListCreatedBy = new ArrayList<MaterialCategory>();

    @Transient
    private List<FabricCategory> fabricCategoryListUpdatedBy = new ArrayList<FabricCategory>();

    @Transient
    private List<MaterialCategory> materialCategoryListUpdatedBy = new ArrayList<MaterialCategory>();

    @Transient
    private List<FavouriteItems> favouriteItems = new ArrayList<FavouriteItems>();

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Shop> shops = new ArrayList<Shop>();

    @Transient
    private List<FavouriteShops> favouriteShops = new ArrayList<FavouriteShops>();

    @Transient
    private Collection<? extends GrantedAuthority> authorities;

    @OneToMany(mappedBy = "user")
    private List<SellerCredit> sellerCredits = new ArrayList<SellerCredit>();

    @OneToMany(mappedBy = "sendUser")
    private List<InsideLetter> sendInsideLetters = new ArrayList<InsideLetter>();

    @OneToMany(mappedBy = "receiveUser")
    private List<InsideLetter> receiveInsideLetters = new ArrayList<InsideLetter>();

    @Transient
    private String companyName;

    @Transient
    private String companyType;//企业类型


    @OneToOne(mappedBy = "user")
    private CompositeScore compositeScore;

    @Transient
    private EcProvider ecProvider;

    public EcUser() {
    }

    public EcUser(String id){
        this.id=id;
    }

    public EcUser(CommonUser commonUser){
        this.id=commonUser.getUserId();
        this.name=commonUser.getEmpName();
        this.createTime=commonUser.getCreateTime();
        this.email=commonUser.getEmail();
        this.logo=commonUser.getHead();
        this.userType=commonUser.getEmpType();
    }

    public EcUser(EcProvider ecProvider) {
        this.id = ecProvider.getUserName();
        this.name = ecProvider.getUserName();
        this.logo = ecProvider.getLogo();
        this.supplyGoodsType = ecProvider.getSupplyGoodsType();
        this.companyRemark = ecProvider.getCompanyRemark();
        this.registeredCapitalCurrency = ecProvider.getRegisteredCapitalCurrency();
        this.companyAddr = ecProvider.getCompanyAddr();
        this.businessLicEffectiveDate = ecProvider.getBusinessLicEffectiveDate();
        this.business = ecProvider.getBusiness();
        this.orgCode = ecProvider.getOrgCode();
        this.zipCode = ecProvider.getZipCode();
        this.businessLicExpirationDate = ecProvider.getBusinessLicExpirationDate();
        this.tariff = ecProvider.getTariff();
        this.createPerson = ecProvider.getCreatePerson();
        this.orgCodeExpirationDate = ecProvider.getOrgCodeExpirationDate();
        this.bank = ecProvider.getBank();
        this.mobileTelephone = ecProvider.getMobileTelephone();
        this.operatingCapital = ecProvider.getOperatingCapital();
        this.fax = ecProvider.getFax();
        this.mainIndustry = ecProvider.getMainIndustry();
        this.mianProductService = ecProvider.getMianProductService();
        this.businessLicRegisterProv = ecProvider.getBusinessLicRegisterProv();
        this.businessLicRegisterCity = ecProvider.getBusinessLicRegisterCity();
        this.fixedTelephone = ecProvider.getFixedTelephone();
        this.businessLicenseCode = ecProvider.getBusinessLicenseCode();
        this.bankCcount = ecProvider.getBankCcount();
        this.businessLicRegRegion = ecProvider.getBusinessLicRegRegion();
        this.tariffEffectiveDate = ecProvider.getTariffEffectiveDate();
        this.email = ecProvider.getEmail();
        this.orgCodeEffectiveDate = ecProvider.getOrgCodeEffectiveDate();
        this.companyMail = ecProvider.getCompanyMail();
        this.companyName = ecProvider.getCompanyName();
        this.companyType = ecProvider.getCompanyType();
        this.tariffExpirationDate = ecProvider.getTariffExpirationDate();
        this.contactPerson = ecProvider.getContactPerson();
        this.contactPerson = ecProvider.getContactPerson();
        this.createTime = ecProvider.getCreateTime();
        this.companyWebsite=ecProvider.getCompanyWebsite();
        this.yyzz=ecProvider.getYyzz();
    //    this.yyList=ecProvider.getYyList();
        this.org=ecProvider.getOrg();
        this.tax=ecProvider.getTax();
    }


    /////////////////////////企业全部信息@sam///////////////////////////////////////////
    @Transient
    private String logo;//公司logo
    @Transient
    private String companyWebsite;//公司网址
    @Transient
    private String supplyGoodsType;
    @Transient
    private String companyRemark;//公司简介
    @Transient
    private String registeredCapitalCurrency;
    @Transient
    private String companyAddr;//公司地址
    @Transient
    private String businessLicEffectiveDate;
    @Transient
    private String business;
    @Transient
    private String orgCode;
    @Transient
    private String zipCode;
    @Transient
    private String businessLicExpirationDate;
    @Transient
    private String tariff;
    @Transient
    private String createPerson;
    @Transient
    private String orgCodeExpirationDate;
    @Transient
    private String bank;
    @Transient
    private String mobileTelephone;
    @Transient
    private String operatingCapital;
    @Transient
    private String fax;
    @Transient
    private String mianProductService;
    @Transient
    private String businessLicRegisterProv;
    @Transient
    private String mainIndustry;
    @Transient
    private String businessLicRegisterCity;
    //private String companyName;
    @Transient
    private String fixedTelephone;
    @Transient
    private String businessLicenseCode;
    @Transient
    private String bankCcount;
    @Transient
    private String businessLicRegRegion;
    @Transient
    private String tariffEffectiveDate;
    @Transient
    private String email;
    @Transient
    private String orgCodeEffectiveDate;
    //   private String companyType;
    @Transient
    private String companyMail;
    @Transient
    private String tariffExpirationDate;
    @Transient
    private String contactPerson;
    @Transient
    private String createTime;
    @Transient
    private String yyzz;
    @Transient
    private List<String> yyList;
    @Transient
    private String org;
    @Transient
    private String tax;

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
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

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getYyzz() {
        return yyzz;
    }

    public void setYyzz(String yyzz) {
        this.yyzz = yyzz;
    }

    public List<String> getYyList() {
        return yyList;
    }

    public void setYyList(List<String> yyList) {
        this.yyList = yyList;
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

    //////////////////////////////////////////////////


    public EcUser(String id, UserDetails details) {
        setId(id);
        setName(details.getUsername());
        setAuthorities(details.getAuthorities());
    }

    //站内信个数
    @Transient
    private int letterLen = 0;

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public List<SampleOrder> getReceivedSampleOrders() {
        return receivedSampleOrders;
    }

    public void setReceivedSampleOrders(List<SampleOrder> receivedSampleOrders) {
        this.receivedSampleOrders = receivedSampleOrders;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public Address getDefaultAddress() {
        return defaultAddress;
    }

    public void setDefaultAddress(Address defaultAddress) {
        this.defaultAddress = defaultAddress;
    }

    public List<Comment> getSendComments() {
        return sendComments;
    }

    public void setSendComments(List<Comment> sendComments) {
        this.sendComments = sendComments;
    }

    public List<SampleOrder> getSampleOrders() {
        return sampleOrders;
    }

    public void setSampleOrders(List<SampleOrder> sampleOrders) {
        this.sampleOrders = sampleOrders;
    }

    public List<DemandOrder> getDemandOrders() {
        return demandOrders;
    }

    public void setDemandOrders(List<DemandOrder> demandOrders) {
        this.demandOrders = demandOrders;
    }

    public List<OrderItem> getBuyerOrders() {
        return buyerOrders;
    }

    public void setBuyerOrders(List<OrderItem> buyerOrders) {
        this.buyerOrders = buyerOrders;
    }

    public List<OrderItem> getSellerOrders() {
        return sellerOrders;
    }

    public void setSellerOrders(List<OrderItem> sellerOrders) {
        this.sellerOrders = sellerOrders;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Item> getFabrics() {
        return fabrics;
    }

    public void setFabrics(List<Item> fabrics) {
        this.fabrics = fabrics;
    }

    public List<Item> getMaterials() {
        return materials;
    }

    public void setMaterials(List<Item> materials) {
        this.materials = materials;
    }


    public List<SellerComment> getSellerComments() {
        return sellerComments;
    }

    public void setSellerComments(List<SellerComment> sellerComments) {
        this.sellerComments = sellerComments;
    }

    public List<Information> getInformations() {
        return informations;
    }

    public void setInformations(List<Information> informations) {
        this.informations = informations;
    }

    public List<FavouriteItems> getFavouriteItems() {
        return favouriteItems;
    }

    public void setFavouriteItems(List<FavouriteItems> favouriteItems) {
        this.favouriteItems = favouriteItems;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public List<SellerCredit> getSellerCredits() {
        return sellerCredits;
    }

    public void setSellerCredits(List<SellerCredit> sellerCredits) {
        this.sellerCredits = sellerCredits;
    }

    public List<FabricCategory> getFabricCategoryListCreatedBy() {
        return fabricCategoryListCreatedBy;
    }

    public void setFabricCategoryListCreatedBy(List<FabricCategory> fabricCategoryListCreatedBy) {
        this.fabricCategoryListCreatedBy = fabricCategoryListCreatedBy;
    }

    public List<MaterialCategory> getMaterialCategoryListCreatedBy() {
        return materialCategoryListCreatedBy;
    }

    public void setMaterialCategoryListCreatedBy(List<MaterialCategory> materialCategoryListCreatedBy) {
        this.materialCategoryListCreatedBy = materialCategoryListCreatedBy;
    }

    public List<FabricCategory> getFabricCategoryListUpdatedBy() {
        return fabricCategoryListUpdatedBy;
    }

    public void setFabricCategoryListUpdatedBy(List<FabricCategory> fabricCategoryListUpdatedBy) {
        this.fabricCategoryListUpdatedBy = fabricCategoryListUpdatedBy;
    }

    public List<MaterialCategory> getMaterialCategoryListUpdatedBy() {
        return materialCategoryListUpdatedBy;
    }

    public void setMaterialCategoryListUpdatedBy(List<MaterialCategory> materialCategoryListUpdatedBy) {
        this.materialCategoryListUpdatedBy = materialCategoryListUpdatedBy;
    }

    public List<Shop> getShops() {
        return shops;
    }

    public void setShops(List<Shop> shops) {
        this.shops = shops;
    }

    public List<FavouriteShops> getFavouriteShops() {
        return favouriteShops;
    }

    public void setFavouriteShops(List<FavouriteShops> favouriteShops) {
        this.favouriteShops = favouriteShops;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }


    public CompositeScore getCompositeScore() {
        return compositeScore;
    }

    public void setCompositeScore(CompositeScore compositeScore) {
        this.compositeScore = compositeScore;
    }


    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public List<InsideLetter> getSendInsideLetters() {
        return sendInsideLetters;
    }

    public void setSendInsideLetters(List<InsideLetter> sendInsideLetters) {
        this.sendInsideLetters = sendInsideLetters;
    }

    public List<InsideLetter> getReceiveInsideLetters() {
        return receiveInsideLetters;
    }

    public void setReceiveInsideLetters(List<InsideLetter> receiveInsideLetters) {
        this.receiveInsideLetters = receiveInsideLetters;
    }

    public List<FavouriteFabricCategory> getPreferFabricCategory() {
        return preferFabricCategory;
    }

    public void setPreferFabricCategory(List<FavouriteFabricCategory> preferFabricCategory) {
        this.preferFabricCategory = preferFabricCategory;
    }

    public List<FavouriteMaterialCategory> getPreferMaterialCategory() {
        return preferMaterialCategory;
    }

    public void setPreferMaterialCategory(List<FavouriteMaterialCategory> preferMaterialCategory) {
        this.preferMaterialCategory = preferMaterialCategory;
    }

    public EcProvider getEcProvider() {
        return ecProvider;
    }

    public void setEcProvider(EcProvider ecProvider) {
        this.ecProvider = ecProvider;
    }

    public int getLetterLen() {
        return letterLen;
    }

    public void setLetterLen(int letterLen) {
        this.letterLen = letterLen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EcUser user = (EcUser) o;

        if (id != null ? !id.equals(user.id) : user.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
