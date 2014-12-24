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
<!-- 弹窗开始 -->
<div class="fixed"></div>
<div class="list-box">
    <span class="close-x"><a href="javascript:void(0);" title="关闭" class="close-this">X</a></span>
    <i class="success-icon"></i>
    <div class="box-con"></div>
</div>
<!-- 弹窗结束 -->
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
<!--我的订单-->
<div class="modebox seller-indent">
    <div class="hd"><h6>我的订单</h6><div class="seller-state"><a href="/orderCenter/buyerOrderList?status=WAIT_GOODS_SEND" title="" class="state">待发货(${orderDFSum})</a><em>|</em><a href="/orderCenter/buyerOrderList?status=GOODS_DELIVER" title="" class="state">待收货(${OrderDSSum})</a><em>|</em><a href="/orderCenter/buyerOrderList?status=GOODS_RECEIVE" title="" class="state">待评价(${OrderDPSum})</a></div><a href="/orderCenter/buyerOrderList" title="更多" class="more">更多&raquo;</a></div>
    <div class="con">
        <div class="pic-list">
            <ul>
                <c:choose>
                    <c:when test="${not empty orderItems}">
                        <c:forEach var="orderItem" items="${orderItems}">
                            <c:forEach var="orderLine" items="${orderItem.lines}" varStatus="st">
                                <c:if test="${st.count==1}">
                                    <li>
                                        <div class="pic"><a href="/orderCenter/buyerViewOrder/${orderItem.id}" title="">
                                            <c:choose>
                                                <c:when test="${not empty orderLine.item.coverImage}">
                                                    <img src="${pageContext.request.contextPath}${orderLine.item.coverImage}">
                                                </c:when>
                                                <c:otherwise>
                                                    <img src="${pageContext.request.contextPath}/resources/img/btn-th.png" />
                                                </c:otherwise>
                                            </c:choose>
                                        </a></div>
                                        <div class="seller-pic-txt">
                                            <div class="name text-over"><a href="/orderCenter/buyerViewOrder/${orderItem.id}" title="${orderLine.item.name}">${orderLine.item.name}</a></div>
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
<!--我的订单 end-->
<!--我的调样单-->
<div class="modebox seller-indent">
    <div class="hd"><h6>我的调样单</h6><div class="seller-state"><a href="${pageContext.request.contextPath}/orderCenter/buyerSamples?state=0" title="" class="state">待发货(${sampleOrderDFSum})</a><em>|</em><a href="/orderCenter/buyerSamples?state=3" title="" class="state">待收货(${sampleOrderDSSum})</a><em>|</em><a href="/orderCenter/buyerSamples?state=2" title="" class="state">卖家已取消(${sampleOrderQXSum})</a></div><a href="/orderCenter/buyerSamples" title="更多" class="more">更多&raquo;</a></div>
    <div class="con">
        <div class="pic-list">
            <ul>
                <c:choose>
                    <c:when test="${not empty sampleOrders}">
                        <c:forEach var="sampleOrder" items="${sampleOrders}">
                            <c:forEach varStatus="st" var="sampleLine" items="${sampleOrder.sampleLines}">
                                <c:if test="${st.count == 1}">
                                    <li>
                                        <div class="pic"><a href="${pageContext.request.contextPath}${sampleLine.item.url}" title="">
                                            <c:choose>
                                                <c:when test="${not empty sampleLine.item.coverImage}">
                                                    <img src="${pageContext.request.contextPath}${sampleLine.item.coverImage}">
                                                </c:when>
                                                <c:otherwise>
                                                    <img src="${pageContext.request.contextPath}/resources/img/btn-th.png" />
                                                </c:otherwise>
                                            </c:choose>
                                        </a></div>
                                        <div class="seller-pic-txt">
                                            <div class="name text-over"><a href="${pageContext.request.contextPath}${sampleLine.item.url}" title="${sampleLine.item.name}">${sampleLine.item.name}</a></div>
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
<!--我的调样单 end-->
<!--我的求购单-->
<div class="modebox seller-indent">
    <div class="hd"><h6>我的求购单</h6><a href="/buyerCenter/demandOrders" title="更多" class="more">更多&raquo;</a></div>
    <div class="con">
        <div class="pic-list">
            <ul>
                <c:choose>
                    <c:when test="${not empty demandOrders}">
                        <c:forEach var="demandOrder" items="${demandOrders}">
                            <li>
                                <div class="pic"><a href="/demandOrder/${demandOrder.id}" title="">
                                    <c:choose>
                                        <c:when test="${not empty demandOrder.coverImage}">
                                            <img src="${demandOrder.coverImage}">
                                        </c:when>
                                        <c:otherwise>
                                            <img src="${pageContext.request.contextPath}/resources/img/btn-th.png" />
                                        </c:otherwise>
                                    </c:choose>
                                </a></div>
                                <div class="seller-pic-txt">
                                    <div class="name text-over"><a href="/demandOrder/${demandOrder.id}" title="${demandOrder.title}">${demandOrder.title}</a></div>
                                        <%--<div class="seller-price"><span class="price">&yen;4003</span><span class="stay col-red">待发货</span></div>--%>
                                    <div class="time"><fmt:formatDate value="${demandOrder.createdTime.time}" type="date" pattern="yyyy-MM-dd"></fmt:formatDate></div>
                                </div>
                            </li>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <div style="height: 258px;">暂无求购</div>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>
    </div>
</div>
<!--我的求购单 end-->
<!--关注的商品-->
<div class="attention-box">
    <!--左边-->
    <div class="attention-left">
        <div class="modebox attention-supply">
            <div class="hd"><h6>关注的供应商（${favouriteShopSum}）</h6><a href="${pageContext.request.contextPath}/buyerCenter/favourite/shops" title="更多" class="more">更多&raquo;</a></div>
            <div class="con">
                <div class="pic-list">
                    <ul>
                        <c:choose>
                            <c:when test="${not empty favouriteShopses}">
                                <c:forEach var="favoriteShop" items="${favouriteShopses}">
                                    <li>
                                        <div class="pic"><a href="/shopCenter/${favoriteShop.shop.user.id}/items" title="">
                                            <c:choose>
                                                <c:when test="${not empty favoriteShop.shop.user.logo}">
                                                    <img src="${favoriteShop.shop.user.logo}">
                                                </c:when>
                                                <c:otherwise>
                                                    <img src="${pageContext.request.contextPath}/resources/img/btn-th.png" />
                                                </c:otherwise>
                                            </c:choose>
                                        </a></div>
                                        <div class="seller-pic-txt">
                                            <div class="name text-over"><a href="/shopCenter/${favoriteShop.shop.user.id}/items" title="${favoriteShop.shop.user.companyName}">${favoriteShop.shop.user.companyName}</a></div>
                                            <div class="tips">
                                                <c:choose>
                                                    <c:when test="${not empty favoriteShop.shop.user.mainIndustry}">
                                                        ${favoriteShop.shop.user.mainIndustry}
                                                    </c:when>
                                                    <c:otherwise>
                                                        <p><br/></p>
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>
                                            <div class="attention-fun"><a title="删除关注的供应商" class="del-icon del-shop" data-id="${favoriteShop.shop.id}"></a><a href="/shopCenter/${favoriteShop.shop.user.id}/items" title="供应商主页" class="home-icon"></a></div>
                                        </div>
                                    </li>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <div style="height: 258px;">暂无关注</div>
                            </c:otherwise>
                        </c:choose>
                    </ul>
                </div>
            </div>
        </div>
    </div>
    <!--左边 end-->
    <!--右边-->
    <div class="attention-right">
        <div class="modebox attention-supply">
            <div class="hd"><h6>关注的商品（${favouriteItemSum}）</h6><a href="${pageContext.request.contextPath}/buyerCenter/favouritesList" title="更多" class="more">更多&raquo;</a></div>
            <div class="con">
                <div class="pic-list">
                    <ul>
                        <c:choose>
                            <c:when test="${not empty favouriteItemses}">
                                <c:forEach var="item" items="${favouriteItemses}" varStatus="st">
                                    <c:if test="${st.count < 3}">
                                        <li>
                                            <div class="pic"><a href="${item.item.url}" title="">
                                                <c:choose>
                                                    <c:when test="${not empty item.item.coverImage}">
                                                        <img src="${item.item.coverImage}">
                                                    </c:when>
                                                    <c:otherwise>
                                                        <img src="${pageContext.request.contextPath}/resources/img/btn-th.png" />
                                                    </c:otherwise>
                                                </c:choose>
                                            </a></div>
                                            <div class="seller-pic-txt">
                                                <div class="name text-over"><a href="${item.item.url}" title="${item.item.name}">${item.item.name}</a></div>
                                                <div class="tips"><span class="price">
                                                    <c:choose>
                                                        <c:when test="${item.item.price > 0}">
                                                            &yen;${item.item.price}
                                                        </c:when>
                                                        <c:otherwise>
                                                            面议
                                                        </c:otherwise>
                                                    </c:choose>
                                                </span><span>(${item.item.state})</span></div>
                                                <div class="attention-fun"><a title="删除关注的商品" class="del-icon deleteFavouriteItem" data-id="${item.item.id}"></a><a href="/shopCenter/${item.item.createdBy.id}/items" title="供应商主页" class="home-icon" ></a>
                                                    <c:choose>
                                                        <c:when test="${item.item.price > 0}">
                                                            <a title="加入购物车" class="shopping-car-icon addCart" data-id="${item.item.id}"></a>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <a title="面议价格不能加入购物车" class="shopping-car-icon"></a>
                                                        </c:otherwise>
                                                    </c:choose>


                                                    <a title="加入调样册" class="album-icon addBook" data-id="${item.item.id}"></a>
                                                </div>
                                            </div>
                                        </li>
                                    </c:if>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <div style="height: 258px;">暂无关注</div>
                            </c:otherwise>
                        </c:choose>
                    </ul>
                </div>
            </div>
        </div>
    </div>
    <!--右边 end-->
</div>
<!--关注的商品 end-->
</div>
