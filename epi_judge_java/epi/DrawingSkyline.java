package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.EpiUserType;
import epi.test_framework.GenericTest;

import java.util.*;

public class DrawingSkyline {
  @EpiUserType(ctorParams = {int.class, int.class, int.class})

  public static class Rectangle {
    public int left, right, height;

    public Rectangle(int left, int right, int height) {
      this.left = left;
      this.right = right;
      this.height = height;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }

      Rectangle rectangle = (Rectangle)o;

      if (left != rectangle.left) {
        return false;
      }
      if (right != rectangle.right) {
        return false;
      }
      return height == rectangle.height;
    }

    @Override
    public String toString() {
      return "[" + left + ", " + right + ", " + height + ']';
    }
  }

  @EpiTest(testDataFile = "drawing_skyline.tsv")

  public static List<Rectangle> drawingSkylines(List<Rectangle> buildings) {
    // TODO - you fill in here.
    List<Rectangle> result = new ArrayList<>();
    return result;
  }

    /**
     * @param buildings: A list of lists of integers
     * @return: Find the outline of those buildings
     */
    public List<List<Integer>> buildingOutline(int[][] buildings) {
        // sort each critical points with x axis
        // if they are in the same positions, put start point first
        // to deal with [1, 2, 5] [2, 4, 5] situation
        // entry point : {x, -height}
        // exit point : {y, height}
        List<int[]> points = new ArrayList<>();
        for (int[] b : buildings) {
            points.add(new int[] {b[0], -b[2]});
            points.add(new int[] {b[1], b[2]});
        }
        Collections.sort(points, (a, b) -> {
            if (a[0] == b[0]) return a[1] - b[1];
            return a[0] - b[0];
        });

        // use TreeMap to keep track max height in current point
        // height -> count
        TreeMap<Integer, Integer> bst = new TreeMap<>(Collections.reverseOrder());
        // ground, deal with empty tree case
        bst.put(0, 1);

        // for each critical point
        int prevHeight = 0;
        int prevIndex = 0;
        List<List<Integer>> intervals = new ArrayList<>();
        for (int[] p : points) {
            // if entry point, increase that height count
            if (p[1] < 0) {
                bst.put(-p[1], bst.getOrDefault(-p[1], 0) + 1);
            }
            // if exit point, remove that height count
            else {
                bst.put(p[1], bst.getOrDefault(p[1], 0) - 1);
                if (bst.get(p[1]) == 0) bst.remove(p[1]);
            }
            // root of tree is the current max height
            int curHeight = bst.firstKey();
            // if current max height != previous height
            // merge the interval
            if (curHeight != prevHeight) {
                if ((prevIndex - p[0]) * prevHeight != 0) {
                    intervals.add(getInterval(prevIndex, p[0], prevHeight));
                }
                prevIndex = p[0];
                prevHeight = curHeight;

            }
        }
        return intervals;
    }

    private List<Integer> getInterval(int s, int e, int h) {
        return Arrays.asList(s, e, h);
    }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "DrawingSkyline.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
