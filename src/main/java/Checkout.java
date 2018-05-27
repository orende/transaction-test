import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;

public class Checkout implements EventListerner {

    private final Order order;
    private final Payment payment;
    private final EventQueue eventQueue;
    private final Map<UUID, Transaction> transactionDatabase = new HashMap<>();

    public Checkout(Order order, Payment payment, EventQueue eventQueue) {
        this.order = order;
        this.payment = payment;
        this.eventQueue = eventQueue;
        eventQueue.register("payment-withdrawn", this);
        eventQueue.register("order-placed", this);
    }

    public CompletableFuture<Boolean> createOrder(Item item, Customer customer) {
        ReversibleAction<Boolean> withdraw = new ReversibleAction<>(
                () -> payment.withdraw(item.price, customer.id),
                () -> payment.deposit(item.price, customer.id)
        );
        ReversibleAction<Boolean> placeOrder = new ReversibleAction<>(
                () -> order.place(item.id),
                () -> true
        );

        Transaction transaction = new Transaction(withdraw, placeOrder);
        transactionDatabase.put(UUID.randomUUID(), transaction);
        return transaction.execute();
    }

    @Override
    public void onMessage(Message message) {
        Transaction transaction = transactionDatabase.get(message.getTransaction());
        transaction.execute();
    }
}
