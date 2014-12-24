<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sbTag" uri="http://zodiake.com" %>
<!--产品列表-->
<div class="modebox product-box">
    <div class="hd"><h6>产品列表 Product List (<c:choose>
        <c:when test="${not empty grid.totalRecords}">
            ${grid.totalRecords}
        </c:when>
        <c:otherwise>
            0
        </c:otherwise>
    </c:choose>)</h6>
        <div class="product-search"><a href="#" title="" class="search-btn"></a><input name="proName" type="text" id="searchByProName" data-id="${id}" value="${proName}"></div>
    </div>
    <div class="con">
        <div class="pic-list">
            <ul>
                <c:forEach var="item" items="${grid.ecList}">
                    <li>
                        <div class="pic"><a href="${item.url}" title="${item.name}">
                            <c:choose>
                                <c:when test="${not empty item.coverImage}">
                                    <img src="${item.coverImage}?size=300">
                                </c:when>
                                <c:otherwise>
                                    <img src="${pageContext.request.contextPath}/resources/img/btn-th.png" class="pic"/>
                                </c:otherwise>
                            </c:choose>

                        </a></div>
                        <div class="price">
                            <c:choose>
                                <c:when test="${item.price > 0}">
                                    &yen;${item.price}
                                </c:when>
                                <c:otherwise>
                                    面议
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="name"><p class="text-over"><a href="${item.url}" title="${item.name}">${item.name}</a></p></div>
                        <div class="bargain">
                            成交${item.bidCount}笔<div class="bargain-fun"><%--<a href="#" title="" class="del-icon deleteFavouriteItem" data-id="${item.id}"></a>--%><a href="/shopCenter/${item.createdBy.id}/items" title="供应商主页" class="home-icon"></a>
                            <%--<c:choose>--%>
                                <%--<c:when test="${item.price > 0}">--%>
                                    <%--<a  title="加入购物车" class="shopping-car-icon addCart" data-id="${item.id}">--%>
                                <%--</c:when>--%>
                                <%--<c:otherwise>--%>
                                    <%--<a  title="面议价格不能加入购物车" class="shopping-car-icon" >--%>
                                <%--</c:otherwise>--%>
                            <%--</c:choose>--%>


                            </a><a title="加入调样册" class="album-icon addBook" data-id="${item.id}"></a></div>
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </div>
        <!--翻页-->
            <c:if test="${grid.totalPages>1}">
                <%--<ul class="pagination">
                    <li><a data-url="${pageContext.request.contextPath}/shopCenter/${id}/items?secondCategory=${secondCategory}&type=${type}&page=${grid.currentPage-1<1?1:grid.currentPage-1}">上一页</a></li>
                    <c:forEach varStatus="status" begin="${grid.currentPage-3<=0?1:grid.currentPage-2}" end="${grid.currentPage+3>grid.totalPages?grid.totalPages:grid.currentPage+3}">
                        <li <c:if test='${grid.currentPage == status.current}'> class="active"</c:if>>
                            <a data-url="${pageContext.request.contextPath}/shopCenter/${id}/items?secondCategory=${secondCategory}&type=${type}&page=${status.current}">${status.current}</a>
                        </li>
                    </c:forEach>
                    <li><a data-url="${pageContext.request.contextPath}/shopCenter/${id}/items?secondCategory=${secondCategory}&type=${type}&page=${grid.currentPage+1>grid.totalPages?grid.totalPages:grid.currentPage+1}">下一页</a></li>
                </ul>--%>
                <sbTag:page total="${grid.totalPages}" current="${grid.currentPage}" href="${pageContext.request.contextPath}/shopCenter/${id}/items?secondCategory=${secondCategory}&type=${type}&proName=${proName}"></sbTag:page>
            </c:if>
        <!--翻页 end-->
    </div>
</div>
<!--产品列表 end-->