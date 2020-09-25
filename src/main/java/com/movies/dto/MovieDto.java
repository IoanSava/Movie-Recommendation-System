package com.movies.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
@ApiModel(description = "Details about a movie")
public class MovieDto {
    private String title;

    @Range(min = 1880, max = 2025)
    private Long releaseYear;

    private MovieGenreDto genre;
}
