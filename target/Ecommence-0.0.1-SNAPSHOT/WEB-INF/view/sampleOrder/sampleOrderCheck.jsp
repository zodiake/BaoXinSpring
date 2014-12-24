<%--
  Created by IntelliJ IDEA.
  User: Charles
  Date: 2014/7/11
  Time: 17:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- /.Container -->
<form action="${pageContext.request.contextPath}/orderCenter/submitBook" method="post" id="orderForm">
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
                <span class="defaultA"><input  name="defaultAddress" id="defaultAddress" type="checkbox"/>设置为默认收货地址</span>
            </li>

            <li class="btn_li">
                <input type="hidden" id="newAddressId" name="newAddressId" value="">
                <input type="button" class="button button-deep submitAddress" value="保存"/>
                <input type="button" class="button default cancelAddress" value="取消"/>
            </li>
        </ul>
    </div>
    <!-- /.Cart Receiving -->
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
            <div class="address-btn">
                <label>交货日期：</label><span><input type="text" id="deliveryTime" name="deliveryTime" datepicker="true"></span>
            </div>
            <div class="address-btn">
                <label>备注信息：</label><div><textarea name="remark" id="remark" cols="80" rows="5" class="tarea-t" style="resize: none;"></textarea></div>
            </div>
        </dd>
    </dl>

    <!-- /.Cart Receiving -->
    <input type="hidden" id="goodsCount" value="${goodsCount}">
    <input type="hidden" id="addressId"  name="addressId" value="${defaultAddress.id}">
    <input type="hidden" id="sellerId" name="sellerId" value="${sellerId}">
    <input type="hidden" id="serverTime" name="serverTime" value="${serverTime}">
    <!-- /.Cart Order-Info -->
    <dl class="box order-info">
        <dt>订单信息</dt>
        <dd>
            <table class="table table-border table-order">
                <thead>
                <tr class="order-title">
                    <th>商品名称</th>
                    <th width="20%">所在企业</th>
                    <th width="10%">操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${sampleBookList}" var="sampleBook">
                    <tr class="dataTable_${sampleBook.item.id}">
                        <td><a href="${pageContext.request.contextPath}/${sampleBook.item.url}/${sampleBook.item.id}">${sampleBook.item.name}</a></td>
                        <td>${sampleBook.sellerName}</td>
                        <td><button type="button" class="button button-default reAddBook" data-id="${sampleBook.item.id}" data-spid="${sellerId}">放回调样册</button></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </dd>
    </dl>
    <div class="cart-submit"><p class="right"><button type="button" class="button button-deep" onclick="preSubmit()">提交调样</button></p></div>
    <!-- /.Cart Order-Info -->
</div>
<!-- /.Container -->
</form>
<script type="text/javascript">
    $(document).ready(function(){
        setup();
        preselect('上海市');
    });
</script>