package com.baosight.scc.ec.service.redis;

import com.baosight.scc.ec.model.Item;
import com.baosight.scc.ec.model.SampleBook;
import com.baosight.scc.ec.service.ItemService;
import com.baosight.scc.ec.service.RedisItemService;
import com.baosight.scc.ec.service.SampleBookService;
import com.baosight.scc.ec.type.ItemState;
import com.baosight.scc.ec.utils.RedisConstant.ConstantKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.baosight.scc.ec.utils.RedisConstant.ConstantKey.*;

/**
 * Created by Administrator on 2014/11/21.
 */
@Service
public class RedisSampleBookServiceImpl implements SampleBookService {
    @Autowired
    public StringRedisTemplate template;
    @Autowired
    public ItemService itemService;

    @Override
    public void addItem(String userId, String id) {
        template.opsForSet().add(REDIS_SAMPLE + userId, id);
    }

    @Override
    public SampleBook findByUserId(String userId) {
        SampleBook book = new SampleBook();
        List<Item> items = new ArrayList<Item>();
        Set<String> sets = template.opsForSet().members(REDIS_SAMPLE + userId);
        for (String i : sets) {
            Item item = itemService.findByIdThroughRedis(i);
            items.add(item);
        }
        book.setItem(items);
        return book;
    }

    @Override
    public boolean exist(String userId, String itemId) {
        return template.opsForSet().isMember(REDIS_SAMPLE + userId, itemId);
    }

    @Override
    public void deleteItem(String userId, String itemId) {
        template.opsForSet().remove(REDIS_SAMPLE + userId, itemId);
    }

    @Override
    public Map<String, List<Item>> tidyBook(String userId) {
        SampleBook sampleBook = findByUserId(userId);
        Map<String, List<Item>> bookMap = new HashMap<String, List<Item>>();
        List<Item> itemList = sampleBook.getItem();
        if (itemList != null){
            for (int i = 0; i < itemList.size(); i++) {
                Item item = itemList.get(i);
                String seller = item.getCreatedBy().getName().trim();
                if(userId.equals(seller)){
                    deleteItem(userId,item.getId());
                    continue;
                }else{
                    List<Item> items = bookMap.get(seller);
                    if(items == null){
                        items = new ArrayList<Item>();
                        items.add(item);
                        bookMap.put(seller,items);
                    }else {
                        items.add(item);
                        bookMap.put(seller,items);
                    }
                }
            }
        }
        return bookMap;
    }
}
