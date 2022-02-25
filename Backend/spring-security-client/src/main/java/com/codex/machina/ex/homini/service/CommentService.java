package com.codex.machina.ex.homini.service;

import com.codex.machina.ex.homini.Model.CommentModel;
import com.codex.machina.ex.homini.entity.Article;
import com.codex.machina.ex.homini.entity.Comment;

public interface CommentService {
	Comment addNewComment(String title, CommentModel comment);
	Iterable<Comment> getArticleComments(String title);
}
