package com.baosight.scc.ec.batch;

import com.baosight.scc.ec.model.Fabric;
import com.baosight.scc.ec.model.FabricIndex;
import com.baosight.scc.ec.repository.search.FabricSearchRepository;
import com.baosight.scc.ec.service.FabricIndexService;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2014/9/25.
 */
public class FabricWriter extends JpaItemWriter<Fabric> {
    private FabricSearchRepository fabricSearchRepository;

    private FabricIndexService fabricIndexService;

    public FabricSearchRepository getFabricSearchRepository() {
        return fabricSearchRepository;
    }

    public void setFabricSearchRepository(FabricSearchRepository fabricSearchRepository) {
        this.fabricSearchRepository = fabricSearchRepository;
    }

    public FabricIndexService getFabricIndexService() {
        return fabricIndexService;
    }

    public void setFabricIndexService(FabricIndexService fabricIndexService) {
        this.fabricIndexService = fabricIndexService;
    }

    @Override
    protected void doWrite(EntityManager entityManager, List<? extends Fabric> items) {
        super.doWrite(entityManager, items);
        final List<Fabric> r = (List<Fabric>)items;
        final List<FabricIndex> results=new ArrayList<FabricIndex>();
        for(Fabric f:r){
            results.add(fabricIndexService.transferFromFabric(f));
        }
        TransactionSynchronizationManager.registerSynchronization(
                new TransactionSynchronizationAdapter() {
                    @Override
                    public void afterCommit() {
                        fabricSearchRepository.save(results);
                    }
                }
        );
    }
}
