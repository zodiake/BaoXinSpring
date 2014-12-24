<%--
  Created by IntelliJ IDEA.
  User: Charles
  Date: 2014/6/3
  Time: 11:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
    <head>
        <title>云端时尚--基于云计算的创意设计公共服务基地</title>
        <meta http-equiv="x-ua-compatible" content="ie=8" />
        <link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/resources/img/icon-logo.png" />
        <link type="text/css" rel="stylesheet" media="screen" href="${pageContext.request.contextPath}/resources/css/admin_menu.css"/>
        <link type="text/css" rel="stylesheet" media="screen" href="${pageContext.request.contextPath}/resources/css/action.css"/>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-1.11.1.min.js"></script>
        <!--[if lt IE 9]>
        <script src="${pageContext.request.contextPath}/resources/js/html5shiv.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/respond.min.js"></script>
        <![endif]-->
        <tiles:importAttribute name="script"/>
        <c:forEach var="item" items="${script}">
            <script src="${pageContext.request.contextPath}${item}"></script>
        </c:forEach>
    </head>
<body>
<tiles:insertAttribute name="header"></tiles:insertAttribute>
<div class="container">
    <div class="row-12">
        <div class="col-3">
            <tiles:insertAttribute name="menu"/>
        </div>
        <div class="col-9">
            <tiles:insertAttribute name="main"/>
        </div>
    </div>
</div>
<tiles:insertAttribute name="footer"/>
</body>
</html>