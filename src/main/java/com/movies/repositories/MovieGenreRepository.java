package com.movies.repositories;

import com.movies.entities.MovieGenre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieGenreRepository extends JpaRepository<MovieGenre, Long> {
    Optional<MovieGenre> findByNameIgnoreCase(String name);
}
