<%--
  Created by IntelliJ IDEA.
  User: zodiake
  Date: 2014/6/11
  Time: 11:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!-- /.Detail Main -->
<dt>
<ul class="list-inline">
    <li id="detail" class="active"><a>详细信息</a></li>
    <li id="bidList" data-id="${material.id}">成交记录（<bdo>${orderCount}</bdo>）</li>
    <li id="li-commentList" data-id="${material.id}">交易评价（<bdo>${commentCount}</bdo>）</li>
</ul>
</dt>
<dd class="particularity">
    <div class="parti-basic-info">
        <ul>
            <li>成分及含量：${material.category.name}</li>
            <li>适用范围：<c:forEach var="scope" items="${material.materialScope}">
                ${scope.name}&nbsp;&nbsp;
            </c:forEach></li>
            <li>厚薄：${material.materialWidthAndSizeType}</li>
            <li>类型：${material.materialType}</li>
        </ul>
    </div>
    <div style="text-align: left" class="detail-info">${material.fakeContent}</div>
</dd>

<dd>
</dd>
<dd>
</dd>

