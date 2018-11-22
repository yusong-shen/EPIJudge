package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NearestRepeatedEntries {
  @EpiTest(testDataFile = "nearest_repeated_entries.tsv")

  public static int findNearestRepetition(List<String> paragraph) {
    int closestDist = Integer.MAX_VALUE;
    Map<String, Integer> wordIndexMap = new HashMap<>();
    int ind = 0;
    for (String word : paragraph) {
      if (wordIndexMap.containsKey(word)) {
        int dist = ind - wordIndexMap.get(word);
        closestDist = Math.min(closestDist, dist);
      }
      wordIndexMap.put(word, ind);
      ind++;
    }
    return closestDist == Integer.MAX_VALUE ? -1 : closestDist;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "NearestRepeatedEntries.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
