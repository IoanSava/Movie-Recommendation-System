package com.movies.graphs;

import com.movies.exceptions.DuplicateEdgeException;
import lombok.*;

import java.util.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Graph {
    private List<Edge> edges = new ArrayList<>();

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
            if (edge.getFirstNode() == node || edge.getSecondNode() == node) {
                removedEdges.add(edge);
            }
        }

        for (Edge edge : removedEdges) {
            edges.remove(edge);
        }
    }

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
