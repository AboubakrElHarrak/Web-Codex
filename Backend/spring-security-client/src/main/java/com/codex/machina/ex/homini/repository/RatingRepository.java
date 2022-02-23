package com.codex.machina.ex.homini.repository;

import com.codex.machina.ex.homini.entity.Article;
import com.codex.machina.ex.homini.entity.Rating;
import com.codex.machina.ex.homini.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Long>
{
    Rating findByUserAndArticle(User user, Article article);
}
