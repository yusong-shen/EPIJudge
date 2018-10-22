package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
public class IsTreeSymmetric {
  @EpiTest(testDataFile = "is_tree_symmetric.tsv")

  public static boolean isSymmetric(BinaryTreeNode<Integer> tree) {
    if (tree == null) return true;
    return isSymmetric(tree.left, tree.right);
  }

  private static boolean isSymmetric(BinaryTreeNode<Integer> node1, BinaryTreeNode<Integer> node2) {
    if (node1 == null && node2 == null) return true;
    if (node1 == null || node2 == null) return false;
    if (!node1.equals(node2)) return false;
    return isSymmetric(node1.left, node2.right) && isSymmetric(node1.right, node2.left);
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "IsTreeSymmetric.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
