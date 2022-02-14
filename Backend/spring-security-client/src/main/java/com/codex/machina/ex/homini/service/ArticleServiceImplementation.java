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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImplementation implements ArticleService
{
    RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(
            new HttpHost("localhost", 9200, "http")));

    @Override
    public String fetchArticleByTitle(String title) throws ArticleNotFoundException, IOException
    {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("codex_articles");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.boolQuery().must(QueryBuilders.termQuery("title.keyword",title)));
        searchSourceBuilder.size(1);
        searchRequest.source(searchSourceBuilder);
        String document;
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        if(searchResponse.getHits().getTotalHits().value == 0)
        {
            throw new ArticleNotFoundException("Article Not Available");
        }
        else if (searchResponse.getHits().getTotalHits().value > 0)
        {
            SearchHit[] searchHit = searchResponse.getHits().getHits();
            for (SearchHit hit : searchHit)
            {
                document = hit.getSourceAsString();
                return document;
            }
        }
        return null;
    }

    @Override
    public List<String> findArticleBySearch(String searchQuery) throws ArticleNotFoundException, IOException {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("codex_articles");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.multiMatchQuery(searchQuery));
        searchSourceBuilder.size(15);
        searchRequest.source(searchSourceBuilder);
        List<String> documents = new ArrayList<>();
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        if(searchResponse.getHits().getTotalHits().value == 0)
        {
            throw new ArticleNotFoundException("Your search " + searchQuery + " did not match any documents");
        }
        else if(searchResponse.getHits().getTotalHits().value > 0)
        {
            SearchHit[] searchHits = searchResponse.getHits().getHits();
            for(SearchHit hit : searchHits)
            {
                documents.add(hit.getSourceAsString());
            }
            return documents;
        }
        return null;
    }

    // NOTE(KARIM) : The map DS needs to be changed to an ordered one to preserve the initial json ordering that I made
    @Override
    public List<String> fetchArticles() throws ArticleNotFoundException, IOException
    {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("codex_articles");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query();
        searchSourceBuilder.size(5);
        searchRequest.source(searchSourceBuilder);
        List<String> documents = new ArrayList<>();
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        if(searchResponse.getHits().getTotalHits().value == 0)
        {
            throw new ArticleNotFoundException("Sorry No Articles Available at the moment !");
        }
        else if(searchResponse.getHits().getTotalHits().value > 0)
        {
            SearchHit[] searchHits = searchResponse.getHits().getHits();
            for(SearchHit hit : searchHits)
            {
                documents.add(hit.getSourceAsString());
            }
            return documents;
        }
        return null;
    }
}
