package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class PrimeSieve {
  @EpiTest(testDataFile = "prime_sieve.tsv")
  // Given n, return all primes up to and including n.
  public static List<Integer> generatePrimes(int n) {
    boolean[] isPrime = new boolean[n + 1];
    Arrays.fill(isPrime, true);
    List<Integer> results = new ArrayList<>();
    isPrime[1] = false;
    for (int i = 2; i <= n; i++) {
      if (isPrime[i]) {
        // exclude all its multiples
        int k = i * 2;
        while (k <= n) {
          isPrime[k] = false;
          k += i;
        }
        results.add(i);
      }
    }

    return results;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "PrimeSieve.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
