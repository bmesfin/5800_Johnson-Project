public class Main {
  public static void main(String[] args) {
    // Create a directed graph
    DirectedGraph<Integer> directedGraph = new DirectedGraph<>();

    // Add nodes [0...7]
    for (int i = 0; i < 8; i++) {
      directedGraph.insert(i);
    }

    // Add edges
    directedGraph.addEdge(2, 6, 6);
    directedGraph.addEdge(3, 1, 3);
    directedGraph.addEdge(4, 6, 3);
    directedGraph.addEdge(4, 7, 9);
    directedGraph.addEdge(5, 7, 7);
    directedGraph.addEdge(5, 1, 2);
    directedGraph.addEdge(5, 3, 5);
    directedGraph.addEdge(6, 2, 8);
    directedGraph.addEdge(6, 4, 9);
    directedGraph.addEdge(7, 5, 8);


    System.out.println(directedGraph);

    System.out.println(Johnson.shortestPath(directedGraph));
  }
}
