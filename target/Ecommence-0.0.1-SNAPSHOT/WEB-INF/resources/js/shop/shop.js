/**
 * sam
 */
$(function () {
    var hostUrl = "http://"+window.location.host;

    //来自买家下拉框
    $('.table-none #fromBuyer select').change(function(){
        var o = $(this);
        var num = o.val();
        var url = o.attr('data-url');
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
        o.siblings('form').children('input').val(num);
        o.siblings('form').attr('action',url).submit();
    });

    //来自卖家评论
    $('#fromSeller').click(function(){
        var obj = $(this);
        var id = obj.attr('data-id');
        var $dd = $('dl.assessment-tab dd').eq(1);
        obj.siblings('li').removeAttr('class');
        obj.addClass('active');
        $dd.css('display','block').siblings('dd').css('display','none');
        if($dd.children('table').length == 0){
            $.ajax({
                url: hostUrl+'/sellerCenter/comments/'+id+'/fromSeller',
                method: 'get',
                success: function (data) {
                    $dd.empty();
                    $dd.append(data);
                    $.getScript(hostUrl+'/resources/js/shop/asyShop.js');
                }
            });
        }
    });

    //卖家发出的评论
    $('#sellerSend').click(function(){
        var obj = $(this);
        var id = obj.attr('data-id');
        var $dd = $('dl.assessment-tab dd').eq(2);
        obj.siblings('li').removeAttr('class');
        obj.addClass('active');
        $dd.css('display','block').siblings('dd').css('display','none');
        if($dd.children('table').length == 0){
            $.ajax({
                url: hostUrl+'/sellerCenter/'+id+'/sellerComments',
                method: 'get',
                success: function (data) {
                    $dd.empty();
                    $dd.append(data);
                    $.getScript(hostUrl+'/resources/js/shop/asyShop.js');
                }
            });
        }
    });

    //来自买家评论分页
    $('#fromBuyerPage ul.pagination li a').click(function(){
        var o = $(this);
        var url = o.attr('data-url');
        var page = o.text();
        url = url + '?page='+page;
        var source = $('.table-none #fromBuyer select');
        var num = source.val();
        if(num <4){
            url = url + '&type='+num;
        }else{
            url = url + '&content='+num;
        }
        o.attr('href',hostUrl+url);
    });

    //来自买家评论
    $('#fromBuyerComment').click(function(){
        var obj = $(this);
        var $dd = $('dl.assessment-tab dd').eq(0);
        obj.siblings('li').removeAttr('class');
        obj.addClass('active');
        $dd.css('display','block').siblings('dd').css('display','none');
    });

});
