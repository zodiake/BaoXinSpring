package com.baosight.scc.ec.service.jpa;

import com.baosight.scc.ec.model.Address;
import com.baosight.scc.ec.model.EcUser;
import com.baosight.scc.ec.repository.AddressRepository;
import com.baosight.scc.ec.security.UserContext;
import com.baosight.scc.ec.service.AddressService;
import com.baosight.scc.ec.service.EcUserService;
import com.baosight.scc.ec.utils.GuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Charles on 2014/5/20.
 */
@Service
@Transactional
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressRepository ar;
    @Autowired
    private UserContext userContext;
    @Autowired
    private EcUserService ecUserService;
    @PersistenceContext
    private EntityManager em;
    @Override
    public Address save(Address address) {
        address.setId(GuidUtil.newGuid());
        return ar.save(address);
    }

    @Override
    public Address findById(String id) {
        return ar.findOne(id);
    }

    @Override
    public List<Address> findByUser(EcUser ecUser) {
        return ar.findByUser(ecUser);
    }

    @Override
    public Address update(Address address) {
        return ar.save(address);
    }

    @Override
    public EcUser  delete(String id,EcUser user) {
        EcUser source=em.find(EcUser.class,user.getId());
        Address defaultAddress=source.getDefaultAddress();
        Address address=new Address();
        address.setId(id);
        if(defaultAddress != null){
            if(defaultAddress.equals(address)){
                source.setDefaultAddress(null);
            }
        }
        ar.delete(id);
        return source;
    }
}
