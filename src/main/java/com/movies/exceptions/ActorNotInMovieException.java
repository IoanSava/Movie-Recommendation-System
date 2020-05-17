package com.movies.exceptions;

public class ActorNotInMovieException extends RuntimeException {
    public ActorNotInMovieException(Long movieId, Long actorId) {
        super("Actor with id " + actorId + " is not part of the movie with id " + movieId);
    }
}
