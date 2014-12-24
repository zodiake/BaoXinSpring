<%--
  Created by IntelliJ IDEA.
  User: zodiake
  Date: 2014/6/23
  Time: 16:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>无标题文档</title>
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
<div class="header">
    <tiles:insertAttribute name="header"></tiles:insertAttribute>
</div>
<!-- Header end -->
<!--container-->
<div class="container">
    <tiles:insertAttribute name="content"></tiles:insertAttribute>
</div>
<!--container end-->
<!--footer-->
<div class="footer">
    <tiles:insertAttribute name="footer"></tiles:insertAttribute>
</div>
<!--footer end-->

