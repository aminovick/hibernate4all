package com.hibernate4all.tutorial.domaine;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Objects;

/**
 * Created by Lenovo on 12/04/2022.
 */
@Entity
@Builder
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String author;
    private String content;
    @Min(value = 0)
    @Max(value = 10)
    private Integer rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")

    private Movie movie;


    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Long getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Review setAuthor(String author) {
        this.author = author;
        return this;
    }

    public Review setContent(String content) {
        this.content = content;
        return this;
    }

    public Integer getRating() {
        return rating;
    }

    public Review setRating(Integer rating) {
        this.rating = rating;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Review)) return false;
        Review review = (Review) o;

        if (id == null && review.getId() == null) {
            return Objects.equals(author, getAuthor()) && Objects.equals(content, getContent())
                    && Objects.equals(rating, getRating());
        }
        return id != null && Objects.equals(id, review.getId());
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
