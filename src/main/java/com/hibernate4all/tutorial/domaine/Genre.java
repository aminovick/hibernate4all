package com.hibernate4all.tutorial.domaine;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Created by Lenovo on 12/04/2022.
 */
@Entity
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    @ManyToMany (mappedBy = "genres")
    @JsonIgnore
    private Set<Movie> movies = new HashSet<>();

    public Long getId() {
        return id;
    }

    public Set<Movie> getMovies() {
        return movies;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Genre setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Genre)) return false;

        Genre genre = (Genre) o;

        return Objects.equals(name, genre.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Genre{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
