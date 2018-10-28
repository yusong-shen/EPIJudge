package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.EpiTestComparator;
import epi.test_framework.LexicographicalListComparator;
import epi.test_framework.GenericTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiPredicate;
public class Permutations {
  @EpiTest(testDataFile = "permutations.tsv")

  public static List<List<Integer>> permutations(List<Integer> A) {
//    return permutations(A, A.size());
      return permuteUnique(A);
  }

  private static List<List<Integer>> permutations(List<Integer> A, int len) {
    List<List<Integer>> result = new ArrayList<>();
    if (len == 0) {
      result.add(new ArrayList<>());
      return result;
    }
    if (len == 1) {
      result.add(new ArrayList<>(A.subList(0, 1)));
      return result;
    }
    List<List<Integer>> prevRet = permutations(A, len - 1);
    int v = A.get(len - 1);
    for (List<Integer> p : prevRet) {
      for (int i = 0; i <= p.size(); i++) {
        List<Integer> newP = new ArrayList<>(p);
        newP.add(i, v);
        result.add(newP);
      }
    }

    return result;
  }

    public static List<List<Integer>> permuteUnique(List<Integer> nums) {
        List<List<Integer>> ret = new ArrayList<>();
        List<Integer> permutation = new ArrayList<>();
        boolean[] used = new boolean[nums.size()];
        Collections.sort(nums);
        backtrack(nums, permutation, ret, used);
        return ret;
    }

    private static void backtrack(List<Integer> nums, List<Integer> permutation, List<List<Integer>> ret, boolean[] used) {
        if (permutation.size() == nums.size()) {
            ret.add(new ArrayList<>(permutation));
            return;
        }
        for (int i = 0; i < nums.size(); i++) {
            // [1, 2_1, 2_2]
            // if current element has been used, continue
            // or haven't been used, but we had (1, 2_1, 2_2) already
            // when we backtrack to (1), should we explore (1, 2_2 ...) path?
            // the answer is not, otherwise we will get duplicated permutation,
            // and 2_1 will be unused in this situation, that's why we use !used[i - 1] here.
            // If we haven't had (1, 2_1, 2_2), for example, we are in (1, 2_1),
            // 2_1 will be in used, 2_2 should not be skipped
            if (used[i] || (i > 0 && nums.get(i) == nums.get(i - 1) && !used[i - 1])) continue;
            used[i] = true;
            permutation.add(nums.get(i));
            backtrack(nums, permutation, ret, used);
            used[i] = false;
            permutation.remove(permutation.size() - 1);
        }


    }

  @EpiTestComparator
  public static BiPredicate<List<List<Integer>>, List<List<Integer>>> comp =
      (expected, result) -> {
    if (result == null) {
      return false;
    }
    for (List<Integer> l : expected) {
      Collections.sort(l);
    }
    expected.sort(new LexicographicalListComparator<>());
    for (List<Integer> l : result) {
      Collections.sort(l);
    }
    result.sort(new LexicographicalListComparator<>());
    return expected.equals(result);
  };

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "Permutations.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
