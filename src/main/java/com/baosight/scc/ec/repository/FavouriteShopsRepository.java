package com.baosight.scc.ec.repository;

import com.baosight.scc.ec.model.EcUser;
import com.baosight.scc.ec.model.FavouriteShops;
import com.baosight.scc.ec.model.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by zodiake on 2014/6/3.
 */
public interface FavouriteShopsRepository extends PagingAndSortingRepository<FavouriteShops,FavouriteShops.Id>{
    public Page<FavouriteShops> findByUser(EcUser user,Pageable pageable);
    public Long countByUserAndShop(EcUser user,Shop shop);

    //统计当前用户收藏的店铺
    public Long countByUser(EcUser user);
}
