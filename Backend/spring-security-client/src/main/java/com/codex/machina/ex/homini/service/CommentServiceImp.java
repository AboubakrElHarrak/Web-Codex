package com.codex.machina.ex.homini.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.codex.machina.ex.homini.entity.Article;
import com.codex.machina.ex.homini.entity.Comment;
import com.codex.machina.ex.homini.repository.CommentRepository;

public class CommentServiceImp implements CommentService {
	@Autowired
	CommentRepository commentRepository;
	
	@Override
	public Comment addNewComment(Comment comment) {
		Comment c = new Comment();
		c.setArticle(comment.getArticle());
		c.setContent(comment.getContent());
		c.setPost_date(comment.getPost_date());
		c.setUser(comment.getUser());
	    commentRepository.save(c);
		return c;
	}
	
	@Override
	public Iterable<Comment> getArticleComments(Article article) {
		return commentRepository.findByArticle(article);
	}
}
