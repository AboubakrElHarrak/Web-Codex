package com.codex.machina.ex.homini.controller;

import com.codex.machina.ex.homini.error.ArticleNotFoundException;
import com.codex.machina.ex.homini.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
public class ArticleController
{

    @Autowired
    private ArticleService articleService;

    @GetMapping("/articles/{title}")
    public Map<String, Object> fetchArticleByTitle(@PathVariable("title") String title) throws ArticleNotFoundException, IOException
    {
        return articleService.fetchArticleByTitle(title);
    }
}
