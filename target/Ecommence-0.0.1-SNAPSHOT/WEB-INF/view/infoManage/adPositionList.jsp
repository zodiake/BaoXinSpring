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
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="main">
    <!-- /.Detail Bread -->
    <div class="bread detail-bread">
        <span>您的位置：</span>
        <ul>
            <li><a href="/">后台管理</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/advertisementList">广告维护</a></li>
            <li>广告栏位维护</li>
        </ul>
    </div>
    <!-- /.Detail Bread -->
    <!-- /.Detail Table -->
    <table class="adpList table table-border table-order">
        <tr>
            <th>序号</th>
            <th>栏位编号</th>
            <th>栏位名称</th>
            <th>说明</th>
            <th>创建日期</th>
            <th>操作</th>
        </tr>
        <c:forEach var="advertisementPosition" items="${grid.ecList}" varStatus="list">
            <tr class="tr-${advertisementPosition.id}">
                <td>${list.index+1}</td>
                <td><a href="#" class="viewAdp" data-id="${advertisementPosition.id}">${advertisementPosition.positionNo}</a></td>
                <td>${advertisementPosition.name}</td>
                <td>${advertisementPosition.description}</td>
                <td><fmt:formatDate value="${advertisementPosition.createdTime.time}" pattern="yyyy-MM-dd HH:mm" type="date"/></td>
                <td>
                    <button class="button button-deep viewAdp" data-id="${advertisementPosition.id}">编辑</button>
                    <button class="button button-deep deleteAdp" data-id="${advertisementPosition.id}">删除</button>
                 </td>
            </tr>
        </c:forEach>
        <tr class="sep-row"><td colspan="7" height="10px"></td></tr>
    </table>
</div>
<div class="central publish-info">
    <form:form modelAttribute="advertisementPosition" method="post" action="${pageContext.request.contextPath}/admin/advertisementPosition/add">
        <div align="left" style="border: 1px solid cornsilk">
            <ul>
                <li><span class="name">栏位编号：</span><form:input path="positionNo" class="input-t"></form:input> </li>
                <li><span class="name">栏位名称：</span><form:input path="name" class="input-t"></form:input></li>
                <li><span class="name">栏位说明：</span><br/><form:textarea path="description" cols="40" rows="5"></form:textarea></li>
            </ul>
        </div>
        <form:hidden path="id" class="input-t"></form:hidden>
        <button class="saveAdp">保存</button><button class="cancelAdp">取消</button>
    </form:form>
</div>



