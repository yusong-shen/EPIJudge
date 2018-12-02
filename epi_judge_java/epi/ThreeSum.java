package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.Collections;
import java.util.List;
public class ThreeSum {
  @EpiTest(testDataFile = "three_sum.tsv")

  public static boolean hasThreeSum(List<Integer> A, int t) {
    Collections.sort(A);
    for (int num : A) {
      if (hasTwoSum(A, t - num)) return true;
    }

    return false;
  }

  public static boolean hasTwoSum(List<Integer> A, int t) {
    int i = 0, j = A.size() - 1;
    while (i <= j) {
      int sum = A.get(i) + A.get(j);
      if (sum == t) {
        return true;
      } else if (sum < t) {
        i++;
      } else {
        j--;
      }
    }
    return false;
  }


  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "ThreeSum.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
