package week04;

import static java.util.Arrays.asList;

import common.Edge;
import common.Graph;
import common.Node;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * This class contains implementation of algorithm for Travelling Salesman Problem.
 * Two algorithms are implemented:
 * - Greedy "Best Next Choice" (shortest edge)
 * - Brute-force O(n!) algorithm
 */
public class Tsp {


    public static List<Node> bruteForce(Graph graph, Node homeTown) {
        LinkedList<Node> shortestPath = new LinkedList<>();

        long shortestDistance = Long.MAX_VALUE;

        // iterate through all permutations and find the one with smallest overall distance
        // notice that the total number of possible permutations is (vertices_num - 1)! (homeTown is excluded)
        //
        // also, to avoid storing all permutations in memory at once,
        // we assign each permutation a number from between 0 and [(vertices_num - 1) - 1]!
        // using basic arithmetic involving integer division and modulo
        // see getPermutation for more details
        final long numberOfPossiblePermutations = factorial(graph.verticesNum() - 1);
        for (long i = 0; i < numberOfPossiblePermutations; i++) {
            List<Node> permutation = getPermutation(graph, homeTown, i);
            // add hometown as the source and final target
            permutation.add(0, homeTown);
            permutation.add(homeTown);

            int permutationPathDistance = getPermutationDistance(graph, permutation);

            if (shortestDistance > permutationPathDistance) {
                shortestPath = new LinkedList<>(permutation);
                shortestDistance = permutationPathDistance;
            }
        }

        return shortestPath;
    }

    public static List<Node> bestNextChoice(Graph graph, Node homeTown) {

        final LinkedList<Node> path = new LinkedList<>();
        path.add(homeTown);

        final Set<Node> visited = new HashSet<>();
        visited.add(homeTown);

        Node current = homeTown;
        while (visited.size() < graph.verticesNum()) {
            final List<Edge> neighborRoutes = graph.neighborEdges(current);
            Edge minDistanceRoute = null;
            for (Edge route : neighborRoutes) {
                if (!visited.contains(route.getEnd())
                        && (minDistanceRoute == null || minDistanceRoute.getDistance() > route.getDistance())) {
                    minDistanceRoute = route;
                }
            }
            final Node bestChoice = minDistanceRoute.getEnd();
            path.add(bestChoice);
            visited.add(bestChoice);
            current = bestChoice;
        }

        // finally, add route back to homeTown if such exist
        if (graph.neighbors(path.getLast()).contains(homeTown)) {
            path.add(homeTown);
        }

        return path;
    }


    //--------------------------------------------------- HELPER METHODS -----------------------------------------------

    private static List<Node> getPermutation(Graph graph, Node homeTown, long i) {
        List<Node> vertexList = new ArrayList<>(graph.vertices());
        vertexList.remove(homeTown);

        List<Node> permutation = new ArrayList<>(vertexList.size());

        int j = graph.verticesNum() - 2;
        long subPermutationIndex = i;
        while (j >= 0) {
            final long subPermutationsCount = factorial(j);
            int elementIndex = (int) (subPermutationIndex / subPermutationsCount);
            subPermutationIndex = subPermutationIndex % subPermutationsCount;

            final Node element = vertexList.get(elementIndex);
            permutation.add(element);
            vertexList.remove(element);

            j--;
        }
        return permutation;
    }

    private static int getPermutationDistance(Graph graph, List<Node> permutation) {
        int permutationPathDistance = 0;
        for (int j = 0; j < permutation.size() - 1; j++) {
            permutationPathDistance += graph.edge(permutation.get(j), permutation.get(j + 1)).getDistance();
        }
        return permutationPathDistance;
    }


    private static long factorial(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Factorial of negative number is not defined!");
        }

        long result = 1;
        for (int i = n; i > 0; i--) {
            result *= i;
        }
        return result;
    }

}
