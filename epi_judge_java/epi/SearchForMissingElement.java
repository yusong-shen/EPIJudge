package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.EpiUserType;
import epi.test_framework.GenericTest;
import java.util.List;
public class SearchForMissingElement {
  @EpiUserType(ctorParams = {Integer.class, Integer.class})

  public static class DuplicateAndMissing {
    public Integer duplicate;
    public Integer missing;

    public DuplicateAndMissing(Integer duplicate, Integer missing) {
      this.duplicate = duplicate;
      this.missing = missing;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }

      DuplicateAndMissing that = (DuplicateAndMissing)o;

      if (!duplicate.equals(that.duplicate)) {
        return false;
      }
      return missing.equals(that.missing);
    }

    @Override
    public int hashCode() {
      int result = duplicate.hashCode();
      result = 31 * result + missing.hashCode();
      return result;
    }

    @Override
    public String toString() {
      return "duplicate: " + duplicate + ", missing: " + missing;
    }
  }

  @EpiTest(testDataFile = "find_missing_and_duplicate.tsv")

  public static DuplicateAndMissing findDuplicateMissing(List<Integer> A) {

    int xor = 0;
    int n = A.size();
    for (int i = 0; i < n; i++) {
      xor = xor ^ i ^ A.get(i);
    }
    // xor = missing ^ duplicate
    // get the least set bit
    int diffBit = xor & (~(xor - 1));
    int missOrDupl = 0;
    for (int i = 0; i < n; i++) {
      if ((i & diffBit) != 0) {
        missOrDupl ^= i;
      }
      if ((A.get(i) & diffBit) != 0) {
        missOrDupl ^= A.get(i);
      }
    }
    for (int num : A) {
      // missOrDupl is duplidate
      if (num == missOrDupl) {
        return new DuplicateAndMissing(missOrDupl, xor ^ missOrDupl);
      }
    }
    // missOrDupl is missing
    return new DuplicateAndMissing(xor ^ missOrDupl, missOrDupl);
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "SearchForMissingElement.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
