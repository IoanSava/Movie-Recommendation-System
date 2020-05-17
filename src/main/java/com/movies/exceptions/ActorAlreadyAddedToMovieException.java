package com.movies.exceptions;

public class ActorAlreadyAddedToMovieException extends RuntimeException {
    public ActorAlreadyAddedToMovieException(Long movieId, Long actorId) {
        super("Actor with id " + actorId + " already added to movie with id " + movieId);
    }
}
