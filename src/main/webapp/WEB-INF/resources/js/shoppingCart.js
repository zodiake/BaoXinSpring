/**
 * Created by Charles on 2014/5/27.
 */
$(function () {
    var hostUrl = "http://" + window.location.host;
    //商品详情页加入购物车
    $(".addCart").click(function (e) {
        e.preventDefault();
        var itemId = $(this).attr("data-id");
        var quantity = $(".orderQuantity").val();
        $.ajax({
            method: "POST",
            url: hostUrl + "/shoppingCart/add",
            data: {
                id: itemId,
                quantity: quantity
            },
            success: function (data) {
                if(data.result == "success"){
                    $(".list-box").find(".success-icon").removeClass("success-icon-display");
                    $(".list-box").find(".list-dl").css('display','block');
                    $(".box-con").html("<span>添加成功!</span><a class='button button-deep' href='"+hostUrl+"/shoppingCart/shopCart'>去购物车结算</a>");
                    $('.list-box').css('display','block');
                    $('.fixed').css('display','block');
                    $(".cartQuantity").html(data.cartQuantity);
                }else if(data.result == "negotiable"){
                    $(".list-box").find(".success-icon").addClass("success-icon-display");
                    $(".list-box").find(".list-dl").css('display','none');
                    $(".box-con").css("padding-left","20px");
                    $(".box-con").html("<div>当前订购数量价格需与供应商面议，请联系供应商。</div>");
                    $('.list-box').css('display','block');
                    $('.fixed').css('display','block');
                }else if(data.result == "notForSale"){
                    $(".list-box").find(".success-icon").addClass("success-icon-display");
                    $(".list-box").find(".list-dl").css('display','none');
                    $(".box-con").css("padding-left","20px");
                    $(".box-con").html("<div>订购量不满足起订量!</div>");
                    $('.list-box').css('display','block');
                    $('.fixed').css('display','block');
                }else if(data.result == "offSale"){
                    $(".list-box").find(".success-icon").addClass("success-icon-display");
                    $(".list-box").find(".list-dl").css('display','block');
                    $(".box-con").html("<span>此商品已停售!</span>");
                    $('.list-box').css('display','block');
                    $('.fixed').css('display','block');
                }else if(data.result == "wrongBuyer"){
                    $(".list-box").find(".success-icon").addClass("success-icon-display");
                    $(".list-box").find(".list-dl").css('display','block');
                    $(".box-con").html("<span>您不能购买自己的商品!</span>");
                    $('.list-box').css('display','block');
                    $('.fixed').css('display','block');
                }else if(data.result == "error"){
                    $(".list-box").find(".success-icon").addClass("success-icon-display");
                    $(".list-box").find(".list-dl").css('display','block');
                    $(".box-con").html("<span>添加失败，请刷新后重试!</span>");
                    $('.list-box').css('display','block');
                    $('.fixed').css('display','block');
                }
            },
            error:function(data){
                $(".list-box").find(".success-icon").addClass("success-icon-display");
                $(".list-box").find(".list-dl").css('display','block');
                $(".box-con").html("<span>添加失败，请刷新后重试!</span>");
                $('.list-box').css('display','block');
                $('.fixed').css('display','block');
            }
        });
    });
    //购物车页面增加商品数量
    $(".increase").click(function () {
        var itemId = $(this).attr("data-id");
        var sellerId = $(this).attr("seller-id");
        var quantity = 1;
        var quantityIpt = $("#" + itemId + "_quantity"), summarySpan = $("#" + itemId + "_summary"), priceSpan = $("#" + itemId + "_price");
        var quantityIptVal = quantityIpt.val();
        $.ajax({
            method: "POST",
            url: hostUrl + "/shoppingCart/modifyNum",
            data: {
                id: itemId,
                sellerId: sellerId,
                quantity: quantity
            },
            success: function (data) {
                var quantityNew = data.quantityNew, priceNew = data.priceNew, summaryNew = data.summaryNew, sumQuantity = data.sumQuantity, sumPrice = data.sumPrice;
                quantityIpt.val(quantityNew);
                priceSpan.html(priceNew);
                summarySpan.html(summaryNew);
                $(".cartQuantity").html(data.cartQuantity);
                $(".sumquantity_" + sellerId).html(sumQuantity);
                if(sumPrice <= 0){
                    $(".sumprice_" + sellerId).html("面议");
                }else{
                    $(".sumprice_" + sellerId).html("<b class='price orange'>"+sumPrice+"</b>");
                }
                if(data.result == "negotiable"){
                    priceSpan.html("面议");
                    summarySpan.html("面议");
                    alert("当前订购数量价格需与供应商面议，请联系供应商。");
                    quantityIpt.val(quantityIptVal);
                }else if(data.result == "notForSale"){
                    alert("订购量不满足起订量!");
                    quantityIpt.val(quantityIptVal);
                }
            }
        });
    });
    //购物车页面减少商品数量
    $(".reduce").click(function () {
        var itemId = $(this).attr("data-id");
        var sellerId = $(this).attr("seller-id");
        var quantityIpt = $("#" + itemId + "_quantity"), summarySpan = $("#" + itemId + "_summary"), priceSpan = $("#" + itemId + "_price");
        var quantity = -1;
        var quantityIptVal = quantityIpt.val();
        if (quantityIpt.val() < 2) {
            return false;
        }
        $.ajax({
            method: "POST",
            url: hostUrl + "/shoppingCart/modifyNum",
            data: {
                id: itemId,
                sellerId: sellerId,
                quantity: quantity
            },
            success: function (data) {
                var quantityNew = data.quantityNew, priceNew = data.priceNew, summaryNew = data.summaryNew, sumQuantity = data.sumQuantity, sumPrice = data.sumPrice;
                quantityIpt.val(quantityNew);
                priceSpan.html(priceNew);
                summarySpan.html(summaryNew);
                $(".cartQuantity").html(data.cartQuantity);
                $(".sumquantity_" + sellerId).html(sumQuantity);
                $(".sumprice_" + sellerId).html(sumPrice);
                if(sumPrice <= 0){
                    $(".sumprice_" + sellerId).html("面议");
                }else{
                    $(".sumprice_" + sellerId).html("<b class='price orange'>"+sumPrice+"</b>");
                }
                if(data.result == "negotiable"){
                    priceSpan.html("面议");
                    summarySpan.html("面议");
                    alert("当前订购数量价格需与供应商面议，请联系供应商。");
                    quantityIpt.val(quantityIptVal);
                }else if(data.result == "notForSale"){
                    alert("订购量不满足起订量!");
                    quantityIpt.val(quantityIptVal);
                }
            }
        });
    });
    //购物车页面手动调整商品数量
    $(".quantity").change(function () {
        var itemId = $(this).attr("data-id");
        var sellerId = $(this).attr("seller-id");
        var quantity = $(this).val(), summarySpan = $("#" + itemId + "_summary"), priceSpan = $("#" + itemId + "_price");
        var rule = /^[0-9]*[0-9][0-9]*$/;
        if (!rule.test(quantity)) {
            alert("输入的数量必须为整数");
            document.execCommand("undo");
            return false;
        }
        if (quantity > 2147483647) {
            alert("输入的数量超过最大值");
            document.execCommand("undo");
            return false;
        }
        $.ajax({
            method: "POST",
            url: hostUrl + "/shoppingCart/refresh",
            data: {
                id: itemId,
                sellerId: sellerId,
                quantity: quantity
            },
            success: function (data) {
                var quantityNew = data.quantityNew, priceNew = data.priceNew, summaryNew = data.summaryNew, sumQuantity = data.sumQuantity, sumPrice = data.sumPrice;
                $(".cartQuantity").html(data.cartQuantity);
                $(".sumquantity_" + sellerId).html(sumQuantity);
                $(".sumprice_" + sellerId).html(sumPrice);
                if(data.result == "success"){
                    $(this).val(quantityNew);
                    priceSpan.html(priceNew);
                    summarySpan.html(summaryNew);
                    if(sumPrice <= 0){
                        $(".sumprice_" + sellerId).html("面议");
                    }else{
                        $(".sumprice_" + sellerId).html("<b class='price orange'>"+sumPrice+"</b>");
                    }
                }else if(data.result == "negotiable"){
                    priceSpan.html("面议");
                    summarySpan.html("面议");
                    alert("当前订购数量价格需与供应商面议，请联系供应商。");
                }else if(data.result == "notForSale"){
                    alert("订购量不满足起订量!");
                    document.execCommand("undo");
                }
            }
        });
    });
    //购物车页面删除商品
    $(".delete").click(function () {
        var itemId = $(this).attr("data-id");
        var sellerId = $(this).attr("seller-id");
        if (confirm("是否从购物车中删除?")) {
            $.ajax({
                method: "POST",
                url: hostUrl + "/shoppingCart/delete",
                data: {
                    id: itemId,
                    sellerId: sellerId
                },
                success: function (data) {
                    if (data.result == "success") {
                        $(".dataTable_" + itemId).remove();
                        $(".cartQuantity").html(data.cartQuantity);
                        alert("删除成功");
                    }
                    if (data.cartLine == "empty") {
                        $(".data_" + itemId).remove();
                    }
                    window.location.href=hostUrl+"/shoppingCart/shopCart";
                }
            });
        } else {
            return false;
        }
    });

    //订单确认页面将商品重新加入购物车
    $(".reAddCart").click(function () {
        var itemId = $(this).attr("data-id");
        var sellerId = $(this).attr("data-spid");
        var price = $(this).attr("data-price"), summaryPrice = $("#summaryPrice").val();
        var GoodsCount = $("#GoodsCount").val() - 1;
        $.ajax({
            method: "POST",
            url: hostUrl + "/shoppingCart/reAdd",
            data: {
                id: itemId,
                sellerId: sellerId
            },
            success: function (data) {
                $(".dataTable_" + itemId).remove();
                if(GoodsCount<1){
                    alert("订单中没有商品，请重新下单!");
                    window.location.href=hostUrl+"/shoppingCart/shopCart";
                }
                $(".summaryPrice").html(summaryPrice - price);
                $("#summaryPrice").val(summaryPrice - price);
                $("#GoodsCount").val(GoodsCount);
            }
        });
    });
    //订单确认页面修改发票title
    $(".drop-invoice-menu").mouseenter(function(){
        $('.drop-invoice-left').css('display','block');
    });
    $('.drop-invoice-left').mouseleave(function(){
        $('.drop-invoice-left').css('display','none');
    });
    $('.changeTitle').click(function () {
        var orderTitle = $(".orderTitle").val();
        $(".showOrderTitle").html(orderTitle);
        $("#title").val(orderTitle);
        $('.drop-invoice-left').css('display','none');
    });
    //订单确认页面选择是否需要发票
    $('.needInvoice').click(function () {
        var isChecked = $(this).attr("checked");
        if (isChecked == "checked") {
            $(this).removeAttr("checked");
            $("#needInvoice").val("0");
        } else {
            $(this).attr("checked", "checked");
            $("#needInvoice").val("1");
        }
    });

    //商品详情页输入购买数量
    $(".orderQuantity").change(function () {
        var itemId = $(this).attr("data-id");
        var quantity = $(this).val();
        var rule = /^[0-9]*[0-9][0-9]*$/;
        if (!rule.test(quantity)) {
            alert("输入的数量必须为整数");
            document.execCommand("undo");
            return false;
        }
        if (quantity > 2147483647) {
            alert("输入的数量超过最大值");
            document.execCommand("undo");
            return false;
        }
    });

    //将立即订购的商品加入购物车
    $(".immediatelyToCart").click(function () {
        var itemId = $(this).attr("data-id");
        var quantity = $("#quantity").val();
        $.ajax({
            method: "GET",
            url: hostUrl + "/shoppingCart/immediatelyToCart",
            data: {
                itemId: itemId,
                quantity:quantity
            },
            success: function (data) {
                $(".cartQuantity").html(data.cartQuantity);
                $(".dataTable_" + itemId).remove();
                window.location.href = hostUrl +"/shoppingCart/shopCart";
            }
        });
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
    //关闭添加收藏夹、购物车、调样册弹出层
    $('.fixed:not(.list-box),.close-this').click(function(){
        $('.list-box').css('display','none');
        $('.fixed').css('display','none');
    });

    //收货地址切换效果
    $(".addressBlock").click(function () {
        var addressId = $.trim($(this).attr("data-id"));
        $(".addressBlock").removeClass("active");
        $(this).addClass("active");
        $("#addressId").val(addressId);
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
                method: 'GET',
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

    //购物车页面提交订单
    $('.submitCart').click(function () {
        var sellerId = $(this).attr("data-key");
        var currentUserId = $("#currentUserId").val();
        if (sellerId == null || sellerId == "") {
            return false;
        } else if (currentUserId == null || currentUserId == "") {
            $(".list-box").find(".success-icon").addClass("success-icon-display");
            $(".box-con").html("<span>订购失败，可能没有<a href='/login'>[登录]</a></span>");
            $('.list-box').css('display', 'block');
            $('.fixed').css('display', 'block');
            return false;
        }else {
            $("#sellerId").val(sellerId);
            $("#cartForm").submit();
        }
    });


});

//提交订单
function preSubmit() {
    var GoodsCount = $("#GoodsCount").val(), addressId = $("#addressId").val(),isSelectAdd=$(".address-list .active").size(),sellerId=$("#sellerId").val(),itemId=$("#itemId").val();
    if (addressId == "" || addressId == null || isSelectAdd <1) {
        alert("请选择收货地址!");
        return false;
    }
    if (GoodsCount < 1) {
        alert("订单中没有找到商品，请选择商品!");
        return false;
    } else {
        $.ajax({
            url: "/orderCenter/preCheckOrder/",
            method: 'POST',
            data: {
                sellerId: sellerId,
                itemId:itemId
            },
            success: function (data) {
                if(data.result=="offSale"){
                    alert(data.item+"已下架，请选购其他商品或联系供应商。");
                    return false;
                }else if(data.result=="negotiable"){
                    alert(data.item+"价格已更新，需要联系供应商面议购买。");
                    return false;
                }else if(data.result=="empty"){
                    alert("购物车中未找到商品。");
                    return false;
                }else{
                    $("#orderForm").submit();
                }
            }
        });

    }
}