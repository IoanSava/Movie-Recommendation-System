package com.movies.services;

import com.movies.dto.MovieGenreDto;
import com.movies.entities.MovieGenre;
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

    public void addMovieGenre(MovieGenreDto movieGenreDto) {
        movieGenreRepository.save(modelMapper.map(movieGenreDto, MovieGenre.class));
        log.info("New genre added");
    }
}
