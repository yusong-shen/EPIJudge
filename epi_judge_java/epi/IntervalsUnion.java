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
    }
  }

  public static List<Interval> unionOfIntervals(List<Interval> intervals) {
    List<Interval> result = new ArrayList<>();
    Collections.sort(intervals, (a, b) -> {
      if (a.left.val == b.left.val) {
        if (a.left.isClosed == b.left.isClosed) {
          return 0;
        } else if (a.left.isClosed) {
          return 1;
        } else {
          return -1;
        }
      }
      return Integer.compare(a.left.val, b.left.val);
    });
    for (Interval interval : intervals) {
      if (result.isEmpty() || !isIntersected(interval, result.get(result.size() - 1))) {
        result.add(interval);
      } else {
        Interval top = result.remove(result.size() - 1);
        result.add(mergeTwoInterval(top, interval));
      }
    }

    return result;
  }

  private static boolean isIntersected(Interval i1, Interval i2) {
    if (i2.left.val > i1.right.val) {
      return false;
    }
    // i2.left.val == i1.right.val
    // only when both of them are open, they are separated
    return !(!i2.left.isClosed && !i1.left.isClosed);
  }

  private static Interval mergeTwoInterval(Interval top, Interval interval) {
    Interval.Endpoint left = top.left;
    Interval.Endpoint right = null;
    if (top.right.val > interval.right.val) {
      right = top.right;
    } else if (top.right.val < interval.right.val) {
      right = interval.right;
    } else {
      if (top.right.isClosed == interval.right.isClosed) {
        right = top.right;
      } else {
        right = (top.right.isClosed) ? top.right : interval.right;
      }
    }
    Interval merged = new Interval();
    merged.left = left;
    merged.right = right;
    return merged;
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
