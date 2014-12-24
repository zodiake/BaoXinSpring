/**
 * Created by Charles on 2014/5/29.
 */
$(function(){
    var hostUrl = "http://"+window.location.host;
    $(".optStatus").click(function(){
        if(confirm("确定进行此操作吗？")){
            var target = $(this);
            var orderId = target.attr("data-id");
            var status = target.attr("data-status");
            $.ajax({
                type: "POST",
                url: hostUrl+"/orderCenter/order/updateStatus",
                data:{
                    id:orderId,
                    status:status
                },
                success:function(data){
                    var state = "";
                    if(data.result=="success"){
                        if(status == "BUYER_CANCEL"){
                            state = "买家已取消";
                            target.parent().html("");
                        }else if(status == "GOODS_RECEIVE"){
                            state = "已确认收货";
                            target.parent().html("<a href='"+hostUrl+"/buyerCenter/"+orderId+"/comment' class='button button-deep aLabel'>评价</a>");
                        }else if(status == "GOODS_DELIVER"){
                            state = "已发货";
                            target.parent().html("");
                        }else if(status == "SELLER_CANCEL"){
                            state = "卖家已取消";
                            target.parent().html("");
                        }
                        $(".orderState_"+orderId).html(state);
                    }else{
                        alert("状态更新失败，请刷新后重试！");
                    }
                }
            });
        }
    });

    //我的订单，分页
    $('div#myOrders ul.pagination li a').click(function(){
        var o = $(this);
        var url = o.attr('data-url');
        var status=$('div.detail-table > select').val();
        url = url + '&status=' + status;
        o.attr('href',url);
    });

    //我的订单，下拉框
    $('div .buyerOrderList >select').change(function(){
        var status = $(this).val();
        var url = '/orderCenter/buyerOrderList?status='+status;
        window.location.href= url;
    });
    //收到的订单，下拉框
    $('div .sellerOrderList >select').change(function(){
        var status = $(this).val();
        var url = '/orderCenter/sellerOrderList?status='+status;
        window.location.href= url;
    });
});
