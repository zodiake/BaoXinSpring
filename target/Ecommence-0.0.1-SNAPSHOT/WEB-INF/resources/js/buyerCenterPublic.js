/**
 * 买家中心公共使用的js
 * Created by Charles on 2014/6/12.
 */
$(function () {
    var hostUrl = "http://" + window.location.host;
    $.ajax({
        method: "GET",
        url: hostUrl + "/shoppingCart/quantity",
        success: function (data) {
            $(".cartQuantity").html(data.cartQuantity);
        }
    });
    $.ajax({
        method: "GET",
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
    $(".selectedType").hover(function () {
        $(".selectedType-left").css("display", "block");
    });
    $(".selectedType-left").mouseleave(function () {
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
        if (itemId == "" || quantity == "") {
            alert("购买出错，请重试！");
            return false;
        } else {
            window.location.href = hostUrl + "/orderCenter/orderImmediately?itemId=" + itemId + "&quantity=" + quantity;
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
});
