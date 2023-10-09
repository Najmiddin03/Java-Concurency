import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeadlockPrevention {
    public static void main(String[] args) {
        Lock lock1 = new ReentrantLock();
        Lock lock2 = new ReentrantLock();
        Runnable runnable1 = new Runnable1(lock1, lock2);
        Runnable runnable2 = new Runnable2(lock1, lock2);
        Thread thread1 = new Thread(runnable1);
        Thread thread2 = new Thread(runnable2);
        thread1.start();
        thread2.start();
    }
}

class Runnable1 implements Runnable {

    Lock lock1;
    Lock lock2;

    public Runnable1(Lock lock1, Lock lock2) {
        this.lock1 = lock1;
        this.lock2 = lock2;
    }

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();

        while (true) {
            int failureCount = 0;
            while (!tryLockBothLocks()) {
                failureCount++;
                System.err.println(threadName + " failed to lock both locks. [" + failureCount + " tries]");
                try {
                    Thread.sleep((long) (1000 * Math.random()));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (failureCount > 0) {
                System.out.println(threadName + " succeeded in locking both locks after " + failureCount + " failures");
            }
            lock2.unlock();
            lock1.unlock();
        }
    }

    private boolean tryLockBothLocks() {
        String threadName = Thread.currentThread().getName();

        try {
            if (!lock1.tryLock(1000, TimeUnit.MILLISECONDS)) {
                return false;
            }
        } catch (InterruptedException e) {
            System.out.println(threadName + " interrupted trying to lock Lock1");
            return false;
        }
        try {
            if (!lock2.tryLock(1000, TimeUnit.MILLISECONDS)) {
                lock1.unlock();
                return false;
            }
        } catch (InterruptedException e) {
            System.out.println(threadName + " interrupted trying to lock Lock2");
            lock2.unlock();
            return false;
        }
        return true;
    }
}

class Runnable2 implements Runnable {

    Lock lock1;
    Lock lock2;

    public Runnable2(Lock lock1, Lock lock2) {
        this.lock1 = lock1;
        this.lock2 = lock2;
    }

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();

        while (true) {
            int failureCount = 0;
            while (!tryLockBothLocks()) {
                failureCount++;
                System.err.println(threadName + " failed to lock both locks. [" + failureCount + " tries]");
                try {
                    Thread.sleep((long) (1000 * Math.random()));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (failureCount > 0) {
                System.out.println(threadName + " succeeded in locking both locks after " + failureCount + " failures");
            }
            lock2.unlock();
            lock1.unlock();
        }
    }

    private boolean tryLockBothLocks() {
        String threadName = Thread.currentThread().getName();

        try {
            if (!lock2.tryLock(1000, TimeUnit.MILLISECONDS)) {
                return false;
            }
        } catch (InterruptedException e) {
            System.out.println(threadName + " interrupted trying to lock Lock2");
            return false;
        }
        try {
            if (!lock1.tryLock(1000, TimeUnit.MILLISECONDS)) {
                lock2.unlock();
                return false;
            }
        } catch (InterruptedException e) {
            System.out.println(threadName + " interrupted trying to lock Lock1");
            lock1.unlock();
            return false;
        }
        return true;
    }
}