
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!-- /.Cart Step -->
<div class="indent-step indent-step-three">

</div>
<!-- /.Cart Step -->

<!-- /.Company Name -->
<div class="assessment-edit">
    <div class="company-name">
        <dl>
            <dt>企业信息</dt>
            <dd>
                <img class="thumb" src="${pageContext.request.contextPath}${user.logo}">
                <div>
                    <h2><a href="${pageContext.request.contextPath}/provider/${user.id}">${user.companyName}</a></h2>
                    <p><label>被评企业：</label>${user.companyName}</p>
                </div>
            </dd>
        </dl>
    </div>
<!-- /.Company Name -->

<%--<!-- /.Assessment Accord -->--%>
    <form:form modelAttribute="comment" method="post" id="form">
    <dl class="box assessment-accord">
        <dt>与描述相符</dt>
        <dd>
            <div class="row-2">
                <div class="col-1">
                    <p>产品信息</p>
                    <c:forEach var="line" varStatus="st" items="${comment.orderItem.lines}">
                        <c:if test="${st.count==1}">
                            <div class="p-10">
                                <img src="${pageContext.request.contextPath}${line.item.coverImage}">
                                    <a class="p-left-10 p-right-10" href="${pageContext.request.contextPath}/${line.item.url}/${line.item.id}">${line.item.name}</a>
                            </div>
                        </c:if>
                    </c:forEach>
                </div>
                <%--<form:hidden path="item.id"></form:hidden>--%>
                <%--<form:hidden path="orderItem.id"></form:hidden>--%>
                <input type="hidden" name="orderItem" value="${comment.orderItem.id}">
                <div class="col-1">
                    <p>产品与描述相符（打分匿名）</p>
                    <div class="p-10">
                                    <span>
                                        <form:radiobutton path="type" value="1" cssClass="radio"></form:radiobutton>
                                        <i class="icon icon-great"></i>
                                        <label>好评</label>
                                    </span>
                                    <span>
                                        <form:radiobutton path="type" value="2" cssClass="radio"></form:radiobutton>
                                        <i class="icon icon-normal"></i>
                                        <label>中评</label>
                                    </span>
                                    <span>
                                        <form:radiobutton path="type" value="3" cssClass="radio"></form:radiobutton>
                                        <i class="icon icon-bad"></i>
                                        <label>差评</label>
                                    </span>
                                    <span><form:errors path="type" cssClass="error"/> </span>
                                    <span>
                                        <input name="hasName" id="hasName" type="checkbox" value="0" class="check-input commentButton">
                                        <label>匿名评价</label>
                                    </span>
                        <textarea name="content" rows="6" placeholder="感谢您的购买，您的好评是我们的动力。请多多评价我们的产品吧！"></textarea>
                    </div>
                </div>
            </div>
        </dd>
    </dl>
<!-- /.Assessment Accord -->

<!-- /.Assessment Act -->
    <dl class="box assessment-act">
        <dt>企业动态评分</dt>
        <dd>
            <ul>
                <li>
                    <label>服务态度</label>
                    <input name="attitude" value="0" type="hidden">
                    <span class="icon-stars icon-stars-0"></span>
                </li>
                <li>
                    <label>产品满意度</label>
                    <input name="satisfied" value="0" type="hidden">
                    <span class="icon-stars icon-stars-0"></span>
                </li>
                <li>
                    <label>发货速度</label>
                    <input name="deliverySpeed" value="0" type="hidden">
                    <span class="icon-stars icon-stars-0"></span>
                </li>
            </ul>
            <div class="text-center"><input id="buyerComment" class="button button-deep button-big" value="提交评价"></div>
        </dd>
    </dl>

    </form:form>
</div>
