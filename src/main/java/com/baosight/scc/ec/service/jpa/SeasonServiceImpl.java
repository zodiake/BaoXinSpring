package com.baosight.scc.ec.service.jpa;

import com.baosight.scc.ec.model.Season;
import com.baosight.scc.ec.repository.SeasonRepository;
import com.baosight.scc.ec.rest.CodeAPI;
import com.baosight.scc.ec.service.SeasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by zodiake on 2014/6/13.
 */
@Service
public class SeasonServiceImpl implements SeasonService {
    @Autowired
    private SeasonRepository repository;

    @Override
    @Cacheable(value = "season")
    public List<Season> findAll() {
        CodeAPI api = new CodeAPI();
        List<Map<String, Object>> lists = api.getBusinessCodeTree("season", "", 1);
        List<Season> seasons = new LinkedList<Season>();
        for (Map<String, Object> map : lists) {
            if (map != null)
                seasons.add(new Season(map));
        }
        return seasons;
    }

    @Override
    public Season findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Season findByName(String name) {
        return repository.findByName(name);
    }
}
