package com.movies.services;

import com.movies.dto.MovieGenreDto;
import com.movies.entities.MovieGenre;
import com.movies.exceptions.DuplicateEntityException;
import com.movies.repositories.MovieGenreRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MovieGenreService {
    private final MovieGenreRepository movieGenreRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    public MovieGenreService(MovieGenreRepository movieGenreRepository) {
        this.movieGenreRepository = movieGenreRepository;
    }

    public List<MovieGenreDto> getAllMovieGenres() {
        return movieGenreRepository.findAll()
                .stream()
                .map(movieGenre -> modelMapper.map(movieGenre, MovieGenreDto.class))
                .collect(Collectors.toList());
    }

    private boolean checkIfMovieGenreExists(Long id) {
        return movieGenreRepository.findById(id).isPresent();
    }

    public void addMovieGenre(MovieGenreDto movieGenreDto) {
        Long movieGenreId = movieGenreDto.getId();
        if (movieGenreId != null && checkIfMovieGenreExists(movieGenreId)) {
            throw new DuplicateEntityException("MovieGenre", movieGenreId);
        }
        log.debug("New genre added");
        movieGenreRepository.save(modelMapper.map(movieGenreDto, MovieGenre.class));
    }
}
