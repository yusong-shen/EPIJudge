package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import epi.test_framework.TimedExecutor;

import java.util.*;

public class TreeFromPreorderWithNull {
    private static int i;
  public static BinaryTreeNode<Integer>
  reconstructPreorder(List<Integer> preorder) {
    i = 0;
    return reconstruct(preorder);
  }

  private static BinaryTreeNode<Integer> reconstruct(List<Integer> preorder) {
      if (i >= preorder.size()) return null;
      Integer val = preorder.get(i);
      i++;
      if (val == null) return null;
      BinaryTreeNode<Integer> root = new BinaryTreeNode<>(val);
      root.left = reconstruct(preorder);
      root.right = reconstruct(preorder);

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
