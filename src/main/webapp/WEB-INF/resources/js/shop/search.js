/**
 * Created by zodiake on 2014/7/14.
 */
$(function () {
    $(':checked').each(function (n, target) {
        var source = $(target);
        var template = $('<li/>');
        if (source.attr('data-label') != undefined) {
            template.text(source.attr('data-label') + "：");
            template.append('<span>' + source.siblings('label').text() + '</span>');
            template.append('<i data-target="' + source.attr('id') + '">×</i>');
            $('div.search-choose ul').append(template);
        }
    });

    $('ul i').click(function (event) {
        var source = $(event.target);
        $('#' + source.attr('data-target')).prop('checked', false);
        $('form.search-main').submit();
    });

    $('ul.weight a').click(function (event) {
        event.preventDefault();
        var source = $(event.target);
        var target = $('form.search-main');
        var min = source.attr('data-min');
        var max = source.attr('data-max');
        $('#widthRangeMin').val(min);
        $('#widthRangeMax').val(max);
        $('#widthMin').val('');
        $('#widthMax').val('');
        target.submit();
    });

    $(':radio,:checkbox').change(function () {
        $('form').submit();
    });

    $('ul.weight a').click(function (event) {
        event.preventDefault();
        var source = $(event.target);
        var target = $('form.search-main');
        var min = source.attr('data-min');
        var max = source.attr('data-max');
        $('#minMoney').val(min);
        $('#maxMoney').val(max);
        $('#rangeMinMoney').val('');
        $('#rangeMaxMoney').val('');
        target.submit();


    });

    //翻页
    $('.pagination a').first().click(function (event) {
        event.preventDefault();
        var source = $(event.target);
        var target = $('form.search-main');
        var page = $('<input type="hidden" name="page"/>');
        page.val(parseInt(source.attr("data-page"))-1);
        target.append(page);
        target.submit();
    });

    $('.pagination a').last().click(function (event) {
        event.preventDefault();
        var source = $(event.target);
        var target = $('form.search-main');
        var page = $('<input type="hidden" name="page"/>');
        page.val(parseInt(source.attr("data-page"))+1);
        target.append(page);
        target.submit();
    });

    $('.pagination a').slice(1,-1).click(function (event) {
        event.preventDefault();
        var source=$(event.target);
        var target = $('form.search-main');
        var page=$('<input type="hidden" name="page"/>')
        page.val(source.attr("data-page"));
        target.append(page);
        target.submit();
    });

    (function () {
        var min = $('#minMoney').val();
        var max = $('#maxMoney').val();
        var rangeMin = $('#rangeMinMoney').val();
        var rangeMax = $('#rangeMaxMoney').val();

        if (rangeMin == '' && rangeMax == '') {
            if (min != '' && max != '') {
                var temp = $('<li>注册资本：<span>' + min + '万' + '-' + max  + '万' + '</span></li>');
                var x = $('<i>×</i>');
                x.on('click', function () {
                    $('#minMoney').val('')
                    $('#maxMoney').val('')
                    $('form.search-main').submit();
                });
                temp.append(x);
                $('div.search-choose ul').append(temp);
            } else if (min != '' && max == '') {
                var temp = $('<li>注册资本：<span>' + min + '- *</span></li>');
                var x = $('<i>×</i>');
                x.on('click', function () {
                    $('#minMoney').val('')
                    $('#maxMoney').val('')
                    $('form.search-main').submit();
                });
                temp.append(x);
                $('div.search-choose ul').append(temp);
            }
        } else if (rangeMin != null && rangeMax != null) {
            var temp = $('<li>注册资本：<span>' + rangeMin  + '万' + '-' + rangeMax  + '万' + '</span></li>');
            var x = $('<i>×</i>');
            x.on('click', function () {
                $('#rangeMinMoney').val('')
                $('#rangeMaxMoney').val('')
                $('form.search-main').submit();
            });
            temp.append(x);
            $('div.search-choose ul').append(temp);
        }
    }())

    $('#sub_submit').click(function (event) {
        var target = $(event.target);
        var subKeyword = $('#sub_keyWord');
        $('#keyWord').val(subKeyword.val());
        $('form.search-main').submit();
    });
});
