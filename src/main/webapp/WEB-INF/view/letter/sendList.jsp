<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head><title>letter</title></head>
<body>
<div>
    站内信发件箱
    <table>
        <tr>
            <td>标题</td>
            <td>收件人</td>
            <td>发件人</td>
        </tr>
        <c:forEach items="${grid.ecList}" var="letter">
            <tr>
                <td><a href="/letterCenter/letter/${letter.id}">${letter.title}</a></td>
                <td>${letter.receiveUser.name}</td>
                <td>${letter.sendUser.name}</td>
            </tr>
        </c:forEach>
    </table>
</div>

</body>
</html>

