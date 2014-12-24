package com.baosight.scc.ec.repository;

import com.baosight.scc.ec.model.EcUser;
import com.baosight.scc.ec.model.FavouriteFabricCategory;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by zodiake on 2014/7/11.
 */
public interface FavouriteFabricCategoryRepository extends CrudRepository<FavouriteFabricCategory,FavouriteFabricCategory.Id>{
    public List<FavouriteFabricCategory> findByUser(EcUser user);
}
