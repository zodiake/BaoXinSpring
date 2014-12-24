package com.baosight.scc.ec.service;

import com.baosight.scc.ec.model.Season;

import java.util.List;

/**
 * Created by zodiake on 2014/6/13.
 */
public interface SeasonService {
    public List<Season> findAll();
    public Season findById(String id);
    public Season findByName(String name);
}
