/**
 * sam
 * 求购列表页面
 */

$(function () {
    //下拉框
    $('div.sortbar select').change(function(){
        var o = $(this);
        var status = o.val();
        var url = '/buyerCenter/demandOrders?type='+status;
        $('#form > input').val(status);
        $('#form').attr('action',url).attr('method','get').submit();
    });

    /*//分页
    $('.ask-box ul.pagination li a').click(function(){
       var o = $(this);
        var url = o.attr('data-url');
        var status = $('div.sortbar select').val();
        url = url + '&type='+status;
        o.attr('href',url);
    });*/

    //状态操作
    $('.down_item').click(function(e){
        e.preventDefault();
        /*var o = $(this),id= o.attr('data-id'),status= o.attr('data-status');
        var url = '/buyerCenter/demandOrder/'+id+'/updateStatus?type='+status;*/
        var o = $(this);
        var url = o.attr('data-url');
        if(confirm('确认下架?')){
            $.ajax({
                url:url,
                cache:false,
                method:'get',
                success:function(data){
                    alert(data.message);
                    window.location.reload();
                }
            });
        }
    });

});
