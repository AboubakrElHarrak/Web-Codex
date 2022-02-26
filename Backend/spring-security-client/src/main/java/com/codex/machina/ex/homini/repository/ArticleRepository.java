package com.codex.machina.ex.homini.repository;

import com.codex.machina.ex.homini.entity.Article;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long>
{
    Optional<Article> findById(Long id);
    Article findByTitle(String title);
    List<Article> findAll();
    @Query("SELECT ar.articleId FROM Article as ar ORDER BY RAND()")
    List<Long> randomArticles();
}
