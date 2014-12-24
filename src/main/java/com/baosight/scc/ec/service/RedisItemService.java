package com.baosight.scc.ec.service;

import com.baosight.scc.ec.model.Item;

/**
 * Created by Administrator on 2014/10/16.
 */
public interface RedisItemService {
    void save(Item item);

    void delete(Item item);

}
