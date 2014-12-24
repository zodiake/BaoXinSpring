package com.baosight.scc.ec.service;

import com.baosight.scc.ec.model.Color;

import java.util.Collection;

/**
 * Created by zodiake on 2014/6/13.
 */
public interface ColorService {
    Color findById(String id);
    Collection<Color> findAll();
}
