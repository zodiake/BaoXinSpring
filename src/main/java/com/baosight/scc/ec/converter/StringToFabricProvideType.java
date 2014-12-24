package com.baosight.scc.ec.converter;

import com.baosight.scc.ec.annotation.AutoRegister;
import com.baosight.scc.ec.service.FabricProvideTypeService;
import com.baosight.scc.ec.type.FabricProvideType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Created by zodiake on 2014/6/17.
 */
@Component
@AutoRegister
public class StringToFabricProvideType implements Converter<String, FabricProvideType> {
    @Autowired
    private FabricProvideTypeService service;

    @Override
    public FabricProvideType convert(String source) {
        return service.findOne(source);
    }
}
