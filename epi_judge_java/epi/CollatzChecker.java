package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class CollatzChecker {
  @EpiTest(testDataFile = "collatz_checker.tsv")

  /**
   * The Collatz conjecture is the following: Take any natural number. If it is odd, triple it and add one; if it
   * is even, halve it. Repeat the process indefinitely. No matter what number you begin with, you will eventually
   * arrive at 1.
   * As an example, if we start with 11 we get the sequence 11,34,17,52,26,13,40, 20,10,5,16,8,4,2,1. Despite intense
   * efforts, the Collatz conjecture has not been proved or disproved.
   * Suppose you were given the task of checking the Collatz conjecture for the first billion integers. A direct
   * approach would be to compute the convergence sequence for each number in this set.
   * Test the Collatz conjecture for the first n positive integers.
   */
  public static boolean testCollatzConjecture(int n) {
    Map<Long, Boolean> map = new HashMap<>();
    for (long i = 1l; i <= n; i++) {
      if (!simulateCollatez(i, map)) return false;
    }
//    System.out.printf("n : %d, map size : %d\n", n, map.size());
    return true;
  }

  private static boolean simulateCollatez(long i, Map<Long, Boolean> map) {
    if (map.containsKey(i)) return map.get(i);
    if (i == 1l) {
      map.put(1l, true);
      return true;
    }
    if (i % 2 == 0) {
      boolean ret = simulateCollatez(i / 2, map);
      map.put(i, ret);
      return ret;
    }
    if (i % 2 == 1) {
      boolean ret = simulateCollatez(i * 3 + 1, map);
      map.put(i, ret);
      return ret;
    }
    boolean ret = false;
    map.put(i, ret);
    return ret;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "CollatzChecker.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
