package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.ArrayList;
import java.util.List;
public class KthLargestElementInTwoSortedArrays {
  @EpiTest(testDataFile = "kth_largest_element_in_two_sorted_arrays.tsv")

  public static int findKthNTwoSortedArrays(List<Integer> A, List<Integer> B,
                                            int k) {
    return findKthSmallest(A, B, k);
  }

  private static int findKthSmallest(List<Integer> A, List<Integer> B, int k) {
      if (k <= 0 || k > A.size() + B.size()) return -1;
      if (A.size() > B.size()) {
          return findKthNTwoSortedArrays(B, A, k);
      }
      if (A.isEmpty()) {
          return B.get(k - 1);
      }
      if (k == 1) {
          return Math.min(A.get(0), B.get(0));
      }
      int ma = Math.min(k / 2, A.size());
      int mb = Math.min(k / 2, B.size());
      if (A.get(ma - 1) < B.get(mb - 1)) {
          return findKthSmallest(A.subList(ma, A.size()), B, k - ma);
      }
      // A.get(ma - 1) > B.get(mb - 1)
      return findKthSmallest(A, B.subList(mb, B.size()), k - mb);
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "KthLargestElementInTwoSortedArrays.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
