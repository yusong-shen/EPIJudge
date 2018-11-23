package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
public class IsTreeABst {
  @EpiTest(testDataFile = "is_tree_a_bst.tsv")

  public static boolean isBinaryTreeBST(BinaryTreeNode<Integer> tree) {
    return isBSTWithRange(tree, Integer.MIN_VALUE, Integer.MAX_VALUE);
  }

  private static boolean isBSTWithRange(BinaryTreeNode<Integer> tree, Integer minVal, Integer maxVal) {
    if (tree == null) return true;
    if (tree.data < minVal || tree.data > maxVal) return false;
    return isBSTWithRange(tree.left, minVal, tree.data) && isBSTWithRange(tree.right, tree.data, maxVal);
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "IsTreeABst.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
