package com.baosight.scc.ec.service;

import com.baosight.scc.ec.model.CartLine;
import com.baosight.scc.ec.model.Item;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2014/10/14.
 */
public interface CartService {
    List<CartLine> findAllItems(String id,Integer offset);

    CartLine findCartById(String userId, String itemId);

    void addItem(Item item,String cartId,int num);

    void increaseItemCount(Item item,String cartId,int num);

    boolean existItem(String key,String cartId);

    public void deleteItem(String id,String itemId);

    public void deleteAll(String id);

    public Map<String,List<CartLine>> tidyCart(String userId);

    CartLine findTemplateCart(String itemId, int quantity);
}
