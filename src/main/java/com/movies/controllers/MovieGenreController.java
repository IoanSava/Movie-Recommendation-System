package com.movies.controllers;

import com.movies.dto.MovieGenreDto;
import com.movies.services.MovieGenreService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("genres")
@RequiredArgsConstructor
public class MovieGenreController {
    private final MovieGenreService movieGenreService;

    @GetMapping
    @ApiOperation(value = "Retrieve all genres",
            response = MovieGenreDto.class,
            responseContainer = "List")
    public List<MovieGenreDto> getAllMovieGenres() {
        return movieGenreService.getAllMovieGenres();
    }

    @PostMapping
    @ApiOperation(value = "Add a new movie genre")
    @ResponseStatus(HttpStatus.CREATED)
    public String addMovieGenre(@RequestBody @Valid MovieGenreDto movieGenreDto) {
        movieGenreService.addMovieGenre(movieGenreDto);
        return "Movie genre added";
    }
}
