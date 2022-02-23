package com.codex.machina.ex.homini.Model;

public class RatingModel
{
    private int rating;
    private String title;

    public RatingModel()
    {}
    public RatingModel(int rating, String title)
    {
        this.rating = rating;
        this.title = title;
    }

    public int getRating()
    {
        return rating;
    }

    public void setRating(int rating)
    {
        this.rating = rating;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }
}
