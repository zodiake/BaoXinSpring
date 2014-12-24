<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sbTag" uri="http://zodiake.com" %>
<div class="modebox stand-mail">
    <div class="hd"><h6>站内信</h6></div>
    <div class="con">
        <div class="mail-list">
            <ul>
                <c:forEach var="letter" items="${grid.ecList}" varStatus="status">
                            <li><div class="pic">
                                <c:choose>
                                    <c:when test="${not empty letter.sendUser.logo}">
                                        <img src="${letter.sendUser.logo}"/>
                                    </c:when>
                                    <c:otherwise>
                                        <img src="${pageContext.request.contextPath}/resources/img/btn-th.png"/>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                                <div class="reply-txt">
                                    <c:choose>
                                        <%-- 发件箱 --%>
                                        <c:when test="${fn:trim(letter.sendUser.id)==fn:trim(user.id)}">
                                            <strong>我：</strong>
                                        </c:when>
                                        <%-- 收件箱 --%>
                                        <c:otherwise>
                                            <strong>${letter.sendUser.name}：</strong>
                                        </c:otherwise>
                                    </c:choose>
                                    ${letter.content}
                                </div>
                                <div class="function"><fmt:formatDate value="${letter.createdTime.time}" pattern="yyyy-MM-dd HH:mm:ss" type="date"></fmt:formatDate></div>
                            </li>
                        <%--<c:otherwise>
                            <li><div class="pic"><img src="/resources/pic/designer.png"></div>
                                <div class="reply-txt">
                                    <c:choose>
                                        <c:when test="${fn:trim(letter.sendUser.id)==fn:trim(user.id)}">
                                            <strong>我</strong>
                                            <em class="mail-padd">回复</em>
                                            <strong>${letter.receiveUser.name}：</strong>
                                        </c:when>
                                        <c:otherwise>
                                            <strong>${letter.sendUser.name}</strong>
                                            <em class="mail-padd">回复</em>
                                            <strong>我：</strong>
                                        </c:otherwise>
                                    </c:choose>
                                    <p>${letter.content}</p>
                                </div>
                                <div class="function"><fmt:formatDate value="${letter.createdTime.time}" pattern="yyyy-MM-dd HH:mm:ss" type="date"></fmt:formatDate></div>
                            </li>
                        </c:otherwise>--%>
                </c:forEach>
            </ul>
        </div>
        <div class="mail-reply">
            <form:form  modelAttribute="letter" id="form">
                <input type="hidden" name="receiveUser" value="${receiveId}">
                <input type="hidden" name="letterStatus" value="0">
                <textarea name="content" cols="" rows="" class=" input-t tarea-mail" id="text"></textarea><form:errors path="content" cssClass="error"/>
                <div class="reply-btn"><span class="col-gray">输入<span>0</span>剩余<span>400</span>字</span><a data-type="submit" title="" class=" btn-small btn-blue">回复</a><a data-type="reset" title="" class=" btn-small btn-gray">取消</a></div>
            </form:form>
        </div>
    </div>
    <div>
        <!--翻页-->
        <c:if test="${grid.totalPages>1}">
            <%--<ul class="pagination">
                <li><a href="${pageContext.request.contextPath}/letterCenter/letterDetails?receiveId=${receiveId}&page=${grids.currentPage-1<1?1:grids.currentPage-1}">上一页</a></li>

                <c:forEach varStatus="status" begin="${grid.currentPage-3<=0?1:grid.currentPage-3}" end="${grid.currentPage+3>grid.totalPages?grid.totalPages:grid.currentPage+4}">
                    <li <c:if test='${grid.currentPage == status.current}'> class="active"</c:if>>
                        <a href="${pageContext.request.contextPath}/letterCenter/letterDetails?receiveId=${receiveId}&page=${status.current}">${status.current}</a>
                    </li>
                </c:forEach>
                <li><a href="${pageContext.request.contextPath}/letterCenter/letterDetails?receiveId=${receiveId}&page=${grid.currentPage+1>grid.totalPages?grid.totalPages:grid.currentPage+1}">下一页</a></li>
            </ul>--%>
            <sbTag:page total="${grid.totalPages}" current="${grid.currentPage}" href="${pageContext.request.contextPath}/letterCenter/letterDetails?receiveId=${receiveId}"></sbTag:page>
        </c:if>
        <!--翻页 end-->
    </div>
</div>

