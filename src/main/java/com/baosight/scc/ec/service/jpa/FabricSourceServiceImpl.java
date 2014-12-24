package com.baosight.scc.ec.service.jpa;

import com.baosight.scc.ec.model.EcUser;
import com.baosight.scc.ec.model.FabricSource;
import com.baosight.scc.ec.repository.FabricSourceRepository;
import com.baosight.scc.ec.rest.StringUtil;
import com.baosight.scc.ec.security.UserContext;
import com.baosight.scc.ec.service.FabricSourceService;
import com.baosight.scc.ec.utils.FSourceListSort;
import com.baosight.scc.ec.utils.GuidUtil;
import com.baosight.scc.ec.web.FabricSourceJSON;
import com.baosight.scc.ec.web.FabricSourceRmi;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * Created by zodiake on 2014/5/22.
 */
@Service
@Transactional
public class FabricSourceServiceImpl implements FabricSourceService {
    @Autowired
    private FabricSourceRepository repository;

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(readOnly = true)
    public FabricSource findById(String id) {
        return repository.findOne(id);
    }

    @Override
    public FabricSource findFirstSourceByName(String name) {
        Query q = em.createNamedQuery("FabricSource.findFirstSourceByName");
        q.setParameter("name", name);
        FabricSource result = (FabricSource) q.getSingleResult();
        return result;
    }

    @Override
    public FabricSource findSecondSourceByName(String name) {
        Query q = em.createNamedQuery("FabricSource.findSecondSourceByName");
        if (!StringUtil.isEmpty(name)) {
            q.setParameter("name", name);
            FabricSource result = (FabricSource) q.getSingleResult();
            return result;
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "fabricSource-firstCategory")
    public List<FabricSource> findAllFirstCategory() {
        List<FabricSource> sources = repository.findByParentIsNull();
        for (FabricSource source : sources) {
            source.getDetailSources().size();
        }
        return sources;
    }

    @Override
    @Transactional(readOnly = true)
    public List<FabricSource> findAllSecondCategory() {
//        return em.createNamedQuery("FabricSource.findAllSecondSource",FabricSource.class).getResultList();
        return repository.findByParentIsNotNull();
    }

    @Override
    public FabricSource save(FabricSource fabricSource) {
        fabricSource.setId(GuidUtil.newGuid());
        return repository.save(fabricSource);
    }

    @Override
    public FabricSource update(FabricSource fabricSource) {
        DateTime dt = new DateTime();
        FabricSource f = repository.findOne(fabricSource.getId());
        List<FabricSource> secondCategoryList = f.getDetailSources();
        if (secondCategoryList != null && fabricSource.getIsValid() == 1) {
            for (int i = 0; i < secondCategoryList.size(); i++) {
                FabricSource secondCategory = secondCategoryList.get(i);
                secondCategory.setUpdatedTime(dt.toCalendar(Locale.SIMPLIFIED_CHINESE));
                secondCategory.setIsValid(1);
            }
            f.setDetailSources(secondCategoryList);
            f.setIsValid(1);
        }
        f.setIsValid(fabricSource.getIsValid());
        f.setName(fabricSource.getName());
        f.setUpdatedTime(dt.toCalendar(Locale.SIMPLIFIED_CHINESE));
        f.setDetailSources(fabricSource.getDetailSources());
        f.setSortNo(fabricSource.getSortNo());
        return f;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FabricSource> findAllFirstCategoryByPage(Pageable pageable) {
        return repository.findByParentIsNull(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FabricSource> findAllSecondCategoryByPage(Pageable pageable) {
        return repository.findByParentIsNotNull(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FabricSource> findFirstCategoryByIsValid(int isValid) {
        List<FabricSource> list = repository.findByIsValidAndParentIsNull(isValid);
        Collections.sort(list, new FSourceListSort());
        for (FabricSource f : list) {
            if (f.getDetailSources() != null) {
                f.getDetailSources().size();
            }
        }
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public List<FabricSource> findSecondCategoryByIsValid(int isValid) {
        return repository.findByIsValidAndParentIsNotNull(isValid);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FabricSource> findByParent(FabricSource fabricSource) {
        return repository.findByParent(fabricSource);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FabricSource> findByParentAndIsValid(FabricSource fabricSource, int isValid) {
        return repository.findByParentAndIsValid(fabricSource, isValid);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FabricSourceJSON> findByParentAndIsValidJSON(FabricSource fabricSource, int isValid) {
        List<FabricSourceJSON> result = new ArrayList<FabricSourceJSON>();
        List<FabricSource> list = repository.findByParentAndIsValid(fabricSource, isValid);
        for (FabricSource s : list) {
            result.add(new FabricSourceJSON(s));
        }
        return result;
    }

    @Override
    public List<FabricSourceRmi> findAll() {
        TypedQuery<FabricSource> query = em.createQuery("select f from FabricSource f", FabricSource.class);
        List<FabricSource> categories = query.getResultList();
        List<FabricSourceRmi> result = new ArrayList<FabricSourceRmi>();
        for (FabricSource f : categories) {
            f.getDetailSources().size();
            FabricSourceRmi rmi = new FabricSourceRmi();
            rmi.setId(f.getId());
            rmi.setName(f.getName());
            List<FabricSourceRmi> children = new ArrayList<FabricSourceRmi>();
            for (FabricSource child : f.getDetailSources()) {
                FabricSourceRmi c = new FabricSourceRmi();
                c.setId(child.getId());
                c.setName(child.getName());
                children.add(c);
            }
            rmi.setChildren(children);
            result.add(rmi);
        }
        return result;
    }
}
