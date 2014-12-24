package com.baosight.scc.ec.converter;

import com.baosight.scc.ec.annotation.AutoRegister;
import com.baosight.scc.ec.service.MaterialProvideTypeService;
import com.baosight.scc.ec.type.MaterialProvideType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Created by zodiake on 2014/6/16.
 */
@Component
@AutoRegister
public class StringToProvideType implements Converter<String,MaterialProvideType> {
    @Autowired
    private MaterialProvideTypeService service;
    @Override
    public MaterialProvideType convert(String source) {
        return service.findById(source);
    }
}
