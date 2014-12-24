package com.baosight.scc.ec.service;

import com.baosight.scc.ec.model.CommonUser;

/**
 * Created by sam on 2014/8/6.
 */
public interface CommonUserService {
    /*
    根据登录用户Id，获取一般用户全部信息
     */
    public CommonUser findOne(String id);

    public String findUserTypeById(String id);
}
