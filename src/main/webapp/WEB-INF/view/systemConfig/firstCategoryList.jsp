<%--
  Created by IntelliJ IDEA.
  User: Charles
  Date: 2014/6/6
  Time: 11:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sbTag" uri="http://zodiake.com" %>
<div>
    <a  class="button button-deep" href="${pageContext.request.contextPath}/admin/fabricFirstCategory/add">添加产品类别</a><br/><br/>
    <div class="detail-table">
        <table class="table table-border">
            <thead>
            <tr>
                <th>序号</th>
                <th>产品类别</th>
                <th>创建人</th>
                <th>创建时间</th>
                <th>更新人</th>
                <th>更新时间</th>
                <th>是否有效</th>
                <th>操作</th>
            </tr>
            </thead>
            <c:forEach var="firstCategory" items="${grid.ecList}">
                <tr class="tr-${firstCategory.id}">
                    <td>${firstCategory.sortNo}</td>
                    <td><a href="${pageContext.request.contextPath}/admin/fabricSecondCategory/add/${firstCategory.id}">${firstCategory.name}</a></td>
                    <td>${firstCategory.createdBy.name}</td>
                    <td><fmt:formatDate value="${firstCategory.createdTime.time}" pattern="yyyy-MM-dd HH:mm" type="date"/></td>
                    <td>${firstCategory.updatedBy.name}</td>
                    <td><fmt:formatDate value="${firstCategory.updatedTime.time}" pattern="yyyy-MM-dd HH:mm" type="date"/></td>
                    <td>
                        <c:choose>
                            <c:when test="${firstCategory.isValid == 0}">是</c:when>
                            <c:otherwise>否</c:otherwise>
                        </c:choose>
                    </td>
                    <td><button type="button" class="button button-deep editFirst" data-id="${firstCategory.id}">编辑</button></td>
                </tr>
            </c:forEach>
        </table>
        <div class="text-right">
            <c:if test="${grid.totalPages>1}">
                <%--<ul class="pagination navigationTo">--%>
                    <%--<li><a href="${pageContext.request.contextPath}/admin/fabricFirstCategory?page=1">首页</a></li>--%>

                    <%--<c:forEach varStatus="status" begin="${grid.currentPage-3<1?1:grid.currentPage-3}" end="${grid.currentPage+3>grid.totalPages?grid.totalPages:grid.currentPage+3}">--%>
                        <%--<li <c:if test='${grid.currentPage == status.current}'> class="active"</c:if>>--%>
                            <%--<a href="${pageContext.request.contextPath}/admin/fabricFirstCategory?page=${status.current}">${status.current}</a>--%>
                        <%--</li>--%>
                    <%--</c:forEach>--%>

                    <%--<li><a href="${pageContext.request.contextPath}/admin/fabricFirstCategory?page=${grid.totalPages}">末页</a></li>--%>
                <%--</ul>--%>
                <sbTag:page total="${grid.totalPages}" current="${grid.currentPage}" href="${pageContext.request.contextPath}/admin/fabricFirstCategory?1=1"></sbTag:page>
            </c:if>
        </div>
    </div>
</div>