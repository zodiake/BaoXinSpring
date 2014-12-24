/**
 * Created by zodiake on 2014/6/27.
 */
$(function () {
    $('ul.weight a').click(function (event) {
        event.preventDefault();
        var source = $(event.target);
        var min = source.attr('data-min');
        var max = source.attr('data-max');
        var target = $('form.search-main');
        $('#weightRangeMin').val(min);
        $('#weightRangeMax').val(max);
        $('#weightMin').val('');
        $('#weightMax').val('');
        target.submit();
    });

    /*
     $('#submit-link').click(function (event) {
     event.preventDefault();
     var source = $(event.target);
     var input = source.siblings('input');
     var val = input.val();
     if (val != null && val != '') {
     $('#keyWord').val(val);
     $('#search').submit();
     }
     });
     */

    $('.area').change(function (event) {
        var source = $(event.target);
        var target = $('form.search-main');
        var input$ = $('<input type="hidden" name="area"/>')
        input$.val(source.val());
        target.append(input$);
        target.submit();
    });
    //项目选中则直接提交表单
    $(':radio,:checkbox').change(function () {
        $('form').submit();
    });

    $('form.search').submit(function (event) {
        event.preventDefault();
        var target = $('form.search-main');
        var input = $(this).find("input[type='text']");
        var val = input.val();
        if (val != null && val != '')
            $('#keyWord').val(input.val());
        target.submit();
    });

    //排序
    $('div.search-result-panel li a').click(function (event) {
        event.preventDefault();
        var source = $(event.target);
        var target = $('form.search-main');
        var input = $('#sort', target).val(source.attr('data-sort'));
        target.submit();
    });

    //翻页
    $('ul.pagination.page a').first().click(function (event) {
        event.preventDefault();
        var source = $(event.target);
        var target = $('form.search-main');
        var page = $('<input type="hidden" name="currentPage"/>')
        page.val(parseInt(source.attr("data-page")) - 1);
        target.append(page);
        target.submit();
    });

    $('ul.pagination.page a').last().click(function (event) {
        event.preventDefault();
        var source = $(event.target);
        var target = $('form.search-main');
        var page = $('<input type="hidden" name="currentPage"/>')
        page.val(parseInt(source.attr("data-page")) + 1);
        target.append(page);
        target.submit();
    });

    $('ul.pagination.page a').slice(1, -1).click(function (event) {
        event.preventDefault();
        var source = $(event.target);
        var target = $('form.search-main');
        var page = $('<input type="hidden" name="currentPage"/>')
        page.val(source.attr("data-page"));
        target.append(page);
        target.submit();
    });

    $('div.search-result-panel li a').click(function (event) {
        event.preventDefault();
        var source = $(event.target);
        var target = $('form.search-main');
        var input = $('#sort', target).val(source.attr('data-sort'));
        target.submit();
    });

    $('form.search-main').submit(function (event) {
        var maxWeight = $('#maxWeight').val();
        var minWeight = $('#minWeight').val();
        if (maxWeight != '' || minWeight != '') {
            $('#weightRangeMin').val('');
            $('#weightRangeMax').val('');
        }
    });

    //将用户选中的checkbox 展现出来
    $(':checked').each(function (n, target) {
        var source = $(target);
        var template = $('<li/>');
        if (source.attr('data-label') != undefined) {
            template.text(source.attr('data-label') + "：");
            template.append('<span>' + source.siblings('label').text() + '</span>');
            template.append('<i data-target="' + source.attr('id') + '">×</i>');
            $('div.search-choose ul').append(template);
        }
        source.parent('li').addClass('active');
    });


    //删除选中的项目
    $('ul i').click(function (event) {
        var source = $(event.target);
        $('#' + source.attr('data-target')).prop('checked', false);
        $('form.search-main').submit();
    });

    $('#sub_submit').click(function (event) {
        var target = $(event.target);
        var subKeyword = $('#sub_keyWord');
        $('#keyWord').val(subKeyword.val());
        $('form.search-main').submit();
    });

    $('#price-search').click(function () {
        var target = $('form.search-main');
        var fakeMin = $('#fakeMinPrice').val();
        var fakeMax = $('#fakeMaxPrice').val();
        $('#minPrice').val(fakeMin);
        $('#maxPrice').val(fakeMax);
        target.submit();
    });

    $('input.w-60').focus(function (event) {
        var target = $(event.target);
        target.parent().parent().addClass('active');
    }).click(function (event) {
        event.stopPropagation();
    });

    $('body').click(function () {
        $('ul.list-inline.inline-block li').removeClass('active');
    });
});
