<%--
  Created by IntelliJ IDEA.
  User: zodiake
  Date: 2014/6/9
  Time: 14:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <title>云端时尚--基于云计算的创意设计公共服务基地</title>
    <meta http-equiv="x-ua-compatible" content="ie=9" />
    <link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/resources/img/icon-logo.png" />
    <link type="text/css" rel="stylesheet" href="http://sentsin.com/lily/lib/layer/skin/layer.css" />
    <link type="text/css" rel="stylesheet" media="screen" href="${pageContext.request.contextPath}/resources/css/jqzoom.css" />
    <link type="text/css" rel="stylesheet" media="screen" href="${pageContext.request.contextPath}/resources/css/action.css"/>
    <!--[if lt IE 9]>
    <script src="${pageContext.request.contextPath}/resources/js/html5shiv.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/respond.min.js"></script>
    <![endif]-->
    <script src='${pageContext.request.contextPath}/resources/js/jquery-1.11.1.min.js'></script>
    <link type="text/css" rel="stylesheet" href="http://sentsin.com/lily/lib/layer/skin/layer.css" id="skinlayercss">
    <script src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/jquery.jqzoom-2.2.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/layer.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/fdj.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/search.js"></script>
    <tiles:importAttribute name="script"/>
    <c:forEach items="${script}" var="script">
        <script src="${pageContext.request.contextPath}${script}"></script>
    </c:forEach>
</head>

<body>
<tiles:insertAttribute name="header"></tiles:insertAttribute>
<div class="container business-box">
    <%--<div class="row-12">
        <div class="col-3">
            <div class="sidebar">
                <tiles:insertAttribute name="ProviderInfo"/>
                <tiles:insertAttribute name="category"/>
            </div>
        </div>
        <div class="col-9">
            <tiles:insertAttribute name="detail"/>
            <tiles:insertAttribute name="main"/>
        </div>
    </div>
    <tiles:insertAttribute name="newItem"/>--%>
    <div class="clearfix">
        <div class="aside">
            <div class="sidebar">
                <tiles:insertAttribute name="ProviderInfo"></tiles:insertAttribute>
                <tiles:insertAttribute name="category"></tiles:insertAttribute>
            </div>
        </div>
        <div class="main">
            <tiles:insertAttribute name="detail"></tiles:insertAttribute>
            <dl class="detail-main">
                <tiles:insertAttribute name="main"></tiles:insertAttribute>
            </dl>
        </div>
    </div>
    <tiles:insertAttribute name="newItem"></tiles:insertAttribute>
</div>
<footer>
<tiles:insertAttribute name="footer"/>
</footer>
</body>
</html>