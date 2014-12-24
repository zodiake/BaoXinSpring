<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
    <dt>辅料分类</dt>
    <dd>
        <form:form modelAttribute="search" method="get" cssClass="search-main">
            <table class="table">
                <tr>
                    <td width="10%">产品类别：</td>
                    <td>
                        <ul class="list-inline no-ipt">
                            <c:forEach items="${firstCategory}" var="c">
                                <li>
                                    <div class="drop">
                                        <span class="drop-menu">${c.name}</span>

                                        <div class="drop-content">
                                            <dl>
                                                <dd>
                                                    <ul class="list-inline">
                                                        <input type="radio" id="nullCategory" name="category" value="">
                                                        <form:radiobuttons path="category" items="${c.secondCategory}"
                                                                           itemLabel="name" itemValue="id" element="li"
                                                                           data-label="产品类别"
                                                                ></form:radiobuttons>
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
                    <td>适用范围：</td>
                    <td>
                        <ul class="list-inline duiqi">
                            <form:checkboxes path="scopes" items="${scopes}" itemLabel="name" cssClass="qr-radio"
                                             itemValue="id" element="li" data-label="适用范围"></form:checkboxes>
                        </ul>
                    </td>
                </tr>
                <tr>
                    <td>厚薄：</td>
                    <td>
                        <ul class="list-inline material-weight">
                            <form:radiobuttons path="weight" items="${width}" element="li"
                                               data-label="厚薄"></form:radiobuttons>
                        </ul>
                    </td>
                </tr>
                <tr>
                    <td>供货方式：</td>
                    <td>
                        <ul class="list-inline duiqi">
                            <form:checkboxes path="materialProvideType" items="${types}" itemValue="id"
                                             cssClass="qr-radio"
                                             itemLabel="name" element="li" data-label="供货方式"></form:checkboxes>
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
            <li><a href="/search/material?keyWord=${key}">${key}</a></li>
        </c:forEach>
    </ul>
</div>
<!-- /.Search Select -->

<!-- /.Search Result -->
<div class="search-result">
    <div class="search-result-panel">
        <ul class="pagination">
            <li><a href="#" data-sort="0">综合</a></li>
            <li><a href="#" data-sort="1">人气</a></li>
            <li><a href="#" data-sort="2">销量</a></li>
            <li id="price-order">
                <a href="#">价格</a>

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
                    <c:forEach items="${lists}" var="material">
                        <tr>
                            <td width="200">
                                <a href="/material/${material.id}">
                                    <c:choose>
                                        <c:when test="${not empty material.cover}">
                                            <img class="thumb"
                                                 src="${pageContext.request.contextPath}${material.cover}?size=300">
                                        </c:when>
                                        <c:otherwise>
                                            <img class="thumb"
                                                 src="${pageContext.request.contextPath}/resources/img/btn-th.png">
                                        </c:otherwise>
                                    </c:choose>
                                </a>
                            </td>
                            <td>
                                <a href="/material/${material.id}">${material.title}</a>

                                <p class="gray">
                                    <span>适用范围：${material.use}</span>|
                                    <span>厚薄：${material.weight}</span>|
                                    <span>分类：${material.category}</span>|
                                    <span>价格：${material.price}</span>|
                                    <span>交易：${material.bidCount}笔</span>|
                                </p>
                            </td>
                            <td align="center" width="100">
                                <c:choose>
                                    <c:when test="${material.price==0.0}">
                                        <bdo class="block orange">面议</bdo>
                                    </c:when>
                                    <c:otherwise>
                                        <bdo class="price block orange">${material.price}</bdo>
                                    </c:otherwise>
                                </c:choose>

                                <p>成交${material.bidCount}笔</p>
                            </td>
                            <td align="center">
                                <p>${material.companyName}</p>
                                <span>${material.area}</span>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <c:if test="${totalPage>1}">
                    <div class="text-right">
                        <sbTag:page total="${totalPage}" current="${currentPage}"></sbTag:page>
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
                    <a href="/material/${s.id}">
                        <c:choose>
                            <c:when test="${empty s.cover}">
                                <img class="thumb" src="${pageContext.request.contextPath}/resources/img/btn-th.jpg">
                            </c:when>
                            <c:otherwise>
                                <img class="thumb" src="${s.cover}&imageSize=300">
                            </c:otherwise>
                        </c:choose>
                    </a>
                    <ul>
                        <li class="text-over">
                            <span><a href="/material/${s.id}" title="${s.title}">${s.title}</a></span>
                        </li>
                        <li>
                            <label>批发价：</label>
                            <span><bdo class="price orange">${s.price}</bdo>元/米</span>
                        </li>
                        <li>
                            <label>销量：</label>
                            <span><bdo class="orange">${s.bidCount}</bdo>件</span>
                        </li>
                    </ul>
                </div>
            </c:forEach>
        </div>
    </dd>
</dl>
<input type="hidden" id="selectedArea" value="${search.area}">
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
