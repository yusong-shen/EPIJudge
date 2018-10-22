package epi;
import epi.test_framework.BinaryTreeUtils;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import epi.test_framework.TestFailure;
import epi.test_framework.TimedExecutor;
public class LowestCommonAncestorWithParent {

  public static BinaryTree<Integer> LCA(BinaryTree<Integer> node0,
                                        BinaryTree<Integer> node1) {
    int d0 = getDepth(node0);
    int d1 = getDepth(node1);
    // move node0 and node1 to the same level
    if (d0 > d1) {
      for (int i = 0; i < d0 - d1; i++) {
        node0 = node0.parent;
      }
    } else {
      for (int i = 0; i < d1 - d0; i++) {
        node1 = node1.parent;
      }
    }
    BinaryTree<Integer> lca = null;
    while (node0 != node1) {
      node0 = node0.parent;
      node1 = node1.parent;
    }
    lca = node0;
    return lca;
  }

  private static int getDepth(BinaryTree<Integer> node0) {
    int depth = 0;
    while (node0 != null) {
      node0 = node0.parent;
      depth++;
    }
    return depth;
  }

  @EpiTest(testDataFile = "lowest_common_ancestor.tsv")
  public static int lcaWrapper(TimedExecutor executor, BinaryTree<Integer> tree,
                               Integer key0, Integer key1) throws Exception {
    BinaryTree<Integer> node0 = BinaryTreeUtils.mustFindNode(tree, key0);
    BinaryTree<Integer> node1 = BinaryTreeUtils.mustFindNode(tree, key1);

    BinaryTree<Integer> result = executor.run(() -> LCA(node0, node1));

    if (result == null) {
      throw new TestFailure("Result can not be null");
    }
    return result.data;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "LowestCommonAncestorWithParent.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
