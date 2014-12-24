package com.baosight.scc.ec.repository;

import com.baosight.scc.ec.model.Season;

/**
 * Created by zodiake on 2014/6/13.
 */
public interface SeasonRepository {

    public Season findById(String id);

    public Season findByName(String name);
}
