package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
public class LargestRectangleUnderSkyline {
  @EpiTest(testDataFile = "largest_rectangle_under_skyline.tsv")

  public static int calculateLargestRectangle(List<Integer> heights) {
    // monotone stack
    Deque<Integer> stack = new LinkedList<>();
    // add a 0 height at the end to handle last rectangle
    heights.add(0);
    int maxArea = 0;
    for (int i = 0; i < heights.size(); i++) {
      int h = heights.get(i);
      while (!stack.isEmpty() && h < heights.get(stack.peekFirst())) {
        int rightIndex = stack.removeFirst();
        // width should be i instead of rightIndex
        int width = (stack.isEmpty() ? i: (i - stack.peekFirst() - 1));
        int curArea = heights.get(rightIndex) * width;
        maxArea = Math.max(maxArea, curArea);
      }
      stack.addFirst(i);
    }

    return maxArea;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "LargestRectangleUnderSkyline.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
