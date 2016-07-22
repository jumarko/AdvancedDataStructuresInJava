package week03;

import common.Edge;
import common.Graph;
import common.Node;
import common.NodeDistance;
import common.Utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * Demonstration of Dijkstra's algorithm for graphs with weighted edges.
 * Check https://www.coursera.org/learn/advanced-data-structures/lecture/2ctyF/core-dijkstras-algorithm.
 */
public class Dijkstra {

    /**
     * Finds shortest path from {@code start} to {@code goal} using Dijkstra's algorithm.
     *
     * @return list of nodes representing shortest path - starting from {@code start} as a first element in the list
     * going to {@code goal} which is the last element in the list
     */
    public static List<Node> dijkstra(Graph graph, Node start, Node goal) {
        // the core of this algorithm is a Priority Queue data structure - we're going to visit nodes
        // which are the closest one
        // {Node, distance} pairs are stored in priority queue
        // "parent map" stores the parent-child relationshipts to be able to reconstruct shortest path
        // "visited" set maintains a set of nodes that have already been visited (we don't want to process such nodes twice)
        //
        // we're visiting each node's (aka "current") neighbors just like BFS but for each neighbor N
        // we check whether there's a shorter path that goes through current node to the N.
        // if so then we "update" distance (put new pair {N, shortest_distance} to priority queue)
        // and update parent map to make current node to be a new parent for N

        final Set<Node> visited = new HashSet<>(graph.verticesNum());
        final Map<Node, Node> parentMap = new HashMap<>(graph.verticesNum());
        final PriorityQueue<NodeDistance> priorityQueue = new PriorityQueue<>(graph.verticesNum());
        final Map<Node, Integer> shortestPaths = new HashMap<>(graph.verticesNum());

        // init priority queue
        for (Node node : graph.vertices()) {
            if (!node.equals(start)) {
                shortestPaths.put(node, Integer.MAX_VALUE);
            }
        }
        shortestPaths.put(start, 0);

        priorityQueue.add(new NodeDistance(start, 0));
        while (!priorityQueue.isEmpty()) {
            final NodeDistance node = priorityQueue.remove();
            final Node current = node.getNode();
            final int currentDistance = node.getDistance();

            if (! visited.contains(current)) {
                visited.add(current);
                final List<Edge> neighbors = graph.neighborEdges(current);
                for (Edge neighbor : neighbors) {
                    final Node neighborNode = neighbor.getEnd();
                    if (!visited.contains(neighborNode)) {
                        final int newShortestPath = currentDistance + neighbor.getDistance();
                        if (shortestPaths.get(neighborNode) > newShortestPath) {
                            // we've found shorter path leading through the current node to the neighbor
                            shortestPaths.put(neighborNode, newShortestPath);
                            priorityQueue.add(new NodeDistance(neighborNode, newShortestPath));
                            parentMap.put(neighborNode, current);
                        }
                    }
                }
            }
        }

        return Utils.reconstructPath(parentMap, start, goal);
    }

}
