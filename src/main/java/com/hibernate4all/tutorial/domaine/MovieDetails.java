package com.hibernate4all.tutorial.domaine;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.util.Objects;

/**
 * Created by Lenovo on 12/04/2022.
 */
@Table(name = "movie_details")
@Entity
public class MovieDetails {
    @Id
    private Long id;

    private String plot;

    @OneToOne
    @MapsId

    private Movie movie;

    public Movie getMovie() {
        return movie;
    }

    public Long getId() {
        return id;
    }

    public String getPlot() {
        return plot;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MovieDetails)) return false;
        MovieDetails movie = (MovieDetails) o;

        if (id == null && movie.getId() == null) {
            return Objects.equals(plot, getPlot()) ;
        }
        return id != null && Objects.equals(id, movie.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(51);
    }
}
