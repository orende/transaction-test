import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by ola on 2018-05-26.
 */
public class OrderTest {

    public static final Item BOOK = new Item(456, 25);
    public static final Customer CUSTOMER = new Customer(1);

    @Test
    public void checkoutSuccessfully() throws Exception {
        Order order = new Order(Maps.newHashMap(ImmutableMap.of(BOOK.id, 1)));
        Payment payment = new Payment(Maps.newHashMap(ImmutableMap.of(CUSTOMER.id, 100)));
        Checkout checkout = new Checkout(order, payment);

        assertThat(checkout.createOrder(BOOK, CUSTOMER)).isTrue();
    }

    @Test
    public void orderPlacementFailsPaymentShouldNotBeReduced() throws Exception {
        Order order = new Order(Maps.newHashMap(ImmutableMap.of(BOOK.id, 0)));
        Payment payment = new Payment(Maps.newHashMap(ImmutableMap.of(CUSTOMER.id, 100)));
        Checkout checkout = new Checkout(order, payment);

        assertThat(checkout.createOrder(BOOK, CUSTOMER)).isFalse();
        assertThat(payment.getBalance(CUSTOMER.id)).isEqualTo(100);
    }
}