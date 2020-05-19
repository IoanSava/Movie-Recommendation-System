package com.movies.graphs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class Edge {
    private Node firstNode;
    private Node secondNode;

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
