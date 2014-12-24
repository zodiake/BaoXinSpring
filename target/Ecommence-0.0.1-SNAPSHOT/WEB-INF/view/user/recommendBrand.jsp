<%--
  Created by IntelliJ IDEA.
  User: Charles
  Date: 2014/9/15
  Time: 14:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!---品牌推荐-->
<c:if test="${not empty recommendBrands}">
    <div class="modebox new-products">
        <div class="hd"><h6>品牌推荐</h6></div>
        <div class="con">
            <div class="pic-list">
                <ul>
                    <c:forEach items="${recommendBrands}" var="brand">
                        <li>
                            <div class="pic">
                                <a href="${linkMap['brandDetail']}${brand.id}" title="${brand.name}" target="_blank">
                                    <c:choose>
                                        <c:when test="${not empty brand.headUrl}"><img src="${linkMap['designerImg']}${brand.headUrl}"></c:when>
                                        <c:otherwise><img src="${pageContext.request.contextPath}/resources/img/btn-th.png"></c:otherwise>
                                    </c:choose>
                                </a>
                            </div>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </div>
</c:if>
<!---品牌推荐 end-->
