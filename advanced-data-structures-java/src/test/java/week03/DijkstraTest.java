package week03;

import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static week03.Dijkstra.dijkstra;

import common.Edge;
import common.Graph;
import common.Node;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

public class DijkstraTest {

    static final Node sanFrancisco = new Node(1, "San Francisco", 400, 500);
    static final Node lasVegas = new Node(2, "Las Vegas", 400, 50);
    static final Node losAngeles = new Node(3, "Los Angeles", 100, 100);
    static final Node laJolla = new Node(4, "La Jolla", 10, 24);
    static final Node sanDiego = new Node(5, "San Diego", 0, 20);
    static final Node tijuana = new Node(6, "Tijuana", 0, 0);

    static final Graph graph = new Graph(new HashMap<Node, List<Edge>>() {{

        put(sanFrancisco, asList(
                new Edge(sanFrancisco, losAngeles, 388),
                new Edge(sanFrancisco, lasVegas, 569)));
        put(losAngeles, asList(
                new Edge(losAngeles, sanFrancisco, 388),
                new Edge(losAngeles, lasVegas, 270),
                new Edge(losAngeles, laJolla, 112)));
        put(laJolla, asList(
                new Edge(laJolla, losAngeles, 112),
                new Edge(laJolla, sanDiego, 12)));
        put(sanDiego, asList(
                new Edge(sanDiego, lasVegas, 332),
                new Edge(sanDiego, laJolla, 12),
                new Edge(sanDiego, tijuana, 20)));
        put(tijuana, asList(new Edge(tijuana, sanDiego, 20)));
        put(lasVegas, asList(
                new Edge(lasVegas, sanFrancisco, 569),
                new Edge(lasVegas, losAngeles, 270),
                new Edge(lasVegas, sanDiego, 332)));
    }});


    @Test
    public void dijkstraShortestPath() {
        final List<Node> shortestPath = dijkstra(graph, sanFrancisco, sanDiego);
        assertThat(shortestPath, is(asList(sanFrancisco, losAngeles, laJolla, sanDiego)));
    }

    @Test
    public void dijkstra3ShortestPath() {
        final List<Node> shortestPath = Dijkstra3.dijkstra(graph, sanFrancisco, sanDiego);
        assertThat(shortestPath, is(asList(sanFrancisco, losAngeles, laJolla, sanDiego)));
    }

}