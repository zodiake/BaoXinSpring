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
<div>
    <a  class="button button-deep" href="${pageContext.request.contextPath}/admin/materialSecondCategory/add">添加辅料产品二级类别</a><br/><br/>
    <div class="detail-table">
        <table class="table table-border">
            <thead>
            <tr>
                <th>序号</th>
                <th>产品类别</th>
                <th>一级分类</th>
                <th>创建人</th>
                <th>创建时间</th>
                <th>更新人</th>
                <th>更新时间</th>
                <th>是否有效</th>
                <th>操作</th>
            </tr>
            </thead>
            <c:forEach var="secondCategory" items="${grid.ecList}" varStatus="list">
                <tr class="tr-${secondCategory.id}">
                    <td>${list.index+1}</td>
                    <td>${secondCategory.name}</td>
                    <td>${secondCategory.parentCategory.name}</td>
                    <td>${secondCategory.createdBy.name}</td>
                    <td><fmt:formatDate value="${secondCategory.createdTime.time}" pattern="yyyy-MM-dd HH:mm" type="date"/></td>
                    <td>${secondCategory.updatedBy.name}</td>
                    <td><fmt:formatDate value="${secondCategory.updatedTime.time}" pattern="yyyy-MM-dd HH:mm" type="date"/></td>
                    <td>
                        <c:choose>
                            <c:when test="${secondCategory.isValid==0}">是</c:when>
                            <c:when test="${secondCategory.isValid==1}">否</c:when>
                        </c:choose>
                    </td>
                    <td><button type="button" class="button button-deep editSecond" data-id="${secondCategory.id}">编辑</button></td>
                </tr>
            </c:forEach>
        </table>
    </div>
    <div class="text-right" id="myOrders">
        <c:if test="${grid.totalPages>0}">
            <ul class="pagination navigationTo">
                <li><a href="${pageContext.request.contextPath}/admin/materialSecondCategory?page=1">首页</a></li>

                <c:forEach varStatus="status" begin="${grid.currentPage-3<1?1:grid.currentPage-3}" end="${grid.currentPage+3>grid.totalPages?grid.totalPages:grid.currentPage+3}">
                    <li <c:if test='${grid.currentPage == status.current}'> class="active"</c:if>>
                        <a href="${pageContext.request.contextPath}/admin/materialSecondCategory?page=${status.current}">${status.current}</a>
                    </li>
                </c:forEach>

                <li><a href="${pageContext.request.contextPath}/admin/materialSecondCategory?page=${grid.totalPages}">末页</a></li>
            </ul>
        </c:if>
    </div>
</div>