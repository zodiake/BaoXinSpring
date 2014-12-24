package com.baosight.scc.ec.service.jpa;

import com.baosight.scc.ec.model.*;
import com.baosight.scc.ec.repository.OrderLineRepository;
import com.baosight.scc.ec.repository.SampleLineRepository;
import com.baosight.scc.ec.service.ItemService;
import com.baosight.scc.ec.service.OrderLineService;
import com.baosight.scc.ec.service.SampleLineService;
import com.baosight.scc.ec.utils.GuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Charles on 2014/5/16.
 */
@Service
@Transactional
public class SampleLineServiceImpl implements SampleLineService{
    @Autowired
    private SampleLineRepository sr;
    

    @Override
    public SampleLine save(SampleLine sampleLine) {
        return sr.save(sampleLine);
    }

    @Override
    public Page<SampleLine> findByItem(SampleOrder sampleOrder,Pageable pageable) {
        return sr.findOrderByItem(sampleOrder,pageable);
    }

}
