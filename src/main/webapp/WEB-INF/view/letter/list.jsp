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
                <c:forEach var="letter" items="${grid.ecList}">
                    <li>
                        <div class="pic">
                            <c:choose>
                                <c:when test="${not empty letter.sendUser.logo}">
                                    <img src="${pageContext.request.contextPath}${letter.sendUser.logo}">
                                </c:when>
                                <c:otherwise>
                                    <img src="${pageContext.request.contextPath}/resources/img/btn-th.png"/>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="reply-txt">

                                <c:choose>
                                    <c:when test="${fn:trim(letter.sendUser.id)==fn:trim(user.id)}">
                                    <strong>我</strong> 发送给 <strong>${letter.receiveUser.name}</strong>：
                                    </c:when>
                                    <c:otherwise>
                                    <strong>${letter.sendUser.name}</strong> 发送给 <strong>我</strong>：
                                    </c:otherwise>
                                </c:choose>
                            <c:choose>
                                <c:when test="${fn:trim(letter.sendUser.id)==fn:trim(user.id)}">
                                    <a href="/letterCenter/letterDetails?receiveId=${letter.receiveUser.id}">
                                        <c:choose>
                                            <c:when test="${fn:length(letter.content) > 30}">
                                                <c:choose>
                                                    <c:when test="${fn:contains(letter.content,'<a' )}">
                                                        <c:choose>
                                                            <c:when test="${fn:indexOf(letter.content,'<a' )<=30}">
                                                                ${letter.content}
                                                            </c:when>
                                                            <c:otherwise>
                                                                ${fn:substring(letter.content,0,30)}...
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:when>
                                                    <c:otherwise>
                                                        ${fn:substring(letter.content,0,30)}...
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:when>
                                            <c:otherwise>
                                                ${letter.content}
                                            </c:otherwise>
                                        </c:choose>
                                    </a>
                                </c:when>
                                <c:otherwise>
                                    <a href="/letterCenter/letterDetails?receiveId=${letter.sendUser.id}">
                                        <%--<c:choose>
                                            <c:when test="${fn:length(letter.content) > 30}">
                                                ${fn:substring(letter.content,0,30)}...
                                            </c:when>
                                            <c:otherwise>
                                                ${letter.content}
                                            </c:otherwise>
                                        </c:choose>--%>
                                        <c:choose>
                                            <c:when test="${fn:length(letter.content) > 30}">
                                                <c:choose>
                                                    <c:when test="${fn:contains(letter.content,'<a' )}">
                                                        <c:choose>
                                                            <c:when test="${fn:indexOf(letter.content,'<a' )<=30}">
                                                                ${letter.content}
                                                            </c:when>
                                                            <c:otherwise>
                                                                ${fn:substring(letter.content,0,30)}...
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:when>
                                                    <c:otherwise>
                                                        ${fn:substring(letter.content,0,30)}...
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:when>
                                            <c:otherwise>
                                                ${letter.content}
                                            </c:otherwise>
                                        </c:choose>
                                    </a>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <%--<div class="function"><a href="#" title="" class="reply-link">回复</a>2014-06-24 10:27</div>--%>
                        <c:choose>
                            <c:when test="${fn:trim(letter.sendUser.id)==fn:trim(user.id)}">
                                <div class="function"><a href="#" title="" class="reply-link" data-id="${letter.receiveUser.id}">回复</a><fmt:formatDate value="${letter.createdTime.time}" pattern="yyyy-MM-dd HH:mm:ss" type="date"></fmt:formatDate></div>
                            </c:when>
                            <c:otherwise>
                                <div class="function"><a href="#" title="" class="reply-link" data-id="${letter.sendUser.id}">回复</a><fmt:formatDate value="${letter.createdTime.time}" pattern="yyyy-MM-dd HH:mm:ss" type="date"></fmt:formatDate></div>
                            </c:otherwise>
                        </c:choose>
                    </li>
                </c:forEach>
            </ul>
        </div>
        <form:form modelAttribute="letter" id="formList">
            <div class="mail-reply" style="display: none">
                <input type="hidden" name="receiveUser" >
                <input type="hidden" name="letterStatus" value="0">
                <textarea name="content" cols="" rows="" class=" input-t tarea-mail" id="text"></textarea>
                <form:errors path="content" cssClass="error"/>
                <div class="reply-btn"><span class="col-gray">输入<span>0</span>剩余<span>400</span>字</span><a title="" class=" btn-small btn-blue" data-type="submit">回复</a><a title="" class=" btn-small btn-gray" data-type="reset">取消</a></div>
            </div>
        </form:form>
    </div>
    <div>
        <!--翻页-->
        <c:if test="${grid.totalPages>1}">
            <%--<ul class="pagination navigationTo">
                <li><a href="${pageContext.request.contextPath}/letterCenter/letters?page=${grid.currentPage-1<1?1:grid.currentPage-1}">上一页</a></li>
                <c:forEach varStatus="status" begin="${grid.currentPage-3<=0?1:grid.currentPage-3}" end="${grid.currentPage+3>grid.totalPages?grid.totalPages:grid.currentPage+4}">
                    <li <c:if test='${grid.currentPage == status.current}'> class="active"</c:if>>
                        <a href="${pageContext.request.contextPath}/letterCenter/letters?page=${status.current}">${status.current}</a>
                    </li>
                </c:forEach>
                <li><a href="${pageContext.request.contextPath}/letterCenter/letters?page=${grid.currentPage+1>grid.totalPages?grid.totalPages:grid.currentPage+1}">下一页</a></li>
            </ul>--%>
            <sbTag:page total="${grid.totalPages}" current="${grid.currentPage}" href="${pageContext.request.contextPath}/letterCenter/letters"></sbTag:page>
        </c:if>
        <!--翻页 end-->
    </div>
</div>
