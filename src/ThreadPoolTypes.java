import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTypes {
    public static void main(String[] args) {
        // CPU cores
        int coreCount = Runtime.getRuntime().availableProcessors();
        System.out.println("Cores in the CPU: " + coreCount);

        // Types of Thread pools
        // 1. FixedThreadPool
        ExecutorService service = Executors.newFixedThreadPool(coreCount);
        for (int i = 0; i < 5; i++) {
            service.execute(new Task());
        }
        System.out.println("Thread name: " + Thread.currentThread().getName());

        // 2. CachedThreadPool
        ExecutorService service1 = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            service1.execute(new Task());
        }
        // 3. ScheduledThreadPool
        ScheduledExecutorService service2 = Executors.newScheduledThreadPool(5);

        // task to run after 3 seconds delay
        service2.schedule(new Task("after delay"), 3, TimeUnit.SECONDS);

        // task to run repeatedly every 3 seconds indefinitely
        service2.scheduleAtFixedRate(new Task("repeat"), 3, 3, TimeUnit.SECONDS);

        // task to run repeatedly 3 seconds indefinitely after previous task completes
        service2.scheduleWithFixedDelay(new Task("repeat after delay"), 3, 3, TimeUnit.SECONDS);

        // 4. SingleThreadedExecutor
        ExecutorService service3 = Executors.newSingleThreadExecutor();
        service3.execute(new Task("single Thread"));
    }
}

class Task implements Runnable {
    String poolName;

    public Task() {
    }

    public Task(String poolName) {
        this.poolName = poolName;
    }

    @Override
    public void run() {
        System.out.println("Thread name: " + Thread.currentThread().getName());
        if (poolName != null) {
            System.out.println(poolName);
        }
        System.out.println(Thread.activeCount());
    }
}