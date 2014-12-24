package com.baosight.scc.ec.repository;

import com.baosight.scc.ec.model.EcUser;
import com.baosight.scc.ec.model.FavouriteFabricCategory;
import com.baosight.scc.ec.model.FavouriteMaterialCategory;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by zodiake on 2014/7/11.
 */
public interface FavouriteMaterialCategoryRepository extends CrudRepository<FavouriteMaterialCategory,FavouriteMaterialCategory.Id>{
    public List<FavouriteMaterialCategory> findByUser(EcUser user);
}
