<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<div class="fixed"></div>
<div class="list-box">
    <span class="close-x"><a href="javascript:void(0);" title="关闭" class="close-this">X</a></span>
    <i class="success-icon"></i>
    <div class="box-con"></div>
    <div class="list-dl fix">
        <c:forEach items="${like}" var="l" varStatus="n">
            <c:if test="${n.index<4}">
                <dl>
                    <dt>
                        <c:choose>
                            <c:when test="${not empty l.cover}">
                                <a href="/material/${l.id}"><img class="thumb" src="${pageContext.request.contextPath}${l.cover}?size=300"></a>
                            </c:when>
                            <c:otherwise>
                                <a href="/material/${l.id}"><img class="thumb" src="${pageContext.request.contextPath}/resources/img/btn-th.png"></a>
                            </c:otherwise>
                        </c:choose>
                    </dt>
                    <dd><a href="${pageContext.request.contextPath}/material/${fabric.id}">${l.title}</a></dd>
                    <dd><i class="red price">${l.price}</i></dd>
                </dl>
            </c:if>
        </c:forEach>
    </div>
</div>
<div class="detail-info">
    <div class="row-2">
        <div class="col-1">
                <div class="magnifier">
                    <div class="magnifier-view central">
                            <c:choose>
                                <c:when test="${not empty image.coverImage}">
                                    <a href="${pageContext.request.contextPath}${image.coverImage}?size=600" class="jqzoom" id="J_large" style="position:relative;">
                                        <img src="${pageContext.request.contextPath}${image.coverImage}" class="tral-img" alt="" height="332" width="378" jqimg="${pageContext.request.contextPath}${image.coverImage}?size=600"/>
                                    </a>
                                </c:when>
                                <c:otherwise>
                                    <a href="${pageContext.request.contextPath}/resources/img/btn-th.png" class="jqzoom" id="J_large" style="position:relative;">
                                        <img src="${pageContext.request.contextPath}/resources/img/btn-th.png" class="tral-img" alt="" height="332" width="378" jqimg="${pageContext.request.contextPath}/resources/img/btn-th_600.png"/>
                                    </a>
                                </c:otherwise>
                            </c:choose>
                    </div>

                    <ul class="magnifier-menu" id="J_picList">
                        <c:choose>
                            <c:when test="${not empty material.images}">
                                <c:forEach items="${material.images}" var="image">
                                    <li data-url="${pageContext.request.contextPath}${image.location}?size=600">
                                        <img src="${pageContext.request.contextPath}${image.location}?size=300">
                                    </li>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <li data-url="${pageContext.request.contextPath}/resources/img/btn-th_600.png">
                                    <img src="${pageContext.request.contextPath}/resources/img/btn-th.png">
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </ul>
                </div>
        </div>
        <div class="col-1">
            <div><h1>${material.name}</h1></div>
            <div>
                <ul class="info-item">
                    <li>
                        <label>产品编码：</label>
                        <span>${material.customId}</span>
                    </li>
                    <li>
                        <label style="vertical-align:top;">起订量：</label>
                    <span class="info-disp">
                        <c:forEach items="${material.showRanges}" var="range">
                            <p><bdo>${range.key}</bdo></p>
                        </c:forEach>
                    </span>
                    <span class="info-disp">
                        <c:forEach items="${material.ranges}" var="range">
                            <p><bdo><b class="price orange font-14"> ${range.value}</b>/${material.materialMeasureType}</bdo></p>
                        </c:forEach>
                    </span>
                    </li>
                    <%--<li>
                        <label>成交/评价：</label>
                        <span>4米成交</span>
                    </li>--%>
                    <c:if test="${material.state == '出售中'}">
                        <li>
                            <div class="button-group button-more-less">
                                <label>订购量：</label>
                                <button class="button button-default" id="reduction">-</button>
                                <input type="text" class="w-60 orderQuantity" id="quantity" name="quantity" value="1">
                                <button class="button button-default" id="plus">+</button>
                            </div>
                            <input type="hidden" id="itemId" name="itemId" value="${material.id}">
                        </li>
                    </c:if>
                </ul>
            </div>

            <c:if test="${currentUserId != fn:trim(user.id)}">
                <c:choose>
                    <c:when test="${material.state == '出售中'}">
                        <div class="row-2">
                            <span class="col-1 detail-button"><a class="button button-default orderImmediately">立即订购</a></span>
                            <span class="col-1 detail-button"><a class="button button-default addCart" data-id="${material.id}">加入购物车</a></span>
                            <span class="col-1 detail-button"><a class="button button-default addFavourite" data-id="${material.id}">添加关注</a></span>
                            <span class="col-1 detail-button"><a class="button button-default addBook" data-id="${material.id}">加入调样册</a></span>
                        </div>
                    </c:when>
                    <c:when test="${material.state != '出售中'}">
                        <div class="row-2">
                            此商品已下架
                        </div>
                    </c:when>
                </c:choose>
            </c:if>
        </div>
    </div>
</div>

