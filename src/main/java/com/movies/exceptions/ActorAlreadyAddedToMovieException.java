package com.movies.exceptions;

public class ActorAlreadyAddedToMovieException extends RuntimeException {
    public ActorAlreadyAddedToMovieException(Long movieId, Long actorId) {
        super(String.format("Actor with id %d already added to movie with id %d", actorId, movieId));
    }
}