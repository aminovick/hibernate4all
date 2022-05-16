package com.hibernate4all.tutorial.domaine;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.*;

/**
 * Created by Lenovo on 24/03/2022.
 */
@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String description;
    private Certification certification;
    private String image;
    private  String director ;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "movie_genre", joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))

    private Set<Genre> genres = new HashSet<>();

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)

    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "movie",cascade = CascadeType.ALL,orphanRemoval = true)

   private List<Award> awards = new ArrayList<>();

    public Movie addAward(Award award){
        if (award!=null){
            this.awards.add(award);
            award.setMovie(this);
        }
        return this;
    }
    public Movie removeAward(Award award){
        if ( award!=null){
            this.awards.remove(award);
            award.setMovie(null);
        }
        return this;
    }
    public Movie addGneres(Genre genre) {
        if (genre != null) {
            this.genres.add(genre);
            genre.getMovies().add(this);
        }
        return this;
    }

    public Movie removeGenres(Genre genre) {

        if (genre != null) {
            this.genres.remove(genre);
            genre.getMovies().remove(this);
        }
        return this;
    }

    public Movie addReview(Review review) {
        if (review != null) {
            this.reviews.add(review);
            review.setMovie(this);
        }
        return this;
    }

    public String getImage() {
        return image;
    }

    public String getDirector() {
        return director;
    }

    public Movie setImage(String image) {
        this.image = image;
        return this;
    }

    public Movie setDirector(String director) {
        this.director = director;
        return this;
    }

    public List<Award> getAwards() {
        return Collections.unmodifiableList(awards);
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    public Set<Genre> getGenres() {
        return Collections.unmodifiableSet(genres);
    }

    public Movie removeReview(Review review) {
        if (review != null) {
            this.reviews.remove(review);
            review.setMovie(null);
        }
        return this;
    }

    public List<Review> getReviews() {
        return Collections.unmodifiableList(reviews);
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public Certification getCertification() {
        return certification;
    }

    public Movie setCertification(Certification certification) {
        this.certification = certification;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Movie setDescription(String description) {
        this.description = description;
        return this;
    }

    public Long getId() {
        return id;
    }

    public Movie setName(String name) {
        this.name = name;
        return this;
    }

    public Movie() {
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // if id is finctionelle

    /**
     * @Override public boolean equals(Object o) {
     * if (this == o) return true;
     * if (!(o instanceof Movie)) return false;
     * <p>
     * Movie movie = (Movie) o;
     * <p>
     * <p>
     * return Objects.equals(name,movie.name);
     * }
     * @Override public int hashCode() {
     * return    Objects.hash(name);
     * }
     **/
    @Override
    //id non fonctionelle
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movie)) return false;
        Movie movie = (Movie) o;

        if (id == null && movie.getId() == null) {
            return Objects.equals(name, getName()) && Objects.equals(description, getDescription()) &&
                      Objects.equals(image, getImage())&& Objects.equals(director, getDirector());
        }
        return id != null && Objects.equals(id, movie.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(31);
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", certification=" + certification +
                '}';
    }
}
