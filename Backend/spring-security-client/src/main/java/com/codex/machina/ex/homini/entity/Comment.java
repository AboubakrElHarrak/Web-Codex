package com.codex.machina.ex.homini.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "comment")
public class Comment
{
    @Id
    @SequenceGenerator(name = "comment_sequence", sequenceName = "comment_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_sequence")
    private long commentId;
    private String content;
    private Date post_date;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "articleId", referencedColumnName = "articleId")
    private Article article;

    public Comment()
    {}
    public Comment(long commentId, String content, Date post_date, User user, Article article)
    {
        this.commentId = commentId;
        this.content = content;
        this.post_date = post_date;
        this.user = user;
        this.article = article;
    }

    public Article getArticle() {
		return article;
	}
	public void setArticle(Article article) {
		this.article = article;
	}
	public long getCommentId()
    {
        return commentId;
    }

    public void setCommentId(long commentId)
    {
        this.commentId = commentId;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public Date getPost_date()
    {
        return post_date;
    }

    public void setPost_date(Date post_date)
    {
        this.post_date = post_date;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

}
