package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import java.util.List;
public class BstFromPreorder {
  private static int ind;

  @EpiTest(testDataFile = "bst_from_preorder.tsv")
  public static BstNode<Integer>
  rebuildBSTFromPreorder(List<Integer> preorderSequence) {
    if (preorderSequence.isEmpty()) return null;
    ind = 0;

    return rebuildBSTHelper(preorderSequence, Integer.MIN_VALUE, Integer.MAX_VALUE);
  }

  private static  BstNode<Integer> rebuildBSTHelper(List<Integer> preorderSequence, int minVal, int maxVal) {
    if (ind >= preorderSequence.size()) return null;
    BstNode<Integer> root = new BstNode<>(preorderSequence.get(ind));
    if (root.data < minVal || root.data > maxVal) return null;
    ind++;
    root.left = rebuildBSTHelper(preorderSequence, minVal, root.data);
    root.right = rebuildBSTHelper(preorderSequence, root.data, maxVal);
    return root;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "BstFromPreorder.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
