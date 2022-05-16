package com.hibernate4all.tutorial.service;

import com.hibernate4all.tutorial.domaine.Movie;
import com.hibernate4all.tutorial.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by Lenovo on 24/03/2022.
 */
@Service

public class ServiveMovies {
   @Autowired
    MovieRepository movieRepository;

    @Transactional
    public void updateMovie(Long id, String description){
       Movie movie= movieRepository.getMovieById(id);
       movie.setDescription(description);


    }
}
