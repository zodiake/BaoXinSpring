<%--
  Created by IntelliJ IDEA.
  User: zodiake
  Date: 2014/5/15
  Time: 13:13
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
        <link type="text/css" rel="stylesheet" media="screen" href="${pageContext.request.contextPath}/resources/css/action.css"/>
        <link type="text/css" rel="stylesheet" media="screen" href="${pageContext.request.contextPath}/resources/css/base.css"/>
        <link type="text/css" rel="stylesheet" media="screen" href="${pageContext.request.contextPath}/resources/css/begbuy.css"/>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
        <tiles:importAttribute name="script"/>
        <c:forEach var="item" items="${script}">
            <script src="${pageContext.request.contextPath}${item}"></script>
        </c:forEach>
    </head>
<body>
    <tiles:insertAttribute name="header"></tiles:insertAttribute>
    <div class="container personage-box">
        <div class="aside">
                <tiles:insertAttribute name="menu"/>
        </div>
        <div class="main">
            <tiles:insertAttribute name="main"/>
        </div>
    </div>
    <div class="footer">
    <tiles:insertAttribute name="footer"/>
        </div>
</body>
</html>
