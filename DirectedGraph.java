/*
 * File: DirectedGraph.java
 * Author: Keith Schwarz (htiek@cs.stanford.edu)
 *
 * A class representing a directed graph where each edge has an associated
 * real-valued length. Internally, the class is represented by an adjacency
 * list.
 */
import java.util.*; // For HashMap

public final class DirectedGraph<Integer> implements Iterable<Integer> {
  private int nodeCount = 0;
  private int edgeCount = 0;

  /* A map from nodes in the graph to sets of outgoing edges. Each
   * set of edges is represented by a map from edges to doubles.
   */
  private final Map<Integer, Map<Integer, Double>> mGraph = new HashMap<>();

  /**
   * Adds a new node to the graph. If the node already exists, this function is a no-op.
   *
   * @param node The node to add.
   */
  public void addNode(Integer node) {
    /* If the node already exists, don't do anything. */
    if (mGraph.containsKey(node)) return;

    /* Otherwise, add the node with an empty set of outgoing edges. */
    this.mGraph.put(node, new HashMap<>());
    this.nodeCount++;
  }

  /**
   * Given a start node, destination, and length, adds an arc from the start node to the destination
   * of the length. If an arc already existed, the length is updated to the specified value. If
   * either endpoint does not exist in the graph, throws a NoSuchElementException.
   *
   * @param start The start node.
   * @param dest The destination node.
   * @param length The length of the edge.
   * @throws NoSuchElementException If either the start or destination nodes do not exist.
   */
  public void addEdge(Integer start, Integer dest, double length) {
    /* Confirm both endpoints exist. */
    if (!mGraph.containsKey(start) || !mGraph.containsKey(dest))
      throw new NoSuchElementException("Both nodes must be in the graph.");

    /* Add the edge. */
    this.mGraph.get(start).put(dest, length);
    this.edgeCount++;
  }

  /**
   * Removes the edge from start to dest from the graph. If the edge does not exist, this operation
   * is a no-op. If either endpoint does not exist, this throws a NoSuchElementException.
   *
   * @param start The start node.
   * @param dest The destination node.
   * @throws NoSuchElementException If either node is not in the graph.
   */
  public void removeEdge(Integer start, Integer dest) {
    /* Confirm both endpoints exist. */
    if (!mGraph.containsKey(start) || !mGraph.containsKey(dest))
      throw new NoSuchElementException("Both nodes must be in the graph.");

    mGraph.get(start).remove(dest);
    this.edgeCount--;
  }

  /**
   * Given a node in the graph, returns an immutable view of the edges leaving that node, as a map
   * from endpoints to costs.
   *
   * @param node The node whose edges should be queried.
   * @return An immutable view of the edges leaving that node.
   * @throws NoSuchElementException If the node does not exist.
   */
  public Map<Integer, Double> edgesFrom(Integer node) {
    /* Check that the node exists. */
    Map<Integer, Double> arcs = mGraph.get(node);
    if (arcs == null) throw new NoSuchElementException("Source node does not exist.");

    return Collections.unmodifiableMap(arcs);
  }

  /**
   * Returns an iterator that can traverse the nodes in the graph.
   *
   * @return An iterator that traverses the nodes in the graph.
   */
  public Iterator<Integer> iterator() {
    return mGraph.keySet().iterator();
  }

  public int getEdgeCount() {
    return this.edgeCount;
  }

  public int getNodeCount() {
    return this.nodeCount;
  }

  @Override
  public String toString() {
    for (int i = 0; i < this.mGraph.size(); i++) {
      System.out.println("Node " + i + " -> " + mGraph.get(i));
    }
    return "\nnodeCount = " + nodeCount + ", edgeCount = " + edgeCount;
  }
}
