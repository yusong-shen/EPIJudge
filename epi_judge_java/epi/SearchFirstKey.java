package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import java.util.List;
public class SearchFirstKey {
  @EpiTest(testDataFile = "search_first_key.tsv")

  public static int searchFirstOfK(List<Integer> A, int k) {
    if (A.isEmpty()) return -1;
    int l = 0, r = A.size() - 1;
    while (l + 1 < r) {
      int mid = l + (r - l) / 2;
      if (A.get(mid) < k) {
        l = mid;
      } else {
        r = mid;
      }
    }
    if (A.get(l) == k) return l;
    if (A.get(r) == k) return r;
    return -1;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "SearchFirstKey.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
