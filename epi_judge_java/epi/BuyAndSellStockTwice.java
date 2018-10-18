package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import java.util.List;
public class BuyAndSellStockTwice {
  /**
   * 6.7 BUY AND SELL A STOCK TWICE
   * The max difference problem, introduced on Page 1, formalizes the maximum profit that can be made by buying and
   * then selling a single share over a given day range.
   * Write a program that computes the maximum profit that can be made by buying and selling a share at most twice.
   * The second buy must be made on another date after the first sale.
   * @param prices
   * @return
   */
  @EpiTest(testDataFile = "buy_and_sell_stock_twice.tsv")
  public static double buyAndSellStockTwice(List<Double> prices) {
      double buyOne = Integer.MIN_VALUE, sellOne = Integer.MIN_VALUE;
      double buyTwo = Integer.MIN_VALUE, sellTwo = 0.0;
      for (double p : prices) {
        buyOne = Math.max(buyOne, -p);
        sellOne = Math.max(sellOne, buyOne + p);
        buyTwo = Math.max(buyTwo, sellOne - p);
        sellTwo = Math.max(sellTwo, buyTwo + p);
      }
      return sellTwo;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "BuyAndSellStockTwice.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
