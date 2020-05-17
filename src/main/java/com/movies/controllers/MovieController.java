package com.movies.controllers;

import com.movies.dto.MovieDto;
import com.movies.services.MovieService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/movies")
@Validated
public class MovieController {
    @Autowired
    private MovieService movieService;

    @GetMapping
    @ApiOperation(value = "Retrieve all movies",
            response = MovieDto.class,
            responseContainer = "List")
    public List<MovieDto> getAllMovies() {
        return movieService.getAllMovies();
    }

    @PostMapping
    @ApiOperation(value = "Add a new movie")
    public ResponseEntity<String> addMovie(@RequestBody @Valid MovieDto movieDto) {
        movieService.addMovie(movieDto);
        return new ResponseEntity<>("Movie added", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update an existing movie title")
    public ResponseEntity<String> updateMovieTitle(@ApiParam(value = "id of the movie you want to update", required = true)
                                                   @PathVariable @Valid @Min(0) Long id, @RequestParam String title) {
        movieService.updateMovieTitle(id, title);
        return new ResponseEntity<>("Movie title updated", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete a movie")
    public ResponseEntity<String> deleteMovie(@ApiParam(value = "id of the movie you want to delete", required = true)
                                              @PathVariable @Valid @Min(0) Long id) {
        movieService.deleteMovie(id);
        return new ResponseEntity<>("Movie deleted", HttpStatus.OK);
    }
}
