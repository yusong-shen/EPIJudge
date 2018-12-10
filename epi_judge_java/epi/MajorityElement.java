package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import java.util.Iterator;
import java.util.List;
public class MajorityElement {

  public static String majoritySearch(Iterator<String> stream) {
    String candidate = "";
    int count = 0;
    while (stream.hasNext()) {
     String s = stream.next();
     if (count == 0) {
       candidate = s;
       count++;
     } else if (s.equals(candidate)) {
       count++;
     } else {
       count--;
     }

    }

    return candidate;
  }
  @EpiTest(testDataFile = "majority_element.tsv")
  public static String majoritySearchWrapper(List<String> stream) {
    return majoritySearch(stream.iterator());
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "MajorityElement.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
