package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import epi.test_framework.TimedExecutor;
public class ReverseWords {

  public static void reverseWords(char[] input) {
    String s = new String(input);
    String[] words = s.split(" ");
    reverse(words);
    int k = 0;
    for (int i = 0; i < words.length; i++) {
      String w = words[i];
      for (int j = 0; j < w.length(); j++) {
        input[k++] = w.charAt(j);
      }
      if (i != words.length - 1) {
        input[k++] = ' ';
      }
    }
    return;
  }

  private static void reverse(String[] words) {
    int l = 0, r = words.length - 1;
    while (l < r) {
      String temp = words[l];
      words[l] = words[r];
      words[r] = temp;
      l++;
      r--;
    }
  }

  @EpiTest(testDataFile = "reverse_words.tsv")
  public static String reverseWordsWrapper(TimedExecutor executor, String s)
      throws Exception {
    char[] sCopy = s.toCharArray();

    executor.run(() -> reverseWords(sCopy));

    return String.valueOf(sCopy);
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "ReverseWords.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
