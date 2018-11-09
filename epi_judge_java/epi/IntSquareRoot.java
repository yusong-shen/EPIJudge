package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
public class IntSquareRoot {
  @EpiTest(testDataFile = "int_square_root.tsv")

  public static int squareRoot(int k) {
    long l = 1, r = k;
    while (l + 1 < r) {
      long m = l + (r - l) / 2;
      if (m * m <= k) {
        l = m;
      } else {
        r = m - 1;
      }
    }
    if (r * r <= k) return (int) r;
    if (l * l <= k) return (int) l;
    return 0;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "IntSquareRoot.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
