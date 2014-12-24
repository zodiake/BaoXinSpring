<%--
  Created by IntelliJ IDEA.
  User: zodiake
  Date: 2014/5/23
  Time: 15:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>供应链</title>
    <link type="text/css" rel="stylesheet" media="screen" href="${pageContext.request.contextPath}/resources/css/action.css"/>
</head>
<!-- /.Header -->
<div class="header">
    <div class="mask clearfix">
        <a class="logo" href="/"></a>

        <div class="foremost">
            <h1>上海张江国家自主创新示范区<br/>基于云计算的创意设计公共服务基地</h1>

            <p>
                <em>SHANGHAI ZHANGJIANG NATION INNOVATION PARK</em>
                <i>PUBLIC SERVICE OF CREATIVE DESIGNBASED ON CLOUD COMPUTING</i>
            </p>
        </div>
        <div class="info">
            <div class="personal">
                您好，欢迎来到创意设计公共服务基地！
                <sec:authorize access="authenticated" var="authenticated"></sec:authorize>
                <sec:authorize access="!hasAnyRole('ROLE_USER','ROLE_ADMIN','ROLE_SUPERADMIN')">
                    <a href="${pageContext.request.contextPath}/login">[登录]</a> |
                    <a href="${linkMap['register']}">[免费注册]</a>
                </sec:authorize>
                <c:if test="${authenticated}">
                    <sec:authentication property="name"></sec:authentication>
                    <a href="${pageContext.request.contextPath}/logout">【退出】</a>
                </c:if>
            </div>
            <div class="search">
                <form id="searchForm" class="form clearfix" action="${pageContext.request.contextPath}/search/fabric">
                    <a id="submit-link"  title="搜索" class="icon-search"></a>
                    <input type="text" name="keyWord" class="input" placeholder="请输入关键字">
                    <div class="drop">
                        <div class="drop-menu selectedType">面&nbsp;&nbsp;&nbsp;料<i class="caret"></i></div>
                        <div class="drop-content drop-down-left selectedType-left">
                            <ul>
                                <li class="searchType" data-src="${pageContext.request.contextPath}/search/fabric">面&nbsp;&nbsp;&nbsp;料</li>
                                <li class="searchType" data-src="${pageContext.request.contextPath}/search/material">辅&nbsp;&nbsp;&nbsp;料</li>
                                <li class="searchType" data-src="${pageContext.request.contextPath}/search/shop">供应商</li>
                            </ul>
                        </div>
                    </div>
                </form>

            </div>
            <div class="nav">
            <nav>
                <ul class="list-inline">
                    <li><a href="#">联系我们</a></li>
                    <li><a href="#">基地介绍</a></li>
                    <li><a href="#">设计体验</a></li>
                    <li><a href="${linkMap['brands']}">品牌</a></li>
                    <li><a href="${linkMap['designer']}">设计师</a></li>
                    <li><a href="${linkMap['designer']}">流行趋势</a></li>
                </ul>
            </nav>
            </div>
        </div>
    </div>
</header>
<!-- /.Header -->
</div>