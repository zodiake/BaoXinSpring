package com.baosight.scc.ec.utils;

import com.baosight.scc.ec.model.FabricCategory;
import com.baosight.scc.ec.model.InsideLetter;

import java.util.Comparator;

/**
 * Created by Charles on 2014/8/21.
 */
public class FCategoryListSort implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        FabricCategory fabricCategory=(FabricCategory)o1;
        FabricCategory fabricCategory1=(FabricCategory)o2;
        int result =(fabricCategory.getSortNo()>fabricCategory1.getSortNo()?1:(fabricCategory.getSortNo()==fabricCategory1.getSortNo()?0:-1));
        if (0 == result){
            result = fabricCategory.getName().compareTo(fabricCategory1.getName());
        }
        return result;
    }
}
