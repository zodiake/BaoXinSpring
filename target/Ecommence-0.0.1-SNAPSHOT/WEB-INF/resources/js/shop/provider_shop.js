/**
 * sam
 * 供应商主页
 */
$(function () {
    var hostUrl = "http://"+window.location.host;
    var timer = null;
    //面料供应商主页左侧分类
    $('.classify-list ul li a').hover(function(){
        clearTimeout(timer);
        var obj=$(this);
        var html = '';
        var str='';
        var id = obj.attr('data-id');//一级分类id
        var uid=obj.attr('data-uid');//用户id
        var type=obj.attr('data-type');
        var url =''; //分类url
        var uri = hostUrl+'/shopCenter/'+uid+'/items';
        if(type == 'fabric')
            url=hostUrl+'/fabricFirstCategory/'+id+'/secondCategory?uid='+uid;
        else
            url=hostUrl+'/materialFirstCategory/'+id+'/secondCategory?uid='+uid;

    //    $('.classify-list ul li a').nextAll('div').remove();
        $('div.lay-classify').removeAttr('style').css('display','none');
        if(obj.has('div').length == 0){
            $.get(url,function(data){
                html = '<div class="lay-classify" style="display:block;">';
                $.each(data,function(i){
                    str =str +  '<span class="tit"><a href="'+uri+'?type='+type+'&secondCategory='+data[i].id+'" title=" data-id="'+data[i].id+'">'+data[i].name+'</a></span>';
                })
                //    $('.lay-classify').removeAttr("style").css("display","none");
                html = html + str + '</div>';
                obj.append(html);
            });
        }
        obj.parents('li').removeClass('cur');
        $('.classify-list ul li').removeClass('cur').removeAttr("style");
        obj.parent('li').addClass('cur');
        obj.parent('li').css("z-index",3);
        obj.children('div').css('display','block');
    },function(){
        $('div.lay-classify').hide();
        var o = $(this);
        o.parent('li').removeClass('cur');
    });


    //搜索
    $('.product-search a.search-btn').click(function(e){
        var input=$('#searchByProName');
        var uid=input.attr('data-id');
        var proName=input.val();
        var o = $(this);
        var url = hostUrl+'/shopCenter/'+uid+'/items?proName='+proName;
        o.attr('href',url);
    });

    /*//分页
    $('.con ul.pagination li a').click(function(e){
        var o = $(this);
        var url = o.attr('data-url');
        var proName = $('#searchByProName').val();
        if(proName != null && proName != undefined){
            url = url + '&proName='+proName;
        }
        o.attr('href',url);
    });*/

});
