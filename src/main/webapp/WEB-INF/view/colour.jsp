<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>


    <link rel="stylesheet" href="${ctx }/bcas/wc/css/jquery-ui.css"/>

    <script type="text/javascript" src="${ctx }/resource/js/jquery.min.js"></script>
    <script type="text/javascript" src="${ctx }/bcas/wc/js/jquery-1.10.2.js"></script>
    <script type="text/javascript" src="${ctx }/bcas/wc/js/jquery-ui.js"></script>
    <script type="text/javascript" src="${ctx }/bcas/wc/js/colorPlate.js"></script>
    <script type="text/javascript" src="${ctx }/bcas/wc/js/jquery-1.10.2.min"></script>
    <script type="text/javascript" src="${ctx }/bcas/wc/js/jquery-ui-1.10.3.custom.min"></script>


    <title>颜色选择器</title>
    <style>
        * {
            padding: 0;
            margin: 0;
        }

        #wrap {
            position: relative;
            zoom: 1;
            margin: 0px auto;
        }

        #wrap li {
            width: 18px;
            float: left;
            list-style: none;
        }

        .boxCont {
            position: relative;
            margin: 0px;
            border: 1px solid #ccc;
        );
        }

        #wrapdiv {
            width: 300px;
            height: 300px;
            background: white;
            word-wrap: break-word;
            overflow: auto;
        }
    </style>
</head>
<body>
<div id="tinctCode"></div>
<div id="material"></div>
<div><img src="${ctx }/bcas/wc/css/color2.jpg"/></div>
<p><input type="text" id="amount" value="1" style="border:0; color:#f6931f; font-weight:bold;"></p>

<div id="slider" style="width:300px;height:5px"></div>
<br/>

<div id="wrapdiv">
    <ul id="wrap">
    </ul>
</div>
选中颜色
<div id="container"
     style="background:white;height:100px;width:300px;border-style:dashed; border-width:1px; border-color:#000">
</div>
<br/>

<div id="buttonDiv">

</div>
</body>
<script type="text/javascript">
    var newFakeObj = new fake("tinctCode", "wrap", "material");
</script>
</html>
