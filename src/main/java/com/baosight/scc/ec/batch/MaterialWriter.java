package com.baosight.scc.ec.batch;

import com.baosight.scc.ec.model.Fabric;
import com.baosight.scc.ec.model.Material;
import com.baosight.scc.ec.model.MaterialIndex;
import com.baosight.scc.ec.repository.search.MaterialSearchRepository;
import com.baosight.scc.ec.service.MaterialIndexService;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2014/9/26.
 */
public class MaterialWriter extends JpaItemWriter<Material> {
    private MaterialSearchRepository repository;
    private MaterialIndexService service;

    public MaterialSearchRepository getRepository() {
        return repository;
    }

    public void setRepository(MaterialSearchRepository repository) {
        this.repository = repository;
    }

    public MaterialIndexService getService() {
        return service;
    }

    public void setService(MaterialIndexService service) {
        this.service = service;
    }

    @Override
    protected void doWrite(EntityManager entityManager, List<? extends Material> items) {
        super.doWrite(entityManager, items);
        final List<Material> r = (List<Material>)items;
        final List<MaterialIndex> results=new ArrayList<MaterialIndex>();
        for(Material f:r){
            results.add(service.transferFromMaterial(f));
        }
        TransactionSynchronizationManager.registerSynchronization(
                new TransactionSynchronizationAdapter() {
                    @Override
                    public void afterCommit() {
                        repository.save(results);
                    }
                }
        );
    }
}
