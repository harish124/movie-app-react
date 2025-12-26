package com.app.movie.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDate;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "movies")
public class Movie {
    @Id
    private String id;

    // Use FieldType.Text for full-text search (searching by name)
    @Field(type = FieldType.Text, name = "movieName")
    private String movieName;

    @Field(type = FieldType.Text, name = "ogLanguage")
    private String ogLanguage;

    // IMPORTANT: Elasticsearch needs a pattern to parse LocalDateTime
    @Field(type = FieldType.Date, format = DateFormat.year_month_day)
    private LocalDate releaseDate;

    @Field(type = FieldType.Double)
    private double voteAvg;

    // Use FieldType.Keyword for strings that don't need "searching" (just storage/URLs)
    @Field(type = FieldType.Keyword)
    private String posterPath;
}
