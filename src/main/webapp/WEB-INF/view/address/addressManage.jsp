<%--
  Created by IntelliJ IDEA.
  User: Charles
  Date: 2014/6/30
  Time: 10:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!--container-->
<div class="main">
    <!--右边-->
        <!--面包屑-->
        <div class="bread detail-bread">
            <span>您的位置：</span>
            <ul>
                <li><a href="#">首页</a></li>
                <li><a href="#">账号管理</a></li>
                <li><a href="#">收货地址管理</a></li>
            </ul>
        </div>
        <!--面包屑 end-->
        <!--收货地址-->
        <div class="modebox security-deliver">
            <div class="con">
                <!--设置成功-->
                <!--div class="deliver-win">
                    <i class="win-icon"></i>默认地址修改成功！
                </div>
                <!--设置成功 end-->
                <div class="deliver-tips"><span class="name col-red">新增收货地址</span>电话号码、手机号选填一项,其余均为必填项<strong><span class="col-red">*</span>表示该项必填</strong></div>
                <form:form modelAttribute="address">
                    <div class="deliver-list">
                        <ul>
                            <li></li>
                            <li><span class="name"><em class="col-red">*</em>所在地区：</span>
                                <form:select path="state" class="select-t addInfoVali">
                                        <form:option value=""></form:option>
                                </form:select>
                                <form:select path="city" class="select-t addInfoVali">
                                        <form:option value=""></form:option>
                                </form:select>
                                <div class="msg-error Hide"></div>
                            </li>
                            <li><div class="name"><em class="col-red">*</em>邮政编码：</div><form:input path="zipCode" class="input-t addInfoVali"></form:input><div class="msg-error Hide"><i class="error-icon"></i>请填写邮编！</div></li>
                            <li><div class="name"><em class="col-red">*</em>街道地址：</div><form:textarea path="street" class="tarea-t addInfoVali"></form:textarea><div class="msg-error Hide"><i class="error-icon"></i>请填写详细地址！</div></li>
                            <li><div class="name"><em class="col-red">*</em>收货人姓名：</div><form:input path="receiverName" class="input-t addInfoVali"></form:input><div class="msg-error Hide"><i class="error-icon"></i>2-20个字符，一个汉字为三个字符！</div></li>
                            <li><div class="name">手机号码：</div><form:input path="mobile" class="input-t" placeholder="手机号码"></form:input><div class="msg-error Hide"><i class="error-icon"></i>电话号码、手机号必须选填一项</div></li>
                            <li><div class="name">电话号码：</div><form:input path="zipPhone" class="input-t input-short" placeholder="区号"></form:input> - <form:input path="phone"  class="input-t input-short" placeholder="号码"></form:input> - <form:input path="childPhone" class="input-t input-short" placeholder="分机号"></form:input><div class="msg-error Hide"><i class="error-icon"></i>电话号码、手机号必须选填一项</div></li>
                            <li><div class="name">设为默认地址：</div>
                                <div class="approve-label">
                                    <label>
                                        <form:checkbox path="defaultAddress" value="0"></form:checkbox>
                                        设置为默认收货地址
                                    </label>
                                </div>
                            </li>
                        </ul>
                    </div>
                    <div class="deliver-btn"><a type="button" class="btn-small btn-blue addressSubmit">保存编辑</a></div>
                </form:form>
                <!--已保存的有效地址-->
                <div class="tbl-deliver-address">
                    <div class="address-tit"><strong class="col-red">已保存的有效地址</strong></div>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="deliver-table">
                        <colgroup>
                            <col style="width:12%">
                            <col style="width:12%">
                            <col style="width:30%">
                            <col style="width:10%">
                            <col style="width:18%">
                            <col style="width:8%">
                            <col style="width:10%">
                        </colgroup>
                        <thead>
                        <tr>
                            <th>收货人</th>
                            <th>所在地区</th>
                            <th>街道地址</th>
                            <th>邮编</th>
                            <th>电话/手机</th>
                            <th></th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${addresses}" var="address" varStatus="i">
                            <tr <c:if test="${i.index%2==1}"> class="active"</c:if> class="tr_${address.id}">
                                <td align="center" valign="middle">${address.receiverName}</td>
                                <td valign="middle">${address.state} ${address.city}</td>
                                <td valign="middle">${address.street}</td>
                                <td align="center" valign="middle">${address.zipCode}</td>
                                <td align="center" valign="middle">
                                ${address.mobile}
                                <c:if test="${not empty address.phone}"><br/>${address.zipPhone}-${address.phone}-${address.childPhone}</c:if>
                                </td>
                                <td align="center" valign="middle" class="addressStatus">
                                    <c:choose>
                                        <c:when test="${defaultAdd.id eq address.id}">默认地址</c:when>
                                        <c:otherwise><a href="#" data-id="${address.id}" class="defaultAddress">设为默认</a></c:otherwise>
                                    </c:choose>
                                 </td>
                                <td valign="middle"><a href="${pageContext.request.contextPath}/buyerCenter/address/${address.id}" title="">修改</a><em class="line">|</em><a href="#" data-id="${address.id}" class="deleteAddress">删除</a></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <p class="tbl-attach">最多保存20个有效地址</p>
                </div>
                <!--已保存的有效地址 end-->
            </div>
        </div>
        <!--收货地址 end-->
    <!--右边 end-->
</div>
<!--container end-->
<script type="application/javascript">
    $(document).ready(function(){
        setup();
        preselect('上海市');
    });
</script>