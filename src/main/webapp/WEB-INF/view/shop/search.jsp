<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
    <span>共<bdo class="orange">${count}</bdo>家供应商符合要求
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
        <dt>供应商分类</dt>
        <dd>
            <form:form modelAttribute="search" method="get" cssClass="search-main">
                <table class="table">
                    <tr>
                        <td>主营业务：</td>
                        <td>
                            <ul class="list-inline season">
                                <form:checkboxes path="type" items="${types}" element="li"
                                                 data-label="主营业务"></form:checkboxes>
                            </ul>
                        </td>
                    </tr>
                    <tr>
                        <td>注册资本：</td>
                        <td>
                            <ul class="list-inline weight">
                                <li><a data-min="0" data-max="10">10万以下</a></li>
                                <li><a data-min="10" data-max="50">10万-50万</a></li>
                                <li><a data-min="50" data-max="100">50万-100万</a></li>
                                <li><a data-min="100">≥100万</a></li>
                                <li>
                                    <form:input path="minMoney" type="text" class="w-40 input-small"/>
                                    ~
                                    <form:input path="maxMoney" type="text" class="w-40 input-small"/>
                                    <input type="submit" class="button button-small button-deep w-40" value="确认"/>
                                </li>
                                <form:hidden path="minMoney"></form:hidden>
                                <form:hidden path="maxMoney"></form:hidden>
                            </ul>
                        </td>
                    </tr>
                </table>
                <form:hidden path="sort"></form:hidden>
                <form:hidden path="keyWord"></form:hidden>
            </form:form>
        </dd>
    </dl>
    <!-- /.Search Select -->

    <!-- /.Search Result -->
    <div class="search-result"><!--
    <div class="search-result-panel">
        <ul class="pagination">
            <li><a href="#" data-sort="0">综合</a></li>
            <li>
                <a href="#" data-sort="1">人气</a>
            </li>
            <li><a href="#" data-sort="2">销量</a></li>
            <li>
                <a href="#" data-sort="3">价格asc</a>
            </li>
            <li>
                <a href="#" data-sort="4">价格desc</a>
            </li>
        </ul>
				<span>
					<input type="text" class="w-60 input-small" placeholder="最低价">
					~
					<input type="text" class="w-60 input-small" placeholder="最高价">
				</span>
				<span>
					<select>
                        <option>上海</option>
                    </select>
				</span>
        <!--
        <span>
            <button type="button" class="button button-deep">确认</button>
        </span>
    </div>-->
        <div class="search-result-main">
            <c:choose>
                <c:when test="${not empty lists}">
                    <table class="table">
                        <tbody>
                        <c:forEach items="${lists}" var="list">
                            <tr>
                                <td width="200">
                                    <a href="${pageContext.request.contextPath}/shopCenter/${list._id}/items">
                                        <c:choose>
                                            <c:when test="${not empty list.cover}"><img class="thumb"
                                                                                        src="${pageContext.request.contextPath}${list.cover}"></c:when>
                                            <c:otherwise><img class="thumb"
                                                              src="${pageContext.request.contextPath}/resources/img/btn-th.png"></c:otherwise>
                                        </c:choose>
                                    </a>
                                </td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/shopCenter/${list._id}/items">${list.name}</a>

                                    <p class="gray">
                                        <c:if test="${not empty list.type}"><span>供应商品类型：[
                                            <c:forEach items="${list.type}" var="type">
                                                ${type}</span>
                                            </c:forEach>
                                            ]
                                        </c:if>
                                        <c:if test="${not empty list.focus}"><span>主营业务：${list.focus}</span>|</c:if>
                                        <c:if test="${not empty list.money}"><span>注册资本：${list.money}万</span>|</c:if>
                                        <c:if test="${not empty list.scope}"><span>经营范围：${list.scope}</span>|</c:if>
                                        <c:if test="${not empty list.desc}"><span>公司简介：${list.desc}</span></c:if>
                                    </p>
                                </td>
                                <td align="center">
                                    <p>综合评分</p>
                                    <span><i class="icon-stars icon-stars-${fn:substring(list.score,0,1)}"></i></span>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>

                    <c:if test="${pageCount>1}">
                        <div class="text-right">
                            <div class="text-right">
                                <sbTag:page total="${pageCount}" current="${currentPage}" dataPage="true"></sbTag:page>
                            </div>
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
                <c:forEach items="${shops}" var="shop">
                    <div class="col-1">
                        <a href="${pageContext.request.contextPath}/shopCenter/${shop.id}/items">
                            <c:choose>
                                <c:when test="${not empty shop.cover}"><img class="thumb"
                                                                            src="${pageContext.request.contextPath}${shop.cover}"></c:when>
                                <c:otherwise><img class="thumb"
                                                  src="${pageContext.request.contextPath}/resources/img/btn-th.png"></c:otherwise>
                            </c:choose>
                        </a>
                        <ul>
                            <li class="text-over">
                                <span><a href="${pageContext.request.contextPath}/shopCenter/${shop.id}/items"
                                         title="${shop.name}">${shop.name}</a></span>
                            </li>
                            <li>
                                <label>综合评分：</label>
                                <span><i class="icon-stars icon-stars-${fn:substring(shop.score,0,1)}"></i></span>
                            </li>
                            <li>
                                <label>主营业务：</label>
                                <c:forEach items="${shop.type}" var="type">
                                    <span>${type} </span>
                                </c:forEach>
                            </li>
                        </ul>
                    </div>
                </c:forEach>
            </div>
        </dd>
    </dl>

</div>
<!-- /.Container -->

