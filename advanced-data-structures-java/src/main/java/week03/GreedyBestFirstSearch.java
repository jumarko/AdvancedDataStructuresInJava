package week03;

import static java.lang.Math.abs;
import static common.Utils.reconstructPath;

import common.Graph;
import common.Node;
import common.NodeDistance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * This is the implementation of the Greedy Best First Search algorithm.
 * Inspired by http://www.redblobgames.com/pathfinding/a-star/introduction.html.
 *
 * Greedy BFS algorithm expands more towards the goal than it expands in other directions.
 * For this purpose, the {@link #heuristic} is used.
 *
 * In Dijkstra's algorithm we used the actual distance of vertex v from the start for the priority queue.
 * In Greedy BFS we're using estimated distance to goal for priority queue.
 */
public class GreedyBestFirstSearch {

    public static List<Node> search(Graph graph, Node start, Node goal) {
        if (graph == null || start == null || goal == null) {
            throw new IllegalArgumentException("Input arguments cannot be null");
        }

        final Queue<NodeDistance> frontier = new PriorityQueue<>();
        frontier.add(new NodeDistance(start, 0));
        final Map<Node, Node> cameFrom = new HashMap<>(graph.verticesNum());
        // cameFrom aka "parent map" - notice that we can use single data structure
        // for both maintaining the set of visisted nodes as well as parent-child relationships
        cameFrom.put(start, null);

        while (!frontier.isEmpty()) {
            Node current = frontier.remove().getNode();
            if (current.equals(goal)) {
                break;
            }

            for (Node neighbor : graph.neighbors(current)) {
                if (!cameFrom.containsKey(neighbor)) {
                    // we haven't visited the neighbor vertex yet
                    frontier.add(new NodeDistance(neighbor, heuristic(goal, neighbor)));
                    cameFrom.put(neighbor, current);
                }
            }
        }

        return reconstructPath(cameFrom, start, goal);
    }


    private static int heuristic(Node a, Node b) {
        // Manhattan distance on a square grid
        return abs(a.getX() - b.getX()) + abs(a.getY() - b.getY());
    }

}
