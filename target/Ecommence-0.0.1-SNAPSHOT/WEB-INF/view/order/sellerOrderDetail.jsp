<%--
  Created by IntelliJ IDEA.
  User: Charles
  Date: 2014/5/22
  Time: 10:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!-- /.Container -->
<div class="container">
    <!-- /.Order Detail -->
    <div class="order-detail">
        <table class="table order-table">
            <thead>
            <tr>
                <th>拍下宝贝</th>
                <th>卖家发货</th>
                <th>确认收货</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td><fmt:formatDate value="${orderItem.createdTime.time}" pattern="yyyy-MM-dd HH:mm" type="date"/></td>
                <td><fmt:formatDate value="${orderItem.deliverTime.time}" pattern="yyyy-MM-dd HH:mm" type="date"/></td>
                <td><fmt:formatDate value="${orderItem.receiveTime.time}" pattern="yyyy-MM-dd HH:mm" type="date"/></td>
            </tr>
            </tbody>
        </table>

        <dl class="detail-recommend box">
            <dt>订单详情</dt>
            <dd>
                <address>
                    <div>
                        <label>收货地址：</label>
                        <span>${orderItem.orderAddress.state} ${orderItem.orderAddress.city} ${orderItem.orderAddress.street}</span>
                    </div>
                    <div>
                        <label>发票抬头：</label>
                        <span>
                        <c:choose>
                            <c:when test="${orderItem.needInvoice==1}">${orderItem.orderAddress.title}</c:when>
                            <c:otherwise>无</c:otherwise>
                        </c:choose>
                        </span>
                    </div>
                    <div>
                        <label>是否需要发票：</label>
                        <span>
                            <c:choose>
                                <c:when test="${orderItem.needInvoice==1}">是</c:when>
                                <c:when test="${orderItem.needInvoice==0}">否</c:when>
                            </c:choose>
                        </span>
                    </div>
                </address>
                <hr />
                <ul>
                    <li>
                        <label>买家信息：</label>
                        <div class="row-10">
                            <div class="col-1"></div>
                            <div class="col-3">收货人：${orderItem.orderAddress.receiverName}</div>
                            <div class="col-3">联系电话：${orderItem.orderAddress.mobile}<c:if test="${orderItem.orderAddress.phone != '' && orderItem.orderAddress.phone != null}">&nbsp;&nbsp;&nbsp;${orderItem.orderAddress.zipPhone}-${orderItem.orderAddress.phone}-${orderItem.orderAddress.childPhone}</c:if></div>
                            <div class="col-3">地址：${orderItem.orderAddress.state}${orderItem.orderAddress.city}${orderItem.orderAddress.street}</div>
                        </div>
                    </li>
                    <li>
                        <label>订单信息：</label>
                        <div class="row-10">
                            <div class="col-1"></div>
                            <div class="col-3">订单编号：${orderItem.orderNo}</div>
                            <div class="col-3">成交时间：<fmt:formatDate value="${orderItem.deliverTime.time}" pattern="yyyy-MM-dd HH:mm" type="date"/></div>
                            <div class="col-3">确认时间：<fmt:formatDate value="${orderItem.receiveTime.time}" pattern="yyyy-MM-dd HH:mm" type="date"/></div>
                        </div>
                    </li>
                    <li>
                        <label>商品详情：</label>
                        <div class="order-description">
                            <table class="table table-border">
                                <thead>
                                <tr>
                                    <th>商品名称</th>
                                    <th>数量</th>
                                    <th>单价（元）</th>
                                    <th>总价（元）</th>
                                </tr>
                                <c:forEach items="${orderItem.lines}" var="orderLine">
                                    <tr>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/${orderLine.item.url}/${orderLine.item.id}">
                                                <c:choose>
                                                    <c:when test="${not empty orderLine.item.coverImage}"><img src="${pageContext.request.contextPath}${orderLine.item.coverImage}"></c:when>
                                                    <c:otherwise><img src="${pageContext.request.contextPath}/resources/img/btn-th.png" /></c:otherwise>
                                                </c:choose>
                                            </a>
                                            <span>
                                                <a href="${pageContext.request.contextPath}/${orderLine.item.url}/${orderLine.item.id}">${orderLine.item.name}</a>
                                            </span>
                                        </td>
                                        <td>${orderLine.quantity}</td>
                                        <td>${orderLine.price}</td>
                                        <td>${orderLine.sum}</td>
                                    </tr>
                                </c:forEach>
                                </thead>
                                <tfoot>
                                <tr>
                                    <td align="right" colspan="4">订单总价：<b class="price orange font-20">${orderItem.summary}</b></td>
                                </tr>
                                </tfoot>
                            </table>
                        </div>
                    </li>
                </ul>
            </dd>
        </dl>

    </div>
    <!-- /.Order Detail -->
    <span class="optSpan right">
        <c:choose>
            <c:when test="${orderItem.status == 'WAIT_GOODS_SEND'}">
                <button type="button" class="button button-deep optStatus"  data-id="${orderItem.id}" data-status="GOODS_DELIVER">发货</button>
                <button type="button" class="button button-deep optStatus"  data-id="${orderItem.id}" data-status="SELLER_CANCEL">取消</button>
            </c:when>
        </c:choose>
    </span>
</div>
<!-- /.Container -->