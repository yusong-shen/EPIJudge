package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
public class IsStringPalindromicPunctuation {
  @EpiTest(testDataFile = "is_string_palindromic_punctuation.tsv")

  public static boolean isPalindrome(String s) {
    s = s.toLowerCase();
    int l = 0, r = s.length() - 1;
    while (l < r) {
      while (l < s.length() && !Character.isLetterOrDigit(s.charAt(l))) {
        l++;
      }
      while (r >= 0 && !Character.isLetterOrDigit(s.charAt(r))) {
        r--;
      }
      if (s.charAt(l) != s.charAt(r)) return false;
      l++;
      r--;
    }

    return true;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "IsStringPalindromicPunctuation.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
