package com.bookstore.backend.entity.event;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class ElasticSearchSubscriber {
    private static Logger logger = LoggerFactory.getLogger(ElasticSearchSubscriber.class);

    @Autowired
    private RestHighLevelClient elasticsearchClient;

    private String elasticsearchIndex = "amazonbooks";

    public ElasticSearchSubscriber(){
        EventBus.getDefault().register(this);
    }

    @Subscribe
    public void insertToElasticsearch(InsertEvent event){
        IndexRequest indexRequest = new IndexRequest(elasticsearchIndex, "_doc", event.getElasticsearchBook().getBook_id());
        indexRequest.source((Map<String, Object>) JSON.toJSON(event.getElasticsearchBook()));
        indexRequest.create(true);
        try {
            IndexResponse indexResponse = elasticsearchClient.index(indexRequest, RequestOptions.DEFAULT);
            if(indexResponse.getResult() == DocWriteResponse.Result.CREATED)
                logger.info(event.toString());
            else
            {
                logger.error("Elasticsearch: 新增实体失败");
                logger.error(indexResponse.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Subscribe
    public void deleteBook(DeleteEvent event){
        DeleteRequest deleteRequest = new DeleteRequest(elasticsearchIndex, "_doc", event.getBook_id());
        try {
            DeleteResponse deleteResponse = elasticsearchClient.delete(deleteRequest, RequestOptions.DEFAULT);
            if(deleteResponse.getResult() == DocWriteResponse.Result.NOT_FOUND)
            {
                logger.error("Elasticsearch: 待删除实体不存在");
            }else
                logger.info(event.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
