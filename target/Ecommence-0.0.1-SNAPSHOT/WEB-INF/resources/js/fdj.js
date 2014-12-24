(function($){
    $(function(){



        //放大镜
        var zoomer = function(){

            $(".jqzoom").jqueryzoom({
                xzoom: 332, //放大图的宽度(默认是 200)
                yzoom: 347, //放大图的高度(默认是 200)
                offset: 10, //离原图的距离(默认是 10)
                position: 'right', //放大图的定位(默认是 "right")
                preload: 1
            });
        };

        var picTab = function(){
            var list = $('#J_picList'),
                lis = list.find('li'),
                large = $('#J_large'),
                img = large.find('img');

            list.delegate('li', 'click', function(e){
                e.stopPropagation();

                var li = $(this),
                    pic = li.find('img'),
                    url = li.attr('data-url');

                lis.removeClass('active');
                li.addClass('active');
                large.attr('href', url);
                img.attr({
                    'src': pic.attr('src'),
                    'jqimg': url
                });

                large.find('.zoomWrapperImage img').attr('src', url);
            });
        };




        zoomer();

        picTab();

    });
})(jQuery);