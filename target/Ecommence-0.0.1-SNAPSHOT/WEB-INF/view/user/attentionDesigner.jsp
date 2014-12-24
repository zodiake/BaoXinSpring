<%--
  Created by IntelliJ IDEA.
  User: Charles
  Date: 2014/7/23
  Time: 17:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sbTag" uri="http://zodiake.com" %>
<!--右边-->
<div class="main">
    <!--面包屑-->
    <div class="bread detail-bread">
        <span>您的位置：</span>
        <ul>
            <li><a href="/">首页</a></li>
            <li><a href="${pageContext.request.contextPath}/buyerCenter">我的平台</a></li>
            <li><a href="#">关注的设计师</a></li>
        </ul>
    </div>
    <!--面包屑 end-->
    <!--关注的供应商-->
    <div class="attention-commodity">
        <div class="pic-list commodity-list">
            <ul>
                <c:forEach items="${designerList}" var="designer">
                    <li>
                        <div class="pic">
                            <a href="${linkMap['designerDetail']}${designer.id}" title="${designer.name}">
                            <c:choose>
                                <c:when test="${not empty designer.headUrl}"><img src="${linkMap['designerImg']}${designer.headUrl}"></c:when>
                                <c:otherwise><img src="${pageContext.request.contextPath}/resources/img/btn-th.png"></c:otherwise>
                            </c:choose>
                            </a></div>
                        <div class="name">
                            <a href="${linkMap['designerDetail']}${designer.id}">${designer.name}</a>
                            <c:if test="${ empty designer.name}"><br/></c:if>
                        </div>
                        <div style="text-align: right"><a class="del-icon unlikeDesigner" data-id="${designer.id}" title="取消关注"></a></div>
                    </li>
                </c:forEach>
            </ul>
        </div>
        <!--翻页-->
        <c:if test="${totalPage>1}">
            <%--<ul class="pagination navigationTo">
                <li><a href="${pageContext.request.contextPath}/buyerCenter/attentionDesigner?page=${currentPage-1<1?1:currentPage-1}">上一页</a></li>
                <c:forEach var ="i" begin="${currentPage-3<1?1:currentPage-3}" end ="${currentPage+3>totalPage?totalPage:currentPage+3}" varStatus="status">
                    <li <c:if test='${currentPage == status.index}'> class="active"</c:if>>
                        <a <c:if test='${currentPage == status.index}'> class="disable"</c:if> href="${pageContext.request.contextPath}/buyerCenter/attentionDesigner?page=${status.index}">${status.index}</a>
                    </li>
                </c:forEach>
                <li><a href="${pageContext.request.contextPath}/buyerCenter/attentionDesigner?page=${currentPage+1>totalPage?totalPage:currentPage+1}">下一页</a></li>
            </ul>--%>
            <sbTag:page total="${totalPage}" current="${currentPage}" href="${pageContext.request.contextPath}/buyerCenter/attentionDesigner"></sbTag:page>
        </c:if>
        <!--翻页 end-->
    </div>
    <!--关注的供应商 end-->
</div>

<%--<div class="focus carous" data-view="2" data-speed="slow" data-time="1000">--%>
    <%--<div class="carous-container">--%>
        <%--<ul>--%>
            <%--<li>--%>
            <%--<c:forEach items="${recommendDesigners}" var="recommendDesigner" varStatus="i">--%>
                <%--<c:if test="${i.index<5}">--%>
                    <%--<a href="${linkMap['designerDetail']}${recommendDesigner.id}">--%>
                        <%--<c:choose>--%>
                            <%--<c:when test="${not empty recommendDesigner.headUrl}"><img src="${linkMap['designerImg']}${recommendDesigner.headUrl}" width="150px" height="150px"></c:when>--%>
                            <%--<c:otherwise><img src="${pageContext.request.contextPath}/resources/img/btn-th.png"></c:otherwise>--%>
                        <%--</c:choose>--%>
                    <%--</a>--%>
                    <%--<span><a href="${linkMap['designerDetail']}${recommendDesigner.id}" >${recommendDesigner.name}</a></span>--%>
                <%--</c:if>--%>
            <%--</c:forEach>--%>
            <%--</li>--%>
            <%--<li>--%>
                <%--<c:forEach items="${recommendDesigners}" var="recommendDesigner" varStatus="i">--%>
                    <%--<c:if test="${i.index>4}">--%>
                        <%--<a href="${linkMap['designerDetail']}${recommendDesigner.id}">--%>
                            <%--<c:choose>--%>
                                <%--<c:when test="${not empty recommendDesigner.headUrl}"><img src="${linkMap['designerImg']}${recommendDesigner.headUrl}" width="150px" height="150px"></c:when>--%>
                                <%--<c:otherwise><img src="${pageContext.request.contextPath}/resources/img/btn-th.png"></c:otherwise>--%>
                            <%--</c:choose>--%>
                        <%--</a>--%>
                        <%--<span><a href="${linkMap['designerDetail']}${recommendDesigner.id}" >${recommendDesigner.name}</a></span>--%>
                    <%--</c:if>--%>
                <%--</c:forEach>--%>
            <%--</li>--%>
        <%--</ul>--%>
    <%--</div>--%>
<%--</div>--%>