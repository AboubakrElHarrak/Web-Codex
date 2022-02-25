package com.codex.machina.ex.homini.controller;

import com.codex.machina.ex.homini.Model.RatingModel;
import com.codex.machina.ex.homini.entity.Article;
import com.codex.machina.ex.homini.error.ArticleNotFoundException;
import com.codex.machina.ex.homini.service.ArticleService;
import com.codex.machina.ex.homini.service.RatingService;
import com.codex.machina.ex.homini.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ArticleController
{

    @Autowired
    private ArticleService articleService;
    @Autowired
    private RatingService ratingService;
    @Autowired
    private RecommendationService recommendationService;

    @GetMapping("/articles/{title}")
    public String fetchArticleByTitle(@PathVariable("title") String title)
            throws ArticleNotFoundException, IOException
    {
        return articleService.fetchArticleByTitle(title);
    }

    @GetMapping("/articles")
    public List<String> findArticleBySearch(@RequestParam(value = "search_query", required = false) String searchQuery)
            throws ArticleNotFoundException, IOException
    {
        if(searchQuery != null)
        {
            return articleService.findArticleBySearch(searchQuery);
        }
        return articleService.fetchArticles(5);
    }
    @GetMapping("/link")
    public String linkArticle() throws IOException
    {
        articleService.linkArticles();
        return "Articles Linked from es to MySQL";
    }

    @PostMapping("/api/rate")
    public String rateArticle(Principal principal, @RequestBody RatingModel ratingModel) throws ArticleNotFoundException
    {
        ratingService.saveRatingForArticleAndUser(ratingModel.getRating(),ratingModel.getTitle(), principal.getName());
        return "success";
    }
    @GetMapping("/api/recommend")
    public List<String> getRecommendations(Principal principal) throws ArticleNotFoundException, IOException
    {
        List<Long> predictionsIds = recommendationService.getPredictions(principal.getName());
        if(predictionsIds == null)
        {
            return articleService.fetchArticles(25);
        }
        List<String> articles = new ArrayList<>();
        for(Long id : predictionsIds)
        {
            articles.add(articleService.fetchArticleById(id));
        }
        return articles;
    }
}
