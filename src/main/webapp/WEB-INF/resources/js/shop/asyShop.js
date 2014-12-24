/**
 * sam
 */
$(function () {
    var hostUrl = "http://"+window.location.host;

    //来自卖家评论下拉框
    $('.table-none #fromSeller select').change(function(){
        var o = $(this);
        var num = o.val();
        var id = o.attr('data-id');
        var url = hostUrl+'/sellerCenter/comments/'+id+'/fromSeller';
        var $dd = $('dl.assessment-tab dd').eq(1).empty();
        if(num > 3){
            if(num == 4){
                url = url + '?content=4';
            }
            if(num == 5){
                url = url + '?content=5'
            }
        }else{
            url = url + '?type='+num;
        }
        if($dd.children('table').length == 0){
            $.ajax({
                url:url,
                method:'get',
                success:function(data){
                    $dd.empty();
                    $dd.append(data);
                    $.getScript(hostUrl+'/resources/js/shop/asyShop.js');
                }
            })
        }
    });

    //卖家发出的评论下拉框
    $('.table-none #sellerSend select').change(function(){
        var o = $(this);
        var num = o.val();
        var id = o.attr('data-id');
        var url = hostUrl+'/sellerCenter/'+id+'/sellerComments';
        var $dd = $('dl.assessment-tab dd').eq(2).empty();
        if(num <4){
            url = url + '?type='+num;
        }else{
            url = url + '?content='+num;
        }
        if($dd.children('table').length == 0){
            $.ajax({
                url:url,
                method:'get',
                success:function(data){
                    $dd.empty();
                    $dd.append(data);
                    $.getScript(hostUrl+'/resources/js/shop/asyShop.js');
                }
            })
        }
    });

    //来自卖家评论的分页
    $('#fromSellerPage ul.pagination li a').click(function(){
        var o = $(this);
        var url = o.attr('href');
        o.removeAttr('href');
    //    var page = o.text();
        var source = $('.table-none #fromSeller select');
        var num = source.val();
        var $dd = $('dl.assessment-tab dd').eq(1).empty();
        if(num <4){
            url = url + '&type='+num;
        }else{
            url = url + '&content='+num;
        }
        if($dd.children('table').length == 0){
            $.ajax({
                url:hostUrl+url,
                method:'get',
                success:function(data){
                    $dd.empty();
                    $dd.append(data);
                    $.getScript(hostUrl+'/resources/js/shop/asyShop.js');
                }
            })
        }

    });

    //卖家发出的评论分页
    $('#fromSellerSendPage ul.pagination li a').click(function(){
        var o = $(this);
        var url = o.attr('href');
        o.removeAttr('href');
        var source = $('.table-none #sellerSend select');
        var num = source.val();
        var $dd = $('dl.assessment-tab dd').eq(2).empty();
        if(num <4){
            url = url + '&type='+num;
        }else{
            url = url + '&content='+num;
        }
        if($dd.children('table').length == 0){
            $.ajax({
                url:hostUrl+url,
                method:'get',
                success:function(data ){
                    $dd.empty();
                    $dd.append(data);
                    $.getScript(hostUrl+'/resources/js/shop/asyShop.js');
                }
            })
        }
    });

});
