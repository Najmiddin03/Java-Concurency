import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MyBlockingQueue {
    private final Queue<String> queue;
    private int max = 16;
    private final ReentrantLock lock = new ReentrantLock(true);
    private final Condition notEmpty = lock.newCondition();
    private final Condition notFull = lock.newCondition();

    public MyBlockingQueue(int size) {
        queue = new LinkedList<>();
        max = size;
    }

    public void put(String str) {
        lock.lock();
        try {
            if (queue.size() == max) {
                notFull.await();
            }
            queue.add(str);
            notEmpty.signalAll();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public String take() {
        lock.lock();
        try {
            while (queue.size() == 0) {
                notEmpty.await();
            }
            String item = queue.remove();
            notFull.signalAll();
            return item;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.lock();
        }
    }
}
