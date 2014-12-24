<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link href="${pageContext.request.contextPath}/resources/css/base.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/resources/css/begbuy.css" rel="stylesheet" type="text/css">
<!-- /.Container -->
<div class="container">
    <div class="indent-win-box">
        <div class="indent-win">
            <i class="win"></i><h6>评价提交成功，感谢您的评价！</h6>
        </div>
        <div class="indent-win-btn">
            <c:choose>
                <c:when test="${message == 'seller'}">
                    <a href="${pageContext.request.contextPath}/orderCenter/sellerOrderList" title="" class="btn-small btn-blue">我的订单列表</a>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/orderCenter/buyerOrderList" title="" class="btn-small btn-blue">我的订单列表</a>
                </c:otherwise>
            </c:choose>
            <a href="${pageContext.request.contextPath}/index" title="" class="btn-small btn-gray">返回首页</a>
        </div>
    </div>
</div>
