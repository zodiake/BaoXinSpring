<%--
  Created by IntelliJ IDEA.
  User: zodiake
  Date: 2014/6/11
  Time: 15:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sbTag" uri="http://zodiake.com" %>
<%--<table class="table table-stripe table-hover">
    <caption>30天内：交易中<bdo class="deep">${totalBid}</bdo>交易成功<bdo class="deep">${totalBidSuccess}</bdo></caption>
    <thead>
    <tr>
        <th>买家</th>
        <th>拍下价格</th>
        <th>数量</th>
        <th>成交时间</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${grid.ecList}" var="item">
        <tr>
            <td>${item.orderItem.buyer.name}</td>
            <td><bdo class="price">${item.price}</bdo></td>
            <td>${item.quantity}</td>
            <td><fmt:formatDate value="${item.createdTime.time}" pattern="yyyy-MM-dd HH:mm:ss" type="date"></fmt:formatDate></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="text-right" id="orders">
    <c:if test="${grid.totalPages>0}">
            <ul class="pagination">
                <li><a data-url="${pageContext.request.contextPath}/fabric/${id}/orders?page=1">首页</a></li>

                <c:forEach varStatus="status" begin="${grid.currentPage-3<0?1:grid.currentPage-3}" end="${grid.currentPage+3>grid.totalPages?grid.totalPages:grid.currentPage+4}">
                    <li <c:if test='${grid.currentPage == status.current}'>class="active"</c:if>>
                        <a data-url="${pageContext.request.contextPath}/fabric/${id}/orders?page=${grid.currentPage}">${status.current}</a>
                    </li>
                </c:forEach>

                <li><a data-url="${pageContext.request.contextPath}/fabric/${id}/orders?page=${grid.totalPages}">末页</a></li>
            </ul>
    </c:if>
</div>--%>

<table class="table table-stripe">
    <caption>30天内：交易中<bdo class="deep">${totalBid}</bdo>，交易成功<bdo class="deep">${totalBidSuccess}</bdo></caption>
    <thead>
    <tr>
        <th>买家</th>
        <th>拍下价格</th>
        <th>数量</th>
        <th>成交时间</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="item" items="${grid.ecList}">
        <tr>
            <td>
            <%--${item.orderItem.buyer.name}--%>

                <c:choose>
                    <c:when test="${fn:length(item.orderItem.buyer.id) > 2}">
                        ${fn:substring(item.orderItem.buyer.id, 0,1 )}**${fn:substring(item.orderItem.buyer.id, fn:length(item.orderItem.buyer.id)-1, fn:length(item.orderItem.buyer.name))}
                    </c:when>
                    <c:otherwise>
                        ${fn:substring(item.orderItem.buyer.id,0,1)}**${fn:substring(item.orderItem.buyer.id, 1, 2)}
                    </c:otherwise>
                </c:choose>

            </td>
            <td><bdo class="price">${item.price}</bdo></td>
            <td>${item.quantity}</td>
            <td><fmt:formatDate value="${item.orderItem.createdTime.time}" pattern="yyyy-MM-dd HH:mm:ss" type="date"></fmt:formatDate></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<c:if test="${grid.totalPages>0}">
    <%--<ul class="pagination navigationTo" id="ordersPage">
        <li><a data-url="${pageContext.request.contextPath}/fabric/${id}/orders?page=${grid.currentPage-1<1?1:grid.currentPage-1}">上一页</a></li>

        <c:forEach varStatus="status" begin="${grid.currentPage-3<1?1:grid.currentPage-3}" end="${grid.currentPage+3>grid.totalPages?grid.totalPages:grid.currentPage+3}">
            <li <c:if test='${grid.currentPage == status.current}'>class="active"</c:if>>
                <a data-url="${pageContext.request.contextPath}/fabric/${id}/orders?page=${grid.currentPage}">${status.current}</a>
            </li>
        </c:forEach>

        <li><a data-url="${pageContext.request.contextPath}/fabric/${id}/orders?page=${grid.currentPage+1>grid.totalPages?grid.totalPages:grid.currentPage+1}">下一页</a></li>
    </ul>--%>
    <sbTag:page total="${grid.totalPages}" current="${grid.currentPage}" dataUrl="${pageContext.request.contextPath}/fabric/${id}/orders"></sbTag:page>
</c:if>