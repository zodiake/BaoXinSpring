<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: zodiake
  Date: 2014/6/11
  Time: 11:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- /.Detail Main -->
<dt>
<ul class="list-inline">
    <li id="detail" class="active"><a>详细信息</a></li>
    <li id="bidList" data-id="${fabric.id}">成交记录（<bdo>${orderCount}</bdo>）</li>
    <li id="li-commentList" data-id="${fabric.id}">交易评价（<bdo>${commentCount}</bdo>）</li>
</ul>
</dt>
<dd class="particularity">
    <div class="parti-basic-info">
        <ul>
            <li>成分分类：${fabric.sourceDetail.name}</li>
            <li>成分及含量：${fabric.ingredient}</li>
            <li>纱支：${fabric.yarn}</li>
            <li>密度：${fabric.density}</li>
            <li>幅宽：${fabric.fabricWidthType}<c:if test="${not empty fabric.fabricWidthType}">cm</c:if></li>
            <li>克重：${fabric.fabricHeightType}<c:if test="${not empty fabric.fabricHeightType}">g/m²</c:if></li>
            <li>图案：<c:forEach var="pattern" items="${fabric.patterns}">
                ${pattern.name} &nbsp;
            </c:forEach></li>
            <li>主要用途：<c:forEach var="mainUseType" items="${fabric.mainUseTypes}">
                ${mainUseType.name} &nbsp;
            </c:forEach></li>
            <li>色彩：<ul><c:forEach var="color" items="${fabric.colors}">
                <li style="float:left;width: 22px;position:relative;top:-18px;left:22px;">
                    <div class="boxCont ui-draggable" id="divload0" style="height:20px;width:20px;background:rgb(${color.rgb})" title="${color.id}"></div>
                </li>

            </c:forEach></ul></li>
        </ul>

    </div>
    <div style="text-align: left" class="detail-info">${fabric.fakeContent}</div>
</dd>
<dd>

</dd>
<dd>

</dd>
<!-- /.Detail Main -->

