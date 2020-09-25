package com.movies.exceptions;

import com.movies.graphs.Edge;

public class DuplicateEdgeException extends RuntimeException {
    public DuplicateEdgeException(Edge edge) {
        super(String.format("Duplicate edge: %s", edge));
    }
}