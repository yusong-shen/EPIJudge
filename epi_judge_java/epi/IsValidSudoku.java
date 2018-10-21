package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import java.util.List;
public class IsValidSudoku {
  @EpiTest(testDataFile = "is_valid_sudoku.tsv")

  // Check if a partially filled matrix has any conflicts.
  public static boolean isValidSudoku(List<List<Integer>> partialAssignment) {
    boolean[] marked = new boolean[10];
    // rows
    for (List<Integer> row : partialAssignment) {
      marked = new boolean[10];
      for (int v : row) {
        if (v == 0) continue;
        if (marked[v]) return false;
        marked[v] = true;
      }
    }
    // cols
    for (int j = 0; j < 9; j++) {
      marked = new boolean[10];
      for (int i = 0; i < 9; i++) {
        int v = partialAssignment.get(i).get(j);
        if (v == 0) continue;
        if (marked[v]) return false;
        marked[v] = true;
      }
    }

    // 9 * 3x3 submatrix
    for (int k = 0; k < 9; k++) {
      int x = (k % 3) * 3;
      int y = (k / 3) * 3;
      marked = new boolean[10];
      for (int i = x; i < x + 3; i++) {
        for (int j = y; j < y + 3; j++) {
          int v = partialAssignment.get(i).get(j);
          if (v == 0) continue;
          if (marked[v]) return false;
          marked[v] = true;
        }
      }
    }

    return true;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "IsValidSudoku.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
