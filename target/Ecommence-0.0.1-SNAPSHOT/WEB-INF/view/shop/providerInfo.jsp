<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 弹窗开始 -->
<div class="fixed"></div>
<div class="list-box">
    <span class="close-x"><a href="javascript:void(0);" title="关闭" class="close-this">X</a></span>
    <i class="success-icon"></i>
    <div class="box-con"></div>
</div>
<!-- 弹窗结束 -->
<div class="hd"><h6>供应商信息</h6></div>
<div class="con supplier-con">
    <!--LOGO-->
    <div class="record-pic">
        <div class="record-logo"><span class="hook"></span>
            <a href="${pageContext.request.contextPath}/shopCenter/${user.id}/items">
                <c:choose>
                    <c:when test="${not empty user.logo}">
                        <img src="${pageContext.request.contextPath}${user.logo}" width="258" height="258">
                    </c:when>
                    <c:otherwise>
                        <img src="${pageContext.request.contextPath}/resources/img/btn-th.png" />
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
                    <li><strong>综合评分：</strong><i class="icon-stars icon-stars-${fn:substring(compositeScore.score,0,1)}"></i>
                        <a href="${pageContext.request.contextPath}/provider/${user.id}"  class="profile-icon"></a>
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
    <!--LOGO-->
    <!---公司信息-->
    <div class="supplier-box">
        <div class="supp-mode">
            <h6>公司主营 OUR PRODUCTS</h6>
            <div class="supplier-tips">
                ${user.mianProductService}
            </div>
        </div>
        <div class="supp-mode supp-aubot">
            <h6>公司简介 COMPANY PROFILE</h6>
            <div class="supplier-tips">
                <p>${user.companyRemark}</p>
            </div>
        </div>
    </div>
    <!---公司信息 end-->
</div>

