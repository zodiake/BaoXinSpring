/**
 * Created by zodiake on 2014/6/9.
 */
$(function () {
    $('.assessment-act ul li span').mousemove(function(e){
        <!-- span对象 -->
        var o = $(this);
        o.removeClass();
        o.addClass('icon-stars');
        var s='icon-stars-';
        /* 算法思路：先计算元素绝对坐标，然后计算元素相对坐标，两者相减获得鼠标在元素中坐标，
        最后在除以16，取得鼠标具体在哪颗星上 */
        var x = e.clientX;
        var x1= $('.assessment-act ul li label').position().left + 120;
        var len = x - x1;
        var i=0;
        i=Math.round(len / 16);
        s = s + i;
        o.addClass(s);
        <!-- input对象 -->
        var str = o.prev();
        <!-- 修改input value值 -->
        str.attr('value',i);
    });

    $('.commentButton').change(function(){

        var t = $('#hasName');
        var hasName =$('input:checkbox:checked').val();
        console.log(hasName)
        if(hasName!=undefined){
            t.val(1);
        }else{
            t.val(0);
        }
    });

});
