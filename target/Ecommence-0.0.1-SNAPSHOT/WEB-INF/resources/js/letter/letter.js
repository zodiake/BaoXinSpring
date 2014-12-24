/**
 * sam
 */
$(function () {
/***********详情页面表单提交**************/
    $('#form a').click(function(e){
        e.preventDefault();
        var o = $(this);
        var str = o.attr('data-type');
        var text=$('#form textarea');
        if(text.val()=='' || text.val()==undefined){
            alert('内容不能为空');
            return false;
        }

        var len = $('#form textarea').val().length + 1;
        if(len>400){
            return false;
        }
        if(str == 'submit'){
            var form = $("#form");
            form.attr('action','/letterCenter/letter');
            form.submit();
        }else{
            $('#form textarea').val('');
        }

    });

    $('#text').keydown(function(){
        var o = $(this);
        var len = o.val().length + 1;
        var total = 400;
        var hasCount = total - len;
        $('span.col-gray span').eq(0).text(len);
        $('span.col-gray span').eq(1).text(hasCount);
        if(len > 400){
            alert('已超过限制');
        }
    });
    /*******************列表页面表单提交***********************/
    $('.mail-list ul li div.function a').click(function(e){
        e.preventDefault();
        var o = $(this);
        var receiveId= o.attr('data-id');
        $('#formList input').eq(0).val(receiveId);
        $('.mail-reply').css('display',"block");
        var text=$('#formList textarea');
        text.focus();
    });

    $('#formList a').click(function(e){
        e.preventDefault();
        var o= $(this);
        var text=$('#formList textarea');
        var len = text.val().length + 1;
        if(len>400){
            return false;
        }
        if(text.val()=='' || text.val()==undefined){
            alert('内容不能为空');
            return false;
        }
        var str = o.attr('data-type');
        if(str == 'submit'){
            var form = $("#formList");
            form.attr('action','/letterCenter/letter');
            form.submit();
        }else{
            text.val('');
        }

    });

});
