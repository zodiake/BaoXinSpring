<%--
  Created by IntelliJ IDEA.
  User: Charles
  Date: 2014/6/13
  Time: 14:16
  订单提交页面ajax更新地址列表
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="application/javascript">
    var hostUrl = "http://" + window.location.host;
    $(function(){

        //收货地址切换效果
        $("#addressId").val("${newAdd.id}");

        $(".addressBlock").click(function () {
            var addressId = $.trim($(this).attr("data-id"));
            $(".addressBlock").removeClass("active");
            $(this).addClass("active");
            $("#addressId").val(addressId);
        });

        $(".deleteAdd").click(function () {
            var addId = $(this).attr("data-id");
            if(confirm("是否删除此地址？！")){
                $.ajax({
                    type: "GET",
                    url: hostUrl + "/buyerCenter/address/deleteAdd/"+addId,
                    success: function (data) {
                        $(".address-list").html(data);
                    }
                });
            }
        });

        //订单确认页面设置默认地址
        $('.setDefaultAddress').click(function () {
            var addressId = $(this).attr("data-id");
            if (addressId == null || addressId == "") {
                return false;
            } else {
                $.ajax({
                    url: hostUrl + "/buyerCenter/setDefaultAddress/"+addressId,
                    type: 'get',
                    success: function (data) {
                        $(".address-list").html(data);
                    }
                });
            }
        });
        //订单确认页面编辑地址
        $('.editAddress').click(function () {
            var addressId = $(this).attr("data-id");
            if (addressId == null || addressId == "") {
                return false;
            } else {
                $.ajax({
                    url: hostUrl + "/buyerCenter/viewAddress/"+addressId,
                    type: 'get',
                    success: function (data) {
                        if(data.result=="success"){
                            $("#receiverName").val(data.receiverName);
                            $("#state").val(data.state);
                            preselect(data.state);
                            $("#city").val(data.city);
                            $("#street").val(data.street);
                            $("#zipCode").val(data.zipCode);
                            $("#mobile").val(data.mobile);
                            $("#zipPhone").val(data.zipPhone);
                            $("#phone").val(data.phone);
                            $("#childPhone").val(data.childPhone);
                            $("#newAddressId").val(data.id);
                            $("#defaultAdd").val(data.defaultAddress);
                            if(data.defaultAddress == 1){
                                $(".defaultA").html("<input  name='defaultAdd' id='defaultAdd' type='checkbox' value='1' checked>设置为默认收货地址");
                            }else{
                                $(".defaultA").html("<input  name='defaultAdd' id='defaultAdd' type='checkbox' value='0'>设置为默认收货地址");
                            }
                            $("#defaultAdd").change(function(){
                                if($(this).val()==0){
                                    $(this).val("1");
                                }else if($(this).val()==1){
                                    $(this).val("0");
                                }
                            });
                            $('.new-address').css('display','block');
                            $('.fixed').css('display','block');
                        }
                    }
                });
            }
        });


    });
</script>
<ul>
    <c:forEach items="${addresses}" var="address">
        <li class="addressBlock <c:if test="${newAdd.id eq address.id}">active</c:if>" data-id="${address.id}">
            <p><b><span class="receiveName_${address.id}">${address.receiverName}</span></b>收</p>
            <p class="address-show"><span class="addressDetail_${address.id}">${address.state}${address.city}</span></p>
            <p class="address-show"><span class="addressDetail_${address.id}">${address.street}</span></p>
            <p>${address.mobile}</p>
            <!--hover编辑-->
            <div class="opera-nav">
                <span class="edit-bt editAddress" data-id="${address.id}">编缉</span><c:if test="${defaultAddress.id != address.id}"><span class="edit-bt setDefaultAddress" data-id="${address.id}">设为常用地址</span></c:if><span class="close deleteAdd" data-id="${address.id}"></span></div>
            <span class="tick"></span>
            <!--hover编辑 end-->
        </li>
    </c:forEach>
</ul>