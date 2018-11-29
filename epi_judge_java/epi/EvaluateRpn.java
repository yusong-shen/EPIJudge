package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

public class EvaluateRpn {
  @EpiTest(testDataFile = "evaluate_rpn.tsv")

  public static int eval(String expression) {
    Deque<Integer> stack = new LinkedList<>();
    String[] elements = expression.split(",");
    for (String e : elements) {
      if (Arrays.asList("+", "-", "*", "/").contains(e)) {
        int right = stack.removeLast();
        int left = stack.removeLast();
        stack.addLast(expr(left, right, e));
      } else {
        stack.addLast(Integer.parseInt(e));
      }
    }
    return stack.getLast();
  }

  private static int expr(int left, int right, String operators) {
    int result = 0;
    switch (operators) {
      case "+":
        result = left + right;
        break;
      case "-":
        result = left - right;
        break;
      case "*":
        result = left * right;
        break;
      case "/":
        result = left / right;
        break;
    }
    return result;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "EvaluateRpn.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
