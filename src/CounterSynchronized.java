public class CounterSynchronized {
    private long count = 0;

    public synchronized long incAndGet() {
        this.count++;
        return this.count;
    }

    public synchronized long get() {
        return this.count;
    }
}
