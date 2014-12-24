<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sbTag" uri="http://zodiake.com" %>
<table class="table table-stripe">
    <caption>30天内：交易中<bdo class="deep">${bidStart}</bdo>，交易成功<bdo class="deep">${bidEnd}</bdo></caption>
    <thead>
    <tr>
        <th>买家</th>
        <th>拍下价格</th>
        <th>数量</th>
        <th>成交时间</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${mgrid.ecList}" var="item">
        <tr>
            <td>
                <c:choose>
                    <c:when test="${fn:length(item.orderItem.buyer.id) > 2}">
                        ${fn:substring(item.orderItem.buyer.id, 0,1 )}**${fn:substring(item.orderItem.buyer.id, fn:length(item.orderItem.buyer.id)-1, fn:length(item.orderItem.buyer.id))}
                    </c:when>
                    <c:otherwise>
                        ${fn:substring(item.orderItem.buyer.id,0,1)}**${fn:substring(item.orderItem.buyer.id, 1, 2)}
                    </c:otherwise>
                </c:choose>
            </td>
            <td><bdo class="price">${item.price}</bdo></td>
            <td>${item.quantity}</td>
            <td><fmt:formatDate value="${item.orderItem.createdTime.time}" pattern="yyyy-MM-dd HH:mm:ss" type="date"></fmt:formatDate> </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<c:if test="${mgrid.totalPages>1}">
    <%--<ul class="pagination navigationTo" id="ordersPage">
        <li><a data-url="${pageContext.request.contextPath}/material/${id}/orders?page=${mgrid.currentPage-1<1?1:mgrid.currentPage-1}">上一页</a></li>

        <c:forEach varStatus="status" begin="${mgrid.currentPage-3<1?1:mgrid.currentPage-3}" end="${mgrid.currentPage+3>mgrid.totalPages?mgrid.totalPages:mgrid.currentPage+3}">
            <li <c:if test='${mgrid.currentPage == status.current}'>class="active"</c:if>>
                <a data-url="${pageContext.request.contextPath}/material/${id}/orders?page=${status.current}">${status.current}</a>
            </li>
        </c:forEach>

        <li><a data-url="${pageContext.request.contextPath}/material/${id}/orders?page=${mgrid.currentPage+1>mgrid.totalPages?mgrid.totalPages:mgrid.currentPage+1}">下一页</a></li>
    </ul>--%>
    <sbTag:page total="${grid.totalPages}" current="${grid.currentPage}" dataUrl="${pageContext.request.contextPath}/material/${id}/orders"></sbTag:page>
</c:if>
