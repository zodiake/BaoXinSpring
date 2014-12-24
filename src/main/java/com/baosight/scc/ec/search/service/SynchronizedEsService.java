package com.baosight.scc.ec.search.service;

import com.baosight.scc.ec.search.client.ElasticClientFactory;
import com.baosight.scc.ec.search.operator.EsOperator;
import com.baosight.scc.ec.search.operator.EsSimpleOperatorImpl;
import com.baosight.scc.ec.search.properties.CompositeQueryParam;
import com.baosight.scc.ec.search.properties.ElasticClientProperties;
import com.baosight.scc.ec.search.properties.IndexMapping;
import com.baosight.scc.ec.search.properties.SearchParam;
import org.dom4j.DocumentException;
import org.elasticsearch.index.query.QueryStringQueryBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SynchronizedEsService implements EsService {
    private EsOperator operator;
    private ElasticClientProperties elasticClientProperties;


    public ElasticClientProperties getElasticClientProperties() {
        return elasticClientProperties;
    }


    public void setElasticClientProperties(
            ElasticClientProperties elasticClientProperties) {
        this.elasticClientProperties = elasticClientProperties;
    }


    public EsOperator getOperator() {
        return operator;
    }

    public void setOperator(EsOperator operator) {
        this.operator = operator;
    }


    @Override
    public boolean createIndex(String indexName, int shards, int replicas) {
        return operator.createIndex(indexName, shards, replicas);
    }

    @Override
    public boolean addMapping(String indexName, String typeName,
                              List<IndexMapping> mappings, String analyzer) {
        return operator.addMapping(indexName, typeName, mappings, analyzer);
    }

    @Override
    public boolean insertList(String typeName,
                              List<Map<String, Object>> indexDatas) {
        return operator.bulkInsert(elasticClientProperties.getIndexName(), typeName, indexDatas);
    }


    @Override
    public boolean deleteByQuery(String typeName, List<SearchParam> params) {
        return operator.deleteByQuery(elasticClientProperties.getIndexName(), typeName, params);
    }

    @Override
    public boolean updateByQuery(String typeName,
                                 List<SearchParam> searchParams, Map<String, Object> indexData) {
        return operator.updateByQuery(elasticClientProperties.getIndexName(), typeName, searchParams, indexData);
    }


    @Override
    public long count(String typeName, CompositeQueryParam param) {
        return operator.count(new String[]{elasticClientProperties.getIndexName()}, typeName, param);
    }

    @Override
    public long countOper(String typeName, CompositeQueryParam param,QueryStringQueryBuilder.Operator operator1) {
        return operator.countOper(new String[]{elasticClientProperties.getIndexName()}, typeName, param,operator1);
    }

    @Override
    public List<Map<String, Object>> search(String typeName,
                                            CompositeQueryParam param) {
        return operator.search(new String[]{elasticClientProperties.getIndexName()}, typeName, param);

    }

    @Override
    public List<Map<String, Object>> searchOper(String typeName,
                                            CompositeQueryParam param,QueryStringQueryBuilder.Operator operator1) {
        return operator.searchOperator(new String[]{elasticClientProperties.getIndexName()}, typeName, param,operator1);

    }

    @Override
    public boolean deleteById(String typeName, String id) {
        return operator.deleteById(elasticClientProperties.getIndexName(), typeName, id);
    }

    @Override
    public List<Map<String, Object>> matchAll(String type, CompositeQueryParam param) {
        return operator.matchAll(new String[]{elasticClientProperties.getIndexName()}, type, param);

    }


    @Override
    public boolean updateById(String typeName, String id,
                              Map<String, Object> indexData) {
        return operator.updateById(elasticClientProperties.getIndexName(), typeName, id, indexData);
    }


    @Override
    public List<Map<String, Object>> moreLikeThis(String type,
                                                  String documentId, int size, String... fields) {
        return operator.moreLikeThis(elasticClientProperties.getIndexName(), type, documentId, size, fields);
    }

    @Override
    public boolean updateByIdBulk(String typeName, Map<String, Map<String, Object>> datas) {
        return operator.updateByIdBulk(elasticClientProperties.getIndexName(), typeName, datas);

    }


    public static void main(String args[]) throws IOException, DocumentException {
        ElasticClientProperties properties = new ElasticClientProperties("es_client.xml");
        ElasticClientFactory factory = new ElasticClientFactory();
        factory.setElasticClientProperties(properties);
        EsSimpleOperatorImpl operator = new EsSimpleOperatorImpl();
        operator.setElasticClientFactory(factory);
        operator.setElasticClientProperties(properties);
        SynchronizedEsService service = new SynchronizedEsService();
        service.setElasticClientProperties(properties);
        service.setOperator(operator);

        //testInsert(service);
        //testSearch(service);
        //service.testCreateIndex(service);
        //testAddMapping(service);
        //testMoreLike(service);
        //testUpdateBulk(service);
        testMatchAll(service);
    }


    public static void testMatchAll(EsService service) {
        CompositeQueryParam param = new CompositeQueryParam();
        param.setSortField("popular");
        System.out.println(service.matchAll("fabric", param));
    }

    public static void testUpdateBulk(EsService service) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("popular", 33);
        Map<String, Map<String, Object>> updateData = new HashMap<String, Map<String, Object>>();
        updateData.put("29", data);
        service.updateByIdBulk("fabric", updateData);

    }

    public static void testInsert(EsService service) {
        List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("_id", "12123-34534-323434");
        map.put("title", "测试");
        map.put("season", "测试");
        map.put("area", "测试");
        map.put("color", "测试");
        map.put("technology", "测试");
        map.put("use", "测试");
        map.put("width", 1234);
        map.put("weight", 4321);
        lists.add(map);
        service.insertList("fabric", lists);

    }

    public static void testSearch(EsService service) {
        CompositeQueryParam param = new CompositeQueryParam();
        SearchParam mustParam = new SearchParam("use", "服饰", true);
        param.addQueryMustParam(mustParam);
        //param.addQueryStringParam(mustParam);
        //	param.addQueryRangeParam(new RangeParam<Double>("price", 5.0, 30.0));
        System.out.println(service.search("material", param));


    }


    public static void testCreateIndex(EsService service) {
        service.createIndex("sccindex", 6, 1);
    }

    public static void testAddMapping(EsService service) {
        List<IndexMapping> mappings = new ArrayList<IndexMapping>();

		/*
		*//**
         * faric表
         *//*
		//产品名称
		IndexMapping map=new IndexMapping();
        map.setFieldName("title");
        map.setFiledType("string");
        map.setIndex("analyzed");
        map.setStore(true);
        mappings.add(map);
        //价格
        map=new IndexMapping();
        map.setFieldName("price");
        map.setFiledType("double");
        map.setIndex("not_analyzed");
        map.setStore(true);
        mappings.add(map);

        //地区
        map=new IndexMapping();
        map.setFieldName("area");
        map.setFiledType("string");
        map.setIndex("not_analyzed");
        map.setStore(true);
        mappings.add(map);

        //人气
        map=new IndexMapping();
        map.setFieldName("popular");
        map.setFiledType("integer");
        map.setIndex("not_analyzed");
        map.setStore(true);
        mappings.add(map);

        //销量
        map=new IndexMapping();
        map.setFieldName("sales");
        map.setFiledType("integer");
        map.setIndex("not_analyzed");
        map.setStore(true);
        mappings.add(map);


        //季节
        map=new IndexMapping();
        map.setFieldName("season");
        map.setFiledType("string");
        map.setIndex("not_analyzed");
        map.setStore(false);
        mappings.add(map);

        //颜色
        map=new IndexMapping();
        map.setFieldName("color");
        map.setFiledType("string");
        map.setIndex("not_analyzed");
        map.setStore(false);
        mappings.add(map);

        //工艺
        map=new IndexMapping();
        map.setFieldName("technology");
        map.setFiledType("string");
        map.setIndex("not_analyzed");
        map.setStore(false);
        mappings.add(map);

        //用途
        map=new IndexMapping();
        map.setFieldName("use");
        map.setFiledType("string");
        map.setIndex("not_analyzed");
        map.setStore(false);
        mappings.add(map);

        //克重
        map=new IndexMapping();
        map.setFieldName("weight");
        map.setFiledType("double");
        map.setIndex("not_analyzed");
        map.setStore(false);
        mappings.add(map);

        //幅宽
        map=new IndexMapping();
        map.setFieldName("width");
        map.setFiledType("double");
        map.setIndex("not_analyzed");
        map.setStore(false);
        mappings.add(map);

        //原料成分
        map=new IndexMapping();
        map.setFieldName("source");
        map.setFiledType("string");
        map.setIndex("not_analyzed");
        map.setStore(false);
        mappings.add(map);

      //公司名称
        map=new IndexMapping();
        map.setFieldName("company");
        map.setFiledType("string");
        map.setIndex("not_analyzed");
        map.setStore(true);
        mappings.add(map);

      //分类
        map=new IndexMapping();
        map.setFieldName("category");
        map.setFiledType("string");
        map.setIndex("not_analyzed");
        map.setStore(true);
        mappings.add(map);

	service.addMapping("sccindex", "fabric", mappings, "ik");



	 mappings=new ArrayList<IndexMapping>();



		 * material表

		//产品名称
		 map=new IndexMapping();
        map.setFieldName("title");
        map.setFiledType("string");
        map.setIndex("analyzed");
        map.setStore(true);
        mappings.add(map);
        //价格
        map=new IndexMapping();
        map.setFieldName("price");
        map.setFiledType("double");
        map.setIndex("not_analyzed");
        map.setStore(true);
        mappings.add(map);

        //地区
        map=new IndexMapping();
        map.setFieldName("area");
        map.setFiledType("string");
        map.setIndex("not_analyzed");
        map.setStore(true);
        mappings.add(map);

        //人气
        map=new IndexMapping();
        map.setFieldName("popular");
        map.setFiledType("integer");
        map.setIndex("not_analyzed");
        map.setStore(true);
        mappings.add(map);

        //销量
        map=new IndexMapping();
        map.setFieldName("sales");
        map.setFiledType("integer");
        map.setIndex("not_analyzed");
        map.setStore(true);
        mappings.add(map);


        //季节
        map=new IndexMapping();
        map.setFieldName("season");
        map.setFiledType("string");
        map.setIndex("not_analyzed");
        map.setStore(false);
        mappings.add(map);

        //颜色
        map=new IndexMapping();
        map.setFieldName("color");
        map.setFiledType("string");
        map.setIndex("not_analyzed");
        map.setStore(false);
        mappings.add(map);


        //用途
        map=new IndexMapping();
        map.setFieldName("use");
        map.setFiledType("string");
        map.setIndex("not_analyzed");
        map.setStore(false);
        mappings.add(map);

        //克重
        map=new IndexMapping();
        map.setFieldName("weight");
        map.setFiledType("string");
        map.setIndex("not_analyzed");
        map.setStore(false);
        mappings.add(map);

      //公司名称
        map=new IndexMapping();
        map.setFieldName("company");
        map.setFiledType("string");
        map.setIndex("not_analyzed");
        map.setStore(true);
        mappings.add(map);

      //分类
        map=new IndexMapping();
        map.setFieldName("category");
        map.setFiledType("string");
        map.setIndex("not_analyzed");
        map.setStore(true);
        mappings.add(map);

	service.addMapping("sccindex", "material", mappings, "ik");

	 mappings=new ArrayList<IndexMapping>();

		*//**
         * user表
         *//*
		//公司名称
		map=new IndexMapping();
        map.setFieldName("name");
        map.setFiledType("string");
        map.setIndex("analyzed");
        map.setStore(true);
        mappings.add(map);
        //供应商品类型
        map=new IndexMapping();
        map.setFieldName("type");
        map.setFiledType("string");
        map.setIndex("not_analyzed");
        map.setStore(true);
        mappings.add(map);

        //主营产品或服务
        map=new IndexMapping();
        map.setFieldName("focus");
        map.setFiledType("string");
        map.setIndex("analyzed");
        map.setStore(true);
        mappings.add(map);
        //公司简介
        map=new IndexMapping();
        map.setFieldName("desc");
        map.setFiledType("string");
        map.setIndex("analyzed");
        map.setStore(true);
        mappings.add(map);
        //经营范围
        map=new IndexMapping();
        map.setFieldName("scope");
        map.setFiledType("string");
        map.setIndex("analyzed");
        map.setStore(true);
        mappings.add(map);

      //注册资本
        map=new IndexMapping();
        map.setFieldName("money");
        map.setFiledType("double");
        map.setIndex("not_analyzed");
        map.setStore(true);
        mappings.add(map);

       service.addMapping("sccindex", "user", mappings, "ik");

        */


    }


    public static void testMoreLike(EsService service) {
        System.out.println(service.moreLikeThis("material", "21", 10, "title"));
    }


}

