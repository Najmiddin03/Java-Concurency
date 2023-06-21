import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TimeoutThread {
    public static void main(String[] args) throws InterruptedException {
        // By putting main thread to sleep
        Task task = new Task() {
            @Override
            public void run() {
                while (!Thread.interrupted()) {
                    System.out.println("Hello");
                }
            }
        };
        Thread t1 = new Thread(task);
        t1.start();
        //Sleep 5 seconds
        Thread.sleep(1000 * 5);
        //Stop thread
        t1.interrupt();

        System.out.println("First task done");
        Thread.sleep(1000);

        //Using Scheduler to cancel
        Thread t2 = new Thread(task);
        t2.start();
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.schedule(t2::interrupt, 5, TimeUnit.SECONDS);
    }
}
