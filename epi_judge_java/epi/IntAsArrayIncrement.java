package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class IntAsArrayIncrement {
  @EpiTest(testDataFile = "int_as_array_increment.tsv")

  /**
   * Write a program which takes as input an array of digits encoding a decimal number D and updates the array to
   * represent the number D + 1. For example, if the input is (1,2,9) then you should update the array to (1,3,0).
   * Your algorithm should work even if it is implemented in a language that has finite-precision arithmetic.
   */
  public static List<Integer> plusOne(List<Integer> A) {
    List<Integer> result = new ArrayList<>(A);
    Collections.reverse(result);
    int carry = 1;
    for (int i = 0; i < result.size(); i++) {
      int digit = result.get(i);
      int newDigit = (digit + carry) % 10;
      carry = (digit + carry) / 10;
      result.set(i, newDigit);
    }
    if (carry == 1) result.add(1);
    Collections.reverse(result);
    return result;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "IntAsArrayIncrement.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
