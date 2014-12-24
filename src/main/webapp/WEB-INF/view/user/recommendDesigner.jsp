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
<c:if test="${not empty recommendDesigners}">
    <div class="modebox new-products">
        <div class="hd"><h6>设计师推荐</h6></div>
        <div class="con">
            <div class="pic-list">
                <ul>
                    <c:forEach items="${recommendDesigners}" var="designer">
                        <li>
                            <div class="pic">
                                <a href="${linkMap['designerDetail']}${designer.id}" title="${designer.name}" target="_blank">
                                    <c:choose>
                                        <c:when test="${not empty designer.headUrl}"><img src="${linkMap['designerImg']}${designer.headUrl}"></c:when>
                                        <c:otherwise><img src="${pageContext.request.contextPath}/resources/img/btn-th.png"></c:otherwise>
                                    </c:choose>
                                </a>
                            </div>
                            <div class="name">
                                <a href="${linkMap['designerDetail']}${designer.id}" target="_blank">${designer.name}</a>
                                <c:if test="${ empty designer.name}"><br/></c:if>
                            </div>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </div>
</c:if>
<!---品牌推荐 end-->