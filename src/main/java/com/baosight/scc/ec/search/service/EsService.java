package com.baosight.scc.ec.search.service;

import com.baosight.scc.ec.search.properties.CompositeQueryParam;
import com.baosight.scc.ec.search.properties.IndexMapping;
import com.baosight.scc.ec.search.properties.SearchParam;
import org.elasticsearch.index.query.QueryStringQueryBuilder;

import java.util.List;
import java.util.Map;


public interface EsService {

    public boolean createIndex(String indexName, int shards, int replicas);

    public boolean addMapping(String indexName, String typeName,
                              List<IndexMapping> mappings, String analyzer);

    public boolean insertList(String typeName, List<Map<String, Object>> indexDatas);

    public long count(String typeName, CompositeQueryParam param);

    long countOper(String typeName, CompositeQueryParam param, QueryStringQueryBuilder.Operator operator1);

    public List<Map<String, Object>> search(String typeName, CompositeQueryParam param);

    public boolean deleteByQuery(String typeName, List<SearchParam> params);

    List<Map<String, Object>> searchOper(String typeName,
                                         CompositeQueryParam param, QueryStringQueryBuilder.Operator operator1);

    public boolean deleteById(String typeName, String id);

    public boolean updateByQuery(String typeName,
                                 List<SearchParam> searchParams, Map<String, Object> indexData);

    public boolean updateById(String typeName,
                              String id, Map<String, Object> indexData);

    public List<Map<String, Object>> moreLikeThis(String type, String documentId, int size, String... fields);

    public boolean updateByIdBulk(String typeName, Map<String, Map<String, Object>> datas);

    public List<Map<String, Object>> matchAll(String type, CompositeQueryParam param);

}

