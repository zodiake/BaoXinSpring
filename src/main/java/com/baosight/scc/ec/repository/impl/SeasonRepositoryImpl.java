package com.baosight.scc.ec.repository.impl;

import com.baosight.scc.ec.model.Season;
import com.baosight.scc.ec.repository.SeasonRepository;
import com.baosight.scc.ec.rest.CodeAPI;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by zodiake on 2014/6/13.
 */
@Component
public class SeasonRepositoryImpl implements SeasonRepository{
    @Override
    public Season findById(String id) {
        CodeAPI api=new CodeAPI();
        Map<String,Object> map=api.getBusinessCodeByValue("season",id);
        return new Season(map);
    }

    @Override
    public Season findByName(String name) {
        CodeAPI api=new CodeAPI();
        Map<String,Object> map=api.getBusinessCodeByName("season",name);
        return new Season(map);
    }
}
