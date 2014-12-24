<%--
  Created by IntelliJ IDEA.
  User: zodiake
  Date: 2014/5/23
  Time: 15:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script>
    $(function () {
        function readCookie(name) {
            var nameEQ = escape(name) + "=";
            var ca = document.cookie.split(';');
            for (var i = 0; i < ca.length; i++) {
                var c = ca[i];
                while (c.charAt(0) === ' ') c = c.substring(1, c.length);
                if (c.indexOf(nameEQ) === 0) return unescape(c.substring(nameEQ.length, c.length));
            }
            return null;
        }

        var name = readCookie("buap_casUser");
        if (name != '' && name != undefined) {
            var div$ = $('#username_personal');
            div$.children().remove();
            div$.text('您好，欢迎来到创意设计公共服务基地！' + name);
            var a$ = $('<a href="http://www.cloudfashion.org/buap/logout3.jsp">【注销】</a>|&nbsp;<a href="/help/userGuide" target="_blank">帮助中心</a>');
            div$.append(a$);
        }
    });
</script>
<!-- /.Header -->
<div class="header">
    <div class="mask clearfix">
        <a class="logo" href="/"></a>

        <div class="foremost">
            <i>上海张江国家自主创新示范区专项发展资金重大项目</i>

            <h1>基于云计算的创意设计公共服务基地</h1>
        </div>
        <div class="info">
            <div class="personal" id="username_personal">
                您好，欢迎来到创意设计公共服务基地！
                <sec:authorize access="authenticated" var="authenticated"></sec:authorize>
                <c:choose>
                    <c:when test="${authenticated}">
                        <sec:authentication property="name"></sec:authentication>
                        <a href="${linkMap['logout']}">【注销】</a>
                    </c:when>
                    <c:otherwise>
                        <a href="${pageContext.request.contextPath}/login">[登录]</a> |
                        <a href="${linkMap['register']}">[免费注册]</a>
                    </c:otherwise>
                </c:choose>
                |&nbsp;<a href="/help/userGuide" target="_blank">帮助中心</a>
            </div>
            <div class="search">
                <form id="searchForm" class="form clearfix" action="${pageContext.request.contextPath}/search/fabric">
                    <a id="submit-link" title="搜索" class="icon-search"></a>
                    <input type="text" name="keyWord" class="input" placeholder="请输入关键字">

                    <div class="drop">
                        <div class="drop-menu selectedType">面&nbsp;&nbsp;&nbsp;料<i class="caret"></i></div>
                        <div class="drop-content drop-down-left selectedType-left">
                            <ul>
                                <li class="searchType" data-src="${pageContext.request.contextPath}/search/fabric">面&nbsp;&nbsp;&nbsp;料</li>
                                <li class="searchType" data-src="${pageContext.request.contextPath}/search/material">辅&nbsp;&nbsp;&nbsp;料</li>
                                <li class="searchType" data-src="${pageContext.request.contextPath}/search/shop">供应商
                                </li>
                            </ul>
                        </div>
                    </div>
                </form>

            </div>
            <div class="nav">
                <nav>
                    <ul class="list-inline">
                        <li><a href="mailto:service@cloudfashion.org">联系我们</a></li>
                        <li><a href="${pageContext.request.contextPath}/buap/intro/intro.html" target="_blank">基地介绍</a></li>
                        <li><a href="${linkMap['cssj']}">设计体验</a></li>
                        <c:choose>
                            <c:when test="${authenticated}">
                                <li><a href="${linkMap['brands']}">品牌</a></li>
                                <li><a href="${linkMap['designer']}">设计师</a></li>
                                <li><a href="${linkMap['fashion']}">流行趋势</a></li>
                            </c:when>
                            <c:otherwise>
                                <li><a href="${linkMap['brands_unauthenticated']}">品牌</a></li>
                                <li><a href="${linkMap['designer_unauthenticated']}">设计师</a></li>
                                <li><a href="${linkMap['fashion_unauthenticated']}">流行趋势</a></li>
                            </c:otherwise>
                        </c:choose>
                    </ul>
                </nav>
            </div>
        </div>
    </div>
    <div class="bar clearfix">
			<span class="drop atte">
				<div class="drop-menu">我的关注<i class="caret"></i></div>
				<div class="drop-content drop-down-right">
                    <ul>
                        <li><a href="${pageContext.request.contextPath}/buyerCenter/favouritesList">关注的商品</a></li>
                        <li><a href="${pageContext.request.contextPath}/buyerCenter/favourite/shops">关注的供应商</a></li>
                        <li><a href="${pageContext.request.contextPath}/buyerCenter/attentionDesigner">关注的设计师</a></li>
                        <li><a href="${pageContext.request.contextPath}/buyerCenter/attentionBrand">关注的品牌</a></li>
                    </ul>
                </div>
			</span>
            <span class="drop cart ">
				<div class="drop-menu sample">调样册(<bdo class="sampleQuantity">${sampleQuantity}</bdo>)<i
                        class="caret"></i></div>
				<div class="drop-content drop-down-right">
                    <div class="shopping viewSample" sample="0"></div>
                </div>
			</span>
			<span class="drop cart ">
				<div class="drop-menu viewCart">购物车(<bdo class="cartQuantity">${cartQuantity}</bdo>)<i
                        class="caret"></i></div>
				<div class="drop-content drop-down-right">
                    <div class="shopping shoppingcart" viewCart="0"></div>
                </div>
			</span>
        <span class="terrace"><a href="${pageContext.request.contextPath}/buyerCenter">我的平台</a></span>
    </div>
    </header>
    <!-- /.Header -->
</div>