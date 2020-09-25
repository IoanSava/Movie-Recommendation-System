package com.movies.mappers;

import com.movies.dto.MovieGenreDto;
import com.movies.entities.MovieGenre;
import com.movies.exceptions.EntityNotFoundException;
import com.movies.repositories.MovieGenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public final class MovieGenreMapper {
    private final MovieGenreRepository movieGenreRepository;

    public MovieGenre movieGenreDtoToMovieGenre(MovieGenreDto movieGenreDto) {
        return movieGenreRepository.findByNameIgnoreCase(movieGenreDto.getName())
                .orElseThrow(() -> new EntityNotFoundException("MovieGenre"));
    }

    public MovieGenreDto movieGenreToMovieGenreDto(MovieGenre movieGenre) {
        MovieGenreDto movieGenreDto = new MovieGenreDto();
        movieGenreDto.setName(movieGenre.getName());
        return movieGenreDto;
    }
}