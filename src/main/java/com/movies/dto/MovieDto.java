package com.movies.dto;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
@ApiModel(description = "Details about a movie")
public class MovieDto {
    private Long id;
    private String title;
    private Long releaseYear;
    private MovieGenreDto genre;
}
