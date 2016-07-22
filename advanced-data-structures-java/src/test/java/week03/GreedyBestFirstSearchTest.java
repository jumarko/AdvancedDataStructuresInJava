package week03;

import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static week03.DijkstraTest.graph;
import static week03.DijkstraTest.laJolla;
import static week03.DijkstraTest.losAngeles;
import static week03.DijkstraTest.sanDiego;
import static week03.DijkstraTest.sanFrancisco;

import common.Node;
import org.junit.Test;

import java.util.List;

public class GreedyBestFirstSearchTest {

    @Test
    public void bestFirstSearchTest() {
        // notice that BFS counts with distance to the goal and thus finds more optimal route than BFS
        final List<Node> shortestPath = GreedyBestFirstSearch.search(graph, sanFrancisco, sanDiego);
        assertThat(shortestPath, is(asList(sanFrancisco, losAngeles, laJolla, sanDiego)));
    }

}