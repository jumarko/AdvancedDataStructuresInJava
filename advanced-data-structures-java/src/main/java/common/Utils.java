package common;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public final class Utils {

    private Utils() {
        throw new AssertionError("Utility class - DO NOT INSTANTIATE");
    }

    public static List<Node> reconstructPath(Map<Node, Node> parentMap, Node start, Node goal) {
        final LinkedList<Node> path = new LinkedList<>();
        path.add(goal);
        Node parent = parentMap.get(goal);
        while (parent != null) {
            path.addFirst(parent);

            if (parent.equals(goal)) {
                // we've reached the destination
                break;
            }

            // get parent's parent
            parent = parentMap.get(parent);
        }
        if (!start.equals(path.getFirst())) {
            throw new IllegalStateException("No path from " + start + " to " + goal + " has been found.");
        }
        return path;
    }
}
