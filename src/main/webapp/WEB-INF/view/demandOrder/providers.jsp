<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="ask-right">
    <div class="modebox supply-box">
        <div class="hd"><h6>供应商列表</h6></div>
        <div class="con supply-list">
            <ul>
                <c:forEach items="${demandShop}" var="shop">
                    <li>&#8226;&nbsp;&nbsp;<a href="${shop.link}" title="">${shop.title}</a></li>
                </c:forEach>
            </ul>
        </div>
    </div>
    <!--供应商列表 end-->
</div>
