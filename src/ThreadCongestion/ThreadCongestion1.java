package ThreadCongestion;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ThreadCongestion1 {
    public static void main(String[] args) {
        int numOfObjects = 1_000_000;

        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(numOfObjects);
        ConsumerRunnable[] consumerRunnables = new ConsumerRunnable[3];

        synchronized (ThreadCongestion1.class) {
            for (int i = 0; i < consumerRunnables.length; i++) {
                consumerRunnables[i] = new ConsumerRunnable(blockingQueue);
                Thread consumingThread = new Thread(consumerRunnables[i]);
                consumingThread.start();
            }
        }

        Thread producingThread = new Thread(() -> {
            for (int i = 0; i < numOfObjects; i++) {
                try {
                    blockingQueue.put(String.valueOf(i));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("All objects are produced");
            System.out.println("Produced ==> " + numOfObjects);
            synchronized (ThreadCongestion1.class) {
                for (int i = 0; i < consumerRunnables.length; i++) {
                    consumerRunnables[i].stop();
                }
            }
        });
        producingThread.start();
    }
}
