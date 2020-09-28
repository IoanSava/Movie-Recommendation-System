package com.movies.services;

import com.google.common.collect.Sets;
import com.movies.dto.MovieDto;
import com.movies.entities.Actor;
import com.movies.entities.Movie;
import com.movies.entities.MovieGenre;
import com.movies.exceptions.ActorAlreadyAddedToMovieException;
import com.movies.exceptions.ActorNotInMovieException;
import com.movies.exceptions.EntityNotFoundException;
import com.movies.graphs.Edge;
import com.movies.graphs.Graph;
import com.movies.graphs.Node;
import com.movies.mappers.MovieMapper;
import com.movies.repositories.ActorRepository;
import com.movies.repositories.MovieGenreRepository;
import com.movies.repositories.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
    private final ActorRepository actorRepository;
    private final MovieGenreRepository movieGenreRepository;
    private final MovieMapper movieMapper;


    public List<MovieDto> getAllMovies() {
        return movieRepository.findAll()
                .stream()
                .map(movieMapper::movieToMovieDto)
                .collect(Collectors.toList());
    }

    public void addMovie(MovieDto movieDto) {
        movieRepository.save(movieMapper.movieDtoToMovie(movieDto));
    }

    public void updateMovieTitle(Long id, String title) {
        movieRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Movie", id));
        movieRepository.updateTitle(id, title);
    }

    public void deleteMovie(Long id) {
        movieRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Movie", id));
        movieRepository.deleteById(id);
    }

    public List<MovieDto> getMoviesOfActor(Long actorId) {
        Actor actor = actorRepository.findById(actorId).orElseThrow(() -> new EntityNotFoundException("Actor", actorId));

        return actor.getMovies()
                .stream()
                .map(movieMapper::movieToMovieDto)
                .collect(Collectors.toList());
    }

    public List<MovieDto> getMoviesByGenre(Long genreId) {
        MovieGenre movieGenre = movieGenreRepository.findById(genreId).orElseThrow(() -> new EntityNotFoundException("MovieGenre", genreId));

        return movieGenre.getMovies()
                .stream()
                .map(movieMapper::movieToMovieDto)
                .collect(Collectors.toList());
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
        return (firstMovie != secondMovie && (firstMovie.getGenre().equals(secondMovie.getGenre()) ||
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
    public List<MovieDto> getMovieRecommendations() {
        Graph graph = buildGraphFromMovies(movieRepository.findAll());
        Set<Node> nodes = graph.getVertexCover();

        List<MovieDto> recommendedMovies = new ArrayList<>();
        for (Node node : nodes) {
            Long movieId = node.getValue();
            Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new EntityNotFoundException("Movie", movieId));
            recommendedMovies.add(movieMapper.movieToMovieDto(movie));
        }

        return recommendedMovies;
    }
}
