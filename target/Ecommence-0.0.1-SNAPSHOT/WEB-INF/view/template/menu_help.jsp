<%--
  Created by IntelliJ IDEA.
  User: Charles
  Date: 2014/6/12
  Time: 10:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!--左侧-->
<div class="aside">
    <!--我的交易-->
    <div class="menu-nav">
        <div class="nav-list">
            <dl class="nav">
                <dt>用户指南</dt>
                <dd><a href="${pageContext.request.contextPath}/help/userGuide#buyItem" data-id="buyItem" title="我要购买" class="tit"><span class="dot">&#8226;</span>我要购买</a></dd>
                <dd ><a href="${pageContext.request.contextPath}/help/userGuide#buySample" data-id="buySample" title="我要调样" class="tit"><span class="dot">&#8226;</span>我要调样</a></dd>
                <dd><a href="${pageContext.request.contextPath}/help/userGuide#buyDemandOrder" data-id="buyDemandOrder" title="我要求购" class="tit"><span class="dot">&#8226;</span>我要求购</a></dd>
                <dd><a href="${pageContext.request.contextPath}/help/userGuide#quickDesign" data-id="buyDemandOrder" title="快速设计" class="tit"><span class="dot">&#8226;</span>快速设计</a></dd>
            </dl>
            <dl class="nav">
                <dt>供应商指南</dt>
                <dd><a href="${pageContext.request.contextPath}/help/providerGuide#fabricPublic" data-id="fabricPublic" title="面料发布" class="tit"><span class="dot">&#8226;</span>面料发布</a></dd>
                <dd><a href="${pageContext.request.contextPath}/help/providerGuide#materialPublic" data-id="materialPublic" title="辅料发布" class="tit"><span class="dot">&#8226;</span>辅料发布</a></dd>
                <%--<dd><a href="${pageContext.request.contextPath}/help/providerGuide#itemOrder" data-id="itemOrder" title="订单交易" class="tit"><span class="dot">&#8226;</span>订单交易</a></dd>--%>
                <%--<dd><a href="${pageContext.request.contextPath}/help/providerGuide#sampleOrder" data-id="sampleOrder" title="调样服务" class="tit"><span class="dot">&#8226;</span>调样服务</a></dd>--%>
            </dl>
            <dl class="nav">
                <dt><a href="${pageContext.request.contextPath}/help/commonProblems" data-id="commonProblem">常见问题</a></dt>
            </dl>
            <dl class="nav">
                <dt>新手上路</dt>
                <dd><a href="${pageContext.request.contextPath}/help/newUserOption#userRegister" data-id="userRegister" title="会员注册" class="tit"><span class="dot">&#8226;</span>会员注册</a></dd>
                <dd><a href="${pageContext.request.contextPath}/help/newUserOption#designerRegister" data-id="designerRegister" title="设计师注册" class="tit"><span class="dot">&#8226;</span>设计师注册</a></dd>
                <dd><a href="${pageContext.request.contextPath}/help/newUserOption#brandCompanyRegister" data-id="brandCompanyRegister" title="品牌注册" class="tit"><span class="dot">&#8226;</span>品牌注册</a></dd>
                <dd><a href="${pageContext.request.contextPath}/help/newUserOption#providerRegister" data-id="providerRegister" title="供应商注册" class="tit"><span class="dot">&#8226;</span>供应商注册</a></dd>
                <dd><a href="${pageContext.request.contextPath}/help/newUserOption#designerRepair" data-id="designerRepair" title="设计师维护" class="tit"><span class="dot">&#8226;</span>设计师维护</a></dd>
                <dd><a href="${pageContext.request.contextPath}/help/newUserOption#brandRepair" data-id="brandRepair" title="品牌维护" class="tit"><span class="dot">&#8226;</span>品牌维护</a></dd>
                <dd><a href="${pageContext.request.contextPath}/help/newUserOption#reportDesign" data-id="reportDesign" title="报告制作" class="tit"><span class="dot">&#8226;</span>报告制作</a></dd>
                <%--<dd><a href="${pageContext.request.contextPath}/help/newUserOption#companyAuthentication" data-id="companyAuthentication" title="企业认证" class="tit"><span class="dot">&#8226;</span>企业认证</a></dd>--%>
                <dd><a href="${pageContext.request.contextPath}/help/newUserOption#forgotPassword" data-id="forgotPassword" title="忘记密码" class="tit"><span class="dot">&#8226;</span>忘记密码</a></dd>
                <dd><a href="${pageContext.request.contextPath}/help/newUserOption#modifyPassword" data-id="modifyPassword" title="修改密码" class="tit"><span class="dot">&#8226;</span>修改密码</a></dd>
            </dl>
            <dl class="nav">
                <dt>企划管理指南</dt>
                <dd><a href="${pageContext.request.contextPath}/help/companyLayOutManage#createNewSeason" title="创建新季节" class="tit"><span class="dot">&#8226;</span>创建新季节</a></dd>
                <dd><a href="${pageContext.request.contextPath}/help/companyLayOutManage#developProcess" title="开发进度" class="tit"><span class="dot">&#8226;</span>开发进度</a></dd>
                <dd><a href="${pageContext.request.contextPath}/help/companyLayOutManage#developPlan" title="开发计划" class="tit"><span class="dot">&#8226;</span>开发计划</a></dd>
                <dd><a href="${pageContext.request.contextPath}/help/companyLayOutManage#concept" title="概念版" class="tit"><span class="dot">&#8226;</span>概念版</a></dd>
                <dd><a href="${pageContext.request.contextPath}/help/companyLayOutManage#style-leader" title="款式列表-总监" class="tit"><span class="dot">&#8226;</span>款式列表-总监</a></dd>
                <dd><a href="${pageContext.request.contextPath}/help/companyLayOutManage#style-designer" title="款式列表-设计师" class="tit"><span class="dot">&#8226;</span>款式列表-设计师</a></dd>
                <dd><a href="${pageContext.request.contextPath}/help/companyLayOutManage#style-valid" title="款式审核" class="tit"><span class="dot">&#8226;</span>款式审核</a></dd>
                <dd><a href="${pageContext.request.contextPath}/help/companyLayOutManage#design-develop-repair" title="维护设计/评审开发团队" class="tit"><span class="dot">&#8226;</span>维护设计/评审开发团队</a></dd>
                <dd><a href="${pageContext.request.contextPath}/help/companyLayOutManage#layoutHistoryManage" title="设计稿历史管理" class="tit"><span class="dot">&#8226;</span>设计稿历史管理</a></dd>
                <dd><a href="${pageContext.request.contextPath}/help/companyLayOutManage#applySPIMade" title="申请样衣制作" class="tit"><span class="dot">&#8226;</span>申请样衣制作</a></dd>
                <dd><a href="${pageContext.request.contextPath}/help/companyLayOutManage#SPIManage" title="样衣管理" class="tit"><span class="dot">&#8226;</span>样衣管理</a></dd>
                <dd><a href="${pageContext.request.contextPath}/help/companyLayOutManage#SPIVerify" title="样衣审核" class="tit"><span class="dot">&#8226;</span>样衣审核</a></dd>
                <dd><a href="${pageContext.request.contextPath}/help/companyLayOutManage#SPIMade" title="样衣制作" class="tit"><span class="dot">&#8226;</span>样衣制作</a></dd>
            </dl>
            <dl class="nav">
                <dt>网上订货会指南</dt>
                <dd><a href="${pageContext.request.contextPath}/help/onlineOrderingManage#orderingUserManage"  title="订货会创建及人员管理" class="tit"><span class="dot">&#8226;</span>订货会创建及人员管理</a></dd>
                <dd><a href="${pageContext.request.contextPath}/help/onlineOrderingManage#orderingManage"  title="订货会管理" class="tit"><span class="dot">&#8226;</span>订货会管理</a></dd>
                <dd><a href="${pageContext.request.contextPath}/help/onlineOrderingManage#AttendToOrdering"  title="参与订货会" class="tit"><span class="dot">&#8226;</span>参与订货会</a></dd>
            </dl>
        </div>
    </div>
    <!--我的交易-->
</div>
<!--左侧 end-->
