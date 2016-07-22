package common;

import week03.Dijkstra;

import java.util.Objects;

public class NodeDistance implements Comparable<NodeDistance> {
    private final Node node;
    private final int distance;

    public NodeDistance(Node node, int distance) {
        this.node = node;
        this.distance = distance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NodeDistance that = (NodeDistance) o;
        return distance == that.distance &&
                Objects.equals(node, that.node);
    }

    @Override
    public int hashCode() {
        return Objects.hash(node, distance);
    }

    @Override
    public int compareTo(NodeDistance o) {
        return Integer.compare(distance, o.distance);
    }

    public Node getNode() {
        return node;
    }

    public int getDistance() {
        return distance;
    }
}
