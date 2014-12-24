package com.baosight.scc.ec.converter;

import com.baosight.scc.ec.annotation.AutoRegister;
import com.baosight.scc.ec.type.SearchSortType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Created by zodiake on 2014/7/2.
 */
@AutoRegister
@Component
public class StringToSearchSortType implements Converter<String, SearchSortType> {
    @Override
    public SearchSortType convert(String source) {
        Integer i = Integer.parseInt(source);
        if (i >= 0 && i <= 4)
            return SearchSortType.values()[i];
        else
            return null;
    }
}
