<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
    <c:forEach var="comment" items="${materialComments.ecList}">
        <tr>
            <td>
                <c:if test="${comment.type == '好评'}">
                    <i class="icon icon-great"></i>
                </c:if>
                <c:if test="${comment.type == '中评'}">
                    <i class="icon icon-normal"></i>
                </c:if>
                <c:if test="${comment.type == '差评'}">
                    <i class="icon icon-bad"></i>
                </c:if>
            </td>
            <td>
            <%--${comment.user.name}--%>
                <c:choose>
                    <c:when test="${comment.hasName == 1}">
                        <c:choose>
                            <c:when test="${fn:length(comment.user.id) > 2}">
                                ${fn:substring(comment.user.id, 0,1 )}**${fn:substring(comment.user.id, fn:length(comment.user.id)-1, fn:length(comment.user.id))}
                            </c:when>
                            <c:otherwise>
                                ${fn:substring(comment.user.id,0,1)}**${fn:substring(comment.user.id, 1, 2)}
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                    <c:otherwise>
                        ${comment.user.id}
                    </c:otherwise>
                </c:choose>
            </td>
            <td class="text-left">${comment.content}</td>
            <td><fmt:formatDate value="${comment.createdTime.time}" pattern="yyyy-MM-dd HH:mm:ss" type="date"></fmt:formatDate> </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<c:if test="${materialComments.totalPages>1}">
    <%--<ul class="pagination navigationTo" id="commentsPage">
        <li><a data-url="${pageContext.request.contextPath}/material/${id}/comments?page=${materialComments.currentPage-1<1?1:materialComments.currentPage-1}" data-id="${type}">上一页</a></li>

        <c:forEach varStatus="status" begin="${materialComments.currentPage-3<1?1:materialComments.currentPage-3}" end="${materialComments.currentPage+3>materialComments.totalPages?materialComments.totalPages:materialComments.currentPage+3}">
            <li <c:if test='${materialComments.currentPage == status.current}'> class="active"</c:if>>
                <a data-url="${pageContext.request.contextPath}/material/${id}/comments?page=${status.current}" data-id="${type}">${status.current}</a>
            </li>
        </c:forEach>

        <li><a data-url="${pageContext.request.contextPath}/material/${id}/comments?page=${materialComments.currentPage+1>materialComments.totalPages?materialComments.totalPages:materialComments.currentPage+1}" data-id="${type}">下一页</a></li>
    </ul>--%>
    <sbTag:page total="${grid.totalPages}" current="${grid.currentPage}" dataUrl="${pageContext.request.contextPath}/material/${id}/comments"></sbTag:page>
</c:if>