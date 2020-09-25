package com.movies.services;

import com.movies.dto.MovieGenreDto;
import com.movies.mappers.MovieGenreMapper;
import com.movies.repositories.MovieGenreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieGenreService {
    private final MovieGenreRepository movieGenreRepository;
    private final MovieGenreMapper movieGenreMapper;

    public List<MovieGenreDto> getAllMovieGenres() {
        return movieGenreRepository.findAll()
                .stream()
                .map(movieGenreMapper::movieGenreToMovieGenreDto)
                .collect(Collectors.toList());
    }

    public void addMovieGenre(MovieGenreDto movieGenreDto) {
        movieGenreRepository.save(movieGenreMapper.movieGenreDtoToMovieGenre(movieGenreDto));
        log.info("New genre added");
    }
}
