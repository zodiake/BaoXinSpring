/**
 * Created by zodiake on 2014/6/23.
 */
$(function () {
    var cache = {};
    var hostUrl = "http://"+window.location.host;
    $('.tabs li').click(function (event) {
        var source = $(event.target);
        var header = source.parent().find('li');
        var index = header.index(source);
        var body = source.parents('.provision-hd').siblings('.provision-con');
        body.css({display: 'none'});
        body.eq(index).css({display: 'block'});
        header.removeClass('active');
        header.eq(index).addClass('active');
        //显示分页
        var type=source.attr('data-type');
        if(type=='new'){
            $('#newPage').css("display","block");$('#hotPage').css("display","none");
        }else{
            $('#hotPage').css('display','block');$('#newPage').css('display','none');
        }
    });

    $('.hot').click(function (event) {
        var source = $(event.target);
        var shopId = source.attr('data-id');

        var source = $(event.target);
        var header = source.parent().find('li');
        var index = header.index(source);
        var body = source.parents('.provision-hd').siblings('.provision-con');
        var target = body.eq(index).find('ul');

        if (cache[shopId] == null && cache[shopId] == undefined) {
            $.ajax({
                url: hostUrl+'/buyerCenter/favourite/shops/' + shopId + '/hottestItems',
                success: function (data) {
                    for (var i = 0; i < data.length; i++) {
                        var html = '<li><a href="/'+data[i].url+'/'+data[i].id+'" ><img src="'+data[i].coverImage+'?size=300"></a><div class="name"><a href="/' + data[i].url + '/' + data[i].id + '" title="">' + data[i].title + '</a></div><div class="price">&yen;' + parseFloat(data[i].price).toFixed(1) + '</div></li>';
                        target.append(html);
                    }
                    cache[shopId] = true;
                }
            });
        }
    });

    $('.del-shop').click(function (event) {
        event.preventDefault();
        var source = $(event.target);
        var shopId = source.attr('data-id');
        if(confirm("确认删除？")){
            $.ajax({
                method: 'delete',
                url: hostUrl+'/buyerCenter/favourites/shops/' + shopId,
                success: function () {
                    source.parents('.provision-mode').remove();
                    window.location.reload();
                }
            });
        }
    });

    $('.locationHomePage').click(function (event) {
        event.preventDefault();
        var source = $(event.target);
        var url = source.attr('data-url');
        if(url.indexOf("http://")<0){
            url = "http://"+url;
        }
        window.open(url);
    });

    $('.attention-btn').click(function(e){
        var currentUserId = $("#currentUserId").val();
        if(currentUserId == null || currentUserId == ""){
            $(".list-box").find(".success-icon").addClass("success-icon-display");
            $(".box-con").html("<span>关注失败，可能没有<a href='/login'>[登录]</a></span>");
            $('.list-box').css('display', 'block');
            $('.fixed').css('display', 'block');
            return false;
        }
        e.preventDefault();
        var o = $(this);
        var id = o.attr('data-id');
        var url = "/buyerCenter/favourite/shops/"+id;
        $.ajax({
            url:url,
            method:'POST',
            success:function(data){
                if (data.content == "成功") {
                    $(".list-box").find(".success-icon").removeClass("success-icon-display");
                    $(".box-con").html("<span>添加关注店铺成功!</span>");
                    $('.list-box').css('display', 'block');
                    $('.fixed').css('display', 'block');
                    o.after("<a class='attention-icon'>已关注</a>");
                    o.remove();
                } else if (data.type == "fail") {
                    $(".list-box").find(".success-icon").addClass("success-icon-display");
                    $(".box-con").html("<span>您已关注此店铺!</span>");
                    $('.list-box').css('display', 'block');
                    $('.fixed').css('display', 'block');
                }
            },
            error: function (data) {
                if(data.readyState != 4){
                    $(".list-box").find(".success-icon").addClass("success-icon-display");
                    $(".box-con").html("<span>关注失败，可能没有登录</span>");
                    $('.list-box').css('display', 'block');
                    $('.fixed').css('display', 'block');
                }
            }
        });

    });

    //热销分页
    $('#hotPage li a').click(function(){
        var o = $(this);
        var id=$('.hot').attr('data-id');
    //    var url =hostUrl + o.attr('data-url')+id+ o.attr('data-uri');
        var url = hostUrl + o.attr('data-url');
        var page = o.text();
        url = url + '?page='+page;
    //    o.attr('href',url);
        $.ajax({
            url: url,
            success: function (data) {
                for (var i = 0; i < data.length; i++) {
                    var html = '<li><a href="#" title=""><img src="'+data[i].coverImage+'"></a><div class="name"><a href="/' + data[i].url + '/' + data[i].id + '" title="">' + data[i].title + '</a></div><div class="price">' +parseFloat(data[i].price).toFixed(1)  + '</div></li>';
                    target.append(html);
                    console.log(html)
                }
                cache[shopId] = true;
            }
        });
    });

    //关闭弹窗
    $('.close-this').click(function(){
        $('.list-box').css('display','none');
        $('.fixed').css('display','none');
    })
});