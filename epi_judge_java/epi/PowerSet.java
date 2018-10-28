package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.EpiTestComparator;
import epi.test_framework.LexicographicalListComparator;
import epi.test_framework.GenericTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiPredicate;
public class PowerSet {
  @EpiTest(testDataFile = "power_set.tsv")

  public static List<List<Integer>> generatePowerSet(List<Integer> inputSet) {
    List<Integer> curSet = new ArrayList<>();
    List<List<Integer>> ret = new ArrayList<>();
    powerSet(0, inputSet, curSet, ret);
    return ret;
  }

  private static void powerSet(int start, List<Integer> inputSet, List<Integer> curSet, List<List<Integer>> ret) {
    ret.add(new ArrayList<>(curSet));
    for (int i = start; i < inputSet.size(); i++) {
      curSet.add(inputSet.get(i));
      powerSet(i + 1, inputSet, curSet, ret);
      curSet.remove(curSet.remove(curSet.size() - 1));
    }

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
            .runFromAnnotations(args, "PowerSet.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
