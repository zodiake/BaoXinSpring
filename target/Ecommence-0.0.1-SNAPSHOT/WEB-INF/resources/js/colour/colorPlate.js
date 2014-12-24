//函数总入口
var colourDiv;
var tinctCodeSelect;


function fake(obj, obj1, obj2) {
    tinctCodeSelect = obj;
    queryTinctCode(obj);
    queryColour(obj1);
    colourDiv = obj1;
    //colourSlider();
    //showButton();
}

var hexarray = new Array(256);
hexarray[0] = "00";
hexarray[1] = "01";
hexarray[2] = "02";
hexarray[3] = "03";
hexarray[4] = "04";
hexarray[5] = "05";
hexarray[6] = "06";
hexarray[7] = "07";
hexarray[8] = "08";
hexarray[9] = "09";
hexarray[10] = "0A";
hexarray[11] = "0B";
hexarray[12] = "0C";
hexarray[13] = "0D";
hexarray[14] = "0E";
hexarray[15] = "0F";
hexarray[16] = "10";
hexarray[17] = "11";
hexarray[18] = "12";
hexarray[19] = "13";
hexarray[20] = "14";
hexarray[21] = "15";
hexarray[22] = "16";
hexarray[23] = "17";
hexarray[24] = "18";
hexarray[25] = "19";
hexarray[26] = "1A";
hexarray[27] = "1B";
hexarray[28] = "1C";
hexarray[29] = "1D";
hexarray[30] = "1E";
hexarray[31] = "1F";
hexarray[32] = "20";
hexarray[33] = "21";
hexarray[34] = "22";
hexarray[35] = "23";
hexarray[36] = "24";
hexarray[37] = "25";
hexarray[38] = "26";
hexarray[39] = "27";
hexarray[40] = "28";
hexarray[41] = "29";
hexarray[42] = "2A";
hexarray[43] = "2B";
hexarray[44] = "2C";
hexarray[45] = "2D";
hexarray[46] = "2E";
hexarray[47] = "2F";
hexarray[48] = "30";
hexarray[49] = "31";
hexarray[50] = "32";
hexarray[51] = "33";
hexarray[52] = "34";
hexarray[53] = "35";
hexarray[54] = "36";
hexarray[55] = "37";
hexarray[56] = "38";
hexarray[57] = "39";
hexarray[58] = "3A";
hexarray[59] = "3B";
hexarray[60] = "3C";
hexarray[61] = "3D";
hexarray[62] = "3E";
hexarray[63] = "3F";
hexarray[64] = "40";
hexarray[65] = "41";
hexarray[66] = "42";
hexarray[67] = "43";
hexarray[68] = "44";
hexarray[69] = "45";
hexarray[70] = "46";
hexarray[71] = "47";
hexarray[72] = "48";
hexarray[73] = "49";
hexarray[74] = "4A";
hexarray[75] = "4B";
hexarray[76] = "4C";
hexarray[77] = "4D";
hexarray[78] = "4E";
hexarray[79] = "4F";
hexarray[80] = "50";
hexarray[81] = "51";
hexarray[82] = "52";
hexarray[83] = "53";
hexarray[84] = "54";
hexarray[85] = "55";
hexarray[86] = "56";
hexarray[87] = "57";
hexarray[88] = "58";
hexarray[89] = "59";
hexarray[90] = "5A";
hexarray[91] = "5B";
hexarray[92] = "5C";
hexarray[93] = "5D";
hexarray[94] = "5E";
hexarray[95] = "6F";
hexarray[96] = "60";
hexarray[97] = "61";
hexarray[98] = "62";
hexarray[99] = "63";
hexarray[100] = "64";
hexarray[101] = "65";
hexarray[102] = "66";
hexarray[103] = "67";
hexarray[104] = "68";
hexarray[105] = "69";
hexarray[106] = "6A";
hexarray[107] = "6B";
hexarray[108] = "6C";
hexarray[109] = "6D";
hexarray[110] = "6E";
hexarray[111] = "6F";
hexarray[112] = "70";
hexarray[113] = "71";
hexarray[114] = "72";
hexarray[115] = "73";
hexarray[116] = "74";
hexarray[117] = "75";
hexarray[118] = "76";
hexarray[119] = "77";
hexarray[120] = "78";
hexarray[121] = "79";
hexarray[122] = "7A";
hexarray[123] = "7B";
hexarray[124] = "7C";
hexarray[125] = "7D";
hexarray[126] = "7E";
hexarray[127] = "7F";
hexarray[128] = "80";
hexarray[129] = "81";
hexarray[130] = "82";
hexarray[131] = "83";
hexarray[132] = "84";
hexarray[133] = "85";
hexarray[134] = "86";
hexarray[135] = "87";
hexarray[136] = "88";
hexarray[137] = "89";
hexarray[138] = "8A";
hexarray[139] = "8B";
hexarray[140] = "8C";
hexarray[141] = "8D";
hexarray[142] = "8E";
hexarray[143] = "8F";
hexarray[144] = "90";
hexarray[145] = "91";
hexarray[146] = "92";
hexarray[147] = "93";
hexarray[148] = "94";
hexarray[149] = "95";
hexarray[150] = "96";
hexarray[151] = "97";
hexarray[152] = "98";
hexarray[153] = "99";
hexarray[154] = "9A";
hexarray[155] = "9B";
hexarray[156] = "9C";
hexarray[157] = "9D";
hexarray[158] = "9E";
hexarray[159] = "9F";
hexarray[160] = "A0";
hexarray[161] = "A1";
hexarray[162] = "A2";
hexarray[163] = "A3";
hexarray[164] = "A4";
hexarray[165] = "A5";
hexarray[166] = "A6";
hexarray[167] = "A7";
hexarray[168] = "A8";
hexarray[169] = "A9";
hexarray[170] = "AA";
hexarray[171] = "AB";
hexarray[172] = "AC";
hexarray[173] = "AD";
hexarray[174] = "AE";
hexarray[175] = "AF";
hexarray[176] = "B0";
hexarray[177] = "B1";
hexarray[178] = "B2";
hexarray[179] = "B3";
hexarray[180] = "B4";
hexarray[181] = "B5";
hexarray[182] = "B6";
hexarray[183] = "B7";
hexarray[184] = "B8";
hexarray[185] = "B9";
hexarray[186] = "BA";
hexarray[187] = "BB";
hexarray[188] = "BC";
hexarray[189] = "BD";
hexarray[190] = "BE";
hexarray[191] = "BF";
hexarray[192] = "C0";
hexarray[193] = "C1";
hexarray[194] = "C2";
hexarray[195] = "C3";
hexarray[196] = "C4";
hexarray[197] = "C5";
hexarray[198] = "C6";
hexarray[199] = "C7";
hexarray[200] = "C8";
hexarray[201] = "C9";
hexarray[202] = "CA";
hexarray[203] = "CB";
hexarray[204] = "CC";
hexarray[205] = "CD";
hexarray[206] = "CE";
hexarray[207] = "CF";
hexarray[208] = "D0";
hexarray[209] = "D1";
hexarray[210] = "D2";
hexarray[211] = "D3";
hexarray[212] = "D4";
hexarray[213] = "D5";
hexarray[214] = "D6";
hexarray[215] = "D7";
hexarray[216] = "D8";
hexarray[217] = "D9";
hexarray[218] = "DA";
hexarray[219] = "DB";
hexarray[220] = "DC";
hexarray[221] = "DD";
hexarray[222] = "DE";
hexarray[223] = "DF";
hexarray[224] = "E0";
hexarray[225] = "E1";
hexarray[226] = "E2";
hexarray[227] = "E3";
hexarray[228] = "E4";
hexarray[229] = "E5";
hexarray[230] = "E6";
hexarray[231] = "E7";
hexarray[232] = "E8";
hexarray[233] = "E9";
hexarray[234] = "EA";
hexarray[235] = "EB";
hexarray[236] = "EC";
hexarray[237] = "ED";
hexarray[238] = "EE";
hexarray[239] = "EF";
hexarray[240] = "F0";
hexarray[241] = "F1";
hexarray[242] = "F2";
hexarray[243] = "F3";
hexarray[244] = "F4";
hexarray[245] = "F5";
hexarray[246] = "F6";
hexarray[247] = "F7";
hexarray[248] = "F8";
hexarray[249] = "F9";
hexarray[250] = "FA";
hexarray[251] = "FB";
hexarray[252] = "FC";
hexarray[253] = "FD";
hexarray[254] = "FE";
hexarray[255] = "FF";

var colorHttp = "http://www.cloudfashion.org/buap";
//var colorHttp = "http://10.70.82.33:9080/buap";

//选中颜色
$(function () {
    $('#wrap').on('dblclick', 'li', function (event) {
        var target = $(event.target);
        var title = target.attr('title');
        var color = target.css('background');
        var label$ = $('label.color_label');
        var clone = $('<div></div>');
        var colors=$('.qr-position-relative div[data-title]');
        if(colors.size()>9){
            alert("只能选择10种颜色");
            return;
        }
        clone.attr('data-title', title);
        clone.attr('data-back', color);
        clone.addClass('color-content');
        clone.css({
            "height": 30,
            "width": 120,
            "margin-right": 10,
            "float": "left",
            "background-color":color
        });
        clone.append("<div class='color-title'>" + title + "</div>");
        var i$ = $("<i class='color-del'></i>");
        clone.append(i$);
        label$.after(clone);
        i$.click(function (event) {
            event.stopPropagation();
            var parent = i$.parent('div');
            var title = parent.attr('data-title');
            parent.remove();
            $('input[value="' + title + '"]').remove();
        });
        var checkbox = $('<input type="hidden" name="colors" value="' + title + '">');
        $('#mainForm').append(checkbox)
    });

    $('i.color-del').click(function(event){
        event.stopPropagation();
        var self=$(event.target);
        var parent = self.parent('div');
        var title = parent.attr('data-title');
        parent.remove();
        $('input[value="' + title + '"]').remove();
    });

    $("#container").droppable({
        drop: function (event, ui) {
            var r = ui.draggable.context.style.backgroundColor;
            var title = ui.draggable.context.title;
            var color = showRGB(r);
            $(this).append($("<div class='color-content' data-title='" + title + "' data-back='" + color + "' style='height:30px;width:30px;margin-right:10px;float:left;background:" + color + "'></div>"));
            var checkbox = $('<input type="hidden" name="colors" value="' + title + '">');
            $('#mainForm').append(checkbox)
        }
    });

});

//RGB十六进制转换
function showRGB(obj) {
    if (obj.indexOf("#") >= 0) {
        return obj;
    } else {
        var RGB = obj.replace("rgb", "").replace("(", "").replace(")", "").split(",");
        var hexcode = "#" + hexarray[RGB[0]] + hexarray[RGB[1].trim()] + hexarray[RGB[2].trim()];
        return hexcode;
    }
}

//滑动条事件
function colourSlider() {
    $("#slider").slider({
        min: 1,
        max: 3687,
        range: "min",
        slide: function (event, ui) {
            $("#amount").val(ui.value);
        },
        change: function (event, ui) {
            var json = '{';
            json += '"rowNumber" : "' + ui.value + '"' + '}';
            $.ajax({
                url: colorHttp + "/restservice/getColourByRing",
                data: { data: json },
                type: "get",
                cache: false,
                dataType: "jsonp",
                jsonp: "callbackparam",
                jsonpCallback: "jsonpCallbackSlider",
                timeout: 50000,
                success: function (data) {
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    alert(errorThrown);
                }
            });
        }
    });
}

function jsonpCallbackSlider(data) {
    $("#" + colourDiv).empty();
    var $tr = "";
    for (i = 0; i < data.length; i++) {
        $tr = '<li><div class="boxCont" id="divslider' + i + '" style="height:20px;width:20px;background:#' + hexarray[data[i].colourSr] + hexarray[data[i].colourSg] + hexarray[data[i].colourSb] + '" title="' + data[i].colourCode + '" ></div></li>';
        $("#" + colourDiv).append($tr);
        loadEvent("divslider" + i);
    }
}

//初始化加载色板
function queryColour(obj) {
    var json = '{}';
    $.ajax({
        type: "get",
        async: false,
        url: colorHttp + "/restservice/getColourByTinctCode",
        data: { data: json },
        cache: false,
        dataType: "jsonp",
        jsonp: "callbackparam",
        jsonpCallback: "jsonpCallback",
        success: function (data) {
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert(errorThrown);
        }
    });

}


//加载div拖动事件
function loadEvent(id) {
    $("#" + id).draggable({
        helper: "clone",
        revert: "invalid",
        opacity: 0.5
    });
}

//初始化色系下拉框
function queryTinctCode(obj) {
    var json = '{}';
    $.ajax({
        url: colorHttp + "/restservice/getTinct",
        data: { data: json },
        type: "get",
        cache: false,
        dataType: "jsonp",
        jsonp: "callbackparam",
        jsonpCallback: "jsonpCallbackSelect",
        timeout: 50000,
        success: function (data) {
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert(errorThrown);
        }
    });
}

function jsonpCallbackSelect(data) {
    var $tr = "<font size='2px'>色系：</font> <select id='tinctCode' name='tinctCode' style='width:80px' onChange='tinctChange(this)'>";
    $tr += "<option value=''>请选择</option>";
    for (i = 0; i < data.length; i++) {
        $tr += '<option value=' + data[i].tinctCode + '>' + data[i].tinctDesc + '</option>';
    }
    $tr += "</select>";
    $tr += "<font size='2px'>代码：</font><input type='text' id='cs2' name='cs2' size='18'/><br/><br/>";
    var cs2=$("<input type='text' id='cs2' name='cs2' size='18'/>");
    //$tr.append(cs2);
    $("#" + tinctCodeSelect).append($tr);

    $('#cs2').keyup(function(event){
        var self=$(this);

        var cs2=self.val();
            var json = '{';
            json += '"tinctGuid" : "' + tinctCode + '",';
            json += '"cs2" :"' + cs2 + '"' + '}';
            $.ajax({
                url: colorHttp + "/restservice/getColourByTinctCode",
                data: { data: json },
                type: "get",
                cache: false,
                dataType: "jsonp",
                jsonp: "callbackparam",
                jsonpCallback: "jsonpCallbackCs2",
                timeout:50000,
                success: function (data) {
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    alert(errorThrown);
                }
            });
    });
}

//显示确定按钮
function showButton() {
    var $tr = '<table width="40%"><tr><td align="center"><input type="button" value="确定" style="width:50px;height:20px" onClick="returnValue();"/></td></tr></table>'
    $("#buttonDiv").append($tr);
}

//获取选中区域的值
function returnValue() {
    var box = document.getElementById("container").getElementsByTagName("div");
    var context = "";
    for (var i = 0; i < box.length; i++) {
        var p = box[i].getElementsByTagName("p");
        if (p.length == 1) {
            alert(p[0].innerHTML);
        }
    }
}

//色系下拉框事件
var tinctCode = '';

function tinctChange(obj) {
    tinctCode = obj.value;
    var json = '{';
    json += '"tinctGuid" : "' + tinctCode + '"' + '}';
    $.ajax({
        url: colorHttp + "/restservice/getColourByTinctCode",
        data: { data: json },
        type: "get",
        cache: false,
        dataType: "jsonp",
        jsonp: "callbackparam",
        jsonpCallback: "jsonpCallbackCode",
        timeout: 50000,
        success: function (data) {
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert(errorThrown);
        }
    });
}

function jsonpCallbackCode(data) {
    $("#" + colourDiv).empty();
    var $tr = "";
    for (var i = 0; i < data.length; i++) {
        $tr = '<li><div class="boxCont" id="divtinct' + i + '" style="height:20px;width:20px;background:#' + hexarray[data[i].colourSr] + hexarray[data[i].colourSg] + hexarray[data[i].colourSb] + '" title="' + data[i].colourCode + '" ></div></li>';
        $("#" + colourDiv).append($tr);
        loadEvent("divtinct" + i);
    }
}

//代码回车事件
document.onkeydown = function (event) {
    var e = event || window.event || arguments.callee.caller.arguments[0];
    if (e && e.keyCode == 13) {
        var cs2 = document.getElementById("cs2").value;
        var json = '{';
        json += '"tinctGuid" : "' + tinctCode + '",';
        json += '"cs2" :"' + cs2 + '"' + '}';
        $.ajax({
            url: colorHttp + "/restservice/getColourByTinctCode",
            data: { data: json },
            type: "get",
            cache: false,
            dataType: "jsonp",
            jsonp: "callbackparam",
            jsonpCallback: "jsonpCallbackCs2",
            timeout: 3000,
            success: function (data) {
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert(errorThrown);
            }
        });
    }
};

function jsonpCallbackCs2(data) {
    $("#" + colourDiv).empty();
    var $tr = "";
    console.log(data.length);
    for (var i = 0; i < data.length; i++) {
        $tr = '<li><div class="boxCont" id="divcs2' + i + '" style="height:20px;width:20px;background:#' + hexarray[data[i].colourSr] + hexarray[data[i].colourSg] + hexarray[data[i].colourSb] + '" title="' + data[i].colourCode + '"></div></li>';
        $("#" + colourDiv).append($tr);
        loadEvent("divcs2" + i);
    }
}

var newFakeObj = new fake("tinctCode", "wrap", "material");

function jsonpCallback(data) {
    var $tr = "";
    for (i = 0; i < data.length; i++) {
        $tr = '<li><div class="boxCont" id="divload' + i + '" style="height:20px;width:20px;background:#' + hexarray[data[i].colourSr] + hexarray[data[i].colourSg] + hexarray[data[i].colourSb] + '" title="' + data[i].colourCode + '"></div></li>';
        $("#wrap").append($tr);
        loadEvent("divload" + i);
    }
}
