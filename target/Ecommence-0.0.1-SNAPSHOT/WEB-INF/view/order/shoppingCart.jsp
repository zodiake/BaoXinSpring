<%--
  Created by IntelliJ IDEA.
  User: Charles
  Date: 2014/5/22
  Time: 14:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- /.Container -->
<div class="container">
    <div class="fixed"></div>
    <div class="list-box">
        <span class="close-x"><a href="javascript:void(0);" title="关闭" class="close-this">X</a></span>
        <i class="success-icon"></i>
        <div class="box-con"></div>
    </div>
    <!-- /.Cart Step -->
    <div class="indent-step">

    </div>
    <!-- /.Cart Step -->
    <!-- /.Order Detail -->
    <div class="order-detail">
        <c:choose>
            <c:when test="${cartLineList == null || cartLineList.size == 0}">
                    <div class="central">
                        <img src="${pageContext.request.contextPath}/resources/img/cart.png">
                        购物车中没有商品!
                        <a class="button button-deep" href="${pageContext.request.contextPath}/">去购物</a>
                    </div>
            </c:when>
            <c:otherwise>
                <table class="table table-border table-order">
                    <thead>
                        <tr>
                            <th colspan="2">商品</th>
                            <th width="10%">单价（元）</th>
                            <th width="15%">数量</th>
                            <th width="10%">总额（元）</th>
                            <th width="15%">操作</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="cartLine" items="${cartLineList}">
                            <tr class="order-title data_${cartLine.key}">
                                <th align="left" colspan="6">供应商:${cartLine.value[0].supplierName}</th>
                            </tr>
                            <c:set var="sumprice" value="0"></c:set>
                            <c:set var="sumquantity" value="0"></c:set>
                            <c:forEach items="${cartLine.value}" var="cartItem">
                                <c:set var="sumprice" value="${sumprice+cartItem.summary}"></c:set>
                                <c:set var="sumquantity" value="${sumquantity+cartItem.quantity}"></c:set>
                                <tr class="dataTable_${cartItem.itemId}">
                                    <td colspan="2">
                                        <div class="central">
                                            <a href="${pageContext.request.contextPath}/${cartItem.itemType}/${cartItem.itemId}">
                                            <c:if test="${not empty cartItem.imgPath}">
                                                <img class="thumb" src="${pageContext.request.contextPath}${cartItem.imgPath}?size=100">
                                            </c:if>
                                            <c:if test="${!not empty cartItem.imgPath}">
                                                <img class="thumb" src="${pageContext.request.contextPath}/resources/img/btn-th.png" width="100" height="100">
                                            </c:if>
                                            </a>
                                            <a href="${pageContext.request.contextPath}/${cartItem.itemType}/${cartItem.itemId}">${cartItem.title}</a>
                                        </div>
                                    </td>
                                    <td><span id="${cartItem.itemId}_price">${cartItem.price}</span></td>
                                    <td>
                                        <span class="button-group button-more-less">
                                            <button class="button button-default reduce" data-id="${cartItem.itemId}" seller-id="${cartItem.supplierId}">-</button>
                                            <input type="text" class="quantity" id="${cartItem.itemId}_quantity"  data-id="${cartItem.itemId}" seller-id="${cartItem.supplierId}" value="${cartItem.quantity}">
                                            <button class="button button-default increase" data-id="${cartItem.itemId}" seller-id="${cartItem.supplierId}">+</button>
                                        </span>
                                    </td>
                                    <td><span id="${cartItem.itemId}_summary">${cartItem.summary}</span></td>
                                    <td>
                                        <a href="#" class="button button-default addFavourite" data-id="${cartItem.itemId}">添加关注</a>
                                        <a href="#" class="button button-default delete" data-id="${cartItem.itemId}" seller-id="${cartItem.supplierId}">删除</a>
                                    </td>
                                </tr>
                            </c:forEach>
                            <tr align="center">
                                <td colspan="3"></td>
                                <td align="center">商品数量<br/><span class="sumquantity_${cartLine.key}">${sumquantity}</span></td>
                                <td align="center">商品金额<br/><b class="price orange"><span class="sumprice_${cartLine.key}">${sumprice}</span></b></td>
                                <td>
                                    <a type="button" class="button button-deep button-big" href="${pageContext.request.contextPath}/orderCenter/checkOut/${cartLine.key}">生成订单</a></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                    <tfoot>
                    <!--tr>
                        <td align="right" colspan="6"><a type="button" class="button button-deep button-big" href="${pageContext.request.contextPath}/orderCenter/checkOut">生成订单</a></td>
                    </tr-->
                    </tfoot>
                </table>
            </c:otherwise>
        </c:choose>
    </div>
    <!-- /.Order Detail -->
</div>
<!-- /.Container -->