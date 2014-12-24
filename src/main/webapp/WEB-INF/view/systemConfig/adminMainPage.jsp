<%--
  Created by IntelliJ IDEA.
  User: Charles
  Date: 2014/7/21
  Time: 19:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div><h6>欢迎回来，${user.name}</h6></div>
<div><h6>今天是<fmt:formatDate value="${date}" pattern="yyyy年MM月dd日 HH:mm" type="date"/></h6></div>
