/**
 * Created by zodiake on 2014/6/10.
 */
//123
$(function () {
    var hostUrl = "http://"+window.location.host;
    $('a.down').click(function (event) {
        var src = $(this);
        var id = src.attr('data-id');
        if(confirm('是否下架?')){
            $.ajax({
                method: 'put',
                url: hostUrl+'/sellerCenter/items/' + id,
                success: function (data) {
                    if(data.type == "fabric" || data.type == "material") {
                        $(".list-box").find(".success-icon").addClass("success-icon-display");
                        $(".box-con").css("padding-left","20px");
                        $(".box-con").html("<div>下架操作成功！</div>");
                        $('.list-box').css('display','block');
                        $('.fixed').css('display','block');
                        src.parent('td').prev('td').text('下架');
                        src.after("<a type='button' class='button button-deep' href="+hostUrl + "/sellerCenter/"+ data.type + "Edit?id=" + id+">编辑</a>");
                        src.remove();
                    }else if(data.type == "confirm"){
                        if(confirm("该商品下有未完成订单,是否继续操作？")){
                            $.ajax({
                                method: 'put',
                                url: hostUrl+'/sellerCenter/itemsContinue/' + id,
                                success: function (data) {
                                    if(data.type == "fabric" || data.type == "material") {
                                        $(".list-box").find(".success-icon").addClass("success-icon-display");
                                        $(".box-con").css("padding-left","20px");
                                        $(".box-con").html("<div>下架操作成功！</div>");
                                        $('.list-box').css('display','block');
                                        $('.fixed').css('display','block');
                                        src.parent('td').prev('td').text('下架');
                                        src.after("<a type='button' class='button button-deep' href="+hostUrl + "/sellerCenter/"+ data.type + "Edit?id=" + id+">编辑</a>");
                                        src.remove();
                                    }
                                }
                            });
                        }
                    }else{
                        alert(data.content);
                    }
                },
                error:function(data){
                    $(".list-box").find(".success-icon").addClass("success-icon-display");
                    $(".box-con").css("padding-left","20px");
                    $(".box-con").html("<div>下架操作失败！</div>");
                    $('.list-box').css('display','block');
                    $('.fixed').css('display','block');
                }
            });
        }
    });

    /*//下拉框
    $('.detail-table select').change(function(){
        var o = $(this),type= o.val();
        var url =hostUrl + '/sellerCenter/items?type='+type;
        $('#formSelect input').val(type);
        $('#formSelect').attr('action',url).submit();
    });*/

    //checkbox
    $('input[name="itemType"]').click(function(){
        var o = $(this),v= o.val();
        var checked = o.is(":checked");
        $(':checkbox:checked').each(function(){
           var a=$(this);
            var k= a.val();
            if(v!=k){
                a.removeAttr('checked');
            }
        })
        if(!checked){
            v='all';
        }
        var type= o.attr('data-id');
        $('#itemType').val(v);
        var url = hostUrl + '/sellerCenter/items?type='+type+'&itemType='+v;
        $('#form').attr('action',url).submit();
    });


    $('.myItemSearch').click(function(){
        var itemStr= $("#itemStr").val();
        if(itemStr == null || itemStr ==""){
            return false;
        }else{
            $("#itemString").val(itemStr);
            var url = hostUrl + "/sellerCenter/items";
            $('#itemStrForm').attr('action',url).submit();
        }
    });


    $('a.clone').click(function (event) {
        var src = $(this);
        var id = src.attr('data-id');
        if(confirm('是否复制此产品?')){
            $.ajax({
                method: 'put',
                url: hostUrl+'/sellerCenter/itemsClone/' + id,
                success: function (data) {
                    if(data.type == "fabric" || data.type == "material") {
                        src.parents("tr").after(data.resultStr);
                        $(".list-box").find(".success-icon").addClass("success-icon-display");
                        $(".box-con").css("padding-left","20px");
                        $(".box-con").html("<div>复制操作成功！</div>");
                        $('.list-box').css('display','block');
                        $('.fixed').css('display','block');
                    }
                },
                error:function(data){
                    $(".list-box").find(".success-icon").addClass("success-icon-display");
                    $(".box-con").css("padding-left","20px");
                    $(".box-con").html("<div>复制操作失败！</div>");
                    $('.list-box').css('display','block');
                    $('.fixed').css('display','block');
                }
            });
        }
    });

    //关闭添加收藏夹、购物车、调样册弹出层
    $('.fixed:not(.list-box),.close-this').click(function(){
        $('.list-box').css('display','none');
        $('.fixed').css('display','none');
    });
});
