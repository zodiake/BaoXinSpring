<%--
  Created by IntelliJ IDEA.
  User: Charles
  Date: 2014/6/3
  Time: 14:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <!-- /.Detail Bread -->
    <div class=" publish-info">
    <form:form modelAttribute="advertisement" method="post" action="${pageContext.request.contextPath}/admin/advertisement/add">
        <ul>
            <li><label>广告名称：</label><form:input path="title"></form:input></li>
            <li><label>二级名称：</label><form:input path="subTitle"></form:input><p style="font-style: italic;font-size: 16;">*在新季推荐中表示产品单价,不需要单位</p</li>
            <li><label>广告描述：</label><form:input path="desc"></form:input><p style="font-style: italic;font-size: 16;">*在新季推荐中表示产品单位,如“/米”</p></li>
            <li><label>广告栏位：</label>
                <select id="position_id" name="position_id">
                    <c:forEach var="position" items="${advertisementPositionList}">
                        <option value="${position.id}" <c:if test="${position.id == advertisement.advertisementPosition.id}">selected="selected" </c:if>>${position.name}</option>
                    </c:forEach>
                </select>
            </li>
            <li><label>广告产品关联：</label><form:textarea path="link" cols="80" rows="2"></form:textarea></li>
            <li><label></label><p style="font-style: italic;font-size: 16;">*请输入相关产品或店铺的完整URL地址，需要带http头</p></li>
            <li><label>封面图片：</label>
                <div class="thumbDiv">
                <input id="fileToUpload"  type="file" name="fileToUpload" value=""/>
                <form:hidden path="coverPath"></form:hidden>
                <div>
                    <p style="font-size: 14;">*图片上传规格：<br/>1、首页banner：577X312；<br/>2、综合求购页banner：810X283；<br/>3、其他栏位请上传正方形图片，像素在300px最佳</p>
                </div>
                </div>
            </li>
            <li>
                <div>封面预览图：
                    <c:if test="${advertisement.coverPath != ''}">
                        <img src="${pageContext.request.contextPath}${advertisement.coverPath}" class="thumb" id="preViewPic">
                    </c:if>
                    <c:if test="${advertisement.coverPath == ''}">
                        <img src="${pageContext.request.contextPath}/resources/img/btn-th.jpg" class="thumb" id="preViewPic">
                    </c:if>
                </div>
            <li>
            <input type="hidden" id="id" name="id"value="${advertisement.id}" >
            <li>
                <div class="center">
                    <input type="submit" value="保存" name="_target1" />
                    <input type="button" value="取消" name="_cancel"  id="cancelBtn"/>
                </div>
            <li>

    </form:form>
    </div>
</div>
