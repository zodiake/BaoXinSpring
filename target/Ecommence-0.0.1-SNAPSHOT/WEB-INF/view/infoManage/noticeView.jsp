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
        <div class="hd"><strong><a href="${pageContext.request.contextPath}/notice" style="font-size: 14px;">公告</a></strong>&nbsp;|&nbsp;<a href="${pageContext.request.contextPath}/info">资讯</a>&nbsp;|&nbsp;<a href="${pageContext.request.contextPath}/topic">8•26专题</a></div>
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
