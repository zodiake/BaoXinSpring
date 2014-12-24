<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="fixed"></div>
<form:form modelAttribute="letter" method="post" id="form">
    <input type="hidden" name="letterStatus" value="0">
    <div class="lay-message">
        <div class="lay-message-list">
            <ul>
                <li style="display: none">
                    <span class="name">收件人：</span><input name="receiveUser" value="${demandOrder.createdBy.id}" />
                </li>
                <li>
                    <span class="name">站内信内容：</span><textarea name="content" cols="" rows="" class="input-t mess-tarea"></textarea>
                </li>
                <li style="display: none">
                    <span class="name">标题：</span><input name="title" value="${demandOrder.title}" />
                </li>
                <li style="display: none">
                    <span class="name">链接：</span><input name="url"  value="${pageContext.request.contextPath}/demandOrder/${demandOrder.id}"/>
                </li>
                <li style="display: none">
                    <span class="name">来源：</span><input name="refers"  value="demandOrder"/>
                </li>
            </ul>
        </div>
        <div class="lay-message-btn">
            <input type="submit" title="发送" class=" btn-small btn-blue" value="发送"/><a href="#" title="取消" class=" btn-small btn-gray">取消</a>
        </div>
        <a title="" class="close">x</a>
    </div>
</form:form>
<div class="modebox ask-details-box">
    <div class="hd">
        <h6>${demandOrder.title}</h6>
    </div>
    <div class="con">
        <div class="ask-details">
                <div class="magnifier" style="position:relative;">
                    <div class="magnifier-view">
                        <c:choose>
                            <c:when test="${not empty image.coverImage}">
                                <a href="${pageContext.request.contextPath}${image.coverImage}?size=600" class="jqzoom" id="J_large">
                                    <img src="${pageContext.request.contextPath}${image.coverImage}" alt="" height="332" width="378" jqimg="${pageContext.request.contextPath}${image.coverImage}?size=600"/>
                                </a>
                            </c:when>
                            <c:otherwise>
                                <a href="${pageContext.request.contextPath}/resources/img/btn-th.png" class="jqzoom" id="J_large">
                                    <img src="${pageContext.request.contextPath}/resources/img/btn-th.png" alt="" height="347" width="378" jqimg="${pageContext.request.contextPath}/resources/img/btn-th_600.png"/>
                                </a>
                            </c:otherwise>
                        </c:choose>
                       </div>
                    <ul class="magnifier-menu" id="J_picList">
                        <c:forEach items="${demandOrder.images}" var="image">
                            <li data-url="${pageContext.request.contextPath}${image.location}?size=600">
                                <img src="${pageContext.request.contextPath}${image.location}?size=300">
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            <div class="details-info">
                <dl>
                    <dd><span class="distance"><i class="distance-icon"></i>
                        <c:choose>
                            <c:when test="${day >= 0}">
                                距离截止时间：还有<em class="col-red">${day}天</em>
                            </c:when>
                            <c:otherwise>
                                <em class="col-red">已结束</em>
                            </c:otherwise>
                        </c:choose>
                    </span>
                        <span class="issue">
                            <c:choose>
                                <c:when test="${createDay == 0}">
                                    今天发布
                                </c:when>
                                <c:otherwise>
                                    <em class="col-red">${createDay}</em>天前发布
                                </c:otherwise>
                            </c:choose>
                        </span>
                    </dd>
                    <dt>产品求购信息</dt>
                    <dd><strong>采购${demandOrder.demandType}：</strong>${demandOrder.demandSum}${demandOrder.unit}</dd>
                    <dd><strong>采购类型：</strong>${demandOrder.provideType}</dd>
                </dl>
                <dl>
                    <dt>详细要求</dt>
                    <dd>价格区间：${demandOrder.priceFrom}～${demandOrder.priceTo}元</dd>
                    <dd><strong>备注：</strong>${demandOrder.content}</dd>
                </dl>
            </div>
                <sec:authorize access="authenticated" var="authenticated"></sec:authorize>
            <c:if test="${day>=0}">
                <c:choose>
                    <c:when test="${authenticated}">
                        <a href="#" title="" class="btn-small btn-blue leaveComment"> 给买家留言</a>
                    </c:when>
                    <c:otherwise>
                        <a class="btn-small btn-blue">请登录后留言</a>
                    </c:otherwise>
                </c:choose>
            </c:if>
        </div>
        <div class="company-details">
            <h6 class="tit">公司详情</h6>

            <sec:authorize access="authenticated" var="authenticated"></sec:authorize>
            <div class="commpay">
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <colgroup>
                        <col style=" width:15%">
                        <col style=" width:35%">
                        <col style=" width:15%">
                        <col style=" width:35%">
                    </colgroup>
                    <tr>
                        <td class="name">企业名称</td>
                        <td>
                            ${user.companyName}
                        </td>
                        <td class="name">联系人</td>
                        <td>
                            ${user.contactPerson}
                        </td>
                    </tr>
                    <tr>
                        <td class="name">电话</td>
                        <td>
                            ${user.fixedTelephone}
                        </td>
                        <td class="name">手机</td>
                        <td>
                            <c:choose>
                                <c:when test="${authenticated}">
                                    ${user.mobileTelephone}
                                </c:when>
                                <c:otherwise>
                                    ******
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                    <tr>
                        <td class="name">主营产品</td>
                        <td>
                            ${user.mianProductService}
                        </td>
                        <td class="name">地址</td>
                        <td>${user.companyAddr}</td>
                    </tr>
                    <tr>
                        <td class="name">企业类型</td>
                        <td>${user.companyType}</td>
                        <td class="name">注册资本</td>
                        <td>${user.registeredCapitalCurrency}${user.operatingCapital}</td>
                    </tr>
                    <%--<tr>
                        <td class="name">年营业额</td>
                        <td>人分币万元/年</td>
                        <td class="name">员工人数</td>
                        <td>${user.tariff}人</td>
                    </tr>--%>
                </table>
            </div>
        </div>
    </div>
</div>