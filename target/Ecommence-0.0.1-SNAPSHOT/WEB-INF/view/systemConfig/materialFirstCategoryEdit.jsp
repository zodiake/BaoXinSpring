<%--
  Created by IntelliJ IDEA.
  User: Charles
  Date: 2014/6/6
  Time: 11:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="main">
    <!-- /.Detail Bread -->
    <div class="bread detail-bread">
        <span>您的位置：</span>
        <ul>
            <li><a href="${pageContext.request.contextPath}/admin">后台管理</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/materialFirstCategory">产品成分一级分类维护</a></li>
            <li>新增产品成分一级分类</li>
        </ul>
    </div>
    <!-- /.Detail Bread -->
    <div class="center publish-info">
        <form:form modelAttribute="firstCategory" action="${pageContext.request.contextPath}/admin/materialFirstCategory/add" method="post">
            <div align="left" style="border: 1px solid cornsilk">
                <ul>
                    <li><span class="name">产品分类：</span><form:input path="name"></form:input></li>
                    <li><span class="name">显示序号：</span><form:input path="sortNo"></form:input></li>
                    <li>
                        <span class="name">是否有效：</span>
                        <form:select path="isValid">
                            <form:option value="0">是</form:option>
                            <form:option value="1">否</form:option>
                        </form:select>
                    </li>
                    <li>
                        <span class="name">已添加的产品分类：</span>
                        <c:forEach var="materialCategory" items="${materialCategories}">
                            ${materialCategory.name}、
                        </c:forEach>
                    </li>
                </ul>
            </div>
            <form:hidden path="id"></form:hidden>
            <div align="center">
                <input type="submit" value="保存" name="_target1" class="button button-deep"/>
                <input type="button" value="取消" name="_cancel"  id="cancelBtn" class="button button-default"/>
            </div>
        </form:form>

    </div>
</div>
