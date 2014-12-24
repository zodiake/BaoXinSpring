package com.baosight.scc.ec.converter;

import com.baosight.scc.ec.annotation.AutoRegister;
import com.baosight.scc.ec.rest.StringUtil;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by zodiake on 2014/6/16.
 */
@AutoRegister
@Component
public class StringToCalendar implements Converter<String, Calendar> {
    @Override
    public Calendar convert(String source) {
        if(StringUtil.isEmpty(source))
            return null;
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if (!StringUtil.isEmpty(source)) {
                cal.setTime(sdf.parse(source));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return cal;
    }
}
