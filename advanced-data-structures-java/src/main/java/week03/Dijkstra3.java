package week03;

import static common.Utils.reconstructPath;

import common.Edge;
import common.Graph;
import common.Node;
import common.NodeDistance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Yet another implementation of Dijkstra inspired by http://www.redblobgames.com/pathfinding/a-star/introduction.html.
 */
public class Dijkstra3 {

    public static List<Node> dijkstra(Graph graph, Node start, Node goal) {
        Queue<NodeDistance> frontier = new PriorityQueue<>();
        frontier.add(new NodeDistance(start, 0));
        // cameFrom aka "parentMap"
        final Map<Node, Node> cameFrom = new HashMap<>();
        // costSoFar aka "distances"
        final Map<Node, Integer> costSoFar = new HashMap<>();
        cameFrom.put(start, null);
        costSoFar.put(start, 0);

        while (!frontier.isEmpty()) {
            Node current = frontier.remove().getNode();
            if (goal.equals(current)) {
                break;
            }
            for (Edge edge : graph.neighborEdges(current)) {
                final Node neighbor = edge.getEnd();
                int newCost = costSoFar.get(current) + edge.getDistance();
                if (!costSoFar.containsKey(neighbor)
                    || newCost < costSoFar.get(neighbor)) {
                    costSoFar.put(neighbor, newCost);
                    frontier.add(new NodeDistance(neighbor, newCost));
                    cameFrom.put(neighbor, current);
                }

            }
        }

        return reconstructPath(cameFrom, start, goal);
    }
}
