<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="ask-left">
    <div class="modebox ask-table-box">
        <div class="hd"><h6>求购信息</h6></div>
        <div class="con">
            <div class="ask-filtrate">
                筛选：
                <select name="type" class="select-t" id="demandType">
                    <option value="all" <c:if test="${type=='all'}">selected="selected" </c:if>>全部</option>
                    <option value="fabric" <c:if test="${type=='fabric'}">selected="selected" </c:if>>面料</option>
                    <option value="material" <c:if test="${type=='material'}">selected="selected" </c:if>>辅料</option>
                </select>
                <input name="content" id="content" type="text" class="input-t" placeholder="求购说明"><a id="demandOrderSearch" title=""class="icon icon-search" data-type="${type}"></a>
                <form id="demandOrderForm" style="display: none">
                    <input name="type">
                    <input name="content">
                </form>
            </div>
            <div class="ask-table">
                <table class="table">
                    <colgroup>
                        <col style="width:17%;">
                        <col style="width:10%;">
                        <col style="width:37%;">
                        <col style="width:18%;">
                        <col style="width:18%;">
                    </colgroup>
                    <thead>
                    <tr>
                        <th>品名</th>
                        <th>类别</th>
                        <th>求购说明</th>
                        <th>有效开始日期</th>
                        <th>有效截止日期</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="demandOrder" items="${grid.ecList}">
                        <tr>
                            <td align="left"><a href="/demandOrder/${demandOrder.id}" title="">${demandOrder.title}</a></td>
                            <td align="center">${demandOrder.demandType}</td>
                            <td align="left" style="width:288px;">
                                <c:choose>
                                    <c:when test="${fn:length(demandOrder.content) > 30}">
                                        ${fn:substring(demandOrder.content,0 ,30 )}...
                                    </c:when>
                                    <c:otherwise>
                                        ${demandOrder.content}
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td align="center"><fmt:formatDate value="${demandOrder.validDateFrom.time}" pattern="yyyy-MM-dd" type="date"></fmt:formatDate> </td>
                            <td align="center"><fmt:formatDate value="${demandOrder.validDateTo.time}" pattern="yyyy-MM-dd" type="date"></fmt:formatDate></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <!--翻页-->
                <c:if test="${grid.totalPages>0}">
                    <ul class="pagination navigationTo">
                        <li><a href="${pageContext.request.contextPath}/demandOrderList?page=${grid.currentPage-1<=1?1:grid.currentPage-1}">上一页</a></li>

                        <c:forEach varStatus="status" begin="${grid.currentPage-3<=1?1:grid.currentPage-3}" end="${grid.currentPage+3>grid.totalPages?grid.totalPages:grid.currentPage+3}">
                            <li <c:if test='${grid.currentPage == status.current}'> class="active"</c:if>>
                                <a href="${pageContext.request.contextPath}/demandOrderList?page=${status.current}">${status.current}</a>
                            </li>
                        </c:forEach>

                        <li><a href="${pageContext.request.contextPath}/demandOrderList?page=${grid.currentPage+1>grid.totalPages?grid.totalPages:grid.currentPage+1}">下一页</a></li>
                    </ul>
                </c:if>
                <!--翻页 end-->
            </div>
        </div>
    </div>
</div>