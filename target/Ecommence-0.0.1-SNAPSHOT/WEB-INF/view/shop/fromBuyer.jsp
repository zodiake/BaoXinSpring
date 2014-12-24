<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sbTag" uri="http://zodiake.com" %>
<div class="main">
    <dl class="box assessment-info">
            <dt>店铺半年内动态评分</dt>
            <dd>
                <ul>
                    <li>
                        <label>描述与宝贝相符</label>
                        <c:choose>
                            <c:when test="${not empty fn:substring(dimensionRate.satisfied,0,1)}">
                                <bdo class="icon-stars icon-stars-${fn:substring(dimensionRate.satisfied,0,1)}"></bdo>
                            </c:when>
                            <c:otherwise>
                                <bdo class="icon-stars icon-stars-0"></bdo>
                            </c:otherwise>
                        </c:choose>
                        <c:choose>
                            <c:when test="${not empty fn:substring(dimensionRate.satisfied,0,1)}">
                                <div class="process-block"><i class="process-control" style=" width:${fn:substring(dimensionRate.satisfied,0,1)/5*100}%" ></i><div class="process-tips" style="left:${fn:substring(dimensionRate.satisfied,0,1)*76}px;">${fn:substring(dimensionRate.satisfied,0,3)}<i class="process-tips-arrow"></i></div></div>
                            </c:when>
                            <c:otherwise>
                                <div class="process-block"><i class="process-control" style=" width:0%" ></i><div class="process-tips">0<i class="process-tips-arrow"></i></div></div>
                            </c:otherwise>
                        </c:choose>
                    </li>
                    <li>
                        <label>卖家的服务态度</label>
                        <c:choose>
                            <c:when test="${not empty fn:substring(dimensionRate.attitude,0,1)}">
                                <bdo class="icon-stars icon-stars-${fn:substring(dimensionRate.attitude,0,1)}" ></bdo>
                            </c:when>
                            <c:otherwise>
                                <bdo class="icon-stars icon-stars-0"></bdo>
                            </c:otherwise>
                        </c:choose>
                        <c:choose>
                            <c:when test="${not empty fn:substring(dimensionRate.attitude,0,1)}">
                                <div class="process-block"><i class="process-control" style=" width:${fn:substring(dimensionRate.attitude,0,1)/5*100}%"></i><div class="process-tips" style="left:${fn:substring(dimensionRate.attitude,0,1)*76}px;" id="addAttu">${fn:substring(dimensionRate.attitude,0,3)}<i class="process-tips-arrow"></i></div></div>
                            </c:when>
                            <c:otherwise>
                                <div class="process-block"><i class="process-control" style="width:0%"></i><div class="process-tips" <%--style="left:380px;"--%> >0<i class="process-tips-arrow"></i></div></div>
                            </c:otherwise>
                        </c:choose>
                    </li>
                    <li>
                        <label>卖家的发货速度</label>
                        <c:choose>
                            <c:when test="${not empty fn:substring(dimensionRate.deliverySpeed,0,1)}">
                                <bdo class="icon-stars icon-stars-${fn:substring(dimensionRate.deliverySpeed,0,1)}"></bdo>
                            </c:when>
                            <c:otherwise>
                                <bdo class="icon-stars icon-stars-0" data-id="0"></bdo>
                            </c:otherwise>
                        </c:choose>
                        <c:choose>
                            <c:when test="${not empty fn:substring(dimensionRate.deliverySpeed,0,1)}">
                                <div class="process-block"><i class="process-control" style=" width:${fn:substring(dimensionRate.deliverySpeed,0,1)/5*100}%"></i><div class="process-tips" style="left:${fn:substring(dimensionRate.deliverySpeed,0,1)*76}px;">${fn:substring(dimensionRate.deliverySpeed,0,3)}<i class="process-tips-arrow"></i></div></div>
                            </c:when>
                            <c:otherwise>
                                <div class="process-block"><i class="process-control" style=" width:0%"></i><div class="process-tips">0<i class="process-tips-arrow"></i></div></div>
                            </c:otherwise>
                        </c:choose>
                    </li>
                    <li>
                        <p>
                            <bdo>1分<b>非常不满</b></bdo>
                            <bdo>2分<b>不满意</b></bdo>
                            <bdo>3分<b>一般</b></bdo>
                            <bdo>4分<b>满意</b></bdo>
                            <bdo>5分<b>非常满意</b></bdo>
                        </p>
                    </li>
                </ul>

                <!-- /.Assessment Credit -->
                <div class="assessment-credit">
                    <span>卖家信用累计</span>
                    <table class="table table-border table-hover">
                        <thead>
                        <tr>
                            <th></th>
                            <th>最近1周</th>
                            <th>最近1个月</th>
                            <th>最近6个月</th>
                            <th>6个月前</th>
                            <th>总计</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="sellerCredit" items="${sellerCredits}">
                            <tr>
                                <td>${sellerCredit.type}</td>
                                <td>${sellerCredit.weekCount}</td>
                                <td>${sellerCredit.oneMonthCount}</td>
                                <td>${sellerCredit.sixMonthCount}</td>
                                <td>${sellerCredit.sixMonthBeforeCount}</td>
                                <td>${sellerCredit.total}</td>
                            </tr>
                        </c:forEach>
                        <tr>
                            <td>总计</td>
                            <td>${sellerCredit.weekCount}</td>
                            <td>${sellerCredit.oneMonthCount}</td>
                            <td>${sellerCredit.sixMonthCount}</td>
                            <td>${sellerCredit.sixMonthBeforeCount}</td>
                            <td>${sellerCredit.total}</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <!-- /.Assessment Credit -->
            </dd>
    </dl>
    <dl class="detail-main assessment-tab">
        <dt>
        <ul class="list-inline">
            <li id="fromBuyerComment" class="active">买家评我</li>
            <li id="fromSeller" data-id="${id}">卖家评我</li>
            <li id="sellerSend" data-id="${id}">给他人的评价</li>
        </ul>
        </dt>
        <dd>
            <table class="table table-none">
                <thead>
                <tr id="fromBuyer">
                    <th width="20%">
                        <select data-url="${pageContext.request.contextPath}/sellerCenter/comments" data-flag="fromBuyer">
                            <option value="0" <c:if test="${type==0}">selected="selected" </c:if>>全部</option>
                            <option value="1" <c:if test="${type==1}">selected="selected" </c:if>>好评</option>
                            <option value="2" <c:if test="${type==2}">selected="selected" </c:if>>中评</option>
                            <option value="3" <c:if test="${type==3}">selected="selected" </c:if>>差评</option>
                        </select>
                        <form style="display:none;">
                            <input type="input" name="type" />
                        </form>
                    </th>
                    <th width="20%">评论内容</th>
                    <th width="20%">评价人</th>
                    <th>产品信息</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="comment" items="${grid.ecList}">
                    <tr>
                        <td>
                            <c:if test="${comment.type == '好评'}">
                                <i class="icon icon-great"></i>
                            </c:if>
                            <c:if test="${comment.type == '中评'}">
                                <i class="icon icon-normal"></i>
                            </c:if>
                            <c:if test="${comment.type == '差评'}">
                                <i class="icon icon-bad"></i>
                            </c:if>

                        </td>
                        <td>
                                ${comment.content}
                            <p><fmt:formatDate value="${comment.createdTime.time}" pattern="yyyy-MM-dd HH:mm:ss" type="date"></fmt:formatDate></p>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${comment.hasName == 1}">
                                    <c:choose>
                                        <c:when test="${fn:length(comment.user.id) > 2}">
                                            ${fn:substring(comment.user.id, 0,1 )}**${fn:substring(comment.user.id, fn:length(comment.user.id)-1, fn:length(comment.user.id))}
                                        </c:when>
                                        <c:otherwise>
                                            ${fn:substring(comment.user.id,0,1)}**${fn:substring(comment.user.id, 1, 2)}
                                        </c:otherwise>
                                    </c:choose>
                                </c:when>
                                <c:otherwise>
                                    ${comment.user.id}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>${comment.item.name}</td>
                    </tr>
                </c:forEach>
                </tbody>
                </tfoot>
                <tr>
                    <td colspan="4">
                        <div class="text-right" id="fromBuyerPage">
                            <c:if test="${grid.totalPages>0}">
                                <%--<ul class="pagination">
                                    <li><a data-url="${pageContext.request.contextPath}/sellerCenter/comments?page=1">上一页</a></li>

                                    <c:forEach varStatus="status" begin="${grid.currentPage-3<=0?1:grid.currentPage-3}" end="${grid.currentPage+3>grid.totalPages?grid.totalPages:grid.currentPage+4}">
                                        <li <c:if test='${grid.currentPage == status.current}'> class="active"</c:if>>
                                            <a data-url="${pageContext.request.contextPath}/sellerCenter/comments?page=${status.current}">${status.current}</a>
                                        </li>
                                    </c:forEach>

                                    <li><a data-url="${pageContext.request.contextPath}/sellerCenter/comments?page=${grid.totalPages}">下一页</a></li>
                                </ul>--%>
                                <sbTag:page total="${grid.totalPages}" current="${grid.currentPage}" dataUrl="${pageContext.request.contextPath}/sellerCenter/comments"></sbTag:page>
                            </c:if>
                        </div>
                    </td>
                </tr>
            </table>
            </table>
        </dd>
        <dd>

        </dd>
        <dd>

        </dd>
    </dl>
</div>



