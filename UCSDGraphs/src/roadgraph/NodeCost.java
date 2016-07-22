package roadgraph;

import java.util.Objects;

/**
 * Wrapper class representing pair [Node, Cost].
 * Cost is used for prioritization of nodes in PriorityQueue-like structure.
 * Lower cost means better, e.g. lower distance.
 */
public class NodeCost implements Comparable<NodeCost> {

    private final MapNode node;
    private final double cost;

    public NodeCost(MapNode node, double cost) {
        if (node == null) {
            throw new IllegalArgumentException("Node cannot be null");
        }
        if (cost < 0) {
            throw new IllegalArgumentException("Cost cannot be negative");
        }
        this.node = node;
        this.cost = cost;
    }

    public MapNode getNode() {
        return node;
    }

    public double getCost() {
        return cost;
    }

    @Override
    public int compareTo(NodeCost o) {
        return Double.compare(cost, o.cost);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NodeCost nodeCost = (NodeCost) o;
        return Double.compare(nodeCost.cost, cost) == 0 &&
                Objects.equals(node, nodeCost.node);
    }

    @Override
    public int hashCode() {
        return Objects.hash(node, cost);
    }

    @Override
    public String toString() {
        return "NodeCost{" +
                "node=" + node +
                ", cost=" + cost +
                '}';
    }
}
