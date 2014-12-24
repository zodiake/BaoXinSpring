<%--
  Created by IntelliJ IDEA.
  User: Charles
  Date: 2014/6/12
  Time: 10:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- /.Sider Bar -->
<div class="sidebar">
    <!-- /.box -->
    <dl class="commodity-list box">
        <dt>后台管理</dt>
        <!--
        <dd>
            <span>
                <i class="icon icon-plus"></i>
                交易管理
            </span>
            <ul style="display: none;">
                <li><a href="${pageContext.request.contextPath}/admin/advertisementPosition"  <c:if test="${menu == 11}">class="selected"</c:if> >面料管理</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/advertisement" <c:if test="${menu == 12}">class="selected"</c:if>>辅料管理</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/informationCategory" <c:if test="${menu == 13}">class="selected"</c:if>>求购管理</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/information" <c:if test="${menu == 14}">class="selected"</c:if>>供应商管理</a></li>
            </ul>
        </dd>
        -->
        <dd>
            <span>
                <i class="icon icon-minus"></i>
                面料分类维护
            </span>
                <li><a href="${pageContext.request.contextPath}/admin/fabricFirstCategory" <c:if test="${menu == 5}">class="selected"</c:if>>面料一级分类维护</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/fabricSecondCategory" <c:if test="${menu == 6}">class="selected"</c:if>>面料二级分类维护</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/firstSource" <c:if test="${menu == 7}">class="selected"</c:if>>面料原料一级分类维护</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/secondSource" <c:if test="${menu == 8}">class="selected"</c:if>>面料原料二级分类维护</a></li>
            </ul>
        </dd>
        <dd>
            <span>
                <i class="icon icon-minus"></i>
                辅料分类维护
            </span>
            <ul>
                <li><a href="${pageContext.request.contextPath}/admin/materialFirstCategory" <c:if test="${menu == 9}">class="selected"</c:if>>辅料一级分类维护</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/materialSecondCategory" <c:if test="${menu == 10}">class="selected"</c:if>>辅料二级分类维护</a></li>
            </ul>
        </dd>
        <dd>
            <span>
                <i class="icon icon-minus"></i>
                广告资讯维护
            </span>
            <ul>
                <li><a href="${pageContext.request.contextPath}/admin/advertisementPositionList"  <c:if test="${menu == 1}">class="selected"</c:if> >广告栏位设置</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/advertisementList" <c:if test="${menu == 2}">class="selected"</c:if>>广告内容设置</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/informationCategoryList" <c:if test="${menu == 3}">class="selected"</c:if>>资讯分类设置</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/informationList" <c:if test="${menu == 4}">class="selected"</c:if>>资讯内容设置</a></li>
            </ul>
        </dd>
    </dl>
    <!-- /.box -->
</div>

