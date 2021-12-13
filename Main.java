public class Main {
  public static void main(String[] args) {
    // Create a directed graph
    DirectedGraph<Integer> directedGraph = new DirectedGraph<>();

    // Add nodes [1, 2, 3, 4, 5, 6, 7]
    for (int i = 0; i < 8; i++) {
      directedGraph.addNode(i);
    }

    // Add edges
    directedGraph.addEdge(0, 3, 6);
    directedGraph.addEdge(1, 0, 4);
    directedGraph.addEdge(1, 3, 8);
    directedGraph.addEdge(1, 5, 1);
    directedGraph.addEdge(2, 0, 2);
    directedGraph.addEdge(2, 4, 5);
    directedGraph.addEdge(3, 5, 5);
    directedGraph.addEdge(3, 7, 8);
    directedGraph.addEdge(4, 7, 6);

    System.out.println(directedGraph);

    System.out.println(Johnson.shortestPaths(directedGraph));
  }
}
