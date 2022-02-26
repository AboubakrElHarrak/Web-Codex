package com.codex.machina.ex.homini.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

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
	public Comment addNewComment(String title, CommentModel comment, String username) {
		Comment c = new Comment();
		Article article = articleRepository.findByTitle(title);
		c.setArticle(article);
		c.setContent(comment.getContent());
		c.setPost_date(new Date());
		User user = userRepository.findByUsername(username);
		c.setUser(user);
	    commentRepository.save(c);
		return c;
	}
	
	public void likeComment(Long id) {
		Comment c = commentRepository.findById(id).get();
		c.incLikes(1);
		commentRepository.save(c);
	}
	
	public void dislikeComment(Long id) {
		Comment c = commentRepository.findById(id).get();
		c.incDislikes(1);
		commentRepository.save(c);
	}
	
	@Override
	public Iterable<Comment> getArticleComments(String title, Long nb) {
		Article article = articleRepository.findByTitle(title);
		ArrayList<Comment> commentList = commentRepository.findByArticle(article);
		ArrayList<Comment> commentList_ = new ArrayList<Comment>();
		for (int i = 0; i < nb; i++) {
			if (commentList.iterator().hasNext()) {
				Comment cc = commentList.iterator().next();
				commentList_.add(cc);
				commentList.remove(0);
			}
		}
		return commentList_;
	}
	@Override
	public List<Comment> fetchComments(){
		return commentRepository.findAll();
	}
}
