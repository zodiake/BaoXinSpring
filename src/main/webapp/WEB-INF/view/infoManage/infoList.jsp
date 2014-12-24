<%--
  Created by IntelliJ IDEA.
  User: Charles
  Date: 2014/6/5
  Time: 14:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
            <li>资讯公告列表</li>
        </ul>
    </div>
    <a class="button button-deep" href="${pageContext.request.contextPath}/admin/information/add">新建资讯</a><br/><br/>
    <!-- /.Detail Bread -->
    <!-- /.Detail Table -->
    <div class="detail-table">
        <table class="adpList table table-border table-order">
        <tr>
            <td>序号</td>
            <td>资讯标题</td>
            <td>资讯分类</td>
            <td>创建人</td>
            <td>创建日期</td>
            <td>操作</td>
        </tr>
        <c:forEach var="information" items="${grid.ecList}" varStatus="list">
            <tr class="tr-${information.id}">
                <td>${list.index+1}</td>
                <td>
                    <c:choose>
                        <c:when test="${information.informationCategory.categoryName == '资讯'}"><a href="${pageContext.request.contextPath}/info/${information.id}" target="_blank"></c:when>
                        <c:when test="${information.informationCategory.categoryName == '公告'}"><a href="${pageContext.request.contextPath}/notice/${information.id}" target="_blank"></c:when>
                        <c:when test="${information.informationCategory.categoryName == '专题'}"><a href="${pageContext.request.contextPath}/topic/${information.id}" target="_blank"></c:when>
                        <c:when test="${information.informationCategory.categoryName == '1022专题'}"><a href="${pageContext.request.contextPath}/topic1022/${information.id}" target="_blank"></c:when>
                    </c:choose>${information.title}</a></td>
                <td>${information.informationCategory.categoryName}</td>
                <td>${information.createdBy.name}</td>
                <td><fmt:formatDate value="${information.createdTime.time}" pattern="yyyy-MM-dd HH:mm" type="date"/></td>
                <td>
                    <button class="button button-deep editInfo" data-id="${information.id}">编辑</button>
                    <button class="button button-deep deleteInfo" data-id="${information.id}">删除</button>
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

