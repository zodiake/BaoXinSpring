<%--
  Created by IntelliJ IDEA.
  User: zodiake
  Date: 2014/6/9
  Time: 14:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="footer">
<!-- /.Footer -->
<footer>
    <div class="rapid">
        <div class="row-4">
            <dl class="col-1" style="text-align: left">
                <dt>新手指南</dt>
                <dd>
                    <ul class="list-unstyled">
                        <li><a href="${pageContext.request.contextPath}/help/newUserOption#userRegister" target="_blank">会员注册</a></li>
                        <li><a href="${pageContext.request.contextPath}/help/newUserOption#designerRegister" target="_blank">设计师注册</a></li>
                        <li><a href="${pageContext.request.contextPath}/help/newUserOption#brandCompanyRegister" target="_blank">品牌注册</a></li>
                        <li><a href="${pageContext.request.contextPath}/help/newUserOption#providerRegister" target="_blank">供应商注册</a></li>
                    </ul>
                </dd>
            </dl>
            <%--<dl class="col-1">
                <dt>关于我们</dt>
                <dd>
                    <ul class="list-unstyled">
                        <li><a href="#">系统公告</a></li>
                        <li><a href="#">服务商入驻</a></li>
                    </ul>
                </dd>
            </dl>--%>
            <dl class="col-1" style="text-align: left">
                <dt>供应商指南</dt>
                <dd>
                    <ul class="list-unstyled">
                        <li><a href="${pageContext.request.contextPath}/help/providerGuide#fabricPublic" target="_blank">面料发布</a></li>
                        <li><a href="${pageContext.request.contextPath}/help/providerGuide#materialPublic" target="_blank">辅料发布</a></li>
                    </ul>
                </dd>
            </dl>
            <%--<dl class="col-1">
                <dt>常见问题</dt>
                <dd>
                    <ul class="list-unstyled">
                        <li><a href="#">如何注册</a></li>
                        <li><a href="#">如何找买家</a></li>
                    </ul>
                </dd>
            </dl>--%>
            <dl class="col-1" style="text-align: left">
                <dt>使用指南</dt>
                <dd>
                    <ul class="list-unstyled">
                        <li><a href="${pageContext.request.contextPath}/help/userGuide#buyItem" target="_blank">如何购买</a></li>
                        <li><a href="${pageContext.request.contextPath}/help/userGuide#buySample" target="_blank">如何调样</a></li>
                        <li><a href="${pageContext.request.contextPath}/help/userGuide#buyDemandOrder" target="_blank">如何求购</a></li>
                        <li><a href="${pageContext.request.contextPath}/help/userGuide#quickDesign" target="_blank">如何快速设计</a></li>
                    </ul>
                </dd>
            </dl>
            <dl class="col-1" style="text-align: left">
                <dt>联系方式</dt>
                <dd>
                    <ul class="list-unstyled">
                        <li>邮箱：<a href="mailto:service@cloudfashion.org">service@cloudfashion.org </a></li>
                        <!--li>电话：400-88-27360</li-->
                    </ul>
                </dd>
            </dl>
        </div>
    </div>
    <div class="links">
        <a href="${pageContext.request.contextPath}/">网站首页</a><em class="link-line">|</em><a href="#">版权隐私</a><em class="link-line">|</em><a href="#">使用协议</a><em class="link-line">|</em><a href="mailto:service@cloudfashion.org">联系方式</a><em class="link-line">|</em><a href="${pageContext.request.contextPath}/buap/intro/intro.html" target="_blank">关于我们</a><em class="link-line">|</em><a href="#">网站地图</a><em class="link-line">|</em><%--<a href="#">网站留言</a><em class="link-line">|</em>--%><a href="mailto:service@cloudfashion.org">广告服务</a><!--em class="link-line">|</em><a href="#">沪ICP备 12025467号-1</a-->
</div>
    <div class="address">Copyright © 2010-2014<br/>上海张江国家自主创新示范区<br/>基于云计算的创意设计公共服务基地<br/>版权所有<script type="text/javascript">
        var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
        document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3F07f16e759248750951e98b417840547b' type='text/javascript'%3E%3C/script%3E"));
    </script></div>

</footer>
<!-- /.Footer -->
</div>
<!-- /.Site -->
<div id="back-to-top" class="top-box"><a class="btn-top" href="#top"><span class="one"></span><span class="arrow"></span><span class="square"></span></a></div>
<script type="text/javascript" id="include" src="${pageContext.request.contextPath}/resources/js/include.js" data-config="${pageContext.request.contextPath}/resources/config"></script>
<script type="text/javascript">
    $(document).ready(function(){
        //首先将#back-to-top隐藏
        $("#back-to-top").hide();
        //当滚动条的位置处于距顶部100像素以下时，跳转链接出现，否则消失
        $(function () {
            $(window).scroll(function(){
                if ($(window).scrollTop()>100){
                    $("#back-to-top").fadeIn(500);
                }
                else
                {
                    $("#back-to-top").fadeOut(500);
                }
            });
            //当点击跳转链接后，回到页面顶部位置
            $("#back-to-top").click(function(){
                $('body,html').animate({scrollTop:0},100);
                return false;
            });
        });

    });
</script>
