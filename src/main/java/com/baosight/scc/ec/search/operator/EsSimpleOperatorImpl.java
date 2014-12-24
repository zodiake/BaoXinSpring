package com.baosight.scc.ec.search.operator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequestBuilder;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.mlt.MoreLikeThisRequestBuilder;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequestBuilder;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;

import com.baosight.scc.ec.search.client.ElasticClientFactory;
import com.baosight.scc.ec.search.properties.CompositeQueryParam;
import com.baosight.scc.ec.search.properties.ElasticClientProperties;
import com.baosight.scc.ec.search.properties.IndexMapping;
import com.baosight.scc.ec.search.properties.RangeParam;
import com.baosight.scc.ec.search.properties.SearchParam;

public class EsSimpleOperatorImpl implements EsOperator {

    private ElasticClientFactory elasticClientFactory;
    private ElasticClientProperties elasticClientProperties;


    public ElasticClientProperties getElasticClientProperties() {
        return elasticClientProperties;
    }


    public void setElasticClientProperties(
            ElasticClientProperties elasticClientProperties) {
        this.elasticClientProperties = elasticClientProperties;
    }

    public ElasticClientFactory getElasticClientFactory() {
        return elasticClientFactory;
    }

    public void setElasticClientFactory(
            ElasticClientFactory elasticClientFactory) {
        this.elasticClientFactory = elasticClientFactory;
    }


    /* 获得搜索结果 */
    private List<Map<String, Object>> getSearchResult(
            SearchResponse searchResponse) {
        try {
            List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
            for (SearchHit searchHit : searchResponse.getHits()) {
                Iterator<Entry<String, Object>> iterator = searchHit
                        .getSource().entrySet().iterator();
                HashMap<String, Object> resultMap = new HashMap<String, Object>();
                while (iterator.hasNext()) {
                    Entry<String, Object> entry = iterator.next();
                    resultMap.put(entry.getKey(), entry.getValue());
                }
                resultMap.put("_id", searchHit.getId());
                Map<String, HighlightField> highlightMap = searchHit
                        .highlightFields();
                Iterator<Entry<String, HighlightField>> highlightIterator = highlightMap
                        .entrySet().iterator();
                while (highlightIterator.hasNext()) {
                    Entry<String, HighlightField> entry = highlightIterator
                            .next();
                    Object[] contents = entry.getValue().fragments();
                    if (contents.length == 1) {
                        resultMap.put(entry.getKey(), contents[0].toString());
                    } else {
                        System.err.println("搜索结果中的高亮结果出现多数据contents.length = "
                                + contents.length);
                    }
                }
                resultList.add(resultMap);
            }
            return resultList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    private QueryBuilder createRangeQueryBuilder(List<RangeParam> params) {
        if (params == null || params.size() == 0) {
            return null;
        }
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        for (RangeParam param : params) {
            RangeQueryBuilder rangeQueryBuilder = QueryBuilders
                    .rangeQuery(param.getFieldName()).from(param.getStart())
                    .to(param.getEnd());
            boolQueryBuilder.must(rangeQueryBuilder);
        }
        return boolQueryBuilder;
    }

    private FilterBuilder createRangeFilterBuilder(List<RangeParam> params) {
        if (params == null || params.size() == 0) {
            return null;
        }
        BoolFilterBuilder boolFilterBuilder = FilterBuilders.boolFilter();
        for (RangeParam param : params) {
            RangeFilterBuilder rangeFilterBuilder = FilterBuilders
                    .rangeFilter(param.getFieldName()).from(param.getStart())
                    .to(param.getEnd());
            boolFilterBuilder.must(rangeFilterBuilder);
        }
        return boolFilterBuilder;
    }

    private QueryBuilder createSingleFieldTermQueryBuilder(
            SearchParam searchParam) {
        try {
            Object valueItem = searchParam.getValue();
            QueryBuilder queryBuilder = null;
            if (searchParam.getBoost() >= 0) {
                queryBuilder = QueryBuilders.termQuery(
                        searchParam.getFieldName(), valueItem).boost(
                        searchParam.getBoost());
            } else {
                queryBuilder = QueryBuilders.termQuery(
                        searchParam.getFieldName(), valueItem);
            }
            return queryBuilder;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private QueryBuilder createSingleFieldStringQueryBuilder(SearchParam searchParam,QueryStringQueryBuilder.Operator operator) {
        try {

            Object valueItem = searchParam.getValue();
            QueryBuilder queryBuilder = null;
            if (searchParam.getBoost() >= 0) {
                queryBuilder = QueryBuilders.queryString((String) valueItem)
                        .field(searchParam.getFieldName())
                        .boost(searchParam.getBoost());
            } else {
                queryBuilder = QueryBuilders.queryString((String) valueItem)
                        .field(searchParam.getFieldName()).defaultOperator(QueryStringQueryBuilder.Operator.AND);
            }
            return queryBuilder;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    private QueryBuilder createSingleFieldStringQueryBuilder(SearchParam searchParam) {
        try {

            Object valueItem = searchParam.getValue();
            QueryBuilder queryBuilder = null;
            if (searchParam.getBoost() >= 0) {
                queryBuilder = QueryBuilders.queryString((String) valueItem)
                        .field(searchParam.getFieldName())
                        .boost(searchParam.getBoost());
            } else {
                queryBuilder = QueryBuilders.queryString((String) valueItem)
                        .field(searchParam.getFieldName());
            }
            return queryBuilder;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private FilterBuilder createSingleFieldFilterBuilder(
            SearchParam searchParam) {
        try {
            Object valueItem = searchParam.getValue();
            FilterBuilder filterBuilder = FilterBuilders.termFilter(searchParam.getFieldName(), valueItem);
            return filterBuilder;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /* 简单的值校验 */
    private boolean checkValue(Object... values) {
        if (values == null) {
            return false;
        } else if (values.length == 0) {
            return false;
        } else if (values[0] == null) {
            return false;
        } else if (values[0].toString().trim().isEmpty()) {
            return false;
        }
        return true;
    }

    /*
     * 创建搜索条件
     */
    private QueryBuilder createMustStringQueryBuilderOper(List<SearchParam> params,QueryStringQueryBuilder.Operator operator) {
        try {
            if (params == null || params.size() == 0) {
                return null;
            }
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

            for (SearchParam param : params) {
                QueryBuilder queryBuilder = this
                        .createSingleFieldStringQueryBuilder(param,operator);
                if (queryBuilder != null) {
                    boolQueryBuilder = boolQueryBuilder.should(queryBuilder);
                }
            }

            return boolQueryBuilder;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    private QueryBuilder createMustStringQueryBuilder(List<SearchParam> params) {
        try {
            if (params == null || params.size() == 0) {
                return null;
            }
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

            for (SearchParam param : params) {
                QueryBuilder queryBuilder = this
                        .createSingleFieldStringQueryBuilder(param);
                if (queryBuilder != null) {
                    boolQueryBuilder = boolQueryBuilder.should(queryBuilder);
                }
            }

            return boolQueryBuilder;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     * 创建搜索条件
     */
    private QueryBuilder createMustTermQueryBuilder(List<SearchParam> params) {
        try {
            if (params == null || params.size() == 0) {
                return null;
            }
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

            for (SearchParam param : params) {
                QueryBuilder queryBuilder = this
                        .createSingleFieldTermQueryBuilder(param);
                if (queryBuilder != null) {
                    boolQueryBuilder = boolQueryBuilder.must(queryBuilder);
                }
            }

            return boolQueryBuilder;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private QueryBuilder createShouldTermQueryBuilder(List<SearchParam> params) {
        try {
            if (params == null || params.size() == 0) {
                return null;
            }
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

            for (SearchParam param : params) {
                QueryBuilder queryBuilder = this
                        .createSingleFieldTermQueryBuilder(param);
                if (queryBuilder != null) {
                    boolQueryBuilder = boolQueryBuilder.should(queryBuilder);
                }
            }

            return boolQueryBuilder;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private FilterBuilder createMustFilterBuilder(List<SearchParam> params) {
        try {
            if (params == null || params.size() == 0) {
                return null;
            }
            BoolFilterBuilder boolFilterBuilder = FilterBuilders.boolFilter().cache(true);

            for (SearchParam param : params) {
                FilterBuilder filterBuilder = this
                        .createSingleFieldFilterBuilder(param);
                if (filterBuilder != null) {
                    boolFilterBuilder = boolFilterBuilder.must(filterBuilder);
                }
            }

            return boolFilterBuilder;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private FilterBuilder createShouldFilterBuilder(List<SearchParam> params) {
        try {
            if (params == null || params.size() == 0) {
                return null;
            }
            BoolFilterBuilder boolFilterBuilder = FilterBuilders.boolFilter().cache(true);

            for (SearchParam param : params) {
                FilterBuilder filterBuilder = this
                        .createSingleFieldFilterBuilder(param);
                if (filterBuilder != null) {
                    boolFilterBuilder = boolFilterBuilder.should(filterBuilder);
                }
            }

            return boolFilterBuilder;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private SearchRequestBuilder createHighlight(
            SearchRequestBuilder searchRequestBuilder,
            List<SearchParam> params) {
        Map<String, String> paramNames = new HashMap<String, String>();

        for (SearchParam param : params) {
            String field = param.getFieldName();
            Object value = param.getValue();
            /* 排除非法的搜索值 */
            if (!this.checkValue(value)) {
                continue;
            }
            if (param.isHighlight() && !paramNames.containsKey(param.getFieldName())) {
				/*
				 * http://www.elasticsearch.org/guide/reference/api/search/
				 * highlighting.html
				 *
				 * fragment_size设置成1000，默认值会造成返回的数据被截断 (PS:引用自网上，有待考察）
				 */
                String tagPrefix = elasticClientProperties
                        .getHighlightTagPrefix();
                String tagSuffix = elasticClientProperties
                        .getHighlightTagSuffix();

                searchRequestBuilder = searchRequestBuilder
                        .addHighlightedField(field, 1000)
                        .setHighlighterPreTags("<" + tagPrefix + ">")
                        .setHighlighterPostTags("</" + tagSuffix + ">");

                paramNames.put(param.getFieldName(), "true");
            }
        }

        return searchRequestBuilder;
    }

    @Override
    public long countOper(String[] indexNames, String typeName, CompositeQueryParam param,QueryStringQueryBuilder.Operator operator1) {
        Client searchClient = this.elasticClientFactory
                .getOrCreateTransportClient();

        List<SearchParam> queryMustParams = param.getQueryMustParams();
        List<SearchParam> queryShouldParams = param.getQueryShouldParams();
        List<RangeParam> queryRangeParams = param.getQueryRangeParams();
        List<SearchParam> filterMustParams = param.getFilterMustParams();
        List<SearchParam> filterShouldParams = param.getFilterShouldParams();
        List<SearchParam> queryString = param.getQueryStringParams();

		/* 创建must搜索条件 */
        QueryBuilder mustQueryBuilder = this.createMustTermQueryBuilder(queryMustParams);
		/* 创建should搜索条件 */
        QueryBuilder shouldQueryBuilder = this
                .createShouldTermQueryBuilder(queryShouldParams);
		/* 创建range搜索条件 */
        QueryBuilder rangeQueryBuilder = this
                .createRangeQueryBuilder(queryRangeParams);
		/* 创建must过滤条件 */
        FilterBuilder mustFilterBuilder = this.createMustFilterBuilder(filterMustParams);
		/* 创建should过滤条件 */
        FilterBuilder shouldFilterBuilder = this
                .createShouldFilterBuilder(filterShouldParams);
		/* 创建String查询条件 */
        QueryBuilder mustStringBuilder = this.
                createMustStringQueryBuilderOper(queryString,operator1);

        if (mustQueryBuilder == null && shouldQueryBuilder == null
                && rangeQueryBuilder == null
                && mustFilterBuilder == null
                && shouldFilterBuilder == null
                && mustStringBuilder == null) {
            return 0;
        }

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        QueryBuilder queryBuilder = null;


        if (mustStringBuilder != null) {
            boolQueryBuilder = boolQueryBuilder.must(mustStringBuilder);
        }

        if (mustQueryBuilder != null) {
            boolQueryBuilder = boolQueryBuilder.must(mustQueryBuilder);
        }
        if (shouldQueryBuilder != null) {
            boolQueryBuilder = boolQueryBuilder.must(shouldQueryBuilder);
        }
        if (rangeQueryBuilder != null) {
            boolQueryBuilder = boolQueryBuilder.must(rangeQueryBuilder);
        }
        if (mustStringBuilder != null) {
            boolQueryBuilder = boolQueryBuilder.must(mustStringBuilder);
        }

        if (mustFilterBuilder != null || shouldFilterBuilder != null) {
            BoolFilterBuilder boolFilterBuilder = FilterBuilders.boolFilter();
            if (mustFilterBuilder != null) {
                boolFilterBuilder = boolFilterBuilder.must(mustFilterBuilder);
            }
            if (shouldFilterBuilder != null) {
                boolFilterBuilder = boolFilterBuilder.must(shouldFilterBuilder);
            }
            if (boolQueryBuilder.hasClauses()) {
                queryBuilder = QueryBuilders.filteredQuery(boolQueryBuilder, boolFilterBuilder);
            } else {
                queryBuilder = QueryBuilders.filteredQuery(QueryBuilders.matchAllQuery(), boolFilterBuilder);
            }
        } else {
            queryBuilder = boolQueryBuilder;
        }

        try {
            SearchRequestBuilder searchRequestBuilder = null;
            searchRequestBuilder = searchClient.prepareSearch(indexNames).setTypes(typeName)
                    .setSearchType(SearchType.COUNT)
                    .setQuery(queryBuilder).setExplain(true);

            SearchResponse searchResponse = searchRequestBuilder.execute()
                    .actionGet();
            return searchResponse.getHits().getTotalHits();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        return 0;
    }

    @Override
    public long count(String[] indexNames, String typeName, CompositeQueryParam param) {
        Client searchClient = this.elasticClientFactory
                .getOrCreateTransportClient();

        List<SearchParam> queryMustParams = param.getQueryMustParams();
        List<SearchParam> queryShouldParams = param.getQueryShouldParams();
        List<RangeParam> queryRangeParams = param.getQueryRangeParams();
        List<SearchParam> filterMustParams = param.getFilterMustParams();
        List<SearchParam> filterShouldParams = param.getFilterShouldParams();
        List<SearchParam> queryString = param.getQueryStringParams();

		/* 创建must搜索条件 */
        QueryBuilder mustQueryBuilder = this.createMustTermQueryBuilder(queryMustParams);
		/* 创建should搜索条件 */
        QueryBuilder shouldQueryBuilder = this
                .createShouldTermQueryBuilder(queryShouldParams);
		/* 创建range搜索条件 */
        QueryBuilder rangeQueryBuilder = this
                .createRangeQueryBuilder(queryRangeParams);
		/* 创建must过滤条件 */
        FilterBuilder mustFilterBuilder = this.createMustFilterBuilder(filterMustParams);
		/* 创建should过滤条件 */
        FilterBuilder shouldFilterBuilder = this
                .createShouldFilterBuilder(filterShouldParams);
		/* 创建String查询条件 */
        QueryBuilder mustStringBuilder = this.
                createMustStringQueryBuilder(queryString);

        if (mustQueryBuilder == null && shouldQueryBuilder == null
                && rangeQueryBuilder == null
                && mustFilterBuilder == null
                && shouldFilterBuilder == null
                && mustStringBuilder == null) {
            return 0;
        }

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        QueryBuilder queryBuilder = null;


        if (mustStringBuilder != null) {
            boolQueryBuilder = boolQueryBuilder.must(mustStringBuilder);
        }

        if (mustQueryBuilder != null) {
            boolQueryBuilder = boolQueryBuilder.must(mustQueryBuilder);
        }
        if (shouldQueryBuilder != null) {
            boolQueryBuilder = boolQueryBuilder.must(shouldQueryBuilder);
        }
        if (rangeQueryBuilder != null) {
            boolQueryBuilder = boolQueryBuilder.must(rangeQueryBuilder);
        }
        if (mustStringBuilder != null) {
            boolQueryBuilder = boolQueryBuilder.must(mustStringBuilder);
        }

        if (mustFilterBuilder != null || shouldFilterBuilder != null) {
            BoolFilterBuilder boolFilterBuilder = FilterBuilders.boolFilter();
            if (mustFilterBuilder != null) {
                boolFilterBuilder = boolFilterBuilder.must(mustFilterBuilder);
            }
            if (shouldFilterBuilder != null) {
                boolFilterBuilder = boolFilterBuilder.must(shouldFilterBuilder);
            }
            if (boolQueryBuilder.hasClauses()) {
                queryBuilder = QueryBuilders.filteredQuery(boolQueryBuilder, boolFilterBuilder);
            } else {
                queryBuilder = QueryBuilders.filteredQuery(QueryBuilders.matchAllQuery(), boolFilterBuilder);
            }
        } else {
            queryBuilder = boolQueryBuilder;
        }

        try {
            SearchRequestBuilder searchRequestBuilder = null;
            searchRequestBuilder = searchClient.prepareSearch(indexNames).setTypes(typeName)
                    .setSearchType(SearchType.COUNT)
                    .setQuery(queryBuilder).setExplain(true);

            SearchResponse searchResponse = searchRequestBuilder.execute()
                    .actionGet();
            return searchResponse.getHits().getTotalHits();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        return 0;
    }

    @Override
    public List<Map<String, Object>> searchOperator(String[] indexNames, String type, CompositeQueryParam param,QueryStringQueryBuilder.Operator operator) {
        List<SearchParam> queryMustParams = param.getQueryMustParams();
        List<SearchParam> queryShouldParams = param.getQueryShouldParams();
        List<RangeParam> queryRangeParams = param.getQueryRangeParams();
        List<SearchParam> filterMustParams = param.getFilterMustParams();
        List<SearchParam> filterShouldParams = param.getFilterShouldParams();
        List<SearchParam> queryString = param.getQueryStringParams();
        int offset = param.getOffset();
        int limit = param.getLimit();
        String sortField = param.getSortField();
        SortOrder sortOrder = param.getSortOrder();


        if (limit <= 0) {
            return null;
        }
        Client searchClient = this.elasticClientFactory
                .getOrCreateTransportClient();
		/* 创建must搜索条件 */
        QueryBuilder mustQueryBuilder = this.createMustTermQueryBuilder(queryMustParams);
		/* 创建should搜索条件 */
        QueryBuilder shouldQueryBuilder = this
                .createShouldTermQueryBuilder(queryShouldParams);
		/* 创建range搜索条件 */
        QueryBuilder rangeQueryBuilder = this
                .createRangeQueryBuilder(queryRangeParams);
		/* 创建String查询条件 */
        QueryBuilder mustStringBuilder = this.
                createMustStringQueryBuilderOper(queryString,operator);

		/* 创建must过滤条件 */
        FilterBuilder mustFilterBuilder = this.createMustFilterBuilder(filterMustParams);
		/* 创建should过滤条件 */
        FilterBuilder shouldFilterBuilder = this
                .createShouldFilterBuilder(filterShouldParams);

        if (mustQueryBuilder == null && shouldQueryBuilder == null
                && rangeQueryBuilder == null
                && mustFilterBuilder == null
                && shouldFilterBuilder == null && queryString == null) {
            return null;
        }
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        QueryBuilder queryBuilder = null;
        if (mustQueryBuilder != null) {
            boolQueryBuilder = boolQueryBuilder.must(mustQueryBuilder);
        }
        if (shouldQueryBuilder != null) {
            boolQueryBuilder = boolQueryBuilder.must(shouldQueryBuilder);
        }
        if (rangeQueryBuilder != null) {
            boolQueryBuilder = boolQueryBuilder.must(rangeQueryBuilder);
        }
        if (mustStringBuilder != null) {
            boolQueryBuilder = boolQueryBuilder.must(mustStringBuilder);
        }

        if (mustFilterBuilder != null || shouldFilterBuilder != null) {
            BoolFilterBuilder boolFilterBuilder = FilterBuilders.boolFilter();
            if (mustFilterBuilder != null) {
                boolFilterBuilder = boolFilterBuilder.must(mustFilterBuilder);
            }
            if (shouldFilterBuilder != null) {
                boolFilterBuilder = boolFilterBuilder.must(shouldFilterBuilder);
            }
            if (boolQueryBuilder.hasClauses()) {
                queryBuilder = QueryBuilders.filteredQuery(boolQueryBuilder, boolFilterBuilder);
            } else {
                queryBuilder = QueryBuilders.filteredQuery(QueryBuilders.matchAllQuery(), boolFilterBuilder);
            }
        } else {
            queryBuilder = boolQueryBuilder;
        }

        try {
            SearchRequestBuilder searchRequestBuilder = null;
            searchRequestBuilder = searchClient.prepareSearch(indexNames).setTypes(type)
                    .setSearchType(SearchType.DEFAULT)
                    .setQuery(queryBuilder).setFrom(offset).setSize(limit)
                    .setExplain(true);
            if (sortField == null || sortField.isEmpty() || sortOrder == null) {
				/* 如果不需要排序 */
            } else {
				/* 如果需要排序 */
                searchRequestBuilder = searchRequestBuilder.addSort(sortField,
                        sortOrder);
            }
            if (queryMustParams != null) {
                searchRequestBuilder = this.createHighlight(searchRequestBuilder,
                        queryMustParams);
            }
            if (queryShouldParams != null) {
                searchRequestBuilder = this.createHighlight(searchRequestBuilder,
                        queryShouldParams);
            }
            if (queryMustParams != null) {
                searchRequestBuilder = this.createHighlight(searchRequestBuilder,
                        queryMustParams);
            }
            if (queryShouldParams != null) {
                searchRequestBuilder = this.createHighlight(searchRequestBuilder,
                        filterShouldParams);
            }
            if (queryString != null) {
                searchRequestBuilder = this.createHighlight(searchRequestBuilder,
                        queryString);
            }

            SearchResponse searchResponse = searchRequestBuilder.execute()
                    .actionGet();
            return this.getSearchResult(searchResponse);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        return null;
    }
    @Override
    public List<Map<String, Object>> search(String[] indexNames, String type,
                                            CompositeQueryParam param) {
        List<SearchParam> queryMustParams = param.getQueryMustParams();
        List<SearchParam> queryShouldParams = param.getQueryShouldParams();
        List<RangeParam> queryRangeParams = param.getQueryRangeParams();
        List<SearchParam> filterMustParams = param.getFilterMustParams();
        List<SearchParam> filterShouldParams = param.getFilterShouldParams();
        List<SearchParam> queryString = param.getQueryStringParams();
        int offset = param.getOffset();
        int limit = param.getLimit();
        String sortField = param.getSortField();
        SortOrder sortOrder = param.getSortOrder();


        if (limit <= 0) {
            return null;
        }
        Client searchClient = this.elasticClientFactory
                .getOrCreateTransportClient();
		/* 创建must搜索条件 */
        QueryBuilder mustQueryBuilder = this.createMustTermQueryBuilder(queryMustParams);
		/* 创建should搜索条件 */
        QueryBuilder shouldQueryBuilder = this
                .createShouldTermQueryBuilder(queryShouldParams);
		/* 创建range搜索条件 */
        QueryBuilder rangeQueryBuilder = this
                .createRangeQueryBuilder(queryRangeParams);
		/* 创建String查询条件 */
        QueryBuilder mustStringBuilder = this.
                createMustStringQueryBuilder(queryString);

		/* 创建must过滤条件 */
        FilterBuilder mustFilterBuilder = this.createMustFilterBuilder(filterMustParams);
		/* 创建should过滤条件 */
        FilterBuilder shouldFilterBuilder = this
                .createShouldFilterBuilder(filterShouldParams);

        if (mustQueryBuilder == null && shouldQueryBuilder == null
                && rangeQueryBuilder == null
                && mustFilterBuilder == null
                && shouldFilterBuilder == null && queryString == null) {
            return null;
        }
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        QueryBuilder queryBuilder = null;
        if (mustQueryBuilder != null) {
            boolQueryBuilder = boolQueryBuilder.must(mustQueryBuilder);
        }
        if (shouldQueryBuilder != null) {
            boolQueryBuilder = boolQueryBuilder.must(shouldQueryBuilder);
        }
        if (rangeQueryBuilder != null) {
            boolQueryBuilder = boolQueryBuilder.must(rangeQueryBuilder);
        }
        if (mustStringBuilder != null) {
            boolQueryBuilder = boolQueryBuilder.must(mustStringBuilder);
        }

        if (mustFilterBuilder != null || shouldFilterBuilder != null) {
            BoolFilterBuilder boolFilterBuilder = FilterBuilders.boolFilter();
            if (mustFilterBuilder != null) {
                boolFilterBuilder = boolFilterBuilder.must(mustFilterBuilder);
            }
            if (shouldFilterBuilder != null) {
                boolFilterBuilder = boolFilterBuilder.must(shouldFilterBuilder);
            }
            if (boolQueryBuilder.hasClauses()) {
                queryBuilder = QueryBuilders.filteredQuery(boolQueryBuilder, boolFilterBuilder);
            } else {
                queryBuilder = QueryBuilders.filteredQuery(QueryBuilders.matchAllQuery(), boolFilterBuilder);
            }
        } else {
            queryBuilder = boolQueryBuilder;
        }

        try {
            SearchRequestBuilder searchRequestBuilder = null;
            searchRequestBuilder = searchClient.prepareSearch(indexNames).setTypes(type)
                    .setSearchType(SearchType.DEFAULT)
                    .setQuery(queryBuilder).setFrom(offset).setSize(limit)
                    .setExplain(true);
            if (sortField == null || sortField.isEmpty() || sortOrder == null) {
				/* 如果不需要排序 */
            } else {
				/* 如果需要排序 */
                searchRequestBuilder = searchRequestBuilder.addSort(sortField,
                        sortOrder);
            }
            if (queryMustParams != null) {
                searchRequestBuilder = this.createHighlight(searchRequestBuilder,
                        queryMustParams);
            }
            if (queryShouldParams != null) {
                searchRequestBuilder = this.createHighlight(searchRequestBuilder,
                        queryShouldParams);
            }
            if (queryMustParams != null) {
                searchRequestBuilder = this.createHighlight(searchRequestBuilder,
                        queryMustParams);
            }
            if (queryShouldParams != null) {
                searchRequestBuilder = this.createHighlight(searchRequestBuilder,
                        filterShouldParams);
            }
            if (queryString != null) {
                searchRequestBuilder = this.createHighlight(searchRequestBuilder,
                        queryString);
            }

            SearchResponse searchResponse = searchRequestBuilder.execute()
                    .actionGet();
            return this.getSearchResult(searchResponse);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        return null;
    }

    public boolean bulkInsert(String indexName, String typeName,
                              List<Map<String, Object>> indexDatas) {
        XContentBuilder xContentBuilder = null;
        Client client = getElasticClientFactory().getOrCreateTransportClient();
        BulkRequestBuilder bulkRequest = client.prepareBulk();
        try {
            for (Map<String, Object> indexData : indexDatas) {
                xContentBuilder = XContentFactory.jsonBuilder().startObject();
                for (Map.Entry<String, Object> pair : indexData.entrySet()) {
                    xContentBuilder = xContentBuilder.field(pair.getKey(),
                            pair.getValue());
                }
                xContentBuilder = xContentBuilder.endObject();
                IndexRequestBuilder request = client.prepareIndex(indexName, typeName).setSource(xContentBuilder);
                if (indexData.get("_id") != null) {
                    request.setId((String) indexData.get("_id"));
                }
                bulkRequest.add(request);

            }
            BulkResponse bulkResponse = bulkRequest.execute().actionGet();
            if (!bulkResponse.hasFailures()) {
                return true;
            } else {
                System.out.println(bulkResponse.buildFailureMessage());
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        return false;

    }

    public boolean deleteByQuery(String indexName, String type, List<SearchParam> params) {
        try {
            Client client = this.elasticClientFactory
                    .getOrCreateTransportClient();
            QueryBuilder queryBuilder = null;
            queryBuilder = createMustTermQueryBuilder(params);
            client.prepareDeleteByQuery(indexName).setTypes(type).setQuery(queryBuilder)
                    .execute().actionGet();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean createIndex(String indexName, int shards, int replicas) {
        Client client = this.elasticClientFactory.getOrCreateTransportClient();
        Settings settings = ImmutableSettings.settingsBuilder()
                .put("number_of_shards", shards)
                .put("number_of_replicas", replicas).build();
        CreateIndexResponse indexresponse = client.admin().indices()
                // 这个索引库的名称还必须不包含大写字母
                .prepareCreate(indexName).setSettings(settings).execute()
                .actionGet();

        return indexresponse.isAcknowledged();

    }

    public boolean addMapping(String indexName, String typeName,
                              List<IndexMapping> mappings, String analyzer) {
        Client client = this.elasticClientFactory.getOrCreateTransportClient();
        PutMappingResponse response = null;
        try {
            XContentBuilder xContentBuilder = XContentFactory.jsonBuilder()
                    .startObject().startObject(typeName)
                    .startObject("properties");
            for (IndexMapping mapping : mappings) {

                xContentBuilder.startObject(mapping.getFieldName())
                        .field("type", mapping.getFiledType())
                        .field("indexAnalyzer", analyzer)
                        .field("searchAnalyzer", analyzer)
                        .field("index", mapping.getIndex());
                if (mapping.isStore()) {
                    xContentBuilder.field("store", "yes");
                }
                xContentBuilder.endObject();

            }

            xContentBuilder.endObject().endObject().endObject();
            PutMappingRequestBuilder builder = client.admin().indices()
                    .preparePutMapping(indexName);
            builder.setType(typeName);
            builder.setSource(xContentBuilder);
            response = builder.execute().actionGet();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return response.isAcknowledged();

    }

    @Override
    public boolean updateByQuery(String indexName, String typeName,
                                 List<SearchParam> searchParams, Map<String, Object> indexData) {
        try {
            Client client = this.elasticClientFactory
                    .getOrCreateTransportClient();
            QueryBuilder queryBuilder = null;
            queryBuilder = createMustTermQueryBuilder(searchParams);
            this.deleteByQuery(indexName, typeName, searchParams);

            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            list.add(indexData);
            this.bulkInsert(indexName, typeName, list);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public boolean deleteById(String indexName, String type, String id) {
        if (StringUtils.isEmpty(id)) {
            return false;
        }
        try {
            Client client = this.elasticClientFactory
                    .getOrCreateTransportClient();
            client.prepareDelete().setIndex(indexName).setType(type).setId(id)
                    .execute().actionGet();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public boolean updateById(String indexName, String typeName, String id,
                              Map<String, Object> indexData) {
        try {
            Client client = this.elasticClientFactory
                    .getOrCreateTransportClient();
            this.deleteById(indexName, typeName, id);
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            indexData.put("_id", id);
            list.add(indexData);
            this.bulkInsert(indexName, typeName, list);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public List<Map<String, Object>> moreLikeThis(String indexName, String type, String documentId, int size, String... fields) {
        try {
            Client client = this.elasticClientFactory
                    .getOrCreateTransportClient();
            MoreLikeThisRequestBuilder mlt = new MoreLikeThisRequestBuilder(client, indexName, type, documentId);
            mlt.setField(fields);
            mlt.setSearchSize(size);
            mlt.setMinTermFreq(1);
            SearchResponse response = client.moreLikeThis(mlt.request()).actionGet();
            return getSearchResult(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public boolean updateByIdBulk(String indexName, String typeName,
                                  Map<String, Map<String, Object>> datas) {
        XContentBuilder xContentBuilder = null;
        Client client = getElasticClientFactory().getOrCreateTransportClient();
        BulkRequestBuilder bulkRequest = client.prepareBulk();
        try {
            for (Map.Entry<String, Map<String, Object>> data : datas.entrySet()) {
                String id = data.getKey();
                Map<String, Object> updateData = data.getValue();
                xContentBuilder = XContentFactory.jsonBuilder().startObject();
                for (Map.Entry<String, Object> pair : updateData.entrySet()) {
                    xContentBuilder = xContentBuilder.field(pair.getKey(),
                            pair.getValue());
                }
                xContentBuilder = xContentBuilder.endObject();
                UpdateRequestBuilder request = client.prepareUpdate().setId(id).setIndex(indexName).setType(typeName).setDoc(xContentBuilder);
                bulkRequest.add(request);
            }

            BulkResponse bulkResponse = bulkRequest.execute().actionGet();
            if (!bulkResponse.hasFailures()) {
                return true;
            } else {
                System.out.println(bulkResponse.buildFailureMessage());
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        return false;
    }


    @Override
    public List<Map<String, Object>> matchAll(String[] indexNames, String type,
                                              CompositeQueryParam param) {
        QueryBuilder builder = QueryBuilders.matchAllQuery();
        Client client = getElasticClientFactory().getOrCreateTransportClient();
        SearchRequestBuilder searchBuilder = client.prepareSearch().setIndices(indexNames)
                .setTypes(type)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(builder) // Query
                .setFrom(param.getOffset()).setSize(param.getLimit()).setExplain(true);
        if (param.getSortField() != null) {
            searchBuilder.addSort(param.getSortField(), param.getSortOrder());
        }

        SearchResponse response = searchBuilder.execute().actionGet();

        return getSearchResult(response);
    }


}

