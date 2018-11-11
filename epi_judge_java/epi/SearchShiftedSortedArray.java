package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import java.util.List;
public class SearchShiftedSortedArray {
  @EpiTest(testDataFile = "search_shifted_sorted_array.tsv")

  // elements are all distinct
  public static int searchSmallest(List<Integer> A) {
    int l = 0, r = A.size() - 1;
    while (l < r) {
      int mid = l + (r - l) / 2;
      // since mid is round down, we need to compare with A[r]
      // think about a 2 element case like [3, 4] or [4, 3]
      if (A.get(mid) > A.get(r)) {
        l = mid + 1;
      } else {
        r = mid;
      }
    }
    return r;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "SearchShiftedSortedArray.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
