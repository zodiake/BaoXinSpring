<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head><title>letter</title></head>
<body>
<div>
    站内信详情页面<br/>
    ${letter.receiveUser.name}<br/>
    ${letter.title}<br/>
    ${letter.content}<br/>
</div>

</body>
</html>

