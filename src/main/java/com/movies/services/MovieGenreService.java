package com.movies.services;

import com.movies.dto.MovieGenreDto;
import com.movies.entities.MovieGenre;
import com.movies.exceptions.DuplicateEntityException;
import com.movies.exceptions.NoDataFoundException;
import com.movies.repositories.MovieGenreRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MovieGenreService {
    private final MovieGenreRepository movieGenreRepository;
    private final ModelMapper modelMapper;

    public MovieGenreService(MovieGenreRepository movieGenreRepository) {
        this.movieGenreRepository = movieGenreRepository;
        this.modelMapper = new ModelMapper();
    }

    public Set<MovieGenreDto> getAllMovieGenres() {
        Set<MovieGenreDto> genres = ((List<MovieGenre>) movieGenreRepository.findAll())
                .stream()
                .map(movieGenre -> modelMapper.map(movieGenre, MovieGenreDto.class))
                .collect(Collectors.toSet());

        if (genres.size() == 0) {
            throw new NoDataFoundException();
        }
        return genres;
    }

    private boolean checkIfMovieGenreExists(Long id) {
        Optional<MovieGenre> movieGenre = movieGenreRepository.findById(id);
        return movieGenre.isPresent();
    }

    public void addMovieGenre(MovieGenreDto movieGenreDto) {
        Long movieGenreId = movieGenreDto.getId();
        if (movieGenreId != null && checkIfMovieGenreExists(movieGenreId)) {
            throw new DuplicateEntityException("MovieGenre", movieGenreId);
        }
        movieGenreRepository.save(modelMapper.map(movieGenreDto, MovieGenre.class));
    }
}
