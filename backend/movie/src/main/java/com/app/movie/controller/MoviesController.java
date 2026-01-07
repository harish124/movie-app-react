package com.app.movie.controller;


import com.app.movie.interfaces.MoviesApi;
import com.app.movie.model.Movie;
import com.app.movie.dto.PaginatedResponse;
import com.app.movie.service.MoviesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/v1/movies")
@RequiredArgsConstructor
@Slf4j
public class MoviesController implements MoviesApi {

    private final MoviesService moviesService;

    private static PaginatedResponse<String> checkPageRequirements(int page, int pageSize, int totalPages) {
        if (page <= 0 || pageSize <= 0) {
            var response = new PaginatedResponse<String>();
            response.setPage(page);
            response.setPageSize(pageSize);
            var warnMsg = "Page/PageSize can't be less than 1";
            log.warn(warnMsg);
            response.setContent(List.of(warnMsg));
            return response;
        }
        if (pageSize > 25) {
            var response = new PaginatedResponse<String>();
            response.setPage(page);
            response.setPageSize(pageSize);
            var warnMsg = "PageSize can't be greater than 25";
            log.warn(warnMsg);
            response.setContent(List.of(warnMsg));
            return response;
        }

        if (page > totalPages) {
            var response = new PaginatedResponse<String>();
            response.setPage(page);
            response.setPageSize(pageSize);
            response.setTotalPages(totalPages);
            var warnMsg = "Page can't be greater than totalPages";
            log.warn(warnMsg);
            response.setContent(List.of(warnMsg));
            return response;
        }
        return null;
    }

    @GetMapping
    public PaginatedResponse<?> getMovies(@RequestParam(value = "characters", defaultValue = "") String characters,
                                          @RequestParam(value = "page", defaultValue = "1") int page,
                                          @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        var response = checkPageRequirements(page, pageSize, Integer.MAX_VALUE);
        if (response != null) return response;

        var moviePage = moviesService.getMovies(characters, page - 1, pageSize);
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteMovie(@PathVariable String id) {
        return moviesService.deleteById(id)
                .map(deleted -> ResponseEntity.noContent().build()) // If deleted, return 204
                .orElseGet(() -> ResponseEntity.notFound().build()); // If not found, return 404
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAllMovies() {
        var deletedCount = moviesService.deleteAll();
        return ResponseEntity.ok("Deleted " + deletedCount + " movies.");
    }


    @PostMapping
    public ResponseEntity<Movie> saveMovie(@RequestBody Movie movie) {
        log.debug("Reached Save Movie");
        var savedMovie = moviesService.saveMovie(movie);


        var location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedMovie.getId())
                .toUri();

        // Return a 201 Created response, with the Location header set to the URI of the created movie
        return ResponseEntity.created(location).body(savedMovie);
    }

    @PutMapping("/{id}")
    public Movie updateMovie(@PathVariable String id, @RequestBody Movie updatedMovie) {
        return moviesService.updateMovie(id, updatedMovie)
                .orElseGet(() -> moviesService.saveMovie(updatedMovie));

    }


}
