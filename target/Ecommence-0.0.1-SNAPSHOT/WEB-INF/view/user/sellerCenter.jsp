<%--
  Created by IntelliJ IDEA.
  User: Charles
  Date: 2014/7/3
  Time: 15:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="main">
    <!--卖家信息-->
    <div class="seller-info">
        <div class="pic-txt">
            <div class="pic">
                <c:choose>
                    <c:when test="${not empty user.logo}">
                        <img src="${pageContext.request.contextPath}${user.logo}">
                    </c:when>
                    <c:otherwise>
                        <img src="${pageContext.request.contextPath}/resources/img/btn-th.png" />
                    </c:otherwise>
                </c:choose>
            </div>
            <p>登录名：${user.id}</p>
            <p>注册日期：${user.createTime}</p>
            <p>注册邮箱：${user.email}</p>
        </div>
    </div>
    <!--卖家信息 end-->
    <!--商品管理-->
    <div class="modebox seller-indent">
        <div class="hd"><h6>商品管理</h6><div class="seller-state"><a href="${pageContext.request.contextPath}/sellerCenter/items?type=0" title="" class="state">草稿(${itemSHSum})</a><em>|</em><a href="/sellerCenter/items?type=1" title="" class="state">出售中(${itemCSSum})</a><em>|</em><a href="/sellerCenter/items?type=3" title="" class="state">已下架(${itemYXJSum})</a></div><a href="/sellerCenter/items" title="更多" class="more">更多&raquo;</a></div>
        <div class="con">
            <div class="pic-list">
                <ul>
                    <c:choose>
                        <c:when test="${not empty ecGrid.ecList}">
                            <c:forEach var="item" items="${ecGrid.ecList}">
                                <li>
                                    <div class="pic"><a href="/${item.url}/${item.id}" title="">
                                        <c:choose>
                                            <c:when test="${not empty item.coverImage}">
                                                <img src="${pageContext.request.contextPath}${item.coverImage}"/>
                                            </c:when>
                                            <c:otherwise>
                                                <img src="${pageContext.request.contextPath}/resources/img/btn-th.png" />
                                            </c:otherwise>
                                        </c:choose>
                                    </a></div>
                                    <div class="seller-pic-txt">
                                        <div class="name text-over"><a href="/${item.url}/${item.id}" title="${item.name}">${item.name}</a></div>
                                        <div class="seller-price"><span class="price">&yen;${item.price}</span><span class="stay col-red">${item.state}</span></div>
                                        <div class="time"><fmt:formatDate value="${item.createdTime.time}" pattern="yyyy-MM-dd" type="date"></fmt:formatDate></div>
                                    </div>
                                </li>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <div style="height: 258px;">暂无商品</div>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </div>
        </div>
    </div>
    <!--商品管理 end-->
    <!--交易管理-->
    <div class="modebox seller-indent">
        <div class="hd"><h6>订单管理</h6><div class="seller-state"><a href="${pageContext.request.contextPath}/orderCenter/sellerOrderList?status=WAIT_GOODS_SEND" title="" class="state">待发货(${orderDFSum})</a><em>|</em><a href="/orderCenter/sellerOrderList?status=GOODS_DELIVER" title="" class="state">已发货(${orderYFSum})</a><em>|</em><a href="/orderCenter/sellerOrderList?status=GOODS_RECEIVE" title="" class="state">待评价(${orderDPSum})</a></div><a href="/orderCenter/sellerOrderList" title="更多" class="more">更多&raquo;</a></div>
        <div class="con">
            <div class="pic-list">
                <ul>
                    <c:choose>
                        <c:when test="${not empty ecGrid1.ecList}">
                            <c:forEach var="orderItem" items="${ecGrid1.ecList}">
                                <c:forEach var="orderLine" items="${orderItem.lines}" varStatus="st">
                                    <c:if test="${st.count == 1}">
                                        <li>
                                            <div class="pic"><a href="/orderCenter/sellerViewOrder/${orderItem.id}" title="">
                                                <c:choose>
                                                    <c:when test="${not empty orderLine.item.coverImage}">
                                                        <img src="${orderLine.item.coverImage}">
                                                    </c:when>
                                                    <c:otherwise>
                                                        <img src="${pageContext.request.contextPath}/resources/img/btn-th.png" />
                                                    </c:otherwise>
                                                </c:choose>
                                            </a></div>
                                            <div class="seller-pic-txt">
                                                <div class="name text-over"><a href="/orderCenter/sellerViewOrder/${orderItem.id}" title="${orderLine.item.name}">${orderLine.item.name}</a></div>
                                                <div class="seller-price"><span class="price">&yen;${orderLine.item.price}</span><span class="stay col-red">
                                            <c:if test="${orderItem.status =='WAIT_GOODS_SEND'}">
                                                待发货
                                            </c:if>
                                            <c:if test="${orderItem.status=='BUYER_CANCEL'}">
                                                买家取消
                                            </c:if>
                                            <c:if test="${orderItem.status=='SELLER_CANCEL'}">
                                                卖家取消
                                            </c:if>
                                            <c:if test="${orderItem.status=='GOODS_DELIVER'}">
                                                已发货
                                            </c:if>
                                            <c:if test="${orderItem.status=='GOODS_RECEIVE'}">
                                                已确认收货
                                            </c:if>
                                            <c:if test="${orderItem.status =='BUYER_APPRAISE'}">
                                                买家已评
                                            </c:if>
                                            <c:if test="${orderItem.status =='SELLER_APPRAISE'}">
                                                卖家已评价
                                            </c:if>
                                            <c:if test="${orderItem.status =='FINISH'}">
                                                交易结束
                                            </c:if>
                                        </span></div>
                                                <div class="time"><fmt:formatDate value="${orderItem.createdTime.time}" pattern="yyyy-MM-dd" type="date"></fmt:formatDate></div>
                                            </div>
                                        </li>
                                    </c:if>
                                </c:forEach>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <div style="height: 258px;">暂无订单</div>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </div>
        </div>
    </div>
    <!--交易管理 end-->
    <!--交易管理-->
    <div class="modebox seller-indent">
        <div class="hd"><h6>调样管理</h6><div class="seller-state"><a href="${pageContext.request.contextPath}/orderCenter/sellerSamples?state=0" title="" class="state">待发货(${sampleOrderDFSum})</a><em>|</em><a href="/orderCenter/sellerSamples?state=3" title="" class="state">已发货(${sampleOrderYFSum})</a></div><a href="/orderCenter/sellerSamples" title="更多" class="more">更多&raquo;</a></div>
        <div class="con">
            <div class="pic-list">
                <ul>
                    <c:choose>
                        <c:when test="${not empty ecGrid2.ecList}">
                            <c:forEach var="sampleOrder" items="${ecGrid2.ecList}">
                                <c:forEach var="sampleLine" items="${sampleOrder.sampleLines}" varStatus="st">
                                    <c:if test="${st.count == 1}">
                                        <li>
                                            <div class="pic"><a href="/${sampleLine.item.url}/${sampleLine.item.id}" title="">
                                                <c:choose>
                                                    <c:when test="${not empty sampleLine.item.coverImage}">
                                                        <img src="${sampleLine.item.coverImage}">
                                                    </c:when>
                                                    <c:otherwise>
                                                        <img src="${pageContext.request.contextPath}/resources/img/btn-th.png" />
                                                    </c:otherwise>
                                                </c:choose>
                                            </a></div>
                                            <div class="seller-pic-txt">
                                                <div class="name text-over"><a href="/${sampleLine.item.url}/${sampleLine.item.id}" title="${sampleLine.item.name}">${sampleLine.item.name}</a></div>
                                                <div class="seller-price"><span class="price">&yen;${sampleLine.item.price}</span><span class="stay col-red">
                                            <c:if test="${sampleOrder.state=='WAIT_GOODS_SEND'}">
                                                待发货
                                            </c:if>
                                            <c:if test="${sampleOrder.state=='BUYER_CANCEL'}">
                                                买家取消
                                            </c:if>
                                            <c:if test="${sampleOrder.state=='SELLER_CANCEL'}">
                                                卖家取消
                                            </c:if>
                                            <c:if test="${sampleOrder.state=='GOODS_DELIVER'}">
                                                已发货
                                            </c:if>
                                            <c:if test="${sampleOrder.state=='GOODS_RECEIVE'}">
                                                已确认收货
                                            </c:if>
                                        </span></div>
                                                <div class="time"><fmt:formatDate value="${sampleOrder.createdTime.time}" pattern="yyyy-MM-dd" type="date"></fmt:formatDate></div>
                                            </div>
                                        </li>
                                    </c:if>
                                </c:forEach>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <div style="height: 258px;">暂无调样</div>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </div>
        </div>
    </div>
    <!--交易管理 end-->

</div>
<!--右边 end-->