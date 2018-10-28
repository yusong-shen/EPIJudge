package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class NextPermutation {
  @EpiTest(testDataFile = "next_permutation.tsv")
  public static List<Integer> nextPermutation(List<Integer> perm) {
    // scan from right to left, find the first decreasing number
    int i = perm.size() - 2;
    while (i >= 0 && perm.get(i) >= perm.get(i + 1)) {
      i--;
    }
    if (i == -1) return Collections.emptyList();

    for (int j = perm.size() - 1; j > i; j--) {
      if (perm.get(j) > perm.get(i)) {
        Collections.swap(perm, j, i);
        break;
      }
    }
    reverse(perm, i + 1, perm.size() - 1);
//    Collections.reverse(perm.subList(i + 1, perm.size()));
    return perm;
  }

  private static void reverse(List<Integer> perm, int start, int end) {
    if (start >= end) return;
    while (start < end) {
      Collections.swap(perm, start++, end--);
    }

  }


  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "NextPermutation.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
