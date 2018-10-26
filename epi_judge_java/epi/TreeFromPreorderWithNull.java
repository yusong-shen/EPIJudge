package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import epi.test_framework.TimedExecutor;

import java.util.*;

public class TreeFromPreorderWithNull {
  public static BinaryTreeNode<Integer>
  reconstructPreorder(List<Integer> preorder) {
    Set<BinaryTreeNode<Integer>> leftVisited = new HashSet<>();
    Stack<BinaryTreeNode<Integer>> stack = new Stack<>();
    for (Integer v : preorder) {
      if (stack.empty()) {
        stack.push(new BinaryTreeNode<>(v));
      } else {
        BinaryTreeNode<Integer> prev = stack.peek();
        BinaryTreeNode<Integer> cur = (v == null) ? null : new BinaryTreeNode<>(v);
        if (!leftVisited.contains(prev) && prev.left == null) {
          prev.left = cur;
          leftVisited.add(prev);
        } else if (leftVisited.contains(prev) && prev.right == null){
          leftVisited.remove(prev);
          prev.right = cur;
          stack.pop();
        }
        if (cur != null) {
          stack.push(cur);
        }
      }
    }
    BinaryTreeNode<Integer> root = null;
    while (!stack.isEmpty()) {
      root = stack.pop();
    }
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
