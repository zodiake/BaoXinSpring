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
        <title>我的平台</title>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
        <tiles:importAttribute name="styles"/>
        <c:forEach var="style" items="${styles}">
            <link type="text/css" rel="stylesheet" media="screen" href="${pageContext.request.contextPath}${style}"/>
        </c:forEach>
        <tiles:importAttribute name="script"/>
        <c:forEach var="item" items="${script}">
            <script src="${pageContext.request.contextPath}${item}"></script>
        </c:forEach>
    </head>
<body>
    <tiles:insertAttribute name="header"/>
     <tiles:insertAttribute name="main"/>
    <tiles:insertAttribute name="footer"/>
</body>
</html>
