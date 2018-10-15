package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import epi.test_framework.TimedExecutor;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MatrixConnectedRegions {
  public static void flipColor(int x, int y, List<List<Boolean>> image) {
      // BFS
      if (!isLegal(x, y, image)) return;
      boolean pixel = image.get(x).get(y);
      int[][] dir = new int[][] {{0,1}, {0,-1}, {1,0}, {-1,0}};
      Queue<int[]> q = new LinkedList<>();
      q.offer(new int[] {x, y});
      while (!q.isEmpty()) {
          int[] cur = q.poll();
          image.get(cur[0]).set(cur[1], !pixel);
          for (int[] d : dir) {
              int xx = cur[0] + d[0];
              int yy = cur[1] + d[1];
              if (!isLegal(xx, yy, image) || image.get(xx).get(yy) != pixel) continue;
              q.offer(new int[] {xx, yy});
          }
      }

      return;
  }

  private static boolean isLegal(int x, int y, List<List<Boolean>> image) {
      return (0 <= x && x < image.size() && 0 <= y && y < image.get(0).size());
  }


  @EpiTest(testDataFile = "painting.tsv")
  public static List<List<Integer>> flipColorWrapper(TimedExecutor executor,
                                                     int x, int y,
                                                     List<List<Integer>> image)
      throws Exception {
    List<List<Boolean>> B = new ArrayList<>();
    for (int i = 0; i < image.size(); i++) {
      B.add(new ArrayList<>());
      for (int j = 0; j < image.get(i).size(); j++) {
        B.get(i).add(image.get(i).get(j) == 1);
      }
    }

    executor.run(() -> flipColor(x, y, B));

    image = new ArrayList<>();
    for (int i = 0; i < B.size(); i++) {
      image.add(new ArrayList<>());
      for (int j = 0; j < B.get(i).size(); j++) {
        image.get(i).add(B.get(i).get(j) ? 1 : 0);
      }
    }

    return image;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "MatrixConnectedRegions.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
