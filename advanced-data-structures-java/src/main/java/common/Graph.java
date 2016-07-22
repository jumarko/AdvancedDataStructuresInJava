package common;

import static java.util.Collections.unmodifiableList;
import static java.util.Collections.unmodifiableSet;
import static java.util.stream.Collectors.toList;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Simple representation of generic graph.
 */
public class Graph {

    private final Map<Node, List<Edge>> graph = new HashMap<>();

    /**
     * Creates new immutable instance of graph based on provided adjacency list.
     */
    public Graph(Map<Node, List<Edge>> graphAdjacencyList) {
        for (Map.Entry<Node, List<Edge>> nodeEntry : graphAdjacencyList.entrySet()) {
            graph.put(nodeEntry.getKey(), unmodifiableList(nodeEntry.getValue()));
        }
    }


    /**
     * Returns number of vertices in this graph.
     */
    public int verticesNum() {
        return graph.size();
    }

    /**
     * Returns unmodifiable set of all vertices in this graph.
     */
    public Set<Node> vertices() {
        return unmodifiableSet(graph.keySet());
    }

    /**
     * Returns all neighbors of this vertex.
     */
    public List<Node> neighbors(Node vertex) {
        return unmodifiableList(graph.get(vertex).stream().map(Edge::getEnd).collect(toList()));
    }

    /**
     * Returns edges to all neighbors of this vertex.
     */
    public List<Edge> neighborEdges(Node vertex) {
        return unmodifiableList(graph.get(vertex));
    }

    public Edge edge(Node start, Node end) {
        final List<Edge> edges = neighborEdges(start);
        for (Edge edge : edges) {
            if (edge.getEnd().equals(end)) {
                return edge;
            }
        }
        return null;
    }


}
