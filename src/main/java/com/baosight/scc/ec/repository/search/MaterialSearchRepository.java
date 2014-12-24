package com.baosight.scc.ec.repository.search;

import com.baosight.scc.ec.model.MaterialIndex;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by Administrator on 2014/9/24.
 */
public interface MaterialSearchRepository extends ElasticsearchRepository<MaterialIndex,String>{
}
