package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.Collections;
import java.util.List;
public class KthLargestInArray {
  // The numbering starts from one, i.e., if A = [3,1,-1,2] then
  // findKthLargest(A, 1) returns 3, findKthLargest(A, 2) returns 2,
  // findKthLargest(A, 3) returns 1, and findKthLargest(A, 4) returns -1.
  @EpiTest(testDataFile = "kth_largest_in_array.tsv")
  public static int findKthLargest(int k, List<Integer> A) {
    if (k < 1 || k > A.size()) return 0;
    k--; // make it 0 index based
    int lo = 0, hi = A.size() - 1;
    while (lo < hi) {
      int p = partition(A, lo, hi);
      if (p < k) {
        lo = p + 1;
      } else if (p > k) {
        hi = p - 1;
      } else {
        return A.get(p);
      }
    }
    // lo >= hi
    // array size 1
    return A.get(k);
  }

  private static int partition(List<Integer> A, int lo, int hi) {
    // pay attention to s and e
    int s = lo, e = hi + 1;
    int v = A.get(lo);
    while (true) {
      // increase s first
      // stop at the element that <= v
      // avoid O(n^2) when there are large number of duplicates
      while (A.get(++s) > v) {
        if (s == hi) break;
      }
      // decrease e first
      // stop at the element that >= v
      while (A.get(--e) < v) {
        if (e == lo) break;
      }
      if (s >= e) break;
      Collections.swap(A, s, e);
    }
    Collections.swap(A, lo, e);
    return e;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "KthLargestInArray.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
