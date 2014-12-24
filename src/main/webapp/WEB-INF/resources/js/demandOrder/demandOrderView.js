/**
 * sam
 */

$(function () {
    //求购详情页面
    $('.leaveComment').click(function(e){
        /*e.preventDefault();
        var o = $(this);
        $.ajax({
            url: '/isLogin',
            method: 'get',
            success: function (data) {
               if("success"){

               }else{
                   alert("请登录后在留言");
               }
            }
        });*/
        e.preventDefault();
        $('.lay-message').css('display','block');
        $('.fixed').css('display','block');
        });

    /*
     变换图片
     */
    $('.magnifier-menu li').hover(function(){
        var o = $(this);
        o.addClass('active').siblings('li').removeClass('active');
        var url = o.attr('data-url');
        $('.magnifier .magnifier-view img').attr('src',url);
        $('.magnifier .magnifier-view img').attr('jqimg',url);
        $('.magnifier .magnifier-view a').attr('href',url);
    });

    //取消、关闭操作
    var clearVals=function (){
        $('.lay-message').css('display','none');
        $('.fixed').css('display','none');
        $('.lay-message textarea').val('');
    }
    //关闭
    $('.lay-message a.close').click(clearVals);

    //取消
      $('.lay-message-btn a.btn-gray').click(clearVals);

    //发送站内信
    $('.lay-message-btn input.btn-small').click(function(e){
        e.preventDefault();
        var url = '/letterCenter/letter';
        var content = $('.mess-tarea').val();
        if(content != '' && content != null){
            $('#form').attr('action',url).submit();
        }else{
            alert('内容不能为空');
        }
    });
});


