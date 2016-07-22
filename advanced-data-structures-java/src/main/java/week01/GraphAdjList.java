package week01;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class GraphAdjList extends Graph {

    private Map<Integer, List<Integer>> adjListsMap;

    public void implementAddVertex() {
        int v = getNumVertices();
        final ArrayList<Integer> neighbors = new ArrayList<Integer>();
        adjListsMap.put(v, neighbors);
    }

    public void implementAddEdge(int v, int w) {
        adjListsMap.get(v).add(w);
    }

    public List<Integer> getNeighbors(int v) {
        return Collections.unmodifiableList(adjListsMap.get(v));
    }
}
