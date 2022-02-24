public class LinkedListDeque<T> {

    private class Node {
        public T item;
        public Node prev;
        public Node next;

        public Node(T i, Node p, Node n) {
            item = i;
            prev = p;
            next = n;
        }
    }

    private Node sentinel;
    private int size;

    /** Helper functions */
    private T getRecursive(int i, Node n){
        if (i == 0){
            return n.item;
        }
        return getRecursive(i-1, n.next);
    }

    /** Creates an empty linked list deque. */
    public LinkedListDeque(){
        sentinel = new Node(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    /** Adds an item of type T to the front of the deque. */
    public void addFirst(T item){
        sentinel.next = new Node(item, sentinel, sentinel.next);
        sentinel.next.next.prev = sentinel.next;
        ++size;
    }

    /** Adds an item of type T to the back of the deque. */
    public void addLast(T item){
        sentinel.prev = new Node(item, sentinel.prev, sentinel);
        sentinel.prev.prev.next = sentinel.prev;
        ++size;
    }

    /** Returns true if deque is empty, false otherwise. */
    public boolean isEmpty(){
        return sentinel.next == sentinel && sentinel.prev == sentinel;
    }

    /** Returns the number of items in the deque. */
    public int size(){
        return size;
    }
    /** Prints the items in the deque from first to last, separated by a space. */
    public void printDeque(){
        if (isEmpty()){
            return;
        }
        Node p = sentinel.next;
        while (p != sentinel){
            System.out.print(p + " ");
            p = p.next;
        }
    }

    /** Removes and returns the item at the front of the deque.
     * If no such item exists, returns null. */
    public T removeFirst() {
        if (sentinel.next != null){
            T item = sentinel.next.item;
            sentinel.next = sentinel.next.next;
            if (!isEmpty()) {
                sentinel.next.prev = sentinel;
            }
            --size;
            return item;
        }
        return null;
    }

    /** Removes and returns the item at the back of the deque.
     * If no such item exists, returns null. */
    public T removeLast(){
        if (sentinel.prev != null){
            T item = sentinel.prev.item;
            sentinel.prev = sentinel.prev.prev;
            if (!isEmpty()) {
                sentinel.prev.next = sentinel;
            }
            --size;
            return item;
        }
        return null;
    }

    /** Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     * If no such item exists, returns null. Must not alter the deque!*/
    public T get(int index){
        if (index < size()) {
            Node p = sentinel.next;
            while (index != 0) {
                p = p.next;
                --index;
            }
            return p.item;
        }
        return null;
    }

    /** Same as get, but uses recursion. */
    public T getRecursive(int index){
        return getRecursive(index, sentinel);
    }
}
