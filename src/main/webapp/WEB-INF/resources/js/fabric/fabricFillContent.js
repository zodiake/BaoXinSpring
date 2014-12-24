/**
 * Created by zodiake on 2014/6/17.
 */
$(function () {
    var cache = {};
    var hostUrl = "http://" + window.location.host;

    $('#fakeContent').ckeditor({
        filebrowserBrowseUrl: '/image/upload',
        filebrowserUploadUrl: '/ckImage/upload',
        width: 895,
        height: 300
    });

    $('#fabricTechnologyId').change(function (event) {
        var source = $(event.target);
        var id = source.val();
        var target = $('#fabricSecondTechnologyId');
        target.children().remove();
        if (id == '') {
            var temp = $('<option value="">--请选择--</option>')
            target.append(temp);
            return;
        }
        if (cache[id] == null) {
            $.ajax({
                url: hostUrl + '/fabricTechnology/' + id,
                method: 'get',
                error: function () {
                    console.log()
                },
                success: function (data) {
                    for (var i = 0; i < data.length; i++) {
                        var temp = $('<option value="' + data[i].id + '">' + data[i].name + '</option>')
                        target.append(temp);
                    }
                    cache[id] = data;
                }
            });
        } else {
            var array = cache[id];
            for (var i = 0; i < array.length; i++) {
                var temp = $('<option value="' + array[i].id + '">' + array[i].name + '</option>')
                target.append(temp);
            }
        }
    });

    $('a.qr-add').click(function (event) {
        event.preventDefault();
        var unit = $('#fabricMeasureType').val();
        var text = $('<li><label>起定量: </label><input type="text" name="keys"/><label class="unit"> ' + unit + '及以上： </label><input type="text" name="values"/><label class="per-unit"> 元/' + unit + ' </label> <a class="qr-delete"> 删除</a> </li>');
        var ul$ = $('ul.qr-inline li');
        var length = ul$.length;
        if (length == 1) {
            ul$.eq(0).before(text);
        } else {
            var index = length - 2;
            ul$.eq(index).after(text);
        }
    });

    $('ul.qr-inline').on('click', 'a.qr-delete', function (event) {
        event.preventDefault();
        var source = $(event.target);
        source.parent('li').remove();
    });

    $('img.thumb').click(function () {
        var source = $(this);
        var file = source.siblings('input');
        file.trigger('click');
    });

    $('div.col-1 input').change(function (event) {
        var source = $(event.target);
        var id = source.attr('id');
        $.ajaxFileUpload({
                url: hostUrl + '/image/upload',
                secureuri: false,
                fileElementId: source.attr('id'),
                dataType: 'json',
                success: function (data, status) {
                    var result = $.parseJSON(data.responseText);
                    if (result.status == "success") {
                        $('#color-Helper').text("");
                        $('#' + id).siblings('img').attr('src', result.location);
                        $('#' + id).siblings('input').val(result.location);
                        if ($('#' + id).siblings('a').size() == 0)
                            $('#' + id).parent().append('<a class="image-delete"></a>');
                    } else if (result.status == "type") {
                        $('#color-Helper').text("文件类型错误");
                    } else if (result.status == "size") {
                        $('#color-Helper').text("图片长宽不符合规范");
                    }
                },
                error: function (data, status, e) {
                    alert(e);
                }
            }
        );
        return false;
    });

    $('div.col-1').on('click', 'a.image-delete', function (event) {
        var self = $(event.target);
        if (confirm('确认删除?')) {
            self.siblings('img').removeAttr('src');
            self.remove();
        }
    });

    $('form').submit(function (event) {
        var template = $('<input type="hidden" name="tempImages"/>');
        var form = $(this);
        var images = $('img.thumb');
        images.each(function (index, source) {
            var temp = template.clone();
            temp.val($(source).attr('src'));
            form.append(temp);
        });
    });

    $('div.lay-publish-list input').change(function (event) {
        var target = $(event.target);
        var id = target.attr('id');
        var pattern = $('label.pattern');
        var size = $('#span-' + id).size();

        $('#span-' + id).remove();

        if (size == 0) {
            var span = $('<span class="publish-pitch">' + target.siblings('label').html() + '</span>');
            span.attr('id', 'span-' + id);
            span.attr('data-id', id);

            span.on('click', function (event) {
                var source = $(this);
                $('#' + id).prop('checked', false);
                span.remove();
            });
            pattern.after(span);
        }
    });

    $('span.publish-pitch').click(function(event){
        var target=$(event.target);
        var id=target.attr('data-id');
        $('input[value="'+id+'"]').prop('checked',false);
        target.remove();
    });

    $('a.publish-close').on('click', 'a', function (event) {
        event.preventDefault();
        var id = $(event.target).attr('data-id');
        $(event.target).remove();
        $('#' + id).click();
    });

    $('.color_label').click(function () {
        $('.box_div').toggle();
    });

    $(".fixed:not(.box_div)").click(function () {
        $(".box_div").fadeOut();
        $(".fixed").fadeOut();
        $('body').removeClass('stop-scrolling');
        var label$ = $('label.color_label');
        $('li.ml_no>div>div.color-div').remove();
        label$.after($('#container div').clone());
    });

    $("#slider").slider({
        min: 1,
        max: 3687,
        range: "min",
        slide: function (event, ui) {
            $("#amount").val(ui.value);
        },
        change: function (event, ui) {
            var json = '{';
            json += '"rowNumber" : "' + ui.value + '"' + '}';
            $.ajax({
                url: colorHttp + "/restservice/getColourByRing",
                data: { data: json },
                type: "get",
                cache: false,
                dataType: "jsonp",
                jsonp: "callbackparam",
                jsonpCallback: "jsonpCallbackSlider",
                timeout: 3000,
                success: function (data) {
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    alert(errorThrown);
                }
            });
        }
    });

    $('#fabricMeasureType').change(function (event) {
        var target = $(event.target);
        var val = target.val();
        console.log($('label.unit').length);
        if (val != null && val != '') {
            var unit = val + "及以上";
            var per_unit = "元/" + val;
            $('label.unit').html(unit);
            $('label.per-unit').html(per_unit);
            $('#amount-unit').html(val);
        }
    });

});
