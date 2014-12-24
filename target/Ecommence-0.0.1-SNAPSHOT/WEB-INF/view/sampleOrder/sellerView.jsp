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
        <dl class="detail-recommend box">
            <dt>调样详情</dt>
            <dd>
                <address>
                    <label>收货地址：</label>
                    <span>${sampleOrder.address.state}${sampleOrder.address.city}${sampleOrder.address.street}</span>
                </address>
                <hr />
                <ul>
                    <li>
                        <label>买家信息：</label>
                        <div class="row-10">
                            <div class="col-1"></div>
                            <div class="col-3">收货人：${sampleOrder.address.receiverName}</div>
                            <div class="col-3">联系电话：${sampleOrder.address.mobile}<c:if test="${not empty sampleOrder.address.phone}">&nbsp;&nbsp;&nbsp;${sampleOrder.address.zipPhone}-${sampleOrder.address.phone}</c:if><c:if test="${not empty sampleOrder.address.childPhone}">-${sampleOrder.address.childPhone}</c:if></div>
                            <div class="col-3">地址：${sampleOrder.address.state}${sampleOrder.address.city}${sampleOrder.address.street}</div>
                        </div>
                    </li>
                    <li>
                        <label>订单信息：</label>
                        <div class="row-10">
                            <div class="col-1"></div>
                            <div class="col-3">订单编号：${sampleOrder.orderNo}</div>
                            <div class="col-3">提交时间：<fmt:formatDate value="${sampleOrder.createdTime.time}" pattern="yyyy-MM-dd" type="date"/></div>
                            <div class="col-3">交货时间：<fmt:formatDate value="${sampleOrder.deliveryTime.time}" pattern="yyyy-MM-dd" type="date"/></div>
                        </div>
                    </li>
                    <li>
                        <label>备注：</label>
                        <div class="row-10">
                            <div class="col-1"></div>
                            <div class="col-9">${sampleOrder.remark}</div>
                        </div>
                    </li>
                    <li>
                        <label>商品详情：</label>
                        <div class="order-description">
                            <table class="table table-border">
                                <thead>
                                <tr>
                                    <th>调样商品</th>
                                </tr>
                                <c:forEach items="${sampleOrder.sampleLines}" var="sampleLine">
                                    <tr>
                                        <td>
                                            <span class="left">
                                                <a href="${pageContext.request.contextPath}/${sampleLine.item.url}/${sampleLine.item.id}">
                                                    <img src="${pageContext.request.contextPath}${sampleLine.item.coverImage}?size=100">
                                                </a>
                                                <span>
                                                    <a href="${pageContext.request.contextPath}/${sampleLine.item.url}/${sampleLine.item.id}">${sampleLine.item.name}</a>
                                                </span>
                                            </span>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </thead>
                                <tfoot>
                                </tfoot>
                            </table>
                        </div>
                    </li>
                </ul>
            </dd>
        </dl>

    </div>
    <!-- /.Order Detail -->

</div>
<!-- /.Container -->