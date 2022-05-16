package com.hibernate4all.tutorial.repository;

import com.hibernate4all.tutorial.conf.PersitenceConfig;
import com.hibernate4all.tutorial.domaine.*;
import com.hibernate4all.tutorial.dto.DtoReviews;
import lombok.Builder;
import org.apache.commons.lang3.tuple.Pair;
import org.hibernate.LazyInitializationException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;


/**
 * Created by Lenovo on 24/03/2022.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {PersitenceConfig.class})
@Sql({"/data/data-test.sql"})
@SqlConfig(dataSource = "dataSource", transactionManager = "transactionManager")

public class MovieRepositoryTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MovieRepository.class);
    @Autowired
    MovieRepository movieRepository;

    @Test
    public void persisteMovieDetail() throws Exception {

        MovieDetails movieDetails = new MovieDetails();
        movieDetails.setPlot("movies details");
        movieRepository.persisteMovieDetail(movieDetails, -1L);
        Assert.assertEquals(movieDetails.getPlot(), "movies details");
    }

    @Test
    public void add_rate_toreview() {

     //   Review review1 = new Review().setAuthor("amine").setContent("super Film !").setRating(55);
        Review review1=Review.builder().author("amine").content("good Mouvie").rating(56).build();
        ValidatorFactory validatorFactory= Validation.buildDefaultValidatorFactory();
        Validator validator=validatorFactory.getValidator();
        Set<ConstraintViolation<Review>> validate = validator.validate(review1);

        Assert.assertEquals(validate.size(), 1);
    }
    @Test
    public void add_award(){
        Movie movie = new Movie().setName("titanic").setDescription("film Americaine historique").setCertification(Certification.INTERDIT_MOIN_12_ANS);

        Award award= new Award().setName("Gramy").setYear(2021);
        movie.addAward(award);
        movieRepository.persist(movie);
        Assert.assertEquals(movie.getAwards().size(),1);
    }

    @Test
    public void persist() {
        Movie movie = new Movie();
        movie.setName("titanic").setCertification(Certification.INTERDIT_MOIN_12_ANS);
        movieRepository.persist(movie);
    }

    //test Association
    @Test
    public void persist_assocationSave_ca_nominal() {


        Movie movie = new Movie().setName("titanic").setDescription("film Americaine historique").setCertification(Certification.INTERDIT_MOIN_12_ANS);
        Review review1 =  Review.builder().author("amine").content("bade Movie").build();
        Review review2 = Review.builder().author("amine").content("bade Movie").build();

        movie.addReview(review1);
        movie.addReview(review2);

        movieRepository.persist(movie);

    }

    @Test
    public void persist_assocationGet_ca_nominal() throws Exception {

        try {
            Movie movie = movieRepository.getMovieById(-1L);
            movie.getReviews().size();
        } catch (LazyInitializationException lazy) {
            lazy.getMessage();
        }
    }

    @Test
    public void cas_nominalAssociation_genre_save() {
        Movie movie = new Movie().setName("avatar");
        Genre genre = new Genre().setName("Fictions").setName("Romance");
        movie.addGneres(genre).addGneres(genre);
        movieRepository.persist(movie);
        Assert.assertEquals(movie.getName(), "avatar");
    }

    @Test
    public void cas_nominalAssociation_genre_merge() {
        Movie movie = new Movie().setName("avatar");
        Genre genre = new Genre().setName("Fictions").setName("Romance");
        Genre genre1 = new Genre();
        genre1.setId(-1L);
        movie.addGneres(genre).addGneres(genre1);
        movieRepository.merge_update(movie);
        Assert.assertEquals(movie.getName(), "avatar");
    }


    @Test
    public void allAllMovies() throws Exception {
        List<Movie> movies = movieRepository.allAllMovies();
        Assert.assertEquals(movies.size(), 2);
    }

    @Test
    public void getMovieById() throws Exception {
        Movie movieById = movieRepository.getMovieById(-2L);
        Assert.assertEquals(movieById.getName(), "tintin");
    }

    @Test
    public void merge_update() throws Exception {
        Movie movie = new Movie();
        movie.setName("titanic3");
        movie.setId(-1L);
        movieRepository.merge_update(movie);
        Assert.assertEquals(movie.getName(), "titanic3");
    }

    @Test
    public void delete() {
        movieRepository.delete(-1L);
        List<Movie> movies = movieRepository.allAllMovies();
        Assert.assertEquals(movies.size(), 1);
    }

    // @Test
    public void getReferance() throws Exception {
        Movie movie = movieRepository.getReferance(-2L);
        Assert.assertEquals(movie.getName(), "tintin");
    }
    @Test
    public void getMovieByNameTest (){
        List<Movie> movies= movieRepository.getMovieByName("pyramid");
        Assert.assertEquals((movies.size()),1);
    }
    @Test
    public void getbyCertificationTest(){
        List<Movie> movies= movieRepository.findByCertification("<=",Certification.INTERDIT_MOIN_12_ANS);
        Assert.assertEquals((movies.size()),2);
    }
    @Test
    public void getReviewByMovistest(){
        List<Movie>movies=movieRepository.getMovisWhitReview();
        Optional<Movie> m = movies.stream().filter(o -> o.getName().equals("pyramid")).findFirst();
        Assert.assertEquals((m.get().getReviews().size()),2);
    }
    @Test
    public void getReviewAndAwardByMovistest_produits_catisiene(){
        List<Movie>movies=movieRepository.getMovisWhitReviewandAwards();
        Optional<Movie> m = movies.stream().filter(o -> o.getName().equals("pyramid")).findFirst();
        Assert.assertEquals((m.get().getReviews().size()),2);
        Assert.assertEquals((m.get().getAwards().size()),4);
    }

    @Test
    @Sql({"/data/datas-test-bulk.sql"})
    public void get_movies_pagination(){
        Instant instant= Instant.now();
        List<Movie>movies= movieRepository.allAllMoviesPagine( 50,100);
        Duration duration =Duration.between(instant ,Instant.now());
        LOGGER.info("dureéé{}",duration.toMillis());
        Assert.assertEquals(movies.size(),100);
    }
    @Test
    public void getTopReviewrsTest(){
        List<Pair<String, Long>> topReviewrs = movieRepository.getTopReviewrs();

        Assert.assertEquals(topReviewrs.size(),3);
        Assert.assertEquals(topReviewrs.get(0).getKey(),"max");

    }
    @Test
    public void getTopReviewrsTestWhitDto(){
        List<DtoReviews> topReviewrs = movieRepository.getTopReviewrsWhitDto();

        Assert.assertEquals(topReviewrs.size(),3);


    }

}