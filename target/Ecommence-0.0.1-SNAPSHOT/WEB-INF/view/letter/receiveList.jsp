<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>letter</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
    <script>
        $(function(){
            $('#test').click(function(){
                var t=$(this);
                var s=$('input:checked');
                var s1='';
                $.each(s,function(i){
                    var o = s.eq(i);
                    if(i==0){
                        s1 = o.attr('data-id');
                    }else{
                        s1=s1+','+o.attr('data-id');
                    }
                })
                console.log(s1);
                var url='/letterCenter/updateLetterStatus?status=2&arrId='+s1;
                t.attr('href',url);
            });
        });
    </script>
</head>
<body>
<div>
    站内信收件箱
    <table>
        <tr>
            <td><a id="test">全选</a></td>
            <td>标题</td>
            <td>收件人</td>
            <td>发件人</td>
        </tr>
        <c:forEach items="${grid.ecList}" var="letter" varStatus="st">
            <tr>
                <td><input type="checkbox" data-id="${letter.id}" id="s${st.count}"></td>
                <td><a href="/letterCenter/letter/${letter.id}">${letter.title}</a></td>
                <td>${letter.receiveUser.name}</td>
                <td>${letter.sendUser.name}</td>
            </tr>
        </c:forEach>
    </table>
    <a href="/letterCenter/sendLetters">发件箱</a>
</div>

</body>
</html>

