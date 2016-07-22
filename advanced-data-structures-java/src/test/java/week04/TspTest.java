package week04;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import common.Edge;
import common.Graph;
import common.Node;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

public class TspTest {

    private final Node povazskaBystrica = new Node(0, "Povazska Bystrica");
    private final Node zilina = new Node(1, "Zilina");
    private final Node trencin = new Node(2, "Trencin");
    private final Node banskaBystrica = new Node(3, "Banska Bystrica");
    private final Node bratislava = new Node(4, "Bratislava");
    private final Node poprad = new Node(5, "Poprad");
    private final Node presov = new Node(6, "Presov");
    private final Node kosice = new Node(7, "Kosice");


    private final Graph slovakCities = new Graph(new HashMap<Node, List<Edge>>() {{
        put(povazskaBystrica, asList(
                new Edge(povazskaBystrica, zilina, 33000),
                new Edge(povazskaBystrica, trencin, 46000),
                new Edge(povazskaBystrica, banskaBystrica, 124000),
                new Edge(povazskaBystrica, bratislava, 170000),
                new Edge(povazskaBystrica, poprad, 173000),
                new Edge(povazskaBystrica, presov, 249000),
                new Edge(povazskaBystrica, kosice, 285000)
        ));
        put(zilina, asList(
                new Edge(zilina, povazskaBystrica, 33000),
                new Edge(zilina, trencin, 82900),
                new Edge(zilina, banskaBystrica, 89800),
                new Edge(zilina, bratislava, 203000),
                new Edge(zilina, poprad, 140000),
                new Edge(zilina, presov, 215000),
                new Edge(zilina, kosice, 251000)
        ));
        put(trencin, asList(
                new Edge(trencin, povazskaBystrica, 46000),
                new Edge(trencin, zilina, 82900),
                new Edge(trencin, banskaBystrica, 141000),
                new Edge(trencin, bratislava, 131000),
                new Edge(trencin, poprad, 221000),
                new Edge(trencin, presov, 296000),
                new Edge(trencin, kosice, 333000)
        ));
        put(banskaBystrica, asList(
                new Edge(banskaBystrica, povazskaBystrica, 124000),
                new Edge(banskaBystrica, zilina, 89800),
                new Edge(banskaBystrica, trencin, 141000),
                new Edge(banskaBystrica, bratislava, 212000),
                new Edge(banskaBystrica, poprad, 117000),
                new Edge(banskaBystrica, presov, 191000),
                new Edge(banskaBystrica, kosice, 228000)
        ));
        put(bratislava, asList(
                new Edge(bratislava, povazskaBystrica, 170000),
                new Edge(bratislava, trencin, 131000),
                new Edge(bratislava, banskaBystrica, 212000),
                new Edge(bratislava, zilina, 203000),
                new Edge(bratislava, poprad, 328000),
                new Edge(bratislava, presov, 403000),
                new Edge(bratislava, kosice, 403000)
        ));
        put(poprad, asList(
                new Edge(poprad, povazskaBystrica, 173000),
                new Edge(poprad, trencin, 221000),
                new Edge(poprad, banskaBystrica, 117000),
                new Edge(poprad, zilina, 140000),
                new Edge(poprad, bratislava, 328000),
                new Edge(poprad, presov, 82000),
                new Edge(poprad, kosice, 119000)
        ));
        put(presov, asList(
                new Edge(presov, povazskaBystrica, 249000),
                new Edge(presov, trencin, 296000),
                new Edge(presov, banskaBystrica, 191000),
                new Edge(presov, zilina, 215000),
                new Edge(presov, bratislava, 403000),
                new Edge(presov, poprad, 82000),
                new Edge(presov, kosice, 36500)
        ));
        put(kosice, asList(
                new Edge(kosice, povazskaBystrica, 285000),
                new Edge(kosice, trencin, 333000),
                new Edge(kosice, banskaBystrica, 228000),
                new Edge(kosice, zilina, 251000),
                new Edge(kosice, bratislava, 403000),
                new Edge(kosice, poprad, 119000),
                new Edge(kosice, presov, 36500)
        ));
    }});

    @Test
    public void bruteForce() {
        final List<Node> tspPath = Tsp.bruteForce(slovakCities, povazskaBystrica);

        // Originally, I proposed this path as optimal (distance 938.3 km)
//        assertThat(tspPath, is(asList(
//                povazskaBystrica,
//                zilina,
//                banskaBystrica,
//                poprad,
//                presov,
//                kosice,
//                bratislava,
//                trencin,
//                povazskaBystrica
//        )));

        // However, this one is shorter (distance 908.5 km)
        assertThat(tspPath, is(asList(
                povazskaBystrica,
                zilina,
                poprad,
                presov,
                kosice,
                banskaBystrica,
                bratislava,
                trencin,
                povazskaBystrica
        )));
    }

    @Test
    public void bestNextChoice() {
        final List<Node> tspPath = Tsp.bestNextChoice(slovakCities, povazskaBystrica);
        assertThat(tspPath, is(asList(
                povazskaBystrica,
                zilina,
                trencin,
                bratislava,
                banskaBystrica,
                poprad,
                presov,
                kosice,
                povazskaBystrica
        )));
    }

}