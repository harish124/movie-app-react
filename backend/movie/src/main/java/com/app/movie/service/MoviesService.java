package com.app.movie.service;


import com.app.movie.model.Movie;
import com.app.movie.repo.MoviesRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MoviesService {
    private final MoviesRepo moviesRepo;

    public Page<Movie> getMovies(String characters, int page, int pageSize) {
        var pageRequest = PageRequest.of(page, pageSize);

        if (characters != null && !characters.isEmpty()) {
            return moviesRepo.findByMovieNameFuzzy(characters, pageRequest);
        } else {
            return moviesRepo.findAll(pageRequest);
        }
    }

    public Movie saveMovie(Movie movie) {
        log.debug("Reached save movie service");
        return moviesRepo.save(movie);
    }

    public Optional<Boolean> deleteById(String id) {
        return moviesRepo.findById(id)
                .map(movie -> {
                    moviesRepo.deleteById(id); // Delete the movie if it exists
                    return true; // Return true to indicate success
                });
    }


    public long deleteAll() {
        long countBeforeDelete = moviesRepo.count();
        moviesRepo.deleteAll();
        return countBeforeDelete;
    }

    public Optional<Movie> findById(String id) {
        return moviesRepo.findById(id);
    }

    public Optional<Movie> updateMovie(String id, Movie updatedMovie) {
        return moviesRepo.findById(id)
                .map(existingMovie -> {
                    existingMovie.setMovieName(updatedMovie.getMovieName());
                    existingMovie.setVoteAvg(updatedMovie.getVoteAvg());
                    existingMovie.setReleaseDate(updatedMovie.getReleaseDate());
                    existingMovie.setOgLanguage(updatedMovie.getOgLanguage());
                    existingMovie.setPosterPath(updatedMovie.getPosterPath());

                    return moviesRepo.save(existingMovie);
                });
    }
}
