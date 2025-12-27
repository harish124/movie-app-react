package com.app.movie.service;


import com.app.movie.model.Movie;
import com.app.movie.repo.MoviesRepo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MoviesService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MoviesService.class);
    private final MoviesRepo moviesRepo;

    public Page<Movie> getMovies(int page, int pageSize) {
        var pageRequest = PageRequest.of(page, pageSize);
        return moviesRepo.findAll(pageRequest);
    }

    public Page<Movie> getMoviesContainingChars(final String characters, int page, int pageSize) {
        var pageRequest = PageRequest.of(page, pageSize);
        return moviesRepo.findByMovieNameFuzzy(characters, pageRequest);
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
