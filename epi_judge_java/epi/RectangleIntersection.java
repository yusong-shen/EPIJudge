package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.EpiUserType;
import epi.test_framework.GenericTest;
public class RectangleIntersection {
  @EpiUserType(ctorParams = {int.class, int.class, int.class, int.class})
  public static class Rectangle {
    int x, y, width, height;

    public Rectangle(int x, int y, int width, int height) {
      this.x = x;
      this.y = y;
      this.width = width;
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

      if (x != rectangle.x) {
        return false;
      }
      if (y != rectangle.y) {
        return false;
      }
      if (width != rectangle.width) {
        return false;
      }
      return height == rectangle.height;
    }

    @Override
    public int hashCode() {
      int result = x;
      result = 31 * result + y;
      result = 31 * result + width;
      result = 31 * result + height;
      return result;
    }

    @Override
    public String toString() {
      return "[" + x + ", " + y + ", " + width + ", " + height + "]";
    }
  }
  @EpiTest(testDataFile = "rectangle_intersection.tsv")
  public static Rectangle intersectRectangle(Rectangle R1, Rectangle R2) {
    int x = 0, y = 0;
    int width = 0, height = 0;
    if (isIntervalIntersected(R1.x, R1.x + R1.width, R2.x, R2.x + R2.width)) {
      x = Math.max(R1.x, R2.x);
      width = Math.min(R1.x + R1.width, R2.x + R2.width) - x;
    } else {
      return new Rectangle(0, 0, -1, -1);
    }
    if (isIntervalIntersected(R1.y, R1.y + R1.height, R2.y, R2.y + R2.height)) {
      y = Math.max(R1.y, R2.y);
      height = Math.min(R1.y + R1.height, R2.y + R2.height) - y;
    } else {
      return new Rectangle(0, 0, -1, -1);
    }
    return new Rectangle(x, y, width, height);
  }

  private static boolean isIntervalIntersected(int x0, int x1, int x2, int x3) {
    if (x0 > x2) {
      return isIntervalIntersected(x2, x3, x0, x1);
    }
    return x1 >= x2;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "RectangleIntersection.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
