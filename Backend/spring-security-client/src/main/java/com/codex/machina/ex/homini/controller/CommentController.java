package com.codex.machina.ex.homini.controller;

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
	
	@PostMapping(path = "/articles/add-comment/{title}")
	public String addNewComment(@PathVariable("title") String title, @RequestBody CommentModel comment) {
		commentService.addNewComment(title, comment);
		return "new comment added";
	}
	
	@GetMapping(path="/articles/{title}/get-comments")
	  public Iterable<Comment> getArticleComments(@PathVariable("title") String title) {
	    return commentService.getArticleComments(title);
	  }
}
