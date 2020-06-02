package com.movies.services;

import com.google.common.collect.Sets;
import com.movies.dto.MovieDto;
import com.movies.entities.Actor;
import com.movies.entities.Movie;
import com.movies.entities.MovieGenre;
import com.movies.exceptions.*;
import com.movies.graphs.Edge;
import com.movies.graphs.Graph;
import com.movies.graphs.Node;
import com.movies.repositories.ActorRepository;
import com.movies.repositories.MovieGenreRepository;
import com.movies.repositories.MovieRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MovieService {
    private final MovieRepository movieRepository;
    private final ActorRepository actorRepository;
    private final MovieGenreRepository movieGenreRepository;
    private final ModelMapper modelMapper;

    public MovieService(MovieRepository movieRepository, ActorRepository actorRepository, MovieGenreRepository movieGenreRepository) {
        this.movieRepository = movieRepository;
        this.actorRepository = actorRepository;
        this.movieGenreRepository = movieGenreRepository;
        this.modelMapper = new ModelMapper();
    }

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

    /**
     * @return true if the given movies have at least one thing in common
     * (the genre, the release year or an actor)
     */
    private boolean haveSimilarities(Movie firstMovie, Movie secondMovie) {
        return (firstMovie != secondMovie && (firstMovie.getGenre() == secondMovie.getGenre() ||
                firstMovie.getReleaseYear().equals(secondMovie.getReleaseYear()) ||
                !Sets.intersection(firstMovie.getActors(), secondMovie.getActors()).isEmpty()));
    }

    /**
     * Building a graph based on a set of movies.
     * A movie is represented by a node in this graph.
     * Two nodes are joined by an edge if
     * the corresponding movies have at least one thing in common.
     */
    private Graph buildGraphFromMovies(List<Movie> movies) {
        Graph graph = new Graph();
        int numberOfMovies = movies.size();

        for (int i = 0; i < numberOfMovies - 1; ++i) {
            for (int j = i + 1; j < numberOfMovies; ++j) {
                Movie firstMovie = movies.get(i);
                Movie secondMovie = movies.get(j);
                if (haveSimilarities(firstMovie, secondMovie)) {
                    Edge edge = new Edge(new Node(firstMovie.getId()), new Node(secondMovie.getId()));
                    graph.addEdge(edge);
                }
            }
        }

        return graph;
    }

    /**
     * @return a list of various movies recommendations based on
     * the similarities of existing movies (a vertex cover of the
     * corresponding graph)
     */
    public Set<MovieDto> getMovieRecommendations() {
        List<Movie> movies = new ArrayList<>(((List<Movie>) movieRepository.findAll()));
        Graph graph = buildGraphFromMovies(movies);
        Set<Node> nodes = graph.getVertexCover();

        Set<MovieDto> recommendedMovies = new HashSet<>();
        for (Node node : nodes) {
            Long movieId = node.getValue();
            Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new EntityNotFoundException("Movie", movieId));
            recommendedMovies.add(modelMapper.map(movie, MovieDto.class));
        }

        if (recommendedMovies.size() == 0) {
            throw new NoDataFoundException();
        }

        return recommendedMovies;
    }
}
