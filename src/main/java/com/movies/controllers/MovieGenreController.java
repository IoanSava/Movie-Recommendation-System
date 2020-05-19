package com.movies.controllers;

import com.movies.dto.MovieGenreDto;
import com.movies.services.MovieGenreService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("/genres")
public class MovieGenreController {
    private final MovieGenreService movieGenreService;

    public MovieGenreController(MovieGenreService movieGenreService) {
        this.movieGenreService = movieGenreService;
    }

    @GetMapping
    @ApiOperation(value = "Retrieve all genres",
            response = MovieGenreDto.class,
            responseContainer = "Set")
    public Set<MovieGenreDto> getAllMovieGenres() {
        return movieGenreService.getAllMovieGenres();
    }

    @PostMapping
    @ApiOperation(value = "Add a new movie genre")
    public ResponseEntity<String> addMovieGenre(@RequestBody @Valid MovieGenreDto movieGenreDto) {
        movieGenreService.addMovieGenre(movieGenreDto);
        return new ResponseEntity<>("Movie genre added", HttpStatus.CREATED);
    }
}