package com.baosight.scc.ec.search.operator;

import com.baosight.scc.ec.search.properties.CompositeQueryParam;
import com.baosight.scc.ec.search.properties.IndexMapping;
import com.baosight.scc.ec.search.properties.SearchParam;
import org.elasticsearch.index.query.QueryStringQueryBuilder;

import java.util.List;
import java.util.Map;


public interface EsOperator {


    List<Map<String, Object>> searchOperator(String[] indexNames, String type, CompositeQueryParam param, QueryStringQueryBuilder.Operator operator);

    public List<Map<String, Object>> search(String[] indexNames, String type, CompositeQueryParam param);

    public List<Map<String, Object>> matchAll(String[] indexNames, String type, CompositeQueryParam param);

    public boolean addMapping(String indexName, String typeName,
                              List<IndexMapping> mappings, String analyzer);

    public boolean createIndex(String indexName, int shards, int replicas);

    public boolean deleteByQuery(String indexName, String types, List<SearchParam> params);

    public boolean deleteById(String indexName, String type, String id);


    public boolean bulkInsert(String indexName, String typeName,
                              List<Map<String, Object>> indexDatas);

    long countOper(String[] indexNames, String typeName, CompositeQueryParam param, QueryStringQueryBuilder.Operator operator1);

    public long count(String[] indexNames, String typeName, CompositeQueryParam param);


    public boolean updateByQuery(String indexName, String typeName, List<SearchParam> searchParams, Map<String, Object> indexData);

    public boolean updateById(String indexName, String typeName, String id, Map<String, Object> indexData);

    public boolean updateByIdBulk(String indexName, String typeName, Map<String, Map<String, Object>> datas);

    public List<Map<String, Object>> moreLikeThis(String indexName, String type, String documentId, int size, String... fields);
}

