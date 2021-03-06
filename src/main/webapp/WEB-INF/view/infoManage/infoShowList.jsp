<%--
  Created by IntelliJ IDEA.
  User: Charles
  Date: 2014/7/21
  Time: 17:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sbTag" uri="http://zodiake.com" %>
<!--container-->
<div class="container">
    <!--title-->
    <div class="modebox">
        <div class="hd"><a href="${pageContext.request.contextPath}/notice">公告</a>&nbsp;|&nbsp;<strong><a href="${pageContext.request.contextPath}/info" style="font-size: 14px;">资讯</a></strong>&nbsp;|&nbsp;<a href="${pageContext.request.contextPath}/masterView">专家访谈</a>&nbsp;|&nbsp;<a href="${pageContext.request.contextPath}/topic1022">云端时尚跨界峰会</a>&nbsp;|&nbsp;<a href="${pageContext.request.contextPath}/topic">云端时尚对接会</a></div>
    </div>
    <!--title-->
    <!--左边-->
    <div class=" clearfix information-box">
        <div class="main">
            <div class="con information-mode">
                <div class="information-list">
                    <ul>
                        <c:forEach items="${grid.ecList}" var="info">
                            <li>
                                <h6><a href="${pageContext.request.contextPath}/info/${info.id}" title="">${info.title} </a></h6>
                                <p <c:if test="${not empty info.viewPath}">class="txt"</c:if>><a href="${pageContext.request.contextPath}/info/${info.id}" title="">${info.briefContent}</a></p>
                                <p class="time"><fmt:formatDate value="${info.createdTime.time}" pattern="yyyy-MM-dd HH:mm" type="date"/></p>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
                <!--翻页-->
                <div class="text-right">
                    <c:if test="${grid.totalPages>1}">
                        <%--<ul class="pagination navigationTo">--%>
                            <%--<li><a data-url="${pageContext.request.contextPath}/info?page=${grid.currentPage-1<1?1:grid.currentPage-1}">上一页</a></li>--%>

                            <%--<c:forEach varStatus="status" begin="${grid.currentPage-3<1?1:grid.currentPage-3}" end="${grid.currentPage+3>grid.totalPages?grid.totalPages:grid.currentPage+3}">--%>
                                <%--<li <c:if test='${grid.currentPage == status.current}'> class="active"</c:if>>--%>
                                    <%--<a data-url="${pageContext.request.contextPath}/info?page=${status.current}">${status.current}</a>--%>
                                <%--</li>--%>
                            <%--</c:forEach>--%>

                            <%--<li><a data-url="${pageContext.request.contextPath}/info?page=${grid.currentPage+1>grid.totalPages?grid.totalPages:grid.currentPage+1}">下一页</a></li>--%>
                        <%--</ul>--%>
                        <sbTag:page total="${grid.totalPages}" current="${grid.currentPage}" href="${pageContext.request.contextPath}/info?1=1"></sbTag:page>
                    </c:if>
                </div>
                <!--翻页 end-->

            </div>
        </div>
        <!--左边 end-->
        <!--右边-->
        <div class="aside">
            <div class="modebox lining-box">
                <div class="hd"><h6>面料快报</h6></div>
                <div class="con">
                    <div class="lining-play">
                        <h6>女装面料</h6>
                        <div class="lining-list">
                            <ul>
                                <c:forEach items="${ladiesFabric}" var="ladyFabric">
                                    <li>
                                        <a href="${ladyFabric.link}"><img class="thumb" src="${pageContext.request.contextPath}${ladyFabric.coverPath}?size=300"></a>
                                    </li>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>
                    <div class="lining-play">
                        <h6>男装面料</h6>
                        <div class="lining-list">
                            <ul>
                                <c:forEach items="${mensFabric}" var="menFabric">
                                    <li>
                                        <a href="${menFabric.link}"><img class="thumb" src="${pageContext.request.contextPath}${menFabric.coverPath}?size=300"></a>
                                    </li>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!--右边 end-->
    </div>
</div>
<!--container end-->
