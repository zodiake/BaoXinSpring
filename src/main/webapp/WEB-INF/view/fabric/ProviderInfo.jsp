<%--
  Created by IntelliJ IDEA.
  User: zodiake
  Date: 2014/6/11
  Time: 11:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!-- /.box -->
<dl class="business-board box">
    <dt>商家信息</dt>
    <dd>
        <a class="central" href="${pageContext.request.contextPath}/shopCenter/${user.id}/items"><i></i>
            <c:choose>
                <c:when test="${not empty user.logo}">
                    <img src="${pageContext.request.contextPath}${user.logo}" style="width: 248px;height: 248px">
                </c:when>
                <c:otherwise>
                    <img src="${pageContext.request.contextPath}/resources/img/btn-th.png" style="width: 248px;height: 248px"/>
                </c:otherwise>
            </c:choose>
        </a>
        <ul class="pr">
            <li>
                <label>公司名称：</label>
                <span>${user.companyName}</span>
            </li>
            <li>
                <label>公司地址：</label>
                <span>${user.companyAddr}</span>
            </li>
            <li>
                <label>官方网站：</label>
                <span><a href="#" data-url="${user.companyWebsite}" class="locationHomePage">${user.companyWebsite}</a></span>
            </li>
            <li>
                <label>综合评分：</label>
                <span><i class="icon-stars icon-stars-${fn:substring(score.score,0,1)}"></i>
                    <a href="${pageContext.request.contextPath}/provider/${user.id}"  class="profile-icon"></a>
                    <c:if test="${currentUserId != fn:trim(user.id)}">
                        <c:forEach var="shop" items="${user.shops}" varStatus="st">
                            <c:if test="${st.count==1}">
                                <c:if test="${isAttention}">
                                    <a class="attention-icon" data-id="${shop.id}">已关注</a>
                                </c:if>
                                <c:if test="${!isAttention}">
                                    <a href="#"  class="attention-icon attention-btn" data-id="${shop.id}">加关注</a>
                                </c:if>
                            </c:if>
                        </c:forEach>
                    </c:if>
                </span>
            </li>
        </ul>
    </dd>
</dl>
<!-- /.box -->
<input type="hidden" id="currentUserId" value="${currentUserId}">