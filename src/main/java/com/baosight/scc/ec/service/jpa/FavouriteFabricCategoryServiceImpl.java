package com.baosight.scc.ec.service.jpa;

import com.baosight.scc.ec.model.EcUser;
import com.baosight.scc.ec.model.FavouriteFabricCategory;
import com.baosight.scc.ec.repository.FavouriteFabricCategoryRepository;
import com.baosight.scc.ec.service.FavouriteFabricCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zodiake on 2014/7/11.
 */
@Service
public class FavouriteFabricCategoryServiceImpl implements FavouriteFabricCategoryService{
    @Autowired
    private FavouriteFabricCategoryRepository repository;

    @Override
    public List<FavouriteFabricCategory> findByUser(EcUser user) {
        return repository.findByUser(user);
    }
}
