package com.movies.controllers;

import com.movies.dto.MovieDto;
import com.movies.services.MovieService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("movies")
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

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

    @GetMapping("/actors/{id}")
    @ApiOperation(value = "Retrieve all movies of a specified actor",
            response = MovieDto.class,
            responseContainer = "List")
    public List<MovieDto> getAllMoviesOfActor(@ApiParam(value = "id of the actor", required = true)
                                             @PathVariable @Valid @Min(0) Long id) {
        return movieService.getMoviesOfActor(id);
    }

    @GetMapping("/genres/{id}")
    @ApiOperation(value = "Retrieve all movies of a specified genre",
            response = MovieDto.class,
            responseContainer = "List")
    public List<MovieDto> getAllMoviesByGenre(@ApiParam(value = "id of the genre", required = true)
                                             @PathVariable @Valid @Min(0) Long id) {
        return movieService.getMoviesByGenre(id);
    }

    @PostMapping("/{movieId}/actors/{actorId}")
    @ApiOperation(value = "Add an actor to a specified movie")
    public ResponseEntity<String> addActorToMovie(@ApiParam(value = "id of the movie", required = true)
                                                  @PathVariable @Valid @Min(0) Long movieId,
                                                  @ApiParam(value = "id of the actor", required = true)
                                                  @PathVariable @Valid @Min(0) Long actorId) {
        movieService.addActorToMovie(movieId, actorId);
        return new ResponseEntity<>("Actor added to movie", HttpStatus.OK);
    }

    @DeleteMapping("/{movieId}/actors/{actorId}")
    @ApiOperation(value = "Remove an actor from a specified movie")
    public ResponseEntity<String> removeActorFromMovie(@ApiParam(value = "id of the movie", required = true)
                                                       @PathVariable @Valid @Min(0) Long movieId,
                                                       @ApiParam(value = "id of the actor", required = true)
                                                       @PathVariable @Valid @Min(0) Long actorId) {
        movieService.removeActorFromMovie(movieId, actorId);
        return new ResponseEntity<>("Actor removed from movie", HttpStatus.OK);
    }

    @GetMapping("/recommendations")
    @ApiOperation(value = "Retrieve a list of recommended movies",
            response = MovieDto.class,
            responseContainer = "List")
    public List<MovieDto> getRecommendedMovies() {
        return movieService.getMovieRecommendations();
    }
}
