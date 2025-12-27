package com.app.movie.repo.dto;

import lombok.Data;

import java.util.List;

@Data
public class PaginatedResponse<T> {
    private int page;
    private int pageSize;
    private int totalPages;
    private List<T> content;
    private long totalResults;
}
