package com.movies.entities;

import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "title cannot be null")
    private String title;

    @Range(min = 1800, max = 2024)
    @NotNull(message = "releaseYear cannot be null")
    private Long releaseYear;

    @ManyToOne
    @JoinColumn(name = "genre_id", nullable = false)
    private MovieGenre genre;

    @ManyToMany
    @JoinTable(name = "movie_actors", joinColumns = @JoinColumn(name = "movie_id"), inverseJoinColumns = @JoinColumn(name = "actor_id"))
    private Set<Actor> actors;
}
