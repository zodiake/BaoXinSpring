package com.baosight.scc.ec.service;

import com.baosight.scc.ec.model.Designer;
import com.baosight.scc.ec.model.EcProvider;

import java.util.List;

/**
 * Created by sam on 2014/7/22.
 */

public interface EcProviderService {

    /*
    根据登录用户Id，获取企业用户全部信息
     */
    public EcProvider findOne(String id);

    /*
    根据用户id list查询多个用户名称
     */
    public String findMultipleUser(String userId,String userType);

    public List<String> getTopBrand();

    public List<Designer> getTopDesigner();

    public String getBrandCount();
}
