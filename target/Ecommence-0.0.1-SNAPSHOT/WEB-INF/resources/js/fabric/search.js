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
        $('#minWeight').val('');
        $('#maxWeight').val('');
        target.submit();
    });

    $('ul.width a').click(function (event) {
        event.preventDefault();
        var source = $(event.target);
        var target = $('form.search-main');
        var min = source.attr('data-min');
        var max = source.attr('data-max');
        $('#widthRangeMin').val(min);
        $('#widthRangeMax').val(max);
        $('#minWidth').val('');
        $('#maxWidth').val('');
        target.submit();
    });

    $(':radio,:checkbox').change(function () {
        $('form').submit();
    });

    //enter click
    $("#submit-link").keyup(function (event) {
        event.preventDefault();
        var source = $(event.target);
        var input = source.siblings('input');
        var val = input.val();
        if (val != null && val != '') {
            $('#keyWord').val(val);
            $('#search').submit();
        }
    });

    //关键字搜索
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
        var maxWidth = $('#maxWidth').val();
        var minWidth = $('#minWidth').val();
        if (maxWeight != '' || minWeight != '') {
            $('#weightRangeMin').val('');
            $('#weightRangeMax').val('');
        }
        if (maxWidth != '' || minWidth != '') {
            $('#widthRangeMin').val('');
            $('#widthRangeMax').val('');
        }
    });

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


    (function init() {
        //init search tag
        var min = $('#weightRangeMin').val();
        var max = $('#weightRangeMax').val();
        var minWidth = $('#widthRangeMin').val();
        var maxWidth = $('#widthRangeMax').val();

        if (min != '' && max != '') {
            var temp = $('<li>克重：<span>' + min + '-' + max + '</span></li>');
            var x = $('<i>×</i>');
            x.on('click', function () {
                $('#weightRangeMin').val('');
                $('#weightRangeMax').val('');
                $('form.search-main').submit();
            });
            temp.append(x);
            $('div.search-choose ul').append(temp);
        } else if (min != '' && max == '') {
            var temp = $('<li>克重：<span>' + min + '- *</span></li>');
            var x = $('<i>×</i>');
            x.on('click', function () {
                $('#weightRangeMin').val('');
                $('#weightRangeMax').val('');
                $('form.search-main').submit();
            });
            temp.append(x);
            $('div.search-choose ul').append(temp);
        }

        if (minWidth != '' && maxWidth != '') {
            var temp = $('<li>幅宽：<span>' + minWidth + '-' + maxWidth + '</span></li>');
            var x = $('<i>×</i>');
            x.on('click', function () {
                $('#widthRangeMin').val('');
                $('#widthRangeMax').val('');
                $('form.search-main').submit();
            });
            temp.append(x);
            $('div.search-choose ul').append(temp);
        } else if (minWidth != '' && maxWidth == '') {
            var temp = $('<li>幅宽：<span>' + minWidth + '- *</span></li>');
            var x = $('<i>×</i>');
            x.on('click', function () {
                $('#widthRangeMin').val('');
                $('#widthRangeMax').val('');
                $('form.search-main').submit();
            });
            temp.append(x);
            $('div.search-choose ul').append(temp);
        }

        //weight init red background
        var weightRangeMin = $('#weightRangeMin').val();
        var weightRangeMax = $('#weightRangeMax').val();
        var a$ = $('ul.weight li a');
        if (weightRangeMin == 0.0 && weightRangeMax == 90.0) {
            a$.eq(0).css({'color': '#fff'}).parent('li').addClass("active");
        } else if (weightRangeMin == 91.0 && weightRangeMax == 160.0) {
            a$.eq(1).css({'color': '#fff'}).parent('li').addClass("active");
        } else if (weightRangeMin == 161.0 && weightRangeMax == 350.0) {
            a$.eq(2).css({'color': '#fff'}).parent('li').addClass("active");
        } else if (weightRangeMin == 351.0 && weightRangeMax == 500.0) {
            a$.eq(3).css({'color': '#fff'}).parent('li').addClass("active");
        } else if (weightRangeMin == 500.0) {
            a$.eq(4).css({'color': '#fff'}).parent('li').addClass("active");
        }

        //width init red background
        var widthRangeMin = $('#widthRangeMin').val();
        var widthRangeMax = $('#widthRangeMax').val();
        var a$ = $('ul.width li a');
        if (widthRangeMin == 0.0 && widthRangeMax == 120.0) {
            a$.eq(0).css({'color': '#fff'}).parent('li').addClass("active");
        } else if (widthRangeMin == 121.0 && widthRangeMax == 140.0) {
            a$.eq(1).css({'color': '#fff'}).parent('li').addClass("active");
        } else if (widthRangeMin == 141.0 && widthRangeMax == 180.0) {
            a$.eq(2).css({'color': '#fff'}).parent('li').addClass("active");
        } else if (widthRangeMin == 180.0) {
            a$.eq(3).css({'color': '#fff'}).parent('li').addClass("active");
        }
    })();

    //删除选中的项目
    $('ul i').click(function (event) {
        var source = $(event.target);
        $('#' + source.attr('data-target')).prop('checked', false);
        $('form.search-main').submit();
    });

    $('.area').change(function (event) {
        var source = $(event.target);
        var target = $('form.search-main');
        var input$ = $('<input type="hidden" name="area"/>');
        input$.val(source.val());
        target.append(input$);
        target.submit();
    });

    $('ul.pagination.page a').click(function (event) {
        event.preventDefault();
        var source = $(event.target);
        var target = $('form.search-main');
        var page = $('<input type="hidden" name="currentPage"/>');
        page.val(source.attr("data-page"));
        target.append(page);
        target.submit();
    });

    $('#sub_submit').click(function (event) {
        var target = $(event.target);
        var subKeyword = $('#sub_keyWord');
        $('#keyWord').val(subKeyword.val());
        $('form.search-main').submit();
    });

    $('input.input-small').focus(function (event) {
        var target = $(event.target);
        target.parent().parent().addClass('active');
    }).click(function (event) {
        event.stopPropagation();
    });


    $('body').click(function () {
        $('ul.list-inline.weight li').last().removeClass('active');
        $('ul.list-inline.width li').last().removeClass('active');
        $('ul.list-inline.inline-block li').removeClass('active');
    });

    $('#price-search').click(function(){
        var target = $('form.search-main');
        var fakeMin=$('#fakeMinPrice').val();
        var fakeMax=$('#fakeMaxPrice').val();
        $('#minPrice').val(fakeMin);
        $('#maxPrice').val(fakeMax);
        target.submit();
    });
});
