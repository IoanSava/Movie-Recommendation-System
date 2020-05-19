package com.movies.graphs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

@AllArgsConstructor
@Getter
public class Edge {
    private final Node firstNode;
    private final Node secondNode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return (Objects.equals(firstNode, edge.firstNode) &&
                Objects.equals(secondNode, edge.secondNode));
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstNode, secondNode);
    }
}
