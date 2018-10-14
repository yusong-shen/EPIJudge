package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.*;

public class StringTransformability {

  @EpiTest(testDataFile = "string_transformability.tsv")

  // Uses BFS to find the least steps of transformation.
  public static int transformString(Set<String> D, String s, String t) {
      // TODO - you fill in here.
      Map<String, Set<String>> graph = buildGraph(D, s);
      return bfs(graph, s, t);
  }

  private static Map<String, Set<String>> buildGraph(Set<String> D, String src) {
      D.add(src);
      Map<String, Set<String>> graph = new HashMap<>();
      for (String s1 : D) {
          graph.put(s1, new HashSet<>());
          for (String s2 : D) {
              if (s1.equals(s2)) continue;
              if (isConnected(s1, s2)) {
                  graph.getOrDefault(s1, new HashSet<>()).add(s2);
              }
          }
      }
      return graph;
  }

  private static int bfs(Map<String, Set<String>> graph, String src, String dest) {
      int length = 0;
      boolean canReach = false;
      Set<String> visited = new HashSet<>();
      Queue<String> q = new LinkedList<>();
      q.offer(src);
      while (!q.isEmpty()) {
          int size = q.size();
          for (int i = 0; i < size; i++) {
              String cur = q.poll();
              visited.add(cur);
              if (cur.equals(dest)) {
                  return length;
              }
              for (String next : graph.get(cur)) {
                  if (!visited.contains(next)) {
                      q.offer(next);
                  }
              }
          }
          length++;
      }

      return canReach ? length : -1;
  }

  private static boolean isConnected(String a, String b) {
      if (a.length() != b.length()) {
          return false;
      }
      int mismatch = 0;
      for (int i = 0; i < a.length(); i++) {
          if (a.charAt(i) != b.charAt(i)) mismatch++;
          if (mismatch > 1) return false;
      }
      return  mismatch == 1;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "StringTransformability.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
