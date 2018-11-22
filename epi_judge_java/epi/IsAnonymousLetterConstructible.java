package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.HashMap;
import java.util.Map;

public class IsAnonymousLetterConstructible {
  @EpiTest(testDataFile = "is_anonymous_letter_constructible.tsv")

  public static boolean isLetterConstructibleFromMagazine(String letterText,
                                                          String magazineText) {
    Map<Character, Integer> count = new HashMap<>();
    for (int i = 0; i < letterText.length(); i++) {
      char c = letterText.charAt(i);
      count.put(c, count.getOrDefault(c, 0) + 1);
    }
    for (int i = 0; i < magazineText.length(); i++) {
      char c = magazineText.charAt(i);
      if (count.containsKey(c)) {
        count.put(c, count.get(c) - 1);
      }
    }
    for (char key : count.keySet()) {
      if (count.get(key) > 0) return false;
    }
    return true;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "IsAnonymousLetterConstructible.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
