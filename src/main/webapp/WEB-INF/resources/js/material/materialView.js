/**
 * Created by zodiake on 2014/6/9.
 */
$(function () {
    var hostUrl = "http://"+window.location.host;

    //详情
    $('#detail').click(function(){
        var source = $(this);
        $('dl.detail-main dd').eq(1).css('display','none');
        $('dl.detail-main dd').eq(0).css('display','block');
        $('dl.detail-main dd').eq(2).css('display','none');
        source.addClass('active').siblings('li').removeClass('active');
    });

    //成交交易记录
    $('#bidList').click(function () {
        var source = $(this);
        var id = source.attr('data-id');
    //    var $dd=$('dl.detail-main dd').eq(1).empty();
        var $dd=$('dl.detail-main dd').eq(1);
        $('dl.detail-main dd').eq(0).css('display','none');
        $('dl.detail-main dd').eq(1).css('display','block');
        $('dl.detail-main dd').eq(2).css('display','none');
        source.addClass('active').siblings('li').removeClass('active');
        if($dd.has('table').length==0) {
            $.ajax({
                url: hostUrl+'/material/' + id + '/orders',
                method: 'get',
                success: function (data) {
                    $dd.append(data);
                    $.getScript(hostUrl+'/resources/js/material/asyMaterialView.js');
                }
            });
        }
    });

    //成交评价记录
    $('#li-commentList').click(function () {
        var source = $(this);
        var id = source.attr('data-id');
    //    var $dd=$('dl.detail-main dd').eq(2).empty();
        var $dd=$('dl.detail-main dd').eq(2)
        $('dl.detail-main dd').eq(0).css('display','none');
        $('dl.detail-main dd').eq(2).css('display','block');
        $('dl.detail-main dd').eq(1).css('display','none');
        source.addClass('active').siblings('li').removeClass('active');
        if($dd.has('table').length==0) {
            $.ajax({
                url: '/material/' + id + '/comments',
                method: 'get',
                success: function (data) {
                    $dd.append(data);
                    $.getScript(hostUrl+'/resources/js/material/asyMaterialView.js');
                }
            });
        }
    });

    //分类
    $('.product-classify dd a').hover(function () {
        var id = $(this).attr('data-id');
        var o = $(this);
        var url = hostUrl + '/materialCategory/' + id + '/secondCategory';
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
        var uri = hostUrl + '/shopCenter/'+uid+'/items'
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
            })
        }
        $('.product-classify dd').removeClass('cur').removeAttr('style');
        o.parent('dd').addClass('cur').css('z-index',3);
        $('div.lay-classify').css('display','none');
        o.children('div').css('display','block');
        o.find('span').removeClass('arrow')
    },function(){
        $('div.lay-classify').css('display','none');
        var o = $(this);
        o.find('span').addClass('arrow');
        $('.classify-list-two dd.cur .arrow').css('display','block');
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
    $('.magnifier ul.magnifier-menu li').hover(function(){
        var o = $(this);
        o.addClass('active').siblings('li').removeClass('active');
        var url = o.attr('data-url');
        $('.magnifier .magnifier-view img').attr('src',url);
        $('.magnifier .magnifier-view img').attr('jqimg',url);
        $('.magnifier .magnifier-view a').attr('href',url);
    });

});
