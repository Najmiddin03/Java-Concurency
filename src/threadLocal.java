public class threadLocal {
    public static void main(String[] args) {
        ThreadLocal<String> threadLocal = new ThreadLocal<>();
        //ThreadLocal example
        Thread thread1 = new Thread(() -> {
            threadLocal.set("Thread 1");
            String value = threadLocal.get();
            System.out.println(value);
        });

        Thread thread2 = new Thread(() -> {
            threadLocal.set("Thread 2");
            String value = threadLocal.get();
            System.out.println(value);
        });

        //InheritableThreadLocal example
        InheritableThreadLocal<String> inheritableThreadLocal = new InheritableThreadLocal<>();
        Thread thread3 = new Thread(() -> {
            System.out.println("Parent thread");
            threadLocal.set("ThreadLocal");
            inheritableThreadLocal.set("InheritableThreadLocal");
            System.out.println(threadLocal.get());
            System.out.println(inheritableThreadLocal.get());

            Thread childThread = new Thread(() -> {
                System.out.println("Child Thread");
                System.out.println(threadLocal.get());
                System.out.println(inheritableThreadLocal.get());
            });
            childThread.start();
        });

        thread1.start();
        thread2.start();
        thread3.start();
    }
}