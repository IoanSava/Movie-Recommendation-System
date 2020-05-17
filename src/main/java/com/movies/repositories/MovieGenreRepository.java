package com.movies.repositories;

import com.movies.entities.MovieGenre;
import org.springframework.data.repository.CrudRepository;

public interface MovieGenreRepository extends CrudRepository<MovieGenre, Long> {
}
