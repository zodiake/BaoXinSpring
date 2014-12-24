/**
 * Created by Charles on 2014/6/6.
 */
$(function(){
    var hostUrl = "http://"+window.location.host;
    /**
     * 编辑一级分类
     */
    $(".editFirst").click(function(){
        var id =$(this).attr("data-id");
        if(id == null || id == ""){
            console.log("doesn't find id in this page!");
            return false;
        }else{
            window.location.href =  hostUrl+"/admin/materialFirstCategory/edit/"+id;
        }
    });

    /**
     * 编辑二级分类
     */
    $(".editSecond").click(function(){
        var id =$(this).attr("data-id");
        if(id == null || id == ""){
            console.log("doesn't find id in this page!");
            return false;
        }else{
            window.location.href =  hostUrl+"/admin/materialSecondCategory/edit/"+id;
        }
    });

    $("#cancelBtn").click(function(){
        window.history.back();
    });
})