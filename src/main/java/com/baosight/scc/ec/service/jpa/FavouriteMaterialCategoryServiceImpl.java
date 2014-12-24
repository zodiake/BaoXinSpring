package com.baosight.scc.ec.service.jpa;

import com.baosight.scc.ec.model.EcUser;
import com.baosight.scc.ec.model.FavouriteMaterialCategory;
import com.baosight.scc.ec.repository.FavouriteMaterialCategoryRepository;
import com.baosight.scc.ec.service.FavouriteMaterialCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zodiake on 2014/7/11.
 */
@Service
public class FavouriteMaterialCategoryServiceImpl implements FavouriteMaterialCategoryService{
    @Autowired
    private FavouriteMaterialCategoryRepository repository;

    @Override
    public List<FavouriteMaterialCategory> findByUser(EcUser user) {
        return repository.findByUser(user);
    }
}
