<%--
  Created by IntelliJ IDEA.
  User: Charles
  Date: 2014/6/5
  Time: 14:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="application/javascript">
    $(function(){
        $('#fakeContent').ckeditor({
            filebrowserBrowseUrl: '/image/upload',
            filebrowserUploadUrl: '/ckImage/upload',
            width: 800,
            height: 300
        });

        if($("#errors").val() != ""){
            alert($("#errors").val());
        }
    });
</script>
<div class="main">
    <!-- /.Detail Bread -->
    <div class="bread detail-bread">
        <span>您的位置：</span>
        <ul>
            <li><a href="${pageContext.request.contextPath}/admin">后台管理</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/informationList">资讯公告维护</a></li>
            <li>新增资讯</li>
        </ul>
    </div>
    <!-- /.Detail Bread -->
    <div class=" publish-info">
        <form:form modelAttribute="information" method="post" action="${pageContext.request.contextPath}/admin/information/add">
            <ul>
                <li><label>标题：</label><form:input path="title"></form:input></li>
                <li><label>资讯分类：</label>
                <select id="category_id" name="category_id">
                    <c:forEach var="category" items="${informationCategoryList}">
                        <option value="${category.id}"<c:if test="${category.id == information.informationCategory.id}">selected="selected" </c:if>>${category.categoryName}</option>
                    </c:forEach>
                </select></li>
                <li><label>内容简要(列表展示)：</label><form:textarea path="briefContent" cols="80" rows="5"></form:textarea></li>
                <li><label>资讯正文：</label>
                <div class="inline-block">
                    <form:textarea path="fakeContent"></form:textarea>
                </div></li>
                <li><label>封面图片：</label>
                    <div class="thumbDiv">
                        <input id="viewPathToUpload"  type="file" name="viewPathToUpload" value=""/>
                        <div>
                            <p style="font-size: 14;">*图片上传规格：<br/>请上传正方形图片，像素在100px最佳</p>
                        </div>
                    </div>
                    <form:hidden path="viewPath"></form:hidden>
                </li>
                <li>
                    <div>预览图：
                        <img src="
                        <c:choose>
                            <c:when test="${not empty information.viewPath}">
                            ${pageContext.request.contextPath}${information.viewPath}
                            </c:when>
                            <c:otherwise>
                                ${pageContext.request.contextPath}/resources/img/btn-th.jpg
                            </c:otherwise>
                        </c:choose>" class="thumb" id="preViewPic">
                    </div>
                </li>
                <li><label>视频地址：</label><form:input path="videoPath"></form:input></li>
                <li>
                    <input type="hidden" id="id" name="id"value="${information.id}" >
                    <input type="submit" value="保存" name="_target1" />
                    <input type="button" value="取消" name="_cancel"  id="cancelBtn"/>
                </li>
            </ul>
        </form:form>
    </div>
    <input type="hidden" id="errors" value="${message.content}">
</div>
