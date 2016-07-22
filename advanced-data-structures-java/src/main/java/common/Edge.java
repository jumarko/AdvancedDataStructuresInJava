package common;

import java.util.Objects;

/**
 * Denotes the edges of the graph.
 * Each edge has start, end and distance.
 */
public class Edge {
    private final Node start;
    private final Node end;
    private final int distance;

    public Edge(Node start, Node end, int distance) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("neither start node, nor end node can be null.");
        }
        if (distance < 1) {
            throw new IllegalArgumentException("Distance must be positive");
        }
        this.start = start;
        this.end = end;
        this.distance = distance;
    }

    public Node getStart() {
        return start;
    }

    public Node getEnd() {
        return end;
    }

    public int getDistance() {
        return distance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return distance == edge.distance &&
                Objects.equals(start, edge.start) &&
                Objects.equals(end, edge.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end, distance);
    }

    @Override
    public String toString() {
        return "Edge{" +
                "start=" + start +
                ", end=" + end +
                ", distance=" + distance +
                '}';
    }
}
