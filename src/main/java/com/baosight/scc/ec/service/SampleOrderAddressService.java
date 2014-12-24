package com.baosight.scc.ec.service;

import com.baosight.scc.ec.model.SampleOrderCopyAddress;

/**
 * 调样册收货地址管理
 * Created by Charles on 2014/5/28.
 */
public interface SampleOrderAddressService {
    /**
     * 收货地址save方法
     * @param address
     * @return
     */
    SampleOrderCopyAddress save(SampleOrderCopyAddress address);

    /**
     * 根据id查找收货地址
     * @param id
     * @return
     */
    SampleOrderCopyAddress findById(String id);
}
