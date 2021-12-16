import java.util.Iterator;
import java.util.Map;

public final class Johnson {
  // Source vertex for Bellman-Ford. Our nodes are represented as Integer objects.
  public static final Integer source = 999999;

  // All-pairs shortest path
  public static <Integer> DirectedGraph<Integer> shortestPath(DirectedGraph<Integer> graph) {

    // gPrime is a copy of the original graph + 1 vertex
    DirectedGraph<Object> graphCopy = gPrime(graph);

    // h(v) = delta(source, vertex)
    Map<Object, Double> hV = BellmanFord.shortestPaths(source, graphCopy);

    // Check for negative cycle
    for (Map.Entry<Object, Double> e : hV.entrySet()) {
      Double v = e.getValue();
      if (v < 0) {
        throw new IllegalArgumentException("Negative Cycle");
      }
    }

    // Reweight the original graph edges if there are negative paths
    DirectedGraph<Integer> transformedGraph = reweightGraph(graph, hV);

    // Initialize cost table and add vertices to it
    DirectedGraph<Integer> costTable = new DirectedGraph<>();
    {
      Iterator<Integer> iterator = graph.iterator();
      while (iterator.hasNext()) {
        Integer vertex = iterator.next();
        costTable.insert(vertex);
      }
    }

    // Run Dijkstra's (SSSP) for every vertex in |V| of G
    Iterator<Integer> iterator = graph.iterator();
    while (iterator.hasNext()) {
      Integer vertex = iterator.next();
      Map<Integer, Double> edgeWeight = Dijkstra.shortestPaths(transformedGraph, vertex);

      // Add the shortest path of all pairs to the cost table
      Iterator<Map.Entry<Integer, Double>> iter = edgeWeight.entrySet().iterator();
      while (iter.hasNext()) {
        Map.Entry<Integer, Double> entry = iter.next();
        Integer key = entry.getKey();
        Double value = entry.getValue();
        costTable.addEdge(vertex, key, value + hV.get(key) - hV.get(vertex));
      }
    }

    return costTable;
  }

  // Helper function to create dummy graph
  public static <Integer> DirectedGraph<Object> gPrime(DirectedGraph<Integer> graph) {
    DirectedGraph<Object> graphCopy = new DirectedGraph<>();

    // Add nodes from original graph to the augmented graph
    Iterator<Integer> iterator = graph.iterator();
    while (iterator.hasNext()) {
      Object vertex = iterator.next();
      graphCopy.insert(vertex);
    }

    // Add edges from the original graph to the augmented graph
    addEdges(graph, graphCopy);
    graphCopy.insert(source);

    // Connect the graph and edge weights = 0 in augmented graph
    Iterator<Integer> vertexIterator = graph.iterator();
    while (vertexIterator.hasNext()) {
      Object vertex = vertexIterator.next();
      graphCopy.addEdge(source, vertex, 0.0);
    }
    return graphCopy;
  }

  public static <Integer> DirectedGraph<Integer> reweightGraph(
      DirectedGraph<Integer> graph, Map<Object, Double> hV) {

    // Add nodes
    DirectedGraph<Integer> graphCopy = new DirectedGraph<>();
    {
      Iterator<Integer> iterator = graph.iterator();
      while (iterator.hasNext()) {
        Integer vertex = iterator.next();
        graphCopy.insert(vertex);
      }
    }

    // Get the edge weights and if they are negative, then set them equal to zero. If positive,
    // leave it alone
    Iterator<Integer> iterator = graph.iterator();
    while (iterator.hasNext()) {
      Integer vertex = iterator.next();
      for (Map.Entry<Integer, Double> entry : graph.edgesFrom(vertex).entrySet()) {
        Integer key = entry.getKey();
        Double value = entry.getValue();
        graphCopy.addEdge(vertex, key, value + hV.get(vertex) - hV.get(key));
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
