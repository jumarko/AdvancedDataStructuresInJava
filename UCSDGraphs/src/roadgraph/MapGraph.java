/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which reprsents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
package roadgraph;


import static java.util.stream.Collectors.toSet;

import com.oracle.jrockit.jfr.Producer;
import geography.GeographicPoint;
import util.GraphLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.IntFunction;

/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which represents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
public class MapGraph {

	// map graph is represented as an adjacency list because it is sparse
	private Map<MapNode, List<MapEdge>> graphAdjList = new HashMap<>();
	private int numVertices;
	private int numEdges;
	
	
	/** 
	 * Create a new empty MapGraph 
	 */
	public MapGraph()
	{
	}
	
	/**
	 * Get the number of vertices (road intersections) in the graph
	 * @return The number of vertices in the graph.
	 */
	public int getNumVertices()
	{
		return numVertices;
	}
	
	/**
	 * Return the intersections, which are the vertices in this graph.
	 * @return The vertices in this graph as GeographicPoints
	 */
	public Set<GeographicPoint> getVertices()
	{
		return graphAdjList.keySet().stream().map(MapNode::getLocation).collect(toSet());
	}
	
	/**
	 * Get the number of road segments in the graph
	 * @return The number of edges in the graph.
	 */
	public int getNumEdges()
	{
		return numEdges;
	}

	
	
	/** Add a node corresponding to an intersection at a Geographic Point
	 * If the location is already in the graph or null, this method does 
	 * not change the graph.
	 * @param location  The location of the intersection
	 * @return true if a node was added, false if it was not (the node
	 * was already in the graph, or the parameter is null).
	 */
	public boolean addVertex(GeographicPoint location)
	{
		if (location == null || graphAdjList.containsKey(new MapNode(location))) {
			return false;
		}

		// add a new node - initially without any neighbors
		graphAdjList.put(new MapNode(location), new ArrayList<>());
		numVertices++;
		return true;
	}
	
	/**
	 * Adds a directed edge to the graph from pt1 to pt2.  
	 * Precondition: Both GeographicPoints have already been added to the graph
	 * @param from The starting point of the edge
	 * @param to The ending point of the edge
	 * @param roadName The name of the road
	 * @param roadType The type of the road
	 * @param length The length of the road, in km
	 * @throws IllegalArgumentException If the points have not already been
	 *   added as nodes to the graph, if any of the arguments is null,
	 *   or if the length is less than 0.
	 */
	public void addEdge(GeographicPoint from, GeographicPoint to, String roadName,
			String roadType, double length) throws IllegalArgumentException {

		final MapNode startNode = new MapNode(from);
		final MapNode endNode = new MapNode(to);
		if (!graphAdjList.containsKey(startNode)) {
			throw new IllegalArgumentException("Point [" + from + "] has not been added to map graph yet.");
		}
		if (!graphAdjList.containsKey(endNode)) {
			throw new IllegalArgumentException("Point [" + to + "] has not been added to map graph yet.");
		}
		graphAdjList.get(startNode).add(new MapEdge(startNode, endNode, roadName, roadType, length));
		numEdges++;
	}
	

	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return bfs(start, goal, temp);
	}
	
	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, 
			 					     GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		final MapNode startNode = new MapNode(start);
		final MapNode goalNode = new MapNode(goal);

		checkBfsInput(startNode, goalNode);

		// use FIFO queue for BFS exploration
		final Queue<MapNode> toExplore = new LinkedList<>();
		final Set<MapNode> visited = new HashSet<>();
		final Map<MapNode, MapNode> parentMap = new HashMap<>();

		toExplore.add(startNode);
		visited.add(startNode);
		while (!toExplore.isEmpty()) {
			final MapNode currentNode = toExplore.remove();
			if (currentNode.equals(goalNode)) {
				// we have reach our destination
				// reconstruct the path
				return reconstructPath(startNode, goalNode, parentMap);
			}

			// all neighbors should be visited
			processNeighbors(nodeSearched, toExplore, visited, parentMap, currentNode);
		}

		System.out.println("No path exists");
		return new ArrayList<>();
	}

	private void checkBfsInput(MapNode startNode, MapNode goalNode) {
		if (!graphAdjList.containsKey(startNode)) {
			throw new IllegalArgumentException("start is not a valid vertex of map graph");
		}

		if (!graphAdjList.containsKey(goalNode)) {
			throw new IllegalArgumentException("goal is not a valid vertex of map graph");
		}
	}

	private void processNeighbors(Consumer<GeographicPoint> nodeSearched, Queue<MapNode> toExplore, Set<MapNode> visited,
								  Map<MapNode, MapNode> parentMap, MapNode currentNode) {
		for (MapEdge edge : graphAdjList.get(currentNode)) {
            final MapNode next = edge.getEnd();
            if (!visited.contains(next)) {
                toExplore.add(next);
                visited.add(next);
                parentMap.put(next, currentNode);
                nodeSearched.accept(next.getLocation());
            }
        }
	}

	private List<GeographicPoint> reconstructPath(MapNode start, MapNode goal, Map<MapNode, MapNode> parentMap) {
		LinkedList<GeographicPoint> path = new LinkedList<>();
		MapNode current = goal;
		while (!current.equals(start)) {
            path.addFirst(current.getLocation());
            MapNode next = parentMap.get(current);
			if (next == null) {
				throw new IllegalStateException("No parent found for node: " + current);
			}
			current = next;
        }
		path.addFirst(start.getLocation());
		return path;
	}


	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
		// You do not need to change this method.
        Consumer<GeographicPoint> temp = (x) -> {};
        return dijkstra(start, goal, temp);
	}
	
	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, 
										  GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// dijkstra's algorithm is a special case of A* where heuristic always returns 0 distance
		return shortestPathSearch(start, goal, nodeSearched, (v1, v2) -> 0.0);
	}

	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return aStarSearch(start, goal, temp);
	}
	
	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, 
											 GeographicPoint goal, Consumer<GeographicPoint> nodeSearched) {
		return shortestPathSearch(start, goal, nodeSearched, GeographicPoint::distance);
	}

	private List<GeographicPoint> shortestPathSearch(GeographicPoint start, GeographicPoint goal,
													 Consumer<GeographicPoint> nodeSearched,
													 BiFunction<GeographicPoint, GeographicPoint, Double> heuristicDistance) {
		final MapNode startNode = new MapNode(start);
		final MapNode goalNode = new MapNode(goal);

		Set<MapNode> visitedNodes = new HashSet<>();

		final PriorityQueue<NodeCost> frontier = new PriorityQueue<>();
		frontier.add(new NodeCost(startNode, 0.0));

		Map<MapNode, Double> distances = new HashMap<>(graphAdjList.size());
		for (MapNode mapNode : graphAdjList.keySet()) {
			distances.put(mapNode, Double.MAX_VALUE);
		}
		distances.put(startNode, 0.0);

		Map<MapNode, MapNode> parentMap = new HashMap<>(graphAdjList.size());

		while (!frontier.isEmpty()) {
			final MapNode current = frontier.remove().getNode();
			visitedNodes.add(current);

			// Hook for visualization.
			// Notice that if we put this hook into a for loop for visiting neighbors
			// than the numbers provided in comments in main method doesn't match
			// therefore it seems that Instructors want to hook it here
			nodeSearched.accept(current.getLocation());

			if (current.equals(goalNode)) {
				break;
			}

			for (MapEdge edge: graphAdjList.get(current)) {
				final MapNode neighbor = edge.getEnd();
				final double newDistance = distances.get(current) + edge.getLength();

				if (!visitedNodes.contains(neighbor)
						&& newDistance < distances.get(neighbor)) {
					// we have a shorter path so update the distance and put into the queue with proper priority
					distances.put(neighbor, newDistance);
					frontier.add(new NodeCost(neighbor, newDistance + heuristicDistance.apply(goal, neighbor.getLocation())));
					parentMap.put(neighbor, current);

				}
			}
		}

		return reconstructPath(startNode, goalNode, parentMap);
	}


	/**
	 * Prints the graph on the standard output.
	 */
	private void printGraph() {
		System.out.println("============== Map graph ==============");
		for (Map.Entry<MapNode, List<MapEdge>> vertexWithEdges : graphAdjList.entrySet()) {
			System.out.println("    Vertex: " + vertexWithEdges.getKey()
					+ "; edges: " + vertexWithEdges.getValue());
		}
		System.out.println("============== End Of Map graph ==============");
	}
	
	
	public static void main(String[] args)
	{
		System.out.print("Making a new map...");
		MapGraph firstMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", firstMap);
		System.out.println("DONE.");

		firstMap.printGraph();
		
		// You can use this method for testing.

		System.out.println();
		System.out.println("Doing BFS");
		System.out.println("========================");
		final List<GeographicPoint> shortestPath = firstMap.bfs(new GeographicPoint(4.0, 1.0),
				new GeographicPoint(8.0, -1.0),
				geoPoint -> System.out.println("BFS Visited: " + geoPoint));
		System.out.println("========================");
		System.out.println("Shortest path found: " + shortestPath);
		
		
		/* Here are some test cases you should try before you attempt 
		 * the Week 3 End of Week Quiz, EVEN IF you score 100% on the 
		 * programming assignment.
		 */

		System.out.println();
		System.out.println("Doing Dijkstra and A* on simpletest.map");
		System.out.println("========================");
		MapGraph simpleTestMap = new MapGraph();
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", simpleTestMap);
		
		GeographicPoint testStart = new GeographicPoint(1.0, 1.0);
		GeographicPoint testEnd = new GeographicPoint(8.0, -1.0);
		
		System.out.println("Test 1 using simpletest: Dijkstra should visit 9 nodes and AStar should visit 5 bodes");
		List<GeographicPoint> testRoute = simpleTestMap.dijkstra(testStart,testEnd,
				geoPoint -> System.out.println("Dijkstra Visited: " + geoPoint));
		List<GeographicPoint> testRoute2 = simpleTestMap.aStarSearch(testStart,testEnd,
				geoPoint -> System.out.println("A* Visited: " + geoPoint));
		System.out.println("Shortest path found by Dijkstra: " + testRoute);
		System.out.println("Shortest path found by A*: " + testRoute2);
		System.out.println("========================");
		

		System.out.println();
		System.out.println("Doing Dijkstra and A* on utc.map");
		System.out.println("========================");
		MapGraph testMap = new MapGraph();
		GraphLoader.loadRoadMap("data/maps/utc.map", testMap);

		System.out.println("A very simple test using real data...");
		testStart = new GeographicPoint(32.869423, -117.220917);
		testEnd = new GeographicPoint(32.869255, -117.216927);
		System.out.println("Test 2 using utc: Dijkstra should visit 13 nodes and AStar should visit 5 nodes");
		testRoute = testMap.dijkstra(testStart,testEnd,
				geoPoint -> System.out.println("Dijkstra Visited: " + geoPoint));
		testRoute2 = testMap.aStarSearch(testStart,testEnd,
				geoPoint -> System.out.println("A* Visited: " + geoPoint));
		System.out.println("Shortest path found by Dijkstra: " + testRoute);
		System.out.println("Shortest path found by A*: " + testRoute2);

		System.out.println();
		
		System.out.println("A slightly more complex test using real data");
		testStart = new GeographicPoint(32.8674388, -117.2190213);
		testEnd = new GeographicPoint(32.8697828, -117.2244506);
		System.out.println("Test 3 using utc: Dijkstra should visit 37 nodes and AStar should visit 10 nodes.");
		testRoute = testMap.dijkstra(testStart,testEnd,
				geoPoint -> System.out.println("Dijkstra Visited: " + geoPoint));
		testRoute2 = testMap.aStarSearch(testStart,testEnd,
				geoPoint -> System.out.println("A* Visited: " + geoPoint));
		System.out.println("Shortest path found by Dijkstra: " + testRoute);
		System.out.println("Shortest path found by A*: " + testRoute2);
		System.out.println("========================");

		System.out.println();
		System.out.println("Week 3 Quiz");
		testStart = new GeographicPoint(32.8648772, -117.2254046);
		testEnd = new GeographicPoint(32.8660691, -117.217393);

		testRoute = testMap.dijkstra(testStart,testEnd,
				geoPoint -> System.out.println("Dijkstra Visited: " + geoPoint));
		testRoute2 = testMap.aStarSearch(testStart,testEnd,
				geoPoint -> System.out.println("A* Visited: " + geoPoint));
		System.out.println("Shortest path found by Dijkstra: " + testRoute);
		System.out.println("Shortest path found by A*: " + testRoute2);
		System.out.println("========================");

		System.out.println("========================");

		
		
		/* Use this code in Week 3 End of Week Quiz */
		/*MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/maps/utc.map", theMap);
		System.out.println("DONE.");

		GeographicPoint start = new GeographicPoint(32.8648772, -117.2254046);
		GeographicPoint end = new GeographicPoint(32.8660691, -117.217393);
		
		
		List<GeographicPoint> route = theMap.dijkstra(start,end);
		List<GeographicPoint> route2 = theMap.aStarSearch(start,end);

		*/

	}
	
}
