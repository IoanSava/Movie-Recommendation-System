package com.movies.controllers;

import com.movies.dto.MovieGenreDto;
import com.movies.services.MovieGenreService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/genres")
@Validated
public class MovieGenreController {
    @Autowired
    MovieGenreService movieGenreService;

    @GetMapping
    @ApiOperation(value = "Retrieve all genres",
            response = MovieGenreDto.class,
            responseContainer = "List")
    public List<MovieGenreDto> getAllMovieGenres() {
        return movieGenreService.getAllMovieGenres();
    }

    @PostMapping
    @ApiOperation(value = "Add a new movie genre")
    public ResponseEntity<String> addMovieGenre(@RequestBody @Valid MovieGenreDto movieGenreDto) {
        movieGenreService.addMovieGenre(movieGenreDto);
        return new ResponseEntity<>("Movie genre added", HttpStatus.CREATED);
    }
}
