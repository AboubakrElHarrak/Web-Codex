package com.codex.machina.ex.homini.service;

import com.codex.machina.ex.homini.error.ArticleNotFoundException;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class ArticleServiceImplementation implements ArticleService
{
    RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200, "http")));

    @Override
    public Map<String, Object> fetchArticleByTitle(String title) throws ArticleNotFoundException, IOException
    {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("codex_articles");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.boolQuery().must(QueryBuilders.termQuery("title.keyword",title)));
        searchRequest.source(searchSourceBuilder);
        Map<String, Object> map = null;
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);;

        if(searchResponse.getHits().getTotalHits().value == 0)
        {
            throw new ArticleNotFoundException("Article Not Available");
        }
        else if (searchResponse.getHits().getTotalHits().value > 0)
        {
            SearchHit[] searchHit = searchResponse.getHits().getHits();
            for (SearchHit hit : searchHit)
            {
                map = hit.getSourceAsMap();
                return map;
            }
        }
        return null;
    }
}
