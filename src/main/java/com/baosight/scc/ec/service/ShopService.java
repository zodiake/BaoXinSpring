package com.baosight.scc.ec.service;

import com.baosight.scc.ec.model.EcUser;
import com.baosight.scc.ec.model.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by sam on 2014/7/26.
 */
public interface ShopService {
    //处理后得到一个Shop
    List<Shop> findByUser(EcUser user);

    public void updateELSShops();
}
