package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.ArrayList;
import java.util.List;
public class SpiralOrderingSegments {
  @EpiTest(testDataFile = "spiral_ordering_segments.tsv")

  /**
   * A 2D array can be written as a sequence in several orders—the most natural ones being row-by-row or
   * column-by-column. In this problem we explore the problem of writing the 2D array in spiral order. For example,
   * the spiral ordering for the 2D array in Figure 6.3(a) is (1,2,3, 6,9,8, 7,4, 5). For Figure 6.3(b), the spiral
   * ordering is <1,2,3,4,8,12,16,15,14,13,9,5,6,7,11,10)
   */
  public static List<Integer>
  matrixInSpiralOrder(List<List<Integer>> squareMatrix) {
    List<Integer> result = new ArrayList<>();
    int n = squareMatrix.size(), level = n / 2;
    for (int k = 0; k < level; k++) {
      // top : right to left
      for (int j =  k; j < n - k - 1; j++) {
        result.add(squareMatrix.get(k).get(j));
      }
      // right : top to bottom
      for (int i = k; i < n - k - 1; i++) {
        result.add(squareMatrix.get(i).get(n - k - 1));
      }
      // bottom : left to right
      for (int j = n - k - 1; j > k; j--) {
        result.add(squareMatrix.get(n - k - 1).get(j));
      }
      // left : bottom to top
      for (int i = n - k - 1; i > k; i--) {
        result.add(squareMatrix.get(i).get(k));
      }
    }
    if (n % 2 == 1) {
      result.add(squareMatrix.get(level).get(level));
    }

    return result;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "SpiralOrderingSegments.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
