<%--
  Created by IntelliJ IDEA.
  User: zodiake
  Date: 2014/6/5
  Time: 15:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!-- /.Container -->
<div class="container">
<sec:authorize access="authenticated" var="authenticated"></sec:authorize>
<!-- /.Jumbotron -->
<section class="jumbotron">
<div class="row-11">
<div class="col-2">
<!-- /.Navigation -->
<div class="navigation">
<dl>
<dt class="deep">商品分类</dt>
<dd>
<div class="directory">
<div class="sliphover">
<i class="icon icon-ml"></i>
<h2>面料分类</h2>
<ol>
<li>
    <div class="fox">
        <b>产品分类</b>
        <ul class="list-inline">
            <li><a href="javascript:void(0);">染色类</a></li>
            <li><a href="javascript:void(0);">色织类</a></li>
            <li><a href="javascript:void(0);">印花类</a></li>
            <li><a href="javascript:void(0);">提花类</a></li>
            <li><a href="javascript:void(0);">纱线工艺</a></li>
            <li><a href="javascript:void(0);">常见面料</a></li>
        </ul>
    </div>
    <div class="slipsheet fob">
        <c:forEach items="${fabricCategoryList}" var="firstCategory" varStatus="i">
            <c:choose>
                <c:when test="${i.index eq firstCategory.sortNo}">
                    <dl>
                        <dt><a class="orange" href="javascript:void(0);">${firstCategory.name}</a></dt>
                        <dd>
                            <ul class="list-inline">
                                <c:forEach items="${firstCategory.secondCategory}" var="secondCategory">
                                    <li><a href="${pageContext.request.contextPath}/search/fabric?category=${secondCategory.id}">${secondCategory.name}</a></li>
                                </c:forEach>
                            </ul>
                        </dd>
                    </dl>
                </c:when>
                <c:otherwise>
                    <dl>
                        <dt><a class="orange" href="javascript:void(0);">${firstCategory.name}</a></dt>
                        <dd>
                            <ul class="list-inline">
                                <c:forEach items="${firstCategory.secondCategory}" var="secondCategory">
                                    <li><a href="${pageContext.request.contextPath}/search/fabric?category=${secondCategory.id}">${secondCategory.name}</a></li>
                                </c:forEach>
                            </ul>
                        </dd>
                    </dl>
                </c:otherwise>
            </c:choose>

        </c:forEach>
    </div>
</li>
<li>
    <div class="fox">
        <b>产品成分</b>
        <ul class="list-inline">
            <li><a href="javascript:void(0);">天然纤维</a></li>
            <li><a href="javascript:void(0);">化学纤维</a></li>
            <li><a href="javascript:void(0);">再生纤维</a></li>
            <li><a href="javascript:void(0);">混纺纤维</a></li>
        </ul>
    </div>
    <div class="slipsheet fob">
        <c:forEach items="${fabricSourceList}" var="firstSource">
            <dl>
                <dt><a class="orange" href="javascript:void(0);">${firstSource.name}</a></dt>
                <dd>
                    <ul class="list-inline">
                        <c:forEach items="${firstSource.detailSources}" var="detailSource">
                            <li><a href="${pageContext.request.contextPath}/search/fabric?source=${detailSource.id}">${detailSource.name}</a></li>
                        </c:forEach>
                    </ul>
                </dd>
            </dl>
        </c:forEach>
    </div>
</li>
<li>
    <div class="fox">
        <b>产品工艺</b>
        <ul class="list-inline">
            <li><a href="javascript:void(0);">装饰工艺</a></li>
            <li><a href="javascript:void(0);">印染工艺</a></li>
            <li><a href="javascript:void(0);">编结工艺</a></li>
            <li><a href="javascript:void(0);">外观整理</a></li>
            <li><a href="javascript:void(0);">功能整理</a></li>
            <li><a href="javascript:void(0);">特殊整理</a></li>
        </ul>
    </div>
    <div class="slipsheet fob">
        <c:forEach items="${fabricTechnologyTypeList}" var="fabricTechnologyType">
            <dl>
                <dt><a class="orange" href="javascript:void(0);">${fabricTechnologyType.name}</a></dt>
                <dd>
                    <ul class="list-inline">
                        <c:forEach items="${fabricTechnologyType.secondType}" var="secondType">
                            <li><a href="${pageContext.request.contextPath}/search/fabric?technology=${secondType.id}">${secondType.name}</a></li>
                        </c:forEach>
                    </ul>
                </dd>
            </dl>
        </c:forEach>
    </div>
</li>
<li>
    <div class="fox">
        <b>产品用途</b>
        <ul class="list-inline">
            <c:forEach items="${fabricMainUseTypeList}" var="fabricMainUseType" varStatus="i">
                <c:if test="${i.index<10}">
                    <li><a href="javascript:void(0);">${fabricMainUseType.name}</a></li>
                </c:if>
            </c:forEach>
        </ul>
    </div>
    <div class="slipsheet fob">
        <dl>
            <dt><a class="orange" href="javascript:void(0);">产品用途</a></dt>
            <dd>
                <ul class="list-inline">
                    <c:forEach items="${fabricMainUseTypeList}" var="fabricMainUseType">
                        <li><a href="${pageContext.request.contextPath}/search/fabric?mainUse=${fabricMainUseType.id}">${fabricMainUseType.name}</a></li>
                    </c:forEach>
                </ul>
            </dd>
        </dl>
    </div>
</li>
</ol>
</div>
</div>
<div class="directory">
    <div class="sliphover">
        <i class="icon icon-fl"></i>
        <h2>辅料分类</h2>
        <ol>
            <li>
                <div class="fox">
                    <b>产品分类</b>
                    <ul class="list-inline">
                        <c:forEach items="${materialCategoryList}" var="materialCategory">
                            <li><a href="javascript:void(0);">${materialCategory.name}</a></li>
                        </c:forEach>
                    </ul>
                </div>
                <div class="slipsheet fob">
                    <c:forEach items="${materialCategoryList}" var="materialCategory">
                        <dl>
                            <dt><a class="orange" href="javascript:void(0);">${materialCategory.name}</a></dt>
                            <dd>
                                <ul class="list-inline">
                                    <c:forEach items="${materialCategory.secondCategory}" var="secondCategory">
                                        <li><a href="${pageContext.request.contextPath}/search/material?category=${secondCategory.id}">${secondCategory.name}</a></li>
                                    </c:forEach>
                                </ul>
                            </dd>
                        </dl>
                    </c:forEach>
                </div>
            </li>
        </ol>
    </div>
</div>
</dd>
</dl>
</div>
<!-- /.Navigation -->
</div>
<div class="col-6">
    <!-- Carous Start -->
    <div class="carous" data-view="${bannerOnHome.size()}" data-speed="slow" data-time="6000">
        <div class="carous-container">
            <ul>
                <c:forEach items="${bannerOnHome}" var="banner">
                    <li><a href="${banner.link}" target="_blank"><img src="${pageContext.request.contextPath}${banner.coverPath}?size=600"></a></li>
                </c:forEach>
            </ul>
        </div>
        <div class="carous-point">
            <div class="carous-thumb">
                <ul>
                    <c:forEach items="${bannerOnHome}" var="banner">
                        <li class="carousItem"></li>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </div>
    <!-- Carous End -->

    <!-- /.Recommend -->
    <div class="recommend">
        <dl>
            <dt>新季推荐</dt>
            <dd>
                <ul class="row-5">
                    <c:forEach items="${newRecommendOnHome}" var="newRecommend">
                        <li class="col-1">
                            <a href="${newRecommend.link}" target="_blank">
                                <img class="thumb" src="${pageContext.request.contextPath}${newRecommend.coverPath}?size=300">
                                <p>${newRecommend.title}</p>
                                <span></span>
                            </a>
                        </li>
                    </c:forEach>
                </ul>
            </dd>
        </dl>
    </div>
    <!-- /.Recommend -->

</div>
<div class="col-3">

    <!-- /.Side -->
    <div class="side">

        <!-- /.Data -->
        <div class="data">
							<span>
								<bdo class="h3">${month}</bdo>
								<bdo class="h4">${year}</bdo>
							</span>
							<span>
								<bdo class="h2 orange">${day}</bdo>
							</span>
							<span>
								<p>商品累计达<bdo class="orange">${statistics['itemsCount']}</bdo>件</p>
                                <p>新增供应商<bdo class="orange">${statistics['businessCount']}</bdo>名</p>
								<p>设计师<bdo class="orange">${statistics['designerCount']}</bdo>名</p>
								<p>设计资源总数<bdo class="orange">${statistics['eventCount']}</bdo>张</p>
							</span>
        </div>
        <!-- /.Data -->

        <!-- /.Application -->
        <div class="app">
            <dl>
                <dt><i></i>企业应用</dt>
                <dd>
                    <ul class="row-4">
                        <li class="col-1">
                            <a href="${linkMap['cssj']}" target="_blank">
                                <i class="icon icon-param"></i>
                                <span>参数设计</span>
                            </a>
                        </li>
                        <li class="col-1">
                            <a href="${linkMap['PMDV01']}" target="_blank">
                                <i class="icon icon-plan"></i>
                                <span>企划管理</span>
                            </a>
                        </li>
                        <li class="col-1">
                            <a href="${linkMap['PMOS01']}" target="_blank">
                                <i class="icon icon-meet"></i>
                                <span>网上订货</span>
                            </a>
                        </li>
                        <li class="col-1">
                            <a href="${linkMap['xnzt']}" target="_blank">
                                <i class="icon icon-booth"></i>
                                <span>虚拟展台</span>
                            </a>
                        </li>
                    </ul>
                </dd>
            </dl>
        </div>
        <!-- /.Application -->
        <!--最新流行趋势-->
        <div class="trend-box">
            <div class="hd"><h6><i></i>最新流行趋势</h6></div>
            <div class="trend-list">
                <ul>
                    <c:choose>
                        <c:when test="${authenticated}">
                            <li><a href="${linkMap['themeStyle']}" title=""><img class="thumb" src="${pageContext.request.contextPath}/resources/img/trend-img1.png"></a></li>
                            <li><a href="${linkMap['colorStyle']}" title=""><img class="thumb" src="${pageContext.request.contextPath}/resources/img/trend-img3.png"></a></li>
                            <li><a href="${linkMap['maleStyle']}" title=""><img class="thumb" src="${pageContext.request.contextPath}/resources/img/trend-img2.png"></a></li>
                            <li><a href="${linkMap['femaleStyle']}" title=""><img class="thumb" src="${pageContext.request.contextPath}/resources/img/trend-img4.png"></a></li>
                        </c:when>
                        <c:otherwise>
                            <li><a href="${linkMap['themeStyle_unauthenticated']}" title=""><img class="thumb" src="${pageContext.request.contextPath}/resources/img/trend-img1.png"></a></li>
                            <li><a href="${linkMap['colorStyle_unauthenticated']}" title=""><img class="thumb" src="${pageContext.request.contextPath}/resources/img/trend-img3.png"></a></li>
                            <li><a href="${linkMap['maleStyle_unauthenticated']}" title=""><img class="thumb" src="${pageContext.request.contextPath}/resources/img/trend-img2.png"></a></li>
                            <li><a href="${linkMap['femaleStyle_unauthenticated']}" title=""><img class="thumb" src="${pageContext.request.contextPath}/resources/img/trend-img4.png"></a></li>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </div>
        </div>
    </div>
    <!-- /.Side -->

</div>
</div>
</section>
<!-- /.Jumbotron -->

<!-- /.Main -->
<section class="main ">
<div class="row-11">
<div class="col-8">

<!-- /.Box -->
<dl class="sheet">
    <dt class="index-block-1">
        <a class="more" href="${pageContext.request.contextPath}/search/fabric">更多</a>
    </dt>
    <dd>
        <div class="row-8">
            <div class="col-2">
                <div class="grid">
                    <ul>
                        <c:forEach items="${fabricShop}" var="fShop">
                            <li><a href="${fShop.link}" target="_blank"></a>
                                <c:choose>
                                    <c:when test="${not empty fShop.coverPath}">
                                        <img src="${pageContext.request.contextPath}${fShop.coverPath}?size=300">
                                    </c:when>
                                    <c:otherwise><img src="${pageContext.request.contextPath}/resources/img/btn-th.png"></c:otherwise>
                                </c:choose>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
            <div class="col-6">
                <ul class="row-4">
                    <c:forEach items="${fabricList}" var="fabric">
                        <li class="col-1">
                            <a href="${pageContext.request.contextPath}/${fabric.url}/${fabric.id}">
                                <c:choose>
                                    <c:when test="${not empty fabric.coverImage}">
                                        <img class="thumb" src="${pageContext.request.contextPath}${fabric.coverImage}?size=300">
                                    </c:when>
                                    <c:otherwise><img class="thumb" src="${pageContext.request.contextPath}/resources/img/btn-th.png"></c:otherwise>
                                </c:choose>
                                <p class="text-over" title="${fabric.name}">${fabric.name}</p>
                                <span><bdo class="price orange">${fabric.price}</bdo>/米</span>
                                <p>${fabric.createdBy.defaultAddress.state}</p>
                            </a>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </dd>
</dl>
<!-- /.Box -->

<!-- /.Box -->
<dl class="sheet">
    <dt class="index-block-2">
        <a class="more" href="${pageContext.request.contextPath}/search/material">更多</a>
    </dt>
    <dd>
        <div class="row-8">
            <div class="col-2">
                <div class="grid">
                    <ul>
                        <c:forEach items="${materialShop}" var="mShop">
                            <li><a href="${mShop.link}" target="_blank"></a>
                                <c:choose>
                                    <c:when test="${not empty mShop.coverPath}">
                                        <img src="${pageContext.request.contextPath}${mShop.coverPath}?size=300">
                                    </c:when>
                                    <c:otherwise><img src="${pageContext.request.contextPath}/resources/img/btn-th.png"></c:otherwise>
                                </c:choose>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
            <div class="col-6">
                <ul class="row-4">
                    <c:forEach items="${materialList}" var="material">
                        <li class="col-1">
                            <a href="${pageContext.request.contextPath}/${material.url}/${material.id}">
                                <c:choose>
                                    <c:when test="${not empty material.coverImage}">
                                        <img class="thumb" src="${pageContext.request.contextPath}${material.coverImage}?size=300">
                                    </c:when>
                                    <c:otherwise><img class="thumb" src="${pageContext.request.contextPath}/resources/img/btn-th.png"></c:otherwise>
                                </c:choose>
                                <p class="text-over" title="${material.name}">${material.name}</p>
                                <span><bdo class="price orange">${material.price}</bdo>/米</span>
                                <p>${material.createdBy.defaultAddress.state}</p>
                            </a>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </dd>
</dl>
<!-- /.Box -->

<!-- /.Box -->
<dl class="sheet">
    <dt class="index-block-4">
        <c:choose>
            <c:when test="${authenticated}">
                <a class="more" href="${linkMap['designer']}">更多</a>
            </c:when>
            <c:otherwise>
                <a class="more" href="${linkMap['designer_unauthenticated']}">更多</a>
            </c:otherwise>
        </c:choose>
    </dt>
    <dd>
        <ul class="row-5">
            <c:forEach items="${designerList}" var="designer">
                <li class="col-1">
                    <c:choose>
                        <c:when test="${authenticated}">
                        <a href="${linkMap['designerDetail']}${designer.id}">
                        </c:when>
                        <c:otherwise>
                            <a href="${linkMap['designerDetail_unauthenticated']}${designer.id}">
                        </c:otherwise>
                    </c:choose>
                        <c:choose>
                            <c:when test="${not empty designer.headUrl}"><img class="thumb" src="${linkMap['designerImg']}${designer.headUrl}"></c:when>
                            <c:otherwise><img class="thumb" src="${pageContext.request.contextPath}/resources/img/btn-th.png"></c:otherwise>
                        </c:choose>
                        <div>
                            <b>${designer.name}</b>
                            <p>${designer.resume}</p>
                        </div>
                    </a>
                </li>
            </c:forEach>
        </ul>
    </dd>
</dl>
<!-- /.Box -->

<!-- /.Box -->
<dl class="sheet">
    <dt class="index-block-5">
        <c:choose>
            <c:when test="${authenticated}">
                <a class="more" href="${linkMap['brands']}">更多</a>
            </c:when>
            <c:otherwise>
                <a class="more" href="${linkMap['brands_unauthenticated']}">更多</a>
            </c:otherwise>
        </c:choose>
    </dt>
    <dd>
        <ul class="grit row-6">
            <c:forEach items="${brands}" var="brand" varStatus="i">
                <c:choose>
                    <c:when test="${i.index<1}">
                        <li class="col-2">
                            <c:choose>
                                <c:when test="${authenticated}">
                                    <a href="${linkMap['brandDetail']}${brand.id}"></a>
                                </c:when>
                                <c:otherwise>
                                    <a href="${linkMap['brandDetail_unauthenticated']}${brand.id}"></a>
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${not empty brand.headUrl}"><img src="${linkMap['designerImg']}${brand.headUrl}" class="thumb"></c:when>
                                <c:otherwise><img class="thumb" src="${pageContext.request.contextPath}/resources/img/btn-th.png"></c:otherwise>
                            </c:choose>
                        </li>
                    </c:when>
                    <c:when test="${i.index>0}">
                        <li class="col-1">
                            <c:choose>
                            <c:when test="${authenticated}">
                                <a href="${linkMap['brandDetail']}${brand.id}"></a>
                            </c:when>
                            <c:otherwise>
                                <a href="${linkMap['brandDetail_unauthenticated']}${brand.id}"></a>
                            </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${not empty brand.headUrl}"><img src="${linkMap['designerImg']}${brand.headUrl}" class="thumb"></c:when>
                                <c:otherwise><img class="thumb" src="${pageContext.request.contextPath}/resources/img/btn-th.png"></c:otherwise>
                            </c:choose>
                        </li>
                    </c:when>
                </c:choose>
            </c:forEach>
        </ul>
    </dd>
</dl>
<!-- /.Box -->

<!-- /.Box -->
<dl class="sheet">
    <dt class="index-block-6">
        <a class="more" href="${pageContext.request.contextPath}/demandOrderList">更多</a>
    </dt>
    <dd>
        <table class="table">
            <thead>
            <tr>
                <th>品名</th>
                <th>产品类型</th>
                <th>求购说明</th>
                <th>有效开始日期</th>
                <th>有效截止日期</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${demandOrderList}" var="demandOrder">
                <tr>
                    <td><a href="${pageContext.request.contextPath}/demandOrder/${demandOrder.id}">${demandOrder.title}</a></td>
                    <td>${demandOrder.demandType}</td>
                    <td><a href="${pageContext.request.contextPath}/demandOrder/${demandOrder.id}">${demandOrder.content}</a></td>
                    <td><fmt:formatDate value="${demandOrder.validDateFrom.time}" pattern="yyyy-MM-dd" type="date"/></td>
                    <td><fmt:formatDate value="${demandOrder.validDateTo.time}" pattern="yyyy-MM-dd" type="date"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </dd>
</dl>
<!-- /.Box -->

</div>

<div class="col-3">
    <div class="box">
        <a href="${pageContext.request.contextPath}/topic" target="_blank">
            <img class="thumb" src="${pageContext.request.contextPath}/resources/images/MediaFocus.gif">
        </a>
    </div>
    <div class="box">
        <a href="${pageContext.request.contextPath}/topic/377814df-c0d1-4cff-a927-2456f717744c" target="_blank">
            <img class="thumb" src="${pageContext.request.contextPath}/resources/images/play.jpg">
        </a>
    </div>
    <!-- /.Tab -->
    <div class="home-message-box sider box">
        <div class="tabs">
            <ul>
                <li id="tabs1"><a href="${pageContext.request.contextPath}/notice" class="active"onMouseMove="switchTab('tabs1','con1');this.blur();return false;" target="_blank">公告</a></li>
                <li id="tabs2"><a href="${pageContext.request.contextPath}/info" onMouseMove="switchTab('tabs2','con2');this.blur();return false;" target="_blank" >资讯</a></li>
            </ul>
        </div>
        <div id="con1" class="message-list" >
            <ul>
                <c:forEach var="notice" items="${noticeList}">
                    <li><a href="${pageContext.request.contextPath}/notice/${notice.id}" target="_blank">${notice.title}</a></li>
                </c:forEach>
            </ul>
        </div>
        <div id="con2" class="message-list" style="display:none;">
            <ul>
                <c:forEach var="info" items="${infoList}">
                    <li><a href="${pageContext.request.contextPath}/info/${info.id}" target="_blank">${info.title}</a></li>
                </c:forEach>
            </ul>
        </div>
    </div>
    <!--.Tab -->
    <div class="side">
        <dl class="sider box">
            <dt>面料快报</dt>
            <dd>
                <b>女装面料</b>
                <ul class="row-3">
                    <c:forEach items="${ladiesFabric}" var="ladyFabric">
                        <li class="col-1">
                            <a href="${ladyFabric.link}"><img class="thumb" src="${pageContext.request.contextPath}${ladyFabric.coverPath}?size=300"></a>
                        </li>
                    </c:forEach>
                </ul>
                <b>男装面料</b>
                <ul class="row-3">
                    <c:forEach items="${mensFabric}" var="menFabric">
                        <li class="col-1">
                            <a href="${menFabric.link}"><img class="thumb" src="${pageContext.request.contextPath}${menFabric.coverPath}?size=300"></a>
                        </li>
                    </c:forEach>
                </ul>
            </dd>
        </dl>
    </div>
    <!-- /.Sider -->
</div>
</div>
</section>
<!-- /.Main -->

</div>
<!-- /.Container -->
<!--tabs-->
<script type="text/javascript">
    function switchTab(ProTag, ProBox) {
        for (var i = 1; i < 5; i++) {
            if ("tabs" + i == ProTag) {
                document.getElementById(ProTag).getElementsByTagName("a")[0].className = "active";
            } else {
                document.getElementById("tabs" + i).getElementsByTagName("a")[0].className = "";
            }
            if ("con" + i == ProBox) {
                document.getElementById(ProBox).style.display = "";
            } else {
                document.getElementById("con" + i).style.display = "none";
            }
        }
    }
</script>
<!--tabs end-->
</script>