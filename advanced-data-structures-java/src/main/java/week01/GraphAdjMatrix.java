package week01;

import java.util.ArrayList;
import java.util.List;

public class GraphAdjMatrix extends Graph {

    private int[][] adjMatrix;

    public void implementAddVertex() {
        // when adding new vertex, we want to plan ahead and double the size of matrix if it's not sufficient
        final int v = getNumVertices();
        if (v >= adjMatrix.length) {
            int[][] newAdjMatrix = new int[v*2][v*2];
            for (int i = 0; i < adjMatrix.length; i++) {
                for (int j = 0; j < adjMatrix.length; j++) {
                    newAdjMatrix[i][j] = adjMatrix[i][j];
                }
            }
            adjMatrix = newAdjMatrix;
        }

        // make sure that new vertex is not connected to any other vertex
        for (int i = 0; i < adjMatrix.length; i++) {
            adjMatrix[v][i] = 0;
        }
    }

    public void implementAddEdge(int v, int w) {
        adjMatrix[v][w] = 1;
    }

    public List<Integer> getNeighbors(int v) {
        final ArrayList<Integer> neighbors = new ArrayList<Integer>();
        for (int i = 0; i < getNumVertices(); i++) {
            if (adjMatrix[v][i] == 1) {
                neighbors.add(i);
            }
        }
        return neighbors;
    }
}
