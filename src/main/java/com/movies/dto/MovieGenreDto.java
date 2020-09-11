package com.movies.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(description = "Details about a movie genre")
public class MovieGenreDto {
    private Long id;
    private String name;
}
