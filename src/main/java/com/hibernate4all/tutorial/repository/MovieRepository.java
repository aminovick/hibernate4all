package com.hibernate4all.tutorial.repository;

import com.hibernate4all.tutorial.domaine.Certification;
import com.hibernate4all.tutorial.domaine.Movie;
import com.hibernate4all.tutorial.domaine.MovieDetails;
import com.hibernate4all.tutorial.dto.DtoReviews;
import org.apache.commons.lang3.tuple.Pair;
import org.hibernate.jpa.QueryHints;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.QueryHint;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Created by Lenovo on 24/03/2022.
 */
@Repository
public class MovieRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(MovieRepository.class);
    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public void persist(Movie movie) {
        LOGGER.trace("Entity manger.containe()" + entityManager.contains(movie));
        entityManager.persist(movie);
        LOGGER.trace("Entity manger.containe()" + entityManager.contains(movie));

    }

    public List<Movie> allAllMovies() {
        return entityManager.createQuery("from Movie", Movie.class).getResultList();
    }

    public List<Movie> allAllMoviesPagine(int start, int maxResult) {
        return entityManager.createQuery("select m from Movie m order by m.name", Movie.class).setFirstResult(start).setMaxResults(maxResult).getResultList();
    }

    @Transactional
    public Movie getMovieById(Long id) {
        Movie move = entityManager.find(Movie.class, id);
        LOGGER.trace("entityManager.contain()" + entityManager.contains(move));
        return move;
    }

    @Transactional
    public MovieDetails getMovieDetailsById(Long id) {

        MovieDetails movie = entityManager.createQuery("select distinct md from MovieDetails md " +
                "join fetch md.movie m "
                + "left join fetch m.reviews "
                + "left join fetch m.genres "
                +" where md.id = :id ", MovieDetails.class).setParameter("id",id)
                .setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false).getSingleResult();
        entityManager.createQuery("select distinct m from Movie m left join  fetch m.awards where m in(:movies)", Movie.class)
                .setParameter("movies", movie.getMovie())
                .setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false).getResultList();

        return movie;
    }

    @Transactional
    public Movie merge_update(Movie movie) {

        return entityManager.merge(movie);
    }

    @Transactional
    public void persisteMovieDetail(MovieDetails movieDetails, Long idMovie) {
        Movie movie = getReferance(idMovie);
        movieDetails.setMovie(movie);
        entityManager.persist(movieDetails);
    }

    @Transactional
    public boolean delete(Long id) {
        boolean result = false;
        if (id != null) {
            Movie movie = entityManager.find(Movie.class, id);
            entityManager.remove(movie);
            result = true;
        }
        return result;
    }

    @Transactional
    public Movie getReferance(Long id) {
        return entityManager.getReference(Movie.class, id);
    }

    @Transactional
    public Optional<Movie> update(Movie movie) {
        if (movie == null) {
            return Optional.empty();
        } else {
            Movie movie1 = entityManager.find(Movie.class, movie.getId());
            movie1.setName(movie.getName());
            movie1.setDescription(movie.getDescription());
            return Optional.ofNullable(movie1);
        }
    }

    public List<Movie> getMovieByName(String name) {
        return entityManager.createQuery("select m from Movie m where m.name = :param", Movie.class).
                setParameter("param", name).getResultList();
    }


    public List<Movie> findByCertification(String operator, Certification certification) {
        CriteriaBuilder build = entityManager.getCriteriaBuilder();
        CriteriaQuery<Movie> query = build.createQuery(Movie.class);
        Root<Movie> root = query.from(Movie.class);
        Predicate predicat = null;
        if (operator.equals("<")) {
            predicat = build.lessThan(root.get("certification"), certification);
        } else if (operator.equals("<=")) {
            predicat = build.lessThanOrEqualTo(root.get("certification"), certification);
        } else if (operator.equals(">")) {
            predicat = build.greaterThan(root.get("certification"), certification);
        } else if (operator.equals(">=")) {
            predicat = build.greaterThanOrEqualTo(root.get("certification"), certification);
        } else if (operator.equals("=")) {
            predicat = build.equal(root.get("certification"), certification);
        } else {
            throw new IllegalArgumentException("l'operarator n'exisite pas " + operator);
        }

        query.where(predicat);

        return entityManager.createQuery(query).getResultList();

    }

    public List<Movie> getMovisWhitReview() {

        return entityManager.createQuery("select distinct m from Movie m left JOIN  fetch m.reviews left join fetch m.genres", Movie.class)
                .setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false).getResultList();
    }

    @Transactional
    public List<Movie> getMovisWhitReviewandAwards() {

        List<Movie> movieReview = entityManager.createQuery("select distinct m from Movie m left JOIN  fetch m.reviews", Movie.class)
                .setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false).getResultList();

        return entityManager.createQuery("select distinct m from Movie m left JOIN  fetch m.awards where m in(:movies)", Movie.class)
                .setParameter("movies", movieReview)
                .setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false).getResultList();
    }

    public List<Movie> searchMovie(String txt) {
        return entityManager.createQuery("select m from Movie m where upper(m.name) like '%'|| :txt || '%' " +
        "or upper(m.description) like  '%'|| :txt || '%' " ,Movie.class).setParameter("txt",txt.toUpperCase()).getResultList();
    }

    public  List<Pair<String,Long>> getTopReviewrs(){
        List<Pair<String,Long>>result= new ArrayList<>();
        List<Tuple> resultList = entityManager.createQuery("select count(r),r.author from Review r group by r.author order by 1 DESC", Tuple.class).getResultList();
        resultList.forEach(t->result.add(Pair.of((String )t.get(1),(Long) t.get(0))));
        return  result;
    }
    // exemple mapping whit dto
    public  List<DtoReviews> getTopReviewrsWhitDto(){

   List<DtoReviews> resultList=  entityManager.createQuery("select new com.hibernate4all.tutorial.dto.DtoReviews(count(*),r.author) from Review r group by r.author order by 1 DESC", DtoReviews.class).getResultList();
     return  resultList;
    }
}
