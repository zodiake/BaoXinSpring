package com.baosight.scc.ec.utils;

import com.baosight.scc.ec.model.InsideLetter;

import java.util.Comparator;

/**
 * Created by sam on 2014/7/28.
 */
public class ListSort implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        InsideLetter insideLetter=(InsideLetter)o1;
        InsideLetter insideLetter1=(InsideLetter)o2;
        if (insideLetter.getCreatedTime().getTime().getTime()<insideLetter1.getCreatedTime().getTime().getTime()){
            return 1;
        }
        return 0;
    }
}
