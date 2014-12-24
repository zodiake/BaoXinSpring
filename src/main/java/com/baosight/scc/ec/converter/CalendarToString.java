package com.baosight.scc.ec.converter;

import com.baosight.scc.ec.annotation.AutoRegister;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by zodiake on 2014/6/16.
 */
@AutoRegister
@Component
public class CalendarToString implements Converter<Calendar,String>{
    @Override
    public String convert(Calendar source) {
        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = formatter.format(source.getTime());
        return currentDate;
    }
}
