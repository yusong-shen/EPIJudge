package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import epi.test_framework.TimedExecutor;

import java.util.*;

public class TreeFromPreorderWithNull {
  public static BinaryTreeNode<Integer>
  reconstructPreorder(List<Integer> preorder) {
    int[] i = new int[] {0};
    return reconstruct(preorder, i);
  }

  private static BinaryTreeNode<Integer> reconstruct(List<Integer> preorder, int[] i) {
      if (i[0] >= preorder.size()) return null;
      Integer val = preorder.get(i[0]);
      i[0]++;
      if (val == null) return null;
      BinaryTreeNode<Integer> root = new BinaryTreeNode<>(val);
      root.left = reconstruct(preorder, i);
      root.right = reconstruct(preorder, i);

      return root;
  }

  @EpiTest(testDataFile = "tree_from_preorder_with_null.tsv")
  public static BinaryTreeNode<Integer>
  reconstructPreorderWrapper(TimedExecutor executor, List<String> strings)
      throws Exception {
    List<Integer> ints = new ArrayList<>();
    for (String s : strings) {
      if (s.equals("null")) {
        ints.add(null);
      } else {
        ints.add(Integer.parseInt(s));
      }
    }

    return executor.run(() -> reconstructPreorder(ints));
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "TreeFromPreorderWithNull.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
