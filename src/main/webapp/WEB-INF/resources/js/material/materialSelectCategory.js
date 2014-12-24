/**
 * Created by zodiake on 2014/6/16.
 */
$(function () {
    var cache = {};
    var hostUrl = "http://" + window.location.host;
    $('#firstCategory').on('click', function (event) {
        var source = $(event.target);
        var text = $("option:selected", $(this)).text();
        $('#fabric-text span').eq(0).text(text);
        $('#fabric-text span').eq(1).text('»');
        var id = source.attr('value') || source.val();
        var select$ = $('#material-secondCategory');
        select$.children().remove();
        if (cache[id] == null) {
            $.ajax({
                url: hostUrl + '/materialCategory/' + id + '/secondCategory',
                method: 'get',
                async: false,
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

    $('#material-secondCategory').click(function (event) {
        var source = $(event.target);
        var text = $("option:selected", $(this)).text();
        $('#fabric-text span').eq(2).text(text);
    });

    $('li input[type="radio"]').click(function (event) {
        var source = $(event.target);
        var first = source.attr('data-firstCategory');
        var second = source.attr('data-id');
        $('#firstCategory').val(first);
        $('#firstCategory').trigger('click');
        $('#fabric-text span').eq(0).text(source.attr('data-first'));
        $('#fabric-text span').eq(2).text(source.attr('data-second'));
        $('#material-secondCategory').val(second);
        $('#material-secondCategory').trigger('click');
        $('#prefer-publish span').eq(0).text(source.attr('data-first'));
        $('#prefer-publish span').eq(1).text("»");
        $('#prefer-publish span').eq(2).text(source.attr('data-second'));
    });

    $('#sub_next').click(function(){
        var header=$('#tab.list-inline li');
        var body=$('dl.publish-catalogue dd');
        header.eq(0).addClass('active');
        header.eq(1).removeClass('active');
        body.eq(0).css({display:"block"});
        body.eq(1).css({display:"none"});
    });
});
