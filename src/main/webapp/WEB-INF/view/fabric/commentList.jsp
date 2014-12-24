<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sbTag" uri="http://zodiake.com" %>

<table class="table table-border">
    <thead>
    <tr>
        <th>
            <select name="type" class="selectComment" data-id="${id}" >
                <option value="0" <c:if test="${type==0}">selected="selected" </c:if>>全部</option>
                <option value="1" <c:if test="${type==1}">selected="selected" </c:if>>好评(<bdo>${good}</bdo>)</option>
                <option value="2" <c:if test="${type==2}">selected="selected" </c:if>>中评(<bdo>${yb}</bdo>)</option>
                <option value="3" <c:if test="${type==3}">selected="selected" </c:if>>差评(<bdo>${waste}</bdo>)</option>
            </select>
        </th>
        <th>买家</th>
        <th>评论内容</th>
        <th>发布时间</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="item" items="${grid.ecList}">
        <tr>
            <td>${item.type}</td>
            <td>
                <c:choose>
                    <c:when test="${item.hasName == 1}">
                        <c:choose>
                            <c:when test="${fn:length(item.user.id) > 2}">
                                ${fn:substring(item.user.id, 0,1 )}**${fn:substring(item.user.id, fn:length(item.user.id)-1, fn:length(item.user.id))}
                            </c:when>
                            <c:otherwise>
                                ${fn:substring(item.user.id,0,1)}**${fn:substring(item.user.id, 1, 2)}
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                    <c:otherwise>
                        ${item.user.id}
                    </c:otherwise>
                </c:choose>
            </td>
            <td>${item.content}</td>
            <td><fmt:formatDate value="${item.createdTime.time}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<c:if test="${grid.totalPages>1}">
    <%--<ul class="pagination navigationTo" id="commentsPage">
        <li><a data-url="${pageContext.request.contextPath}/fabric/${id}/comments?type=${type}&page=${grid.currentPage-1<1?1:grid.currentPage-1}"  data-id="${type}>上一页</a></li>

        <c:forEach varStatus="status" begin="${grid.currentPage-3<1?1:grid.currentPage-3}" end="${grid.currentPage+3>grid.totalPages?grid.totalPages:grid.currentPage+3}">
            <li <c:if test='${grid.currentPage == status.current}'>class='active'</c:if>>
                <a data-url="${pageContext.request.contextPath}/fabric/${id}/comments?type=${type}&page=${grid.currentPage}">${status.current}</a>
            </li>
        </c:forEach>

        <li><a data-url="${pageContext.request.contextPath}/fabric/${id}/comments?type=${type}&page=${grid.currentPage+1>grid.totalPages?grid.totalPages:grid.currentPage+1}"  data-id="${type}>下一页</a></li>
    </ul>--%>
    <sbTag:page total="${grid.totalPages}" current="${grid.currentPage}" dataUrl="${pageContext.request.contextPath}/fabric/${id}/comments?type=${type}"></sbTag:page>
</c:if>