/**
 * Created by zodiake on 2014/6/9.
 */
$(function () {
    var hostUrl = "http://"+window.location.host;
    /*  暂时不删除
    $('#favourite').click(function(){
        var o = $(this);
        var id= o.attr('data-id');
        var type = o.attr('data-type');
        var url = "";
        if(type == 'material'){
            url = "/sellerCenter/material/favourite/"+id;
        }else{
            url = "/fabric/favourite/"+id;
        }
        var str = o.html();
        if('加入收藏夹' == str){
            $.ajax({
                url:url,
                method:'post',
                success:function(data){
                    console.log(data);
                    if('success' == data){
                        alert('收藏成功');
                        o.html('取消收藏夹');
                    } else
                        alert('收藏失败')
                }
            })

        }else{
            $.ajax({
             url:url,
             method:'delete',
             success:function(data){
                if('success' == data){
                    alert('取消收藏成功');
                    o.html('加入收藏夹');
                }else{
                    alert('取消收藏失败');
                }

                }
             })
        }
    });*/

    $('#favourite').click(function(e){
        e.preventDefault();
        var o = $(this);
        var id= o.attr('data-id');
        var type = o.attr('data-type');
        var url = hostUrl+"/buyerCenter/favourites/item/"+id;
        /*if(type == 'material'){
            url = hostUrl+"/sellerCenter/material/favourite/"+id;
        }else{
            url = hostUrl+"/fabric/favourite/"+id;
        }*/
        $.ajax({
            url:url,
            method:'post',
            success:function(data){
                if('success' == data){
                    alert(data.content);
                } else
                    alert(data.content)
            }
        })
    });
});
