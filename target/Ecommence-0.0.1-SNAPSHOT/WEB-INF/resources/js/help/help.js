/**
 * sam
 */
$(function () {
    var hostUrl = "http://"+window.location.host;

    //菜单栏控制右侧内容显示
    $('.nav a').click(function(){
        var o = $(this);
        var id = o.attr('data-id');
        var url = o.attr('href').split('#')[0];
        console.log(location.pathname);
        console.log(url)
    //    $('#'+id).css('display','block').siblings('li').css('display','none');


    });

});
