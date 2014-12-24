<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>云端时尚--基于云计算的创意设计公共服务基地</title>
    <meta http-equiv="x-ua-compatible" content="ie=9" />
    <link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/resources/img/icon-logo.png" />
    <link type="text/css" rel="stylesheet" media="screen" href="${pageContext.request.contextPath}/resources/css/action.css"/>
    <!--[if lt IE 9]>
    <script src="${pageContext.request.contextPath}/resources/js/html5shiv.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/respond.min.js"></script>
    <![endif]-->
    <script src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
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

