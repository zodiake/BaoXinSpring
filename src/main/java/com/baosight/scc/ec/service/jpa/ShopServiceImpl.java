package com.baosight.scc.ec.service.jpa;

import com.baosight.scc.ec.model.CompositeScore;
import com.baosight.scc.ec.model.EcUser;
import com.baosight.scc.ec.model.Shop;
import com.baosight.scc.ec.model.UserInfo;
import com.baosight.scc.ec.repository.ShopRepository;
import com.baosight.scc.ec.search.service.EsService;
import com.baosight.scc.ec.service.CompositeScoreService;
import com.baosight.scc.ec.service.EcUserService;
import com.baosight.scc.ec.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sam on 2014/7/26.
 */
@Service
@Transactional
public class ShopServiceImpl implements ShopService {
    @Autowired
    private ShopRepository shopRepository;
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private EcUserService ecUserService;
    @Autowired
    private CompositeScoreService compositeScoreService;
    @Autowired
    private EsService esService;

    @Override
    public List<Shop> findByUser(EcUser user) {
        List<Shop> page = shopRepository.findByUser(user);
        return page;
    }

    /*
    更新搜索引擎供应商数据@sam 2014-8-27
     */
    @Override
    public void updateELSShops(){
        String sql = "select u from EcUser u ";
        int offset = 0;
        Query query = em.createQuery(sql).setFirstResult(offset).setMaxResults(200);
        List<EcUser> ecUserList = new ArrayList<EcUser>();
        ecUserList = query.getResultList();
        while (ecUserList.size()>0){
        //    List<UserInfo> userInfos = new ArrayList<UserInfo>();
        //    ecUserList = new ArrayList<EcUser>();
        //    ecUserList = query.getResultList();
            for (EcUser user : ecUserList){
            //    user = ecUserService.findById(user.getId());
                UserInfo userInfo = new UserInfo();
            //    CompositeScore compositeScore = compositeScoreService.findByUser(user);
            //    user.setCompositeScore(compositeScore);
                userInfo.setId(user.getId().trim());
            //    userInfo.setId("haokuo".trim());
                /*userInfo.setBusiness(user.getBusiness());
                userInfo.setCompanyName(user.getCompanyName());
                userInfo.setCompanyRemark(user.getCompanyRemark());
                userInfo.setLogo(user.getLogo());
                userInfo.setYyzz(user.getYyzz());*/
                userInfo.setScore(user.getCompositeScore().getScore());
            //    userInfo.setScore(500);

                Map<String, Map<String, Object>> map = new HashMap<String, Map<String, Object>>();
                map.put(userInfo.getId(),convertObjectToMap(userInfo));
                boolean flag=esService.updateByIdBulk("user",map);
                System.out.println("====================================flag=..."+flag);
            }
            em.clear();
            offset=offset+200;
            query = em.createQuery(sql).setFirstResult(offset).setMaxResults(200);
            ecUserList = query.getResultList();

        }
    }

    private List<Map<String, Object>> convertObjectToList(UserInfo userInfo) {
        List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
        lists.add(convertObjectToMap(userInfo));
        return lists;
    }

    private Map<String, Object> convertObjectToMap(UserInfo userInfo) {
        Map<String, Object> map = new HashMap<String, Object>();
    //    map.put("_id", userInfo.getId());
        /*map.put("focus", userInfo.getMianProductService());
        map.put("desc", userInfo.getCompanyRemark());
        map.put("name", userInfo.getCompanyName());
        map.put("scope", userInfo.getBusiness());
        map.put("money", userInfo.getOperatingCapital());
        map.put("type", userInfo.getUserType());
        map.put("cover", userInfo.getLogo());
        map.put("yyzz", userInfo.getYyzz());*/
        map.put("score",userInfo.getScore());
        return map;
    }
}
