package com.codex.machina.ex.homini.repository;

import org.springframework.data.repository.CrudRepository;

import com.codex.machina.ex.homini.entity.Article;
import com.codex.machina.ex.homini.entity.Comment;

public interface CommentRepository extends CrudRepository<Comment, Long>{
	Iterable<Comment> findByArticle(Article article);
}
