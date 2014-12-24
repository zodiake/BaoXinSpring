/**
 * sam
 */
$(function () {
    var hostUrl = "http://"+window.location.host;


    //卖家发出的评论下拉框
    $('#sellerSend select').change(function(){
        var o = $(this);
        var num = o.val();
        var id = o.attr('data-id');
        var url = hostUrl+'/buyerCenter/'+id+'/sellerComments';
        var $dd = $('dl.assessment-tab dd').eq(1).empty();
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
                    $.getScript(hostUrl+'/resources/js/shop/asyBuyerShop.js');
                }
            })
        }
    });



    //卖家发出的评论分页
    $('#fromSellerSendPage ul.pagination li a').click(function(){
        var o = $(this);
        var url = o.attr('data-url');
        var page = o.text();
        var source = $('.table-none #sellerSend select');
        var num = source.val();
        var $dd = $('dl.assessment-tab dd').eq(1).empty();
        url = url + '?page='+page;
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
                    $.getScript(hostUrl+'/resources/js/shop/asyBuyerShop.js');
                }
            })
        }
    });

});
