<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">

    <title>云端时尚--基于云计算的创意设计公共服务基地</title>
    <meta http-equiv="x-ua-compatible" content="ie=9" />
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
<tiles:insertAttribute name="header"></tiles:insertAttribute>
<div class="container">
    <!--供应商信息-->
    <div class="modebox supplier-info">
        <tiles:insertAttribute name="providerInfo" />
    </div>
    <!--供应商信息 end-->
</div>
<div class="container">
    <div class="conter">
        <div class="aside">
            <tiles:insertAttribute name="category" />
        </div>
        <div class="main">
            <tiles:insertAttribute name="main" />
        </div>
    </div>
    <div class="modebox new-products">
        <tiles:insertAttribute name="newItems" />
    </div>
</div>
<div class="footer">
    <tiles:insertAttribute name="footer"/>
 </div>
</body>
</html>

