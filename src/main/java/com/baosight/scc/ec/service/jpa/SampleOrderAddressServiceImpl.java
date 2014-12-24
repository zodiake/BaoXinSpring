package com.baosight.scc.ec.service.jpa;

import com.baosight.scc.ec.model.SampleOrderCopyAddress;
import com.baosight.scc.ec.repository.SampleOrderAddressRepository;
import com.baosight.scc.ec.service.SampleOrderAddressService;
import com.baosight.scc.ec.utils.GuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Charles on 2014/5/28.
 */
@Service
@Transactional
public class SampleOrderAddressServiceImpl implements SampleOrderAddressService {
    @Autowired
    private SampleOrderAddressRepository sar;
    @Override
    public SampleOrderCopyAddress save(SampleOrderCopyAddress address) {
        address.setId(GuidUtil.newGuid());
        return sar.save(address);
    }

    @Override
    public SampleOrderCopyAddress findById(String id) {
        return sar.findOne(id);
    }
}
