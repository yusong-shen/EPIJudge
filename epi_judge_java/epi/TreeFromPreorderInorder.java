package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TreeFromPreorderInorder {
  @EpiTest(testDataFile = "tree_from_preorder_inorder.tsv")

  public static BinaryTreeNode<Integer>
  binaryTreeFromPreorderInorder(List<Integer> preorder, List<Integer> inorder) {
    Map<Integer, Integer> inorderMap = new HashMap<>();
    int i = 0;
    for (int v : inorder) {
      inorderMap.put(v, i++);
    }
    return buildTree(preorder, inorder, 0, preorder.size() - 1, 0, inorder.size() - 1, inorderMap);
  }

  private static BinaryTreeNode<Integer> buildTree(List<Integer> preorder, List<Integer> inorder, int pl, int pr,
                                                   int il, int ir, Map<Integer, Integer> inorderMap) {
    if (pl > pr) return null;
    BinaryTreeNode<Integer> root = new BinaryTreeNode<Integer>(preorder.get(pl));
    int rootInd = inorderMap.get(root.data);
    int leftSize = rootInd - il;
    int rightSize = ir - rootInd;
    // invariant : pr - pl == ir - il
    root.left = buildTree(preorder, inorder, pl + 1, pl + leftSize, il, il + leftSize - 1, inorderMap);
    root.right = buildTree(preorder, inorder, pr - rightSize + 1, pr, ir - rightSize + 1, ir, inorderMap);
    return root;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "TreeFromPreorderInorder.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
