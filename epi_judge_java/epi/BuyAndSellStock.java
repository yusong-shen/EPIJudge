package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import java.util.List;
public class BuyAndSellStock {
  @EpiTest(testDataFile = "buy_and_sell_stock.tsv")
  public static double computeMaxProfit(List<Double> prices) {
      double lowSoFar = Integer.MAX_VALUE;
      double maxDiff = 0.0;
      for (double p : prices) {
          lowSoFar = Math.min(lowSoFar, p);
          maxDiff = Math.max(maxDiff, p - lowSoFar);
      }

      return maxDiff;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "BuyAndSellStock.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
