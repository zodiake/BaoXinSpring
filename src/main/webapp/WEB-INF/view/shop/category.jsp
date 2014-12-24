<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!--产品分类-->
<div class="modebox product-classify">
    <c:if test="${not empty fabricCategoryList}">
    <div class="hd"><h6>面料分类</h6></div>
    <div class="con">
        <div class="classify-list">
            <ul>
                <c:forEach var="fabricCategory" items="${fabricCategoryList}">
                    <li><a class="list" data-id="${fabricCategory.id}" data-uid="${id}" data-type="fabric">${fabricCategory.name}<span class="arrow"></span></a>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>
    </c:if>
    <c:if test="${not empty materialCategoryList}">
    <div class="hd"><h6>辅料分类</h6></div>
    <div class="con">
        <div class="classify-list">
            <ul>
                <c:forEach var="materialCategory" items="${materialCategoryList}">
                    <li><a class="list" data-uid="${id}" data-type="material" data-id="${materialCategory.id}">${materialCategory.name}<span class="arrow"></span></a>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>
    </c:if>
</div>
<!--产品分类-->

