package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.lang.reflect.Array;
import java.util.*;

public class SortedArraysMerge {
  @EpiTest(testDataFile = "sorted_arrays_merge.tsv")


  public static List<Integer>
  mergeSortedArrays(List<List<Integer>> sortedArrays) {
    int n = sortedArrays.size();
    List<Integer> result = new ArrayList<>();
    // (arrayId, headValue)
    PriorityQueue<List<Integer>> minHeap = new PriorityQueue<>(n, Comparator.comparingInt(a -> a.get(1)));
    List<Iterator<Integer>> iters = new ArrayList<>();
    for (List<Integer> arr : sortedArrays) {
      iters.add(arr.iterator());
    }
    // initialize
    for (int i = 0; i < n; i++) {
      if (iters.get(i).hasNext()) {
        minHeap.add(Arrays.asList(i, iters.get(i).next()));
      }
    }
    while (!minHeap.isEmpty()) {
      List<Integer> top = minHeap.poll();
      int iterId = top.get(0), headValue = top.get(1);
      result.add(headValue);
      if (iters.get(iterId).hasNext()) {
        minHeap.add(Arrays.asList(iterId, iters.get(iterId).next()));
      }
    }
    return result;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "SortedArraysMerge.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
