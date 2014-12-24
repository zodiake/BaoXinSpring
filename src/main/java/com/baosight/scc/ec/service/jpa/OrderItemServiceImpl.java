package com.baosight.scc.ec.service.jpa;

import com.baosight.scc.ec.model.*;
import com.baosight.scc.ec.repository.OrderItemRepository;
import com.baosight.scc.ec.security.UserContext;
import com.baosight.scc.ec.service.*;
import com.baosight.scc.ec.type.OrderState;
import com.baosight.scc.ec.utils.GuidUtil;
import com.baosight.scc.ec.utils.MailSender;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Charles on 2014/5/16.
 */
@Service
@Transactional
public class OrderItemServiceImpl implements OrderItemService {
    @Autowired
    private OrderItemRepository or;

    @Autowired
    private UserContext userContext;
    @Autowired
    private AddressService addressService;
    @Autowired
    private OrderLineService orderLineService;
    @Autowired
    private OrderAddressService orderAddressService;
    @Autowired
    private MailRecordService mailRecordService;
    @Autowired
    private CartService cartService;
    @PersistenceContext
    private EntityManager em;

    private static String mailSenderSwitch;
    @Value("${mailSender-switch}")
    public void setMailSenderSwitch(String mailSenderSwitch){
        OrderItemServiceImpl.mailSenderSwitch = mailSenderSwitch;
    }
    @Override
    @Transactional(readOnly = true)
    public OrderItem findById(String id) {
        return or.findOne(id);
    }

    @Override
    public OrderItem save(OrderItem item) {
        return or.save(item);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderItem> findByBuyer(EcUser buyer,Pageable pageable) {
        Page<OrderItem> page=or.findByBuyer(buyer,pageable);
        Iterator<OrderItem> iterator=page.iterator();
        while(iterator.hasNext()){
            OrderItem item=iterator.next();
            for(OrderLine line:item.getLines()){
                line.getItem().getName();
                line.getItem().getCustomId();
            }
        }
        return page;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderItem> findBySeller(EcUser seller,Pageable pageable) {
        return or.findBySeller(seller, pageable);
    }

    /**
     * 订单提交
     * @param cartLineMap
     * @param addressId
     * @param orderTitle
     * @return
     */
    public Map createOrder(Map<String,List<CartLine>> cartLineMap,String addressId,String orderTitle,int needInvoice,String sellerId){
        DateTime dt = new DateTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmssSSSS");
        String orderItemId = GuidUtil.newGuid();
        OrderItem orderItem = new OrderItem();
        orderItem.setId(orderItemId);
        orderItem.setNeedInvoice(needInvoice);
        String orderNo = sdf.format(dt.toDate())+Math.round(Math.random()*900+100);
        List<OrderLine> orderLines = new ArrayList<OrderLine>();
        Address address = addressService.findById(addressId);
        OrderAddress orderAddress = new OrderAddress(address,orderTitle);
        double orderSum = 0;
        orderAddressService.save(orderAddress);
        EcUser buyer = userContext.getCurrentUser();
        EcUser seller = new EcUser();
        seller.setId(sellerId);
        orderItem.setBuyer(buyer);
        orderItem.setOrderAddress(orderAddress);
        orderItem.setSeller(seller);
        orderItem.setOrderNo(orderNo);
        orderItem.setStatus(OrderState.WAIT_GOODS_SEND.toString());
        List<CartLine> cartLines = cartLineMap.get(sellerId);
        orderItem.setSellerName(cartLines.get(0).getSupplierName());
        for (int i = cartLines.size()-1; i >= 0; i --){
            if(cartLines.get(i).getPrice() > 0){
                OrderLine orderLine = new OrderLine(cartLines.get(i));
                orderLines.add(orderLine);
                orderSum += orderLine.getSum();
                cartService.deleteItem(buyer.getId(), cartLines.get(i).getItemId());
                cartLines.remove(i);
            }
        }
        if(cartLines.isEmpty()){
            cartLineMap.remove(sellerId);
        }else{
            cartLineMap.put(sellerId,cartLines);
        }
        orderItem.setSummary(orderSum);
        OrderItem order = save(orderItem);
        for (int j=0;j<orderLines.size();j++){
            OrderLine orderLine = orderLines.get(j);
            orderLine.setOrderItem(orderItem);
            orderLineService.save(orderLine);
        }
        if(order != null && mailSenderSwitch.equals("0")){
            OrderThread orderThread = new OrderThread(orderItem,orderLines,orderAddress);
            orderThread.start();
        }
        return  cartLineMap;
    }

    /**
     * 更新订单状态
     * @param orderId   订单id
     * @param status   更新状态
     * @return
     */
    public String updateStatus(String orderId,String status){
        OrderItem orderItemOld = findById(orderId);
        //更新状态前校验是否已更新
        if(OrderState.valueOf(status).ordinal() <= OrderState.valueOf(orderItemOld.getStatus()).ordinal()){
            return "statusError";
        }else{
            orderItemOld.setStatus(status);
            DateTime dateTime = new DateTime();
            Calendar calendar = dateTime.toCalendar(Locale.SIMPLIFIED_CHINESE);
            //如果是取消交易、发货、确认收货等操作，记录相应时间,更新状态
            if(status.equals(OrderState.BUYER_APPRAISE.toString()) || status.equals(OrderState.SELLER_APPRAISE.toString())){
                orderItemOld.setCancelTime(calendar);
            }else if(status.equals(OrderState.GOODS_DELIVER.toString())){
                orderItemOld.setDeliverTime(calendar);
            }else if(status.equals(OrderState.GOODS_RECEIVE.toString())){
                orderItemOld.setReceiveTime(calendar);
                List<OrderLine> orderLineList = orderItemOld.getLines();
                //买家确认收货，更新item表，记录一笔交易
                for(int i = 0; i < orderLineList.size(); i ++){
                    Item item = em.find(Item.class,orderLineList.get(i).getItem().getId());
                    int bidCount = item.getBidCount();
                    item.setBidCount(bidCount+1);
                }
            }
            return "success";
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderItem> findByBuyerAndStatusNotLike(EcUser buyer, String status, Pageable pageable) {
        return or.findByBuyerAndStatusNotLike(buyer, status, pageable);
    }

    @Override
    public Long countByReceiveTimeBetweenNowAnd30DaysBefore(DateTime time) {
        DateTime begin=time.minusDays(30);
        return or.countByReceiveTimeBetween(begin.toCalendar(Locale.SIMPLIFIED_CHINESE),time.toCalendar(Locale.SIMPLIFIED_CHINESE));
    }

    @Override
    public Long countByReceiveTimeBetweenNowAnd30DaysBeforeAndStatus(DateTime time, String status) {
        DateTime begin=time.minusDays(30);
        return or.countByReceiveTimeBetweenAndStatus(begin.toCalendar(Locale.SIMPLIFIED_CHINESE),time.toCalendar(Locale.SIMPLIFIED_CHINESE),status);
    }

    @Override
    public Page<OrderItem> findByBuyerAndStatus(EcUser buyer, String status, Pageable pageable) {
        return or.findByBuyerAndStatus(buyer,status,pageable);
    }

    @Override
    public Page<OrderItem> findBySellerAndStatus(EcUser seller, String status, Pageable pageable) {
        return or.findBySellerAndStatus(seller,status,pageable);
    }

    @Override
    public Long countByStatusAndBuyer(String status, EcUser buyer) {
        return or.countByStatusAndBuyer(status,buyer);
    }

    @Override
    public Long countByStatusAndSeller(String status, EcUser seller) {
        return or.countByStatusAndSeller(status,seller);
    }

    @Override
    public Long countOrdersByStatusAndBuyer(String status1,String status2, EcUser buyer) {
        String querySql = "select count(o) from OrderItem o where (o.status=:status1 or o.status=:status2) and o.buyer=:buyer";
        Query query = em.createQuery(querySql).setParameter("buyer",buyer).setParameter("status2",status2).setParameter("status1",status1);
        Long count = (Long) query.getSingleResult();
        return count;
    }

    @Override
    public Long countOrdersStatusAndSeller(String status1,String status2,EcUser seller) {
        String querySql = "select count(o) from OrderItem o where (o.status=:status1 or o.status=:status2) and o.seller=:seller";
        Query query = em.createQuery(querySql).setParameter("seller",seller).setParameter("status2",status2).setParameter("status1",status1);
        Long count = (Long) query.getSingleResult();
        return count;
    }

    @Override
    public Map<String, Integer> orderCount() {
        Map<String,Integer> orderMap = new HashMap<String, Integer>();
        String orderSql = "select t1.status as orderState,count(*) as orderCount from t_ec_orderitem t1  where t1.BUYER_ID not in('admin','admintest','lichaoyi') group by t1.status";
        Query orderQuery = em.createNativeQuery(orderSql);
        List orderResult =  orderQuery.getResultList();
        for (Iterator iterator = orderResult.iterator(); iterator.hasNext(); ) {
            Object[] values = (Object[]) iterator.next();
            orderMap.put((String) values[0],(Integer) values[1]);
        }
        return orderMap;
    }

    @Async
    public void sendOrderMail(OrderItem orderItem,List<OrderLine> orderLines,OrderAddress orderAddress) {
        //查询订单数据
        MailSender mailSender = new MailSender();
        String userName = orderItem.getSeller().getId();
        String mailTo = "";
        try {
            JSONParser parser = new JSONParser();
            JSONObject object = (JSONObject) parser.parse(mailSender.getUserInfo(userName));
            mailTo = (String) object.get("email");   //邮箱
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String needInvoice = orderItem.getNeedInvoice()==1?"是":"否";
        String title = orderItem.getNeedInvoice()==1?orderAddress.getTitle():"无";
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("<html><head><meta charset=\"utf-8\"></head><body style=\"font-family:Microsoft Yahei\"><table style=\"width:680px;margin:0 auto;zoom:1;font-family:Microsoft Yahei,sans-serif\"><tr><td><img src=\"http://www.cloudfashion.org/resources/images/mailHeader.png\" border=\"0\"></td></tr><tr><td style=\"margin-bottom:20px;width:680px;\"><table style=\"width:680px;border:1px solid #c3c3c3;border-spacing:0px;\"><tr><td style=\"padding:6px 8px;font-size:14px;color:#fff;background:#0060af\">订单编号：<a href=\"http://www.cloudfashion.org/orderCenter/sellerViewOrder/"+orderItem.getId()+"\" title=\"点击查看订单\" style=\"text-decoration:none;color:#fff\">");
        stringBuffer.append(orderItem.getOrderNo());
        stringBuffer.append("</a></td></tr><tr><td><table><tr><td style=\"line-height:30px;font-weight:bold\">买家信息：</td></tr><tr><td>发票抬头：");
        stringBuffer.append(title);
        stringBuffer.append("</td></tr><tr><td>是否需要发票：");
        stringBuffer.append(needInvoice);
        stringBuffer.append("</td></tr><tr><td>收货人：");
        stringBuffer.append(orderAddress.getReceiverName());
        stringBuffer.append("</td></tr><tr><td>联系电话：");
        stringBuffer.append(orderAddress.getPhone());
        stringBuffer.append("</td></tr><tr><td>地址：");
        stringBuffer.append(orderAddress.getState()+" " +orderAddress.getCity()+" "+orderAddress.getStreet());
        stringBuffer.append("</td></tr><tr><td style=\"line-height:30px;font-weight:bold\">商品详情：</td></tr><tr> <td><table style=\"width:670px;border-collapse:collapse\"><thead><tr style=\"background-color:#f8f7f7;\"><th width=\"420px\"style=\"text-align: center;font-size:1em;border:1px solid #c3c3c3;padding:3px 7px 2px 7px;background-color:#f8f7f7\" colspan=\"2\">商品名称</th><th width=\"50px\"style=\"text-align: center;font-size:1em;border:1px solid #c3c3c3;padding:3px 7px 2px 7px;background-color:#f8f7f7\">数量</th> <th width=\"100px\"style=\"text-align: center;font-size:1em;border:1px solid #c3c3c3;padding:3px 7px 2px 7px;background-color:#f8f7f7\">单价（元）</th><th width=\"100px\"style=\"text-align: center;font-size:1em;border:1px solid #c3c3c3;padding:3px 7px 2px 7px;background-color:#f8f7f7\">总价（元）</th></tr>");
        for (int i =0;i<orderLines.size();i++){
            OrderLine orderLine = orderLines.get(i);
            stringBuffer.append("<tr><td style=\"text-align: left;font-size:1em;border:1px solid #c3c3c3;padding:3px 7px 2px 7px;width:100px\">");
            if(orderLine.getItem().getCoverImage().isEmpty()){
                stringBuffer.append("<img src=\"http://www.cloudfashion.org/resources/img/btn-th_100.png\" style=\"vertical-align:middle\"/></td><td style=\"text-align: left;font-size:1em;border:1px solid #c3c3c3;padding:3px 7px 2px 7px;\">");
            }else{
                stringBuffer.append("<img src=\"http://www.cloudfashion.org"+orderLine.getItem().getCoverImage()+"?size=100\" style=\"vertical-align:middle\"/></td><td style=\"text-align: left;font-size:1em;border:1px solid #c3c3c3;padding:3px 7px 2px 7px;\">");
            }
            stringBuffer.append(orderLine.getItem().getName()+"</td>");
            stringBuffer.append("<td style=\"font-size:1em;border:1px solid #c3c3c3;padding:3px 7px 2px 7px;\">"+orderLine.getQuantity()+"</td>");
            stringBuffer.append("<td style=\"font-size:1em;border:1px solid #c3c3c3;padding:3px 7px 2px 7px;\">"+orderLine.getPrice()+"</td>");
            stringBuffer.append("<td style=\"font-size:1em;border:1px solid #c3c3c3;padding:3px 7px 2px 7px;\">" + orderLine.getSum() + "</td></tr>");
        }
        stringBuffer.append("</thead><tfoot><tr><td colspan=\"5\" style=\"text-align: right\">订单总价：");
        stringBuffer.append(orderItem.getSummary());
        stringBuffer.append("元</td></tr></tfoot></table></td></tr></table></td></tr></table></td></tr></table></body></html>");

        if(!StringUtils.isEmpty(mailTo)){
            MailRecord mailRecord = new MailRecord();
            mailRecord.setBillType("order");
            mailRecord.setMailTo(mailTo);
            mailRecord.setMailToId(userName);
            mailRecord.setSubject("云端时尚：交易订单提醒");
            mailRecord.setContent(orderItem.getId());
            try {
                if(mailSender.sendOrderMail(mailTo,"云端时尚：交易订单提醒",stringBuffer.toString())){
                    mailRecord.setIsSuccess(1);
                }else {
                    mailRecord.setIsSuccess(0);
                }
                mailRecordService.save(mailRecord);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private class OrderThread extends Thread{
        private OrderItem orderItem;
        private List<OrderLine> orderLines;
        private OrderAddress orderAddress;

        private OrderThread(OrderItem orderItem,List<OrderLine> orderLines,OrderAddress orderAddress){
            this.orderItem = orderItem;
            this.orderLines = orderLines;
            this.orderAddress = orderAddress;
        }

        public void run(){
            try {
                sendOrderMail(orderItem,orderLines,orderAddress);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }
}