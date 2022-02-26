package com.codex.machina.ex.homini.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codex.machina.ex.homini.Model.CommentModel;
import com.codex.machina.ex.homini.entity.Article;
import com.codex.machina.ex.homini.entity.Comment;
import com.codex.machina.ex.homini.service.CommentService;
import com.codex.machina.ex.homini.service.CommentServiceImp;

@RestController
public class CommentController {
	@Autowired
	private CommentService commentService;
	
	@PostMapping(path = "/api/add-comment/{title}")
	public String addNewComment(@PathVariable("title") String title, @RequestBody CommentModel comment, Principal principal) {
		String username = principal.getName();
		commentService.addNewComment(title, comment, username);
		return "New comment added";
	}
	
	@PostMapping(path = "/like-comment/{id}")
	public String likeComment(@PathVariable("id") Long id) {
		commentService.likeComment(id);
		return "Comment liked";
	}
	
	@PostMapping(path = "/dislike-comment/{id}")
	public String dislikeComment(@PathVariable("id") Long id) {
		commentService.dislikeComment(id);
		return "Comment disliked";
	}
	
	@GetMapping(path="/articles/{title}/get-comments/{nb}")
	  public Iterable<Comment> getArticleComments(@PathVariable("title") String title, @PathVariable("nb") Long nb) {
	    return commentService.getArticleComments(title, nb);
	  }
}
