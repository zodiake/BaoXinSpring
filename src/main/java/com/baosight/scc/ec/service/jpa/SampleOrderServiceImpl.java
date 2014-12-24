package com.baosight.scc.ec.service.jpa;

import com.baosight.scc.ec.model.*;
import com.baosight.scc.ec.repository.SampleOrderRepository;
import com.baosight.scc.ec.service.MailRecordService;
import com.baosight.scc.ec.service.SampleOrderAddressService;
import com.baosight.scc.ec.service.SampleOrderService;
import com.baosight.scc.ec.type.SampleOrderState;
import com.baosight.scc.ec.utils.GuidUtil;
import com.baosight.scc.ec.utils.MailSender;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by zodiake on 2014/5/28.
 */
@Service
public class SampleOrderServiceImpl implements SampleOrderService {

    @Autowired
    private SampleOrderRepository repository;
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private SampleOrderAddressService sampleOrderAddressService;
    @Autowired
    private MailRecordService mailRecordService;

    private static String mailSenderSwitch;
    @Value("${mailSender-switch}")
    public void setMailSenderSwitch(String mailSenderSwitch){
        SampleOrderServiceImpl.mailSenderSwitch = mailSenderSwitch;
    }
    @Override
    public SampleOrder findByIdAndCreator(String id, EcUser user) {
        SampleOrder order = repository.findByIdAndCreator(id, user);
        return order;
    }

    @Override
    public SampleOrder save(SampleOrder sampleOrder) {
        DateTime dt = new DateTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmssSSSS");
        String orderNo = sdf.format(dt.toDate())+Math.round(Math.random()*900+100);
        sampleOrder.setOrderNo(orderNo);
        sampleOrderAddressService.save(sampleOrder.getAddress());
        if(sampleOrder != null && mailSenderSwitch.equals("0")){
            SampleThread sampleThread = new SampleThread(sampleOrder);
            sampleThread.start();
        }
        SampleOrder order = repository.save(sampleOrder);
        return order;
    }

    @Override
    public SampleOrder findById(String id) {
        return repository.findOne(id);
    }

    @Override
    public SampleOrder update(SampleOrder sampleOrder) {
        return repository.save(sampleOrder);
    }


    @Override
    public Page<SampleOrder> findByItemCreatedBy(EcUser user, Pageable pageable) {
        TypedQuery<SampleOrder> query=em.createNamedQuery("sampleOrder.findByCreatedBy", SampleOrder.class).setFirstResult(pageable.getOffset()).setMaxResults(pageable.getPageSize());
        query.setParameter(1,user);
        List<SampleOrder> lists=query.getResultList();
        TypedQuery<Long> countQuery=em.createNamedQuery("sampleOrder.countByCreatedBy", Long.class);
        countQuery.setParameter(1,user);
        Long i=countQuery.getSingleResult();
        Page<SampleOrder> pageImpl=new PageImpl<SampleOrder>(lists,pageable,i);
        return pageImpl;
    }

    @Override
    public Page<SampleOrder> findByStateAndItemCreatedBy(SampleOrderState state,EcUser user, Pageable pageable) {
        TypedQuery<SampleOrder> query=em.createNamedQuery("sampleOrder.findByStateAndCreatedBy", SampleOrder.class).setFirstResult(pageable.getOffset()).setMaxResults(pageable.getPageSize());
        query.setParameter(1,state);
        query.setParameter(2,user);
        List<SampleOrder> lists=query.getResultList();
        TypedQuery<Long> countQuery=em.createNamedQuery("sampleOrder.countByStateAndCreatedBy", Long.class);
        countQuery.setParameter(1,state);
        countQuery.setParameter(2,user);
        Long i=countQuery.getSingleResult();
        Page<SampleOrder> pageImpl=new PageImpl<SampleOrder>(lists,pageable,i);
        return pageImpl;
    }

    @Override
    public Page<SampleOrder> findByCreatedBy(EcUser user, Pageable pageable) {
        return repository.findByCreator(user, pageable);
    }

    @Override
    public Page<SampleOrder> findByStateAndCreatedBy(EcUser user, SampleOrderState state, Pageable pageable) {
        return repository.findByCreatorAndState(user,state,pageable);
    }

    @Override
    public List<SampleOrder> findAll() {
        return Lists.newArrayList(repository.findAll());
    }

    @Override
    public Page<SampleOrder> findByProvider(EcUser user, Pageable pageable) {
        return repository.findByProvider(user,pageable);
    }

    @Override
    public Page<SampleOrder> findByStateAndProvider(EcUser user, SampleOrderState state, Pageable pageable) {
        return repository.findByStateAndProvider(state, user, pageable);
    }

    @Override
    public Long countByCreatorAndStatus(EcUser user, SampleOrderState state) {
        return repository.countByCreatorAndState(user,state);
    }

    @Override
    public Long countByProviderAndStatus(EcUser user, SampleOrderState state) {
        return repository.countByProviderAndState(user,state);
    }

    @Override
    public Map<String, Integer> sampleCount() {
        Map<String,Integer> sampleMap = new HashMap<String, Integer>();
        String sampleSql = "select t1.state as orderState,count(*) as orderCount from t_ec_sampleorder t1  where t1.USER_ID not in('admin','admintest','lichaoyi') group by t1.state";
        Query sampleQuery = em.createNativeQuery(sampleSql);
        List sampleResult =  sampleQuery.getResultList();
        for (Iterator iterator = sampleResult.iterator(); iterator.hasNext(); ) {
            Object[] values = (Object[]) iterator.next();
            sampleMap.put((String) values[0],(Integer) values[1]);
        }
        return sampleMap;
    }

    public void sendOrderMail(SampleOrder sampleOrder) {
        //查询订单数据
        MailSender mailSender = new MailSender();
        String userName = sampleOrder.getProvider().getId();
        SampleOrderCopyAddress address = sampleOrder.getAddress();
        List<SampleLine> sampleLines = sampleOrder.getSampleLines();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String mailTo = "";
        try {
            JSONParser parser = new JSONParser();
            JSONObject object = (JSONObject) parser.parse(mailSender.getUserInfo(userName));
            mailTo = (String) object.get("email");   //邮箱
        } catch (ParseException e) {
            e.printStackTrace();
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("<html><head><meta charset=\"utf-8\"></head><body style=\"font-family:Microsoft Yahei\"><table style=\"width:680px;margin:0 auto;zoom:1;font-family:Microsoft Yahei,sans-serif\"><tr><td><img src=\"http://www.cloudfashion.org/resources/images/mailHeader.png\" border=\"0\"></td></tr><tr><td style=\"margin-bottom:20px;width:680px;\"><table style=\"width:680px;border:1px solid #c3c3c3;border-spacing:0px;\"><tr><td style=\"padding:6px 8px;font-size:14px;color:#fff;background:#0060af\">订单编号：<a href=\"http://www.cloudfashion.org/orderCenter/sellerViewSampleOrder/"+sampleOrder.getId()+"\" title=\"点击查看订单\" style=\"text-decoration:none;color:#fff\">");
        stringBuffer.append(sampleOrder.getOrderNo());
        stringBuffer.append("</a></td></tr><tr><td><table><tr><td style=\"line-height:30px;font-weight:bold\">买家信息：</td></tr><tr><td>收货人：");
        stringBuffer.append(address.getReceiverName());
        stringBuffer.append("</td></tr><tr><td>联系电话：");
        stringBuffer.append(address.getPhone());
        stringBuffer.append("</td></tr><tr><td>地址：");
        stringBuffer.append(address.getState()+" " +address.getCity()+" "+address.getStreet());
        stringBuffer.append("</td></tr><tr><td>交货时间：");
        stringBuffer.append(sdf.format(sampleOrder.getDeliveryTime().getTime()));
        stringBuffer.append("</td></tr><tr><td>买家备注：");
        stringBuffer.append(sampleOrder.getRemark());
        stringBuffer.append("</td></tr><tr><td style=\"line-height:30px;font-weight:bold\">商品详情：</td></tr><tr> <td><table style=\"width:670px;border-collapse:collapse\"><thead><tr style=\"background-color:#f8f7f7;\"><th width=\"100%\"style=\"text-align: center;font-size:1em;border:1px solid #c3c3c3;padding:3px 7px 2px 7px;background-color:#f8f7f7\" colspan=\"2\">商品名称</th></tr>");
        for (int i =0;i<sampleLines.size();i++){
            SampleLine sampleLine = sampleLines.get(i);
            stringBuffer.append("<tr><td style=\"text-align: left;font-size:1em;border:1px solid #c3c3c3;padding:3px 7px 2px 7px;width:100px\">");
            if(sampleLine.getItem().getCoverImage().isEmpty()){
                stringBuffer.append("<img src=\"http://www.cloudfashion.org/resources/img/btn-th_100.png\" style=\"vertical-align:middle\"/></td><td style=\"text-align: left;font-size:1em;border:1px solid #c3c3c3;padding:3px 7px 2px 7px;\">");
            }else{
                stringBuffer.append("<img src=\"http://www.cloudfashion.org"+sampleLine.getItem().getCoverImage()+"?size=100\" style=\"vertical-align:middle\"/></td><td style=\"text-align: left;font-size:1em;border:1px solid #c3c3c3;padding:3px 7px 2px 7px;\">");
            }

            stringBuffer.append(sampleLine.getItem().getName()+"</td></tr>");
        }
        stringBuffer.append("</table></td></tr></table></td></tr></table></td></tr></table></body></html>");
        if(!StringUtils.isEmpty(mailTo)){
            MailRecord mailRecord = new MailRecord();
            mailRecord.setBillType("sampleOrder");
            mailRecord.setMailTo(mailTo);
            mailRecord.setMailToId(userName);
            mailRecord.setSubject("云端时尚：调样订单提醒");
            mailRecord.setContent(sampleOrder.getId());
            try {
                if(mailSender.sendOrderMail(mailTo,"云端时尚：调样订单提醒",stringBuffer.toString())){
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

    private class SampleThread extends Thread{
        private SampleOrder sampleOrder;

        private SampleThread(SampleOrder sampleOrder){
            this.sampleOrder = sampleOrder;
        }

        public void run(){
            try {
                sendOrderMail(sampleOrder);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }
}
