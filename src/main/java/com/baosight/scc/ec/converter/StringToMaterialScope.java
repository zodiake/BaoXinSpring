package com.baosight.scc.ec.converter;

import com.baosight.scc.ec.annotation.AutoRegister;
import com.baosight.scc.ec.service.MaterialScopeService;
import com.baosight.scc.ec.type.MaterialScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Created by zodiake on 2014/6/16.
 */
@AutoRegister
@Component
public class StringToMaterialScope implements Converter<String,MaterialScope>{
    @Autowired
    private MaterialScopeService scopeService;
    @Override
    public MaterialScope convert(String source) {
        return scopeService.findOne(source);
    }
}
