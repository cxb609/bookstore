package com.bookstore.backend.service;

import com.alibaba.fastjson.JSON;
import com.bookstore.backend.dao.BookDao;
import com.bookstore.backend.entity.*;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class ElasticsearchService {
    @Autowired
    private RestHighLevelClient elasticsearchClient;

    @Autowired
    RestClientBuilder restClientBuilder;

    @Autowired
    private BookDao bookDao;

    private final String elasticsearchIndex = "amazonbooks";

    /**
     * 创建索引
     * @return
     */
    public boolean createIndex() {
        restClientBuilder = RestClient.builder(new HttpHost("localhost",9200)).setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
            @Override
            public RequestConfig.Builder customizeRequestConfig(RequestConfig.Builder requestConfigBuilder) {
                requestConfigBuilder.setConnectTimeout(5000);
                requestConfigBuilder.setSocketTimeout(600000);
                requestConfigBuilder.setConnectionRequestTimeout(1000);
                return requestConfigBuilder;
            }
        }).setMaxRetryTimeoutMillis(5*60*10000);

        elasticsearchClient = new RestHighLevelClient(restClientBuilder);

//        restClientBuilder.setMaxRetryTimeoutMillis(60000);
//        elasticsearchClient = new RestHighLevelClient(restClientBuilder);

        CreateIndexRequest request = new CreateIndexRequest(elasticsearchIndex);
        request.settings(Settings.builder().put("index.number_of_shards", 1));
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder();
            builder.startObject();
            {
                builder.startObject("_doc");
                {
                    builder.startObject("properties");
                    {
                        builder.startObject("title");
                        {
                            builder.field("type", "text");
                        }
                        builder.endObject();
                        builder.startObject("author");
                        {
                            builder.field("type", "text");
                        }
                        builder.endObject();
                        builder.startObject("category");
                        {
                            builder.field("type", "text");
                        }
                        builder.endObject();
                        builder.startObject("price");
                        {
                            builder.field("type", "text");
                        }
                        builder.endObject();
                        builder.startObject("picture");
                        {
                            builder.field("type", "text");
                        }
                        builder.endObject();
                        builder.startObject("description");
                        {
                            builder.field("type", "text");
                        }
                        builder.endObject();
                    }
                    builder.endObject();
                }
                builder.endObject();
            }
            builder.endObject();
            request.mapping("_doc", builder);
            CreateIndexResponse createIndexResponse = elasticsearchClient.indices().create(request, RequestOptions.DEFAULT);
            return createIndexResponse.isAcknowledged();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 加载数据
     * @return
     */
    public Result loadData(){
        BulkRequest bulkRequest = new BulkRequest();
        List<Book> books = bookDao.getAllBook();
        for(Book book:books) {
            ElasticsearchBook elasticsearchBook = new ElasticsearchBook(book);
            UpdateRequest updateRequest = new UpdateRequest(elasticsearchIndex, "_doc", elasticsearchBook.getBook_id());
            Map<String, Object> jsonMap = (Map<String, Object>) (JSON.toJSON(elasticsearchBook));
            updateRequest.doc(jsonMap);
            updateRequest.upsert(jsonMap);
            bulkRequest.add(updateRequest);
        }
        try {
            BulkResponse bulkResponse = elasticsearchClient.bulk(bulkRequest, RequestOptions.DEFAULT);
            int failed_count = 0;
            int success_count = 0;
            for(BulkItemResponse bulkItemResponse:bulkResponse){
                if(bulkItemResponse.isFailed()){
                    failed_count++;
                }
                else{
                    success_count++;
                }
            }
            Map<String, Object> data = new HashMap<>();
            data.put("failed_count", failed_count);
            data.put("success_count", success_count);
            return Result.OK(data).build();
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceException(ErrorCode.SERVER_EXCEPTION, "Elasticsearch异常" + e.getMessage());
        }
    }


    public Result search(String keyword, int size){
        SearchRequest searchRequest = new SearchRequest(elasticsearchIndex);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        if(keyword.equals("")){
            searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        }else{
            BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
            boolQueryBuilder.should(QueryBuilders.matchQuery("title", keyword).boost(1.0f));
            boolQueryBuilder.should(QueryBuilders.matchQuery("description", keyword).boost(3.0f));
            searchSourceBuilder.query(boolQueryBuilder);
        }
        searchSourceBuilder.size(size);
        searchRequest.source(searchSourceBuilder);
        try {
            SearchResponse searchResponse = elasticsearchClient.search(searchRequest, RequestOptions.DEFAULT);
            SearchHit[] searchHits = searchResponse.getHits().getHits();
            List<Map<String, Object>> suggested_entities = new LinkedList<>();
            for(SearchHit searchHit: searchHits){
                Map<String, Object> entity = searchHit.getSourceAsMap();
                entity.put("score", searchHit.getScore());
                suggested_entities.add(entity);
            }
            return Result.OK(suggested_entities).build();
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceException(ErrorCode.SERVER_EXCEPTION, "Elasticsearch异常" + e.getMessage());
        }
    }
}
