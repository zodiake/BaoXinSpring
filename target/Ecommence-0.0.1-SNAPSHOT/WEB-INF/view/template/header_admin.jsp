<%--
  Created by IntelliJ IDEA.
  User: Charles
  Date: 2014/6/12
  Time: 10:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- /.Header -->
<div class="header">
    <div class="mask clearfix">
        <a class="logo" href="/"></a>
        <div class="foremost">
            <i>上海张江国家自主创新示范区专项发展资金重大项目</i>
            <h1>基于云计算的创意设计公共服务基地</h1>
        </div>
        <div class="info">
            <div class="personal">
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
                    <a id="submit-link" href="#" title="" class="icon-search"></a>
                    <input type="text" name="keyWord" class="input" placeholder="请输入关键字">
                    <div class="drop">
                        <div class="drop-menu selectedType">面&nbsp;&nbsp;&nbsp;料<i class="caret"></i></div>
                        <div class="drop-content drop-down-left selectedType-left">
                            <ul>
                                <li class="searchType" data-src="${pageContext.request.contextPath}/search/fabric">面&nbsp;&nbsp;&nbsp;料</li>
                                <li class="searchType" data-src="${pageContext.request.contextPath}/search/material">辅&nbsp;&nbsp;&nbsp;料</li>
                                <li class="searchType" data-src="${pageContext.request.contextPath}/search/shop">供应商</li>
                            </ul>
                        </div>
                    </div>
                </form>
            </div>
            <nav>
                <ul class="list-inline">
                    <li><a href="mailto:service@cloudfashion.org">联系我们</a></li>
                    <li><a href="${pageContext.request.contextPath}/buap/intro/intro.html" target="_blank">基地介绍</a></li>
                    <li><a href="${linkMap['cssj']}">设计体验</a></li>
                    <li><a href="${linkMap['brands']}">品牌</a></li>
                    <li><a href="${linkMap['designer']}">设计师</a></li>
                    <li><a href="${linkMap['fashion']}">流行趋势</a></li>
                </ul>
            </nav>
        </div>
    </div>
    <div class="bar clearfix">
        <span class="left">
            <a href="${pageContext.request.contextPath}/buyerCenter" class="buyer-icon1" style="font-size:14px ">我的平台</a>
            <sec:authorize access="hasAnyRole('ROLE_FABRIC','ROLE_MATERIAL')">
                | <a href="${pageContext.request.contextPath}/sellerCenter " class="seller-icon1">卖家中心</a>
            </sec:authorize></span>
			<span class="drop atte">
				<div class="drop-menu">账号管理<i class="caret"></i></div>
				<div class="drop-content drop-down-left">
                    <ul>
                        <li><a href="${linkMap['profile']}" target="_blank">信息维护</a></li>
                        <li><a href="${linkMap['changePwd']}" target="_blank">修改密码</a></li>
                        <li><a href="${linkMap['ecadmin']}">交易平台后台</a></li>
                        <li><a href="${linkMap['buapadmin']}">公共后台管理</a></li>
                    </ul>
                </div>
			</span>
    </div>
    </div>

</div>
<!-- /.Header -->
