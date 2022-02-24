package com.codex.machina.ex.homini.Model;

import java.util.Date;

import com.codex.machina.ex.homini.entity.User;

public class CommentModel {
	private String content;
    private Long userId;
    
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
}
