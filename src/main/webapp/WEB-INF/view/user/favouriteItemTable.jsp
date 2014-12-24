<%--
  Created by IntelliJ IDEA.
  User: zodiake
  Date: 2014/5/30
  Time: 13:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sbTag" uri="http://zodiake.com" %>
<!-- 弹窗开始 -->
<div class="fixed"></div>
<div class="list-box">
    <span class="close-x"><a href="javascript:void(0);" title="关闭" class="close-this">X</a></span>
    <i class="success-icon"></i>
    <div class="box-con"></div>
</div>
<!-- 弹窗结束 -->
<!--右边-->
<div class="main">
    <!--面包屑-->
    <div class="bread detail-bread">
        <span>您的位置：</span>
        <ul>
            <li><a href="/">首页</a></li>
            <li><a href="${pageContext.request.contextPath}/buyerCenter">我的平台</a></li>
            <li>关注的商品</li>
        </ul>
    </div>
    <!--面包屑 end-->
    <!--关注的供应商-->
    <div class="attention-commodity">
        <!--筛选-->
        <div class="sortbar">
            <strong class="tit">全部商品</strong>
            <div class="listfn"><a href="${pageContext.request.contextPath}/buyerCenter/favouritesList" title="" class="shape"><i class="btn-switch"></i></a><a href="${pageContext.request.contextPath}/buyerCenter/favouritesTable" title="" class="shape active"><i class="btn-switch btn-list"></i></a></div>
        </div>
        <!--筛选-->
        <div class="pic-list commodity-list">
            <ul>
                <c:forEach var="favouriteItem" items="${grid.ecList}">
                    <%--<li class="tbody_${favouriteItem.item.id}">
                        <div class="pic"><a href="${pageContext.request.contextPath}/${favouriteItem.item.url}/${favouriteItem.item.id}" title="${favouriteItem.item.name}"><img src="${pageContext.request.contextPath}/resources/pic/designer.png" class="pic"></a></div>
                        <div class="price">${favouriteItem.item.price}</div>
                        <div class="name"><a href="${pageContext.request.contextPath}/${favouriteItem.item.url}/${favouriteItem.item.id}">${favouriteItem.item.name}</a></div>
                        <div class="bargain">
                            成交${favouriteItem.item.bidCount}笔<div class="bargain-fun"><a  title="" class="del-icon deleteFavouriteItem"   data-id="${favouriteItem.item.id}"></a><a href="#" title="" class="home-icon"   data-id="${favouriteItem.item.id}"></a><a href="#" title="" class="shopping-car-icon addCart"  data-id="${favouriteItem.item.id}"></a></div>
                        </div>
                    </li>--%>
                    <li>
                        <div class="pic"><a href="${pageContext.request.contextPath}${favouriteItem.item.url}" title="">
                            <c:choose>
                                <c:when test="${not empty favouriteItem.item.coverImage}">
                                    <img src="${pageContext.request.contextPath}${favouriteItem.item.coverImage}?size=300">
                                </c:when>
                                <c:otherwise>
                                    <img src="${pageContext.request.contextPath}/resources/img/btn-th.png"/>
                                </c:otherwise>
                            </c:choose>
                        </a></div>
                        <div class="price">
                            <c:choose>
                                <c:when test="${favouriteItem.item.price > 0}">
                                    &yen;${favouriteItem.item.price}
                                </c:when>
                                <c:otherwise>
                                    面议
                                </c:otherwise>
                            </c:choose>
                            (${favouriteItem.item.state})</div>
                        <div class="name text-over"><a href="${pageContext.request.contextPath}${favouriteItem.item.url}" title="${favouriteItem.item.name}">${favouriteItem.item.name}</a></div>
                        <div class="bargain">
                            成交${favouriteItem.item.bidCount}笔<div class="bargain-fun"><a class="del-icon deleteFavouriteItem" data-id="${favouriteItem.item.id}" title="删除关注的商品"></a><a class="home-icon" href="/shopCenter/${favouriteItem.item.createdBy.id}/items" title="供应商主页"></a>
                            <%--<c:choose>--%>
                                <%--<c:when test="${favouriteItem.item.price > 0}">--%>
                                    <%--<a class="shopping-car-icon addCart"  data-id="${favouriteItem.item.id}" title="加入购物车"></a>--%>
                                <%--</c:when>--%>
                                <%--<c:otherwise>--%>
                                    <%--<a class="shopping-car-icon"   title="面议价格不能加入购物车"></a>--%>
                                <%--</c:otherwise>--%>
                            <%--</c:choose>--%>

                            <a class="album-icon addBook"  data-id="${favouriteItem.item.id}" title="加入调样册"></a></div>
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </div>
        <!--翻页-->
        <c:if test="${grid.totalPages>1}">
            <%--<ul class="pagination navigationTo">
                <li><a href="${pageContext.request.contextPath}/buyerCenter/favouritesTable?page=${grid.currentPage-1<1?1:grid.currentPage-1}">上一页</a></li>

                <c:forEach varStatus="status" begin="${grid.currentPage-3<1?1:grid.currentPage-3}" end="${grid.currentPage+3>grid.totalPages?grid.totalPages:grid.currentPage+3}">
                    <li <c:if test='${grid.currentPage == status.current}'> class="active"</c:if>>
                        <a href="${pageContext.request.contextPath}/buyerCenter/favouritesTable?page=${grid.currentPage}">${status.current}</a>
                    </li>
                </c:forEach>

                <li><a href="${pageContext.request.contextPath}/buyerCenter/favouritesTable?page=${grid.currentPage+1>grid.totalPages?grid.totalPages:grid.currentPage+1}">下一页</a></li>
            </ul>--%>
            <sbTag:page total="${grid.totalPages}" current="${grid.currentPage}" href="${pageContext.request.contextPath}/buyerCenter/favouritesTable"></sbTag:page>
        </c:if>
        <!--翻页 end-->
    </div>
    <!--关注的供应商 end-->
</div>

<!--右边 end-->
