package com.baosight.scc.ec.service;

import com.baosight.scc.ec.model.Item;
import com.baosight.scc.ec.model.SampleBook;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2014/11/21.
 */
public interface SampleBookService {
    public void addItem(String userId,String id);

    public SampleBook findByUserId(String userId);

    public boolean exist(String userId,String itemId);

    public void deleteItem(String userId,String itemId);

    public Map<String, List<Item>> tidyBook(String userId);
}
