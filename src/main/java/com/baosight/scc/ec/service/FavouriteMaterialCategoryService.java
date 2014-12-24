package com.baosight.scc.ec.service;

import com.baosight.scc.ec.model.EcUser;
import com.baosight.scc.ec.model.FavouriteFabricCategory;
import com.baosight.scc.ec.model.FavouriteMaterialCategory;
import com.baosight.scc.ec.repository.FavouriteFabricCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zodiake on 2014/7/11.
 */
public interface FavouriteMaterialCategoryService {
    public List<FavouriteMaterialCategory> findByUser(EcUser user);
}
