public class Checkout {

    private final Order order;
    private final Payment payment;

    public Checkout(Order order, Payment payment) {
        this.order = order;
        this.payment = payment;
    }

    public boolean createOrder(Item item, Customer customer) {
        ReversibleAction<Boolean> withdraw = new ReversibleAction<>(
                () -> payment.withdraw(item.price, customer.id),
                () -> payment.deposit(item.price, customer.id)
        );
        ReversibleAction<Boolean> placeOrder = new ReversibleAction<>(
                () -> order.place(item.id),
                () -> true
        );

        Transaction transaction = new Transaction(withdraw, placeOrder);
        return transaction.execute();
    }
}
