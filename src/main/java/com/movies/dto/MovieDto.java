package com.movies.dto;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

@NoArgsConstructor
@Getter
@Setter
@ToString
@ApiModel(description = "Details about a movie")
public class MovieDto {
    private Long id;
    private String title;
    @Range(min = 1800, max = 2024)
    private Long releaseYear;
    private MovieGenreDto genre;
}
