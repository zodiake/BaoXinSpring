<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sbTag" uri="http://zodiake.com" %>
<table class="table table-none">
    <thead>
    <tr>
        <th width="20%" id="sellerSend">
            <select data-id="${id}">
                <option value="0" <c:if test="${type==0}">selected="selected" </c:if>>全部</option>
                <option value="1" <c:if test="${type==1}">selected="selected" </c:if>>好评</option>
                <option value="2" <c:if test="${type==2}">selected="selected" </c:if>>中评</option>
                <option value="3" <c:if test="${type==3}">selected="selected" </c:if>>差评</option>
            </select>

        </th>
        <th width="20%">评论内容</th>
        <th width="20%">被评价人</th>
        <th>产品信息</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="comment" items="${grid.ecList}">
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
                    ${comment.content}
                <p><fmt:formatDate value="${comment.createdTime.time}" pattern="yyyy-MM-dd HH:mm:ss" type="date"></fmt:formatDate></p>
            </td>
            <td>
            <%--${comment.user.name}--%>
                <%--<c:choose>
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
                </c:choose>--%>
                    ${comment.user.id}
            </td>
            <td>${comment.name}</td>
        </tr>
    </c:forEach>
    </tbody>
    </tfoot>
    <tr>
        <td colspan="4">
            <div class="text-right" id="fromSellerSendPage">
                <c:if test="${grid.totalPages>1}">
                    <%--<ul class="pagination">
                        <li><a data-url="${pageContext.request.contextPath}/buyerCenter/${id}/sellerComments?page=${grid.currentPage-1<1?1:grid.currentPage-1}">上一页</a></li>

                        <c:forEach varStatus="status" begin="${grid.currentPage-3<=0?1:grid.currentPage-3}" end="${grid.currentPage+3>grid.totalPages?grid.totalPages:grid.currentPage+4}">
                            <li <c:if test='${grid.currentPage == status.current}'> class="active"</c:if>>
                                <a data-url="${pageContext.request.contextPath}/buyerCenter/${id}/sellerComments?page=${status.current}">${status.current}</a>
                            </li>
                        </c:forEach>

                        <li><a data-url="${pageContext.request.contextPath}/buyerCenter/${id}/sellerComments?page=${grid.currentPage+1>grid.totalPages?grid.totalPages:grid.currentPage+1}">下一页</a></li>
                    </ul>--%>
                    <sbTag:page total="${grid.totalPages}" current="${grid.currentPage}" dataUrl="${pageContext.request.contextPath}/buyerCenter/${id}/sellerComments"></sbTag:page>
                </c:if>
            </div>
        </td>
    </tr>
</table>