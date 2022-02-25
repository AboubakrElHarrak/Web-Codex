package com.codex.machina.ex.homini.service;

import java.util.Date;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codex.machina.ex.homini.Model.CommentModel;
import com.codex.machina.ex.homini.entity.Article;
import com.codex.machina.ex.homini.entity.Comment;
import com.codex.machina.ex.homini.entity.User;
import com.codex.machina.ex.homini.repository.ArticleRepository;
import com.codex.machina.ex.homini.repository.CommentRepository;
import com.codex.machina.ex.homini.repository.UserRepository;

@Service
public class CommentServiceImp implements CommentService {
	@Autowired
	CommentRepository commentRepository;
	@Autowired
	ArticleRepository articleRepository;
	@Autowired
	UserRepository userRepository;
	@Override
	public Comment addNewComment(String title, CommentModel comment) {
		Comment c = new Comment();
		Article article = articleRepository.findByTitle(title).get();
		c.setArticle(article);
		c.setContent(comment.getContent());
		c.setPost_date(new Date());
		Long userId = comment.getUserId();
		User user = userRepository.findById(userId).get();
		c.setUser(user);
	    commentRepository.save(c);
		return c;
	}
	
	@Override
	public Iterable<Comment> getArticleComments(String title) {
		Article article = articleRepository.findByTitle(title).get();
		return commentRepository.findByArticle(article);
	}
}
