package com.baosight.scc.ec.converter;

import com.baosight.scc.ec.annotation.AutoRegister;
import com.baosight.scc.ec.model.EcPattern;
import com.baosight.scc.ec.service.EcPatternService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Created by zodiake on 2014/7/8.
 */
@AutoRegister
@Component
public class StringToEcPattern implements Converter<String,EcPattern> {
    @Autowired
    private EcPatternService service;

    @Override
    public EcPattern convert(String source) {
        return service.findOne(source);
    }
}
