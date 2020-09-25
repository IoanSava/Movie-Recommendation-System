package com.movies.exceptions;

public class ActorNotInMovieException extends RuntimeException {
    public ActorNotInMovieException(Long movieId, Long actorId) {
        super(String.format("Actor with id %d is not part of the movie with id %d", actorId, movieId));
    }
}