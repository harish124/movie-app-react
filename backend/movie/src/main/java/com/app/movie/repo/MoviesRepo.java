package com.app.movie.repo;

import com.app.movie.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoviesRepo extends ElasticsearchRepository<Movie, String> {
    @Query("{" +
            "\"bool\": {" +
            "\"should\": [" +
            // 1. Prefix match (Azhagiya for "azh")
            "{ \"match_phrase_prefix\": { \"movieName\": \"?0\" } }," +
            // 2. Substring match (Mersal for "al")
            "{ \"wildcard\": { \"movieName\": { \"value\": \"*?0*\", \"case_insensitive\": true } } }," +
            // 3. Typo tolerance (Inception for "Incepton")
            "{ \"match\": { \"movieName\": { \"query\": \"?0\", \"fuzziness\": \"AUTO\" } } }" +
            "]" +
            "}" +
            "}")
    Page<Movie> findByMovieNameFuzzy(String name, PageRequest pageRequest);
}
