package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
public class PowerXY {
  @EpiTest(testDataFile = "power_x_y.tsv")
  public static double power(double x, int y) {
    double result = 1.0;
    if (y < 0) {
      y = -y;
      x = 1.0 / x;
    }
    // y is even number
    // x^y = (x^(y/2))^2
    // y is odd number
    // x^y = x * (x^(y/2))^2
    while (y != 0) {
      // y is odd
      if ((y & 1) != 0) {
        result *= x;
      }
      // x = x ^ 2
      x *= x;
      // y = y / 2
      y = y >>> 1;
    }

    return result;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "PowerXY.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
