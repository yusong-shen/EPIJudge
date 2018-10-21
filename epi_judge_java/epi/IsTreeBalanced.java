package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
public class IsTreeBalanced {

  @EpiTest(testDataFile = "is_tree_balanced.tsv")

  /**
   * A binary tree is said to be height-balanced if for each node in the tree, the difference in the height of its left
   * and right subtrees is at most one.
   */
  public static boolean isBalanced(BinaryTreeNode<Integer> tree) {
    return getDepth(tree) >= 0;
  }

  private static int getDepth(BinaryTreeNode<Integer> node) {
    if (node == null) {
      return  0;
    }
    int ld = getDepth(node.left);
    int rd = getDepth(node.right);
    boolean isBalanced = (ld >= 0) && (rd >= 0) && (Math.abs(ld - rd) <= 1);
    int depth = Math.max(Math.abs(ld), Math.abs(rd)) + 1;
    return isBalanced ? depth : -depth;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "IsTreeBalanced.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
