package com.baosight.scc.ec.service.redis;

import com.baosight.scc.ec.model.Item;
import com.baosight.scc.ec.service.EcUserService;
import com.baosight.scc.ec.service.RedisItemService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import static com.baosight.scc.ec.utils.RedisConstant.ConstantKey.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2014/10/16.
 */
@Service
public class RedisItemServiceImpl implements RedisItemService {
    @Autowired
    protected StringRedisTemplate template;
    @Autowired
    private EcUserService ecUserService;

    @Override
    public void save(Item item) {
        String id = item.getId();
        template.opsForHash().putAll(REDIS_ITEM + id, initRedisDate(item));
        template.opsForHash().putAll(REDIS_PRICE + id, initPrice(item));
    }

    @Override
    public void delete(Item item) {
        String id = item.getId();
        String[] key = {ItemKey.cover, ItemKey.id, ItemKey.name, ItemKey.providerId, ItemKey.providerName, ItemKey.state};
        template.opsForHash().delete(REDIS_ITEM + id, key);
        Set<String> priceKey = (Set) template.opsForHash().keys(REDIS_PRICE + id);
        for (String s : priceKey)
            template.opsForHash().delete(REDIS_PRICE + id, s);
    }

    private Map<String, String> initRedisDate(Item item) {
        Map<String, String> result = new HashMap<String, String>();
        String providerId = item.getCreatedBy().getId();
        String providerName = ecUserService.findById(providerId).getCompanyName();
        String name = item.getName();
        String image = StringUtils.isEmpty(item.getCoverImage()) ? "" : item.getCoverImage();

        result.put(ItemKey.id, item.getId());
        result.put(ItemKey.cover, image);
        result.put(ItemKey.providerId, providerId);
        result.put(ItemKey.providerName, providerName);
        result.put(ItemKey.name, name);
        result.put(ItemKey.state, item.getState().name());
        result.put(ItemKey.url, item.getUrl());

        return result;
    }

    private Map<String, String> initPrice(Item item) {
        Map<Double, Double> source = item.getRange();
        Map<String, String> result = new HashMap<String, String>();
        for (Map.Entry<Double, Double> e : source.entrySet()) {
            result.put(e.getKey().toString(), e.getValue().toString());
        }
        return result;
    }
}
