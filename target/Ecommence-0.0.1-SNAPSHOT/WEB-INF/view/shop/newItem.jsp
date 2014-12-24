<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="hd"><h6>新品展示New Looks for Materials</h6></div>
<div class="con product-box">
    <div class="pic-list">
        <ul>
            <c:forEach var="item" items="${list}">
            <li>
                <div class="pic"><a href="${item.url}" title="">
                    <c:choose>
                        <c:when test="${not empty item.coverImage}">
                            <img src="${item.coverImage}">
                        </c:when>
                        <c:otherwise><img src="${pageContext.request.contextPath}/resources/img/btn-th.png" /></c:otherwise>
                    </c:choose>
                    </a>
                    <div class="products-tips">
                        <p class="text-over">标题：<a href="${item.url}" title="${item.name}" >${item.name}</a></p>
                        <p>价格：${item.price}元</p>
                        <p>成交笔数：${item.bidCount}笔</p>
                    </div>
                </div>
            </li>
            </c:forEach>
        </ul>
    </div>
</div>

