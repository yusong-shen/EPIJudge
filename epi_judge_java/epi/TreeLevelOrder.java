package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

import java.util.*;

public class TreeLevelOrder {
  @EpiTest(testDataFile = "tree_level_order.tsv")

  public static List<List<Integer>>
  binaryTreeDepthOrder(BinaryTreeNode<Integer> tree) {
    if (tree == null) return Collections.emptyList();
    Queue<BinaryTreeNode> q = new LinkedList<>();
    List<List<Integer>> result = new ArrayList<>();
    q.offer(tree);
    while (!q.isEmpty()) {
      int qsize = q.size();
      List<Integer> level = new ArrayList<>();
      for (int i = 0; i < qsize; i++) {
        BinaryTreeNode<Integer> cur = q.poll();
        level.add(cur.data);
        if (cur.left != null) {
          q.offer(cur.left);
        }
        if (cur.right != null) {
          q.offer(cur.right);
        }
      }
      result.add(level);
    }

    return result;
  }

    public List<List<Integer>> zigzagLevelOrder(BinaryTreeNode<Integer> root) {
        if (root == null) return Collections.emptyList();
        List<List<Integer>> result = new ArrayList<>();
        Queue<BinaryTreeNode<Integer>> q = new LinkedList<>();
        q.offer(root);
        int levelNum = 0;
        while (!q.isEmpty()) {
            int levelSize = q.size();
            List<Integer> level = new LinkedList<>();
            for (int i = 0; i < levelSize; i++) {
                BinaryTreeNode<Integer> cur = q.poll();

                if (levelNum % 2 == 0) {
                    level.add(cur.data);
                } else {
                    level.add(0, cur.data);
                }
                if (cur.left != null) {
                    q.offer(cur.left);
                }
                if (cur.right != null) {
                    q.offer(cur.right);
                }
            }
            levelNum++;
            result.add(level);
        }
        return result;
    }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "TreeLevelOrder.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
