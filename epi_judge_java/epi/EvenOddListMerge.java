package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import java.util.List;
public class EvenOddListMerge {
  @EpiTest(testDataFile = "even_odd_list_merge.tsv")

  public static ListNode<Integer> evenOddMerge(ListNode<Integer> L) {
    ListNode<Integer> evenDummyHead = new ListNode<>(-1, null);
    ListNode<Integer> oddDummyHead = new ListNode<>(-1, null);
    ListNode<Integer> evenIter = evenDummyHead, oddIter = oddDummyHead;
    int i = 0;
    while (L != null) {
      if (i % 2 == 0) {
        evenIter.next = L;
        evenIter = evenIter.next;
      } else {
        oddIter.next = L;
        oddIter = oddIter.next;
      }
      i++;
      L = L.next;
    }
    oddIter.next = null; // make sure it's not connected to the original list
    evenIter.next = oddDummyHead.next;
    return evenDummyHead.next;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "EvenOddListMerge.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
