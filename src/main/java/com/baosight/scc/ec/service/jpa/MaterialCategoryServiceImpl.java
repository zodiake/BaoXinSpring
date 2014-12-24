package com.baosight.scc.ec.service.jpa;

import com.baosight.scc.ec.model.EcUser;
import com.baosight.scc.ec.model.MaterialCategory;
import com.baosight.scc.ec.repository.MaterialCategoryRepository;
import com.baosight.scc.ec.security.UserContext;
import com.baosight.scc.ec.service.MaterialCategoryService;
import com.baosight.scc.ec.utils.GuidUtil;
import com.baosight.scc.ec.utils.MCategoryListSort;
import com.baosight.scc.ec.web.MaterialCategoryJSON;
import com.baosight.scc.ec.web.MaterialCategoryRmi;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

@Service
@Transactional
public class MaterialCategoryServiceImpl implements MaterialCategoryService {

    @Autowired
    private MaterialCategoryRepository mr;
    @PersistenceContext
    private EntityManager em;

    /**
     * @param category firstCategory
     * @return
     */
    @Transactional(readOnly = true)
    @Cacheable(value = "material-secondCategory", key = "#p0")
    public List<MaterialCategoryJSON> findByParentCategory(MaterialCategory category) {
        TypedQuery<MaterialCategory> query = em.createNamedQuery("MaterialCategory.findByParentCategory", MaterialCategory.class);
        query.setParameter("category", category);
        List<MaterialCategory> categories = query.getResultList();
        List<MaterialCategoryJSON> results = new ArrayList<MaterialCategoryJSON>();
        for (MaterialCategory mc : categories) {
            results.add(new MaterialCategoryJSON(mc));
        }
        return results;
    }

    public List<MaterialCategory> findByParentCategorySource(MaterialCategory category) {
        TypedQuery<MaterialCategory> query = em.createNamedQuery("MaterialCategory.findByParentCategory", MaterialCategory.class);
        query.setParameter("category", category);
        return query.getResultList();
    }

    @Override
    @Cacheable(value = "materialCategory-rmi")
    public List<MaterialCategoryRmi> findAll() {
        TypedQuery<MaterialCategory> query = em.createQuery("select f from MaterialCategory f", MaterialCategory.class);
        List<MaterialCategory> categories = query.getResultList();
        List<MaterialCategoryRmi> result = new ArrayList<MaterialCategoryRmi>();
        for (MaterialCategory f : categories) {
            f.getSecondCategory().size();
            MaterialCategoryRmi rmi = new MaterialCategoryRmi();
            rmi.setId(f.getId());
            rmi.setName(f.getName());
            List<MaterialCategoryRmi> children = new ArrayList<MaterialCategoryRmi>();
            for (MaterialCategory child : f.getSecondCategory()) {
                MaterialCategoryRmi c = new MaterialCategoryRmi();
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
    public MaterialCategory findByName(String name) {
        return mr.findByName(name);
    }

//    @Override
//    @Transactional(readOnly = true)
//    public List<MaterialCategory> findAllSecondCategory() {
//        return em.createNamedQuery("MaterialCategory.findAllSecondCategories", MaterialCategory.class).getResultList();
//    }

    @Override
    @Transactional(readOnly = true)
    public MaterialCategory findOne(String id) {
        return mr.findOne(id);
    }

    /**
     * 获取辅料一级分类
     *
     * @return
     */
    @Transactional(readOnly = true)
    @Cacheable(value = "materialCategory")
    public List<MaterialCategory> getMaterialFirstCategorys() {

        return em.createNamedQuery("MaterialCategory.findAllFirstCategorys", MaterialCategory.class).getResultList();
    }

    /**
     * *********************************************************************************************************************************
     */
    public MaterialCategory findById(String id) {
        return mr.findOne(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MaterialCategory> findAllSecondCategory() {
        return mr.findByParentCategoryIsNotNull();
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "materialFirstCategory")
    public List<MaterialCategory> findAllFirstCategory() {
        return mr.findByIsValidAndParentCategoryIsNull(0);
    }

    @Override
    public MaterialCategory save(MaterialCategory materialCategory) {
        materialCategory.setId(GuidUtil.newGuid());
        return mr.save(materialCategory);
    }

    @Override
    public MaterialCategory update(MaterialCategory materialCategory) {
        DateTime dt = new DateTime();
        MaterialCategory m = mr.findOne(materialCategory.getId());
        List<MaterialCategory> secondCategoryList = m.getSecondCategory();
        if (secondCategoryList != null && materialCategory.getIsValid() == 1) {
            for (int i = 0; i < secondCategoryList.size(); i++) {
                MaterialCategory secondCategory = secondCategoryList.get(i);
                secondCategory.setUpdatedBy(materialCategory.getUpdatedBy());
                secondCategory.setUpdatedTime(dt.toCalendar(Locale.SIMPLIFIED_CHINESE));
                secondCategory.setIsValid(1);
            }
            m.setSecondCategory(secondCategoryList);
            m.setIsValid(1);
        }
        m.setName(materialCategory.getName());
        m.setIsValid(materialCategory.getIsValid());
        m.setUpdatedTime(dt.toCalendar(Locale.SIMPLIFIED_CHINESE));
        m.setParentCategory(materialCategory.getParentCategory());
        m.setSortNo(materialCategory.getSortNo());
        return m;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MaterialCategory> findAllFirstCategoryByPage(Pageable pageable) {
        return mr.findByParentCategoryIsNull(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MaterialCategory> findAllSecondCategoryByPage(Pageable pageable) {
        return mr.findByParentCategoryIsNotNull(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable("material-firstCategory")
    public List<MaterialCategory> findFirstCategoryByIsValid(int isValid) {
        List<MaterialCategory> categories = mr.findByIsValidAndParentCategoryIsNull(isValid);
        Collections.sort(categories, new MCategoryListSort());
        for (MaterialCategory c : categories) {
            c.getSecondCategory().size();
        }
        return categories;
    }

    @Override
    @Transactional(readOnly = true)
    public List<MaterialCategory> findSecondCategoryByIsValid(int isValid) {
        return mr.findByIsValidAndParentCategoryIsNotNull(isValid);
    }

    @Override
    public List<MaterialCategory> findSecondCategoryByParentCategory(MaterialCategory materialCategory) {
        TypedQuery<MaterialCategory> query = em.createNamedQuery("MaterialCategory.findByParentCategory", MaterialCategory.class);
        query.setParameter("category", materialCategory);
        return query.getResultList();
    }

    @Override
    public List<MaterialCategory> findSecondCategoryByParentCategoryAndIsValid(MaterialCategory materialCategory, int isValid) {
        return mr.findByParentCategoryAndIsValid(materialCategory, isValid);
    }
}
