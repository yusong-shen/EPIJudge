package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
public class ReverseBits {

  static long[] precompute = new long[1<<16];

  static {
    for (long x = 0; x < precompute.length; x++) {
      precompute[(int) x] = reverseBits(x, 15);
    }
  }

  @EpiTest(testDataFile = "reverse_bits.tsv")
  public static long reverseBits(long x) {
    int WORD_SIZE = 16;
    int BIT_MASK = 0xFFFF;
    return precompute[(int) (x >>> (WORD_SIZE * 3) & BIT_MASK)]
            | precompute[(int) (x >>> (WORD_SIZE * 2) & BIT_MASK)] << WORD_SIZE
            | precompute[(int) (x >>> WORD_SIZE & BIT_MASK)] << (WORD_SIZE * 2)
            | precompute[(int) (x & BIT_MASK)] << (WORD_SIZE * 3);
  }

  public static long reverseBits(long x, int n) {
    for (int i = 0, j = n; i < j; i++, j--) {
      x = swapBits(x, i, j);
    }
    return x;
  }

  private static long swapBits(long x, int i, int j) {
    if (((x >>> i) & 1) != ((x >>> j) & 1)) {
      long mask = (1l << i) | (1l << j);
      x ^= mask;
    }
    return x;
  }

  public static void main(String[] args) {


    System.exit(
        GenericTest
            .runFromAnnotations(args, "ReverseBits.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
