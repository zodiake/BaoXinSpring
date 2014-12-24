package com.baosight.scc.ec.utils;

import com.baosight.scc.ec.model.FabricCategory;
import com.baosight.scc.ec.model.FabricSource;

import java.util.Comparator;

/**
 * Created by Charles on 2014/8/21.
 */
public class FSourceListSort implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        FabricSource fabricSource=(FabricSource)o1;
        FabricSource fabricSource1=(FabricSource)o2;
        int result =(fabricSource.getSortNo()>fabricSource1.getSortNo()?1:(fabricSource.getSortNo()==fabricSource1.getSortNo()?0:-1));
        if (0 == result){
            result = fabricSource.getName().compareTo(fabricSource1.getName());
        }
        return result;
    }
}
