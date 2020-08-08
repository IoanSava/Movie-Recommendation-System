package com.movies.repositories;

import com.movies.entities.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface ActorRepository extends JpaRepository<Actor, Long> {
    @Transactional
    @Modifying
    @Query("update Actor a set a.name = :name where a.id = :id")
    void updateName(@Param("id") Long id, @Param("name") String name);
}
