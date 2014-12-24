<%--
  Created by IntelliJ IDEA.
  User: Charles
  Date: 2014/6/5
  Time: 14:55
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
            <li><a href="${pageContext.request.contextPath}/admin/informationList">资讯公告维护</a></li>
            <li>资讯分类维护</li>
        </ul>
    </div>
    <!-- /.Detail Bread -->
    <!-- /.Detail Table -->
    <div class="detail-table">
    <table class="adpList table table-border table-order">
        <tr>
            <th>序号</th>
            <th>栏位编号</th>
            <th>栏位名称</th>
            <th>说明</th>
            <!--th>创建日期</th-->
            <th>操作</th>
        </tr>
        <c:forEach var="informationCategory" items="${grid.ecList}" varStatus="list">
            <tr class="tr-${informationCategory.id}">
                <td>${list.index+1}</td>
                <td>${informationCategory.id}</td>
                <td>${informationCategory.categoryName}</td>
                <td>${informationCategory.description}</td>
                <!--td><fmt:formatDate value="${informationCategory.createdTime.time}" pattern="yyyy-MM-dd HH:mm" type="date"/></td-->
                <td>
                    <button class="button button-deep editInfoCate" data-id="${informationCategory.id}">编辑</button>
                    <button class="button button-deep deleteInfoCate" data-id="${informationCategory.id}">删除</button>
                </td>
            </tr>
        </c:forEach>
        <tr class="sep-row"><td colspan="7" height="10px"></td></tr>
    </table>
    </div>
    <div class="text-right">
        <c:if test="${grid.totalPages>1}">
            <sbTag:page total="${grid.totalPages}" current="${grid.currentPage}" href="${pageContext.request.contextPath}/admin/informationList?1=1"></sbTag:page>
        </c:if>
    </div>
</div>
<div class="central publish-info">
    <form id="infoCateForm" action="/admin/informationCategory/add" method="post">
        <div align="left" style="border: 1px solid cornsilk">
            <ul>
                <li><span class="name">分类名称：</span><input type="text" id="categoryName" name="categoryName" value="" class="input-t"></li>
                <li><span class="name">栏位说明：</span><br/><textarea id="description" name="description" cols="40" rows="5"></textarea></li>
            </ul>
        </div>

        <input type="hidden" id="id" name="id" value="">
        <button class="saveInfoCate">保存</button><button class="cancelInfoCate">取消</button>
    </form>
</div>



