<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sbTag" uri="http://zodiake.com" %>
<div class="main">
    <div class="bread detail-bread">
        <span>您的位置：</span>
        <ul>
            <li><a href="${pageContext.request.contextPath}/">首页</a></li>
            <li><a href="${pageContext.request.contextPath}/orderCenter/buyerOrderList">我的平台</a></li>
            <li>我的调样单</li>
        </ul>
    </div>
    <!-- /.Detail Bread -->

    <!-- /.Detail Table -->
    <div class="detail-table">
        <div class="drop">
            <div class="drop-menu">
                <c:choose>
                    <c:when test="${state==0}">待发货</c:when>
                    <c:when test="${state==1}">买家已取消</c:when>
                    <c:when test="${state==2}">卖家已取消</c:when>
                    <c:when test="${state==3}">卖家已发货</c:when>
                    <c:when test="${state==4}">已确认收货</c:when>
                    <c:otherwise>全部</c:otherwise>
                </c:choose>
                <i class="caret"></i></div>
            <div class="drop-content">
                <ul>
                    <c:if test="${state != 0}">
                        <li><a href="${pageContext.request.contextPath}/orderCenter/buyerSamples?state=0">待发货</a></li>
                    </c:if>
                    <c:if test="${state != 1}">
                        <li><a href="${pageContext.request.contextPath}/orderCenter/buyerSamples?state=1">买家已取消</a></li>
                    </c:if>
                    <c:if test="${state != 2}">
                        <li><a href="${pageContext.request.contextPath}/orderCenter/buyerSamples?state=2">卖家已取消</a></li>
                    </c:if>
                    <c:if test="${state != 3}">
                        <li><a href="${pageContext.request.contextPath}/orderCenter/buyerSamples?state=3">卖家已发货</a></li>
                    </c:if>
                    <c:if test="${state != 4}">
                        <li><a href="${pageContext.request.contextPath}/orderCenter/buyerSamples?state=4">已确认收货</a></li>
                    </c:if>
                    <c:if test="${state != 5}">
                        <li><a href="${pageContext.request.contextPath}/orderCenter/buyerSamples?state=5">全部</a></li>
                    </c:if>
                </ul>
            </div>

        </div>
        <table class="table table-border table-order">
            <thead>
            <tr>
                <th colspan="5">商品</th>
                <th width="15%">状态</th>
                <th width="15%">操作</th>
            </tr>
            </thead>
            <c:forEach var="sampleOrder" items="${grid.ecList}">
                <tbody>
                <tr class="order-title">
                    <th colspan="2" class="order-r">订单编号：${sampleOrder.orderNo}</th>
                    <th colspan="2" class="order-bo">${sampleOrder.sellerName}</th>
                    <th colspan="3" class="order-l">交货时间：<fmt:formatDate value="${sampleOrder.deliveryTime.time}" pattern="yyyy-MM-dd"></fmt:formatDate></th>
                </tr>
                <c:forEach var="sampleLine" items="${sampleOrder.sampleLines}" varStatus="i">
                <tr>
                    <td colspan="5">
                        <span class="left">
                            <a href="${pageContext.request.contextPath}/${sampleLine.item.url}/${sampleLine.item.id}">
                                <c:choose>
                                    <c:when test="${not empty sampleLine.item.coverImage}">
                                        <img src="${pageContext.request.contextPath}${sampleLine.item.coverImage}?size=100">
                                    </c:when>
                                    <c:otherwise>
                                        <img src="${pageContext.request.contextPath}/resources/img/btn-th.png">
                                    </c:otherwise>
                                </c:choose>
                            </a>
                        </span>
                        <span class="central"><a href="${pageContext.request.contextPath}/${sampleLine.item.url}/${sampleLine.item.id}">${sampleLine.item.name}</a></span><br/>
                    </td>
                    <c:if test="${i.index<1}">
                        <td rowspan="${sampleOrder.sampleLines.size()}" align="center">
                            <div class="orderState_${sampleOrder.id}">
                                <c:choose>
                                    <c:when test="${sampleOrder.state == 'WAIT_GOODS_SEND'}">待发货</c:when>
                                    <c:when test="${sampleOrder.state == 'BUYER_CANCEL'}">已取消</c:when>
                                    <c:when test="${sampleOrder.state == 'SELLER_CANCEL'}">卖家已取消</c:when>
                                    <c:when test="${sampleOrder.state == 'GOODS_DELIVER'}">已发货</c:when>
                                    <c:when test="${sampleOrder.state == 'GOODS_RECEIVE'}">已确认收货</c:when>
                                </c:choose>
                            </div>
                            <a class="block" href="${pageContext.request.contextPath}/orderCenter/buyerViewSampleOrder/${sampleOrder.id}" target="_blank">订单详情</a>
                        </td>
                        <td rowspan="${sampleOrder.sampleLines.size()}" align="center">
                            <span class="optSpan">
                                <c:choose>
                                    <c:when test="${sampleOrder.state == 'WAIT_GOODS_SEND'}"><a type="button" class="button button-deep optStatus"  data-id="${sampleOrder.id}" data-status="BUYER_CANCEL">取消</a></c:when>
                                    <c:when test="${sampleOrder.state == 'GOODS_DELIVER'}"><a type="button" class="button button-deep optStatus"  data-id="${sampleOrder.id}" data-status="GOODS_RECEIVE">确认收货</a></c:when>
                                </c:choose>
                            </span>
                        </td>
                    </c:if>
                </tr>
                </c:forEach>
                <tr class="sep-row"><td colspan="7" height="7"></td></tr>
                </tbody>
            </c:forEach>
        </table>
        <div class="text-right">
            <c:if test="${grid.totalPages>1}">
                <%--<ul class="pagination navigationTo">
                    <li><a href="${pageContext.request.contextPath}/orderCenter/buyerSamples?state=${state}&page=${grid.currentPage-1<1?1:grid.currentPage-1}">上一页</a></li>

                    <c:forEach varStatus="status" begin="${grid.currentPage-3<0?1:grid.currentPage-3}" end="${grid.currentPage+3>grid.totalPages?grid.totalPages:grid.currentPage+4}">
                        <li <c:if test='${grid.currentPage == status.current}'> class="active"</c:if>>
                            <a href="${pageContext.request.contextPath}/orderCenter/buyerSamples?state=${state}&page=${grid.currentPage}">${status.current}</a>
                        </li>
                    </c:forEach>

                    <li><a href="${pageContext.request.contextPath}/orderCenter/buyerSamples?state=${state}&page=${grid.currentPage+1>grid.totalPages?grid.totalPages:grid.currentPage+1}">下一页</a></li>
                </ul>--%>
                <sbTag:page total="${grid.totalPages}" current="${grid.currentPage}" href="${pageContext.request.contextPath}/orderCenter/buyerSamples?state=${state}"></sbTag:page>
            </c:if>
        </div>
    </div>
</div>