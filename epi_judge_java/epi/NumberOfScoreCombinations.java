package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NumberOfScoreCombinations {
  @EpiTest(testDataFile = "number_of_score_combinations.tsv")

  public static int
  numCombinationsForFinalScore(int finalScore,
                               List<Integer> individualPlayScores) {
    Map<Integer, Integer> cache = new HashMap<>();
    for (int score : individualPlayScores) {
      cache.put(score, 1);
    }
    return helper(finalScore, individualPlayScores, cache);
  }

  private static int helper(int finalScore, List<Integer> individualPlayScores, Map<Integer, Integer> cache) {
    if (finalScore < 0) return 0;
    if (cache.containsKey(finalScore)) {
      return cache.get(finalScore);
    }
    int result = 0;
    for (int score : individualPlayScores) {
      result += helper(finalScore - score, individualPlayScores, cache);
    }
    cache.put(finalScore, result);
    return result;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "NumberOfScoreCombinations.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
