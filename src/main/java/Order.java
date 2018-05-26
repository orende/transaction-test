import java.util.Map;

/**
 * Created by ola on 2018-05-26.
 */
public class Order {

    private final Map<Integer, Integer> inventory;

    public Order(Map<Integer, Integer> inventory) {
        this.inventory = inventory;
    }

    public synchronized boolean place(int id) {
        Integer currentStock = inventory.getOrDefault(id, 0);
        if (currentStock > 0) {
            inventory.put(id, currentStock - 1);
            return true;
        }
        throw new IllegalStateException("Insufficient inventory");
    }
}
