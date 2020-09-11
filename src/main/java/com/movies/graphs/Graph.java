package com.movies.graphs;

import com.movies.exceptions.DuplicateEdgeException;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
public class Graph {
    private final List<Edge> edges;

    public Graph() {
        edges = new ArrayList<>();
    }

    public Graph(List<Edge> edges) {
        this.edges = new ArrayList<>(edges);
    }

    public void addEdge(Edge edge) {
        if (edges.contains(edge)) {
            throw new DuplicateEdgeException(edge);
        }
        edges.add(edge);
    }

    private List<Node> getNodes() {
        List<Node> nodes = new ArrayList<>();
        for (Edge edge : this.edges) {
            if (!nodes.contains(edge.getFirstNode())) {
                nodes.add(edge.getFirstNode());
            }
            if (!nodes.contains(edge.getSecondNode())) {
                nodes.add(edge.getSecondNode());
            }
        }

        return nodes;
    }

    /**
     * @return the node with the maximum degree
     * among the given edges
     */
    private Node getMaxDegree(List<Edge> edges) {
        int maximumDegree = 0;
        Node maxDegreeNode = null;

        Map<Node, Integer> degrees = new HashMap<>();
        for (Edge edge : edges) {
            degrees.merge(edge.getFirstNode(), 1, Integer::sum);
            if (maximumDegree < degrees.get(edge.getFirstNode())) {
                maximumDegree = degrees.get(edge.getFirstNode());
                maxDegreeNode = edge.getFirstNode();
            }

            degrees.merge(edge.getSecondNode(), 1, Integer::sum);

            if (maximumDegree < degrees.get(edge.getSecondNode())) {
                maximumDegree = degrees.get(edge.getSecondNode());
                maxDegreeNode = edge.getSecondNode();
            }
        }

        return maxDegreeNode;
    }

    private void removeEdgesWhichContainsNode(List<Edge> edges, Node node) {
        List<Edge> removedEdges = new ArrayList<>();
        for (Edge edge : edges) {
            if (edge.getFirstNode().equals(node) || edge.getSecondNode().equals(node)) {
                removedEdges.add(edge);
            }
        }

        for (Edge edge : removedEdges) {
            edges.remove(edge);
        }
    }

    /**
     * <a href="https://en.wikipedia.org/wiki/Vertex_cover">Vertex cover</a>
     *
     * @return a set of vertices which forms a vertex cover
     * for the current graph (a set of vertices
     * that includes at least
     * one endpoint of every edge of the graph)
     */
    public Set<Node> getVertexCover() {
        List<Edge> edges = this.getEdges();
        List<Node> nodes = this.getNodes();
        Set<Node> vertexCover = new HashSet<>();

        while (!edges.isEmpty()) {
            Node node = this.getMaxDegree(edges);
            vertexCover.add(node);
            nodes.remove(node);
            this.removeEdgesWhichContainsNode(edges, node);
        }

        return vertexCover;
    }
}
