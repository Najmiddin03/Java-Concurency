public class CreatingThreads {
    public static void main(String[] args) {

    }

    public static class MyThread1 extends Thread{
        @Override
        public void run() {
            System.out.println("Thread 1");
        }
    }
}
