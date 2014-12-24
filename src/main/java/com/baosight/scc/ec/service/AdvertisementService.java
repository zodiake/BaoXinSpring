package com.baosight.scc.ec.service;

import com.baosight.scc.ec.model.Advertisement;
import com.baosight.scc.ec.model.AdvertisementPosition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Charles on 2014/5/30.
 */

public interface AdvertisementService {
    /**
     * 保存广告
     * @param advertisement
     * @return
     */
    Advertisement save(Advertisement advertisement);
    /**
     * 更新广告
     * @param advertisement
     * @return
     */
    Advertisement update(Advertisement advertisement,String id);
    /**
     * 查找一条广告
     * @param id
     * @return
     */
    Advertisement findById(String id);

    /**
     * 查询有效或无效的广告
     * @param isValid
     * @param pageable
     * @return
     */
    Page<Advertisement> findByIsValid(int isValid, Pageable pageable);

    /**
     * 查找首页广告
     * @param isValid
     * @param pageable
     * @return
     */
//    @Query("select coverPath,link,position_id,id from T_ec_ad where isValid=0 group by position_id order by createdTime")
//    List<Advertisement> findHomeAd(int isValid, Pageable pageable);
    List<Advertisement> findByAdvertisementPositionAndIsValid(AdvertisementPosition advertisementPosition,int isValid, Pageable pageable);

    /**
     * 更新广告缓存
     * @param position
     * @return
     */
    List<Advertisement> updateAdContext(AdvertisementPosition position);
}
