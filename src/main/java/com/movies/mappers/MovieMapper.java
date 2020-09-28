package com.movies.mappers;

import com.movies.dto.MovieDto;
import com.movies.entities.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public final class MovieMapper {
    private final MovieGenreMapper movieGenreMapper;

    public Movie movieDtoToMovie(MovieDto movieDto) {
        Movie movie = new Movie();
        movie.setTitle(movieDto.getTitle());
        movie.setReleaseYear(movieDto.getReleaseYear());
        movie.setGenre(movieGenreMapper.movieGenreDtoToMovieGenre(movieDto.getGenre()));
        return movie;
    }

    public MovieDto movieToMovieDto(Movie movie) {
        MovieDto movieDto = new MovieDto();
        movieDto.setId(movie.getId());
        movieDto.setTitle(movie.getTitle());
        movieDto.setReleaseYear(movie.getReleaseYear());
        movieDto.setGenre(movieGenreMapper.movieGenreToMovieGenreDto(movie.getGenre()));
        return movieDto;
    }
}