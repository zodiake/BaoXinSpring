<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="synthesize-info">
    <ul>
        <li><div class="name"><span class="beg"></span></div>
            <p class="beg-t">新增求购：<span class="col-red">${statistics['newDemandCount']}</span></p>
            <p>求购总数：<span class="col-red">${statistics['demandCount']}</span></p>
        </li>
        <li><strong class="name">现货</strong>
            <p>化纤混纺面料</p>
            <p>交织面料</p>
            <p>混纺纱织</p>
        </li>
        <li><strong class="name">库存</strong>
            <p>丝棉针织</p>
            <p>粗纺面料</p>
            <p>灯芯绒面料</p>
        </li>
        <li><strong class="name">尾货</strong>
            <p>麻棉针织</p>
            <p>棉弹针织</p>
            <p>粗纺面料</p>
        </li>
    </ul>
</div>
<!--焦点图-->
<div class="focus carous" data-view="${demandBanner.size()}" data-speed="slow" data-time="6000">
    <div class="focus-list carous-container">
        <ul>
            <c:forEach items="${demandBanner}" var="banner">
                <li><a href="${banner.link}"><img src="${pageContext.request.contextPath}${banner.coverPath}" ></a></li>
            </c:forEach>
        </ul>
    </div>
    <div class="carous-point">
        <div class="carous-thumb">
            <ul style="width: 204px;">
                <c:forEach items="${demandBanner}" var="banner">
                    <li class="carousItem"></li>
                </c:forEach>
            </ul>
        </div>
    </div>
</div>
<!--焦点图 end-->