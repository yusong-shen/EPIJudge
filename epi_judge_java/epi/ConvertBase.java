package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
public class ConvertBase {
  @EpiTest(testDataFile = "convert_base.tsv")

  public static String convertBase(String numAsString, int b1, int b2) {
    if (numAsString.equals("-0")) return "-0";
    int base10Val = numToBase10(numAsString, b1);
    return base10ToBaseB(base10Val, b2);
  }

  private static int numToBase10(String numAsString, int b1) {
    int value = 0;
    int sign = 1;
    int i = 0;
    if (numAsString.charAt(i) == '+') {
      i++;
    } else if (numAsString.charAt(i) == '-'){
      sign = -1;
      i++;
    }
    for (; i < numAsString.length(); i++) {
      int ch = numAsString.charAt(i);
      int digit = ch >= 'A' ? ch - 'A' + 10 : ch - '0';
      value = value * b1 + digit;
    }
    return sign * value;
  }

  private static String base10ToBaseB(int x, int b) {
    boolean isNegative = x < 0;
    StringBuilder sb = new StringBuilder();
    do {
      int digit = Math.abs(x % b);
      char dightChar = digit >= 10 ? (char) ('A' + digit - 10) : (char) ('0' + digit);
      sb.append(dightChar);
      x = x / b;
    } while (x != 0);
    if (isNegative) sb.append("-");
    return sb.reverse().toString();
  }

  public static String convertBaseII(String numAsString, int b1, int b2) {
    if (numAsString.equals("-0")) return "-0";
    int a1 = Integer.parseInt(numAsString, b1);
    String a2 = Integer.toString(a1, b2);
    return a2.toUpperCase();
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "ConvertBase.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
