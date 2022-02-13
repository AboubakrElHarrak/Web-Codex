package com.codex.machina.ex.homini.entity;

import javax.persistence.*;

@Entity
@Table(name = "rating")
public class Rating
{
    @Id
    @SequenceGenerator(name = "rating_sequence", sequenceName = "rating_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rating_sequence")
    private long ratingId;
    private int rating;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name ="articleId", referencedColumnName = "articleId")
    private Article article;

    public Rating()
    {}
    public Rating(long ratingId, int rating, User user)
    {
        this.ratingId = ratingId;
        this.rating = rating;
        this.user = user;
    }

    public long getRatingId()
    {
        return ratingId;
    }

    public void setRatingId(long ratingId)
    {
        this.ratingId = ratingId;
    }

    public int getRating()
    {
        return rating;
    }

    public void setRating(int rating)
    {
        this.rating = rating;
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
