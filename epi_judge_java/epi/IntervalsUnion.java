package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.EpiUserType;
import epi.test_framework.GenericTest;
import epi.test_framework.TimedExecutor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
public class IntervalsUnion {

  public static class Interval {
    public Endpoint left = new Endpoint();
    public Endpoint right = new Endpoint();

    private static class Endpoint {
      public boolean isClosed;
      public int val;

      @Override
      public String toString() {
        return "Endpoint{" +
                "isClosed=" + isClosed +
                ", val=" + val +
                '}';
      }
    }

    @Override
    public String toString() {
      return "Interval{" +
              "left=" + left +
              ", right=" + right +
              '}';
    }
  }

  public static List<Interval> unionOfIntervals(List<Interval> intervals) {
    List<Interval> result = new ArrayList<>();
    Collections.sort(intervals, (a, b) -> {
      if (a.left.val == b.left.val) {
        // closed point should appear first, i.e 0 or -1
        if (a.left.isClosed == b.left.isClosed) {
          return 0;
        } else if (a.left.isClosed) {
          return -1;
        } else {
          return 1;
        }
      }
      return Integer.compare(a.left.val, b.left.val);
    });
    for (Interval interval : intervals) {
      if (result.isEmpty() || !isIntersected(result.get(result.size() - 1), interval)) {
        result.add(interval);
      } else {
        // interval intersected
        Interval top = result.remove(result.size() - 1);
        result.add(mergeTwoInterval(top, interval));
      }
    }

    return result;
  }

  private static boolean isIntersected(Interval leftPoint, Interval rightPoint) {
    if (rightPoint.left.val > leftPoint.right.val) {
      return false;
    } else if (rightPoint.left.val == leftPoint.right.val) {
      // only when both of them are open, they are separated
      return (rightPoint.left.isClosed || leftPoint.right.isClosed);
    } else {
      // rightPoint.left.val < leftPoint.right.val
      return true;
    }
  }

  private static Interval mergeTwoInterval(Interval leftInterval, Interval rightInterval) {
    Interval.Endpoint right = null;
    if (leftInterval.right.val > rightInterval.right.val) {
      right = leftInterval.right;
    } else if (leftInterval.right.val < rightInterval.right.val) {
      right = rightInterval.right;
    } else {
      // leftInterval.right.val == rightInterval.right.val
      if (leftInterval.right.isClosed == rightInterval.right.isClosed) {
        right = leftInterval.right;
      } else {
        right = (leftInterval.right.isClosed) ? leftInterval.right : rightInterval.right;
      }
    }
    leftInterval.right = right;
    return leftInterval;
  }

  @EpiUserType(
      ctorParams = {int.class, boolean.class, int.class, boolean.class})
  public static class FlatInterval {
    int leftVal;
    boolean leftIsClosed;
    int rightVal;
    boolean rightIsClosed;

    public FlatInterval(int leftVal, boolean leftIsClosed, int rightVal,
                        boolean rightIsClosed) {
      this.leftVal = leftVal;
      this.leftIsClosed = leftIsClosed;
      this.rightVal = rightVal;
      this.rightIsClosed = rightIsClosed;
    }

    public FlatInterval(Interval i) {
      if (i != null) {
        leftVal = i.left.val;
        leftIsClosed = i.left.isClosed;
        rightVal = i.right.val;
        rightIsClosed = i.right.isClosed;
      }
    }

    public Interval toInterval() {
      Interval i = new Interval();
      i.left.val = leftVal;
      i.left.isClosed = leftIsClosed;
      i.right.val = rightVal;
      i.right.isClosed = rightIsClosed;
      return i;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }

      FlatInterval that = (FlatInterval)o;

      if (leftVal != that.leftVal) {
        return false;
      }
      if (leftIsClosed != that.leftIsClosed) {
        return false;
      }
      if (rightVal != that.rightVal) {
        return false;
      }
      return rightIsClosed == that.rightIsClosed;
    }

    @Override
    public int hashCode() {
      int result = leftVal;
      result = 31 * result + (leftIsClosed ? 1 : 0);
      result = 31 * result + rightVal;
      result = 31 * result + (rightIsClosed ? 1 : 0);
      return result;
    }

    @Override
    public String toString() {
      return "" + (leftIsClosed ? "<" : "(") + leftVal + ", " + rightVal +
          (rightIsClosed ? ">" : ")");
    }
  }

  @EpiTest(testDataFile = "intervals_union.tsv")
  public static List<FlatInterval>
  unionIntervalWrapper(TimedExecutor executor, List<FlatInterval> intervals)
      throws Exception {
    List<Interval> casted = new ArrayList<>(intervals.size());
    for (FlatInterval in : intervals) {
      casted.add(in.toInterval());
    }

    List<Interval> result = executor.run(() -> unionOfIntervals(casted));

    intervals = new ArrayList<>(result.size());
    for (Interval i : result) {
      intervals.add(new FlatInterval(i));
    }
    return intervals;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "IntervalsUnion.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
