package com.baosight.scc.ec.repository.search;

import com.baosight.scc.ec.model.Fabric;
import com.baosight.scc.ec.model.FabricIndex;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by Administrator on 2014/9/24.
 */
public interface FabricSearchRepository extends ElasticsearchCrudRepository<FabricIndex,String> {
}
