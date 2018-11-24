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
    if (individualPlayScores.isEmpty()) return 0;
    // dp[i][j] : # of ways to get score j, by using individualPlayScores.subList(0, i+1)
    int[][] dp = new int[individualPlayScores.size()][finalScore + 1];
    // initialization
    for (int k = 0; k * individualPlayScores.get(0) <= finalScore; k++) {
        dp[0][k * individualPlayScores.get(0)] = 1;
    }
    for (int i = 1; i < individualPlayScores.size(); i++) {
        for (int j = 0; j < finalScore + 1; j++) {
            int withoutCurrentPlay = dp[i - 1][j];
            int withCurrentPlay = j - individualPlayScores.get(i) >= 0 ?
                    dp[i][j - individualPlayScores.get(i)] : 0;
            dp[i][j] = withoutCurrentPlay + withCurrentPlay;
        }
    }

    return dp[individualPlayScores.size() - 1][finalScore];
  }


  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "NumberOfScoreCombinations.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
