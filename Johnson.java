import java.util.Iterator;
import java.util.Map;

public final class Johnson {
  // Source vertex for Bellman-Ford. Our nodes are represented as Integer objects.
  public static final Integer source = 999999;

  // All-pairs shortest path
  public static <Integer> DirectedGraph<Integer> shortestPath(DirectedGraph<Integer> graph) {

    // The augmented graph is a copy of the original graph + 1 vertex
    DirectedGraph<Object> graphCopy = augmentGraph(graph);

    // h(v) = delta(source, vertex)
    Map<Object, Double> potential = BellmanFord.shortestPaths(source, graphCopy);

    // Check for negative cycle
    for (Map.Entry<Object, Double> e : potential.entrySet()) {
      Double v = e.getValue();
      if (v < 0) {
        throw new IllegalArgumentException("Negative");
      }
    }

    // Reweight the original graph edges if there are negative paths
    DirectedGraph<Integer> transformedGraph = reweightGraph(graph, potential);

    // Initialize cost table and add vertices to it
    DirectedGraph<Integer> costTable = new DirectedGraph<>();
    for (Integer vertex : graph) {
      costTable.addNode(vertex);
    }

    // Run Dijkstra's (SSSP) for every vertex in |V| of G
    for (Integer vertex : graph) {
      Map<Integer, Double> edgeWeight = Dijkstra.shortestPaths(transformedGraph, vertex);

      // Add the shortest path of all pairs to the cost table
      for (Map.Entry<Integer, Double> entry : edgeWeight.entrySet()) {
        Integer key = entry.getKey();
        Double value = entry.getValue();
        costTable.addEdge(vertex, key, value + potential.get(key) - potential.get(vertex));
      }
    }

    return costTable;
  }

  // Helper function to create dummy graph
  public static <Integer> DirectedGraph<Object> augmentGraph(DirectedGraph<Integer> graph) {
    DirectedGraph<Object> graphCopy = new DirectedGraph<>();

    // Add nodes from original graph to the augmented graph
    Iterator<Integer> iterator = graph.iterator();
    while (iterator.hasNext()) {
      Object vertex = iterator.next();
      graphCopy.addNode(vertex);
    }

    // Add edges from the original graph to the augmented graph
    addEdges(graph, graphCopy);
    graphCopy.addNode(source);

    // Connect the graph and edge weights = 0 in augmented graph
    Iterator<Integer> vertexIterator = graph.iterator();
    while (vertexIterator.hasNext()) {
      Object vertex = vertexIterator.next();
      graphCopy.addEdge(source, vertex, 0.0);
    }
    return graphCopy;
  }

  public static <Integer> DirectedGraph<Integer> reweightGraph(
      DirectedGraph<Integer> graph, Map<Object, Double> potential) {

    DirectedGraph<Integer> graphCopy = new DirectedGraph<>();
    {
      Iterator<Integer> iterator = graph.iterator();
      while (iterator.hasNext()) {
        Integer vertex = iterator.next();
        graphCopy.addNode(vertex);
      }
    }

    Iterator<Integer> iterator = graph.iterator();
    while (iterator.hasNext()) {
      Integer vertex = iterator.next();
      for (Map.Entry<Integer, Double> entry : graph.edgesFrom(vertex).entrySet()) {
        Integer key = entry.getKey();
        Double value = entry.getValue();
        graphCopy.addEdge(vertex, key, value + potential.get(vertex) - potential.get(key));
      }
    }

    return graphCopy;
  }

  // Helper function to add edges to the graph
  public static <Integer> void addEdges(DirectedGraph<Integer> g, DirectedGraph<Object> gCopy) {
    Iterator<Integer> edgeIterator = g.iterator();
    while (edgeIterator.hasNext()) {
      Integer vertex = edgeIterator.next();
      for (Map.Entry<Integer, Double> entry : g.edgesFrom(vertex).entrySet()) {
        Integer key = entry.getKey();
        Double value = entry.getValue();
        gCopy.addEdge(vertex, key, value);
      }
    }
  }
}
