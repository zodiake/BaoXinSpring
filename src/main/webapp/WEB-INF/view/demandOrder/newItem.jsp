<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
    <div class="pic-list">
        <ul>
            <c:forEach items="${newDemands}" var="demand">
                <li><div class="pic"><a href="${demand.link}" title=""><img src="${pageContext.request.contextPath}${demand.coverPath}" ></a></div>
                    <div class="ask-txt">
                        <p>品名：${demand.title}</p>
                        <p>成分：${demand.desc}</p>
                        <p>数量：<span class="col-red">${demand.subTitle}</span>米</p>
                    </div>
                </li>
            </c:forEach>
        </ul>
    </div>