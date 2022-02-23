package com.codex.machina.ex.homini.service;

import com.codex.machina.ex.homini.entity.Article;
import com.codex.machina.ex.homini.entity.User;
import com.codex.machina.ex.homini.error.ArticleNotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface RatingService
{
    void saveRatingForArticleAndUser(int rating_grade, String title, String username)
            throws ArticleNotFoundException, UsernameNotFoundException;
}
