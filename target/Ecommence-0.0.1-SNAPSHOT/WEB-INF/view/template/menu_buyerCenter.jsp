<%--
  Created by IntelliJ IDEA.
  User: Charles
  Date: 2014/6/12
  Time: 10:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!--左侧-->
<div class="aside">
    <!--我的交易-->
    <div class="menu-nav">
        <div class="nav-list">
            <dl class="nav">
                <dt>我的交易</dt>
                <dd><a href="${pageContext.request.contextPath}/shoppingCart/shopCart" title="我的购物车" class="tit"><span class="dot">&#8226;</span>我的购物车</a></dd>
                <dd ><a href="${pageContext.request.contextPath}/orderCenter/buyerOrderList" title="我的订单" class="tit"><span class="dot">&#8226;</span>我的订单</a></dd>
                <dd><a href="${pageContext.request.contextPath}/sampleBook/sample" title="我的调样册" class="tit"><span class="dot">&#8226;</span>我的调样册</a></dd>
                <dd><a href="${pageContext.request.contextPath}/orderCenter/buyerSamples" title="我的调样单" class="tit"><span class="dot">&#8226;</span>我的调样单</a></dd>
                <dd><a href="${pageContext.request.contextPath}/buyerCenter/demandOrders" title="我的求购单" class="tit"><span class="dot">&#8226;</span>我的求购单</a>
                    <div class="nav-second">
                        <a href="${pageContext.request.contextPath}/buyerCenter/demandOrder" title="发布求购" class="tit">发布求购</a>
                        <a href="${pageContext.request.contextPath}/buyerCenter/demandOrders" title="求购单列表" class="tit">求购单列表</a>
                    </div>
                </dd>
                <dd><a href="${pageContext.request.contextPath}/buyerCenter/comments/fromSeller" title="评价管理" class="tit"><span class="dot">&#8226;</span>评价管理</a></dd>
            </dl>
            <dl class="nav">
                <dt>我的关注</dt>
                <dd><a href="${pageContext.request.contextPath}/buyerCenter/favouritesList" title="关注的商品" class="tit"><span class="dot">&#8226;</span>关注的商品</a></dd>
                <dd><a href="${pageContext.request.contextPath}/buyerCenter/favourite/shops" title="关注的供应商" class="tit"><span class="dot">&#8226;</span>关注的供应商</a></dd>
                <dd><a href="${pageContext.request.contextPath}/buyerCenter/attentionDesigner" title="关注的设计师" class="tit"><span class="dot">&#8226;</span>关注的设计师</a></dd>
                <dd><a href="${pageContext.request.contextPath}/buyerCenter/attentionBrand" title="关注的品牌" class="tit"><span class="dot">&#8226;</span>关注的品牌</a></dd>
            </dl>
        </div>
    </div>
    <!--我的交易-->
</div>
<!--左侧 end-->
