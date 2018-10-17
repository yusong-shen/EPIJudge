package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import epi.test_framework.TestFailure;
import epi.test_framework.TimedExecutor;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.swap;

public class DutchNationalFlag {
  public enum Color { RED, WHITE, BLUE }

    /**
     * Write a function that takes an array A and an index i into A, and rearranges the elements such that all
     * elements less than A[i] appear first, followed by elements equal to A[i], followed by elements greater
     * than A[i]. Your algorithm should have 0(1) space complexity and O(|A|)time complexity.
     * @param pivotIndex
     * @param A
     */
  public static void dutchFlagPartition(int pivotIndex, List<Color> A) {
      swap(A, 0, pivotIndex);
      Color pivot = A.get(0);
      // 3-way partition, such that
      // A[0, ..., lt-1] < pivot
      // A[gt+1, ..., A.size()-1] > pivot
      // i is the scanning pointer, A[lt, ... i-1] == pivot
      // A[i, ..,gt] are unknown and remain to be partitioned
      // loop until i cross the gt
      int lt = 0, i = 0;
      int gt = A.size() - 1;
      while (i <= gt) {
          if (A.get(i).ordinal() < pivot.ordinal()) {
              swap(A, i, lt);
              i++; lt++;
          } else if (A.get(i).ordinal() > pivot.ordinal()) {
              swap(A, i, gt);
              gt--;
          } else {
              i++;
          }
      }
      return;
  }


  @EpiTest(testDataFile = "dutch_national_flag.tsv")
  public static void dutchFlagPartitionWrapper(TimedExecutor executor,
                                               List<Integer> A, int pivotIdx)
      throws Exception {
    List<Color> colors = new ArrayList<>();
    int[] count = new int[3];

    Color[] C = Color.values();
    for (int i = 0; i < A.size(); i++) {
      count[A.get(i)]++;
      colors.add(C[A.get(i)]);
    }

    Color pivot = colors.get(pivotIdx);
    executor.run(() -> dutchFlagPartition(pivotIdx, colors));

    int i = 0;
    while (i < colors.size() && colors.get(i).ordinal() < pivot.ordinal()) {
      count[colors.get(i).ordinal()]--;
      ++i;
    }

    while (i < colors.size() && colors.get(i).ordinal() == pivot.ordinal()) {
      count[colors.get(i).ordinal()]--;
      ++i;
    }

    while (i < colors.size() && colors.get(i).ordinal() > pivot.ordinal()) {
      count[colors.get(i).ordinal()]--;
      ++i;
    }

    if (i != colors.size()) {
      throw new TestFailure("Not partitioned after " + Integer.toString(i) +
                            "th element");
    } else if (count[0] != 0 || count[1] != 0 || count[2] != 0) {
      throw new TestFailure("Some elements are missing from original array");
    }
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "DutchNationalFlag.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
