package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import epi.test_framework.TimedExecutor;
import java.util.ArrayList;
import java.util.List;
public class ReplaceAndRemove {

  /**
   * • Replace each 'a' by two 'd's.
   * • Delete each entry containing a 'b'.
   * [a,c,d,b,b,c,a] -> [d,d,c,d,c,d,d]
   *
   */
  public static int replaceAndRemove(int size, char[] s) {
    int nextLen = 0;
    for (int i = 0; i < size; i++) {
      char c = s[i];
      if (c == 'a') nextLen += 2;
      else if (c != 'b') nextLen++;
    }
    char[] result = new char[nextLen];
    for (int i = 0, j = 0; i < size; i++) {
      char c = s[i];
      if (c == 'a') {
        result[j++] = 'd';
        result[j++] = 'd';
      } else if (c != 'b') {
        result[j++] = c;
      }
    }
    for (int j = 0; j < result.length; j++) {
      s[j] = result[j];
    }
    return nextLen;
  }

  @EpiTest(testDataFile = "replace_and_remove.tsv")
  public static List<String>
  replaceAndRemoveWrapper(TimedExecutor executor, Integer size, List<String> s)
      throws Exception {
    char[] sCopy = new char[s.size()];
    for (int i = 0; i < size; ++i) {
      if (!s.get(i).isEmpty()) {
        sCopy[i] = s.get(i).charAt(0);
      }
    }

    Integer resSize = executor.run(() -> replaceAndRemove(size, sCopy));

    List<String> result = new ArrayList<>();
    for (int i = 0; i < resSize; ++i) {
      result.add(Character.toString(sCopy[i]));
    }
    return result;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "ReplaceAndRemove.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
