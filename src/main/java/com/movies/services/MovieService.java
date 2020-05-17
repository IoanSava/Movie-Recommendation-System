package com.movies.services;

import com.movies.dto.MovieDto;
import com.movies.entities.Actor;
import com.movies.entities.Movie;
import com.movies.entities.MovieGenre;
import com.movies.exceptions.*;
import com.movies.repositories.ActorRepository;
import com.movies.repositories.MovieGenreRepository;
import com.movies.repositories.MovieRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ActorRepository actorRepository;
    @Autowired
    private MovieGenreRepository movieGenreRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    public Set<MovieDto> getAllMovies() {
        Set<MovieDto> movies = ((List<Movie>) movieRepository.findAll())
                .stream()
                .map(movie -> modelMapper.map(movie, MovieDto.class))
                .collect(Collectors.toSet());

        if (movies.size() == 0) {
            throw new NoDataFoundException();
        }

        return movies;
    }

    private boolean checkIfMovieExists(Long id) {
        Optional<Movie> movie = movieRepository.findById(id);
        return movie.isPresent();
    }

    public void addMovie(MovieDto movieDto) {
        Long movieId = movieDto.getId();
        if (movieId != null && checkIfMovieExists(movieId)) {
            throw new DuplicateEntityException("Movie", movieId);
        }
        movieRepository.save(modelMapper.map(movieDto, Movie.class));
    }

    public void updateMovieTitle(Long id, String title) {
        if (!checkIfMovieExists(id)) {
            throw new EntityNotFoundException("Movie", id);
        }
        movieRepository.updateTitle(id, title);
    }

    public void deleteMovie(Long id) {
        if (!checkIfMovieExists(id)) {
            throw new EntityNotFoundException("Movie", id);
        }
        movieRepository.deleteById(id);
    }

    public Set<MovieDto> getMoviesOfActor(Long actorId) {
        Actor actor = actorRepository.findById(actorId).orElseThrow(() -> new EntityNotFoundException("Actor", actorId));

        Set<MovieDto> movies = actor.getMovies()
                .stream()
                .map(movie -> modelMapper.map(movie, MovieDto.class))
                .collect(Collectors.toSet());

        if (movies.size() == 0) {
            throw new NoDataFoundException();
        }

        return movies;
    }

    public Set<MovieDto> getMoviesByGenre(Long genreId) {
        MovieGenre movieGenre = movieGenreRepository.findById(genreId).orElseThrow(() -> new EntityNotFoundException("MovieGenre", genreId));

        Set<MovieDto> movies = movieGenre.getMovies()
                .stream()
                .map(movie -> modelMapper.map(movie, MovieDto.class))
                .collect(Collectors.toSet());

        if (movies.size() == 0) {
            throw new NoDataFoundException();
        }

        return movies;
    }

    public void addActorToMovie(Long movieId, Long actorId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new EntityNotFoundException("Movie", movieId));
        Actor actor = actorRepository.findById(actorId).orElseThrow(() -> new EntityNotFoundException("Actor", actorId));

        if (movie.getActors().contains(actor)) {
            throw new ActorAlreadyAddedToMovieException(movieId, actorId);
        }

        movie.addActor(actor);
        movieRepository.save(movie);
    }

    public void removeActorFromMovie(Long movieId, Long actorId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new EntityNotFoundException("Movie", movieId));
        Actor actor = actorRepository.findById(actorId).orElseThrow(() -> new EntityNotFoundException("Actor", actorId));

        if (!movie.getActors().contains(actor)) {
            throw new ActorNotInMovieException(movieId, actorId);
        }

        movie.removeActor(actor);
        movieRepository.save(movie);
    }
}
