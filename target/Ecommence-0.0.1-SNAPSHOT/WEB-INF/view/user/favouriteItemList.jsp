<%--
  Created by IntelliJ IDEA.
  User: Charles
  Date: 2014/6/24
  Time: 13:59
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
            <div class="listfn"><a href="${pageContext.request.contextPath}/buyerCenter/favouritesList" title="" class="shape active"><i class="btn-switch"></i></a><a href="${pageContext.request.contextPath}/buyerCenter/favouritesTable" title="" class="shape"><i class="btn-switch btn-list"></i></a></div>
        </div>
        <!--筛选-->
        <div class="commodity-list">
            <table class="commodity-table">
                <colgroup>
                    <col style="width:60%;" />
                    <col style="width:20%;" />
                    <col style="width:20%;" />
                </colgroup>
                <thead>
                <tr>
                    <th>商品</th>
                    <th>单价（元）</th>
                    <th>操作</th>
                </tr>
                </thead>
                <c:forEach var="favouriteItem" items="${grid.ecList}">
                <tbody class="tbody_${favouriteItem.item.id}">
                <tr class="sep-row">
                    <td colspan="3" height="7" class=""></td>
                </tr>
                <tr>
                    <td colspan="3" class="desc-tit"><a href="${pageContext.request.contextPath}/shopCenter/${favouriteItem.item.createdBy.id}/items">${favouriteItem.item.createdBy.name}</a><span class="time">关注时间：<fmt:formatDate value="${favouriteItem.createdTime.time}"  pattern="yyyy-MM-dd HH:mm"></fmt:formatDate></span></td>
                </tr>
                <tr>
                    <td>
                        <div class="desc-pic">
                            <a href="${pageContext.request.contextPath}${favouriteItem.item.url}" title="${favouriteItem.item.name}">
                                <c:choose>
                                    <c:when test="${not empty favouriteItem.item.coverImage}">
                                        <img src="${pageContext.request.contextPath}${favouriteItem.item.coverImage}?size=300" class="pic">
                                    </c:when>
                                    <c:otherwise>
                                        <img src="${pageContext.request.contextPath}/resources/img/btn-th.png" class="pic"/>
                                    </c:otherwise>
                                </c:choose>
                            </a>
                            <div class="desc">
                                <a href="${pageContext.request.contextPath}${favouriteItem.item.url}">${favouriteItem.item.name}</a>
                            </div>
                        </div>
                    </td>
                    <td class="col-red">
                        ${favouriteItem.item.price} (${favouriteItem.item.state})
                    </td>
                    <td>
                        <p><a title="加入购物车" class="button btn-small btn-blue addCart"  data-id="${favouriteItem.item.id}">加入购物车</a></p>
                        <a title="加入调样册" class="button btn-small btn-blue addBook"  data-id="${favouriteItem.item.id}">加入调样册</a>
                        <p><a title="删除" class=" button btn-small btn-gray deleteFavouriteItem"  data-id="${favouriteItem.item.id}">删除</a></p>
                    </td>
                </tr>
                </tbody>
                </c:forEach>
            </table>

        </div>
        <!--翻页-->
        <c:if test="${grid.totalPages>1}">
            <%--<ul class="pagination navigationTo">
                <li><a href="${pageContext.request.contextPath}/buyerCenter/favouritesList?page=${grid.currentPage-1<1?1:grid.currentPage-1}">上一页</a></li>

                <c:forEach varStatus="status" begin="${grid.currentPage-3<0?1:grid.currentPage-3}" end="${grid.currentPage+3>grid.totalPages?grid.totalPages:grid.currentPage+4}">
                    <li <c:if test='${grid.currentPage == status.current}'> class="active"</c:if>>
                        <a href="${pageContext.request.contextPath}/buyerCenter/favouritesList?page=${grid.currentPage}">${status.current}</a>
                    </li>
                </c:forEach>

                <li><a href="${pageContext.request.contextPath}/buyerCenter/favouritesList?page=${grid.currentPage+1>grid.totalPages?grid.totalPages:grid.currentPage+1}">下一页</a></li>
            </ul>--%>
            <sbTag:page total="${grid.totalPages}" current="${grid.currentPage}" href="${pageContext.request.contextPath}/buyerCenter/favouritesList"></sbTag:page>
        </c:if>
        <!--翻页 end-->
    </div>
    <!--关注的供应商 end-->
</div>
</div>
<!--右边 end-->

