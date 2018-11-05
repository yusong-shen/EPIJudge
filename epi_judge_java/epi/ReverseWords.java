package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import epi.test_framework.TimedExecutor;
public class ReverseWords {

  public static void reverseWords(char[] input) {
    reverse(input, 0, input.length - 1);
    int s = 0, e = e = find(input, s, ' ');
    for (; e != -1; e = find(input, s, ' ')) {
        reverse(input, s, e - 1);
        s = e + 1;
    }
    reverse(input, s, input.length - 1);
    return;
  }

    private static int find(char[] input, int s, char c) {
      int i = s;
      for (; i < input.length; i++) {
          if (input[i] == c) {
              return i;
          }
      }

      return -1;
    }

    private static void reverse(char[] words, int l, int r) {
    while (l < r) {
      char temp = words[l];
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
