package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.EpiTestComparator;
import epi.test_framework.LexicographicalListComparator;
import epi.test_framework.GenericTest;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;
public class NQueens {
  @EpiTest(testDataFile = "n_queens.tsv")

  public static List<List<Integer>> nQueens(int n) {
    // TODO - you fill in here.
    List<List<Integer>> result = new ArrayList<>();
    List<Integer> colPlacement = new ArrayList<>();
    solve(n, 0, colPlacement, result);
    return result;
  }

  /**
   * Try each row
   * @param n
   * @param rows number of rows that legally placed
   * @param colPlacement ith element in colPlacement : column number that queen placed in ith row
   * @param ret
   */
  private static void solve(int n, int rows, List<Integer> colPlacement, List<List<Integer>> ret) {
    if (rows == n) {
      // need to make a copy of colPlacement
      ret.add(new ArrayList<>(colPlacement));
      return;
    }
    for (int col = 0; col < n; col++) {
      colPlacement.add(col);
      if (isValid(colPlacement)) {
        solve(n, rows + 1, colPlacement, ret);
      }
      // backtrack
      colPlacement.remove(colPlacement.size() - 1);
    }

  }

  // test if a newly placed queen conflicted with previous queens
  private static boolean isValid(List<Integer> cols) {
    int newRowId = cols.size() - 1;
    for (int i = 0; i < cols.size() - 1; i++) {
      int diff = Math.abs(cols.get(i) - cols.get(newRowId));
      if (diff == 0 || diff == newRowId - i) return false;
    }
    return true;
  }

  @EpiTestComparator
  public static BiPredicate<List<List<Integer>>, List<List<Integer>>> comp =
      (expected, result) -> {
    if (result == null) {
      return false;
    }
    expected.sort(new LexicographicalListComparator<>());
    result.sort(new LexicographicalListComparator<>());
    return expected.equals(result);
  };

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "NQueens.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
