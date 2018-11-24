package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
public class LevenshteinDistance {
  @EpiTest(testDataFile = "levenshtein_distance.tsv")

  public static int levenshteinDistance2(String A, String B) {
    int len1= A.length(), len2 = B.length();
    int[][] dp = new int[len1 + 1][len2 + 1];
    // init
    for (int i = 0; i <= len1; i++) {
      dp[i][0] = i;
    }
    for (int j = 0; j <= len2; j++) {
      dp[0][j] = j;
    }
    for (int i = 1; i <= len1; i++) {
      for (int j = 1; j <= len2; j++) {
        int substitute = (A.charAt(i - 1) == B.charAt(j - 1)) ?
                dp[i - 1][j - 1] : dp[i - 1][j - 1] + 1;
        int insert = dp[i][j - 1] + 1;
        int delete = dp[i - 1][j] + 1;
        dp[i][j] = Math.min(substitute, Math.min(insert, delete));
      }
    }


    return dp[len1][len2];
  }

    public static int levenshteinDistance(String A, String B) {
        int len1= A.length(), len2 = B.length();
        // make sure len2 >= len1
        if (len1 < len2) return levenshteinDistance(B, A);
        int[][] dp = new int[2][len2 + 1];
        // init
        dp[1][0] = 1;
        for (int j = 0; j <= len2; j++) {
            dp[0][j] = j;
        }
        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                int substitute = (A.charAt(i - 1) == B.charAt(j - 1)) ?
                        dp[(i - 1) % 2][j - 1] : dp[(i - 1) % 2][j - 1] + 1;
                int insert = dp[i % 2][j - 1] + 1;
                int delete = dp[(i - 1) % 2][j] + 1;
                dp[i % 2][j] = Math.min(substitute, Math.min(insert, delete));
            }
        }


        return dp[len1 % 2][len2];
    }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "LevenshteinDistance.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
