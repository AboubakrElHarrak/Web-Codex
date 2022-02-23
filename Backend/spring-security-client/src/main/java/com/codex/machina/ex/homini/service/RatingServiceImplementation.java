package com.codex.machina.ex.homini.service;

import com.codex.machina.ex.homini.entity.Article;
import com.codex.machina.ex.homini.entity.Rating;
import com.codex.machina.ex.homini.entity.User;
import com.codex.machina.ex.homini.error.ArticleNotFoundException;
import com.codex.machina.ex.homini.repository.ArticleRepository;
import com.codex.machina.ex.homini.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class RatingServiceImplementation implements RatingService
{
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private UserService userService;
    @Override
    public void saveRatingForArticleAndUser(int rating_grade, String title, String username)
            throws ArticleNotFoundException, UsernameNotFoundException
    {
        Article article = articleRepository.findByTitle(title);
        if(article == null)
        {
            throw new ArticleNotFoundException("Article not found");
        }
        User user = userService.getUserByUsername(username);
        if(user == null)
        {
            throw new UsernameNotFoundException("User not found");
        }
        Rating rating = ratingRepository.findByUserAndArticle(user, article);
        if(rating != null)
        {
            rating.setRating(rating_grade);
        }
        else
        {
            rating = new Rating();
            rating.setRating(rating_grade);
            rating.setArticle(article);
            rating.setUser(user);
        }
        ratingRepository.save(rating);
    }
}
