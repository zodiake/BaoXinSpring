/**
 * sam
 */
$(function () {
    var hostUrl = "http://"+window.location.host;

    //来自卖家下拉框
    $('#fromSellerTab select').change(function(){
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



    //卖家发出的评论
    $('#sellerSend').click(function(){
        var obj = $(this);
        var id = obj.attr('data-id');
        var $dd = $('dl.assessment-tab dd').eq(1);
        obj.siblings('li').removeAttr('class');
        obj.addClass('active');
        $dd.css('display','block').siblings('dd').css('display','none');
        if($dd.children('table').length == 0){
            $.ajax({
                url: hostUrl+'/buyerCenter/'+id+'/sellerComments',
                method: 'get',
                success: function (data) {
                    $dd.empty();
                    $dd.append(data);
                    $.getScript(hostUrl+'/resources/js/shop/asyBuyerShop.js');
                }
            });
        }
    });

    /*//来自买家评论分页
    $('#fromSellerPage ul.pagination li a').click(function(){
        var o = $(this);
        var url = o.attr('data-url');
        o.attr('href',hostUrl+url);
    });*/

    //来自买家评论
    $('#fromSeller').click(function(){
        var obj = $(this);
        var $dd = $('dl.assessment-tab dd').eq(0);
        obj.siblings('li').removeAttr('class');
        obj.addClass('active');
        $dd.css('display','block').siblings('dd').css('display','none');
    });

});
