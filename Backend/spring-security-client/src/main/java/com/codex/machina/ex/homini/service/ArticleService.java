package com.codex.machina.ex.homini.service;

import com.codex.machina.ex.homini.error.ArticleNotFoundException;

import java.io.IOException;
import java.util.List;

public interface ArticleService
{
    String fetchArticleByTitle(String title) throws ArticleNotFoundException, IOException;
    List<String> findArticleBySearch(String searchQuery) throws ArticleNotFoundException, IOException;
    String fetchArticleById(Long id) throws ArticleNotFoundException, IOException;
    List<String> fetchArticles(int size) throws ArticleNotFoundException, IOException;
    void linkArticles() throws IOException;
}
