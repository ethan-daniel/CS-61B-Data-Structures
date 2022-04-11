package synthesizer;

public abstract class AbstractBoundedQueue<T> implements BoundedQueue<T> {
    protected int fillCount;
    protected int capacity;
    public abstract T peek();
    public abstract T dequeue();
    public abstract void enqueue(T x);

    /** Return size of the buffer */
    @Override
    public int capacity() {
        return capacity;
    }

    /** Return number of items currently in the buffer */
    @Override
    public int fillCount() {
        return fillCount;
    }
}
