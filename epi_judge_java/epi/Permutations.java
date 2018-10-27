package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.EpiTestComparator;
import epi.test_framework.LexicographicalListComparator;
import epi.test_framework.GenericTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiPredicate;
public class Permutations {
  @EpiTest(testDataFile = "permutations.tsv")

  public static List<List<Integer>> permutations(List<Integer> A) {
    return permutations(A, A.size());
  }

  private static List<List<Integer>> permutations(List<Integer> A, int len) {
    List<List<Integer>> result = new ArrayList<>();
    if (len == 0) {
      result.add(new ArrayList<>());
      return result;
    }
    if (len == 1) {
      result.add(new ArrayList<>(A.subList(0, 1)));
      return result;
    }
    List<List<Integer>> prevRet = permutations(A, len - 1);
    int v = A.get(len - 1);
    for (List<Integer> p : prevRet) {
      for (int i = 0; i <= p.size(); i++) {
        List<Integer> newP = new ArrayList<>(p);
        newP.add(i, v);
        result.add(newP);
      }
    }

    return result;
  }

  @EpiTestComparator
  public static BiPredicate<List<List<Integer>>, List<List<Integer>>> comp =
      (expected, result) -> {
    if (result == null) {
      return false;
    }
    for (List<Integer> l : expected) {
      Collections.sort(l);
    }
    expected.sort(new LexicographicalListComparator<>());
    for (List<Integer> l : result) {
      Collections.sort(l);
    }
    result.sort(new LexicographicalListComparator<>());
    return expected.equals(result);
  };

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "Permutations.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
