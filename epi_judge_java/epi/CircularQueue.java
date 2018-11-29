package epi;
import epi.test_framework.EpiTest;
import epi.test_framework.EpiUserType;
import epi.test_framework.GenericTest;
import epi.test_framework.TestFailure;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
public class CircularQueue {

  public static class Queue {
    Integer[] q;
    int numElements = 0;
    int head = 0, tail = 0;

    public Queue(int capacity) {
      q = new Integer[capacity];
    }
    public void enqueue(Integer x) {
      if (numElements == q.length) {
        // q is full, dynamically resize array
        Collections.rotate(Arrays.asList(q), -head);
        head = 0;
        tail = numElements;
        q = Arrays.copyOf(q, numElements * 2);
      }
      q[tail] = x;
      tail = (tail + 1) % q.length;
      numElements++;
      return;
    }
    public Integer dequeue() {
      if (numElements != 0) {
        numElements--;
        int result = q[head];
        head = (head + 1) % q.length;
        return result;
      }
      return null;
    }
    public int size() {
      return numElements;
    }
    @Override
    public String toString() {
      return super.toString();
    }
  }
  @EpiUserType(ctorParams = {String.class, int.class})
  public static class QueueOp {
    public String op;
    public int arg;

    public QueueOp(String op, int arg) {
      this.op = op;
      this.arg = arg;
    }

    @Override
    public String toString() {
      return op;
    }
  }

  @EpiTest(testDataFile = "circular_queue.tsv")
  public static void queueTest(List<QueueOp> ops) throws TestFailure {
    Queue q = new Queue(1);
    int opIdx = 0;
    for (QueueOp op : ops) {
      switch (op.op) {
      case "Queue":
        q = new Queue(op.arg);
        break;
      case "enqueue":
        q.enqueue(op.arg);
        break;
      case "dequeue":
        int result = q.dequeue();
        if (result != op.arg) {
          throw new TestFailure()
              .withProperty(TestFailure.PropertyName.STATE, q)
              .withProperty(TestFailure.PropertyName.COMMAND, op)
              .withMismatchInfo(opIdx, op.arg, result);
        }
        break;
      case "size":
        int s = q.size();
        if (s != op.arg) {
          throw new TestFailure()
              .withProperty(TestFailure.PropertyName.STATE, q)
              .withProperty(TestFailure.PropertyName.COMMAND, op)
              .withMismatchInfo(opIdx, op.arg, s);
        }
        break;
      }
      opIdx++;
    }
  }

  public static void main(String[] args) {
    System.exit(
        GenericTest
            .runFromAnnotations(args, "CircularQueue.java",
                                new Object() {}.getClass().getEnclosingClass())
            .ordinal());
  }
}
