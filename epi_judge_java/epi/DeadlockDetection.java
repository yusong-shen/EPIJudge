package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.EpiUserType;
import epi.test_framework.GenericTest;
import epi.test_framework.TimedExecutor;

import java.util.*;

public class DeadlockDetection {

  public static class GraphVertex {
    public List<GraphVertex> edges;

    public GraphVertex() { edges = new ArrayList<>(); }
  }

  /**
   * High performance database systems use multiple processes and resource locking. These systems may not provide
   * mechanisms to avoid or prevent deadlock: a situation in which two or more competing actions are each waiting for
   * the other to finish, which precludes all these actions from progressing. Such systems must support a mechanism to
   * detect deadlocks, as well as an algorithm for recovering from them.
   *
   * One deadlock detection algorithm makes use of a "wait-for" graph to track which other processes a process is
   * currently blocking on. In a wait-for graph, processes are represented as nodes, and an edge from process P to 0
   * implies 0 is holding a resource that P needs and thus P is waiting for 0 to release its lock on that resource.
   * A cycle in this graph implies the possibility of a deadlock. This motivates the following problem.
   * Write a program that takes as input a directed graph and checks if the graph contains a cycle.
   * @param graph
   * @return
   */
  public static boolean isDeadlocked(List<GraphVertex> graph) {
    // TODO - you fill in here.
    if (graph.isEmpty()) return false;
    // 0 : not visited
    // 1 : visited, but not yet complete processing
    // 2 : complete processing (visit all its following edges)
    Map<GraphVertex, Integer> nodeStatus = new HashMap<>();
    for (GraphVertex node: graph) {
      nodeStatus.put(node, 0);
    }
    for (GraphVertex node: graph) {
      if (nodeStatus.get(node) == 0 && hasCycle(nodeStatus, node)) {
        return true;
      }
    }
    return false;
  }

  private static boolean hasCycle(Map<GraphVertex, Integer> nodeStatus, GraphVertex node) {
    if (nodeStatus.get(node) == 1) return true;
    nodeStatus.put(node, 1);
    for (GraphVertex next : node.edges) {
      if (hasCycle(nodeStatus, next)) {
        return true;
      }
    }
    nodeStatus.put(node, 2);
    return false;
  }


  @EpiUserType(ctorParams = {int.class, int.class})
  public static class Edge {
    public int from;
    public int to;

    public Edge(int from, int to) {
      this.from = from;
      this.to = to;
    }
  }

  @EpiTest(testDataFile = "deadlock_detection.tsv")
  public static boolean isDeadlockedWrapper(TimedExecutor executor,
                                            int numNodes, List<Edge> edges)
      throws Exception {
    if (numNodes <= 0) {
      throw new RuntimeException("Invalid numNodes value");
    }
    List<GraphVertex> graph = new ArrayList<>();
    for (int i = 0; i < numNodes; i++) {
      graph.add(new GraphVertex());
    }
    for (Edge e : edges) {
      if (e.from < 0 || e.from >= numNodes || e.to < 0 || e.to >= numNodes) {
        throw new RuntimeException("Invalid vertex index");
      }
      graph.get(e.from).edges.add(graph.get(e.to));
    }

    return executor.run(() -> isDeadlocked(graph));
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "DeadlockDetection.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
