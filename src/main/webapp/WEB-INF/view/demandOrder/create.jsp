<%--
  Created by IntelliJ IDEA.
  User: zodiake
  Date: 2014/5/22
  Time: 17:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ibForm" uri="http://zodiake.com" %>
<form:form  modelAttribute="demandOrder" id="form">
<div class="publish-info">
    <%--<form:errors path="*"></form:errors>--%>
    <dl>
        <dt>基本信息填写</dt>
        <dd>
                <ul>
                    <li>
                        <label><i class="red">*</i>求购类型：</label>
                        <select name="demandType">
                            <option value="面料" <c:if test="${demandOrder.demandType == '面料'}">selected="selected" </c:if>>面料</option>
                            <option value="辅料" <c:if test="${demandOrder.demandType == '辅料'}">selected="selected" </c:if>>辅料</option>
                        </select>
                    </li>
                    <li>
                        <label><i class="red">*</i>货品名称：</label>
                        <form:input type="text" path="title"></form:input>
                        <form:errors path="title" cssClass="error"/>
                    </li>
                    <li>
                        <label><i class="red">*</i>供货方式：</label>
                        <ibForm:checkboxes path="provideType" items="${types}" itemLabel="name" itemValue="name" ></ibForm:checkboxes>
                        <form:errors path="provideType" cssClass="error"/>
                    </li>
                    <li>
                        <label>价格区间： </label>
                        <form:input type="text" path="priceFrom" id="priceFrom"></form:input> ～ <form:input type="text" id="priceTo" path="priceTo"></form:input>元
                    </li>
                    <li>
                        <label><i class="red">*</i>计量单位：</label>
                        <form:select path="unit" items="${units}" itemValue="name" itemLabel="name"></form:select>
                    </li>
                    <li>
                        <label><i class="red">*</i>求购总量：</label>
                        <form:input type="text" path="demandSum"></form:input>
                        <form:errors path="demandSum" cssClass="error"/>
                        <p class="gray modifyUnit">
                            <c:choose>
                                <c:when test="${not empty demandOrder.unit}">${demandOrder.unit}</c:when>
                                <c:otherwise>米</c:otherwise>
                            </c:choose>
                        </p>
                    </li>
                    <li>
                        <label><i class="red">*</i>求购说明：</label>
                        <form:textarea path="content" cssStyle="width: 434px;height: 100px;"></form:textarea>
                        <form:errors path="content" cssClass="error"/>
                    </li>
                </ul>

        </dd>
    </dl>
    <dl>
        <dt>产品求购信息</dt>
        <dd>
                <ul>
                    <li>
                        <label>参考图片:</label>

                        <p id="color-Helper" class="red"></p>

                        <div>
                            <div class="row-5">

                                <c:choose>
                                    <c:when test="${not empty demandOrder.tempImages}">
                                        <c:forEach begin="0" end="4" var="i">

                                            <div class="col-1">
                                                <img class="thumb" alt="未上传" src="${demandOrder.tempImages[i]}">
                                                <input id="fileToUpload${i}" class="btn-thumb" type="file"
                                                       name="fileToUpload${i}"/>
                                                <a class="image-delete"></a>
                                            </div>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="col-1">
                                            <img class="thumb" alt="未上传">
                                            <input id="fileToUpload1" class="btn-thumb" type="file" name="fileToUpload1"/>
                                        </div>
                                        <div class="col-1">
                                            <img class="thumb" alt="未上传">
                                            <input id="fileToUpload2" class="btn-thumb" type="file" name="fileToUpload2"/>
                                        </div>
                                        <div class="col-1">
                                            <img class="thumb" alt="未上传">
                                            <input id="fileToUpload3" class="btn-thumb" type="file" name="fileToUpload3"/>
                                        </div>
                                        <div class="col-1">
                                            <img class="thumb" alt="未上传">
                                            <input id="fileToUpload4" class="btn-thumb" type="file" name="fileToUpload4"/>
                                        </div>
                                        <div class="col-1">
                                            <img class="thumb" alt="未上传">
                                            <input id="fileToUpload5" class="btn-thumb" type="file" name="fileToUpload5"/>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <p class="gray">1、第一张图片默认主图，图片长宽必须为300px及以上，图片不能大于10MB。</p>
                        </div>
                    </li>
                    <li>
                        <label><i class="red">*</i>信息有效期限：</label>
                        开始：
                        <form:input type="text" path="validDateFrom" class="validDate" id="validDateFrom"  datepicker="true"></form:input>
                        <form:errors path="validDateFrom" cssClass="error"></form:errors>
                        结束：
                        <form:input type="text" path="validDateTo" class="validDate" id="validDateTo"  datepicker="true"></form:input>
                        <form:errors path="validDateTo" cssClass="error"/>
                    </li>
                </ul>
                <div class="publish-info-submit">
                    <button type="button" class="button button-deep" onclick="history.go(-1)">取消</button>
                    <button type="button" class="button button-deep demandOrderCommit">同意协议条款，我要发布</button>
                    <span>点击阅读 <a class="deep" target="_new" href="#">时尚窗服务条款</a></span>
                </div>
        </dd>
    </dl>
</div>
</form:form>