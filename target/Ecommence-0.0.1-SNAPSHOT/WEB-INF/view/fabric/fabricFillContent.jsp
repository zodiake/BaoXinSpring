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
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ibForm" uri="http://zodiake.com" %>

<!-- /.Container -->
<div class="container" style="overflow-x: hidden;">
<div class="fixed"></div>
<!-- /.Publish Type -->
<div class="publish-type">
    <label>您当前选择的类目：</label>
    <span>${fabric.firstCategory.name} » ${fabric.category.name}</span>
    <span>${fabric.source.name} » ${fabric.sourceDetail.name}</span>
</div>
<!-- /.Publish Type -->

<!-- /.Publish Info -->
<div class="publish-info">
<form:form id="mainForm" method="post" action="${flowExecutionUrl}" modelAttribute="fabric">
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
                <label><i class="red">*</i>面料信息：</label>
                <div>
                    <ul>
                        <li class="season_li">
                            <label><i class="red">*</i>适用季节：</label>
                            <ibForm:checkboxes path="seasons" items="${seasons}" itemValue="id"
                                               cssClass="checkbox"
                                               itemLabel="type"></ibForm:checkboxes>
                            <form:errors path="seasons" cssClass="error"></form:errors>
                        </li>
                        <li>
                            <label><i class="red">*</i>成份及含量：</label>
                            <form:input path="ingredient"></form:input>
                            <form:errors path="ingredient" cssClass="error"></form:errors>
                        </li>
                        <li>
                            <label>纱支：</label>
                            <form:input path="yarn"></form:input>
                            <form:errors path="yarn" cssClass="error"></form:errors>
                        </li>
                        <li>
                            <label>密度：</label>
                            <form:input path="density"></form:input>
                            <form:errors path="density" cssClass="error"></form:errors>
                        </li>
                        <li>
                            <label><i class="red">*</i>幅宽：</label>
                            <form:input path="fakeWeight"></form:input>
                            <p class="gray">cm</p>
                            <form:errors path="fakeWeight" cssClass="error"></form:errors>
                        </li>
                        <li>
                            <label>克重：</label>
                            <form:input path="fabricHeightType"></form:input>
                            <p class="gray">g/m²</p>
                            <form:errors path="fabricHeightType" cssClass="error"></form:errors>
                        </li>
                        <li>
                            <label>染整工艺：</label>
                            <form:select path="fabricTechnologyId" items="${fabricTechnology}"
                                         itemValue="id" itemLabel="name"></form:select>
                            <form:errors path="fabricTechnologyId" cssClass="error"></form:errors>
                            <c:if test="${fabric.fabricSecondTechnologyId!=null}">
                                <form:select path="fabricSecondTechnologyId" items="${st}" itemLabel="name"
                                             itemValue="id"></form:select>
                                <form:errors path="fabricSecondTechnologyId" cssClass="error"></form:errors>
                            </c:if>
                            <c:if test="${fabric.fabricSecondTechnologyId==null}">
                                <select name="fabricSecondTechnologyId" id="fabricSecondTechnologyId">
                                    <option value="" selected>--请选择--</option>
                                </select>
                                <form:errors path="fabricSecondTechnologyId" cssClass="error"></form:errors>
                            </c:if>
                        </li>
                        <li>
                            <label>货号：</label>
                            <form:input path="customId"></form:input>
                        </li>
                        <li class="publish">
                            <label class="pattern" title="请选择图案"><i class="red">*</i>图案：<img src="${pageContext.request.contextPath}/resources/images/PocketAdd.png" style="height: 25px;width: 25px;float: right"></label>
                            <form:errors path="patterns" cssClass="error"></form:errors>
                            <c:if test="${not empty fabric.patterns}">
                                <c:forEach items="${fabric.patterns}" var="pattern">
                                    <span class="publish-pitch" data-id="${pattern.id}">${pattern.name}
                                    </span>
                                </c:forEach>
                            </c:if>

                            <div class="lay-publish">
                                <div class="lay-publish-box">
                                    <div class="lay-publish-list">
                                        <ul>
                                            <c:forEach items="${pattern}" var="pattern">
                                                <li>
                                                    <h3>${pattern.name}</h3>
                                                    <ul>
                                                        <form:checkboxes path="patterns" items="${pattern.children}"
                                                                         element="li" itemLabel="name"
                                                                         itemValue="id"
                                                                         data-name="${pattern.name}"></form:checkboxes>
                                                    </ul>
                                                </li>
                                            </c:forEach>
                                        </ul>
                                    </div>
                                    <span class="arrow"></span>
                                </div>
                            </div>
                        </li>
                        <li class="ml_no">
                            <div class="qr-position-relative">
                                <label class="color_label" title="请选择颜色"><img src="${pageContext.request.contextPath}/resources/images/PocketAdd.png" style="height: 25px;width: 25px;float: right">颜色：</label>
                                <c:forEach items="${fabric.colors}" var="color">
                                    <div class="boxCont color-content" id="divload3" data-title="${color.id}" data-back="${color.rgb}" style="height: 30px; width: 120px; margin-right: 10px; float: left; background-color:rgb(${color.rgb});" title="04-CW-0100330-CK">
                                        <div class="color-title">04-CW-0100330-CK</div>
                                        <i class="color-del"></i>
                                    </div>
                                </c:forEach>
                                <form:errors path="colors" cssClass="error"></form:errors>
                                <div class="box_div lay-publish-box" style="display: none;">
                                    <span class="arrow"></span>
                                    <div>
                                        <ol class="list-inline inline-block">

                                            <div id="tinctCode"></div>
                                            <div id="material"></div>
                                            <img src="${pageContext.request.contextPath}/resources/img/color2.jpg" style="width: 100%;height: 20px;">
                                            <p>
                                                <input type="text" id="amount" value="1"
                                                       style="border:0; color:#f6931f; font-weight:bold;">
                                            </p>
                                            <div id="slider">
                                            </div>
                                            <br/>

                                            <div id="wrapdiv">
                                                <ul id="wrap">
                                                </ul>
                                            </div>
                                        </ol>
                                    </div>

                                </div>
                            </div>
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
            <li class="season_li">
                <label><i class="red">*</i>交易信息：</label>

                <div class="product" >
                    <ul>
                        <li>
                            <label><i class="red">*</i>供货方式：</label>
                            <ibForm:checkboxes path="fabricProvideType"
                                               items="${provideType}" itemValue="id"
                                               itemLabel="name"></ibForm:checkboxes>
                            <form:errors path="fabricProvideType" cssClass="error"></form:errors>
                        </li>
                        <li>
                            <label><i class="red">*</i>计量单位：</label>
                            <form:select path="fabricMeasureType" items="${units}" itemValue="id"
                                         itemLabel="name"></form:select>
                            <form:errors path="fabricMeasureType" cssClass="error"></form:errors>
                        </li>
                        <li>
                            <label><i class="red">*</i>价格区间：</label>

                            <ul class="qr-inline">
                                <c:choose>
                                    <c:when test="${not empty fabric.ranges}">
                                        <c:forEach items="${fabric.ranges}" var="ranges">
                                            <li>
                                                <label>起定量:</label>
                                                <form:input path="keys" value="${ranges.key}"></form:input>
                                                <form:errors path="keys" cssClass="error"></form:errors>
                                                <label class="unit">米及以上：</label>
                                                <form:input path="values" value="${ranges.value}"></form:input>
                                                <form:errors path="values" cssClass="error"></form:errors>
                                                <label class="per-unit">元/米</label>
                                                <a class="qr-delete">删除</a>
                                            </li>
                                        </c:forEach>
                                        <li>
                                            <a class="qr-add">增加</a>
                                        </li>
                                    </c:when>
                                    <c:when test="${not empty fabric.keys}">
                                        <c:set var="keys" value="${fabric.keys}"/>
                                        <c:set var="values" value="${fabric.values}"/>
                                        <c:forEach var="i" begin="0" end="${fn:length(keys)-1}">
                                            <li>
                                                <label>起定量:</label>
                                                <input name="keys" id="keys" value="${fabric.keys[i]}"
                                                       type="text"/>
                                                <label class="unit">米及以上：</label>
                                                <form:errors path="keys" cssClass="error"></form:errors>
                                                <input name="values" id="values" value="${fabric.values[i]}"
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
                            <label>可售总量：</label>
                            <form:input path="availableSum"></form:input>
                            <p class="gray" id="amount-unit">米</p>
                            <form:errors path="availableSum" cssClass="error"></form:errors>
                        </li>
                        <li>
                            <label>发货时间：</label>
                            <form:input path="shipInterval"></form:input>
                            <p class="gray">天内发货</p>
                            <form:errors path="shipInterval" cssClass="error"></form:errors>
                        </li>
                    </ul>
                </div>
            </li>
            <li>
                <label>产品图片:</label>

                <p id="color-Helper" class="red"></p>

                <div>
                    <div class="row-5">
                        <c:choose>
                            <c:when test="${not empty fabric.tempImages}">
                                <c:forEach begin="0" end="4" var="i">
                                    <div class="col-1">
                                        <img class="thumb" alt="未上传" src="${fabric.tempImages[i]}">
                                        <input id="fileToUpload${i}" class="btn-thumb" type="file"
                                               name="fileToUpload${i}"/>
                                        <c:if test="${not empty fabric.tempImages[i]}">
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
            <span>点击阅读 <a class="deep" target="_new">时尚窗服务条款</a></span>
        </div>
    </dd>
</dl>
<c:forEach items="${fabric.colors}" var="color">
    <input type="hidden" value="${color.id}" name="colors">
</c:forEach>
</form:form>
</div>
<!-- /.Publish Info -->

</div>

<!-- /.Container -->

