package com.app.movie.controller;


import com.app.movie.model.Movie;
import com.app.movie.service.MoviesService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/v1/movies-data")
@RequiredArgsConstructor
public class MoviesController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MoviesController.class);

    private final MoviesService moviesService;

    @RequestMapping("/getMovies")
    public List<Movie> getMovies() {
        return moviesService.getMovies();
    }

    @DeleteMapping("/delete/{id}")
    public void deleteMovie(@PathVariable String id) {
        moviesService.deleteById(id); // Deletes the movie by ID
    }

    @DeleteMapping("/deleteAll")
    public void deleteAllMovies() {
        moviesService.deleteAll();  // Deletes all documents from the index
    }


    @RequestMapping("/getMoviesContainingChars")
    public List<Movie> getMovies(@RequestParam String characters) {
        return moviesService.getMoviesContainingChars(characters);
    }

    @PostMapping("/saveMovie")
    public ResponseEntity<Movie> saveMovie(@RequestBody Movie movie) {
        LOGGER.debug("Reached Save Movie");
        var savedMovie = moviesService.saveMovie(movie);
        return ResponseEntity.ok(savedMovie);
    }

    @PutMapping("/update/{id}")
    public Movie updateMovie(@PathVariable String id, @RequestBody Movie updatedMovie) {
        return moviesService.findById(id)
                .map(existingMovie -> {
            existingMovie.setMovieName(updatedMovie.getMovieName());
            existingMovie.setVoteAvg(updatedMovie.getVoteAvg());
            existingMovie.setReleaseDate(updatedMovie.getReleaseDate());
            existingMovie.setOgLanguage(updatedMovie.getOgLanguage());
            existingMovie.setPosterPath(updatedMovie.getPosterPath());

            return moviesService.saveMovie(existingMovie);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found"));
    }


}
