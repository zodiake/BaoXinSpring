/**
 * Created by zodiake on 2014/6/9.
 */
$(function () {
    var hostUrl = "http://"+window.location.host;
    //面料详情tab交易评价radio
    $('.selectComment').change(function(){
        var source = $(this);
        var id = source.attr('data-id');
        var type = source.val();
        var $dd=$('dl.detail-main dd').eq(2).empty();
        var url = '';
        if(type == 0){
            url = hostUrl+'/fabric/' + id + '/comments';
        }else{
            url = hostUrl+'/fabric/' + id + '/comments?type='+type;
        }
        if($dd.children('table').length==0) {
            $.ajax({
                url: url,
                method: 'get',
                success: function (data) {
                    $dd.empty();
                    $dd.append(data);
                    $.getScript(hostUrl+'/resources/js/fabric/asyFabricView.js');
                }
            });
        }
    });
    //面料详情tab交易评价分页
    $('#commentsPage li a').click(function(e){
        e.preventDefault();
        var source = $(this);
    //    var type = $(':radio[checked="checked"]').val();
        var $dd=$('dl.detail-main dd').eq(2);
        var url = source.attr('data-url');
        var type = source.attr('data-id');
        var page = source.text();
        url = url + '?page='+page;
        if(type != 0){
            url = url + '&type='+type;
        }
        if($dd.has('table').length==1) {
            $.ajax({
                url: url,
                method: 'get',
                success: function (data) {
                    $dd.empty();
                    $dd.append(data);
                    $.getScript(hostUrl+'/resources/js/fabric/asyFabricView.js');
                }
            });
        }
    });
    //面料详情tab成交记录分页
    $('#ordersPage li a').click(function(e){
        e.preventDefault();
        var source = $(this);
        var $dd=$('dl.detail-main dd').eq(1);
        var url = source.attr('data-url');
        var page = source.text();
        url = url + '&page';
        if($dd.has('table').length==1) {
            $.ajax({
                url: url,
                method: 'get',
                success: function (data) {
                    $dd.empty();
                    $dd.append(data);
                    $.getScript(hostUrl+'/resources/js/fabric/asyFabricView.js');
                }
            });
        }
    });

});
