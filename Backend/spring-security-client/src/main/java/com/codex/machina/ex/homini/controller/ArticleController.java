package com.codex.machina.ex.homini.controller;

import com.codex.machina.ex.homini.error.ArticleNotFoundException;
import com.codex.machina.ex.homini.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
public class ArticleController
{

    @Autowired
    private ArticleService articleService;

    @GetMapping("/articles/{title}")
    public String fetchArticleByTitle(@PathVariable("title") String title)
            throws ArticleNotFoundException, IOException
    {
        return articleService.fetchArticleByTitle(title);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/articles")
    public List<String> findArticleBySearch(@RequestParam(value = "search_query", required = false) String searchQuery)
            throws ArticleNotFoundException, IOException
    {
        if(searchQuery != null)
        {
            return articleService.findArticleBySearch(searchQuery);
        }
        return articleService.fetchArticles();
    }
    @GetMapping("/link")
    public String linkArticle() throws IOException
    {
        articleService.linkArticles();
        return "Articles Linked from es to MySQL";
    }
}
