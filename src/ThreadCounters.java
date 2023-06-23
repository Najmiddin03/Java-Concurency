import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

public class ThreadCounters {
    public static void main(String[] args) throws InterruptedException {
        // AtomicLong
        AtomicLong counter = new AtomicLong(0);
        ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        for (int i = 0; i < 100; i++) {
            service.submit(new Task(counter));
        }
        Thread.sleep(1000);
        System.out.println("AtomicLong counter: " + counter.get());

        // LongAdder
        LongAdder adder = new LongAdder();
        for (int i = 0; i < 100; i++) {
            service.submit(new Task(adder));
        }
        Thread.sleep(1000);
        System.out.println("LongAdder counter: " + adder.sum());


        // LongAccumulator
        LongAccumulator accumulator = new LongAccumulator((x, y) -> x + y, 0);
        for (int i = 0; i < 100; i++) {
            service.submit(new Task(accumulator));
        }
        Thread.sleep(1000);
        System.out.println("LongAccumulator counter: " + accumulator.get());
        service.shutdown();
    }

    private static class Task implements Runnable {
        AtomicLong counter;
        LongAdder adder;
        LongAccumulator accumulator;

        Task(AtomicLong counter) {
            this.counter = counter;
        }

        Task(LongAdder adder) {
            this.adder = adder;
        }

        Task(LongAccumulator accumulator) {
            this.accumulator = accumulator;
        }

        @Override
        public void run() {
            if (counter != null) {
                counter.incrementAndGet();
            }
            if (adder != null) {
                adder.increment();
            }
            if (accumulator != null) {
                accumulator.accumulate(1);
            }
        }
    }
}
