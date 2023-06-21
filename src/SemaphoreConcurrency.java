import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
public class SemaphoreConcurrency {
    public static void main(String[] args) throws InterruptedException {
        Semaphore semaphore = new Semaphore(3);
        ExecutorService service = Executors.newFixedThreadPool(50);
        IntStream.of(1000).forEach(i -> service.execute(new Task2(semaphore)));
        service.shutdown();
        service.awaitTermination(1, TimeUnit.MINUTES);
    }
}

class Task2 implements Runnable {
    Semaphore semaphore;

    Task2(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        // processing
        semaphore.acquireUninterruptibly();
        // IO call to the slow service
        System.out.println(semaphore.availablePermits());
        semaphore.release();
        // rest of processing
    }
}