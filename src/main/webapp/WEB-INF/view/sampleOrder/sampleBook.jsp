<%--
  Created by IntelliJ IDEA.
  User: Charles
  Date: 2014/7/4
  Time: 15:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- /.Container -->
<div class="container">

    <!-- /.Detail Bread -->
    <div class="bread detail-bread">
        <!-- /.Cart Step -->
        <ol class="steper">
            <li class="active">调样册</li>
            <li>提交调样册</li>
        </ol>
        <!-- /.Cart Step -->
    </div>
    <!-- /.Detail Bread -->

    <!-- /.Order Detail -->
    <div class="order-detail">
        <c:choose>
        <c:when test="${bookMap == null || bookMap.size() == 0}">
            <div class="central">
                <img src="${pageContext.request.contextPath}/resources/img/cart.png">
                调样册中没有商品!
                <a class="button button-deep" href="${pageContext.request.contextPath}/">去调样</a>
            </div>
        </c:when>
        <c:otherwise>
        <table class="table table-border table-order">
            <thead>
            <tr>
                <th colspan="5">商品</th>
                <th width="15%">操作</th>
            </tr>
            </thead>

            <c:forEach var="sampleBook" items="${bookMap}" varStatus="i">
            <tbody>
                <tr class="order-title data_${sampleBook.key}">
                    <th align="left" colspan="6">供应商:${sampleBook.value[0].createdBy.name}</th>
                </tr>
                <c:forEach items="${sampleBook.value}" var="item">
                    <tr class="dataTable_${item.id}">
                        <td><div class="central"><a href="${pageContext.request.contextPath}${item.url}">
                            <c:choose>
                                <c:when test="${not empty item.coverImage}">
                                    <img src="${pageContext.request.contextPath}${item.coverImage}?size=100">
                                </c:when>
                                <c:otherwise><img src="${pageContext.request.contextPath}/resources/img/btn-th_100.png"></c:otherwise>
                            </c:choose>
                        </a></div></td>
                        <td colspan="4"><div class="central"><a href="${pageContext.request.contextPath}${item.url}">${item.name}</a></div></td>
                        <td align="center">
                            <a href="#" class="button button-default delete" data-id="${item.id}">删除</a>
                        </td>
                    </tr>
                </c:forEach>
                <tr>
                    <td colspan="6">
                        <div class="right"><a type="button" class="button button-deep button-big" href="${pageContext.request.contextPath}/orderCenter/sampleCheckOut/${sampleBook.key}">提交调样</a></div>
                    </td>
                </tr>
                <tr class="sep-row">
                    <td colspan="6"></td>
                </tr>
            </tbody>
            </c:forEach>
        </table>
        </c:otherwise>
        </c:choose>
    </div>
    <!-- /.Order Detail -->

</div>
<!-- /.Container -->