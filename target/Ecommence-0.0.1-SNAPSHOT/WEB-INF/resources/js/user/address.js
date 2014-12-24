/**
 * Created by Charles on 2014/6/30.
 */
$(function(){
    var hostUrl = "http://" + window.location.host;
    $(".submitAddress").click(function(){
        var receiverName = $("#receiverName");
        var state = $("#state");
        var city = $("#city");
        var street = $("#street");
        var zipCode = $("#zipCode");
        var mobile = $("#mobile");
        var zipPhone = $("#zipPhone");
        var phone = $("#phone");
        var childPhone = $("#childPhone");
        var defaultAddress = $("#defaultAdd");
        var addressId = $("#newAddressId").val();
        if(state.val() == "" || state.val() == null || state.val() == "请选择"){
            receiverName.nextAll(".msg-error").html("<i class='error-icon'></i>请选择省份");
            receiverName.nextAll(".msg-error").removeClass("Hide");
            return false;
        }
        if(city.val() == "" || city.val() == null || city.val() == "请选择"){
            receiverName.nextAll(".msg-error").html("<i class='error-icon'></i>请选择城市");
            receiverName.nextAll(".msg-error").removeClass("Hide");
            return false;
        }
        if(receiverName.val() == "" || receiverName.val() == null || CheckLength(receiverName.val())>20){
            alert("请正确填写收货人名称");
            return false;
        }
        if(street.val() == "" || street.val() == null || CheckLength(street.val())>100){
            alert("请正确填写详细地址");
            return false;
        }
        if(zipCode.val() == "" || zipCode.val() == null || CheckLength(zipCode.val())>10 || !/^[0-9]*$/.test(zipCode.val())){
            alert("请正确填写邮政编码");
            return false;
        }
        if(mobile.val() =="" && zipPhone.val() == "" && phone.val() ==""){
            alert("手机号和固定电话至少填写一项");
            return false;
        }
        if(CheckLength(mobile.val())!=11 || !/^[0-9]*$/.test(mobile.val())){
            alert("请正确填写手机号码");
            return false;
        }
        if(CheckLength(zipPhone.val())>4 || !/^[0-9]*$/.test(zipPhone.val())){
            alert("请正确填写电话号码");
            return false;
        }
        if(CheckLength(phone.val())>8 || !/^[0-9]*$/.test(phone.val())){
            alert("请正确填写电话号码");
            return false;
        }

        $.ajax({
            method: "POST",
            url: hostUrl+"/buyerCenter/saveAddress",
            data: {
                addressId : addressId,
                receiverName:receiverName.val(),
                state:state.val(),
                city:city.val(),
                street:street.val(),
                zipCode:zipCode.val(),
                mobile:mobile.val(),
                zipPhone:zipPhone.val(),
                phone:phone.val(),
                childPhone:childPhone.val(),
                defaultAddress:defaultAddress.val()
            },
            success: function (data) {
                $('.new-address').css('display','none');
                $('.fixed').css('display','none');
                $(".address-list").html(data);
                $("#newAddressId").val("");
                receiverName.val("");
                state.val("");
                city.val("");
                street.val("");
                zipCode.val("");
                mobile.val("");
                zipPhone.val("");
                phone.val("");
                childPhone.val("");
                defaultAddress.val("0");
                defaultAddress.removeAttr("checked");
            }
        });
    });

    $(".addressSubmit").click(function(){
        var receiverName = $("#receiverName");
        var state = $("#state");
        var city = $("#city");
        var street = $("#street");
        var zipCode = $("#zipCode");
        var mobile = $("#mobile");
        var zipPhone = $("#zipPhone");
        var phone = $("#phone");
        var childPhone = $("#childPhone");
        var defaultAddress = $("#defaultAddress1");
        var addressId = $("#id");
        if(state.val() == "" || state.val() == null || state.val() == "请选择"){
            state.nextAll(".msg-error").html("<i class='error-icon'></i>请选择省份");
            state.nextAll(".msg-error").removeClass("Hide");
            return false;
        }
        if(city.val() == "" || city.val() == null || city.val() == "请选择"){
            city.nextAll(".msg-error").html("<i class='error-icon'></i>请选择城市");
            city.nextAll(".msg-error").removeClass("Hide");
            return false;
        }
        if(receiverName.val() == "" || receiverName.val() == null || CheckLength(receiverName.val())>20){
            receiverName.nextAll(".msg-error").html("<i class='error-icon'></i>请正确填写收货人名称");
            receiverName.nextAll(".msg-error").removeClass("Hide");
            return false;
        }
        if(street.val() == "" || street.val() == null || CheckLength(street.val())>100){
            street.nextAll(".msg-error").html("<i class='error-icon'></i>请正确填写详细地址");
            street.nextAll(".msg-error").removeClass("Hide");
            return false;
        }
        if(zipCode.val() == "" || zipCode.val() == null || CheckLength(zipCode.val())>10 || !/^[0-9]*$/.test(zipCode.val())){
            zipCode.nextAll(".msg-error").html("<i class='error-icon'></i>请正确填写邮政编码");
            zipCode.nextAll(".msg-error").removeClass("Hide");
            return false;
        }
        if((mobile.val()) =="" && zipPhone.val() == "" && phone.val() ==""){
            mobile.nextAll(".msg-error").html("<i class='error-icon'></i>手机号和固定电话至少填写一项");
            mobile.nextAll(".msg-error").removeClass("Hide");
            return false;
        }
        if(mobile.val() !="" || (zipPhone.val() != "" && phone.val() !="")){
            if((CheckLength(mobile.val()) !=11 || !/^[0-9]*$/.test(mobile.val())) && zipPhone.val() == "" && phone.val() ==""){
                mobile.nextAll(".msg-error").html("<i class='error-icon'></i>请正确填写手机号码");
                mobile.nextAll(".msg-error").removeClass("Hide");
                return false;
            }
            if( (CheckLength(zipPhone.val())>4 || !/^[0-9]*$/.test(zipPhone.val())) && mobile.val() ==""){
                zipPhone.nextAll(".msg-error").html("<i class='error-icon'></i>请正确填写电话号码");
                zipPhone.nextAll(".msg-error").removeClass("Hide");
                return false;
            }
            if((CheckLength(phone.val())>8 || !/^[0-9]*$/.test(phone.val())) && mobile.val() ==""){
                phone.nextAll(".msg-error").html("<i class='error-icon'></i>请正确填写电话号码");
                phone.nextAll(".msg-error").removeClass("Hide");
                return false;
            }
            if(CheckLength(childPhone.val())>4 || !/^[0-9]*$/.test(childPhone.val())){
                childPhone.nextAll(".msg-error").html("<i class='error-icon'></i>请正确填写电话号码");
                childPhone.nextAll(".msg-error").removeClass("Hide");
                return false;
            }
        }
            $("#address").submit();
    });

    $("#defaultAddress1").change(function(){
        if($(this).val()==0){
            $(this).attr("checked","checked");
            $(this).val("1");
        }else if($(this).val()==1){
            $(this).val("0");
            $(this).removeAttr("checked");
        }
    });

    $(".addInfoVali").blur(function(){
        var target = $(this);
        if(target.val() == "" || target.val() == null){
            target.nextAll(".msg-error").removeClass("Hide");
        }else{
            target.nextAll(".msg-error").addClass("Hide");
        }
    });
    $(".deleteAddress").click(function () {
        var addId = $(this).attr("data-id");
        if(confirm("是否删除此地址？")){
            $.ajax({
                method: "GET",
                url: hostUrl + "/buyerCenter/address/delete/"+addId,
                success: function (data) {
                    $(".tbl-deliver-address").html(data);
                }
            });
        }
    });

    //订单确认页面设置默认地址
    $('.defaultAddress').click(function () {
        var addressId = $(this).attr("data-id");
        if (addressId == null || addressId == "") {
            return false;
        } else {
            $.ajax({
                url: hostUrl + "/buyerCenter/defaultAddress/"+addressId,
                method: 'get',
                success: function (data) {
                    $(".tbl-deliver-address").html(data);
                }
            });
        }
    });
});

function CheckLength(strTemp) {
    var i,sum;
    sum=0;
    for(i=0;i<strTemp.length;i++) {
        if ((strTemp.charCodeAt(i)>=0) && (strTemp.charCodeAt(i)<=255)) {
            sum=sum+1;
        }else {
            sum=sum+2;
        }
    }
    return sum;
}