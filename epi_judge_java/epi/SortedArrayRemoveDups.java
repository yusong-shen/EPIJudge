package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import epi.test_framework.TimedExecutor;

import java.util.Collections;
import java.util.List;
public class SortedArrayRemoveDups {

  // Returns the number of valid entries after deletion.
  /**
   * This problem is concerned with deleting repeated elements from a sorted array. For example, for the array
   * (2,3,5,5,7,11,11,11,13), then after deletion, the array is (2,3,5,7,11,13,0,0,0). After deleting repeated
   * elements, there are 6 valid entries. There are no requirements as to the values stored beyond the last valid
   * element.
   * Write a program which takes as input a sorted array and updates it so that all duplicates have been removed and
   * the remaining elements have been shifted left to fill the emptied indices. Return the number of valid elements.
   * Many languages have library functions for performing this operation—you cannot use these functions.
   */
  public static int deleteDuplicates(List<Integer> A) {
      if (A.isEmpty()) return 0;
      // lastDistinct : A.subList(0, lastDistinct + 1) contain all distinct values
      int lastDistinct = 0;
      // cur is the scanning pointer, it always advance by 1 in each iteration
      for (int cur = 0; cur < A.size(); cur++) {
          if (!A.get(cur).equals(A.get(lastDistinct))) {
              // we find another distinct value, so advance lastDistinct pointer
              lastDistinct++;
              // so now the lastDistinct position is vacant,
              Collections.swap(A, lastDistinct, cur);
              // swap will make all the item remain order
              // or we can do following, which won't keep the order
//               A.set(lastDistinct, A.get(cur));
          }
      }
      return lastDistinct + 1;
  }
  @EpiTest(testDataFile = "sorted_array_remove_dups.tsv")
  public static List<Integer> deleteDuplicatesWrapper(TimedExecutor executor,
                                                      List<Integer> A)
      throws Exception {
    int end = executor.run(() -> deleteDuplicates(A));
    return A.subList(0, end);
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "SortedArrayRemoveDups.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
