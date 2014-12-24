<%--
  Created by IntelliJ IDEA.
  User: Charles
  Date: 2014/6/12
  Time: 10:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!--左侧-->
<div class="aside">
    <!--我的交易-->
    <div class="menu-nav">
        <div class="nav-list">
            <dl class="nav">
                <dt>交易管理<i class="down"></i></dt>
                <dd ><a href="#" title="" class="tit"><span class="dot">&#8226;</span>商品管理</a>
                    <div class="nav-second">
                        <sec:authorize access="hasAnyRole('ROLE_FABRIC')">
                            <a href="${pageContext.request.contextPath}/sellerCenter/fabricCreate" title="发布面料" class="tit">发布面料</a>
                        </sec:authorize>
                        <sec:authorize access="hasAnyRole('ROLE_MATERIAL')">
                            <a href="${pageContext.request.contextPath}/sellerCenter/materialCreate" title="发布辅料" class="tit">发布辅料</a>
                        </sec:authorize>
                        <a href="${pageContext.request.contextPath}/sellerCenter/items" title="已发布的产品" class="tit">已发布的产品</a>
                    </div>
                </dd>
                <dd><a href="${pageContext.request.contextPath}/orderCenter/sellerOrderList" title="收到的订单" class="tit"><span class="dot">&#8226;</span>收到的订单</a></dd>
                <dd><a href="${pageContext.request.contextPath}/orderCenter/sellerSamples" title="收到的调样单" class="tit"><span class="dot">&#8226;</span>收到的调样单</a></dd>
                <dd><a href="${pageContext.request.contextPath}/sellerCenter/comments" title="评价管理" class="tit"><span class="dot">&#8226;</span>评价管理</a></dd>
            </dl>
        </div>
    </div>
    <!--我的交易-->
</div>
<!--左侧 end-->
