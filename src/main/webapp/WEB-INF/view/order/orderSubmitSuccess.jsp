<%--
  Created by IntelliJ IDEA.
  User: Charles
  Date: 2014/5/22
  Time: 14:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link href="${pageContext.request.contextPath}/resources/css/base.css" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/resources/css/begbuy.css" rel="stylesheet" type="text/css">
<!-- /.Container -->
<div class="container">
    <div class="indent-win-box">
        <div class="indent-win">
            <i class="win"></i><h6>订单提交成功，感谢您的购买！</h6>
        </div>
        <div class="indent-win-btn"><a href="${pageContext.request.contextPath}/orderCenter/buyerOrderList" title="" class="btn-small btn-blue">我的订单列表</a><a href="${pageContext.request.contextPath}/index" title="" class="btn-small btn-gray">返回首页</a></div>
    </div>
</div>