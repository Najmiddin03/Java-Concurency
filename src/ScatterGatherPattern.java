import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ScatterGatherPattern {
    static ExecutorService threadPool = Executors.newFixedThreadPool(4);

    public static void main(String[] args) throws InterruptedException {
        Set<Integer> set = getPrices(1565);
        System.out.println(set);
    }

    private static Set<Integer> getPrices(int productId) throws InterruptedException {
        Set<Integer> prices = Collections.synchronizedSet(new HashSet<>());
        CountDownLatch latch = new CountDownLatch(3);

        threadPool.submit(new Task3("Url1", productId, prices, latch));
        threadPool.submit(new Task3("Url2", productId, prices, latch));
        threadPool.submit(new Task3("Url3", productId, prices, latch));

        latch.await();

        return prices;
    }
}

class Task3 implements Runnable {
    private final String url;
    private final int productId;
    private final Set<Integer> prices;
    private final CountDownLatch latch;

    public Task3(String url, int productId, Set<Integer> prices, CountDownLatch latch) {
        this.url = url;
        this.productId = productId;
        this.prices = prices;
        this.latch = latch;
    }

    @Override
    public void run() {
        int price = 0;
        // http call
        prices.add(price);
        latch.countDown();
    }
}


