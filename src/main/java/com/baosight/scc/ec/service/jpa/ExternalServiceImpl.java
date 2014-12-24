package com.baosight.scc.ec.service.jpa;

import com.baosight.scc.ec.model.CommonUser;
import com.baosight.scc.ec.model.Designer;
import com.baosight.scc.ec.model.EcProvider;
import com.baosight.scc.ec.model.EcUser;
import com.baosight.scc.ec.service.*;
import com.baosight.scc.ec.type.ItemState;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.io.IOException;
import java.util.*;

/**
 * Created by Charles on 2014/7/23.
 * 供应链外部数据接口
 */
@Service
public class ExternalServiceImpl implements ExternalService {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private CommonUserService commonUserService;
    @Autowired
    private EcProviderService ecProviderService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private DemandOrderService demandOrderService;

    @Override
    @Cacheable(value = "homeDesignerCacheLoadService")
    public List<Designer> designerOnHome() {
        List<Designer> list = ecProviderService.getTopDesigner();
        String querySql = "";
        List result = new ArrayList();
        List<Designer> results = new ArrayList<Designer>();
        //如果用户接口中查询不到置顶的设计师，则从设计师模块查询置顶的数据，然后去用户接口查询设计师名称
        if(list == null){
            querySql = "SELECT DISTINCT t1.DESIGN_ID,t2.HEAD_URL,t3.RESUME,t1.REC_CREATE_TIME FROM T_FC_US_TOP_DESIGN t1 left join T_FC_US_HEAD t2 on t1.DESIGN_ID = t2.DESIGN_ID left join T_FC_US_RESUME t3 on t1.DESIGN_ID = t3.DESIGN_ID and t3.YEAR is null WHERE t1.ALIVE_FLAG='1' fetch first 5 rows only";
            Query query = em.createNativeQuery(querySql);
            String userIds = "";
            String users = "";
            result = query.getResultList();
            for (Iterator iterator = result.iterator(); iterator.hasNext(); ) {
                Object[] values = (Object[]) iterator.next();
                Designer designer = new Designer();
                designer.setId((String) values[0]);
                userIds += "," +values[0];
                designer.setHeadUrl((String) values[1]);
                designer.setResume((String) values[2]);
                results.add(designer);
            }
            if (userIds.length() > 1) {
                users = ecProviderService.findMultipleUser(userIds.substring(1), "");
            }

            JsonNode rootNode = null;
            ObjectMapper m = new ObjectMapper();
            Map<String, String> userMap = new HashMap<String, String>();
            if (!users.isEmpty()) {
                try {
                    rootNode = m.readTree(users);
                    JsonNode node = rootNode.get("data");
                    if (node.isArray()) {
                        for (final JsonNode objNode : node) {
                            userMap.put(objNode.get("guid").getTextValue(), objNode.get("empName").getTextValue());
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (!userMap.isEmpty()) {
                for (int i = 0; i < results.size(); i++) {
                    Designer d = results.get(i);
                    d.setName(userMap.get(d.getId()));
                }
            }
        }else{
            querySql = "SELECT DISTINCT t1.DESIGN_ID,t1.HEAD_URL,t2.RESUME FROM T_FC_US_HEAD t1 left join T_FC_US_RESUME t2 on t1.DESIGN_ID = t2.DESIGN_ID and t2.YEAR is null WHERE t1.DESIGN_ID in(:id) fetch first 5 rows only";
            Query query = em.createNativeQuery(querySql);
            List<String> ids=new ArrayList<String>();
            Map<String,Designer> userMap = new HashMap<String, Designer>();
            for(Designer d:list){
                ids.add(d.getId());
                userMap.put(d.getId(),d);
            }
            query.setParameter("id", ids);
            result = query.getResultList();
            for (Iterator iterator = result.iterator(); iterator.hasNext(); ) {
                Object[] values = (Object[]) iterator.next();
                Designer designer = userMap.get((String) values[0]);
                designer.setHeadUrl((String) values[1]);
                designer.setResume((String) values[2]);
            }
            for (Designer designer:userMap.values()){
                results.add(designer);
            }
        }
        return results;
    }

    @Override
    @Cacheable(value = "homeBrandCacheLoadService")
    public List<Designer> brandsOnHome() {
        List<String> userNames = ecProviderService.getTopBrand();
        String querySql = "";
        List<Designer> list = new LinkedList<Designer>();
        Map<String,Designer> map = new HashMap<String, Designer>();
        if(userNames.size() < 1){
            querySql = "SELECT DISTINCT t1.BRANDS_ID ,t2.HEAD_URL,t1.REC_CREATE_TIME FROM T_FC_US_TOP_BRANDS t1 left join T_FC_US_HEAD_BRANDS t2 on t1.BRANDS_ID = t2.BRANDS_ID WHERE t1.ALIVE_FLAG='1' fetch first 9 rows only";
            Query query = em.createNativeQuery(querySql);
            List result =  query.getResultList();

            for (Iterator iterator = result.iterator(); iterator.hasNext(); ) {
                Object[] values = (Object[]) iterator.next();
                Designer designer = new Designer();
                designer.setId((String) values[0]);
                designer.setHeadUrl((String) values[1]);
                list.add(designer);
            }

        }else{
            querySql = "SELECT DISTINCT t1.BRANDS_ID ,t1.HEAD_URL,t1.REC_CREATE_TIME FROM T_FC_US_HEAD_BRANDS t1 WHERE t1.brands_id in (:ids) fetch first 9 rows only";
            Query query = em.createNativeQuery(querySql);
            query.setParameter("ids",userNames);
            List result =  query.getResultList();

            for (Iterator iterator = result.iterator(); iterator.hasNext(); ) {
                Object[] values = (Object[]) iterator.next();
                Designer designer = new Designer();
                designer.setId((String) values[0]);
                designer.setHeadUrl((String) values[1]);
                map.put((String)values[0],designer);
            }

            for(String s:userNames){
                list.add(map.get(s));
            }
        }

        return list;
    }

    @Override
    public List<Designer> attentionDesigners(int pageTo, int pageSize, String userId) {
        String querySql = "SELECT * FROM(SELECT INNER2_.*, ROWNUMBER() OVER(ORDER BY ORDER OF INNER2_) AS ROWNUMBER_ FROM(SELECT DISTINCT T1.ATTENTION_ID,T2.HEAD_URL,T3.RESUME,T1.REC_CREATE_TIME FROM T_FC_US_ATTENTION_DESIGN T1 LEFT JOIN T_FC_US_HEAD T2 ON T1.ATTENTION_ID = T2.DESIGN_ID LEFT JOIN T_FC_US_RESUME T3 ON T1.ATTENTION_ID = T3.DESIGN_ID and t3.YEAR is null WHERE T1.ALIVE_FLAG  = 1 and T1.USER_ID = '" + userId + "' ORDER BY T1.REC_CREATE_TIME DESC FETCH FIRST " + (pageTo * pageSize) + " ROWS ONLY) AS INNER2_)AS INNER1_ WHERE ROWNUMBER_ > " + (pageSize * (pageTo - 1)) + " ORDER BY ROWNUMBER_";
        List<Designer> designers = Designers(querySql);
        return designers;
    }

    @Override
    public List<Designer> attentionBrands(int pageTo, int pageSize, String userId) {
        String querySql = "SELECT * FROM(SELECT INNER2_.*, ROWNUMBER() OVER(ORDER BY ORDER OF INNER2_) AS ROWNUMBER_ FROM(SELECT DISTINCT T1.ATTENTION_ID,T2.HEAD_URL,T1.REC_CREATE_TIME FROM T_FC_US_ATTENTION_BRANDS T1 LEFT JOIN T_FC_US_HEAD_BRANDS T2 ON T1.ATTENTION_ID = T2.BRANDS_ID WHERE T1.ALIVE_FLAG  = 1 and  T1.USER_ID = '" + userId + "' ORDER BY T1.REC_CREATE_TIME DESC FETCH FIRST " + (pageTo * pageSize) + " ROWS ONLY) AS INNER2_)AS INNER1_ WHERE ROWNUMBER_ > " + (pageSize * (pageTo - 1)) + " ORDER BY ROWNUMBER_";
        List<Designer> brands = Brands(querySql);
        return brands;
    }

    @Override
    public int totalDesigners(String userId) {
        String queryCountSql = "SELECT count(*) FROM T_FC_US_ATTENTION_DESIGN T1 LEFT JOIN T_FC_US_HEAD T2 ON T1.ATTENTION_ID = T2.DESIGN_ID LEFT JOIN T_FC_US_RESUME T3 ON T1.ATTENTION_ID = T3.DESIGN_ID and t3.YEAR is null WHERE T1.USER_ID ='" + userId + "'";
        Query queryCount = em.createNativeQuery(queryCountSql);
        int resultCount = Integer.parseInt(queryCount.getSingleResult().toString());  //记录总数
        return resultCount;
    }

    @Override
    public int totalBrands(String userId) {
        String queryCountSql = "SELECT count(*) FROM T_FC_US_ATTENTION_BRANDS T1 LEFT JOIN T_FC_US_HEAD_BRANDS T2 ON T1.ATTENTION_ID = T2.BRANDS_ID WHERE T1.USER_ID ='" + userId + "'";
        Query queryCount = em.createNativeQuery(queryCountSql);
        int resultCount = Integer.parseInt(queryCount.getSingleResult().toString());  //记录总数
        return resultCount;
    }

    @Override
    @Cacheable(value = "statisticsCacheLoadService")
    public Map<String, String> statistics() {
        Map<String, String> statisticsMap = new HashMap<String, String>();
        String businessCount = "select count(*) as total  from (select DISTINCT r.id,r.USER_NAME from T_CS_BUSINESS_RELATION r,t_cs_company c,t_um_user_info u where r.USER_NAME=u.EMP_CODE and r.USER_NAME = c.USER_NAME and u.AUDIT_FLAG='2' and POSSTR(r.BUSINESS_RELATION,'gy') > 0)"; // 供应商统计
        String designerCount = "SELECT count(*) as total FROM T_UM_USER_INFO u join T_CS_BUSINESS_RELATION r on u.EMP_CODE=r.USER_NAME where 1=1 and  u.emp_type =  'D'  and  audit_flag = '2'"; // 设计师总数
        String eventCount = "select sum(a) as total  from (SELECT count(*) a from T_FC_DB_MATERIAL union all SELECT count(*) a from T_FC_DB_EVENT) temp"; // 设计素材统计
        String itemCount = itemService.itemCount(ItemState.出售中).toString(); // 商品统计
        String brandCount = ecProviderService.getBrandCount();
        Query queryBusiness = em.createNativeQuery(businessCount);
        Query queryDesigner = em.createNativeQuery(designerCount);
        Query queryEvent = em.createNativeQuery(eventCount);
        String resultBusinessCount = queryBusiness.getSingleResult() + "";
        String resultDesignerCount = queryDesigner.getSingleResult() + "";
        String resultEventCount = queryEvent.getSingleResult() + "";
        statisticsMap.put("businessCount", resultBusinessCount);
        statisticsMap.put("designerCount", resultDesignerCount);
        statisticsMap.put("eventCount", resultEventCount);
        statisticsMap.put("itemsCount", itemCount);
        statisticsMap.put("brandCount", brandCount);
        return statisticsMap;
    }

    @Override
    @Cacheable(value = "demandStatisticsCacheLoadService")
    public Map<String, String> demandStatistics() {
        Map<String, String> statisticsMap = new HashMap<String, String>();
        String demandCount = demandOrderService.demandCount().toString(); // 求购统计
        String newDemandCount = "select count(*) as total  from T_EC_DEMANDORDER where state = '发布中'";
        Query queryNewDemand = em.createNativeQuery(newDemandCount);
        String resultNewDemand = queryNewDemand.getSingleResult() + "";
        statisticsMap.put("demandCount", demandCount);
        statisticsMap.put("newDemandCount", resultNewDemand);
        return statisticsMap;
    }

    @Override
    @Transactional
    public int cancelAttentionDesigner(String userId, String designerId) {
        String querySql = "UPDATE T_FC_US_ATTENTION_DESIGN SET ALIVE_FLAG = '0' WHERE USER_ID='"+userId+"' AND ATTENTION_ID='" + designerId + "'";
        Query query = em.createNativeQuery(querySql);
        int result = query.executeUpdate();
        return result;
    }

    @Override
    @Transactional
    public int cancelAttentionBrand(String userId, String brandId) {
        String querySql = "UPDATE T_FC_US_ATTENTION_BRANDS SET ALIVE_FLAG = '0' WHERE USER_ID='"+userId+"' AND ATTENTION_ID='" + brandId + "'";
        Query query = em.createNativeQuery(querySql);
        int result = query.executeUpdate();
        return result;
    }

    @Override
    public List<Designer> recommendDesigners(String userId) {
        String querySql = "SELECT DISTINCT T1.DESIGN_ID,T2.HEAD_URL FROM T_FC_US_STYLE_DESIGN T1, T_FC_US_HEAD T2 WHERE T1.STYLE_CODE IN (SELECT DISTINCT STYLE_CODE FROM T_FC_US_STYLE_DESIGN WHERE DESIGN_ID IN (SELECT ATTENTION_ID FROM T_FC_US_ATTENTION_DESIGN WHERE USER_ID = '"+userId+"' AND ALIVE_FLAG = '1')) AND T1.DESIGN_ID NOT IN (SELECT ATTENTION_ID FROM T_FC_US_ATTENTION_DESIGN WHERE USER_ID = '"+userId+"' AND ALIVE_FLAG = '1') AND T1.DESIGN_ID = T2.DESIGN_ID FETCH FIRST 5 ROWS ONLY";
        List<Designer> designerList = Designers(querySql);
        return designerList;
    }

    @Override
    public List<Designer> recommendBrands(String userId) {
        String querySql = "SELECT DISTINCT T1.BRANDS_ID,T2.HEAD_URL FROM T_FC_US_STYLE_BRAND T1,T_FC_US_HEAD_BRANDS T2 WHERE T1.STYLE_ID IN (SELECT DISTINCT STYLE_ID FROM T_FC_US_STYLE_BRAND WHERE BRANDS_ID IN(SELECT ATTENTION_ID FROM T_FC_US_ATTENTION_BRANDS WHERE USER_ID = '"+userId+"' AND ALIVE_FLAG = '1')) AND T1.BRANDS_ID NOT IN (SELECT ATTENTION_ID FROM T_FC_US_ATTENTION_BRANDS WHERE USER_ID = '"+userId+"' AND ALIVE_FLAG = '1') AND T1.BRANDS_ID = T2.BRANDS_ID FETCH FIRST 5 ROWS ONLY";
        List<Designer> brandList = Brands(querySql);
        return brandList;
    }


    public List<Designer> Brands(String querySql) {
        Query query = em.createNativeQuery(querySql);
        List result = new ArrayList();
        result = query.getResultList();
        List<Designer> list = new ArrayList();
        String userIds = "";
        for (Iterator iterator = result.iterator(); iterator.hasNext(); ) {
            Object[] values = (Object[]) iterator.next();
            Designer designer = new Designer();
            designer.setId((String) values[0]);
            userIds += "," + (String) values[0];
            designer.setHeadUrl((String) values[1]);
            list.add(designer);
        }
        String userNames = "";
        if (userIds.length() > 1) {
            userNames = ecProviderService.findMultipleUser(userIds.substring(1), "");
        }
        JsonNode rootNode = null;
        ObjectMapper m = new ObjectMapper();
        Map<String, String> userMap = new HashMap<String, String>();
        if (userNames != null && userNames != "") {
            try {
                rootNode = m.readTree(userNames);
                JsonNode node = rootNode.get("data");
                if (node.isArray()) {
                    for (final JsonNode objNode : node) {
                        userMap.put(objNode.get("guid").getTextValue(), objNode.get("brandName").getTextValue());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (userMap != null) {
            for (int i = 0; i < list.size(); i++) {
                Designer d = list.get(i);
                d.setName(userMap.get(d.getId()));
            }
        }
        return list;
    }

    public List<Designer> Designers(String querySql) {
        Query query = em.createNativeQuery(querySql);
        List result = new ArrayList();
        result = query.getResultList();
        List<Designer> list = new ArrayList();
        String userIds = "";
        for (Iterator iterator = result.iterator(); iterator.hasNext(); ) {
            Object[] values = (Object[]) iterator.next();
            Designer designer = new Designer();
            designer.setId((String) values[0]);
            designer.setHeadUrl((String) values[1]);
            if(values.length<3){
                designer.setResume("");
            }else{
                designer.setResume((String)values[2] );
            }
            list.add(designer);
            userIds += ","+values[0];
        }
        String userNames = "";
        if (userIds.length() > 1) {
            userNames = ecProviderService.findMultipleUser(userIds.substring(1), "");
        }
        JsonNode rootNode = null;
        ObjectMapper m = new ObjectMapper();
        Map<String, String> userMap = new HashMap<String, String>();
        if (userNames != null && userNames != "") {
            try {
                rootNode = m.readTree(userNames);
                JsonNode node = rootNode.get("data");
                if (node.isArray()) {
                    for (final JsonNode objNode : node) {
                        userMap.put(objNode.get("guid").getTextValue(), objNode.get("empName").getTextValue());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (userMap != null) {
            for (int i = 0; i < list.size(); i++) {
                Designer d = list.get(i);
                d.setName(userMap.get(d.getId()));
            }
        }
        return list;
    }

    @Override
    @Cacheable(value = "homeOrderingCacheLoadService")
    public List<Designer> orderingOnHome() {
        List<Designer> list = new LinkedList<Designer>();
        String querySql = "SELECT ORDER_SHOW_ID, ORDER_SHOW_NAME, REC_CREATE_TIME FROM T_PM_OS_ORDER_SHOW WHERE ORDER_SHOW_STATUS = '20' and ALIVE_FLAG = '1' ORDER BY REC_CREATE_TIME DESC fetch first 5 rows only";
        Query query = em.createNativeQuery(querySql);
        List result =  query.getResultList();

        for (Iterator iterator = result.iterator(); iterator.hasNext(); ) {
            Object[] values = (Object[]) iterator.next();
            Designer designer = new Designer();
            designer.setId((String) values[0]);
            designer.setName((String) values[1]);
            list.add(designer);
        }
        return list;
    }
}
