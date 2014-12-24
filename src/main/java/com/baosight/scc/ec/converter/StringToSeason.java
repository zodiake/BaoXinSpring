package com.baosight.scc.ec.converter;

import com.baosight.scc.ec.annotation.AutoRegister;
import com.baosight.scc.ec.model.Season;
import com.baosight.scc.ec.service.SeasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Created by zodiake on 2014/6/13.
 */
@AutoRegister
@Component
public class StringToSeason implements Converter<String,Season>{
    @Autowired
    private SeasonService seasonService;

    @Override
    public Season convert(String source) {
        return seasonService.findById(source);
    }
}
