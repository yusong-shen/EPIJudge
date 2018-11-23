package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.EpiTestComparator;
import epi.test_framework.GenericTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiPredicate;
public class KLargestValuesInBst {
  @EpiTest(testDataFile = "k_largest_values_in_bst.tsv")

  public static List<Integer> findKLargestInBst(BstNode<Integer> tree, int k) {
    List<Integer> kLargest = new ArrayList<>();
    findKLargestInBstHelper(tree, k, kLargest);
    return kLargest;
  }

  private static void findKLargestInBstHelper(BstNode<Integer> tree, int k, List<Integer> kLargest) {
    if (tree != null && kLargest.size() < k) {
      findKLargestInBstHelper(tree.right, k, kLargest);
      if (kLargest.size() < k) {
        kLargest.add(tree.data);
        findKLargestInBstHelper(tree.left, k, kLargest);
      }
    }

  }
  @EpiTestComparator
  public static BiPredicate<List<Integer>, List<Integer>> comp =
      (expected, result) -> {
    if (result == null) {
      return false;
    }
    Collections.sort(expected);
    Collections.sort(result);
    return expected.equals(result);
  };

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "KLargestValuesInBst.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
