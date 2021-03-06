<%--
  Created by IntelliJ IDEA.
  User: Charles
  Date: 2014/5/22
  Time: 10:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sbTag" uri="http://zodiake.com" %>
<div class="main">
<!-- /.Detail Bread -->
<div class="bread detail-bread">
    <span>您的位置：</span>
    <ul>
        <li><a href="${pageContext.request.contextPath}/">首页</a></li>
        <li><a href="${pageContext.request.contextPath}/sellerCenter">卖家中心</a></li>
        <li>收到的订单</li>
    </ul>
</div>
<!-- /.Detail Bread -->
<!-- /.Detail Table -->
    <div class="detail-table sellerOrderList">
        <select>
            <option value="ALL" <c:if test="${status=='ALL'}">selected="selected" </c:if>>全部</option>
            <option value="WAIT_GOODS_SEND" <c:if test="${status=='WAIT_GOODS_SEND'}">selected="selected" </c:if>>等待卖家发货</option>
            <option value="GOODS_DELIVER" <c:if test="${status=='GOODS_DELIVER'}">selected="selected" </c:if>>卖家已发货</option>
            <option value="GOODS_RECEIVE" <c:if test="${status=='GOODS_RECEIVE'}">selected="selected" </c:if>>已确认收货</option>
            <option value="BUYER_APPRAISE" <c:if test="${status=='BUYER_APPRAISE'}">selected="selected" </c:if>>买家已评价</option>
            <option value="SELLER_APPRAISE" <c:if test="${status=='SELLER_APPRAISE'}">selected="selected" </c:if>>卖家已评价</option>
            <option value="BUYER_CANCEL" <c:if test="${status=='BUYER_CANCEL'}">selected="selected" </c:if>>买家取消的订单</option>
            <option value="SELLER_CANCEL" <c:if test="${status=='SELLER_CANCEL'}">selected="selected" </c:if>>卖家取消的订单</option>
        </select><br/><br/>
        <table class="table table-border table-order">
            <thead>
            <tr>
                <th colspan="2">商品</th>
                <th width="10%">单价（元）</th>
                <th width="10%">数量</th>
                <th width="10%">总额（元）</th>
                <th width="15%">交易状态</th>
                <th width="15%">操作</th>
            </tr>
            </thead>
            <c:forEach var="orderItem" items="${grid.ecList}">
                <tbody>
                <tr class="order-title">
                    <th colspan="2" class="order-r">订单编号：${orderItem.orderNo}</th>
                    <th colspan="2" class="order-bo">${orderItem.orderAddress.receiverName}</th>
                    <th colspan="3" class="order-l">成交时间：<fmt:formatDate value="${orderItem.createdTime.time}" pattern="yyyy-MM-dd HH:mm" type="date"/></th>
                </tr>
                <c:forEach items="${orderItem.lines}" var="orderLine" varStatus="i">
                    <tr>
                        <td>
                            <span class="left">
                                <c:choose>
                                    <c:when test="${not empty orderLine.coverPath}">
                                        <img src="${pageContext.request.contextPath}${orderLine.item.coverImage}?size=100">
                                    </c:when>
                                    <c:otherwise>
                                        <c:if test="${not empty orderLine.coverPath}"><img src="${pageContext.request.contextPath}${orderLine.coverPath}?size=100"></c:if>
                                        <c:if test="${!not empty orderLine.coverPath}"><img src="${pageContext.request.contextPath}/resources/img/btn-th.png"></c:if>
                                    </c:otherwise>
                                </c:choose>
                            </span>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${orderLine.itemUrl == null || orderLine.itemUrl == ''}">
                                    <a href="${pageContext.request.contextPath}/${orderLine.item.url}/${orderLine.item.id}"> ${orderLine.item.name}</a>
                                </c:when>
                                <c:otherwise>
                                    <a href="${pageContext.request.contextPath}/${orderLine.itemUrl}"> ${orderLine.item.name}</a>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td><b class="price">${orderLine.price}</b></td>
                        <td>${orderLine.quantity}</td>
                        <c:if test="${i.index<1}">
                            <td rowspan="${orderItem.lines.size()}"><b class="price">${orderItem.summary}</b></td>
                            <td rowspan="${orderItem.lines.size()}" align="center">
                                <div class="orderState_${orderItem.id}">
                                    <c:choose>
                                        <c:when test="${orderItem.status == 'WAIT_GOODS_SEND'}">等待卖家发货</c:when>
                                        <c:when test="${orderItem.status == 'BUYER_CANCEL'}">买家已取消</c:when>
                                        <c:when test="${orderItem.status == 'SELLER_CANCEL'}">卖家已取消</c:when>
                                        <c:when test="${orderItem.status == 'GOODS_DELIVER'}">卖家已发货</c:when>
                                        <c:when test="${orderItem.status == 'GOODS_RECEIVE'}">已确认收货</c:when>
                                        <c:when test="${orderItem.status == 'BUYER_APPRAISE'}">买家已评价</c:when>
                                        <c:when test="${orderItem.status == 'SELLER_APPRAISE'}">卖家已评价</c:when>
                                        <c:when test="${orderItem.status == 'FINISH'}">双方已评价</c:when>
                                    </c:choose>
                                </div>
                                <br/><a href="${pageContext.request.contextPath}/orderCenter/sellerViewOrder/${orderItem.id}" target="_blank">订单详情</a>
                            </td>
                            <td rowspan="${orderItem.lines.size()}" align="center">
                                <span class="optSpan">
                                    <c:choose>
                                        <c:when test="${orderItem.status == 'WAIT_GOODS_SEND'}">
                                            <button type="button" class="button button-deep optStatus"  data-id="${orderItem.id}" data-status="GOODS_DELIVER">发货</button>
                                            <button type="button" class="button button-deep optStatus"  data-id="${orderItem.id}" data-status="SELLER_CANCEL">取消</button>
                                        </c:when>
                                        <c:when test="${orderItem.status == 'BUYER_APPRAISE'}"><a href="${pageContext.request.contextPath}/sellerCenter/${orderItem.id}/sellerComment" class="button button-deep aLabel">评价</a></c:when>
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

        <div class="text-right" id="myOrders">
            <c:if test="${grid.totalPages>1}">
                <%--<ul class="pagination">
                    <li><a data-url="${pageContext.request.contextPath}/orderCenter/sellerOrderList?page=${grid.currentPage-1<1?1:grid.currentPage-1}">上一页</a></li>
                    <c:forEach varStatus="st" begin="${grid.currentPage-3<1?1:grid.currentPage-3}" end="${grid.currentPage+3>grid.totalPages?grid.totalPages:grid.currentPage+3}">
                        <li <c:if test='${grid.currentPage == st.current}'> class="active"</c:if>>
                            <a data-url="${pageContext.request.contextPath}/orderCenter/sellerOrderList?page=${st.current}">${st.current}</a>
                        </li>
                    </c:forEach>
                    <li><a data-url="${pageContext.request.contextPath}/orderCenter/sellerOrderList?page=${grid.currentPage+1>grid.totalPages?grid.totalPages:grid.currentPage+1}">下一页</a></li>
                </ul>--%>
                <sbTag:page total="${grid.totalPages}" current="${grid.currentPage}" data-url="${pageContext.request.contextPath}/orderCenter/sellerOrderList"></sbTag:page>
            </c:if>
        </div>
    </div>
</div>
</div>