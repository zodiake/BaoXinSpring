package com.baosight.scc.ec.utils;

import com.baosight.scc.ec.model.FabricCategory;
import com.baosight.scc.ec.model.MaterialCategory;

import java.util.Comparator;

/**
 * Created by Charles on 2014/8/21.
 */
public class MCategoryListSort implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        MaterialCategory materialCategory=(MaterialCategory)o1;
        MaterialCategory materialCategory1=(MaterialCategory)o2;
        int result =(materialCategory.getSortNo()>materialCategory1.getSortNo()?1:(materialCategory.getSortNo()==materialCategory1.getSortNo()?0:-1));
        if (0 == result){
            result = materialCategory.getName().compareTo(materialCategory1.getName());
        }
        return result;
    }
}
