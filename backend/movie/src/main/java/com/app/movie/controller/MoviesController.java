package com.app.movie.controller;


import com.app.movie.model.Movie;
import com.app.movie.repo.dto.PaginatedResponse;
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

    private static PaginatedResponse<String> checkPageRequirements(int page, int pageSize, int totalPages) {
        if (page <= 0 || pageSize <= 0) {
            var response = new PaginatedResponse<String>();
            response.setPage(page);
            response.setPageSize(pageSize);
            var errorMsg = "Page/PageSize can't be less than 1";
            response.setContent(List.of(errorMsg));
            return response;
        }
        if (pageSize > 25) {
            var response = new PaginatedResponse<String>();
            response.setPage(page);
            response.setPageSize(pageSize);
            var errorMsg = "PageSize can't be greater than 25";
            response.setContent(List.of(errorMsg));
            return response;
        }

        if (page > totalPages) {
            var response = new PaginatedResponse<String>();
            response.setPage(page);
            response.setPageSize(pageSize);
            response.setTotalPages(totalPages);
            var errorMsg = "Page can't be greater than totalPages";
            response.setContent(List.of(errorMsg));
            return response;
        }
        return null;
    }

    @RequestMapping("/getMovies")
    public PaginatedResponse<?> getMovies(@RequestParam(value = "page", defaultValue = "1") int page,
                                          @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        var response = checkPageRequirements(page, pageSize, Integer.MAX_VALUE);
        if (response != null) return response;

        var moviePage = moviesService.getMovies(page - 1, pageSize);
        response = checkPageRequirements(page, pageSize, moviePage.getTotalPages());
        if (response != null) return response;

        var moviePaginatedResponse = new PaginatedResponse<Movie>();
        moviePaginatedResponse.setContent(moviePage.getContent());
        moviePaginatedResponse.setPage(moviePage.getNumber() + 1);
        moviePaginatedResponse.setPageSize(moviePage.getSize());
        moviePaginatedResponse.setTotalPages(moviePage.getTotalPages());
        moviePaginatedResponse.setTotalResults(moviePage.getTotalElements());
        return moviePaginatedResponse;
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
    public PaginatedResponse<?> getMovies(@RequestParam String characters,
                                          @RequestParam(value = "page", defaultValue = "1") int page,
                                          @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

        var response = checkPageRequirements(page, pageSize, Integer.MAX_VALUE);
        if (response != null) return response;

        var moviePage = moviesService.getMoviesContainingChars(characters, page - 1, pageSize);
        response = checkPageRequirements(page, pageSize, moviePage.getTotalPages());
        if (response != null) return response;

        var moviePaginatedResponse = new PaginatedResponse<Movie>();
        moviePaginatedResponse.setContent(moviePage.getContent());
        moviePaginatedResponse.setPage(moviePage.getNumber() + 1);
        moviePaginatedResponse.setPageSize(moviePage.getSize());
        moviePaginatedResponse.setTotalPages(moviePage.getTotalPages());
        moviePaginatedResponse.setTotalResults(moviePage.getTotalElements());
        return moviePaginatedResponse;
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
