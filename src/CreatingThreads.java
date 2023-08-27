public class CreatingThreads {
    public static void main(String[] args) {
        MyThread1 thread1 = new MyThread1();

        Thread thread2 = new Thread(new MyThread2());

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread 3");
            }
        };
        Thread thread3 = new Thread(runnable);

        Runnable runnable2 = () -> {
            System.out.println("Thread 4");
        };
        Thread thread4 = new Thread(runnable2);

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
    }

    public static class MyThread1 extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Thread 1");
        }
    }

    public static class MyThread2 implements Runnable {
        @Override
        public void run() {
            System.out.println("Thread 2");
        }
    }
}
