package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class IsValidParenthesization {
  @EpiTest(testDataFile = "is_valid_parenthesization.tsv")

  public static boolean isWellFormed(String s) {
    Map<Character, Character> parenthesisMap = new HashMap<>();
    parenthesisMap.put(')', '(');
    parenthesisMap.put(']', '[');
    parenthesisMap.put('}', '{');
    Deque<Character> stack = new LinkedList<>();
    for (int i = 0; i < s.length(); i++) {
      char ch = s.charAt(i);
      if (parenthesisMap.values().contains(ch)) {
        stack.addLast(ch);
      } else if (parenthesisMap.containsKey(ch)){
        if (stack.isEmpty()) return false;
        if (!parenthesisMap.get(ch).equals(stack.getLast())) return false;
        stack.removeLast();
      }
    }

    return stack.isEmpty();
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "IsValidParenthesization.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
