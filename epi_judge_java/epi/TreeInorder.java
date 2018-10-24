package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class TreeInorder {
  @EpiTest(testDataFile = "tree_inorder.tsv")

  public static List<Integer> inorderTraversal(BinaryTreeNode<Integer> tree) {
      return inorderTraversalOHSpace(tree);
  }

  public static List<Integer> inorderTraversalOHSpace(BinaryTreeNode<Integer> tree) {
    Stack<BinaryTreeNode<Integer>> stack = new Stack<>();
    List<Integer> result = new ArrayList<>();
    BinaryTreeNode<Integer> cur = tree;

    while (cur != null || !stack.isEmpty()) {
        while (cur != null) {
            stack.push(cur);
            cur = cur.left;
        }
      cur = stack.pop();
      result.add(cur.data);
      cur = cur.right;
    }

    return result;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "TreeInorder.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
