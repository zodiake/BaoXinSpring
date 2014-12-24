<%--
  Created by IntelliJ IDEA.
  User: Charles
  Date: 2014/5/22
  Time: 10:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:choose>
    <c:when test="${result == 'offSale'}">
        <script type="text/javascript">
            $(document).ready(function(){
                var hostUrl = "http://" + window.location.host;
                var confirmResult = confirm("商品已停止出售，请选择其他商品!");
                if(confirmResult == true){
                    window.location.href = hostUrl;
                }else{
                    window.location.href = hostUrl;
                }
            });
        </script>
    </c:when>
    <c:when test="${result == 'wrongBuyer'}">
        <script type="text/javascript">
            $(document).ready(function(){
                var hostUrl = "http://" + window.location.host;
                var confirmResult = confirm("您不能购买自己的商品!");
                if(confirmResult == true){
                    window.location.href = hostUrl;
                }else{
                    window.location.href = hostUrl;
                }
            });
        </script>
    </c:when>
    <c:when test="${result == 'negotiable'}">
        <script type="text/javascript">
            $(document).ready(function(){
                var confirmResult = confirm("当前订购商品需要与供应商面议，请联系供应商!");
                if(confirmResult == true){
                    window.history.back();
                }else{
                    window.history.back();
                }
            });
        </script>
    </c:when>
    <c:when test="${result == 'notForSale'}">
        <script type="text/javascript">
            $(document).ready(function(){
                var confirmResult = confirm("订购量不满足起订量!");
                if(confirmResult == true){
                    window.history.back();
                }else{
                    window.history.back();
                }
            });
        </script>
    </c:when>
    <c:otherwise>
        <!-- /.Container -->
        <div class="container">
            <div class="fixed"></div>
            <div class="new-address">
                <h2>使用新地址</h2>
                <ul>
                    <li>
                        <label><i class="red">*</i>收货人：</label>
                        <input id="receiverName" name="receiverName" type="text" value="" class="addInfoVali"><span class="msg-error Hide"><i class="error-icon"></i>2-20个字符，一个汉字为三个字符！</span>
                    </li>
                    <li>
                        <label><i class="red">*</i>所在地区：</label>
                        <select id="state" name="state" class="addInfoVali">
                            <option></option>
                        </select>
                        <select id="city" name="city" class="addInfoVali">
                            <option></option>
                        </select>
                        <span class="msg-error Hide"><i class="error-icon"></i>请正确选择省市地区！</span>
                    </li>
                    <li>
                        <label><i class="red">*</i>详细地址：</label>
                        <input id="street" name="street" type="text" value="" class="detail-add addInfoVali"><span class="msg-error Hide"><i class="error-icon"></i>请填写详细地址！</span>
                    </li>
                    <li>
                        <label><i class="red">*</i>邮编：</label>
                        <input id="zipCode" name="zipCode" type="text" value="" class="addInfoVali"><span class="msg-error Hide"><i class="error-icon"></i>请填写邮编！</span>
                    </li>
                    <li>
                        <label>手机号：</label>
                        <input id="mobile" name="mobile" type="text" value="" class="addInfoVali">
                        或固定电话：<input id="zipPhone" name="zipPhone" type="text" value="" class="code" class="addInfoVali"> -
                        <input id="phone" name="phone" type="text" value="" class="tel" class="addInfoVali"> -
                        <input id="childPhone" name="childPhone" type="text" value="" class="wei addInfoVali">
                    </li>
                    <li>
                        <label></label><span class="gray">*为保证商品准确送达，请保证手机号和固定电话至少填写一项。</span>
                    </li>
                    <li>
                        <label>设为常用地址：</label>
                        <span class="defaultA"><input  name="defaultAdd" id="defaultAdd" type="checkbox"/>设置为默认收货地址</span>
                    </li>

                    <li class="btn_li">
                        <input type="hidden" id="newAddressId" name="newAddressId" value="">
                        <input type="button" class="button button-deep submitAddress" value="保存"/>
                        <input type="button" class="button default cancelAddress" value="取消"/>
                    </li>
                </ul>
            </div>
            <!-- /.Cart Step -->
            <div class="indent-step indent-step-two">

            </div>
            <!-- /.Cart Step -->
            <dl class="box address-box">
                <dt>收货信息</dt>
                <!--地址-->
                <dd>
                    <div class="address-list">
                        <ul>
                            <c:forEach items="${addresses}" var="address">
                                <li class="addressBlock <c:if test="${defaultAddress.id == address.id}">active</c:if>" data-id="${address.id}">
                                    <p><b><span class="receiveName_${address.id}">${address.receiverName}</span></b>收</p>
                                    <p class="address-show"><span class="addressDetail_${address.id}">${address.state}${address.city}</span></p>
                                    <p class="address-show"><span class="addressDetail_${address.id}">${address.street}</span></p>
                                    <p>${address.mobile}</p>
                                    <!--hover编辑-->
                                    <div class="opera-nav"><span class="edit-bt editAddress" data-id="${address.id}">编缉</span><c:if test="${defaultAddress.id != address.id}"><span class="edit-bt set-defaulte-addr setDefaultAddress" data-id="${address.id}">设为常用地址</span></c:if><span class="close deleteAdd" data-id="${address.id}"></span></div>
                                    <span class="tick"></span>
                                    <!--hover编辑 end-->
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                    <div class="address-btn"><a href="#" title="" class="button button-default newAddress">使用新地址</a></div>
                    <!--地址 end-->
                    <div class="address-btn box cart-receiving">
                        <label>发票抬头：</label>
                        <span class="showOrderTitle">无</span>
                        <div class="drop inline drop-invoice">
                            <span class="drop-menu drop-invoice-menu"><button class="button button-default button-small">修改</button></span>
                            <div class="drop-content drop-down-left  drop-invoice-left ">
                                <div>
                                    <input type="checkbox" class="checkbox needInvoice">
                                    <label>发票开具（如您需要发票，请点击选中）</label>
                                </div>
                                <div>
                                    <label>发票抬头：</label>
                                    <input type="text" class="orderTitle">
                                </div>
                                <div class="text-center">
                                    <a type="button" class="button button-default changeTitle">提交发票抬头</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </dd>
            </dl>
            <!-- /.Cart Order-Info -->
            <dl class="box order-info">
                <dt>订单</dt>
                <dd>
                    <table class="table table-border table-order">
                        <thead>
                        <tr>
                            <th>商品名称</th>
                            <th width="20%">所在企业</th>
                            <th width="10%">单价</th>
                            <th width="10%">数量</th>
                            <th width="10%">小计</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${cartLineList}" var="cartLine">
                            <c:if test="${cartLine.price >0}">
                                <tr class="dataTable_${cartLine.itemId}">
                                    <td><a href="${pageContext.request.contextPath}${cartLine.itemType}">${cartLine.title}</a></td>
                                    <td>${cartLine.supplierName}</td>
                                    <td><bdo class="price">${cartLine.price}</bdo></td>
                                    <td>${cartLine.quantity}</td>
                                    <td><bdo class="price">${cartLine.summary}</bdo></td>
                                </tr>
                            </c:if>
                        </c:forEach>
                        </tbody>
                        <tfoot>
                        <tr>
                            <td align="right" colspan="6">
                                <span class="right"><label>小计总额：</label><bdo class="price summaryPrice">${summaryPrice}</bdo></span>
                            </td>
                        </tr>
                        </tfoot>
                    </table>
                </dd>
            </dl>
            <input type="hidden" id="summaryPrice" value="${summaryPrice}">
            <input type="hidden" id="GoodsCount" value="${GoodsCount}">
            <div class="cart-submit clearfix">
                <div class="right">
                    <p><label>商品金额：</label><bdo class="price summaryPrice">${summaryPrice}</bdo></p>
                    <p><label>应付总额（含运费）：</label><bdo class="price orange  font-20 summaryPrice">${summaryPrice}</bdo></p>
                    <c:if test="${orderImmediately == 0}">
                    <form action="${pageContext.request.contextPath}/orderCenter/submitOrderImmediately" method="post" id="orderForm">
                        <input type="hidden" name="quantity" id="quantity" value="${quantity}">
                        </c:if>
                        <c:if test="${orderImmediately == 1}">
                        <form action="${pageContext.request.contextPath}/orderCenter/submitOrder" method="post" id="orderForm">
                            </c:if>
                            <input type="hidden" name="title" id="title" value="${defaultAddress.receiverName}">
                            <input type="hidden" name="sellerId" id="sellerId" value="${sellerId}">
                            <input type="hidden" name="itemId" id="itemId" value="${itemId}">
                            <input type="hidden" name="needInvoice" id="needInvoice" value="0">
                            <input type="hidden" name="addressId" id="addressId" value="${defaultAddress.id}">
                            <p class="right"><c:if test="${orderImmediately == 1}"><input type="button" value="返回购物车" class="button button-default" onclick="javascript:window.history.back();"/></c:if><input type="button" value="提交订单" class="button button-deep" name="_target1" onclick="preSubmit()"/></p>
                        </form>
                </div>
            </div>
            <!-- /.Cart Order-Info -->
        </div>
        <!-- /.Container -->
    </c:otherwise>
</c:choose>
<script type="text/javascript">
    $(document).ready(function(){
        setup();
        preselect('上海市');
    });
</script>