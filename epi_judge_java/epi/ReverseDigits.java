package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
public class ReverseDigits {
  @EpiTest(testDataFile = "reverse_digits.tsv")
  public static long reverse(int x) {
    long result = 0l;
    boolean isPositive = x > 0;
    x = Math.abs(x);
    while (x != 0) {
      result = 10 * result + x % 10;
      x = x / 10;
    }
    return isPositive ? result : - result;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "ReverseDigits.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
