<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!-- /.Detail Info -->
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
                                <a href="/fabric/${l.id}"><img class="thumb"
                                                               src="${pageContext.request.contextPath}${l.cover}?size=300"></a>
                            </c:when>
                            <c:otherwise>
                                <a href="/fabric/${l.id}"><img class="thumb"
                                                               src="${pageContext.request.contextPath}/resources/img/btn-th.png"></a>
                            </c:otherwise>
                        </c:choose>
                    </dt>
                    <dd><a href="${pageContext.request.contextPath}/fabric/${fabric.id}">${l.title}</a></dd>
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
                            <a href="${pageContext.request.contextPath}${image.coverImage}?size=600" class="jqzoom"
                               id="J_large" style="position:relative;">
                                <img src="${pageContext.request.contextPath}${image.coverImage}" class="tral-img"
                                     height="332" width="378"
                                     jqimg="${pageContext.request.contextPath}${image.coverImage}?size=600"/>
                            </a>
                        </c:when>
                        <c:otherwise>
                            <a href="${pageContext.request.contextPath}/resources/img/btn-th.png" class="jqzoom"
                               id="J_large" style="position:relative;">
                                <img src="${pageContext.request.contextPath}/resources/img/btn-th.png" class="tral-img"
                                     height="332" width="378"
                                     jqimg="${pageContext.request.contextPath}/resources/img/btn-th_600.png"/>
                            </a>
                        </c:otherwise>
                    </c:choose>
                </div>

                <ul class="magnifier-menu" id="J_picList">
                    <c:choose>
                        <c:when test="${not empty fabric.images}">
                            <c:forEach items="${fabric.images}" var="image">
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
            <h1>${fabric.name}</h1>
            <ul class="info-item">
                <li>
                    <label>产品编码：</label>
                    <span>${fabric.customId}</span>
                </li>
                <li>
                    <c:choose>
                        <c:when test="${fabric.showRanges.size() > 0}">
                            <label style="vertical-align:top;">起订量${image.coverImage}：</label>
									<span class="info-disp faceToface">
										<c:forEach items="${fabric.showRanges}" var="range">
                                            <c:if test="${range.value >= 0}">
                                                <p data-id="${range.value}"><bdo>${range.key=="0"?"":range.key}</bdo></p>
                                            </c:if>
                                        </c:forEach>
									</span>
									<span class="info-disp">
										<c:forEach items="${fabric.ranges}" var="range">
                                            <p>
                                                <bdo>
                                                    <c:choose>
                                                        <c:when test="${range.value==0}">
                                                            <b class="orange font-14" style="font-weight: bold">面议</b>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <b class="price orange font-14"> ${range.value}</b>
                                                            /${fabric.fabricMeasureType}
                                                        </c:otherwise>
                                                    </c:choose>
                                                </bdo>
                                            </p>
                                        </c:forEach>
									</span>
                        </c:when>
                        <c:otherwise>
                            <label style="vertical-align:top;">起订量：</label>
                                <span class="info-disp">
                                    <p><bdo><b class="orange font-14">面议</b></bdo></p>
                                </span>
                        </c:otherwise>
                    </c:choose>
                </li>
                <c:if test="${fabric.state == '出售中'}">
                    <li>
                        <div class="button-group button-more-less">
                            <label>订购量：</label>
                            <button class="button button-default" id="reduction">-</button>
                            <input type="text" class="w-60 orderQuantity" id="quantity" name="quantity" value="1">
                            <button class="button button-default" id="plus">+</button>
                        </div>
                        <input type="hidden" id="itemId" name="itemId" value="${fabric.id}">
                    </li>
                </c:if>
            </ul>
            <c:if test="${currentUserId != fn:trim(user.id)}">
                <c:choose>
                    <c:when test="${fabric.state == '出售中'}">
                        <div class="row-2">
                            <span class="col-1 detail-button"><a class="button button-default orderImmediately">立即订购</a></span>
                            <span class="col-1 detail-button"><a class="button button-default addCart"
                                                                 data-id="${fabric.id}">加入购物车</a></span>
                            <span class="col-1 detail-button"><a class="button button-default addFavourite"
                                                                 data-id="${fabric.id}">添加关注</a></span>
                            <span class="col-1 detail-button"><a class="button button-default addBook"
                                                                 data-id="${fabric.id}">加入调样册</a></span>
                        </div>
                    </c:when>
                    <c:when test="${fabric.state != '出售中'}">
                        <div class="row-2">
                            此商品已下架
                        </div>
                    </c:when>
                </c:choose>
            </c:if>
        </div>
    </div>
</div>
<!-- /.Detail Info -->


