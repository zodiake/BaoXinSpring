/**
 * Created by zodiake on 2014/6/12.
 */
$(function () {
    var cache = {};
    var sourceCache = {};
    var hostUrl = "http://"+window.location.host;
    $('#firstCategory').click(function (event) {
        var source = $(event.target);
        var text=$("option:selected",$(this)).text();
        $('#fabric-text span').eq(0).text(text);
        $('#fabric-text span').eq(1).text('»');
        var id = source.val();
        var select$ = $('#fabric-secondCategory');
        select$.children().remove();
        if (cache[id] == null) {
            $.ajax({
                url: hostUrl+'/fabricCategory/' + id + '/secondCategory',
                method: 'get',
                async:false,
                success: function (data) {
                    for (var i = 0; i < data.length; i++) {
                        var temp = $('<option value="' + data[i].id + '">' + data[i].name + '</option>')
                        select$.append(temp);
                    }
                    cache[id] = data;
                }
            })
        } else {
            var array = cache[id];
            for (var i = 0; i < array.length; i++) {
                var temp = $('<option value="' + array[i].id + '">' + array[i].name + '</option>')
                select$.append(temp);
            }
        }
    });

    $('#fabric-secondCategory').click(function(event){
        var source=$(event.target);
        var text=$("option:selected",$(this)).text();
        $('#fabric-text span').eq(2).text(text);
    });


    $('select#source').click(function (event) {
        var source = $(event.target);
        var id = source.val();
        var text=$("option:selected",$(this)).text();
        $('#source-text span').eq(0).text(text);
        $('#source-text span').eq(1).text('»');
        var select$ = $('select#sourceDetail');
        select$.children().remove();
        if (sourceCache[id] == null) {
            $.ajax({
                url: hostUrl+'/fabric/' + id + '/sourceDetail',
                method: 'get',
                success: function (data) {
                    for (var i = 0; i < data.length; i++) {
                        var temp = $('<option value="' + data[i].id + '">' + data[i].name + '</option>')
                        select$.append(temp);
                    }
                    sourceCache[id] = data;
                }
            })
        } else {
            var array = sourceCache[id];
            for (var i = 0; i < array.length; i++) {
                var temp = $('<option value="' + array[i].id + '">' + array[i].name + '</option>')
                select$.append(temp);
            }
        }
    });

    $('li input[type="radio"]').click(function (event) {
        var source = $(event.target);
        var first = source.attr('data-firstCategory');
        var second = source.attr('data-id');
        $('#firstCategory').val(first);
        $('#firstCategory').trigger('click');
        $('#fabric-secondCategory').val(second);
        $('#fabric-text span').eq(0).text(source.attr('data-first'));
        $('#fabric-text span').eq(2).text(source.attr('data-second'));
    });

    $('select#sourceDetail').click(function(event){
        var source=$(event.target);
        var text=$("option:selected",$(this)).text();
        $('#source-text span').eq(2).text(text);
    });

    $('#next_sub').click(function(){
        var header=$('#tab.list-inline li');
        var body=$('dl.publish-catalogue dd');
        header.eq(0).addClass('active');
        header.eq(1).removeClass('active');
        body.eq(0).css({display:"block"});
        body.eq(1).css({display:"none"});
    });

    $('#mainTypes input').click(function(event){
        var list=$('#mainTypes input:checked');
        var text="";
        for(var i=0;i<list.length;i++){
            text+=list.eq(i).siblings('label').html()+"  ";
        }
        $('#mainType_text').text("用途："+text);
    });

    (function(){
        var text="";
        $('#mainTypes input:checked').each(function(i,target){
            text+=$(target).siblings('label').html()+" ";
        });
        $('#mainType_text').text("用途："+text);
    })();
});
