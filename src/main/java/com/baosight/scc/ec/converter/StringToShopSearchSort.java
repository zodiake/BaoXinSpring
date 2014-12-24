package com.baosight.scc.ec.converter;

import com.baosight.scc.ec.annotation.AutoRegister;
import com.baosight.scc.ec.type.ShopSearchSort;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Created by zodiake on 2014/7/11.
 */
@AutoRegister
@Component
public class StringToShopSearchSort implements Converter<String,ShopSearchSort>{
    @Override
    public ShopSearchSort convert(String source) {

        Integer i = Integer.parseInt(source);
        if (i == 0 )
            return ShopSearchSort.values()[i];
        else
            return null;
    }
}
