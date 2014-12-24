<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>云端时尚--基于云计算的创意设计公共服务基地</title>
    <meta http-equiv="x-ua-compatible" content="ie=8" />
    <link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/resources/img/icon-logo.png" />
    <link href="${pageContext.request.contextPath}/resources/css/base.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/resources/css/begbuy.css" rel="stylesheet" type="text/css">
    <script src="${pageContext.request.contextPath}/resources/js/jquery-1.11.1.min.js"></script>
    <tiles:importAttribute name="script"/>
    <c:forEach items="${script}" var="script">
        <script src="${pageContext.request.contextPath}${script}"></script>
    </c:forEach>
</head>

<body>
<!-- Header -->
<tiles:insertAttribute name="header"></tiles:insertAttribute>
<!-- Header end -->
<!--container-->
<div class="container personage-box">
    <!--左侧-->
    <div class="aside">
        <tiles:insertAttribute name="menu"></tiles:insertAttribute>
    </div>
    <!--左侧 end-->
    <!--右边-->
    <div class="main">
        <tiles:insertAttribute name="main"></tiles:insertAttribute>
    </div>
    <!--右边 end-->
</div>
<!--container end-->
<!--footer-->
<div class="footer">
    <tiles:insertAttribute name="footer"></tiles:insertAttribute>
</div>
<!--footer end-->
</body>
</html>
