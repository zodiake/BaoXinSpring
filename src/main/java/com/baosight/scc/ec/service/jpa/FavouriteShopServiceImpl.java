package com.baosight.scc.ec.service.jpa;

import com.baosight.scc.ec.model.*;
import com.baosight.scc.ec.repository.FavouriteShopsRepository;
import com.baosight.scc.ec.repository.ShopRepository;
import com.baosight.scc.ec.service.CompositeScoreService;
import com.baosight.scc.ec.service.EcUserService;
import com.baosight.scc.ec.service.FavouriteShopService;
import com.baosight.scc.ec.type.ItemState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.*;

/**
 * Created by zodiake on 2014/6/3.
 */
@Service
@Transactional
public class FavouriteShopServiceImpl implements FavouriteShopService {
    @Autowired
    private FavouriteShopsRepository repository;
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private EcUserService ecUserService;
    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private CompositeScoreService compositeScoreService;

    @Override
    public Page<FavouriteShops> findByUser(EcUser user, Pageable pageable) {
        Page<FavouriteShops> page = repository.findByUser(user, pageable);
        for (Iterator<FavouriteShops> i = page.iterator(); i.hasNext(); ) {
            FavouriteShops favouriteShops = i.next();
            Shop shop = favouriteShops.getShop();
            EcUser u = shop.getUser();

            TypedQuery<Item> query = em.createNamedQuery("item.findByUser", Item.class);
            query.setParameter("user", u).setParameter("state", ItemState.出售中);
            List<Item> items = query.setMaxResults(4).getResultList();
            for (Item im : items) {
                if (im instanceof Fabric) {
                //    im.setUrl(Fabric.class.getName());
                    im.setUrl("fabric");
                } else if (im instanceof Material) {
                //    im.setUrl(Material.class.getName());
                    im.setUrl("material");
                }
            }
            shop.setNewestItem(items);
            //added by sam 2014-7-24 获取企业信息
            EcUser uDetail = ecUserService.findById(shop.getUser().getId());
            CompositeScore score = compositeScoreService.findByUser(u);
            uDetail.setCompositeScore(score);
            shop.setUser(uDetail);
            //ended by sam

        }
        return page;
    }

    @Override
    public FavouriteShops save(EcUser user, Shop shop) {
        //modifyed by sam 2014-7-25 注释内容为王祺荣写的
       /* FavouriteShops favouriteShops=new FavouriteShops();
        favouriteShops.setShop(shop);
        favouriteShops.setUser(user);*/
        //下面注释部分@sam
        /*EcUser ecUser = new EcUser();
        ecUser.setId(shop.getId());
        shop.setUser(ecUser);
        //判断该店铺是否存在，不存在添加
        if(shopRepository.findOne(shop.getId()).getId() == null){
            shopRepository.save(shop);
        }*/
        FavouriteShops favouriteShops=new FavouriteShops(user,shop);
        //ended by sam

        favouriteShops.setCreatedTime(Calendar.getInstance());
        FavouriteShops source=repository.save(favouriteShops);
        return source;
    }

    @Override
    public boolean countByUserAndShop(EcUser user, Shop shop) {
        return repository.countByUserAndShop(user,shop)>0;
    }

    @Override
    public void delete(FavouriteShops favouriteShops) {
        repository.delete(favouriteShops);
    }

    @Override
    public Long countByUser(EcUser user) {
        return repository.countByUser(user);
    }
}
