<%--
  Created by IntelliJ IDEA.
  User: zodiake
  Date: 2014/6/23
  Time: 13:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sbTag" uri="http://zodiake.com" %>
<div class="main">
    <!--面包屑-->
    <div class="bread detail-bread">
        <span>您的位置：</span>
        <ul>
            <li><a href="${pageContext.request.contextPath}/">首页</a></li>
            <li><a href="${pageContext.request.contextPath}/buyerCenter">我的平台</a></li>
            <li><a>关注的供应商</a></li>
        </ul>
    </div>
    <!--面包屑 end-->
    <div class="attention-provision">
        <!--模块一-->
        <c:forEach items="${grid.ecList}" var="list">
            <div class="provision-mode">
                <div class="provision-hd">
                    <!--tabs-->
                    <div class="tabs">
                        <ul>
                            <li class="active" class="new" data-type="new">最新货品</li>
                            <li data-id="${list.shop.user.id}" class="hot" data-type="hot">热销货品</li>
                        </ul>
                    </div>
                    <!--tabs end-->
                    <!--公司名称-->
                    <div class="provis-name">
                        <a href="${pageContext.request.contextPath}/shopCenter/${list.shop.user.id}/items">${list.shop.user.companyName}</a>
                    </div>
                    <!--公司名称 end-->
                    <div class="provis-credit">
                        电商信誉：<span class="icon-stars icon-stars-${fn:substring(list.shop.user.compositeScore.score, 0, 1)}"></span>
                        <a href="" title="删除关注的供应商" data-id="${list.shop.id}" class="del-icon del-shop"></a>
                    </div>
                </div>
                <div class="provision-con" style="display: block">
                    <div class="pic-list">
                        <ul>
                            <c:forEach items="${list.shop.newestItem}" var="item">
                                <li><a href="/${item.url}/${item.id}" title="">
                                    <c:choose>
                                        <c:when test="${not empty item.coverImage}">
                                            <img src="${pageContext.request.contextPath}${item.coverImage}?size=300">
                                        </c:when>
                                        <c:otherwise>
                                            <img src="${pageContext.request.contextPath}/resources/img/btn-th.png" />
                                        </c:otherwise>
                                    </c:choose>
                                </a>
                                    <div class="name text-over"><a href="/${item.url}/${item.id}" title="${item.name}">${item.name}</a></div>
                                    <div class="price">&yen;${item.price}</div>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
                <div class="provision-con" style="display: none">
                    <div class="pic-list">
                        <ul>

                        </ul>
                    </div>
                </div>
            </div>
        </c:forEach>
        <!--模块一 end-->
        <c:if test="${grid.totalPages>1}">
            <%--<ul class="pagination navigationTo" style="display: block" id="newPage">
                <li><a href="${pageContext.request.contextPath}/buyerCenter/favourite/shops?page=1">首页</a></li>

                <c:forEach varStatus="status" begin="${grid.currentPage-3<=1?1:grid.currentPage-3}" end="${grid.currentPage+3>grid.totalPages?grid.totalPages:grid.currentPage+3}">
                    <li <c:if test='${grid.currentPage == status.current}'> class="active"</c:if>>
                        <a href="${pageContext.request.contextPath}/buyerCenter/favourite/shops?page=${status.current}">${status.current}</a>
                    </li>
                </c:forEach>

                <li><a href="${pageContext.request.contextPath}/buyerCenter/favourite/shops?page=${grid.totalPages}">末页</a></li>
            </ul>--%>
            <sbTag:page total="${grids.totalPages}" current="${grids.currentPage}" href="${pageContext.request.contextPath}/buyerCenter/favourite/shop"></sbTag:page>
        </c:if>

        <c:if test="${grid.totalPages>1}">
            <%--<ul class="pagination navigationTo" style="display: none" id="hotPage">
                <li><a data-url="${pageContext.request.contextPath}/buyerCenter/favourite/shops/" data-uri="/hottestItems?page=1">首页</a></li>

                <c:forEach varStatus="status" begin="${grid.currentPage-3<=1?1:grid.currentPage-3}" end="${grid.currentPage+3>grid.totalPages?grid.totalPages:grid.currentPage+3}">
                    <li <c:if test='${grid.currentPage == status.current}'> class="active"</c:if>>
                        <a data-url="${pageContext.request.contextPath}/buyerCenter/favourite/shops/" data-uri="/hottestItems?page=${status.current}">${status.current}</a>
                    </li>
                </c:forEach>

                <li><a data-url="${pageContext.request.contextPath}/buyerCenter/favourite/shops/" data-uri="/hottestItems?page=${grid.totalPages}">末页</a></li>
            </ul>--%>
            <sbTag:page total="${grid.totalPages}" current="${grid.currentPage}" dataUrl="${pageContext.request.contextPath}/buyerCenter/favourite/shops/hottestItems"></sbTag:page>
        </c:if>
    </div>
</div>