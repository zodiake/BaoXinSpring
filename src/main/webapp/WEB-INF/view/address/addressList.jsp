<%--
  Created by IntelliJ IDEA.
  User: Charles
  Date: 2014/6/13
  Time: 14:16
  地址管理页面ajax更新地址列表
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="application/javascript">
    var hostUrl = "http://" + window.location.host;
    $(function(){

        $(".deleteAddress").click(function () {
            var addId = $(this).attr("data-id");
            if(confirm("是否删除此地址？！")){
                $.ajax({
                    type: "GET",
                    url: hostUrl + "/buyerCenter/address/delete/"+addId,
                    success: function (data) {
                        $(".tbl-deliver-address").html(data);
                    }
                });
            }
        });

        //订单确认页面设置默认地址
        $('.defaultAddress').click(function () {
            var addressId = $(this).attr("data-id");
            if (addressId == null || addressId == "") {
                return false;
            } else {
                $.ajax({
                    url: hostUrl + "/buyerCenter/defaultAddress/"+addressId,
                    type: 'get',
                    success: function (data) {
                        $(".tbl-deliver-address").html(data);
                    }
                });
            }
        });
    });
</script>
<div class="address-tit"><strong class="col-red">已保存的有效地址</strong></div>
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="deliver-table">
    <colgroup>
        <col style="width:10%">
        <col style="width:20%">
        <col style="width:30%">
        <col style="width:10%">
        <col style="width:10%">
        <col style="width:10%">
        <col style="width:10%">
    </colgroup>
    <thead>
    <tr>
        <th>收货人</th>
        <th>所在地区</th>
        <th>街道地址</th>
        <th>邮编</th>
        <th>电话/手机</th>
        <th></th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${addresses}" var="address" varStatus="i">
        <tr <c:if test="${i.index%2==1}"> class="active"</c:if> class="tr_${address.id}">
            <td align="center" valign="middle">${address.receiverName}</td>
            <td valign="middle">${address.state} ${address.city}</td>
            <td valign="middle">${address.street}</td>
            <td align="center" valign="middle">${address.zipCode}</td>
            <td align="center" valign="middle">${address.mobile}</td>
            <td align="center" valign="middle" class="addressStatus">
                <c:choose>
                    <c:when test="${defaultAdd.id eq address.id}">默认地址</c:when>
                    <c:otherwise><a href="javascript:void(0);" data-id="${address.id}" class="defaultAddress">设为默认</a></c:otherwise>
                </c:choose>
            </td>
            <td valign="middle"><a href="${pageContext.request.contextPath}/buyerCenter/address/${address.id}" title="">修改</a><em class="line">|</em><a href="javascript:void(0);" data-id="${address.id}" class="deleteAddress">删除</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<p class="tbl-attach">最多保存20个有效地址</p>
