package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.EpiTestComparator;
import epi.test_framework.GenericTest;

import java.util.*;
import java.util.function.BiPredicate;
public class SearchFrequentItems {

  public static List<String> searchFrequentItems(int k,
                                                 Iterable<String> stream) {
    List<String> result = new ArrayList<>();
    Map<String, Integer> candidates = new HashMap<>();
    int n = 0;
    for (String s : stream) {
      n++;
      candidates.put(s, candidates.getOrDefault(s, 0) + 1);
      if (candidates.size() == k) {
        // avoid concurrent modification exception
        Set<String> delKeys = new HashSet<>();
        for (String key : candidates.keySet()) {
          candidates.put(key, candidates.get(key) - 1);
          if (candidates.get(key) == 0) {
            delKeys.add(key);
          }
        }
        for (String delKey : delKeys) {
          candidates.remove(delKey);
        }

      }
    }
    Map<String, Integer> candidatefreqCount = new HashMap<>();
    for (String s : stream) {
      if (candidates.containsKey(s)) {
        candidatefreqCount.put(s, candidatefreqCount.getOrDefault(s, 0) + 1);
      }
    }
    for (String key : candidatefreqCount.keySet()) {
      if ((double) candidatefreqCount.get(key) > 1.0 * n / k) {
        result.add((key));
      }
    }

    return result;
  }

  // 18.5 : k = 2 special case
  public static String majoritySearch(Iterator<String> stream) {
    String candidate = "";
    int count = 0;
    while (stream.hasNext()) {
      String s = stream.next();
      if (count == 0) {
        candidate = s;
        count++;
      } else if (s.equals(candidate)) {
        count++;
      } else {
        count--;
      }

    }

    return candidate;
  }

  @EpiTest(testDataFile = "search_frequent_items.tsv")
  public static List<String> searchFrequentItemsWrapper(int k,
                                                        List<String> stream) {
    return searchFrequentItems(k, stream);
  }


  @EpiTestComparator
  public static BiPredicate<List<String>, List<String>> comp =
      (expected, result) -> {
    if (result == null) {
      return false;
    }
    Collections.sort(expected);
    Collections.sort(result);
    return expected.equals(result);
  };

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "SearchFrequentItems.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
