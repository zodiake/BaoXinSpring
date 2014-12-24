/**
 * Created by zodiake on 2014/6/30.
 */
$(function(){
    $('ul.search-search-option').click(function(event){
        var source=$(event.target);
        var index=$(this).indexOf(source);
        if(index==0){
            $('form.search').attr('action','/search/fabric');
        }else
            $('form.search').attr('action','/search/material');
    });
});
