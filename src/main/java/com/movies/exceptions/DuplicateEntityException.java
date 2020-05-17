package com.movies.exceptions;

public class DuplicateEntityException extends RuntimeException {
    public DuplicateEntityException(String entityName, Long id) {
        super(entityName + " with id " + id + " already exists");
    }
}
