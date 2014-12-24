<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: zodiake
  Date: 2014/6/9
  Time: 15:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<dl class="detail-recommend box">
    <dt>同类推荐</dt>
    <dd>
        <div class="row-6">
            <c:forEach items="${like}" var="l">
                <div class="col-1">
                    <%--<a href="#"><img class="thumb" src="/resources/pic/designer.png"></a>--%>

                    <c:choose>
                        <c:when test="${not empty l.cover}">
                            <a href="/material/${l.id}"><img class="thumb" src="${pageContext.request.contextPath}${l.cover}?size=300"></a>
                        </c:when>
                        <c:otherwise>
                            <a href="/material/${l.id}"><img class="thumb" src="${pageContext.request.contextPath}/resources/img/btn-th.png"></a>
                        </c:otherwise>
                    </c:choose>
                    <ul>
                        <li  class="text-over">
                            <span><a href="${pageContext.request.contextPath}/material/${material.id}" title="${l.title}">${l.title}</a></span>
                        </li>
                        <%--<li>
                            <label>成份：</label>
                            <span>${l.source}</span>
                        </li>--%>
                        <li>
                            <span>
                                <c:choose>
                                    <c:when test="${l.price > 0.0}">
                                        <bdo class="price orange">${l.price}</bdo><c:if test="${not empty l.price}">元</c:if>
                                    </c:when>
                                    <c:otherwise>
                                        <bdo class="price orange">面议</bdo>
                                    </c:otherwise>
                                </c:choose>
                            </span>
                        </li>
                    </ul>
                </div>
            </c:forEach>
        </div>
    </dd>
</dl>