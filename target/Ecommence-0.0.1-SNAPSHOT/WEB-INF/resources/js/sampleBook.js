/**
 * Created by Charles on 2014/5/27.
 */
$(function () {
    var hostUrl = "http://" + window.location.host;
    $(".addBook").click(function (e) {
        e.preventDefault();
        var itemId = $(this).attr("data-id");
        $.ajax({
            type: "POST",
            url: hostUrl + "/sampleBook/add",
            data: {
                id: itemId
            },
            success: function (data) {
                if(data.result == "success"){
                    $(".list-box").find(".success-icon").removeClass("success-icon-display");
                    $(".box-con").html("<span>添加成功!</span><a class='button button-deep' href='"+hostUrl+"/sampleBook/sample'>提交调样册</a>");
                    $('.list-box').css('display','block');
                    $('.fixed').css('display','block');
                    $(".sampleQuantity").html(data.sampleQuantity);
                }else if(data.result == "itemExist"){
                    $(".list-box").find(".success-icon").addClass("success-icon-display");
                    $(".box-con").html("<span>调样册中已存在此产品!</span><a class='button button-deep' href='"+hostUrl+"/sampleBook/sample'>提交调样册</a>");
                    $('.list-box').css('display','block');
                    $('.fixed').css('display','block');
                    return false;
                }else if(data.result == "offSale"){
                    $(".list-box").find(".success-icon").addClass("success-icon-display");
                    $(".box-con").html("<span>此商品已停售!</span>");
                    $('.list-box').css('display','block');
                    $('.fixed').css('display','block');
                    return false;
                }else if(data.result == "wrongBuyer"){
                    $(".list-box").find(".success-icon").addClass("success-icon-display");
                    $(".box-con").html("<span>您不能调样自己的商品!</span>");
                    $('.list-box').css('display','block');
                    $('.fixed').css('display','block');
                    return false;
                }else if(data.result == "error"){
                    $(".list-box").find(".success-icon").addClass("success-icon-display");
                    $(".box-con").html("<span>添加失败，请刷新后重试!</span>");
                    $('.list-box').css('display','block');
                    $('.fixed').css('display','block');
                    return false;
                }
            },
            error:function(data){
                $(".list-box").find(".success-icon").addClass("success-icon-display");
                $(".box-con").html("<span>添加失败，请刷新后重试!</span>");
                $('.list-box').css('display','block');
                $('.fixed').css('display','block');
            }
        });
    });

    $(".delete").click(function () {
        var itemId = $(this).attr("data-id");
        var sellerId = $(this).attr("seller-id");
        if (confirm("是否从购物车中删除?")) {
            $.ajax({
                type: "POST",
                url: hostUrl + "/sampleBook/delete",
                data: {
                    id: itemId,
                    sellerId: sellerId
                },
                success: function (data) {
                    if (data.result == "success") {
                        $(".dataTable_" + itemId).remove();
                        $(".sampleQuantity").html(data.sampleQuantity);
                        alert("删除成功");
                    }
                    window.location.href=hostUrl+"/sampleBook/sample";
                }
            });
        } else {
            return false;
        }
    });

    $(".reAddBook").click(function () {
        var itemId = $(this).attr("data-id");
        var sellerId = $(this).attr("data-spid");
        var GoodsCount = $("#goodsCount").val() - 1;
        $.ajax({
            type: "POST",
            url: hostUrl + "/sampleBook/reAdd",
            data: {
                id: itemId,
                sellerId: sellerId
            },
            success: function (data) {
                $(".dataTable_" + itemId).remove();
                $("#goodsCount").val(GoodsCount);
                window.location.href=hostUrl+"/sampleBook/sample";
            }
        });
    });

    $(".optStatus").click(function(){
        var target = $(this);
        var orderId = target.attr("data-id");
        var status = target.attr("data-status");
        $.ajax({
            type: "POST",
            url: hostUrl+"/orderCenter/sampleOrder/updateStatus",
            data:{
                id:orderId,
                status:status
            },
            success:function(data){
                var state = "";
                if(status == "BUYER_CANCEL"){
                    state = "已取消";
                }else if(status == "GOODS_RECEIVE"){
                    state = "已确认收货";
                }else if(status == "GOODS_DELIVER"){
                    state = "已发货";
                }else if(status == "SELLER_CANCEL"){
                    state = "BUYER_CANCEL";
                }
                $(".orderState_"+orderId).html(state);
                target.parent().html("");
            }
        });
    });

    $(".addressBlock").click(function(){
        var addressId = $.trim($(this).attr("data-id"));
        $(".addressBlock").removeClass("active");
        $(this).addClass("active");
        $("#addressId").val(addressId);
    });

    //新地址弹出框
    $('.newAddress').click(function(){
        $('.new-address').css('display','none');
        $('.fixed').css('display','none');
        $("#newAddressId").val("");
        $("#receiverName").val("");
        $("#state").val("");
        $("#city").val("");
        $("#street").val("");
        $("#zipCode").val("");
        $("#mobile").val("");
        $("#zipPhone").val("");
        $("#phone").val("");
        $("#childPhone").val("");
        $("#defaultAdd").val("0");
        $("#defaultAdd").removeAttr("checked");
        $('.new-address').css('display','block');
        $('.fixed').css('display','block');
    });


    //新地址弹出层关闭
    $('.fixed:not(.new-address),.btn_li .default').click(function(){
        $('.new-address').css('display','none');
        $('.fixed').css('display','none');
        $("#newAddressId").val("");
        $("#receiverName").val("");
        $("#state").val("");
        $("#city").val("");
        $("#street").val("");
        $("#zipCode").val("");
        $("#mobile").val("");
        $("#zipPhone").val("");
        $("#phone").val("");
        $("#childPhone").val("");
        $("#defaultAdd").val("0");
        $("#defaultAdd").removeAttr("checked");
    });

    $(".deleteAdd").click(function () {
        var addId = $(this).attr("data-id");
        if(confirm("是否删除此地址？")){
            $.ajax({
                method: "GET",
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
                method: 'GET',
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
                            $("#defaultAdd").attr("checked","checked");
                        }else{
                            $("#defaultAdd").removeAttr("checked");
                        }
                        $('.new-address').css('display','block');
                        $('.fixed').css('display','block');
                    }
                }
            });
        }
    });
})

    function preSubmit() {
        var GoodsCount = $("#GoodsCount").val(), addressId = $("ul .active").attr("data-id");
        var currentDate= $("#serverTime").val();
        var deliveryTime= $("#deliveryTime").val();

        if (addressId == "" || addressId == null) {
            alert("请选择收货地址!");
            return false;
        }
        if(deliveryTime == ""){
            alert("请选择交货日期!");
            return false;
        }
        if(currentDate>deliveryTime){
            alert("交货日期不能早于当前日期!");
            $("#deliveryTime").val('');
            return false;
        }
        $("#addressId").val(addressId);
        if (GoodsCount < 1) {
            alert("调样册中没有找到商品，请重新购买!");
            return false;
        } else {
            $("#orderForm").submit();
        }
    }