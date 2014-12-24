<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- 商品分类 -->
<div class="modebox product-classify">
    <div class="hd"><h6>产品分类</h6></div>
    <div class="con">
        <div class="classify-list-two">
            <c:if test="${not empty categories}">
            <dl>
                <dt class="cover-icon">面料</dt>
                <c:forEach var="category" items="${categories}">
                <dd class="fix">
                    <a data-id="${category.id}" data-type="fabric" data-uid="${fabric.createdBy.id}"  class="list">${category.name}<span class="arrow"></span>
                    </a>
                </dd>
                </c:forEach>
            </dl>
            </c:if>
            <c:if test="${not empty materialCategories}">
            <dl>
                <dt class="assist-icon">辅料</dt>
                <c:forEach var="category" items="${materialCategories}">
                    <dd class="fix">
                        <a data-id="${category.id}" data-type="material" data-uid="${fabric.createdBy.id}"  class="list">${category.name}<span class="arrow"></span>
                        </a>
                    </dd>
                </c:forEach>
            </dl>
            </c:if>
        </div>
    </div>
</div>

<!-- 商品分类 end -->

