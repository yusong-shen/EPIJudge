package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
public class ReverseSublist {
  @EpiTest(testDataFile = "reverse_sublist.tsv")

  public static ListNode<Integer> reverseSublist(ListNode<Integer> L, int start,
                                                 int finish) {
    if (start == finish) return  L;
    ListNode<Integer> dummyHead = new ListNode<>(-1, L);
    ListNode<Integer> sublistHead = dummyHead; // the node before reverse
    int k = 1; // since 'start' begin with 1
    while (k++ < start) {
      sublistHead = sublistHead.next;
    }
    // before iteration : 11 -> | 3 -> 5 -> 7 | -> 2  (s = 2, f = 4)
    // sublistIter scan through the list
    ListNode<Integer> sublistIter = sublistHead.next; // 11.next = 3
    while (k++ <= finish) {
      // 1st iteration : 11 -> | 5 -> 3 -> 7 | -> 2
      // 2nd iteration : 11 -> | 7 -> 5 -> 3 | -> 2
      ListNode<Integer> temp = sublistIter.next; // 5, 7
      sublistIter.next = temp.next; // 3 -> 7, 3 -> 2
      temp.next = sublistHead.next; // 5 -> 3, 7 -> 5
      sublistHead.next = temp; // 11 -> 5, 11 -> 7
    }

    return dummyHead.next;
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "ReverseSublist.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
