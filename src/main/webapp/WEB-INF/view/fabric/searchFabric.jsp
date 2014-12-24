<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="sbTag" uri="http://zodiake.com" %>
<%--
  Created by IntelliJ IDEA.
  User: Charles
  Date: 2014/6/24
  Time: 16:43
  To change this template use File | Settings | File Templates.
--%>
<!-- /.Container -->
<div class="container">

<!-- /.Search Condition -->
<div class="search-condition">
    <span>共<bdo class="orange">${count}</bdo>件产品符合要求
   <span>&nbsp;<input type="text" id="sub_keyWord" class="input" placeholder="在结果中查找" value="${search.keyWord}"><button
           type="button" id="sub_submit" class="button button-small button-deep w-40 m-left-10">搜索
   </button></span>
    </span>

    <div class="search-choose">
        <label>已选择条件：</label>
        <ul class="list-inline">
        </ul>
    </div>
</div>
<!-- /.Search Condition -->

<!-- /.Search Select -->
<div class="text-right">
    <button type="button" class="search-pack"><i class="icon icon-minus"></i>收起</button>
</div>
<dl class="search-select box">
    <dt>面料分类</dt>
    <dd>
        <form:form modelAttribute="search" method="get" cssClass="search-main">
            <table class="table">
                <tr>
                    <td>产品类别：</td>
                    <td>
                        <ul class="list-inline">
                            <c:forEach items="${firstCategory}" var="category">
                                <li class="inline-act">
                                    <div class="drop">
                                        <span class="drop-menu category"
                                              data-id="${category.id}">${category.name}</span>

                                        <div class="drop-content"
                                             style="width:${category.secondCategory.size()>=8?500:300}">
                                            <dl>
                                                <dd>
                                                    <ul class="list-inline">
                                                        <form:radiobuttons path="category"
                                                                           items="${category.secondCategory}"
                                                                           itemLabel="name" itemValue="id"
                                                                           element="li" data-label="产品类别"
                                                                           cssClass="qr-radio"></form:radiobuttons>
                                                    </ul>
                                                </dd>
                                            </dl>
                                        </div>
                                    </div>
                                </li>
                            </c:forEach>
                        </ul>
                    </td>
                </tr>
                <tr>
                    <td>适用季节：</td>
                    <td>
                        <ul class="list-inline season duiqi">
                            <form:checkboxes path="season" items="${seasons}" element="li" itemLabel="type"
                                             itemValue="id" cssClass="qr-radio" data-label="适用季节"></form:checkboxes>
                        </ul>
                    </td>
                </tr>
                <tr>
                    <td>色彩：</td>
                    <td>
                        <ul class="list-inline duiqi">
                            <form:checkboxes path="hierarchy" items="${hierarchies}" itemLabel="name"
                                             itemValue="id" cssClass="qr-radio" element="li"
                                             data-label="色系"></form:checkboxes>
                        </ul>
                    </td>
                </tr>

                <tr>
                    <td>图案：</td>
                    <td>
                        <ul class="list-inline">
                            <c:forEach items="${pattern}" var="pattern">
                                <li class="inline-act-three">
                                    <div class="drop">
                                        <span class="drop-menu">${pattern.name}</span>

                                        <div class="drop-content">
                                            <dl>
                                                <dd>
                                                    <ul class="list-inline">
                                                        <form:checkboxes path="patterns" items="${pattern.children}"
                                                                         itemLabel="name" itemValue="id"
                                                                         element="li" data-label="图案"
                                                                         cssClass="qr-radio"></form:checkboxes>
                                                    </ul>
                                                </dd>
                                            </dl>
                                        </div>
                                    </div>
                                </li>
                            </c:forEach>
                        </ul>
                    </td>
                </tr>
                <tr>
                    <td>工艺：</td>
                    <td>
                        <ul class="list-inline">
                            <c:forEach items="${technology}" var="tech">
                                <li class="inline-act">
                                    <div class="drop">
                                        <span class="drop-menu">${tech.name}</span>

                                        <div class="drop-content">
                                            <dl>
                                                <dd>
                                                    <ul class="list-inline">
                                                        <form:radiobuttons path="technology" items="${tech.secondType}"
                                                                           itemValue="id"
                                                                           itemLabel="name" element="li" data-label="工艺"
                                                                           cssClass="qr-radio"></form:radiobuttons>
                                                    </ul>
                                                </dd>
                                            </dl>
                                        </div>
                                    </div>
                                </li>
                            </c:forEach>
                        </ul>
                    </td>
                </tr>
                <tr>
                    <td>用途：</td>
                    <td>
                        <ul class="list-inline duiqi">
                            <form:checkboxes path="mainUse" items="${types}" itemLabel="name" itemValue="id"
                                             cssClass="qr-radio"
                                             element="li" data-label="用途"></form:checkboxes>
                        </ul>
                    </td>
                </tr>
                <tr>
                    <td>克重：</td>
                    <td>
                        <ul class="list-inline weight">
                            <li><a data-min="0" data-max="90">0-90g</a></li>
                            <li><a data-min="91" data-max="160">91-160g</a></li>
                            <li><a data-min="161" data-max="350">161-350g</a></li>
                            <li><a data-min="351" data-max="500">351-500g</a></li>
                            <li><a data-min="500">≥500g</a></li>
                            <li>
                                <div class="inline-fame">
                                    <form:input path="minWeight" type="text" class="w-40 input-small"/>
                                    <span class="interval">~</span>
                                    <form:input path="maxWeight" type="text" class="w-40 input-small"/>
                                </div>
                                <div class="inline-pop">
                                    <button class="button button-small button-deep w-40">搜索</button>
                                </div>
                            </li>
                            <form:hidden path="weightRangeMin"></form:hidden>
                            <form:hidden path="weightRangeMax"></form:hidden>
                        </ul>
                    </td>
                </tr>
                <tr>
                    <td>幅宽：</td>
                    <td>
                        <ul class="list-inline width">
                            <li><a data-min="0" data-max="120">0-120cm</a></li>
                            <li><a data-min="121" data-max="140">121-140cm</a></li>
                            <li><a data-min="141" data-max="180">141-180cm</a></li>
                            <li><a data-min="180">≥180cm</a></li>
                            <li>
                                <div class="inline-fame">
                                    <form:input type="text" class="w-40 input-small" path="minWidth"/>
                                    <span class="interval">~</span>
                                    <form:input type="text" class="w-40 input-small" path="maxWidth"/>
                                </div>
                                <div class="inline-pop">
                                    <button class="button button-small button-deep w-40">搜索</button>
                                </div>
                            </li>
                            <form:hidden path="widthRangeMin"></form:hidden>
                            <form:hidden path="widthRangeMax"></form:hidden>
                        </ul>
                    </td>
                </tr>
                <tr>
                    <td>原料成份：</td>
                    <td>
                        <ul class="list-inline">
                            <c:forEach items="${sources}" var="source">
                                <li class="inline-act-one">
                                    <div class="drop">
                                        <span class="drop-menu">${source.name}</span>

                                        <div class="drop-content">
                                            <dl>
                                                <dd>
                                                    <ul class="list-inline no-ipt">
                                                        <form:radiobuttons path="source" items="${source.detailSources}"
                                                                           itemLabel="name" itemValue="id"
                                                                           element="li"
                                                                           data-label="原料成分"></form:radiobuttons>
                                                    </ul>
                                                </dd>
                                            </dl>
                                        </div>
                                    </div>
                                </li>
                            </c:forEach>
                        </ul>
                    </td>
                </tr>
            </table>
            <form:hidden path="sort"></form:hidden>
            <form:hidden path="keyWord"></form:hidden>
            <form:hidden path="minPrice"/>
            <form:hidden path="maxPrice"/>
        </form:form>
    </dd>
</dl>
<!-- /.Search Select -->

<!-- /.Search Select -->
<div class="search-recommend">
    <label>大家都在找：</label>
    <ul class="list-inline inline">
        <c:forEach items="${hotKeys}" var="key">
            <li><a href="/search/fabric?keyWord=${key}">${key}</a></li>
        </c:forEach>
    </ul>
</div>
<!-- /.Search Select -->

<!-- /.Search Result -->
<div class="search-result">
    <div class="search-result-panel">
        <ul class="pagination">
            <li><a href="#" data-sort="0">综合</a></li>
            <li>
                <a href="#" data-sort="1">人气</a>
            </li>
            <li><a href="#" data-sort="2">销量</a></li>
            <li id="price-order">
                <a href="#" data-sort="">价格</a>

                <div class="pagin-pop">
                    <a href="#" title="" data-sort="4">从高到底</a>
                    <a href="#" title="" data-sort="3">从低到高</a>
                </div>
            </li>
        </ul>
        <ul class="list-inline inline-block">
            <li>
                <div class="inline-fame">
                    <input type="text" class="w-60 input-small" id="fakeMinPrice" name="fakeMinPrice" placeholder="最低价"
                           value="${search.minPrice}">
                    ~
                    <input type="text" class="w-60 input-small" id="fakeMaxPrice" name="fakeMaxPrice" placeholder="最高价"
                           value="${search.maxPrice}">
                </div>
                <div class="inline-pop">
                    <button id="price-search" class="button button-small button-deep w-40">搜索</button>
                </div>
            </li>
        </ul>
        <select id="state" name="area" class="area">
            <option></option>
        </select>
        <!--
        <span>
            <button type="button" class="button button-deep">确认</button>
        </span>
        -->
    </div>
    <div class="search-result-main">
        <c:choose>
            <c:when test="${not empty lists}">
                <table class="table">
                    <tbody>
                    <c:forEach items="${lists}" var="list">
                        <tr>
                            <td width="200">
                                <a href="/fabric/${list.id}">
                                    <c:choose>
                                        <c:when test="${not empty list.cover}">
                                            <img class="thumb"
                                                 src="${pageContext.request.contextPath}${list.cover}?size=300">
                                        </c:when>
                                        <c:otherwise>
                                            <img class="thumb"
                                                 src="${pageContext.request.contextPath}/resources/img/btn-th.png">
                                        </c:otherwise>
                                    </c:choose>
                                </a>
                            </td>
                            <td>
                                <a href="/fabric/${list.id}">${list.title}</a>

                                <p class="gray">
                                    <span>用途：${list.use}</span>|
                                    <span>幅宽：${list.width}</span>|
                                    <span>克重：<c:if test="${list.weight!=0.0}">${list.weight}</c:if></span>|
                                    <span>季节：<c:forEach items="${list.seasons}"
                                                        var="season">${season} </c:forEach></span>|
                                    <span>纺织工艺：${list.source}</span>|
                                    <span>${list.patterns}</span>
                                </p>
                            </td>
                            <td align="center" width="100">
                                <c:choose>
                                    <c:when test="${list.price==0.0}">
                                        <bdo class="block orange">面议</bdo>
                                    </c:when>
                                    <c:otherwise>
                                        <bdo class="price block orange">${list.price}</bdo>
                                    </c:otherwise>
                                </c:choose>

                                <p>成交${list.sales}笔</p>
                            </td>
                            <td align="center">
                                <p>${list.companyName}</p>
                                <span>${list.area}</span>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <c:if test="${totalPage>1}">
                    <div class="text-right">
                        <sbTag:page total="${totalPage}" current="${currentPage}" dataPage="true"></sbTag:page>
                    </div>
                </c:if>
            </c:when>
            <c:otherwise>
                <div style="height: 200px;text-align: center;padding-top:100px;font-size: 18px; font-weight:bold;">
                    <strong>抱歉，没有找到符合条件的记录。</strong>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>
<!-- /.Search Result -->

<dl class="detail-recommend box">
    <dt>人气推荐</dt>
    <dd>
        <div class="row-6">

            <c:forEach items="${suggestion}" var="s">
                <div class="col-1">
                    <a href="/fabric/${s.id}">
                        <c:choose>
                            <c:when test="${empty s.cover}">
                                <img class="thumb" src="${pageContext.request.contextPath}/resources/img/btn-th.png">
                            </c:when>
                            <c:otherwise>
                                <img class="thumb" src="${s.cover}&imageSize=300">
                            </c:otherwise>
                        </c:choose>
                    </a>
                    <ul>
                        <li class="text-over">
                            <span><a href="/fabric/${s.id}" title="${s.title}">${s.title}</a></span>
                        </li>
                        <li>
                            <c:choose>
                                <c:when test="${s.price==0.0}">
                                    <span><bdo class="orange">面议</bdo></span>
                                </c:when>
                                <c:otherwise>
                                    <span><bdo class="price orange">${s.price}</bdo></span>
                                </c:otherwise>
                            </c:choose>
                        </li>
                    </ul>
                </div>
            </c:forEach>
        </div>
    </dd>
</dl>
<input type="hidden" id="selectedArea" value="${requestScope.search.area}">
</div>
<!-- /.Container -->
<script type="text/javascript">
    $(document).ready(function () {
        setup();
        var selectedArea = $("#selectedArea").val();
        if (selectedArea == "") {
            preselect('请选择');
        } else {
            preselect(selectedArea);
        }

    });
</script>
