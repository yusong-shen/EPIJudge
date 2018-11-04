package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import epi.test_framework.TestFailure;
public class StringIntegerInterconversion {

  public static String intToString(int x) {
    if (x == 0) return "0";
    boolean isNegative = x < 0;
    StringBuilder sb = new StringBuilder();
    while (x != 0) {
      sb.append(Math.abs(x % 10));
      x = x / 10;
    }
    if (isNegative) sb.append("-");
    return sb.reverse().toString();
//    return "" + x;
  }
  public static int stringToInt(String s) {
//    return Integer.parseInt(s);
    int value = 0;
    int sign = 1;
    int i = 0;
    if (s.charAt(i) == '+') {
      i++;
    } else if (s.charAt(i) == '-'){
      sign = -1;
      i++;
    }
    for (; i < s.length(); i++) {
      value = value * 10 + (s.charAt(i) - '0');
    }
    return sign * value;
  }
  @EpiTest(testDataFile = "string_integer_interconversion.tsv")
  public static void wrapper(int x, String s) throws TestFailure {
    if (!intToString(x).equals(s)) {
      throw new TestFailure("Int to string conversion failed");
    }
    if (stringToInt(s) != x) {
      throw new TestFailure("String to int conversion failed");
    }
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "StringIntegerInterconversion.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
