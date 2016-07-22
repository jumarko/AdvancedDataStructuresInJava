package week03;

import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static week03.DijkstraTest.graph;
import static week03.DijkstraTest.lasVegas;
import static week03.DijkstraTest.sanDiego;
import static week03.DijkstraTest.sanFrancisco;

import common.Node;
import org.junit.Test;

import java.util.List;

public class BfsTest {

    @Test
    public void bfsTest() {
        // notice that BFS counts only number of edges and not their weights!
        final List<Node> shortestPath = Bfs.bfs(graph, sanFrancisco, sanDiego);
        assertThat(shortestPath, is(asList(lasVegas, sanDiego)));
    }

}