package com.app.movie.service;

import com.app.movie.dto.PaginatedResponse;
import com.app.movie.model.Movie;
import com.app.movie.repo.MoviesRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MoviesServiceTest {

    @Mock
    private MoviesRepo moviesRepo;

    @InjectMocks
    private MoviesService moviesService;

    private Movie sampleMovie;

    @BeforeEach
    void setUp() {
        sampleMovie = Movie.builder()
                .id("1")
                .movieName("Inception")
                .ogLanguage("English")
                .releaseDate(LocalDate.of(2010, 7, 16))
                .voteAvg(8.8)
                .posterPath("/inception.jpg")
                .build();
    }

    @Test
    void getMovies_withCharacters() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Movie> moviePage = new PageImpl<>(List.of(sampleMovie), pageRequest, 1);

        when(moviesRepo.findByMovieNameFuzzy(eq("Incep"), eq(pageRequest))).thenReturn(moviePage);

        PaginatedResponse<Movie> response = moviesService.getMovies("Incep", 0, 10);

        assertNotNull(response);
        assertEquals(1, response.getContent().size());
        assertEquals("Inception", response.getContent().get(0).getMovieName());
        assertEquals(1, response.getPage());
        assertEquals(10, response.getPageSize());
        assertEquals(1, response.getTotalPages());
        assertEquals(1, response.getTotalResults());

        verify(moviesRepo).findByMovieNameFuzzy(eq("Incep"), eq(pageRequest));
        verify(moviesRepo, never()).findAll(any(PageRequest.class));
    }

    @Test
    void getMovies_withoutCharacters() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Movie> moviePage = new PageImpl<>(List.of(sampleMovie), pageRequest, 1);

        when(moviesRepo.findAll(eq(pageRequest))).thenReturn(moviePage);

        PaginatedResponse<Movie> response = moviesService.getMovies(null, 0, 10);

        assertNotNull(response);
        assertEquals(1, response.getContent().size());
        assertEquals(1, response.getPage());
        assertEquals(10, response.getPageSize());
        assertEquals(1, response.getTotalPages());

        verify(moviesRepo).findAll(eq(pageRequest));
        verify(moviesRepo, never()).findByMovieNameFuzzy(anyString(), any(PageRequest.class));
    }

    @Test
    void getMovies_withEmptyCharacters() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Movie> moviePage = new PageImpl<>(List.of(sampleMovie), pageRequest, 1);

        when(moviesRepo.findAll(eq(pageRequest))).thenReturn(moviePage);

        PaginatedResponse<Movie> response = moviesService.getMovies("", 0, 10);

        assertNotNull(response);
        verify(moviesRepo).findAll(eq(pageRequest));
        verify(moviesRepo, never()).findByMovieNameFuzzy(anyString(), any(PageRequest.class));
    }

    @Test
    void saveMovie() {
        when(moviesRepo.save(any(Movie.class))).thenReturn(sampleMovie);

        Movie savedMovie = moviesService.saveMovie(sampleMovie);

        assertNotNull(savedMovie);
        assertEquals("Inception", savedMovie.getMovieName());
        verify(moviesRepo).save(sampleMovie);
    }

    @Test
    void deleteById_existingMovie() {
        when(moviesRepo.findById("1")).thenReturn(Optional.of(sampleMovie));

        Optional<Boolean> result = moviesService.deleteById("1");

        assertTrue(result.isPresent());
        assertTrue(result.get());
        verify(moviesRepo).findById("1");
        verify(moviesRepo).deleteById("1");
    }

    @Test
    void deleteById_nonExistingMovie() {
        when(moviesRepo.findById("2")).thenReturn(Optional.empty());

        Optional<Boolean> result = moviesService.deleteById("2");

        assertFalse(result.isPresent());
        verify(moviesRepo).findById("2");
        verify(moviesRepo, never()).deleteById(anyString());
    }

    @Test
    void deleteAll() {
        when(moviesRepo.count()).thenReturn(5L);

        long count = moviesService.deleteAll();

        assertEquals(5L, count);
        verify(moviesRepo).count();
        verify(moviesRepo).deleteAll();
    }

    @Test
    void findById_existing() {
        when(moviesRepo.findById("1")).thenReturn(Optional.of(sampleMovie));

        Optional<Movie> result = moviesService.findById("1");

        assertTrue(result.isPresent());
        assertEquals("Inception", result.get().getMovieName());
        verify(moviesRepo).findById("1");
    }

    @Test
    void findById_nonExisting() {
        when(moviesRepo.findById("2")).thenReturn(Optional.empty());

        Optional<Movie> result = moviesService.findById("2");

        assertFalse(result.isPresent());
        verify(moviesRepo).findById("2");
    }

    @Test
    void updateMovie_existing() {
        Movie updatedData = Movie.builder()
                .movieName("Inception 2")
                .voteAvg(9.0)
                .releaseDate(LocalDate.of(2025, 1, 1))
                .ogLanguage("English")
                .posterPath("/inception2.jpg")
                .build();

        when(moviesRepo.findById("1")).thenReturn(Optional.of(sampleMovie));
        when(moviesRepo.save(any(Movie.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Optional<Movie> result = moviesService.updateMovie("1", updatedData);

        assertTrue(result.isPresent());
        Movie updatedMovie = result.get();
        assertEquals("Inception 2", updatedMovie.getMovieName());
        assertEquals(9.0, updatedMovie.getVoteAvg());
        assertEquals(LocalDate.of(2025, 1, 1), updatedMovie.getReleaseDate());
        assertEquals("English", updatedMovie.getOgLanguage());
        assertEquals("/inception2.jpg", updatedMovie.getPosterPath());

        verify(moviesRepo).findById("1");
        verify(moviesRepo).save(any(Movie.class));
    }

    @Test
    void updateMovie_nonExisting() {
        when(moviesRepo.findById("2")).thenReturn(Optional.empty());

        Optional<Movie> result = moviesService.updateMovie("2", sampleMovie);

        assertFalse(result.isPresent());
        verify(moviesRepo).findById("2");
        verify(moviesRepo, never()).save(any(Movie.class));
    }
}
