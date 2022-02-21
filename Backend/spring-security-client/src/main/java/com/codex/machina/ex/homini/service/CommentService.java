package com.codex.machina.ex.homini.service;

import com.codex.machina.ex.homini.entity.Article;
import com.codex.machina.ex.homini.entity.Comment;

public interface CommentService {
	Comment addNewComment(Comment comment);
	Iterable<Comment> getArticleComments(Article article);
}
