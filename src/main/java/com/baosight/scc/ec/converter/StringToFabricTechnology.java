package com.baosight.scc.ec.converter;

import com.baosight.scc.ec.annotation.AutoRegister;
import com.baosight.scc.ec.service.FabricTechnologyTypeService;
import com.baosight.scc.ec.type.FabricTechnologyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Created by zodiake on 2014/7/4.
 */
@AutoRegister
@Component
public class StringToFabricTechnology implements Converter<String, FabricTechnologyType> {
    @Autowired
    private FabricTechnologyTypeService service;

    @Override
    public FabricTechnologyType convert(String source) {
        return service.findOne(source);
    }
}
