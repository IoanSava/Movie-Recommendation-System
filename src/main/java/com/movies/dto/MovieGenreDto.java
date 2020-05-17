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
@ApiModel(description = "Details about a movie genre")
public class MovieGenreDto {
    private Long id;
    private String name;
}
