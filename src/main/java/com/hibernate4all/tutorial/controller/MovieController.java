package com.hibernate4all.tutorial.controller;

import com.hibernate4all.tutorial.domaine.Movie;
import com.hibernate4all.tutorial.domaine.MovieDetails;
import com.hibernate4all.tutorial.dto.MovieDto;
import com.hibernate4all.tutorial.repository.MovieRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Lenovo on 25/03/2022.
 */
@RestController
@RequestMapping("/v1/api/movie")
public class MovieController {

    MovieRepository movieRepository;

    public MovieController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @PostMapping
    public Movie addMovie(@RequestBody Movie movie) {

        movieRepository.persist(movie);
        return movie;
    }

    @GetMapping("/search")
    public ResponseEntity<List<MovieDto>> searchMovies(@ModelAttribute("text") String txt) {
        List<Movie> movieList = movieRepository.searchMovie(txt);
        List<MovieDto> movieDtoList = new ArrayList<>();
        moviTodto(movieList, movieDtoList);

        if (movieDtoList == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(movieDtoList);
    }


    @GetMapping
    public ResponseEntity<List<MovieDto>> getMovies() {
        List<Movie> movieList = movieRepository.allAllMovies();
        List<MovieDto> movieDtoList = new ArrayList<>();
        moviTodto(movieList, movieDtoList);

        if (movieDtoList == null) {
            return ResponseEntity.notFound().build();
        }
            return ResponseEntity.ok(movieDtoList);
        }

    @GetMapping("/test")
    public ResponseEntity<List<Movie>> Movies() {
        List<Movie> movieList = movieRepository.getMovisWhitReviewandAwards();



        return ResponseEntity.ok(movieList);
    }
    private void moviTodto(List<Movie> movieList, List<MovieDto> movieDtoList) {
        movieList.forEach(m -> movieDtoList.add(
                new MovieDto().setImage(m.getImage()).
                        setDescription(m.getDescription())
                        .setCertification(m.getCertification())
                        .setName(m.getName())
                        .setId(m.getId())));
    }


    @GetMapping("/{id}")
    public ResponseEntity<MovieDetails> getMovieById(@PathVariable Long id) {
        ResponseEntity<MovieDetails> result;
        try {
            MovieDetails movieById = movieRepository.getMovieDetailsById(id);
            if (movieById == null) {
                result=ResponseEntity.notFound().build();
            } else {
                result=ResponseEntity.ok(movieById);
            }
        }catch (EmptyResultDataAccessException er){
            result=ResponseEntity.notFound().build();
        }
       return  result;
    }

    @PutMapping
    public ResponseEntity<Movie> updateMovie(@RequestBody Movie movie) {

        Optional<Movie> update = movieRepository.update(movie);
        if (update == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(update.get());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Movie> deleteMovie(@PathVariable Long id) {
        return movieRepository.delete(id) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
