/**
 * Created by Charles on 2014/6/4.
 */
$(function(){
    var hostUrl = "http://"+window.location.host;
    /**
     * 保存广告栏位
     * return id
     */
    $(".saveAdp").click(function(){
        var id =$("#id").val();
        var positionNo = $("#positionNo").val();
        var name = $("#name").val();
        var description = $("#description").val();
        if(positionNo == null || positionNo == "" || name == null || name == ""){
            alert("栏位编号和栏位名称为必填项！");
            return false;
        }
        $("#adpForm").submit();

    });
    /**
     * 查看广告栏位，提供修改的数据
     */
    $(".viewAdp").click(function(){
        var id =$(this).attr("data-id");
        if(id == null || id == ""){
            alert("操作失败，请刷新后重试！");
            return false;
        }else{
            $.ajax({
                method: "GET",
                url: hostUrl+"/admin/advertisementPosition",
                data:{
                    id:id
                },
                success:function(data){
                    if(data.result=="success"){
                        $("#id").val(id);
                        $("#positionNo").val(data.positionNo);
                        $("#name").val(data.name);
                        $("#description").val(data.description);
                    }else if(data.result=="error"){
                        alert("操作失败，请刷新后重试！");
                        return false;
                    }
                }
            });
        }
    });

    /**
     * 删除广告栏位
     */
    $(".deleteAdp").click(function(){
        var id =$(this).attr("data-id");
        if(confirm("是否删除？")){
            if(id == null || id == ""){
                alert("操作失败，请刷新后重试！");
                return false;
            }else{
                $.ajax({
                    method: "GET",
                    url: hostUrl+"/admin/advertisementPosition/delete",
                    data:{
                        id:id
                    },
                    success:function(data){
                        if(data.result=="success"){
                            alert("删除成功！");
                            window.location.href = hostUrl+"/admin/advertisementPositionList";
                        }else{
                            alert("删除失败，请刷新后重试。");
                        }
                    }
                });
            }
        }
    });

    /**
     * 取消按钮，清空输入框内容
     */
    $(".cancelAdp").click(function(){
        $("#id").val("");
        $("#positionNo").val("");
        $("#name").val("");
        $("#description").val("");
    });

    /************************************************广告信息设置************************************
     * 编辑广告
     */
    $(".editAd").click(function(){
        var id =$(this).attr("data-id");
        if(id == null || id == ""){
            alert("操作失败，请刷新后重试！");
            return false;
        }else{
            window.location.href =  hostUrl+"/admin/advertisement/edit/"+id;
        }
    });

    /**
     * 删除广告
     */
    $(".deleteAd").click(function(){
        var id =$(this).attr("data-id");
        if(confirm("是否删除？")){
            if(id == null || id == ""){
                alert("操作失败，请刷新后重试！");
                return false;
            }else{
                $.ajax({
                    method: "GET",
                    url: hostUrl+"/admin/advertisement/delete",
                    data:{
                        id:id
                    },
                    success:function(data){
                        if(data.result=="success"){
                            alert("删除成功！");
                            window.location.href = hostUrl+"/admin/advertisementList";
                        }else{
                            alert("删除失败，请刷新后重试。");
                        }
                    }
                });
            }
        }
    });

    /***************************************************资讯分类设置*************************************************
     * 资讯分类保存
     * return id
     */
    $(".saveInfoCate").click(function(){
        var id =$("#id").val();
        var categoryName = $("#categoryName").val();
        var description = $("#description").val();
        var isValid = $("#isValid").val();
        if(categoryName == null || categoryName == ""){
            alert("请填写分类名称！");
            return false;
        }
        $("#infoCateForm").submit();
    });
    /**
     * 查看资讯分类，提供修改的数据
     */
    $(".editInfoCate").click(function(){
        var id =$(this).attr("data-id");
        if(id == null || id == ""){
            alert("操作失败，请刷新后重试！");
            return false;
        }else{
            $.ajax({
                method: "GET",
                url: hostUrl+"/admin/informationCategory/view",
                data:{
                    id:id
                },
                success:function(data){
                    if(data.result=="success"){
                        $("#id").val(id);
                        $("#categoryName").val(data.categoryName);
                        $("#description").val(data.description);
                        $("#isValid").val(data.isValid);
                    }else if(data.result=="error"){
                        alert("操作失败，请刷新后重试！");
                        return false;
                    }
                }
            });
        }
    });

    /**
     * 删除资讯分类
     */
    $(".deleteInfoCate").click(function(){
        var id =$(this).attr("data-id");
        if(confirm("是否删除？")){
            if(id == null || id == ""){
                alert("操作失败，请刷新后重试！");
                return false;
            }else{
                $.ajax({
                    method: "GET",
                    url: hostUrl+"/admin/informationCategory/delete",
                    data:{
                        id:id
                    },
                    success:function(data){
                        if(data.result=="success"){
                            alert("删除成功！");
                            window.location.href = hostUrl+"/admin/informationCategoryList";
                        }else{
                            alert("操作失败，请刷新后重试！");
                        }
                    }
                });
            }
        }
    });

    /**
     * 取消按钮，清空输入框内容
     */
    $(".cancelInfoCate").click(function(){
        $("#id").val("");
        $("#categoryName").val("");
    });

    /************************************************资讯内容设置*************************************
     * 编辑广告
     */
    $(".editInfo").click(function(){
        var id =$(this).attr("data-id");
        if(id == null || id == ""){
            alert("操作失败，请刷新后重试！");
            return false;
        }else{
            window.location.href = hostUrl+"/admin/information/edit/"+id;
        }
    });

    /**
     * 删除广告
     */
    $(".deleteInfo").click(function(){
        var id =$(this).attr("data-id");
        if(confirm("是否删除？")){
            if(id == null || id == ""){
                alert("操作失败，请刷新后重试！");
                return false;
            }else{
                $.ajax({
                    method: "POST",
                    url: hostUrl+"/admin/information/delete",
                    data:{
                        id:id
                    },
                    success:function(data){
                        if(data.result=="success"){
                            alert("删除成功！");
                            window.location.href = hostUrl+"/admin/informationList";
                        }else{
                            alert("操作失败，请刷新后重试！");
                        }
                    }
                });
            }
        }
    });

    $("#cancelBtn").click(function(){
        window.history.back();
    });

    $('#fileToUpload').change(function (event) {
        var source = $(event.target);
        var id = source.attr('id');
        $("#preViewPic").removeAttr("style");
        $.ajaxFileUpload({
                url: hostUrl + '/addImage/upload',
                secureuri: false,
                fileElementId: source.attr('id'),
                dataType: 'json',
                success: function (data, status) {
                    var result = data.responseText;
                    if(result == "fail"){
                        alert("图片上传失败，请重试!");
                        return false;
                    }else{
                        $("#preViewPic").attr('src', result);
                        $('#coverPath').val(result);
                    }
                },
                error: function (data, status, e) {
                    alert(e);
                }
            }
        );
        return false;
    });

    $('#viewPathToUpload').change(function (event) {
        var source = $(event.target);
        var id = source.attr('id');
        $("#preViewPic").removeAttr("style");
        $.ajaxFileUpload({
                url: hostUrl + '/infoImage/upload',
                secureuri: false,
                fileElementId: source.attr('id'),
                dataType: 'json',
                success: function (data, status) {
                    var result = data.responseText;
                    if(result == "fail"){
                        alert("图片上传失败，请重试!");
                        return false;
                    }else{
                        $("#preViewPic").attr('src', result);
                        $('#viewPath').val(result);
                    }
                },
                error: function (data, status, e) {
                    alert(e);
                }
            }
        );
        return false;
    });

});
