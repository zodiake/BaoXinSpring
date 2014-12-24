package com.baosight.scc.ec.service.jpa;

import com.baosight.scc.ec.model.*;
import com.baosight.scc.ec.repository.EcUserRepository;
import com.baosight.scc.ec.security.UserContext;
import com.baosight.scc.ec.service.CommonUserService;
import com.baosight.scc.ec.service.EcProviderService;
import com.baosight.scc.ec.service.EcUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
@Transactional
public class EcUserServiceImpl implements EcUserService {
    @Autowired
    private EcUserRepository er;
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private EcProviderService ecProviderService;
    @Autowired
    private UserContext userContext;
    @Autowired
    private CommonUserService commonUserService;

    private Logger logger= LoggerFactory.getLogger(EcUserServiceImpl.class);

    /**
     * 根据id，查询服务商信息
     *
     * @param id 服务商id
     * @return
     * @author sam
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public EcUser findById(String id) {
        String userType="";
        EcUser user = null;
        userType = commonUserService.findUserTypeById(id);
        if("E".equals(userType)){
            EcProvider ecProvider = ecProviderService.findOne(id);
            user = new EcUser(ecProvider);
        }else{
            CommonUser commonUser = commonUserService.findOne(id);
            user = new EcUser(commonUser);
        }
        return user;
    }

    @Override
    public EcUser save(EcUser user) {
        return er.save(user);
    }

    @Override
    public List<FavouriteMaterialCategory> findUserPref(EcUser user) {
        EcUser source = er.findOne(user.getId());
        List<FavouriteMaterialCategory> categories=source.getPreferMaterialCategory();
        return categories;
    }

    public List<FavouriteFabricCategory> findFabricCategoryPref(EcUser user){
        EcUser source = er.findOne(user.getId());
        logger.info("size:"+source.getPreferFabricCategory().size());
        return source.getPreferFabricCategory();
    }

    @Override
    public EcUser findByName(String name) {
        return this.er.findByName(name);
    }
}

