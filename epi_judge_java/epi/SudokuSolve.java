package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import epi.test_framework.TestFailure;
import epi.test_framework.TimedExecutor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
public class SudokuSolve {
  public static boolean solveSudoku(List<List<Integer>> partialAssignment) {

    return backtrackSolver(0, 0, partialAssignment);
  }

  private static boolean backtrackSolver(int i, int j, List<List<Integer>> partialAssignment) {
    // base case :
    if (i == partialAssignment.size()) {
      // if i reach the end of current column, try new column
      i = 0;
      j++;
      // if the entire board is filled, we have found a solution, return true
      if (j == partialAssignment.get(0).size()) return true;
    }

    // try each empty entry remaining
    // skip filled entry
    if (partialAssignment.get(i).get(j) != 0) {
      return backtrackSolver(i + 1, j, partialAssignment);
    };
    for (int v = 1; v <= 9; v++) {
      if (!isValidToAdd(i, j, v, partialAssignment)) continue;
      partialAssignment.get(i).set(j, v);
      if (backtrackSolver(i + 1, j, partialAssignment)) return true;
    }

    // undo assignment, backtrack
    partialAssignment.get(i).set(j, 0);
    return false;
  }

  private static boolean isValidToAdd(int i, int j, int val, List<List<Integer>> partialAssignment) {
    // row
    for (List<Integer> row : partialAssignment) {
      if (val == row.get(j)) return false;
    }
    // column
    for (int c = 0; c < partialAssignment.get(0).size(); c++) {
      if (val == partialAssignment.get(i).get(c)) return false;
    }

    // submatrix
    // locate the specific submatrix
    int x = i / 3, y = j / 3;
    for (int r = x * 3; r < (x + 1) * 3; r++) {
      for (int c = y * 3; c < (y + 1) * 3; c++) {
          if (val == partialAssignment.get(r).get(c)) return false;
        }
    }

    return true;
  }

  @EpiTest(testDataFile = "sudoku_solve.tsv")
  public static void solveSudokuWrapper(TimedExecutor executor,
                                        List<List<Integer>> partialAssignment)
      throws Exception {
    List<List<Integer>> solved = new ArrayList<>();
    for (List<Integer> row : partialAssignment) {
      solved.add(new ArrayList<>(row));
    }

    executor.run(() -> solveSudoku(solved));

    if (partialAssignment.size() != solved.size()) {
      throw new TestFailure("Initial cell assignment has been changed");
    }

    for (int i = 0; i < partialAssignment.size(); i++) {
      List<Integer> br = partialAssignment.get(i);
      List<Integer> sr = solved.get(i);
      if (br.size() != sr.size()) {
        throw new TestFailure("Initial cell assignment has been changed");
      }
      for (int j = 0; j < br.size(); j++)
        if (br.get(j) != 0 && !Objects.equals(br.get(j), sr.get(j)))
          throw new TestFailure("Initial cell assignment has been changed");
    }

    int blockSize = (int)Math.sqrt(solved.size());
    for (int i = 0; i < solved.size(); i++) {
      assertUniqueSeq(solved.get(i));
      assertUniqueSeq(gatherColumn(solved, i));
      assertUniqueSeq(gatherSquareBlock(solved, blockSize, i));
    }
  }

  private static void assertUniqueSeq(List<Integer> seq) throws TestFailure {
    Set<Integer> seen = new HashSet<>();
    for (Integer x : seq) {
      if (x == 0) {
        throw new TestFailure("Cell left uninitialized");
      }
      if (x < 0 || x > seq.size()) {
        throw new TestFailure("Cell value out of range");
      }
      if (seen.contains(x)) {
        throw new TestFailure("Duplicate value in section");
      }
      seen.add(x);
    }
  }

  private static List<Integer> gatherColumn(List<List<Integer>> data, int i) {
    List<Integer> result = new ArrayList<>();
    for (List<Integer> row : data) {
      result.add(row.get(i));
    }
    return result;
  }

  private static List<Integer> gatherSquareBlock(List<List<Integer>> data,
                                                 int blockSize, int n) {
    List<Integer> result = new ArrayList<>();
    int blockX = n % blockSize;
    int blockY = n / blockSize;
    for (int i = blockX * blockSize; i < (blockX + 1) * blockSize; i++) {
      for (int j = blockY * blockSize; j < (blockY + 1) * blockSize; j++) {
        result.add(data.get(i).get(j));
      }
    }

    return result;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "SudokuSolve.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
