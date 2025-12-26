package com.app.movie.service;


import com.app.movie.model.Movie;
import com.app.movie.repo.MoviesRepo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class MoviesService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MoviesService.class);
    private final MoviesRepo moviesRepo;

    public List<Movie> getMovies() {
        var moviesIterable = moviesRepo.findAll();
        return StreamSupport.stream(moviesIterable.spliterator(), false)
                .toList();
    }

    public List<Movie> getMoviesContainingChars(final String characters) {
        return moviesRepo.findByMovieNameFuzzy(characters);
    }

    public Movie saveMovie(Movie movie) {
        LOGGER.debug("Reached save movie service");
        return moviesRepo.save(movie);
    }

    public void deleteById(String id) {
        moviesRepo.deleteById(id);
    }


    public void deleteAll() {
        moviesRepo.deleteAll();
    }

    public Optional<Movie> findById(String id) {
        return moviesRepo.findById(id);
    }
}
