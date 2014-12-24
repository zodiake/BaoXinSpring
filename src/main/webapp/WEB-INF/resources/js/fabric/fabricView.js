/**
 * Created by zodiake on 2014/6/9.
 */
$(function () {
    var flag = 0;
    var hostUrl = "http://"+window.location.host;
    $('a#add').click(function () {
        $.ajax({
            method: 'post',
            url: hostUrl+'/userCenter/favourites/item/1',
            success: function () {
                alert(22);
            }
        });
    });
    $('a#remove').click(function () {
        $.ajax({
            method: 'delete',
            url: hostUrl+'/userCenter/favourites/item/2',
            success: function () {
                alert(22);
            }
        });
    });
    $('a#addShop').click(function () {
        $.ajax({
            method: 'post',
            url: hostUrl+'/userCenter/favourites/shops/1',
            success: function () {
                alert(22);
            }
        });
    });
    $('a#removeShop').click(function () {
        $.ajax({
            method: 'delete',
            url: hostUrl+'/userCenter/favourites/shops/2',
            success: function () {
                alert(22);
            }
        });
    });

    //分类
    $('.product-classify dd a').hover(function(e){
        e.preventDefault();
        var o = $(this),id= o.attr('data-id');
        var uid = o.attr('data-uid');
        var type= o.attr('data-type');
        var ur1=hostUrl+'/fabricFirstCategory/' + id + '/secondCategory?uid='+uid;
        var ur2=hostUrl+'/materialFirstCategory/'+id+'/secondCategory?uid='+uid;
        var url = ur1;
        if('fabric' == type){
            url = ur1;
        }else{
            url=ur2;
        }
        var uri=hostUrl + '/shopCenter/'+uid+'/items';
        if (o.has('div').length == 0) {
            $.ajax({
                method:'get',
                url:url,
                success:function(data){
                    var str = '<div class="lay-classify" style="display: block">';
                    $.each(data,function(i){
                        str =str +  '<span class="tit"><a href="'+uri+'?type='+type+'&secondCategory='+data[i].id+'" data-id="'+data[i].id+'">'+data[i].name+'</a></span>';
                    })
                    o.append(str);
                }
            });
        }
        $('.product-classify dd').removeClass('cur').removeAttr('style');
        o.parent('dd').addClass('cur');
            //.css('z-index',3);
        $('div.lay-classify').css('display','none');
        o.children('div').css('display','block');
        o.find('span').removeClass('arrow')

    },function(){
        $('div.lay-classify').css('display','none');
        var o = $(this);
        o.find('span').addClass('arrow');
        $('.classify-list-two dd.cur .arrow').css('display','block');
    });

    //详情
    $('#detail').click(function(){
        var source = $(this);
        $('dl.detail-main dd').eq(1).css('display','none');
        $('dl.detail-main dd').eq(0).css('display','block');
        $('dl.detail-main dd').eq(2).css('display','none');
        source.addClass('active').siblings('li').removeClass('active');
    });

    //面料详情tab成交记录
    $('#bidList').click(function () {
        var source = $(this);
        var id = source.attr('data-id');
        var $dd=$('dl.detail-main dd').eq(1);
        $('dl.detail-main dd').eq(0).css('display','none');
        $('dl.detail-main dd').eq(2).css('display','none');
        $('dl.detail-main dd').eq(1).css('display','block');
        source.addClass('active').siblings('li').removeClass('active');
        if($dd.has('table').length==0) {
            $.ajax({
                url: hostUrl+'/fabric/' + id + '/orders',
                method: 'get',
                success: function (data) {
                    $dd.append(data);
                    $.getScript(hostUrl+'/resources/js/fabric/asyFabricView.js');
                }
            });
        }
    });
//面料详情tab交易评价
    $('#li-commentList').click(function (e) {
        e.preventDefault();
        var source = $(this);
        var id = source.attr('data-id');
        var $dd=$('dl.detail-main dd').eq(2);
        $('dl.detail-main dd').eq(0).css('display','none');
        $('dl.detail-main dd').eq(2).css('display','block');
        $('dl.detail-main dd').eq(1).css('display','none');
        source.addClass('active').siblings('li').removeClass('active');
        if($dd.has('table').length==0) {
            $.ajax({
                url: hostUrl+'/fabric/' + id + '/comments',
                method: 'get',
                success: function (data) {
                    $dd.append(data);
                    $.getScript(hostUrl+'/resources/js/fabric/asyFabricView.js');
                }
            });
        }
    });


    /*
    商品数量
     */
    $('#plus').click(function(e){
        e.preventDefault();
        var o = $(this);
        var input = o.siblings('input');
        var num = input.val();
        num++;
        input.val(num);
    });

    $('#reduction').click(function(e){
        e.preventDefault();
        var o = $(this);
        var input = o.siblings('input');
        var num = input.val();
        num--;
        if(num < 1){
            num = 1;
        }
        input.val(num);
    });

    /*
     变换图片
     */
    $('.magnifier-menu li').hover(function(){
        var o = $(this);
        o.addClass('active').siblings('li').removeClass('active');
        var url = o.attr('data-url');
        $('.magnifier .magnifier-view img').attr('src',url);
        $('.magnifier .magnifier-view img').attr('jqimg',url);
        $('.magnifier .magnifier-view a').attr('href',url);
    });


});

