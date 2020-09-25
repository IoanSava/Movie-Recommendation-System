package com.movies.graphs;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VertexCoverTests {
    @Test
    public void vertexCoverWithNoNodes() {
        Graph graph = new Graph();

        Assert.assertEquals(0, graph.getVertexCover().size());
    }

    @Test
    public void vertexCoverWith2NodesChain() {
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Edge edge1 = new Edge(node1, node2);
        Graph graph = new Graph();
        graph.addEdge(edge1);

        Assert.assertEquals(1, graph.getVertexCover().size());
    }

    @Test
    public void vertexCoverWith3NodesChain() {
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);

        Edge edge1 = new Edge(node1, node2);
        Edge edge2 = new Edge(node2, node3);

        Graph graph = new Graph();
        graph.addEdge(edge1);
        graph.addEdge(edge2);

        Assert.assertEquals(1, graph.getVertexCover().size());
    }

    @Test
    public void vertexCoverWith4NodesChain() {
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);

        Edge edge1 = new Edge(node1, node2);
        Edge edge2 = new Edge(node2, node3);
        Edge edge3 = new Edge(node3, node4);

        List<Edge> edgeList = new ArrayList<>(Arrays.asList(edge1, edge2, edge3));
        Graph graph = new Graph(edgeList);

        Assert.assertEquals(2, graph.getVertexCover().size());
    }

    @Test
    public void vertexCoverWith4NodesCompleteGraph() {
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);

        Edge edge1 = new Edge(node1, node2);
        Edge edge2 = new Edge(node1, node3);
        Edge edge3 = new Edge(node1, node4);
        Edge edge4 = new Edge(node2, node3);
        Edge edge5 = new Edge(node2, node4);
        Edge edge6 = new Edge(node3, node4);

        List<Edge> edgeList = new ArrayList<>(Arrays.asList(edge1, edge2, edge3, edge4, edge5, edge6));
        Graph graph = new Graph(edgeList);

        Assert.assertEquals(3, graph.getVertexCover().size());
    }

    @Test
    public void vertexCoverWith4Nodes2Edges() {
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);

        Edge edge1 = new Edge(node1, node2);
        Edge edge2 = new Edge(node3, node4);

        List<Edge> edgeList = new ArrayList<>(Arrays.asList(edge1, edge2));
        Graph graph = new Graph(edgeList);

        Assert.assertEquals(2, graph.getVertexCover().size());
    }
}
