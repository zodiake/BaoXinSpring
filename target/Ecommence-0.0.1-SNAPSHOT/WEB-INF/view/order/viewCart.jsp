<%--
  Created by IntelliJ IDEA.
  User: Charles
  Date: 2014/6/12
  Time: 17:33
  To change this template use File | Settings | File Templates.
--%>
<%
    response.setHeader("Pragma","No-cache");
    response.setHeader("Cache-Control","no-cache");
    response.setDateHeader("Expires", 0);
%>
<script type="text/javascript">
    $(document).ready(function(){
        var hostUrl = "http://" + window.location.host;
        //购物车浮动页面删除商品
        $(".cartLidelete").click(function () {
            var itemId = $(this).attr("data-id");
            var sellerId = $(this).attr("seller-id");
            if (confirm("是否从购物车中删除?!")) {
                $.ajax({
                    type: "POST",
                    url: hostUrl + "/shoppingCart/delete",
                    data: {
                        id: itemId,
                        sellerId: sellerId
                    },
                    success: function (data) {
                        if (data.result == "success") {
                            $(".cartLi-" + itemId).remove();
                            $(".cartQuantity").html(data.cartQuantity);
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
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<dl>
    <dt>最新加入的商品</dt>
    <c:choose>
        <c:when test="${cartLineMap == null || cartLineMap.size == 0}">
            <dd>购物车中没有商品!
                <div class="balance">
                    <a class="button button-deep" href="${pageContext.request.contextPath}/">去购物</a>
                </div>
            </dd>
        </c:when>
        <c:otherwise>
            <dd>
                <ul>
                    <c:forEach var="cartLine" items="${cartLineMap}">
                        <c:forEach items="${cartLine.value}" var="cartItem">
                            <li class="cartLi-${cartItem.itemId}">
                                <span>
                                    <c:choose>
                                        <c:when test="${not empty cartItem.imgPath}"><img class="thumb" src="${pageContext.request.contextPath}${cartItem.imgPath}?size=100"></c:when>
                                        <c:otherwise>
                                            <img class="thumb" src="${pageContext.request.contextPath}/resources/img/btn-th.png">
                                        </c:otherwise>
                                    </c:choose>
                                    <a href="${pageContext.request.contextPath}/${cartItem.itemType}/${cartItem.itemId}">${cartItem.title}</a>
                                </span>
                                <div>
                                    <b class="price">${cartItem.price}</b>
                                    <bdo>×${cartItem.quantity}</bdo>
                                    <br/>
                                    <a  class="button button-default cartLidelete"  data-id="${cartItem.itemId}" seller-id="${cartItem.supplierId}">删除</a>
                                </div>
                            </li>
                        </c:forEach>
                    </c:forEach>
                </ul>
                <div class="balance">
                    <div>共<bdo>${summaryQuantity}</bdo>件商品　共计<b class="price">${summaryPrice}</b></div>
                    <a class="button button-deep" href="${pageContext.request.contextPath}/shoppingCart/shopCart">去购物车结算</a>
                </div>
            </dd>
        </c:otherwise>
    </c:choose>
</dl>