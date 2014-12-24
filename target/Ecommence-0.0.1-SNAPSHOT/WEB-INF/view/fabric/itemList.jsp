<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sbTag" uri="http://zodiake.com" %>
<%--
  Created by IntelliJ IDEA.
  User: zodiake
  Date: 2014/5/14
  Time: 15:00
  To change this template use File | Settings | File Templates.
--%>

<!-- /.Sider Bar -->

<!-- /.Detail Bread -->
<div class="main">
    <div class="bread detail-bread">
        <span>您的位置：</span>
        <ul>
            <li><a href="${pageContext.request.contextPath}/">首页</a></li>
            <li><a href="${pageContext.request.contextPath}/orderCenter/buyerOrderList">我的平台</a></li>
            <li>已发布的产品</li>
        </ul>
    </div>
    <!-- /.Detail Bread -->

    <!-- /.Detail Table -->
    <div class="detail-table">
        <%--<select name="type">
            <option value="4" <c:if test="${type==4}">selected="selected" </c:if>>全部</option>
            <option value="0" <c:if test="${type==0}">selected="selected" </c:if>>草稿</option>
            <option value="1" <c:if test="${type==1}">selected="selected" </c:if>>出售中</option>
            <option value="2" <c:if test="${type==2}">selected="selected" </c:if>>审核中</option>
            <option value="3" <c:if test="${type==3}">selected="selected" </c:if>>下架</option>
        </select>--%>
        <%--<select name="type">
            <option value="4" <c:if test="${type==4}">selected="selected" </c:if>>全部</option>
            <option value="0" <c:if test="${type==0}">selected="selected" </c:if>>面料</option>
            <option value="1" <c:if test="${type==1}">selected="selected" </c:if>>辅料</option>
        </select>--%>
        <label class="label-approve" for="check1"><input name="itemType" id="check1" data-id="${itemType}"
                                                         <c:if test="${itemType=='fabric'}">checked="checked" </c:if>
                                                         type="checkbox" value="fabric"
                                                         class="check-input">面料</label><label
            class="label-approve" for="check2"><input name="itemType" id="check2"
                                                      <c:if test="${itemType=='material'}">checked="checked" </c:if>
                                                      data-id="${itemType}" type="checkbox" value="material"
                                                      class="check-input">辅料</label>

        <form style="display: none" id="form">
            <input name="itemType" id="itemType">
        </form>
        <div class="drop">
            <div class="drop-menu">
                <c:choose>
                    <c:when test="${type==0}">草稿</c:when>
                    <c:when test="${type==1}">出售中</c:when>
                    <c:when test="${type==2}">审核中</c:when>
                    <c:when test="${type==3}">下架</c:when>
                    <c:otherwise>全部</c:otherwise>
                </c:choose>
                <i class="caret"></i></div>
            <div class="drop-content">
                <ul>
                    <c:if test="${type != 0}">
                        <li>
                            <a href="${pageContext.request.contextPath}/sellerCenter/items?type=0&itemType=${itemType}">草稿</a>
                        </li>
                    </c:if>
                    <c:if test="${type != 1}">
                        <li>
                            <a href="${pageContext.request.contextPath}/sellerCenter/items?type=1&itemType=${itemType}">出售中</a>
                        </li>
                    </c:if>
                    <c:if test="${type != 2}">
                        <li>
                            <a href="${pageContext.request.contextPath}/sellerCenter/items?type=2&itemType=${itemType}">审核中</a>
                        </li>
                    </c:if>
                    <c:if test="${type != 3}">
                        <li>
                            <a href="${pageContext.request.contextPath}/sellerCenter/items?type=3&itemType=${itemType}">下架</a>
                        </li>
                    </c:if>
                    <c:if test="${type != 4}">
                        <li>
                            <a href="${pageContext.request.contextPath}/sellerCenter/items?type=4&itemType=${itemType}">全部</a>
                        </li>
                    </c:if>
                </ul>
            </div>
        </div>
        <table class="table table-border">
            <thead>
            <tr>
                <th>图片</th>
                <th>标题</th>
                <th width="20%">发布时间</th>
                <th>类型</th>
                <th>状态</th>
                <th width="20%">操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${grid.ecList}" var="item">
                <tr>
                    <td>
                        <c:choose>
                            <c:when test="${not empty item.coverImage}">
                                <div class="central"><img
                                        src="${pageContext.request.contextPath}${item.coverImage}?size=300">
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="central"><img
                                        src="${pageContext.request.contextPath}/resources/img/btn-th.png">
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td><a href="${pageContext.request.contextPath}/${item.url}/${item.id}">${item.name}</a></td>
                    <td><fmt:formatDate value="${item.createdTime.time}"
                                        pattern="yyyy-MM-dd hh:mm:ss"></fmt:formatDate></td>
                    <td>
                        <c:choose>
                            <c:when test="${item.url == 'fabric'}">
                                面料
                            </c:when>
                            <c:otherwise>
                                辅料
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>${item.state}</td>
                    <td align="center">
                        <c:if test="${item.state=='草稿'}">
                            <a type="button" class="button button-deep"
                               href="${pageContext.request.contextPath}/sellerCenter/${item.url}Edit?id=${item.id}">编辑</a>
                        </c:if>
                        <c:if test="${item.state=='出售中'}">
                            <a type="button" data-id="${item.id}" class="button button-deep down">下架</a>
                        </c:if>
                        <c:if test="${item.state=='下架'}">
                            <a type="button" class="button button-deep"
                               href="${pageContext.request.contextPath}/sellerCenter/${item.url}Edit?id=${item.id}">编辑</a>
                        </c:if>
                        <a type="button" data-id="${item.id}" class="button button-deep clone">复制</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <div class="text-right">
            <c:if test="${grid.totalPages>1}">
                <sbTag:page total="${grid.totalPages}" current="${grid.currentPage}" href="${pageContext.request.contextPath}/sellerCenter/items?type=${type}&itemType=${itemType}"></sbTag:page>
            </c:if>
        </div>
    </div>
    <!-- /.Detail Info -->
</div>

<!-- /.Container -->

