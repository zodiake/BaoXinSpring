package com.baosight.scc.ec.service;

import com.baosight.scc.ec.model.EcUser;
import com.baosight.scc.ec.model.FavouriteFabricCategory;

import java.util.List;

/**
 * Created by zodiake on 2014/7/11.
 */
public interface FavouriteFabricCategoryService {
    public List<FavouriteFabricCategory> findByUser(EcUser user);
}
