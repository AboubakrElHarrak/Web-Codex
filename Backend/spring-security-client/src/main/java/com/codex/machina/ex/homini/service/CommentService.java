package com.codex.machina.ex.homini.service;

import com.codex.machina.ex.homini.Model.CommentModel;
import com.codex.machina.ex.homini.entity.Article;
import com.codex.machina.ex.homini.entity.Comment;

public interface CommentService {
	Comment addNewComment(String title, CommentModel comment, String username);
	Iterable<Comment> getArticleComments(String title, Long nb);
	void likeComment(Long id);
	void dislikeComment(Long id);
}
