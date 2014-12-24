package com.baosight.scc.ec.service.jpa;

import com.baosight.scc.ec.model.EcUser;
import com.baosight.scc.ec.model.Fabric;
import com.baosight.scc.ec.model.FabricCategory;
import com.baosight.scc.ec.repository.FabricCategoryRepository;
import com.baosight.scc.ec.security.UserContext;
import com.baosight.scc.ec.service.FabricCategoryService;
import com.baosight.scc.ec.utils.FCategoryListSort;
import com.baosight.scc.ec.utils.GuidUtil;
import com.baosight.scc.ec.web.FabricCategoryRmi;
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
import java.util.*;

/**
 * Created by zodiake on 2014/5/13.
 */
@Service
@Transactional
public class FabricCategoryServiceImpl implements FabricCategoryService {

    @Autowired
    private FabricCategoryRepository repository;
    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(readOnly = true)
    public FabricCategory findById(String id) {
        return repository.findOne(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FabricCategory> findAllSecondCategory() {
//        return em.createNamedQuery("FabricCategory.findAllSecondCategory",FabricCategory.class).getResultList();
        return repository.findByParentCategoryIsNotNull();
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "fabric-firstCategory")
    public List<FabricCategory> findAllFirstCategory() {
//    	return em.createNamedQuery("FabricCategory.findAllFirstCategory",FabricCategory.class).getResultList();
        List<FabricCategory> categories = repository.findByIsValidAndParentCategoryIsNull(0);
        for (FabricCategory category : categories) {
            category.getSecondCategory().size();
        }
        return categories;
    }

    @Override
    public FabricCategory save(FabricCategory fabricCategory) {
        fabricCategory.setId(GuidUtil.newGuid());
        return repository.save(fabricCategory);
    }

    @Override
    public FabricCategory update(FabricCategory fabricCategory) {
        DateTime dt = new DateTime();
        FabricCategory f = repository.findOne(fabricCategory.getId());
        List<FabricCategory> secondCategoryList = f.getSecondCategory();
        if (secondCategoryList != null && fabricCategory.getIsValid() == 1) {
            for (int i = 0; i < secondCategoryList.size(); i++) {
                FabricCategory secondCategory = secondCategoryList.get(i);
                secondCategory.setUpdatedBy(fabricCategory.getUpdatedBy());
                secondCategory.setUpdatedTime(dt.toCalendar(Locale.SIMPLIFIED_CHINESE));
                secondCategory.setIsValid(1);
            }
            f.setSecondCategory(secondCategoryList);
            f.setIsValid(1);
        }
        f.setIsValid(fabricCategory.getIsValid());
        f.setName(fabricCategory.getName());
        f.setUpdatedTime(dt.toCalendar(Locale.SIMPLIFIED_CHINESE));
        f.setParentCategory(fabricCategory.getParentCategory());
        f.setSortNo(fabricCategory.getSortNo());
        return f;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FabricCategory> findAllFirstCategoryByPage(Pageable pageable) {
        return repository.findByParentCategoryIsNull(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FabricCategory> findAllSecondCategoryByPage(Pageable pageable) {
        return repository.findByParentCategoryIsNotNull(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FabricCategory> findFirstCategoryByIsValid(int isValid) {
        List<FabricCategory> list = repository.findByIsValidAndParentCategoryIsNull(isValid);
        Collections.sort(list,new FCategoryListSort());
        for(FabricCategory f: list){
            f.getSecondCategory().size();
        }
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public List<FabricCategory> findSecondCategoryByIsValid(int isValid) {
        return repository.findByIsValidAndParentCategoryIsNotNull(isValid);
    }

    @Override
    @Cacheable(value = "fabricParentFirstCategory", key = "#p0")
    public List<FabricCategory> findByParentCategoryAndIsValid(FabricCategory category, int isValid) {
        return repository.findByParentCategoryAndIsValid(category, 0);
    }

    @Override
    public List<FabricCategory> findByParentCategory(FabricCategory category) {
        return repository.findByParentCategory(category);
    }

    @Override
    @Cacheable(value = "fabricCategory-rmi")
    public List<FabricCategoryRmi> findAll() {
        TypedQuery<FabricCategory> query = em.createQuery("select f from FabricCategory f", FabricCategory.class);
        List<FabricCategory> categories = query.getResultList();
        List<FabricCategoryRmi> result = new ArrayList<FabricCategoryRmi>();
        for (FabricCategory f : categories) {
            f.getSecondCategory().size();
            FabricCategoryRmi rmi = new FabricCategoryRmi();
            rmi.setId(f.getId());
            rmi.setName(f.getName());
            List<FabricCategoryRmi> children = new ArrayList<FabricCategoryRmi>();
            for (FabricCategory child : f.getSecondCategory()) {
                FabricCategoryRmi c = new FabricCategoryRmi();
                c.setId(child.getId());
                c.setName(child.getName());
                children.add(c);
            }
            rmi.setChildren(children);
            result.add(rmi);
        }
        return result;
    }

    @Override
    public FabricCategory findByName(String name) {
        return repository.findByName(name);
    }
}
