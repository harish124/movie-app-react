package com.app.movie.interfaces;

import com.app.movie.dto.PaginatedResponse;
import com.app.movie.model.Movie;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface MoviesApi {

    // Operation for fetching movies with pagination and character search
    @Operation(summary = "Get movies", description = "Retrieve a paginated list of movies based on characters search and pagination parameters.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movies fetched successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid pagination parameters")
    })
    PaginatedResponse<?> getMovies(
            @RequestParam(value = "characters", defaultValue = "") String characters,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize
    );

    // Operation for deleting a movie by its ID
    @Operation(summary = "Delete a movie by ID", description = "Delete a movie from the database by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Movie not found")
    })
    ResponseEntity<Object> deleteMovie(@PathVariable String id);

    // Operation for deleting all movies
    @Operation(summary = "Delete all movies", description = "Delete all movies from the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All movies deleted successfully")
    })
    ResponseEntity<String> deleteAllMovies();

    // Operation for saving a new movie
    @Operation(summary = "Create a new movie", description = "Add a new movie to the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Movie created successfully")
    })
    ResponseEntity<Movie> saveMovie(@RequestBody Movie movie);

    // Operation for updating an existing movie
    @Operation(summary = "Update an existing movie", description = "Update the details of an existing movie or create it if not found (upsert).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie updated successfully")
    })
    Movie updateMovie(@PathVariable String id, @RequestBody Movie updatedMovie);
}
