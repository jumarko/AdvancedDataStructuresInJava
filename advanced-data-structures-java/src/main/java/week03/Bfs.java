package week03;

import static common.Utils.reconstructPath;

import common.Graph;
import common.Node;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * This is the implementation of the BFS algorithm just for the sake of practice.
 * Inspired by http://www.redblobgames.com/pathfinding/a-star/introduction.html.
 */
public class Bfs {

    public static List<Node> bfs(Graph graph, Node start, Node goal) {
        if (graph == null || start == null || goal == null) {
            throw new IllegalArgumentException("Input arguments cannot be null");
        }

        final Queue<Node> frontier = new LinkedList<>();
        frontier.add(start);
        final Map<Node, Node> cameFrom = new HashMap<>(graph.verticesNum());
        // cameFrom aka "parent map" - notice that we can use single data structure
        // for both maintaining the set of visisted nodes as well as parent-child relationships
        cameFrom.put(start, null);

        while (!frontier.isEmpty()) {
            Node current = frontier.remove();
            if (current.equals(goal)) {
                break;
            }

            for (Node neighbor : graph.neighbors(current)) {
                if (!cameFrom.containsKey(neighbor)) {
                    // we haven't visited the neighbor vertex yet
                    frontier.add(neighbor);
                    cameFrom.put(neighbor, current);
                }
            }
        }

        return reconstructPath(cameFrom, start, goal);
    }
}
