package com.baosight.scc.ec.service;

import com.baosight.scc.ec.model.EcPattern;
import com.baosight.scc.ec.web.EcPatternJSON;

import java.util.List;

/**
 * Created by zodiake on 2014/7/7.
 */
public interface EcPatternService {
    public List<EcPattern> findFirstCategory();

    public EcPattern findOne(String id);

    public List<EcPatternJSON> findAll();

    public EcPattern findByName(String name);
}
