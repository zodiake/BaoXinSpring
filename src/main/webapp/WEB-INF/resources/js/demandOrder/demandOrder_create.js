/**
 * sam
 * 求购列表页面
 */

$(function () {
    var hostUrl = "http://" + window.location.host;
    $('div.col-1 input').change(function (event) {
        var source = $(event.target);
        var id = source.attr('id');
        $.ajaxFileUpload({
                url: hostUrl + '/image/uploadImage',
                secureuri: false,
                fileElementId: source.attr('id'),
                dataType: 'json',
                /*success: function (data, status) {
                 var result = data.responseText;
                 if (data.responseText!="fail") {
                 $('#color-Helper').text("");
                 $('#' + id).siblings('img').attr('src', result);
                 $('#' + id).siblings('input').val(result);
                 } else {
                 $('#color-Helper').text("请上传正方形图片");
                 }
                 },
                 error: function (data, status, e) {
                 alert(e);
                 }*/
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

    $('.demandOrderCommit').click(function (event) {
        var template = $('<input type="hidden" name="tempImages"/>');
        var form = $('#form');
        var images = $('img.thumb');
        var type=$('#unit').val();
        images.each(function (index, source) {
            var temp = template.clone();
            temp.val($(source).attr('src'));
            form.append(temp);
        });
        var from = $('#validDateFrom').val();
        var to = $('#validDateTo').val();
        var priceFrom = $('#priceFrom').val();
        var priceTo = $('#priceTo').val();
        var demandSum = $('#demandSum').val();
        var regex = /^[0-9]+([.]{1}[0-9]+){0,2}$/;
        if (!regex.test(priceTo) || !regex.test(priceFrom)) {
            alert('价格区间只能够为整数或者保留两位的小数');
            return false;
        }

        if(type=="--请选择--"){
            alert('请选择单位');
            return false;
        }
        if (!regex.test(demandSum)) {
            alert('请正确填写求购总量');
            return false;
        }
        if (from == '' || from == 'undefined') {
            alert('开始日期不能为空');
            return false;
        }
        if (to == '' || to == 'undefined') {
            alert('结束日期不能为空');
            return false;
        }
        if (from == to) {
            alert('结束日期大于开始日期');
            return false;
        }
        form.submit();
    });

    //下拉框，单位
    $('select[name="unit"]').change(function () {
        var o = $(this), unit = o.val();
        $('.modifyUnit').text(unit);
    });


    //日期校验，今天以后的日期
    $('input[class="validDate"]').datepicker({
        minDate: '%y-%M-{%d}'
    });

    $('div.col-1').on('click', 'a.image-delete', function (event) {
        var self = $(event.target);
        if (confirm('确认删除?')) {
            self.siblings('img').removeAttr('src');
            self.remove();
        }
    });
});
