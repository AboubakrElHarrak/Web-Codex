package com.codex.machina.ex.homini.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "article")
public class Article
{
    @Id
    private long articleId;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false, columnDefinition = "default 0")
    private long views;

    public Article()
    {}
    public Article(long articleId, String title, long views)
    {
        this.articleId = articleId;
        this.title = title;
        this.views = views;
    }

    public long getArticleId()
    {
        return articleId;
    }

    public void setArticleId(long articleId)
    {
        this.articleId = articleId;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public long getViews()
    {
        return views;
    }

    public void setViews(long views)
    {
        this.views = views;
    }
}
