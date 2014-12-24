<%--
  Created by IntelliJ IDEA.
  User: Charles
  Date: 2014/6/6
  Time: 11:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sbTag" uri="http://zodiake.com" %>
<div>
    <div>
        <div style="text-align: center;font-size: 25px;">供应链平台数据统计</div>
        <div style="text-align: right;font-size: 14px;">注：以下数据不包含用户"admin","admintest","lichaoyi"的相关数据。</div>
        <div style="text-align: right;font-size: 12px;">统计时间：<fmt:formatDate value="${date}" pattern="yyyy年MM月dd日 HH:mm" type="date"/></div>
    </div>
    <br/>
    <div class="detail-table">
        <table class="table table-border">
            <thead>
            <tr>
                <th colspan="6">商品统计</th>
            </tr>
            <tr>
                <th>分类</th>
                <th>草稿</th>
                <th>出售中</th>
                <th>已下架</th>
                <th>审核中</th>
                <th>总计</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>面料</td>
                <td>${fabricMap.get("草稿") == null ?0:fabricMap.get("草稿")}</td>
                <td>${fabricMap.get("出售中") == null ?0:fabricMap.get("出售中")}</td>
                <td>${fabricMap.get("下架") == null ?0:fabricMap.get("下架")}</td>
                <td>${fabricMap.get("审核中") == null ?0:fabricMap.get("审核中")}</td>
                <td>${fabricMap.get("草稿")+fabricMap.get("出售中")+fabricMap.get("下架")+fabricMap.get("审核中")}</td>
            </tr>
            <tr>
                <td>辅料</td>
                <td>${materialMap.get("草稿")== null ?0:materialMap.get("草稿")}</td>
                <td>${materialMap.get("出售中")== null ?0:materialMap.get("出售中")}</td>
                <td>${materialMap.get("下架")== null ?0:materialMap.get("下架")}</td>
                <td>${materialMap.get("审核中")== null ?0:materialMap.get("审核中")}</td>
                <td>${materialMap.get("草稿")+materialMap.get("出售中")+materialMap.get("下架")+materialMap.get("审核中")}</td>
            </tr>
            <tr>
                <td>总计</td>
                <td>${fabricMap.get("草稿")+materialMap.get("草稿")}</td>
                <td>${fabricMap.get("出售中")+materialMap.get("出售中")}</td>
                <td>${fabricMap.get("下架")+materialMap.get("下架")}</td>
                <td>${fabricMap.get("审核中")+materialMap.get("审核中")}</td>
                <td>${fabricMap.get("草稿")+fabricMap.get("出售中")+fabricMap.get("下架")+fabricMap.get("审核中")+materialMap.get("草稿")+materialMap.get("出售中")+materialMap.get("下架")+materialMap.get("审核中")}</td>
            </tr>
            </tbody>
        </table>
    </div>
    <br/>

    <div class="detail-table">
        <table class="table table-border">
            <thead>
            <tr>
                <th colspan="9">订单统计</th>
            </tr>
            <tr>
                <th>分类</th>
                <th>等待卖家发货</th>
                <th>卖家已发货</th>
                <th>已确认收货</th>
                <th>买家已评价</th>
                <th>卖家已评价</th>
                <th>买家取消的订单</th>
                <th>卖家取消的订单</th>
                <th>全部</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>交易订单</td>
                <td>${orderMap.get("WAIT_GOODS_SEND")== null ?0:orderMap.get("WAIT_GOODS_SEND")}</td>
                <td>${orderMap.get("GOODS_DELIVER")== null ?0:orderMap.get("GOODS_DELIVER")}</td>
                <td>${orderMap.get("GOODS_RECEIVE")== null ?0:orderMap.get("GOODS_RECEIVE")}</td>
                <td>${orderMap.get("BUYER_APPRAISE")== null ?0:orderMap.get("BUYER_APPRAISE")}</td>
                <td>${orderMap.get("SELLER_APPRAISE")== null ?0:orderMap.get("SELLER_APPRAISE")}</td>
                <td>${orderMap.get("BUYER_CANCEL")== null ?0:orderMap.get("BUYER_CANCEL")}</td>
                <td>${orderMap.get("SELLER_CANCEL")== null ?0:orderMap.get("SELLER_CANCEL")}</td>
                <td>${orderMap.get("WAIT_GOODS_SEND")+orderMap.get("GOODS_DELIVER")+orderMap.get("GOODS_RECEIVE")+orderMap.get("BUYER_APPRAISE")+orderMap.get("SELLER_APPRAISE")+orderMap.get("BUYER_CANCEL")+orderMap.get("SELLER_CANCEL")}</td>
            </tr>
            <tr>
                <td>调样单</td>
                <td>${sampleMap.get("WAIT_GOODS_SEND")== null ?0:sampleMap.get("WAIT_GOODS_SEND")}</td>
                <td>${sampleMap.get("GOODS_DELIVER")== null ?0:sampleMap.get("GOODS_DELIVER")}</td>
                <td>${sampleMap.get("GOODS_RECEIVE")== null ?0:sampleMap.get("GOODS_RECEIVE")}</td>
                <td>-</td>
                <td>-</td>
                <td>${sampleMap.get("BUYER_CANCEL")== null ?0:sampleMap.get("BUYER_CANCEL")}</td>
                <td>${sampleMap.get("SELLER_CANCEL")== null ?0:sampleMap.get("SELLER_CANCEL")}</td>
                <td>${sampleMap.get("WAIT_GOODS_SEND")+sampleMap.get("GOODS_DELIVER")+sampleMap.get("GOODS_RECEIVE")+sampleMap.get("BUYER_CANCEL")+sampleMap.get("SELLER_CANCEL")}</td>
            </tr>
            <tr>
                <td>总计</td>
                <td>${orderMap.get("WAIT_GOODS_SEND")+sampleMap.get("WAIT_GOODS_SEND")}</td>
                <td>${orderMap.get("GOODS_DELIVER")+sampleMap.get("GOODS_DELIVER")}</td>
                <td>${orderMap.get("GOODS_RECEIVE")+sampleMap.get("GOODS_RECEIVE")}</td>
                <td>${orderMap.get("BUYER_APPRAISE")== null ?0:orderMap.get("BUYER_APPRAISE")}</td>
                <td>${orderMap.get("SELLER_APPRAISE")== null ?0:orderMap.get("SELLER_APPRAISE")}</td>
                <td>${orderMap.get("BUYER_CANCEL")+sampleMap.get("BUYER_CANCEL")}</td>
                <td>${orderMap.get("SELLER_CANCEL")+sampleMap.get("SELLER_CANCEL")}</td>
                <td>${orderMap.get("WAIT_GOODS_SEND")+orderMap.get("GOODS_DELIVER")+orderMap.get("GOODS_RECEIVE")+orderMap.get("BUYER_APPRAISE")+orderMap.get("SELLER_APPRAISE")+orderMap.get("BUYER_CANCEL")+orderMap.get("SELLER_CANCEL")+sampleMap.get("WAIT_GOODS_SEND")+sampleMap.get("GOODS_DELIVER")+sampleMap.get("GOODS_RECEIVE")+sampleMap.get("BUYER_CANCEL")+sampleMap.get("SELLER_CANCEL")}</td>
            </tr>
            </tbody>
        </table>
    </div>
    <br/>
    <div class="detail-table">
        <table class="table table-border">
            <thead>
            <tr>
                <th colspan="5">求购单统计</th>
            </tr>
            <tr>
                <th>分类</th>
                <th>已上架(有效期内)</th>
                <th>已上架(有效期外)</th>
                <th>已下架</th>
                <th>总计</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>交易订单</td>
                <td>${demandMap.get("valid")== null ?0:demandMap.get("valid")}</td>
                <td>${demandMap.get("invalid")== null ?0:demandMap.get("invalid")}</td>
                <td>${demandMap.get("offSale")== null ?0:demandMap.get("offSale")}</td>
                <td>${demandMap.get("valid")+demandMap.get("invalid")+demandMap.get("offSale")}</td>
            </tr>
            </tbody>
        </table>
    </div>
</div>