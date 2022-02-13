package com.codex.machina.ex.homini.service;

import com.codex.machina.ex.homini.error.ArticleNotFoundException;

import java.io.IOException;
import java.util.Map;

public interface ArticleService
{
    Map<String, Object> fetchArticleByTitle(String title) throws ArticleNotFoundException, IOException;
}
