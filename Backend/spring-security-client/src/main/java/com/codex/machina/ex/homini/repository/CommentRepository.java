package com.codex.machina.ex.homini.repository;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import com.codex.machina.ex.homini.entity.Article;
import com.codex.machina.ex.homini.entity.Comment;

public interface CommentRepository extends CrudRepository<Comment, Long>{
	ArrayList<Comment> findByArticle(Article article);
}
