<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container">
    <!-- 弹窗开始 -->
    <div class="fixed"></div>
    <div class="list-box">
        <span class="close-x"><a href="javascript:void(0);" title="关闭" class="close-this">X</a></span>
        <i class="success-icon"></i>
        <div class="box-con"></div>
    </div>
    <!-- 弹窗结束 -->
    <!--左侧-->
    <div class="aside">
        <!--公司档案-->
        <div class="modebox company-record">
            <div class="hd"><h6>公司档案</h6></div>
            <div class="con record-con">
                <div class="record-pic">
                    <div class="record-logo"><span class="hook"></span>
                        <a href="${pageContext.request.contextPath}/shopCenter/${user.id}/items">
                            <c:choose>
                                <c:when test="${not empty user.logo}">
                                    <img src="${user.logo}" width="258" height="258">
                                </c:when>
                                <c:otherwise>
                                    <img src="${pageContext.request.contextPath}/resources/img/btn-th.png" height="258" width="258"/>
                                </c:otherwise>
                            </c:choose>
                        </a>
                    </div>
                    <div class="record-about">
                        <div class="record-list">
                            <ul>
                                <li><strong class="name">公司名称：</strong>${user.companyName}</li>
                                <li><strong>公司地址：</strong>${user.companyAddr}</li>
                                <li><strong>官方网站：</strong><a href="#" data-url="${user.companyWebsite}" class="locationHomePage">${user.companyWebsite}</a></li>
                                <li><strong>综合评分：</strong><i class="icon-stars icon-stars-${fn:substring(compositeScore.score,0,1)}"></i><a href="${pageContext.request.contextPath}/provider/${user.id}"  class="profile-icon"></a>
                                    <c:forEach var="shop" items="${user.shops}" varStatus="st">
                                        <c:if test="${st.count==1 && currentUserId != fn:trim(user.id)}">
                                            <c:if test="${isAttention}">
                                                <a class="attention-icon" data-id="${shop.id}">已关注</a>
                                            </c:if>
                                            <c:if test="${!isAttention}">
                                                <a href="#"  class="attention-icon attention-btn" data-id="${shop.id}">加关注</a>
                                            </c:if>
                                        </c:if>
                                    </c:forEach>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
                <input type="hidden" id="currentUserId" value="${currentUserId}">
                <!--联系方式-->
                <div class="relation">
                    <div class="record-list">
                        <ul>
                            <li><strong class="name">联系人：</strong>${user.contactPerson}</li>
                            <li><strong>固定电话：</strong>${user.fixedTelephone}</li>
                            <li><strong>联系电话：</strong>${user.mobileTelephone}</li>
                            <li><strong>传真：</strong>${user.fax}</li>
                            <li><strong>联系人邮箱：</strong>${user.email}</li>
                        </ul>
                    </div>
                </div>
                <!--联系方式 end-->
                <!--主营行业-->
                <div class="business">
                    <h6>主营行业</h6>
                    <div class="business-txt">
                        ${user.mainIndustry}
                    </div>
                </div>
                <!--主营行业 end-->
                <!--主营产品或服务-->
                <div class="business">
                    <h6>主营产品或服务</h6>
                    <div class="business-txt">
                        ${user.mianProductService}
                    </div>
                </div>
                <!--主营产品或服务 end-->
                <!--公司简介-->
                <div class="business">
                    <h6>公司简介</h6>
                    <div class="business-txt">
                        ${user.companyRemark}
                    </div>
                </div>
                <!--公司简介 end-->
            </div>
        </div>
        <!--公司档案-->
    </div>
    <!--左侧 end-->
    <!--右边-->
    <div class="main">
        <!--企业基本信息-->
        <div class="modebox enterprise-info">
            <div class="hd"><h6>企业基本信息</h6></div>
            <div class="con enterprise-con">
                <div class="enterprise-list">
                    <ul>
                        <li><span class="name">公司名称：</span>${user.companyName}</li>
                        <li><span class="name">公司地址：</span>${user.companyAddr}</li>
                        <li><span class="name">邮编：</span>${user.zipCode}</li>
                    </ul>
                </div>
                <div class="enterprise-list license-list">
                    <ul>
                        <li><span class="name">营业执照号码：</span>${user.businessLicenseCode}</li>
                        <li><span class="name">营业执照注册地址：</span>${user.businessLicRegisterProv}</li>
                        <li><span class="name">成立时间：</span>${user.businessLicEffectiveDate}</li>
                        <%--<li><span class="name">注册资本：</span>人民币100.00万</li>--%>
                        <li><span class="name">经营范围：</span>${user.business}</li>
                        <li><span class="name">组织机构代码：</span>${user.orgCode}</li>
                        <%--<li><span class="name">法人姓名：</span>${user.contactPerson}</li>--%>
                        <li><span class="name">开户行：</span>${user.bank}</li>
                        <li><span class="name">银行账户：</span>${user.bankCcount}</li>
                    </ul>
                </div>
            </div>
        </div>
        <!--企业基本信息 end-->
        <!--企业资质认证-->
        <div class="modebox aptitude-approve">
            <div class="hd"><h6>企业资质认证</h6></div>
            <div class="con aptitude-con">
                <div class="aptitude-list">
                    <ul>
                        <c:if test="${not empty user.yyzz}">
                        <li><img src="${pageContext.request.contextPath}${user.yyzz}?size=206" class="img">
                            <div class="name">营业执照<a href="${pageContext.request.contextPath}${user.yyzz}" target="_blank" title="" class="check">查看大图</a></div>
                        </li>
                        </c:if>
                        <c:if test="${not empty user.org}">
                        <li><img src="${pageContext.request.contextPath}${user.org}?size=206" class="img">
                            <div class="name">组织机构代码扫描件<a href="${pageContext.request.contextPath}${user.org}" target="_blank" title="" class="check">查看大图</a></div>
                        </li>
                        </c:if>
                        <c:if test="${not empty user.tax}">
                        <li><img src="${pageContext.request.contextPath}${user.tax}?size=206" class="img">
                            <div class="name">税务登记扫描件<a href="${pageContext.request.contextPath}${user.tax}" target="_blank" title="" class="check">查看大图</a></div>
                        </li>
                        </c:if>
                    </ul>
                </div>
            </div>
        </div>
        <!--企业资质认证 end-->
    </div>
    <!--右边 end-->
</div>
