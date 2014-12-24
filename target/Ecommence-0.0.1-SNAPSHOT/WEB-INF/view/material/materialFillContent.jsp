<%--
  Created by IntelliJ IDEA.
  User: zodiake
  Date: 2014/5/14
  Time: 16:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ibForm" uri="http://zodiake.com" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!-- /.Container -->
<div class="container" style="overflow-x: hidden;">

<!-- /.Publish Type -->
<div class="publish-type">
    <label>您当前选择的类目：</label>
    <span>${material.firstCategory.name} » ${material.category.name}</span>
</div>
<!-- /.Publish Type -->

<!-- /.Publish Info -->
<div class="publish-info">
<form:form id="mainForm" method="post" action="${flowExecutionUrl}" modelAttribute="material">
<dl>
    <dt>基本信息填写</dt>
    <dd>
        <ul>
            <li>
                <label><i class="red">*</i>货品名称：</label>
                <form:input path="name"></form:input>
                <form:errors path="name" cssClass="error"></form:errors>
                <p>请在标题中添加产品描述，突出您的产品卖点，使您的产品更具有吸引力！</p>
            </li>
            <li>
                <label><i class="red">*</i>辅料信息：</label>

                <p>请在标题中添加产品描述，突出您的产品卖点，使您的产品更具有吸引力！</p>

                <div>
                    <ul>
                        <li>
                            <label><i class="red">*</i>类型：</label>
                            <form:select path="materialType" items="${type}" itemLabel="name" itemValue="id"></form:select>
                            <form:errors path="materialType" cssClass="error"></form:errors>
                        </li>
                        <li >
                            <label><i class="red">*</i>厚薄：</label>
                            <form:select path="materialWidthAndSizeType" items="${widthType}" itemLabel="name" itemValue="name"></form:select>
                            <form:errors path="materialWidthAndSizeType" cssClass="error"></form:errors>
                        </li>
                        <li class="season_li">
                            <label><i class="red">*</i>适用范围：</label>
                            <ibForm:checkboxes path="materialScope" items="${scope}" itemLabel="name"
                                               itemValue="id"></ibForm:checkboxes>
                            <form:errors path="materialScope" cssClass="error"></form:errors>
                        </li>
                        <li>
                            <label>货号：</label>
                            <form:input path="customId"></form:input>
                            <form:errors path="customId" cssClass="error"></form:errors>
                        </li>
                    </ul>
                </div>
            </li>
        </ul>
    </dd>
</dl>
<dl>
    <dt>产品销售信息</dt>
    <dd>
        <ul>
            <li>
                <label><i class="red">*</i>交易信息：</label>

                <div class="product">
                    <ul>
                        <li class="season_li">
                            <label><i class="red">*</i>供货方式：</label>
                            <ibForm:checkboxes path="materialProvideType" items="${provideType}"
                                               itemValue="id" itemLabel="name"></ibForm:checkboxes>
                            <form:errors path="materialProvideType" cssClass="error"></form:errors>
                        </li>
                        <li>
                            <label><i class="red">*</i>计量单位：</label>
                            <form:select path="materialMeasureType" items="${units}" itemValue="id" itemLabel="name"></form:select>
                            <form:errors path="materialMeasureType" cssClass="error"></form:errors>
                        </li>
                        <li>
                            <label><i class="red">*</i>价格区间：</label>
                            <ul class="qr-inline">
                                <c:choose>
                                    <c:when test="${not empty material.ranges}">
                                        <c:forEach items="${material.ranges}" var="ranges">
                                            <li>
                                                <label>起定量:</label>
                                                <form:input path="keys" value="${ranges.key}"></form:input>
                                                <form:errors path="keys" cssClass="error"></form:errors>
                                                <label class="unit">米及以上：</label>
                                                <form:input path="values" value="${ranges.value}"></form:input>
                                                <label class="per-unit">元/米</label>
                                                <form:errors path="values" cssClass="error"></form:errors>
                                                <a class="qr-delete">删除</a>
                                            </li>
                                        </c:forEach>
                                        <li>
                                            <a class="qr-add">增加</a>
                                        </li>
                                    </c:when>
                                    <c:when test="${not empty material.keys}">
                                        <c:set var="keys" value="${material.keys}"/>
                                        <c:set var="values" value="${material.values}"/>
                                        <c:forEach var="i" begin="0" end="${fn:length(keys)-1}">
                                            <li>
                                                <label>起定量:</label>
                                                <input name="keys" id="keys" value="${material.keys[i]}"
                                                       type="text"/>
                                                <label class="unit">米及以上：</label>
                                                <form:errors path="keys" cssClass="error"></form:errors>
                                                <input name="values" id="values" value="${material.values[i]}"
                                                       type="text"/>
                                                <label class="per-unit">元/米</label>
                                                <form:errors path="values" cssClass="error"></form:errors>
                                                <a class="qr-delete">删除</a>
                                            </li>
                                        </c:forEach>
                                        <li>
                                            <a class="qr-add">增加</a>
                                        </li>
                                    </c:when>
                                    <c:otherwise>
                                        <li>
                                            <label>起定量:</label>
                                            <form:input path="keys" value="${ranges.key}"></form:input>
                                            <label class="unit">米及以上：</label>
                                            <form:errors path="keys" cssClass="error"></form:errors>
                                            <form:input path="values" value="${ranges.value}"></form:input>
                                            <label class="per-unit">元/米</label>
                                            <form:errors path="values" cssClass="error"></form:errors>
                                            <a class="qr-delete">删除</a>
                                        </li>
                                        <li>
                                            <a class="qr-add">增加</a>
                                        </li>
                                    </c:otherwise>
                                </c:choose>
                            </ul>
                        </li>
                        <li>
                            <label><i class="red">*</i>可售总量：</label>
                            <form:input path="availableSum"></form:input>
                            <p id="amount-unit" class="gray">米</p>
                            <form:errors path="availableSum" cssClass="error"></form:errors>
                        </li>
                        <li>
                            <label><i class="red">*</i>发货时间：</label>
                            <form:input path="shipInterval"></form:input>
                            <p class="gray">天内发货</p>
                            <form:errors path="shipInterval" cssClass="error"></form:errors>
                        </li>
                    </ul>
                </div>
            </li>
            <li>
                <label>产品图片:</label>
                <p class="red" id="color-Helper"></p>
                <div>
                    <div class="row-5">
                        <c:choose>
                            <c:when test="${not empty material.tempImages}">
                                <c:forEach begin="0" end="4" var="i">
                                    <div class="col-1">
                                        <img class="thumb" alt="未上传" src="${material.tempImages[i]}">
                                        <input id="fileToUpload${i}" class="btn-thumb" type="file"
                                               name="fileToUpload${i}"/>
                                        <c:if test="${not empty material.tempImages[i]}">
                                            <a class="image-delete"></a>
                                        </c:if>
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
                    <p class="gray">1、第一张图片默认主图，图片尺寸必须为300px及以上，图片不能大于10MB。2、建议图片尺寸在750*750像素以上，图片请避免全文字。</p>
                </div>
            </li>
            <li>
                <label>详细描述:</label>
                <div class="inline-block">
                    <form:textarea path="fakeContent"></form:textarea>
                    <form:errors path="fakeContent" cssClass="error"></form:errors>
                </div>
            </li>
            <li>
                <label>信息有效期限：</label>
                开始：
                <form:input path="validDateFrom" datepicker="true"></form:input>
                结束：
                <form:input path="validDateTo" datepicker="true"></form:input>
                <form:errors path="validDateFrom" cssClass="error"></form:errors>
            </li>
        </ul>
        <div class="publish-info-submit">
            <input type="submit" class="button button-deep" value="暂存" name="_eventId_temporary"></button>
            <input type="submit" class="button button-deep" value="同意协议条款，我要发布" name="_eventId_finish"/>
            <input type="submit" class="button button-deep" value="上一步" name="_eventId_previous"/>
            <span>点击阅读 <a class="deep" target="_new" href="#">时尚窗服务条款</a></span>
        </div>
    </dd>
</dl>
</form:form>
</div>
<!-- /.Publish Info -->

</div>
<!-- /.Container -->

