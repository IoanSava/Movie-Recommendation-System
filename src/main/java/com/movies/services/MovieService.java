package com.movies.services;

import com.movies.dto.MovieDto;
import com.movies.entities.Movie;
import com.movies.exceptions.DuplicateEntityException;
import com.movies.exceptions.EntityNotFoundException;
import com.movies.exceptions.NoDataFoundException;
import com.movies.repositories.MovieRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    public List<MovieDto> getAllMovies() {
        List<MovieDto> movies = ((List<Movie>) movieRepository.findAll())
                .stream()
                .map(movie -> modelMapper.map(movie, MovieDto.class))
                .collect(Collectors.toList());

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
}
