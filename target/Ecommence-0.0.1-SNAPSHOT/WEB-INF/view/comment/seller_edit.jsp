
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--<!-- /.Cart Step -->
<div class="indent-step indent-step-three">

</div>
<!-- /.Cart Step -->--%>
<div class="bread detail-bread">
    <span>您的位置：</span>
    <ul>
        <li><a href="/">首页</a></li>
        <li><a href="/sellerCenter">卖家中心</a></li>
        <li><a href="/orderCenter/sellerOrderList">收到的订单</a></li>
        <li><a>评价</a></li>
    </ul>
</div>
<div class="assessment-edit">
<!-- /.Assessment Accord -->
<form:form modelAttribute="sellerComment" method="post">
<dl class="box assessment-accord">
    <dd>
        <div class="row-2">
            <c:forEach varStatus="st" var="line" items="${sellerComment.orderItem.lines}">
                <c:if test="${st.count==1}">
                    <div class="col-1">
                        <p>产品信息</p>
                        <div class="p-10">
                            <img src="${pageContext.request.contextPath}${line.item.coverImage}">
                            <a class="p-left-10 p-right-10" href="${pageContext.request.contextPath}/${line.item.url}/${line.item.id}">${line.item.name}</a>
                        </div>
                    </div>
                </c:if>
            </c:forEach>
            <%--<form:hidden  path="orderItem.id"></form:hidden>
            <form:hidden path="item.id"></form:hidden>--%>
            <input type="hidden" name="orderItem" value="${sellerComment.orderItem.id}">
          <%--chaping  <form:input path="item"></form:input>--%>
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
                                <span><form:errors path="type"/> </span>
                                <span>
                                    <input name="hasName" id="hasName" type="checkbox" value="0" class="check-input commentButton">
                                    <label>匿名评价</label>
                                </span>
                    <%--<form:textarea path="content" rows="6" ></form:textarea>--%>
                    <textarea name="content" rows="6" placeholder="感谢您的购买，您的好评是我们的动力。请多多评价我们的产品吧！"></textarea>
                </div>
            </div>
        </div>
        <div class="text-center p-bottom-10" id="sellerComment"><input type="submit" class="button button-deep button-big" value="提交评价"></div>
    </dd>
</dl>
</form:form>
</div>