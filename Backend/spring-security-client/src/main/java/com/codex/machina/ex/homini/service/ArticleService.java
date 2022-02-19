package com.codex.machina.ex.homini.service;

import com.codex.machina.ex.homini.error.ArticleNotFoundException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ArticleService
{
    String fetchArticleByTitle(String title) throws ArticleNotFoundException, IOException;
    List<String> findArticleBySearch(String searchQuery) throws ArticleNotFoundException, IOException;

    List<String> fetchArticles() throws ArticleNotFoundException, IOException;
    void linkArticles() throws IOException;
}
