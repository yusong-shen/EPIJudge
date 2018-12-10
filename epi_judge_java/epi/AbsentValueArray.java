package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.HashSet;
import java.util.Set;

public class AbsentValueArray {

  @EpiTest(testDataFile = "absent_value_array.tsv")
  public static int findMissingElement(Iterable<Integer> stream) {
    int min = 0, max = Integer.MIN_VALUE;
    // the space can be reduced by counting, for example for the leading 16 bit
      // and then focus on the single bucket among those 2 ^ 16 bucket
    Set<Integer> set = new HashSet<>();
    for (int num : stream) {
      max = Math.max(num, max);
      set.add(num);
    }
    int missing = 0;
    for (int i = min; i <= max; i++) {
      if (!set.contains(i)) {
        missing = i;
        break;
      }
    }
    return missing;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "AbsentValueArray.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
