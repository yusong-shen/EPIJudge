package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.HashMap;
import java.util.Map;

public class IsStringPermutableToPalindrome {
  @EpiTest(testDataFile = "is_string_permutable_to_palindrome.tsv")

  public static boolean canFormPalindrome(String s) {
    Map<Character, Integer> charFreq = new HashMap<>();
    for (int i = 0; i < s.length(); i++) {
      charFreq.put(s.charAt(i), charFreq.getOrDefault(s.charAt(i), 0) + 1);
    }
    int oddFreqCount = 0;
    for (char key : charFreq.keySet()) {
      oddFreqCount += (charFreq.get(key) % 2 == 1) ? 1 : 0;
    }
    return oddFreqCount <= 1;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "IsStringPermutableToPalindrome.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
