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
            <li><a href="#">关注的品牌</a></li>
        </ul>
    </div>
    <!--面包屑 end-->
    <!--关注的供应商-->
    <div class="attention-commodity">
        <div class="pic-list commodity-list">
            <ul>
                <c:forEach items="${brandList}" var="brand">
                    <li>
                        <div class="pic">
                            <a href="${linkMap['brandDetail']}${brand.id}" title="${brand.name}">
                                <c:choose>
                                    <c:when test="${not empty brand.headUrl}"><img src="${linkMap['designerImg']}${brand.headUrl}"></c:when>
                                    <c:otherwise><img src="${pageContext.request.contextPath}/resources/img/btn-th.png"></c:otherwise>
                                </c:choose>
                            </a>
                        </div>
                        <div class="name">
                            <a href="${linkMap['brandDetail']}${brand.id}">${brand.name}</a>
                            <c:if test="${ empty brand.name}"><br/></c:if>
                        </div>
                        <div style="text-align: right"><a class="del-icon unlikeBrand" data-id="${brand.id}" title="取消关注"></a></div>
                    </li>
                </c:forEach>
            </ul>
        </div>
        <!--翻页-->
        <c:if test="${totalPage>1}">
            <%--<ul class="pagination navigationTo">
                <li><a href="${pageContext.request.contextPath}/buyerCenter/attentionBrand?page=${currentPage-1<1?1:currentPage-1}">上一页</a></li>
                <c:forEach var ="i" begin="${currentPage-3<1?1:currentPage-3}" end ="${currentPage+3>totalPage?totalPage:currentPage+3}" varStatus="status">
                    <li <c:if test='${currentPage == status.index}'> class="active"</c:if>>
                        <a <c:if test='${currentPage == status.index}'> class="disable"</c:if> href="${pageContext.request.contextPath}/buyerCenter/attentionBrand?page=${status.index}">${status.index}</a>
                    </li>
                </c:forEach>
                <li><a href="${pageContext.request.contextPath}/buyerCenter/attentionBrand?page=${currentPage+1>totalPage?totalPage:currentPage+1}">下一页</a></li>
            </ul>--%>
            <sbTag:page total="${totalPage}" current="${currentPage}" href="${pageContext.request.contextPath}/buyerCenter/attentionBrand"></sbTag:page>
        </c:if>
        <!--翻页 end-->
    </div>
    <!--关注的供应商 end-->
</div>
<!--右边 end-->