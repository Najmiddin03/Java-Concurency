public class ConsumerProducer {
    public static void main(String[] args) throws InterruptedException {
        MyBlockingQueue queue = new MyBlockingQueue(20);
        // Producer
        final Runnable producer = () -> {
            while (true) {
                queue.put("Cotton");
                System.out.println("Cotton inserted");
            }
        };
        new Thread(producer).start();
        new Thread(producer).start();

        // Consumer
        final Runnable consumer = () -> {
            while (true) {
                System.out.println(queue.take() + " taken");
            }
        };
        new Thread(consumer).start();
        new Thread(consumer).start();

        Thread.sleep(1000);
    }
}
