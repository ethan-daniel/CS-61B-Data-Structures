public class SLList {
    private class IntNode {
        public int item;
        public IntNode next;
        public IntNode (int item, IntNode next) {
            this.item = item;
            this.next = next;
        }
    }

    private IntNode first;

    public void addFirst(int x) {
        first = new IntNode(x, first);
    }

    public void reverse() {
        first = reverseRecursiveHelper(first);
    }

    private IntNode reverseRecursiveHelper(IntNode front) {
        if (front == null || front.next == null) {
            return front;
        } else {
            IntNode reversed = reverseRecursiveHelper(front.next);
            front.next.next = front;
            front.next = null;
            return reversed;
        }
    }

    public static void main(String[] args) {
        SLList sll = new SLList();
        sll.addFirst(40);
        sll.addFirst(30);
        sll.addFirst(20);
        sll.addFirst(10);
        sll.reverse();
    }
}
