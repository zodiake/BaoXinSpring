package com.baosight.scc.ec.converter;

import com.baosight.scc.ec.annotation.AutoRegister;
import com.baosight.scc.ec.model.EcColor;
import com.baosight.scc.ec.rest.CodeAPI;
import com.baosight.scc.ec.service.EcColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zodiake on 2014/7/9.
 */
@AutoRegister
@Component
public class StringToEcColor implements Converter<String, EcColor> {
    @Autowired
    private EcColorService service;

    @Override
    public EcColor convert(String source) {
        return service.findOne(source);
    }
}
