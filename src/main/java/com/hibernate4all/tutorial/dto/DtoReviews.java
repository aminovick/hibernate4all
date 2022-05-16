package com.hibernate4all.tutorial.dto;

/**
 * Created by Lenovo on 15/04/2022.
 */
public class DtoReviews {
    private long count;
    private String author;

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public DtoReviews(long count, String author) {
        this.count = count;
        this.author = author;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
