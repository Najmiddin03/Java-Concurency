import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockExample {
    public static void main(String[] args) {
        lockBasics();
    }

    private static void lockBasics() {
        Lock lock = new ReentrantLock(true);
        Runnable runnable = () -> lockSleepUnlock(lock, 1000);

        Runnable runnable2 = () -> tryLock(lock);

        Thread thread1 = new Thread(runnable, "Thread 1");
        Thread thread2 = new Thread(runnable, "Thread 2");
        Thread thread3 = new Thread(runnable, "Thread 3");

        //tryLock() example
        Thread thread4 = new Thread(runnable2);

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
    }

    private static void lockSleepUnlock(Lock lock, long timeMillis) {
        try {
            lock.lock();
            printThreadMSG(" holds the lock");
            sleep(timeMillis);
        } finally {
            lock.unlock();
        }
    }

    private static void tryLock(Lock lock) {
        boolean lockSuccessful = false;
        try {
            //tryLock() method, returns false if lock is not successful
            lockSuccessful = lock.tryLock();
            System.out.println("Lock successful: " + lockSuccessful);
        } finally {
            if (lockSuccessful) {
                lock.unlock();
            }
        }
    }

    private static void printThreadMSG(String text) {
        System.out.println(Thread.currentThread().getName() + text);
    }

    private static void sleep(long timeMillis) {
        try {
            Thread.sleep(timeMillis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
