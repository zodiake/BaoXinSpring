/**
 * sam
 */

$(function () {
    //综合求购
    //下拉框
    var hostUrl = "http://" + window.location.host;
    $('#demandType').change(function(){
        var o = $(this),type= o.val();
        $('#demandOrderForm input[name=type]').val(type);
        var url =hostUrl + "/demandOrderList?type="+type;
        $('#demandOrderForm').attr('action',url).submit();
    });

    //求购搜索
    $('#demandOrderSearch').click(function(e){
        e.preventDefault();
        var o=$(this);
        var content = $('#content').val();
        var url =hostUrl + "/demandOrderList?1=1";
        var type= o.attr('data-type');
       $('#demandOrderForm input[name=type]').val(type);
        $('#demandOrderForm input[name=content]').val(content);
        url = url + "&type="+type+"&content="+content;
        $('#demandOrderForm').attr('action',url).submit();

    });
});


