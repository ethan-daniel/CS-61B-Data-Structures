import java.lang.reflect.Array;

public class ArrayDeque<T> {
    private T[] items;
    private int size, nextFirst, nextLast;
    private static int RFACTOR = 2;

    public ArrayDeque(){
        items = (T[]) new Object[8];
        size = 0;
        nextFirst = 4;
        nextLast = 5;
    }

    /** Helper Functions*/
    /** Returns the first item in the array */
    public T getFirst() {
        if (!isEmpty()){
            if (nextFirst == items.length - 1){
                nextFirst = -1;
            }
            int index = nextFirst + 1;
            return items[index];
        }
        return null;
    }
    /** Returns the last item in the array */
    public T getLast() {
        if (!isEmpty()){
            if (nextLast == 0){
                nextLast = items.length;
            }
            int index = nextLast - 1;
            return items[index];
        }
        return null;
    }

    /** Grows the underlying array to the target capacity.
     * Used in addLast() */
    public void growLast(int capacity) {
        T[] a = (T[]) new Object[capacity];
        System.arraycopy(items, 0, a, 0, nextFirst + 1);
        System.arraycopy(items, nextLast, a, nextFirst + capacity/2 + 1, size - nextLast);
        nextFirst += capacity/2;
        items = a;
    }

    /** Grows the underlying array to the target capacity.
     * Used in addLast() */
    public void growFirst(int capacity){
        T[] a = (T[]) new Object[capacity];
        System.arraycopy(items, 0, a, 0, nextLast);
        System.arraycopy(items, nextLast, a, nextLast + capacity/2, size - nextLast);
        nextFirst += capacity/2;
        items = a;
    }

    /** Reduces the underlying array to the target capacity.
     * Used in removeFirst() */
    public void reduceFirst(int capacity){
        T[] a = (T[]) new Object[capacity];
        System.arraycopy(items, 0, a, 0, nextLast);
        System.arraycopy(items, nextFirst + 1, a, nextFirst + 1 - capacity, size - nextLast);
        nextFirst -= capacity;
        items = a;
    }

    /** Reduces the underlying array to the target capacity.
     * Used in removeLast() */
    public void reduceLast(int capacity){
        T[] a = (T[]) new Object[capacity];
        System.arraycopy(items, 0, a, 0, nextLast);
        System.arraycopy(items, nextFirst + 1, a, nextFirst + 1 - capacity, size - nextLast);
        nextFirst -= capacity;
        items = a;
    }

    /** Adds an item of type T to the front of the deque. */
    public void addFirst(T item){
        //TODO: same as below issue
        if (nextFirst == nextLast - 1 && size == items.length){
            growFirst(size * RFACTOR);
        }
        if (nextFirst == -1){
            nextFirst = items.length - 1;
        }
        items[nextFirst] = item;
        --nextFirst;
        ++size;
    }
    /** Adds an item of type T to the back of the deque. */
    public void addLast(T item){
        //TODO: resizing? what to do when trying to overwrite a front one
        if (nextLast == nextFirst + 1 && size == items.length) {
            growLast(size * RFACTOR);
        }
        if (nextLast == items.length){
            nextLast = 0;
        }
        items[nextLast] = item;
        ++nextLast;
        ++size;
    }
    /** Returns true if deque is empty, false otherwise. */
    public boolean isEmpty(){
        return size == 0;
    }

    /** Returns the number of items in the deque. */
    public int size(){
        return size;
    }
    /** Prints the items in the deque from first to last, separated by a space. */
    public void printDeque(){
        if (!isEmpty()){
            for (int i = 0; i != size(); ++i){
                System.out.print(get(i) + " ");
            }
        }
    }

    /** Removes and returns the item at the front of the deque.
     * If no such item exists, returns null. */
    public T removeFirst() {
        if (!isEmpty()){
            T x = getFirst();
            ++nextFirst;
            items[nextFirst] = null;
            --size;
            if ((float)size/items.length < 0.25 && items.length >= 16){
                reduceFirst(items.length / RFACTOR);
            }
            return x;
        }
        return null;
    }

    /** Removes and returns the item at the back of the deque.
     * If no such item exists, returns null. */
    public T removeLast(){
        if (!isEmpty()){
            T x = getLast();
            --nextLast;
            items[nextLast] = null;
            --size;
            if ((float)size/items.length < 0.25 && items.length >= 16){
                reduceLast(items.length / RFACTOR);
            }

            return x;
        }
        return null;
    }
    /** Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     * If no such item exists, returns null. Must not alter the deque!*/
    public T get(int index){
        if (!isEmpty()){
            int adjusted_index = index + (nextFirst + 1);
            if (adjusted_index >= items.length){
                adjusted_index -= items.length;
            }
            return items[adjusted_index];
        }
        return null;
    }
}
