package com.baosight.scc.ec.service.jpa;


import com.baosight.scc.ec.model.Advertisement;
import com.baosight.scc.ec.model.AdvertisementPosition;
import com.baosight.scc.ec.repository.AdvertisementRepository;
import com.baosight.scc.ec.service.AdvertisementService;
import com.baosight.scc.ec.utils.GuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Charles on 2014/6/3.
 */
@Service
@Transactional
public class AdvertisementServiceImpl implements AdvertisementService {
    @Autowired
    private AdvertisementRepository ar;
    @Override
    public Advertisement save(Advertisement advertisement) {
        advertisement.setId(GuidUtil.newGuid());
        return ar.save(advertisement);
    }

    @Override
    public Advertisement update(Advertisement advertisement,String id) {
        Advertisement ad = findById(id);
        ad.setAdvertisementPosition(advertisement.getAdvertisementPosition());
        ad.setCoverPath(advertisement.getCoverPath());
        ad.setTitle(advertisement.getTitle());
        ad.setLink(advertisement.getLink());
        ad.setIsValid(advertisement.getIsValid());
        ad.setDesc(advertisement.getDesc());
        ad.setSubTitle(advertisement.getSubTitle());
        return ad;
    }

    @Override
    public Advertisement findById(String id) {
        return ar.findOne(id);
    }

    @Override
    public Page<Advertisement> findByIsValid(int isValid, Pageable pageable) {
        return ar.findByIsValid(isValid, pageable);
    }

    @Override
    @Cacheable(value = "homeBanner")
    public List<Advertisement> findByAdvertisementPositionAndIsValid(AdvertisementPosition advertisementPosition, int isValid, Pageable pageable) {
        return ar.findByAdvertisementPositionAndIsValid(advertisementPosition, isValid, pageable);
    }

    @Override
    public List<Advertisement> updateAdContext(AdvertisementPosition position){
        Sort sort = new Sort(Sort.Direction.DESC,  "createdTime");
        PageRequest pageRequest = new PageRequest(0, 5, sort);
        PageRequest fabricReportPageRequest = new PageRequest(0,6,sort);
        PageRequest bannerPageRequest = new PageRequest(0,12,sort);
        PageRequest shopPageRequest = new PageRequest(0,4,sort);
        List<Advertisement> list = new ArrayList<Advertisement>();
        if(position.getPositionNo().equals("bannerOnHome")){
            //平台广告加载
            list = ar.findByAdvertisementPositionAndIsValid(position, 0, bannerPageRequest);
        }else if(position.getPositionNo().equals("newRecommendOnHome")){
            list = ar.findByAdvertisementPositionAndIsValid(position, 0, pageRequest);
        }else if(position.getPositionNo().equals("fabricShop")){
            list = ar.findByAdvertisementPositionAndIsValid(position, 0, shopPageRequest);
        }else if(position.getPositionNo().equals("materialShop")){
            list = ar.findByAdvertisementPositionAndIsValid(position, 0, shopPageRequest);
        }else if(position.getPositionNo().equals("ladiesFabric")){
            list = ar.findByAdvertisementPositionAndIsValid(position, 0, fabricReportPageRequest);
        }else if(position.getPositionNo().equals("mensFabric")){
            list = ar.findByAdvertisementPositionAndIsValid(position, 0, fabricReportPageRequest);
        }else if(position.getPositionNo().equals("demandBanner")){
            list = ar.findByAdvertisementPositionAndIsValid(position, 0, bannerPageRequest);
        }else if(position.getPositionNo().equals("newDemands")){
            list = ar.findByAdvertisementPositionAndIsValid(position,0,pageRequest);
        }else if(position.getPositionNo().equals("demandShop")){
            list = ar.findByAdvertisementPositionAndIsValid(position,0,fabricReportPageRequest);
        }
        return list;
    }
}
