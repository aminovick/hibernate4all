package com.hibernate4all.tutorial.domaine;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;


/**
 * Created by Lenovo on 13/04/2022.
 */
@Entity
public class Award {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private int year;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    @JsonIgnore
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

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public void setId(Long id) {
        this.id = id;

    }

    public Award setName(String name) {
        this.name = name;
        return this;
    }

    public Award setYear(int year) {
        this.year = year;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movie)) return false;
        Award award = (Award) o;

        if (id == null && award.getId() == null) {
            return Objects.equals(name, getName()) && Objects.equals(year, getYear());
        }
        return id != null && Objects.equals(id, award.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(51);
    }

    @Override
    public String toString() {
        return "Award{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", year=" + year +
                ", movie=" + movie +
                '}';
    }
}
