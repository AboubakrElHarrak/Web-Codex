package com.codex.machina.ex.homini.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codex.machina.ex.homini.entity.Article;
import com.codex.machina.ex.homini.entity.Comment;
import com.codex.machina.ex.homini.service.CommentService;

@RestController
public class CommentController {
	@Autowired
	private CommentService commentService;
	
	@PostMapping(path = "/add-comment")
	public String addNewComment(@RequestBody Comment comment) {
		commentService.addNewComment(comment);
		return "new comment added";
	}
	
	@GetMapping(path="/add-comments")
	  public Iterable<Comment> getArticleComments(@RequestParam Article article) {
	    return commentService.getArticleComments(article);
	  }
}
