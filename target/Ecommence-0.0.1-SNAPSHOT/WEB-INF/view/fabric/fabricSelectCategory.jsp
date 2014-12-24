<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: zodiake
  Date: 2014/5/15
  Time: 9:58
  To change this template use File | Settings | File Templates.
--%>

<div class="container">

    <!-- /.Publish Tip -->
    <div class="publish-tip"><i class="icon icon-volume-down"></i>为您的产品信息选择合适的类目</div>
    <!-- /.Publish Tip -->

    <!-- /.Publish Catalogue -->
    <dl class="publish-catalogue">
        <dt>
        <ul id="tab" class="list-inline">
            <li>查找类目</li>
            <li>您常使用的类目</li>
        </ul>
        </dt>
        <dd>
            <form:form id="mainForm" class="publish-search" action="${flowExecutionUrl}" modelAttribute="fabric">
                <div class="publish-choose">
                    <!--整休-->
                    <div class="row-11">
                        <!--模块一-->
                        <div class="publish-main">
                            <div class="publish-row">
                                <div class="tit">产品类别</div>
                                <div class="row-2">
                                    <div class="col-1">
                                        <form:select path="firstCategory" multiple="true" items="${categoies}"
                                                     itemLabel="name" itemValue="id">
                                        </form:select>
                                        <form:errors path="firstCategory" cssClass="error"></form:errors>
                                    </div>
                                    <div class="col-1">
                                        <c:if test="${empty fabric.category}">
                                            <select id="fabric-secondCategory" name="category" multiple>
                                            </select>
                                            <form:errors path="category" cssClass="error"></form:errors>
                                        </c:if>
                                        <c:if test="${not empty fabric.category}">
                                            <form:select path="category" items="${secondCategory}" multiple="multiple"
                                                         itemLabel="name" itemValue="id"
                                                         id="fabric-secondCategory">
                                            </form:select>
                                            <form:errors path="category" cssClass="error"></form:errors>
                                        </c:if>
                                    </div>
                                </div>
                                <div class="publish-choose-type" id="fabric-text">
                                    <label>您当前选择的产品类别：</label>
                                    <span>${fabric.firstCategory.name}</span>
                                    <span>
                                        <c:if test="${not empty fabric.firstCategory}">
                                            »
                                        </c:if>
                                    </span>
                                    <span>${fabric.category.name}</span>
                                </div>
                            </div>
                            <div class="publish-side">
                                <div class="publish-help">
                                    <ul>
                                        <li><img src="${pageContext.request.contextPath}/resources/img/ml1.jpg"> </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <!--end-->

                        <!--module two-->
                        <div class="publish-main">
                            <div class="publish-row">
                                <div class="tit">原料成分</div>
                                <div class="row-2">
                                    <div class="col-1">
                                        <form:select items="${sources}" path="source" itemLabel="name" itemValue="id"
                                                     multiple="multiple">
                                        </form:select>
                                        <form:errors path="source" cssClass="error"></form:errors>
                                    </div>
                                    <div class="col-1">
                                        <c:if test="${empty fabric.sourceDetail}">
                                            <select id="sourceDetail" name="sourceDetail" multiple>
                                            </select>
                                            <form:errors path="sourceDetail" cssClass="error"></form:errors>
                                        </c:if>
                                        <c:if test="${not empty fabric.sourceDetail}">
                                            <form:select path="sourceDetail" items="${secondSourceDetail}"
                                                         itemLabel="name"
                                                         itemValue="id" multiple="multiple"
                                                         id="sourceDetail">
                                            </form:select>
                                            <form:errors path="sourceDetail" cssClass="error"></form:errors>
                                        </c:if>
                                    </div>
                                </div>
                                <div class="publish-choose-type" id="source-text">
                                    <label>您当前选择的成分属性：</label>
                                    <span>${fabric.source.name}</span>
                                    <span>
                                        <c:if test="${not empty fabric.source}">
                                            »
                                        </c:if>
                                    </span>
                                    <span>${fabric.sourceDetail.name}</span>
                                </div>
                            </div>
                            <div class="publish-side">
                                <div class="publish-help">
                                    <ul>
                                        <li><img src="${pageContext.request.contextPath}/resources/img/ml2.jpg"></li>
                                    </ul>
                                </div>
                            </div>
                        </div>


                        <!--module three-->
                        <div class="publish-main">
                            <div class="publish-row">
                                <div class="tit">主要用途</div>
                                <div class="row-2">
                                    <div>
                                        <ul class="list-inline" id="mainTypes">
                                            <form:checkboxes path="mainUseTypes" items="${mainTypes}" itemLabel="name"
                                                             itemValue="id" element="li"></form:checkboxes>
                                            <form:errors path="mainUseTypes" cssClass="error"></form:errors>
                                        </ul>
                                    </div>
                                </div>
                                <div class="publish-choose-type"><label id="mainType_text">用途：</label></div>
                            </div>
                            <div class="publish-side">
                                <div class="publish-help">
                                    <ul>
                                        <li><img src="${pageContext.request.contextPath}/resources/img/ml3.jpg"></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="text-center p-10">
                        <input type="submit" value="下一步，填写详细信息" name="_eventId_next" class="button button-deep"/>
                        <input type="submit" value="取消" name="_eventId_cancel" class="button button-deep"/>
                    </div>
                </div>
            </form:form>
        </dd>
        <dd>
            <div class="publish-has">
                <div class="publish-choose">
                    <span>产品类别</span>
                    <ul>
                        <c:forEach items="${prefs}" var="pref">
                            <li>
                                <input type="radio" name="pref" data-firstCategory="${pref.category.parentCategory.id}"
                                       data-id="${pref.category.id}" data-first="${pref.category.parentCategory.name}" data-second="${pref.category.name}"/>
                                    ${pref.category.parentCategory.name}»${pref.category.name}
                            </li>
                        </c:forEach>
                    </ul>
                    <div class="publish-choose-type"><label>您当前选择的类目：</label>染色类 » 扎染 您尚未选择类目</div>
                </div>

                <div class="text-center p-10">
                    <button class="button button-deep" id="next_sub">下一步，填写详细信息</button>
                    <button class="button button-deep">取消</button>
                </div>
            </div>
        </dd>
    </dl>
    <!-- /.Publish Catalogue -->

</div>
<!-- /.Container -->


