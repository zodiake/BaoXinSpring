/**
 * 买家中心公共使用的js
 * Created by Charles on 2014/6/12.
 */
$(function () {
    var hostUrl = "http://" + window.location.host;
    $.ajax({
        method: "GET",
        cache:false,
        url: hostUrl + "/shoppingCart/quantity",
        success: function (data) {
            $(".cartQuantity").html(data.cartQuantity);
        }
    });
    $.ajax({
        method: "GET",
        cache:false,
        url: hostUrl + "/sampleBook/quantity",
        success: function (data) {
            $(".sampleQuantity").html(data.sampleQuantity);
        }
    });

    $(".viewCart").mouseenter(function () {
        var cartDiv = $(".shoppingcart");
        var viewCart = cartDiv.attr("viewCart");
        if (viewCart == 0) {
            cartDiv.attr("viewCart", "1");
            $.ajax({
                method: "GET",
                cache:false,
                url: hostUrl + "/shoppingCart/viewCart",
                success: function (data) {
                    $(".shoppingcart").html(data);
                }
            });
        } else {
            return;
        }
    });
    $(".shopping").mouseleave(function () {
        var viewCart = $(this).attr("viewCart");
        if (viewCart == 1) {
            $(this).attr("viewCart", "0")
        } else {
            return;
        }
    });

    $(".addFavourite").click(function () {
        var currentUserId = $("#currentUserId").val();
        if(currentUserId == null || currentUserId == ""){
            $(".list-box").find(".success-icon").addClass("success-icon-display");
            $(".box-con").html("<span>关注失败，可能没有<a href='/login'>[登录]</a></span>");
            $('.list-box').css('display', 'block');
            $('.fixed').css('display', 'block');
            return false;
        }
        var target = $(this);
        var itemId = target.attr("data-id");
        if (itemId != "" && itemId != null) {
            $.ajax({
                method: "POST",
                url: hostUrl + "/buyerCenter/favourites/item/" + itemId,
                success: function (data) {
                    if (data.type == "success") {
                        $(".list-box").find(".success-icon").removeClass("success-icon-display");
                        $(".box-con").html("<span>添加关注成功!</span>");
                        $('.list-box').css('display', 'block');
                        $('.fixed').css('display', 'block');
                    } else if (data.type == "fail") {
                        $(".list-box").find(".success-icon").addClass("success-icon-display");
                        $(".box-con").html("<span>您已关注此产品!</span>");
                        $('.list-box').css('display', 'block');
                        $('.fixed').css('display', 'block');
                    }

                },
                error: function (data) {
                    $(".list-box").find(".success-icon").addClass("success-icon-display");
                    $(".box-con").html("<span>添加关注失败，请重试!</span>");
                    $('.list-box').css('display', 'block');
                    $('.fixed').css('display', 'block');
                }
            });
        } else {
            return;
        }
    });

    //删除收藏的商品
    $('.deleteFavouriteItem').click(function(){
        var o = $(this);
        var id= o.attr('data-id');
        if(confirm("确认删除关注的商品？")){
            $.ajax({
                method:'delete',
                url:hostUrl + '/buyerCenter/favourites/item/'+id,
                success:function(data){
                    window.location.reload();
                }
            });
        }
    });

    /*$('.close-this').click(function(){
        $('.list-box').css('display','none');
        $('.fixed').css('display','none');
    })*/



    //搜索类型切换
    $(".search .drop").hover(function () {
        $(".selectedType-left").css("display", "block");
    });
    $(".search .drop").mouseleave(function () {
        $(".selectedType-left").css("display", "none");
    });

    $(".searchType").click(function () {
        var target = $(this);
        var src = target.attr("data-src");
        $(".selectedType").html(target.html() + "<i class='caret'></i>");
        $("#searchForm").attr('action', src);
        $(".selectedType-left").css("display", "none");
    });

    $(".sample").mouseenter(function () {
        var bookDiv = $(".viewSample");
        var sample = bookDiv.attr("sample");
        if (sample == 0) {
            bookDiv.attr("sample", "1");
            $.ajax({
                method: "GET",
                cache:false,
                url: hostUrl + "/sampleBook/viewBook",
                success: function (data) {
                    $(".viewSample").html(data);
                }
            });
        } else {
            return;
        }
    });
    $(".viewSample").mouseleave(function () {
        var viewCart = $(this).attr("sample");
        if (viewCart == 1) {
            $(this).attr("sample", "0")
        } else {
            return;
        }
    });

    //立即订购
    $(".orderImmediately").click(function () {
        var itemId = $("#itemId").val();
        var quantity = $("#quantity").val();
        var currentUserId = $("#currentUserId").val();
        if(currentUserId == null || currentUserId == ""){
            var cookies = document.cookie.split(';');
            for (var i = 0; i < cookies.length; i++) {
                var c = cookies[i];
                while (c.charAt(0) === ' ') c = c.substring(1, c.length);
                if (c.indexOf("buap_casUser=") === 0){
                    currentUserId = c.substring("buap_casUser=".length, c.length);
                }
            }
        }
        if(currentUserId == null || currentUserId == ""){
            $(".list-box").find(".success-icon").addClass("success-icon-display");
            $(".box-con").html("<span>订购失败，可能没有<a href='/login'>[登录]</a></span>");
            $('.list-box').css('display', 'block');
            $('.fixed').css('display', 'block');
            return false;
        }
        if (itemId == "" || quantity == "") {
            alert("购买出错，请重试！");
            return false;
        } else {
            $.ajax({
                method: "POST",
                url: hostUrl + "/shoppingCart/orderImmediately",
                data: {
                    itemId: itemId,
                    quantity: quantity
                },
                success: function (data) {
                    if(data.result == "success"){
                        window.location.href = hostUrl + "/orderCenter/orderImmediately?itemId=" + itemId + "&quantity=" + quantity;
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
                        $(".box-con").html("<span>订购失败，请刷新后重试!</span>");
                        $('.list-box').css('display','block');
                        $('.fixed').css('display','block');
                    }
                }
            });

        }
    });

    //搜索按钮
    $("#submit-link").click(function (event) {
        $("#searchForm").submit();
    });

    //删除关注的设计师
    $('.unlikeDesigner').click(function(){
        var o = $(this);
        var id= o.attr('data-id');
        if(confirm("确认取消关注吗？")){
            $.ajax({
                method:'POST',
                url:hostUrl + '/buyerCenter/unlikeDesigner',
                data:{
                    id:id
                },
                success:function(data){
                    if(data.result=="success"){
                        o.parents("li").remove();
                    }
                    if(data.result=="error"){
                        alert("操作失败，请刷新后重试!");
                    }
                },
                error:function(data){
                    alert("操作失败，请刷新后重试!");
                }
            });
        }
    });

    //删除关注的品牌
    $('.unlikeBrand').click(function(){
        var o = $(this);
        var id= o.attr('data-id');
        if(confirm("确认取消关注吗？")){
            $.ajax({
                method:'POST',
                url:hostUrl + '/buyerCenter/unlikeBrand',
                data:{
                    id:id
                },
                success:function(data){
                    if(data.result=="success"){
                        o.parents("li").remove();
                    }
                    if(data.result=="error"){
                        alert("操作失败，请刷新后重试!");
                    }
                },
                error:function(data){
                    alert("操作失败，请刷新后重试!");
                }
            });
        }
    });

    //首页品牌图片链接，解决ie兼容性问题
    $(".grit .thumb").click(function (event) {
        var href = $(this).prev("a").attr("href");
        window.location.href = href;
    });
});
