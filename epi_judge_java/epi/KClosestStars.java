package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.EpiTestComparator;
import epi.test_framework.EpiTestExpectedType;
import epi.test_framework.EpiUserType;
import epi.test_framework.GenericTest;

import java.util.*;
import java.util.function.BiPredicate;
public class KClosestStars {
  @EpiUserType(ctorParams = {double.class, double.class, double.class})

  public static class Star implements Comparable<Star> {
    private double x, y, z;

    public Star(double x, double y, double z) {
      this.x = x;
      this.y = y;
      this.z = z;
    }

    public double distance() { return Math.sqrt(x * x + y * y + z * z); }

    @Override
    public int compareTo(Star that) {
      return Double.compare(this.distance(), that.distance());
    }

    @Override
    public String toString() {
      return String.valueOf(distance());
    }
  }

  public static List<Star> findClosestKStars(Iterator<Star> stars, int k) {
    PriorityQueue<Star> maxHeap = new PriorityQueue<>(k + 1, (a, b) -> Double.compare(b.distance(), a.distance()));
    while (stars.hasNext()) {
      maxHeap.add(stars.next());
      if (maxHeap.size() > k ) {
        maxHeap.poll();
      }
    }
    List<Star> results = new ArrayList<>(maxHeap);
    Collections.reverse(results);
    return results;
  }

  @EpiTest(testDataFile = "k_closest_stars.tsv")
  public static List<Star> findClosestKStarsWrapper(List<Star> stars, int k) {
    return findClosestKStars(stars.iterator(), k);
  }

  @EpiTestExpectedType public static List<Double> expectedType;

  @EpiTestComparator
  public static BiPredicate<List<Double>, List<Star>> comp =
      (expected, result) -> {
    if (expected.size() != result.size()) {
      return false;
    }
    Collections.sort(result);
    for (int i = 0; i < result.size(); i++) {
      if (result.get(i).distance() != expected.get(i)) {
        return false;
      }
    }
    return true;
  };

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "KClosestStars.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
