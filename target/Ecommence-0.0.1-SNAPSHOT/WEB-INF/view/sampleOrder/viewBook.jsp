<%--
  Created by IntelliJ IDEA.
  User: Charles
  Date: 2014/7/4
  Time: 15:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
    $(document).ready(function(){
        var hostUrl = "http://" + window.location.host;
        //购物车浮动页面删除商品
        $(".samLiDelete").click(function () {
            var itemId = $(this).attr("data-id");
            var sellerId = $(this).attr("seller-id");
            if (confirm("是否从调样册中删除?!")) {
                $.ajax({
                    type: "POST",
                    url: hostUrl + "/sampleBook/delete",
                    data: {
                        id: itemId,
                        sellerId: sellerId
                    },
                    success: function (data) {
                        if (data.result == "success") {
                            $(".samLi-" + itemId).remove();
                            $(".sampleQuantity").html(data.sampleQuantity);
                            alert("删除成功");
                        }
                    }
                });
            } else {
                return false;
            }
        });
    });
</script>
<dl>
    <dt>最新加入的商品</dt>
    <c:choose>
        <c:when test="${sampleBook == null || sampleBook.size == 0 || sampleQuantity == 0}">
            <dd>调样册中没有商品! </dd>
            <div class="balance">
                <a class="button button-deep" href="${pageContext.request.contextPath}/">去调样</a>
            </div>
        </c:when>
        <c:otherwise>
            <dd>
                <ul>
                    <c:forEach var="sample" items="${sampleBook}">
                        <c:forEach items="${sample.value}" var="sampleItem">
                            <li class="samLi-${sampleItem.item.id}">
                                <span>
                                    <c:choose>
                                        <c:when test="${not empty sampleItem.item.coverImage}"><img class="thumb" src="${pageContext.request.contextPath}${sampleItem.item.coverImage}?size=100"></c:when>
                                        <c:otherwise>
                                            <img class="thumb" src="${pageContext.request.contextPath}/resources/img/btn-th.png">
                                        </c:otherwise>
                                    </c:choose>
                                    ${sampleItem.item.name}
                                </span>
                                <div><br/>
                                    <a class="button button-default samLiDelete"  data-id="${sampleItem.item.id}" seller-id="${sample.key}">删除</a>
                                </div>
                            </li>
                        </c:forEach>
                    </c:forEach>
                </ul>
                <div class="balance">
                    <div>共<bdo>${sampleQuantity}</bdo>件商品</div>
                    <a class="button button-deep" href="${pageContext.request.contextPath}/sampleBook/sample">提交调样册</a>
                </div>
            </dd>
        </c:otherwise>
    </c:choose>
</dl>