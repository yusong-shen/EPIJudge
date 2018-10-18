package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.Arrays;
import java.util.List;
public class BuyAndSellStockKTimes {
  @EpiTest(testDataFile = "buy_and_sell_stock_k_times.tsv")

  public static double buyAndSellStockKTimes(List<Double> prices, int k) {
    if (k > prices.size() / 2) return buyAndSellStockUnlimited(prices);
    double[] buy = new double[k];
    double[] sell = new double[k + 1];
    Arrays.fill(buy, Integer.MIN_VALUE);
    Arrays.fill(sell, Integer.MIN_VALUE);
    sell[0] = 0.0;
    sell[k] = 0.0;
    for (double p : prices) {
      for (int i = 0; i < k; i++) {
        buy[i] = Math.max(buy[i], sell[i] - p);
        sell[i + 1] = Math.max(sell[i + 1], buy[i] + p);
      }
    }
    return sell[k];
  }

  public static double buyAndSellStockUnlimited(List<Double> prices) {
    double maxDiff = 0.0;
    for (int i = 1; i < prices.size(); i++) {
      maxDiff += Math.max(0, prices.get(i) - prices.get(i - 1));
    }

    return maxDiff;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "BuyAndSellStockKTimes.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
