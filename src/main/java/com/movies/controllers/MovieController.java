package com.movies.controllers;

import com.movies.dto.MovieDto;
import com.movies.services.MovieService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("movies")
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;

    @GetMapping
    @ApiOperation(value = "Retrieve all movies",
            response = MovieDto.class,
            responseContainer = "List")
    public List<MovieDto> getAllMovies() {
        return movieService.getAllMovies();
    }

    @PostMapping
    @ApiOperation(value = "Add a new movie")
    @ResponseStatus(HttpStatus.CREATED)
    public String addMovie(@RequestBody @Valid MovieDto movieDto) {
        movieService.addMovie(movieDto);
        return "Movie added";
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update an existing movie title")
    @ResponseStatus(HttpStatus.OK)
    public String updateMovieTitle(@ApiParam(value = "id of the movie to update", required = true)
                                   @PathVariable @Valid @Min(0) Long id, @RequestParam String title) {
        movieService.updateMovieTitle(id, title);
        return "Movie title updated";
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete a movie")
    @ResponseStatus(HttpStatus.OK)
    public String deleteMovie(@ApiParam(value = "id of the movie to delete", required = true)
                              @PathVariable @Valid @Min(0) Long id) {
        movieService.deleteMovie(id);
        return "Movie deleted";
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
    @ResponseStatus(HttpStatus.OK)
    public String addActorToMovie(@ApiParam(value = "id of the movie", required = true)
                                  @PathVariable @Valid @Min(0) Long movieId,
                                  @ApiParam(value = "id of the actor", required = true)
                                  @PathVariable @Valid @Min(0) Long actorId) {
        movieService.addActorToMovie(movieId, actorId);
        return "Actor added to movie";
    }

    @DeleteMapping("/{movieId}/actors/{actorId}")
    @ApiOperation(value = "Remove an actor from a specified movie")
    @ResponseStatus(HttpStatus.OK)
    public String removeActorFromMovie(@ApiParam(value = "id of the movie", required = true)
                                       @PathVariable @Valid @Min(0) Long movieId,
                                       @ApiParam(value = "id of the actor", required = true)
                                       @PathVariable @Valid @Min(0) Long actorId) {
        movieService.removeActorFromMovie(movieId, actorId);
        return "Actor removed from movie";
    }

    @GetMapping("/recommendations")
    @ApiOperation(value = "Retrieve a list of recommended movies",
            response = MovieDto.class,
            responseContainer = "List")
    public List<MovieDto> getRecommendedMovies() {
        return movieService.getMovieRecommendations();
    }
}
