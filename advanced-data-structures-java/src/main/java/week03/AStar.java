package week03;

import static java.lang.Math.abs;
import static javax.swing.UIManager.put;

import common.Graph;
import common.Node;
import common.NodeDistance;
import common.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Implementation of A* algorithm for finding shortest path from A to B inspired by
 * http://www.redblobgames.com/pathfinding/a-star/introduction.html
 */
public class AStar {

    public static List<Node> search(Graph graph, Node start, Node goal) {

        final Queue<NodeDistance> frontier = new PriorityQueue();
        frontier.add(new NodeDistance(start, 0));
        final Map<Node, Node> cameFrom = new HashMap<>(graph.verticesNum());
        final Map<Node, Integer> costSoFar = new HashMap<>(graph.verticesNum());
        cameFrom.put(start, null);
        costSoFar.put(start, 0);

        while (!frontier.isEmpty()) {
            final Node current = frontier.remove().getNode();
            if (current.equals(goal)) {
                break;
            }

            for (Node neighbor : graph.neighbors(current)) {
                int newCost = costSoFar.get(current) + graph.edge(current, neighbor).getDistance();
                if (!costSoFar.containsKey(neighbor)
                        || newCost < costSoFar.get(neighbor)) {
                    costSoFar.put(neighbor, newCost);
                    frontier.add(new NodeDistance(neighbor, newCost + heuristic(goal, neighbor)));
                    cameFrom.put(neighbor, current);
                }
            }
        }

        return Utils.reconstructPath(cameFrom, start, goal);
    }

    private static int heuristic(Node a, Node b) {
        // Manhattan distance on a square grid
        return abs(a.getX() - b.getX()) + abs(a.getY() - b.getY());
    }

}
