<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sbTag" uri="http://zodiake.com" %>
<div class="main">
    <!--面包屑-->
    <div class="bread detail-bread">
        <span>您的位置：</span>
        <ul>
            <li><a href="${pageContext.request.contextPath}/">首页</a></li>
            <li><a href="${pageContext.request.contextPath}/orderCenter/buyerOrderList">我的平台</a></li>
            <li>我的求购单列表</li>
        </ul>
    </div>
    <!--面包屑 end-->
    <!--关注的供应商-->
        <!--筛选-->
        <%--<div class="sortbar">
            <select>
                <option value="5" <c:if test="${status == 5}">selected="selected" </c:if>>全部</option>
                <option value="0" <c:if test="${status == 0}">selected="selected" </c:if>>等待卖家发货</option>
                <option value="3" <c:if test="${status == 3}">selected="selected" </c:if>>已取消</option>
                <option value="4" <c:if test="${status == 4}">selected="selected" </c:if>>被拒绝</option>
                <option value="1" <c:if test="${status == 1}">selected="selected" </c:if>>卖家已发货</option>
                <option value="2" <c:if test="${status == 2}">selected="selected" </c:if>>确认收货</option>
            </select>
            <!-- 隐藏form，用于提交下拉框 -->
            <form id="form"><input type="hidden" name="type"></form>
        </div>--%>
        <!--筛选-->
        <div class="detail-table">
            <table class="table table-border table-order">
                <colgroup>
                    <col style="width:25%;" />
                    <col style="width:21%;" />
                    <col style="width:10%;" />
                    <col style="width:10%;" />
                    <col style="width:10%;" />
                    <col style="width:15%;" />
                </colgroup>
                <thead>
                <tr>
                    <th>商品</th>
                    <th>求购说明</th>
                    <th>有效期开始</th>
                    <th>有效期结束</th>
                    <th>状态</th>
                    <th>操作</th>
                </tr>
                </thead>
                <c:forEach var="demandOrder" items="${grids.ecList}">
                <tbody>
                <tr class="sep-row">
                    <td colspan="6" height="7" class=""></td>
                </tr>
                <tr class="order-title">
                    <th colspan="6">
                        <span class="left">
                            求购单号：<a href="${pageContext.request.contextPath}/demandOrder/${demandOrder.id}"> ${demandOrder.demandOrderNo}</a>
                        </span>
                    </th>
                </tr>
                <tr>
                    <td>
                        <span class="left">
                            <a href="${pageContext.request.contextPath}/demandOrder/${demandOrder.id}" title="">
                                <c:choose>
                                    <c:when test="${not empty demandOrder.coverImage}">
                                        <img src="${pageContext.request.contextPath}${demandOrder.coverImage}?size=100">
                                    </c:when>
                                    <c:otherwise>
                                        <img src="${pageContext.request.contextPath}/resources/img/btn-th.png" class="pic">
                                    </c:otherwise>
                                </c:choose>
                            </a>
                            <span class="desc">
                                <a href="${pageContext.request.contextPath}/demandOrder/${demandOrder.id}">${demandOrder.title}</a>
                            </span>
                        </span>
                    </td>
                    <td align="left" style="text-align:left;">
                        <c:choose>
                            <c:when test="${fn:length(demandOrder.content) > 20}">
                                ${fn:substring(demandOrder.content,0,20)}...
                            </c:when>
                            <c:otherwise>
                                ${demandOrder.content}
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td><fmt:formatDate value="${demandOrder.validDateFrom.time}" pattern="yyyy-MM-dd" type="date"></fmt:formatDate></td>
                    <td><fmt:formatDate value="${demandOrder.validDateTo.time}" pattern="yyyy-MM-dd" type="date"></fmt:formatDate></td>
                    <td>${demandOrder.state}</td>
                    <td>
                        <c:if test="${demandOrder.state == '下架'}">
                            <a class="button button-deep" href="/buyerCenter/demandOrder/${demandOrder.id}/edit">编辑</a>
                        </c:if>
                        &nbsp;&nbsp;
                        <c:if test="${demandOrder.state != '下架'}">
                            <a class="button button-deep down_item" data-url="/buyerCenter/demandOrder/${demandOrder.id}/updateStatus?type=2">下架</a>
                        </c:if>
                    </td>
                    <%--<td class="col-red">${demandOrder.state}</td>--%>
                    <%--<td>
                        <c:choose>
                            <c:when test="${demandOrder.state == '等待卖家发货'}">
                                <a href="#" title="取消" class="btn-small btn-gray" data-status="3" data-id="${demandOrder.id}">取消</a>
                            </c:when>
                            <c:when test="${demandOrder.state == '卖家已发货'}">
                                <a href="#" title="确认收货" class="btn-small btn-blue" data-status="2" data-id="${demandOrder.id}">确认收货</a>
                            </c:when>
                        </c:choose>

                    </td>--%>
                </tr>
                </tbody>
                </c:forEach>
            </table>

        </div>
        <!--翻页-->
        <div class="text-right">
        <%--<c:if test="${grids.totalPages>1}">
            <ul class="pagination navigationTo">
                <li><a href="${pageContext.request.contextPath}/buyerCenter/demandOrders?page=${grids.currentPage-1<1?1:grids.currentPage-1}">上一页</a></li>

                <c:forEach varStatus="status" begin="${grids.currentPage-3<=0?1:grids.currentPage-3}" end="${grids.currentPage+3>grids.totalPages?grids.totalPages:grids.currentPage+4}">
                    <li <c:if test='${grids.currentPage == status.current}'> class="active"</c:if>>
                        <a href="${pageContext.request.contextPath}/buyerCenter/demandOrders?page=${status.current}">${status.current}</a>
                    </li>
                </c:forEach>

                <li><a href="${pageContext.request.contextPath}/buyerCenter/demandOrders?page=${grids.currentPage+1>grids.totalPages?grids.totalPages:grids.currentPage+1}">下一页</a></li>
            </ul>
        </c:if>--%>

            <c:if test="${grids.totalPages>1}">
                <sbTag:page total="${grids.totalPages}" current="${grids.currentPage}" href="${pageContext.request.contextPath}/buyerCenter/demandOrders"></sbTag:page>
            </c:if>
        </div>
        <!--翻页 end-->
    <!--关注的供应商 end-->
</div>