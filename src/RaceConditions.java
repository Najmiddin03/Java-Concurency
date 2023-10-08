public class RaceConditions {
    public static void main(String[] args) {
        //Race Conditions problem
        Counter counter = new Counter();
        Thread thread1 = new Thread(runnable(counter, "Thread1: "));
        Thread thread2 = new Thread(runnable(counter, "Thread2: "));
        thread1.start();
        thread2.start();

        //Race Conditions solution
        CounterSynchronized counterSynchronized = new CounterSynchronized();
        Thread thread3 = new Thread(runnable2(counterSynchronized, "Thread3: "));
        Thread thread4 = new Thread(runnable2(counterSynchronized, "Thread4: "));
        thread3.start();
        thread4.start();
    }

    private static Runnable runnable(Counter counter, String message) {
        return () -> {
            for (int i = 0; i < 1000000; i++) {
                counter.incAndGet();
            }
            System.out.println(message + counter.get());
        };
    }

    private static Runnable runnable2(CounterSynchronized counter, String message) {
        return () -> {
            for (int i = 0; i < 1000000; i++) {
                counter.incAndGet();
            }
            System.out.println(message + counter.get());
        };
    }
}
