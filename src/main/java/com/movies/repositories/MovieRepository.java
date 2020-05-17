package com.movies.repositories;

import com.movies.entities.Movie;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface MovieRepository extends CrudRepository<Movie, Long> {
    Movie findByTitle(String title);

    @Transactional
    @Modifying
    @Query("update Movie m set m.title = :title where m.id = :id")
    void updateTitle(@Param("id") Long id, @Param("title") String title);
}
