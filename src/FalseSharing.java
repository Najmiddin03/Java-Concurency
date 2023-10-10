import jdk.internal.vm.annotation.Contended;

public class FalseSharing {
    public static void main(String[] args) {
        long iterations = 1_000_000_000;

        Counter1 counter1 = new Counter1();
        Counter1 counter2 = counter1;

        Thread thread1 = new Thread(() -> {
            long startTime = System.currentTimeMillis();
            for (long i = 0; i < iterations; i++) {
                counter1.count1++;
            }
            long endTime = System.currentTimeMillis();
            System.out.println("total time with false-sharing: " + (endTime - startTime));
        });
        Thread thread2 = new Thread(() -> {
            long startTime = System.currentTimeMillis();
            for (long i = 0; i < iterations; i++) {
                counter2.count2++;
            }
            long endTime = System.currentTimeMillis();
            System.out.println("total time with false-sharing: " + (endTime - startTime));
        });
        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Counter1 counter3 = new Counter1();
        Counter1 counter4 = new Counter1();

        Thread thread3 = new Thread(() -> {
            long startTime = System.currentTimeMillis();
            for (long i = 0; i < iterations; i++) {
                counter3.count1++;
            }
            long endTime = System.currentTimeMillis();
            System.out.println("total time without false-sharing: " + (endTime - startTime));
        });
        Thread thread4 = new Thread(() -> {
            long startTime = System.currentTimeMillis();
            for (long i = 0; i < iterations; i++) {
                counter4.count2++;
            }
            long endTime = System.currentTimeMillis();
            System.out.println("total time without false-sharing: " + (endTime - startTime));
        });
        thread3.start();
        thread4.start();
        try {
            thread3.join();
            thread4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Counter1 {
    public volatile long count1 = 0;
    public volatile long count2 = 0;
}

//Solutions for avoiding false-sharing
class Counter2 {
    @Contended
    public volatile long count1 = 0;
    @Contended
    public volatile long count2 = 0;
}

@Contended
class Counter3 {
    public volatile long count1 = 0;
    public volatile long count2 = 0;
}

class Counter4 {
    @Contended("group1")
    public volatile long count1 = 0;
    @Contended("group1")
    public volatile long count2 = 0;
    @Contended("group2")
    public volatile long count3 = 0;
}