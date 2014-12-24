<%--
  Created by IntelliJ IDEA.
  User: Charles
  Date: 2014/6/3
  Time: 14:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sbTag" uri="http://zodiake.com" %>
<div class="main">
    <!-- /.Detail Bread -->
    <div class="bread detail-bread">
        <span>您的位置：</span>
        <ul>
            <li><a href="${pageContext.request.contextPath}/admin">后台管理</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/advertisementList">广告维护</a></li>
            <li>广告维护</li>
        </ul>
    </div>
    <div>
        <a href="${pageContext.request.contextPath}/admin/advertisement/add" class="button button-deep">添加广告</a><br/><br/>
    </div>
    <!-- /.Detail Bread -->
    <!-- /.Detail Table -->
    <div class="detail-table">
            <table class="adpList table table-border table-order">
                <tr>
                    <th>序号</th>
                    <th>广告名称</th>
                    <th>栏位名称(栏位编号)</th>
                    <th>关联的产品</th>
                    <th>创建日期</th>
                    <th>操作</th>
                </tr>
                <c:forEach var="advertisement" items="${grid.ecList}" varStatus="list">
                    <tr class="tr-${advertisement.id}">
                        <td>${list.index+1}</td>
                        <td><a href="${advertisement.link}" data-id="${advertisement.id}">${advertisement.title}</a></td>
                        <td>${advertisement.advertisementPosition.name}(${advertisement.advertisementPosition.positionNo})</td>
                        <td><a href="${advertisement.link}">查看产品详情</a></td>
                        <td><fmt:formatDate value="${advertisement.createdTime.time}" pattern="yyyy-MM-dd HH:mm" type="date"/></td>
                        <td>
                            <button class="button button-deep editAd" data-id="${advertisement.id}">编辑</button>
                            <button class="button button-deep deleteAd" data-id="${advertisement.id}">删除</button>
                        </td>
                    </tr>
                </c:forEach>
                <tr class="sep-row"><td colspan="7" height="10px"></td></tr>
            </table>
        </div>
        <!--翻页-->
        <div class="text-right">
            <c:if test="${grid.totalPages>1}">
                <%--<ul class="pagination navigationTo">--%>
                    <%--<li><a data-url="${pageContext.request.contextPath}/admin/advertisementList?page=1">首页</a></li>--%>

                    <%--<c:forEach varStatus="status" begin="${grid.currentPage-3<1?1:grid.currentPage-3}" end="${grid.currentPage+3>grid.totalPages?grid.totalPages:grid.currentPage+3}">--%>
                        <%--<li <c:if test='${grid.currentPage == status.current}'> class="active"</c:if>>--%>
                            <%--<a data-url="${pageContext.request.contextPath}/admin/advertisementList?page=${status.current}">${status.current}</a>--%>
                        <%--</li>--%>
                    <%--</c:forEach>--%>

                    <%--<li><a data-url="${pageContext.request.contextPath}/admin/advertisementList?page=${grid.totalPages}">末页</a></li>--%>
                <%--</ul>--%>
                <sbTag:page total="${grid.totalPages}" current="${grid.currentPage}" href="${pageContext.request.contextPath}/admin/advertisementList?1=1"></sbTag:page>
            </c:if>
        </div>
        <!--翻页 end-->
</div>
