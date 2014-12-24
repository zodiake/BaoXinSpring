<%--
  Created by IntelliJ IDEA.
  User: Charles
  Date: 2014/7/3
  Time: 15:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="main">
<!--我的订单-->
    <ul>
        <li id="buyItem" style="margin-bottom: 40px;">
            <div class="modebox seller-indent">
                <div class="help_tit"><h6>我要购买</h6></div>
                <div class="con" style="height: 430px;margin-top: 10px;">
                   <%-- <IFRAME src="http://www.cloudfashion.org/video/help_mianliaogoumai.html" frameBorder=0 scrolling="no" width="100%" height="100%" style="position:relative;left:0;bottom:0" marginheight="0" allowfullscreen></IFRAME>--%>
                       <iframe src="http://www.cloudfashion.org/video/help_mianliaogoumai.html" frameborder="0" scrolling="no" width="100%" height="400px" style="position:relative;left:0;bottom:0" marginheight="0" allowfullscreen=""></iframe>
                </div>
            </div>
        </li>
        <li id="buySample" style="margin-bottom: 40px;" >
            <div class="modebox seller-indent">
                <div class="help_tit"><h6>我要调样</h6></div>
                <div class="con" style="height: 430px;margin-top: 10px;">
                            <iframe src="http://www.cloudfashion.org/video/help_shangpintiaoyang.html" frameborder="0" scrolling="no" width="100%" height="400px" style="position:relative;left:0;bottom:0" marginheight="0" allowfullscreen=""></iframe>
                </div>
            </div>
        </li>
        <li id="buyDemandOrder" style="margin-bottom: 40px;">
            <div class="modebox seller-indent">
                <div class="help_tit"><h6>我要求购</h6></div>
                <div class="con" style="height: 430px;margin-top: 10px;">
                    <iframe src="http://www.cloudfashion.org/video/help_fabuqiugou.html" frameborder="0" scrolling="no" width="100%" height="400px" style="position:relative;left:0;bottom:0" marginheight="0" allowfullscreen=""></iframe>
                </div>
            </div>
        </li>
        <li id="quickDesign" style="margin-bottom: 40px;">
            <div class="modebox seller-indent">
                <div class="help_tit"><h6>快速设计</h6></div>
                <div class="con" style="height: 430px;margin-top: 10px;">
                    <iframe src="http://www.cloudfashion.org/video/help_canshuhua.html" frameborder="0" scrolling="no" width="100%" height="400px" style="position:relative;left:0;bottom:0" marginheight="0" allowfullscreen=""></iframe>
                </div>
            </div>
        </li>
    </ul>
</div>
