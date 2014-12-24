package com.baosight.scc.ec.converter;

import com.baosight.scc.ec.annotation.AutoRegister;
import com.baosight.scc.ec.model.EcUser;
import com.baosight.scc.ec.service.EcUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Created by sam on 2014/6/27.
 */
@AutoRegister
@Component
public class StringToEcUser implements Converter<String,EcUser> {
    @Autowired
    private EcUserService ecUserService;
    @Override
    public EcUser convert(String source) {
        return ecUserService.findById(source);
    }
}
