<%--
  Created by IntelliJ IDEA.
  User: Charles
  Date: 2014/6/5
  Time: 15:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!--container-->
<div class="container">
    <!--title-->
    <div class="modebox">
        <div class="hd"><a href="${pageContext.request.contextPath}/notice">公告</a>&nbsp;|&nbsp;<a href="${pageContext.request.contextPath}/info">资讯</a>&nbsp;|&nbsp;<a href="${pageContext.request.contextPath}/masterView">专家访谈</a>&nbsp;|&nbsp;<a href="${pageContext.request.contextPath}/topic1022">云端时尚跨界峰会</a>&nbsp;|&nbsp;<strong><a href="${pageContext.request.contextPath}/topic" style="font-size: 14px;">云端时尚对接会</a></strong></div>
    </div>
    <!--title-->
    <!--左边-->
    <div class=" clearfix information-box">
        <div class="main">
            <div class="con information-details">
                <h2 class="detail-title">${information.title}</h2>
                <div class="detail-time"><fmt:formatDate value="${information.createdTime.time}" pattern="yyyy-MM-dd HH:mm" type="date"/></div>
                <div class="detail-txt">
                    ${information.fakeContent}
                </div>
                <c:if test="${not empty information.videoPath}">
                    <IFRAME src="${information.videoPath}" frameBorder=0 scrolling="no" width="100%" height="400px" style="position:relative;left:0;bottom:0" marginheight="0" allowfullscreen></IFRAME>
                </c:if>
            </div>
            <div>
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