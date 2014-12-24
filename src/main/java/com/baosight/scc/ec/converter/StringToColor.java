package com.baosight.scc.ec.converter;

import com.baosight.scc.ec.annotation.AutoRegister;
import com.baosight.scc.ec.model.Color;
import com.baosight.scc.ec.model.EcColor;
import com.baosight.scc.ec.service.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Created by zodiake on 2014/6/13.
 */
@AutoRegister
@Component
public class StringToColor implements Converter<String, Color> {
    @Autowired
    private ColorService service;

    @Override
    public Color convert(String source) {
        return null;
    }
}
