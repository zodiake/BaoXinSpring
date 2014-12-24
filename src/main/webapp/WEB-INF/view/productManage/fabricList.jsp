<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: Charles
  Date: 2014/6/12
  Time: 15:00
  To change this template use File | Settings | File Templates.
--%>

<!-- /.Detail Table -->
<div class="detail-table">
    <select>
        <option>草稿</option>
        <option>出售中</option>
        <option>审核中</option>
        <option>下架</option>
    </select>
    <table class="table table-border">
        <thead>
        <tr>
            <th> 商品编号</th>
            <th>标题</th>
            <th>分类</th>
            <th width="20%">发布时间</th>
            <th>状态</th>
            <th width="20%">发布人</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${grid.ecList}" var="item">
            <tr>
                <td>
                    <div class="central"><img src="${pageContext.request.contextPath}/resources/pic/model.jpg"></div>
                </td>
                <td><a href="${pageContext.request.contextPath}/${item.url}/${item.id}">${item.name}</a></td>
                <td><fmt:formatDate value="${item.createdTime.time}"
                                    pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></td>
                <td>${item.state}</td>
                <td align="center">
                    <c:if test="${item.state=='草稿'}">
                        <a type="button" class="button button-deep">编辑</a>
                        <a type="button" class="button button-deep">删除</a>
                    </c:if>
                    <c:if test="${item.state=='出售中'}">
                        <a type="button" data-id="${item.id}" class="button button-deep down">下架</a>
                    </c:if>
                    <c:if test="${item.state=='下架'}">
                        <a type="button" class="button button-deep"
                           href="${pageContext.request.contextPath}/sellerCenter/${item.url}Edit?id=${item.id}">编辑</a>
                    </c:if>
                    <c:if test="${item.state==''}"></c:if>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="text-right">
        <c:if test="${grid.totalPages>0}">
            <ul class="pagination">
                <li><a data-url="${pageContext.request.contextPath}/sellerCenter/items?type=${type}&page=1">首页</a></li>

                <c:forEach varStatus="status" begin="${grid.currentPage-3<0?1:grid.currentPage-3}" end="${grid.currentPage+3>grid.totalPages?grid.totalPages:grid.currentPage+4}">
                    <li <c:if test='${grid.currentPage == status.current}'>class="active"</c:if>>
                        <a data-url="${pageContext.request.contextPath}/sellerCenter/items?type=${type}&page=${grid.currentPage}">${status.current}</a>
                    </li>
                </c:forEach>

                <li><a data-url="${pageContext.request.contextPath}/sellerCenter/items?type=${type}page=${grid.totalPages}">末页</a></li>
            </ul>
        </c:if>
    </div>
</div>
<!-- /.Detail Info -->


<!-- /.Container -->

