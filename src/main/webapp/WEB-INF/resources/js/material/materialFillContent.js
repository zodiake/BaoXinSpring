/**
 * Created by zodiake on 2014/6/16.
 */

$(function () {
    var hostUrl = "http://" + window.location.host;
    $('ul.qr-inline').on('click', 'a.qr-delete', function (event) {
        event.preventDefault();
        var source = $(event.target);
        console.log(source.parent('li').length);
        source.parent('li').remove();
    });

    $('a.qr-add').click(function (event) {
        event.preventDefault();
        var unit=$('#materialMeasureType').val();
        var text = $('<li><label>起定量: </label><input type="text" name="keys"/><label class="unit"> '+unit+'及以上： </label><input type="text" name="values"/><label class="per-unit"> 元/'+unit+' </label> <a class="qr-delete"> 删除</a> </li>');
        var ul$ = $('ul.qr-inline li');
        var length = ul$.length;
        if (length == 1) {
            console.log(123);
            ul$.eq(0).before(text);
        } else {
            var index = length - 2;
            ul$.eq(index).after(text);
        }
    });

    $('#fakeContent').ckeditor({
        filebrowserBrowseUrl: '/image/upload',
        filebrowserUploadUrl: '/ckImage/upload',
        width: 895,
        height: 300
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
                url: '/image/upload',
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

    $('div.col-1').on('click','a.image-delete',function(event){
        var self=$(event.target);
        if(confirm('确认删除?')){
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

    $('#materialMeasureType').change(function (event) {
        var target = $(event.target);
        var val = target.val();
        if (val != null && val != '') {
            var unit = val + "及以上";
            var per_unit = "元/" + val;
            $('label.unit').html(unit);
            $('label.per-unit').html(per_unit);
            $('#amount-unit').html(val);
        }
    });
});
